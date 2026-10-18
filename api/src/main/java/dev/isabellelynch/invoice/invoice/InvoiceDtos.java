package dev.isabellelynch.invoice.invoice;

import dev.isabellelynch.invoice.money.LineInput;
import dev.isabellelynch.invoice.money.TaxRate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

/** Request and response shapes for the invoice API, kept separate from the JPA entities. */
public final class InvoiceDtos {

    private InvoiceDtos() {
    }

    /** taxRate may be null here on purpose: the calculator rejects it with a friendly message. */
    public record LineRequest(
            @NotBlank @Size(max = 300) String description,
            @NotNull BigDecimal quantity,
            @NotNull BigDecimal unitPrice,
            TaxRate taxRate) {

        public LineInput toInput() {
            return new LineInput(description.trim(), quantity, unitPrice, taxRate);
        }
    }

    /** Note: no totals in the request. The client never tells the server what the totals are. */
    public record CreateDraftRequest(
            @NotNull Long customerId,
            @NotEmpty List<@Valid LineRequest> lines) {
    }

    public record LineResponse(int lineNo, String description, BigDecimal quantity, BigDecimal unitPrice,
                               TaxRate taxRate, BigDecimal net, BigDecimal tax) {
        static LineResponse from(InvoiceLine l) {
            return new LineResponse(l.getLineNo(), l.getDescription(), l.getQuantity(), l.getUnitPrice(),
                    l.getTaxRate(), l.getNet(), l.getTax());
        }
    }

    public record InvoiceResponse(Long id, String reference, Long customerId, String customerName,
                                  InvoiceStatus status, BigDecimal net, BigDecimal tax, BigDecimal total,
                                  Instant createdAt, List<LineResponse> lines) {
        /** Call this inside a transaction, because it reads the lazy customer and lines. */
        static InvoiceResponse from(Invoice i) {
            return new InvoiceResponse(i.getId(), i.reference(), i.getCustomer().getId(),
                    i.getCustomer().getName(), i.getStatus(), i.getNet(), i.getTax(), i.getTotal(),
                    i.getCreatedAt(), i.getLines().stream().map(LineResponse::from).toList());
        }
    }
}

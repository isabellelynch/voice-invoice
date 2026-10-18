package dev.isabellelynch.invoice.invoice;

import dev.isabellelynch.invoice.customer.CustomerRepository;
import dev.isabellelynch.invoice.invoice.InvoiceDtos.CreateDraftRequest;
import dev.isabellelynch.invoice.invoice.InvoiceDtos.InvoiceResponse;
import dev.isabellelynch.invoice.money.InvoiceCalculator;
import dev.isabellelynch.invoice.web.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InvoiceService {

    private static final int MAX_LIMIT = 50;

    private final InvoiceRepository invoices;
    private final CustomerRepository customers;
    private final InvoiceCalculator calculator = new InvoiceCalculator();

    public InvoiceService(InvoiceRepository invoices, CustomerRepository customers) {
        this.invoices = invoices;
        this.customers = customers;
    }

    /**
     * TODO (Isabelle): implement once InvoiceCalculator passes its tests.
     *
     *  1. If idempotencyKey is not null and invoices.findByIdempotencyKey finds one,
     *     return InvoiceResponse.from(existing). A double click gets the same draft back.
     *  2. Load the customer, or throw new NotFoundException("Customer not found").
     *  3. Turn req.lines() into LineInputs (LineRequest::toInput) and call calculator.calculate.
     *     Never trust totals from the browser.
     *  4. new Invoice(customer, idempotencyKey); for each line i, addLine(new InvoiceLine(i + 1, ...,
     *     totals.lines().get(i).net(), totals.lines().get(i).tax())); then setTotals(...).
     *  5. Save it and return InvoiceResponse.from(saved).
     *
     * Stretch: two requests with the same key at the same moment can both pass step 1.
     * The UNIQUE constraint on idempotency_key stops the second insert; catch
     * DataIntegrityViolationException and return the existing draft. Good interview story.
     */
    @Transactional
    public InvoiceResponse createDraft(CreateDraftRequest req, String idempotencyKey) {
        throw new UnsupportedOperationException("TODO: implement InvoiceService.createDraft");
    }

    @Transactional(readOnly = true)
    public InvoiceResponse get(Long id) {
        return invoices.findById(id)
                .map(InvoiceResponse::from)
                .orElseThrow(() -> new NotFoundException("Invoice not found"));
    }

    @Transactional(readOnly = true)
    public List<InvoiceResponse> recent(int limit) {
        int safeLimit = Math.max(1, Math.min(limit, MAX_LIMIT));
        return invoices.findAllByOrderByCreatedAtDesc(PageRequest.of(0, safeLimit)).stream()
                .map(InvoiceResponse::from)
                .toList();
    }
}

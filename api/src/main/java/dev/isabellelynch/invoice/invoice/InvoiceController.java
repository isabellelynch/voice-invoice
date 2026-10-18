package dev.isabellelynch.invoice.invoice;

import dev.isabellelynch.invoice.invoice.InvoiceDtos.CreateDraftRequest;
import dev.isabellelynch.invoice.invoice.InvoiceDtos.InvoiceResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** Thin on purpose: all the logic lives in InvoiceService. */
@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    private final InvoiceService service;

    public InvoiceController(InvoiceService service) {
        this.service = service;
    }

    @PostMapping("/drafts")
    @ResponseStatus(HttpStatus.CREATED)
    public InvoiceResponse createDraft(
            @Valid @RequestBody CreateDraftRequest req,
            @RequestHeader(value = "Idempotency-Key", required = false) String idempotencyKey) {
        return service.createDraft(req, idempotencyKey);
    }

    @GetMapping
    public List<InvoiceResponse> recent(@RequestParam(defaultValue = "20") int limit) {
        return service.recent(limit);
    }

    @GetMapping("/{id}")
    public InvoiceResponse get(@PathVariable Long id) {
        return service.get(id);
    }
}

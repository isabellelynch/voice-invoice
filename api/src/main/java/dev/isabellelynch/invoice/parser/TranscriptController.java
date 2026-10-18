package dev.isabellelynch.invoice.parser;

import dev.isabellelynch.invoice.customer.CustomerRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** Parses a transcript into proposed lines. Saves nothing. */
@RestController
@RequestMapping("/api/transcripts")
public class TranscriptController {

    public record ParseRequest(@NotBlank @Size(max = 2000) String transcript) {
    }

    private final CustomerRepository customers;
    private final TranscriptParser parser = new TranscriptParser();

    public TranscriptController(CustomerRepository customers) {
        this.customers = customers;
    }

    @PostMapping("/parse")
    public ParseResult parse(@Valid @RequestBody ParseRequest req) {
        List<CustomerRef> refs = customers.findAll().stream()
                .map(c -> new CustomerRef(c.getId(), c.getName()))
                .toList();
        return parser.parse(req.transcript(), refs);
    }
}

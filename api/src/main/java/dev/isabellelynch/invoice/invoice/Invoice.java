package dev.isabellelynch.invoice.invoice;

import dev.isabellelynch.invoice.customer.Customer;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Customer customer;

    // Stored as plain text to match the VARCHAR column in V1__init.sql.
    // Without this, newer Hibernate expects a native ENUM column and schema validation fails.
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private InvoiceStatus status = InvoiceStatus.DRAFT;

    private String idempotencyKey;
    private BigDecimal net;
    private BigDecimal tax;
    private BigDecimal total;
    private Instant createdAt;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("lineNo")
    private List<InvoiceLine> lines = new ArrayList<>();

    protected Invoice() {
        // for JPA
    }

    public Invoice(Customer customer, String idempotencyKey) {
        this.customer = customer;
        this.idempotencyKey = idempotencyKey;
        this.createdAt = Instant.now();
    }

    /** Adds a line and links it back to this invoice (both sides of the relationship). */
    public void addLine(InvoiceLine line) {
        line.setInvoice(this);
        lines.add(line);
    }

    public void setTotals(BigDecimal net, BigDecimal tax, BigDecimal total) {
        this.net = net;
        this.tax = tax;
        this.total = total;
    }

    /** A draft reference, deliberately not a legal invoice number. */
    public String reference() {
        return "DRAFT-%05d".formatted(id);
    }

    public Long getId() { return id; }
    public Customer getCustomer() { return customer; }
    public InvoiceStatus getStatus() { return status; }
    public String getIdempotencyKey() { return idempotencyKey; }
    public BigDecimal getNet() { return net; }
    public BigDecimal getTax() { return tax; }
    public BigDecimal getTotal() { return total; }
    public Instant getCreatedAt() { return createdAt; }
    public List<InvoiceLine> getLines() { return Collections.unmodifiableList(lines); }
}

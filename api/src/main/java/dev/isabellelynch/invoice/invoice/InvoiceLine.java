package dev.isabellelynch.invoice.invoice;

import dev.isabellelynch.invoice.money.TaxRate;
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

import java.math.BigDecimal;

@Entity
public class InvoiceLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Invoice invoice;

    private int lineNo;
    private String description;
    private BigDecimal quantity;
    private BigDecimal unitPrice;

    // Stored as plain text to match the VARCHAR column in V1__init.sql.
    // Without this, newer Hibernate expects a native ENUM column and schema validation fails.
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private TaxRate taxRate;

    private BigDecimal net;
    private BigDecimal tax;

    protected InvoiceLine() {
        // for JPA
    }

    public InvoiceLine(int lineNo, String description, BigDecimal quantity, BigDecimal unitPrice,
                       TaxRate taxRate, BigDecimal net, BigDecimal tax) {
        this.lineNo = lineNo;
        this.description = description;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.taxRate = taxRate;
        this.net = net;
        this.tax = tax;
    }

    void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Long getId() { return id; }
    public int getLineNo() { return lineNo; }
    public String getDescription() { return description; }
    public BigDecimal getQuantity() { return quantity; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public TaxRate getTaxRate() { return taxRate; }
    public BigDecimal getNet() { return net; }
    public BigDecimal getTax() { return tax; }
}

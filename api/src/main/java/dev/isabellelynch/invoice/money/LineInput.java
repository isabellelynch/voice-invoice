package dev.isabellelynch.invoice.money;

import java.math.BigDecimal;

/** One invoice line as the user entered it, before the server calculates anything. */
public record LineInput(String description, BigDecimal quantity, BigDecimal unitPrice, TaxRate taxRate) {
}

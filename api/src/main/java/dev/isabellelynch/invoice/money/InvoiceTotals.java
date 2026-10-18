package dev.isabellelynch.invoice.money;

import java.math.BigDecimal;
import java.util.List;

/** Totals for a whole invoice. lines() is in the same order as the input lines. */
public record InvoiceTotals(List<LineTotals> lines, BigDecimal net, BigDecimal tax, BigDecimal total) {
}

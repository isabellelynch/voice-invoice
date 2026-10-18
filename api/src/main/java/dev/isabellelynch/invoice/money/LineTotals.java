package dev.isabellelynch.invoice.money;

import java.math.BigDecimal;

/** Server-calculated amounts for one line, all at 2 decimal places. */
public record LineTotals(BigDecimal net, BigDecimal tax, BigDecimal gross) {
}

package dev.isabellelynch.invoice.money;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * Calculates every money amount on an invoice. The browser never does money maths;
 * the server always recalculates from the raw lines.
 *
 * Rounding policy (write this in the README too):
 *   lineNet = round(quantity x unitPrice, 2, HALF_UP)
 *   lineTax = round(lineNet x taxRate, 2, HALF_UP)
 *   invoice net / tax = sum of the rounded line values; total = net + tax
 */
public final class InvoiceCalculator {

    private static final int SCALE = 2;
    private static final RoundingMode MODE = RoundingMode.HALF_UP;

    /**
     * TODO (Isabelle): implement this, then run InvoiceCalculatorTest until it is all green.
     *
     * Steps:
     *  1. Reject a null or empty list with InvalidInvoiceException("An invoice needs at least one line").
     *  2. For each line, reject: missing tax rate, quantity null or <= 0, unit price null or < 0.
     *     Use BigDecimal.signum(), never compareTo with doubles.
     *  3. Work out lineNet and lineTax using SCALE and MODE above, and add a LineTotals.
     *  4. Keep running totals and return an InvoiceTotals.
     *
     * The "Data model, money and API" section of your build plan doc explains each step.
     */
    public InvoiceTotals calculate(List<LineInput> lines) {
        throw new UnsupportedOperationException("TODO: implement InvoiceCalculator.calculate");
    }
}

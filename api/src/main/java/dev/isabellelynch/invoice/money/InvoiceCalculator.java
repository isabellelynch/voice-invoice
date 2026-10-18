package dev.isabellelynch.invoice.money;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
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
        if(lines == null || lines.isEmpty()) {
            throw new InvalidInvoiceException("An invoice needs at least one line");
        }

        List<LineTotals> lineTotals = new ArrayList<>();
        BigDecimal net = BigDecimal.ZERO;
        BigDecimal tax = BigDecimal.ZERO;

        for (LineInput line : lines) {
            if (line.taxRate() == null) throw new InvalidInvoiceException("Choose a tax rate for every line");
            if (line.quantity() == null || line.quantity().signum() <= 0) throw new InvalidInvoiceException("Quantity must be more than zero");
            if (line.unitPrice() == null || line.unitPrice().signum() < 0) throw new InvalidInvoiceException("Unit price can't be negative");

            BigDecimal lineNet = line.unitPrice().multiply(line.quantity()).setScale(SCALE, MODE);
            BigDecimal lineTax = lineNet.multiply(line.taxRate().rate()).setScale(SCALE, MODE);
            lineTotals.add(new LineTotals(lineNet, lineTax, lineNet.add(lineTax)));

            net = net.add(lineNet);
            tax = tax.add(lineTax);
        }

        return new InvoiceTotals(lineTotals,net, tax, net.add(tax));
    }
}

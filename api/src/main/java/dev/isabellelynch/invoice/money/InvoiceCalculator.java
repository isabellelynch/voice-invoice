package dev.isabellelynch.invoice.money;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public final class InvoiceCalculator {

    private static final int SCALE = 2;
    private static final RoundingMode MODE = RoundingMode.HALF_UP;

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

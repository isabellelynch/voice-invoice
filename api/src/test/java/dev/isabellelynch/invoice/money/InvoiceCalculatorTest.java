package dev.isabellelynch.invoice.money;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * The spec for InvoiceCalculator. These fail until you implement calculate().
 * Add your own cases as you think of them.
 */
class InvoiceCalculatorTest {

    private final InvoiceCalculator calculator = new InvoiceCalculator();

    private static LineInput line(String qty, String price, TaxRate rate) {
        return new LineInput("Test line", new BigDecimal(qty), new BigDecimal(price), rate);
    }

    @Test
    void oneLineWithTax() {
        InvoiceTotals t = calculator.calculate(List.of(line("1", "120.00", TaxRate.STANDARD_DEMO)));

        assertThat(t.net()).isEqualByComparingTo("120.00");
        assertThat(t.tax()).isEqualByComparingTo("27.60");
        assertThat(t.total()).isEqualByComparingTo("147.60");
    }

    @Test
    void twoLinesAreSummed() {
        InvoiceTotals t = calculator.calculate(List.of(
                line("1", "120.00", TaxRate.STANDARD_DEMO),
                line("1", "85.50", TaxRate.ZERO)));

        assertThat(t.lines()).hasSize(2);
        assertThat(t.net()).isEqualByComparingTo("205.50");
        assertThat(t.tax()).isEqualByComparingTo("27.60");
        assertThat(t.total()).isEqualByComparingTo("233.10");
    }

    @Test
    void fractionalQuantity() {
        InvoiceTotals t = calculator.calculate(List.of(line("1.5", "40.00", TaxRate.ZERO)));

        assertThat(t.net()).isEqualByComparingTo("60.00");
    }

    @Test
    void halfCentRoundsUp() {
        // 2.50 x 23% = 0.575, which HALF_UP rounds to 0.58
        InvoiceTotals t = calculator.calculate(List.of(line("1", "2.50", TaxRate.STANDARD_DEMO)));

        assertThat(t.tax()).isEqualByComparingTo("0.58");
        assertThat(t.tax().scale()).isEqualTo(2);
    }

    @Test
    void rejectsEmptyInvoice() {
        assertThatThrownBy(() -> calculator.calculate(List.of()))
                .isInstanceOf(InvalidInvoiceException.class);
    }

    @Test
    void rejectsZeroQuantity() {
        assertThatThrownBy(() -> calculator.calculate(List.of(line("0", "10.00", TaxRate.ZERO))))
                .isInstanceOf(InvalidInvoiceException.class);
    }

    @Test
    void rejectsNegativePrice() {
        assertThatThrownBy(() -> calculator.calculate(List.of(line("1", "-5.00", TaxRate.ZERO))))
                .isInstanceOf(InvalidInvoiceException.class);
    }

    @Test
    void rejectsMissingTaxRate() {
        assertThatThrownBy(() -> calculator.calculate(List.of(line("1", "10.00", null))))
                .isInstanceOf(InvalidInvoiceException.class)
                .hasMessageContaining("tax");
    }
}

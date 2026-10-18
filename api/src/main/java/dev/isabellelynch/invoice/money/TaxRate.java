package dev.isabellelynch.invoice.money;

import java.math.BigDecimal;

/**
 * The only tax rates the app accepts. The user must pick one explicitly for every line;
 * the app never guesses. These are demo values for a prototype, not tax advice.
 */
public enum TaxRate {
    ZERO(new BigDecimal("0.00")),
    STANDARD_DEMO(new BigDecimal("0.23"));

    // Always build BigDecimal from a String: new BigDecimal(0.23) would hold
    // 0.2300000000000000099920072216264088638126850128173828125 because of how doubles work.
    private final BigDecimal rate;

    TaxRate(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal rate() {
        return rate;
    }
}

package dev.isabellelynch.invoice.parser;

/** Things the parser flags for the user instead of guessing. */
public enum IssueType {
    AMBIGUOUS_NUMBER,
    AMOUNT_WITHOUT_CURRENCY,
    NO_AMOUNT,
    CUSTOMER_NOT_FOUND,
    CUSTOMER_AMBIGUOUS,
    TAX_NOT_CHOSEN
}

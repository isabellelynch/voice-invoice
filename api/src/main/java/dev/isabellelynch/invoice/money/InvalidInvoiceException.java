package dev.isabellelynch.invoice.money;

/** Thrown when invoice input breaks a rule. The message is shown to the user, so keep it plain. */
public class InvalidInvoiceException extends RuntimeException {
    public InvalidInvoiceException(String message) {
        super(message);
    }
}

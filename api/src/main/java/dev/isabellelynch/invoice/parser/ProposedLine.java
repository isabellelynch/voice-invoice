package dev.isabellelynch.invoice.parser;

import java.math.BigDecimal;

/** A line the parser thinks it heard. The user always reviews and edits it before saving. */
public record ProposedLine(String description, BigDecimal quantity, BigDecimal unitPrice) {
}

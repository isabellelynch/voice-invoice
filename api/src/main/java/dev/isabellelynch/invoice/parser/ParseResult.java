package dev.isabellelynch.invoice.parser;

import java.util.List;

/**
 * What the parser found. customerId is null when no single customer matched.
 * Nothing here is saved; the user reviews it in the draft editor first.
 */
public record ParseResult(Long customerId, List<ProposedLine> lines, List<Issue> issues) {
}

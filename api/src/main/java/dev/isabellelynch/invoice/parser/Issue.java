package dev.isabellelynch.invoice.parser;

/**
 * Something the user needs to check.
 *
 * @param fragment the part of the transcript it relates to (may be empty)
 */
public record Issue(IssueType type, String message, String fragment) {
}

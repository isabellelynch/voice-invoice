package dev.isabellelynch.invoice.parser;

/**
 * The minimum the parser needs to know about a customer. Keeping this separate from the
 * JPA entity means the parser has no Spring or database code and is easy to unit test.
 */
public record CustomerRef(long id, String name) {
}

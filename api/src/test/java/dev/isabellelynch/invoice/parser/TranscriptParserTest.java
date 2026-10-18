package dev.isabellelynch.invoice.parser;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TranscriptParserTest {

    private final TranscriptParser parser = new TranscriptParser();
    private final List<CustomerRef> customers = List.of(
            new CustomerRef(1, "John Murphy"),
            new CustomerRef(2, "Joan Murphy"),
            new CustomerRef(3, "Mary O'Brien"));

    @Test
    void demoSentenceGivesTwoLines() {
        ParseResult r = parser.parse(
                "Customer John Murphy, labour one hundred and twenty euro, materials eighty five euro fifty",
                customers);

        assertThat(r.customerId()).isEqualTo(1L);
        assertThat(r.lines()).hasSize(2);
        assertThat(r.lines().get(0).description()).isEqualToIgnoringCase("labour");
        assertThat(r.lines().get(0).unitPrice()).isEqualByComparingTo("120.00");
        assertThat(r.lines().get(1).description()).isEqualToIgnoringCase("materials");
        assertThat(r.lines().get(1).unitPrice()).isEqualByComparingTo("85.50");
    }

    @Test
    void digitsFromChromeWorkToo() {
        // Chrome's speech recognition often returns digits rather than words.
        ParseResult r = parser.parse("customer John Murphy, labour 120 euro", customers);

        assertThat(r.lines()).hasSize(1);
        assertThat(r.lines().get(0).unitPrice()).isEqualByComparingTo("120");
    }

    @Test
    void taxIsAlwaysLeftToTheUser() {
        ParseResult r = parser.parse("customer John Murphy, labour 120 euro", customers);

        assertThat(r.issues()).extracting(Issue::type).contains(IssueType.TAX_NOT_CHOSEN);
    }

    @Test
    void amountWithoutCurrencyIsFlagged() {
        ParseResult r = parser.parse("customer John Murphy, labour 120", customers);

        assertThat(r.issues()).extracting(Issue::type).contains(IssueType.AMOUNT_WITHOUT_CURRENCY);
    }

    @Test
    void similarNamesAreFlaggedNotGuessed() {
        ParseResult r = parser.parse("customer Jon Murphy, labour 120 euro", customers);

        assertThat(r.customerId()).isNull();
        assertThat(r.issues()).extracting(Issue::type).contains(IssueType.CUSTOMER_AMBIGUOUS);
    }
}

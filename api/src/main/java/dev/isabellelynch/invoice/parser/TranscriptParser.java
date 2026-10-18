package dev.isabellelynch.invoice.parser;

import java.util.List;

/**
 * Turns a transcript such as
 *   "customer John Murphy, labour one hundred and twenty euro, materials eighty five euro fifty"
 * into proposed lines and issues. It never guesses: anything unclear becomes an Issue.
 *
 * Plain Java on purpose: no Spring, no database, so it is fast to test and easy to explain.
 */
public final class TranscriptParser {

    /**
     * TODO (Isabelle): build this up in small steps, running TranscriptParserTest as you go.
     *
     *  1. Normalise: lower-case, turn "€" into " euro ", strip punctuation except a '.' between digits.
     *  2. Tokenise in one pass into typed tokens: NUMBER, NUMBER_WORD, CURRENCY (euro/euros/eur),
     *     CENTS (cent/cents), KEYWORD (customer, labour, materials, at, each, x, times), WORD.
     *  3. Fold each run of number words into one NUMBER using NumberWords.parseWhole;
     *     a run that doesn't parse becomes an AMBIGUOUS_NUMBER issue.
     *  4. Walk the tokens with a small state machine (DESCRIPTION -> AMOUNT -> optional CENTS):
     *     words build a description until "number + currency" closes the line;
     *     "euro fifty" right after an amount adds 0.50;
     *     a number with no currency word gives AMOUNT_WITHOUT_CURRENCY;
     *     words after "customer" up to the next keyword are the customer name.
     *  5. Match the customer with CustomerMatcher.
     *  6. Always add a TAX_NOT_CHOSEN issue, because the user picks the tax rate in the editor.
     *
     * Start with ONLY the demo sentence format working. Extra formats are a bonus, not a must.
     */
    public ParseResult parse(String transcript, List<CustomerRef> customers) {
        throw new UnsupportedOperationException("TODO: implement TranscriptParser.parse");
    }
}

package dev.isabellelynch.invoice.parser;

import java.util.List;
import java.util.Optional;

/**
 * Turns spoken number words into a whole number, e.g. "one hundred and twenty" -> 120.
 */
public final class NumberWords {

    private NumberWords() {
    }

    /**
     * TODO (Isabelle): implement this, then run NumberWordsTest until it is green.
     *
     * Idea: walk the words once, left to right, keeping two accumulators.
     *  - "current" builds the part below a thousand ("one hundred and twenty").
     *  - "total" collects finished thousands.
     *  - A small word (zero..nineteen, twenty, thirty .. ninety) adds to current.
     *    Only allow two small words in a row when it is tens then units ("twenty five").
     *    Anything else ("one twenty", "five five") is ambiguous: return Optional.empty().
     *  - "hundred" multiplies current by 100 (treat an empty current as 1, so "a hundred" works).
     *  - "thousand" moves current x 1000 into total and resets current.
     *  - "and" is allowed between parts; "a" is allowed only before "hundred" or "thousand".
     *  - Any other word means this is not a clean number: return Optional.empty().
     *
     * Aim for O(k) time for k words and O(1) extra space. Be ready to explain why.
     *
     * @param words lower-case words, e.g. ["one", "hundred", "and", "twenty"]
     * @return the number, or empty if the words are not a clean number
     */
    public static Optional<Long> parseWhole(List<String> words) {
        throw new UnsupportedOperationException("TODO: implement NumberWords.parseWhole");
    }
}

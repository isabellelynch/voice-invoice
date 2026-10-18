package dev.isabellelynch.invoice.parser;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class NumberWordsTest {

    private static List<String> words(String s) {
        return List.of(s.split(" "));
    }

    @ParameterizedTest
    @CsvSource({
            "'eighty five', 85",
            "'one hundred and twenty', 120",
            "'a hundred', 100",
            "'twenty five', 25",
            "'nineteen', 19",
            "'ninety nine', 99",
            "'a thousand', 1000",
            "'two thousand three hundred', 2300"
    })
    void parsesCleanNumbers(String spoken, long expected) {
        assertThat(NumberWords.parseWhole(words(spoken))).contains(expected);
    }

    @ParameterizedTest
    @ValueSource(strings = {"one twenty", "five five", "hundred banana", "and"})
    void refusesToGuess(String spoken) {
        assertThat(NumberWords.parseWhole(words(spoken))).isEmpty();
    }
}

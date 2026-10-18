package dev.isabellelynch.invoice.parser;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerMatcherTest {

    @ParameterizedTest
    @CsvSource({
            "kitten, sitting, 3",
            "john murphy, john murphy, 0",
            "jon murphy, john murphy, 1",
            "john murphey, john murphy, 1",
            "'', abc, 3"
    })
    void editDistance(String a, String b, int expected) {
        assertThat(CustomerMatcher.editDistance(a, b)).isEqualTo(expected);
    }

    // TODO (Isabelle): once you've chosen how match() returns its result, add tests for:
    //  - "john murphy" -> John Murphy (exact)
    //  - "john murphey" -> John Murphy (within distance 2, only one candidate)
    //  - "jon murphy"  -> CUSTOMER_AMBIGUOUS (John and Joan are both within 2)
    //  - "pat smith"   -> CUSTOMER_NOT_FOUND
}

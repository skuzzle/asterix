package de.skuzzle.roman;

import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.hamcrest.core.Is;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ToStringTest {

    @Parameters(name = "ToString {1} using {2}")
    public static Iterable<Object[]> parameterFactory() {
        return Arrays.asList(new Object[][] {
                { "MMMCMXCIX", 3999, RomanNumeralType.SINGLE_SUBTRACT },
                { "MCMLXXXIV", 1984, RomanNumeralType.SINGLE_SUBTRACT },
                { "CML", 950, RomanNumeralType.SINGLE_SUBTRACT },
                { "XXX", 30, RomanNumeralType.SINGLE_SUBTRACT },
                { "IX", 9, RomanNumeralType.SINGLE_SUBTRACT },
                { "IV", 4, RomanNumeralType.SINGLE_SUBTRACT },
                { "XXXIX", 39, RomanNumeralType.SINGLE_SUBTRACT },
                { "XIX", 19, RomanNumeralType.SINGLE_SUBTRACT },
                { "XLII", 42, RomanNumeralType.SINGLE_SUBTRACT },
                { "CXCIX", 199, RomanNumeralType.SINGLE_SUBTRACT },
                { "DIII", 503, RomanNumeralType.SINGLE_SUBTRACT },
                { "MMXII", 2012, RomanNumeralType.SINGLE_SUBTRACT },
                { "CMXCIX", 999, RomanNumeralType.SINGLE_SUBTRACT },

                { "MMMCMXCIX", 3999, RomanNumeralType.DOUBLE_SUBTRACT },
                { "MCMXXCIIV", 1983, RomanNumeralType.DOUBLE_SUBTRACT },
                { "CML", 950, RomanNumeralType.DOUBLE_SUBTRACT },
                { "XXL", 30, RomanNumeralType.DOUBLE_SUBTRACT },
                { "IX", 9, RomanNumeralType.DOUBLE_SUBTRACT },
                { "IV", 4, RomanNumeralType.DOUBLE_SUBTRACT },
                { "IIV", 3, RomanNumeralType.DOUBLE_SUBTRACT },
                { "XXC", 80, RomanNumeralType.DOUBLE_SUBTRACT },
                { "IIX", 8, RomanNumeralType.DOUBLE_SUBTRACT },

                { "MDCCCCLXXXIIII", 1984, RomanNumeralType.ADDITIVE },
        });
    }

    private final String expected;
    private final int input;
    private final RomanNumeralType type;

    public ToStringTest(String expected, int input, RomanNumeralType type) {
        this.input = input;
        this.expected = expected;
        this.type = type;
    }

    @Test
    public void testToString() throws Exception {
        assertThat(RomanNumberFormat.getInstance(this.type).format(this.input),
                Is.is(this.expected));
    }
}

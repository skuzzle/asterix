package de.skuzzle.roman;

import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.hamcrest.core.Is;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class SuccessfulParsingTest {

    @Parameters(name = "Parsing {0} with {2}")
    public static Iterable<Object[]> parameterFactory() {
        return Arrays.asList(new Object[][] {
                { "MMMCMXCIX", 3999, RomanNumeralType.SINGLE_SUBTRACT },
                { "MCMLXXXIV", 1984, RomanNumeralType.SINGLE_SUBTRACT },
                { "CML", 950, RomanNumeralType.SINGLE_SUBTRACT },
                { "XXX", 30, RomanNumeralType.SINGLE_SUBTRACT },
                { "IX", 9, RomanNumeralType.SINGLE_SUBTRACT },
                { "IV", 4, RomanNumeralType.SINGLE_SUBTRACT },
                { "XCV", 95, RomanNumeralType.SINGLE_SUBTRACT },
                { "XCIX", 99, RomanNumeralType.SINGLE_SUBTRACT },

                { "MCMLXXLIIV", 1983, RomanNumeralType.DOUBLE_SUBTRACT },
                { "CML", 950, RomanNumeralType.DOUBLE_SUBTRACT },
                { "XXL", 30, RomanNumeralType.DOUBLE_SUBTRACT },
                { "IX", 9, RomanNumeralType.DOUBLE_SUBTRACT },
                { "IV", 4, RomanNumeralType.DOUBLE_SUBTRACT },
                { "IIV", 3, RomanNumeralType.DOUBLE_SUBTRACT },
                { "XXC", 80, RomanNumeralType.DOUBLE_SUBTRACT },
                { "IIX", 8, RomanNumeralType.DOUBLE_SUBTRACT },

                { "MDCCCCLXXXIIII", 1984, RomanNumeralType.ADDITIVE },

                { "MDCCCCLXXXIIII", 1984, RomanNumeralType.LENIENT },
                { "MCMLXXLIIV", 1983, RomanNumeralType.LENIENT },
                { "CML", 950, RomanNumeralType.LENIENT },
                { "XXL", 30, RomanNumeralType.LENIENT },
                { "CML", 950, RomanNumeralType.LENIENT },
                { "XXX", 30, RomanNumeralType.LENIENT },
                { "IX", 9, RomanNumeralType.LENIENT },
                { "IV", 4, RomanNumeralType.LENIENT },
                { "IX", 9, RomanNumeralType.LENIENT },
                { "IV", 4, RomanNumeralType.LENIENT },
                { "IIV", 3, RomanNumeralType.LENIENT },
                { "XXC", 80, RomanNumeralType.LENIENT },
                { "IIX", 8, RomanNumeralType.LENIENT },
                { "XCVIII", 98, RomanNumeralType.LENIENT },
                { "IIC", 98, RomanNumeralType.LENIENT },
                { "IL", 49, RomanNumeralType.LENIENT },
                { "CMXCIX", 999, RomanNumeralType.LENIENT },
                { "IM", 999, RomanNumeralType.LENIENT },

        });
    }

    private final String input;
    private final int expected;
    private final RomanNumeralType type;

    public SuccessfulParsingTest(String input, int expected,
            RomanNumeralType type) {
        this.input = input;
        this.expected = expected;
        this.type = type;
    }

    @Test
    public void testParse() throws Exception {
        assertThat(RomanNumberFormat.getInstance(this.type).parse(this.input),
                Is.is(this.expected));
    }
}

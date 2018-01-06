package de.skuzzle.roman;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ExceptionParsingTest {

    private static final Object[][] PARAMS = new Object[][] {
            { "DM", 0, RomanNumeralType.LENIENT },
            { "LC", 0, RomanNumeralType.LENIENT },
            { "IVIV", 2, RomanNumeralType.LENIENT },
            { "IA", 1, RomanNumeralType.LENIENT },

            { "DM", 0, RomanNumeralType.SINGLE_SUBTRACT },
            { "XDMXX", 0, RomanNumeralType.SINGLE_SUBTRACT },
            { "XXXX", 3, RomanNumeralType.SINGLE_SUBTRACT },
            { "LXXXX", 4, RomanNumeralType.SINGLE_SUBTRACT },
            { "LXXXVVVV", 7, RomanNumeralType.SINGLE_SUBTRACT },
            { "IXIXIXIX", 2, RomanNumeralType.SINGLE_SUBTRACT },
            { "MMMM", 3, RomanNumeralType.SINGLE_SUBTRACT },
            { "IA", 1, RomanNumeralType.SINGLE_SUBTRACT },

            { "MMMM", 3, RomanNumeralType.DOUBLE_SUBTRACT },
            { "DM", 0, RomanNumeralType.DOUBLE_SUBTRACT },
            { "XDMXX", 0, RomanNumeralType.DOUBLE_SUBTRACT },
            { "XXX", 2, RomanNumeralType.DOUBLE_SUBTRACT },
            { "LXXX", 3, RomanNumeralType.DOUBLE_SUBTRACT },
            { "LXXVVV", 5, RomanNumeralType.DOUBLE_SUBTRACT },
            { "IXIXIX", 2, RomanNumeralType.DOUBLE_SUBTRACT },
            { "IA", 1, RomanNumeralType.DOUBLE_SUBTRACT },

            { "IA", 1, RomanNumeralType.ADDITIVE },

    };

    @Parameters(name = "Parsing {0} using {2}")
    public static Iterable<Object[]> parameterFactory() {
        return Arrays.asList(PARAMS);
    }

    private final String input;
    private final int expectedPosition;
    private final RomanNumeralType type;

    public ExceptionParsingTest(String input, int expectedPosition,
            RomanNumeralType type) {
        this.input = input;
        this.expectedPosition = expectedPosition;
        this.type = type;
    }

    @Test
    public void testParseException() throws Exception {
        try {
            RomanNumberFormat.getInstance(this.type).parse(this.input);
            Assert.fail("Expected ParseException being thrown");
        } catch (final ParseException e) {
            assertEquals("Expected error at different position",
                    this.expectedPosition, e.getErrorOffset());
        }
    }
}

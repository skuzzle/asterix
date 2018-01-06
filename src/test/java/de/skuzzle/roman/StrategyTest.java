package de.skuzzle.roman;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.OptionalInt;

import org.hamcrest.core.Is;
import org.junit.Assume;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class StrategyTest {

    @Parameters(name = "Max bounds for {0}")
    public static Iterable<Object[]> parameterFactory() {
        return Arrays.asList(new Object[][] {
                { RomanNumeralType.LENIENT },
                { RomanNumeralType.ADDITIVE },
                { RomanNumeralType.SINGLE_SUBTRACT },
                { RomanNumeralType.DOUBLE_SUBTRACT },
        });
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private final RomanNumeralType type;

    public StrategyTest(RomanNumeralType type) {
        this.type = type;
    }

    @Test
    public void testGetMaxValue() throws Exception {
        assertThat(this.type.getMaxValue(), Is.is(this.type.getStrategy().getMaxValue()));
    }

    @Test
    public void testParseNull() throws Exception {
        this.exception.expect(IllegalArgumentException.class);
        this.exception.expectMessage("Can not parse null value as roman numeral");
        this.type.parse(null);
    }

    @Test
    public void testCountToMaxNumberFormat() throws Exception {
        for (int i = 1; i <= this.type.getStrategy().getMaxValue(); ++i) {
            final NumberFormat rnf = this.type.getNumberFormat();

            final String asString = rnf.format(i);
            final int parsed = rnf.parse(asString).intValue();
            assertThat(parsed, Is.is(i));
        }
    }

    @Test
    public void testCountToMaxEnum() throws Exception {
        for (int i = 1; i <= this.type.getStrategy().getMaxValue(); ++i) {
            final String asString = this.type.format(i);
            final int parsed = this.type.parse(asString);
            assertThat(parsed, Is.is(i));
            assertThat(this.type.tryParse(asString), Is.is(OptionalInt.of(i)));
        }
    }

    @Test
    public void testTryParse() throws Exception {
        assertThat(this.type.tryParse("-.,-.."), Is.is(OptionalInt.empty()));
    }

    @Test
    public void testNotDisplayable() throws Exception {
        final int max = this.type.getStrategy().getMaxValue();
        Assume.assumeTrue("Skipped because strategy can display every positive int",
                max < Integer.MAX_VALUE);
        final int tooMuch = max + 1;
        this.exception.expectMessage(String
                .format("'%d' can not be displayed as roman number using '%s'",
                        tooMuch, this.type));
        this.exception.expect(IllegalArgumentException.class);
        RomanNumberFormat.getInstance(this.type).format(tooMuch);
    }

    @Test
    public void testStrategyPrefixFree() throws Exception {
        final RomanLiteralStrategy strategy = this.type.getStrategy();
        Assume.assumeTrue(strategy instanceof AbstractRomanLiteralStrategy);
        final AbstractRomanLiteralStrategy subject = (AbstractRomanLiteralStrategy) strategy;

        for (int i = 1; i < subject.getLiterals().length; ++i) {
            final Literal prev = subject.getLiterals()[i - 1];
            final Literal current = subject.getLiterals()[i];

            assertFalse(String.format("'%s' must not be a prefix of '%s'",
                    prev, current),
                    current.toString().startsWith(prev.toString()));
        }
    }

    @Test
    public void testStrategyOrdering() throws Exception {
        final RomanLiteralStrategy strategy = this.type.getStrategy();
        Assume.assumeTrue(strategy instanceof AbstractRomanLiteralStrategy);
        final AbstractRomanLiteralStrategy subject = (AbstractRomanLiteralStrategy) strategy;

        for (int i = 1; i < subject.getLiterals().length; ++i) {
            final Literal prev = subject.getLiterals()[i - 1];
            final Literal current = subject.getLiterals()[i];

            assertTrue(String.format("'%s' (%d) must be greater than '%s' (%d)",
                    prev, prev.intValue(), current, current.intValue()),
                    prev.intValue() > current.intValue());
        }
    }
}

package de.skuzzle.roman;

class DoubleSubtractionStrategy extends AbstractRomanLiteralStrategy {

    private static final Literal[] LITERALS = new Literal[] {
            Literal.define("M", 1000),
            Literal.define("CM", 900),
            Literal.define("CCM", 800),
            Literal.define("D", 500),
            Literal.define("CD", 400),
            Literal.define("CCD", 300),
            Literal.define("C", 100),
            Literal.define("XC", 90),
            Literal.define("XXC", 80),
            Literal.define("L", 50),
            Literal.define("XL", 40),
            Literal.define("XXL", 30),
            Literal.define("X", 10),
            Literal.define("IX", 9),
            Literal.define("IIX", 8),
            Literal.define("V", 5),
            Literal.define("IV", 4),
            Literal.define("IIV", 3),
            Literal.define("I", 1),
    };

    public static final RomanLiteralStrategy INSTANCE = new DoubleSubtractionStrategy();

    protected DoubleSubtractionStrategy() {
        // hidden
    }

    @Override
    public int getMaxValue() {
        return 3999;
    }

    @Override
    public int getMaxSameConsecutive(Literal literal) {
        if (literal == LITERALS[0]) {
            // M may occur 3 times
            return 3;
        } else if (literal.length() > 1) {
            // literals made up of multiple chars may never occur twice
            return 1;
        } else {
            // every other literal may occur two times consecutively
            return 2;
        }
    }

    @Override
    protected Literal[] getLiterals() {
        return LITERALS;
    }

}

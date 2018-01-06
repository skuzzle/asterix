package de.skuzzle.roman;

class SingleSubtractionStrategy extends AbstractRomanLiteralStrategy {

    private static final Literal[] LITERALS = new Literal[] {
            Literal.define("M", 1000),
            Literal.define("CM", 900),
            Literal.define("D", 500),
            Literal.define("CD", 400),
            Literal.define("C", 100),
            Literal.define("XC", 90),
            Literal.define("L", 50),
            Literal.define("XL", 40),
            Literal.define("X", 10),
            Literal.define("IX", 9),
            Literal.define("V", 5),
            Literal.define("IV", 4),
            Literal.define("I", 1),
    };

    public static final RomanLiteralStrategy INSTANCE = new SingleSubtractionStrategy();

    protected SingleSubtractionStrategy() {
        // hidden
    }

    @Override
    public int getMaxValue() {
        // MMMCMXCIX
        return 3999;
    }

    @Override
    protected Literal[] getLiterals() {
        return LITERALS;
    }

    @Override
    public int getMaxSameConsecutive(Literal literal) {
        return literal.length() == 1
                ? 3
                : 1;
    }

}

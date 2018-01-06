package de.skuzzle.roman;

class AdditiveStrategy extends AbstractRomanLiteralStrategy {

    private static final Literal[] LITERALS = new Literal[] {
            Literal.define("M", 1000),
            Literal.define("D", 500),
            Literal.define("C", 100),
            Literal.define("L", 50),
            Literal.define("X", 10),
            Literal.define("V", 5),
            Literal.define("I", 1),
    };

    public static final RomanLiteralStrategy INSTANCE = new AdditiveStrategy();

    protected AdditiveStrategy() {
        // hidden
    }

    @Override
    public int getMaxValue() {
        return Settings.ARTIFICIAL_MAX_VALUE;
    }

    @Override
    public int getMaxSameConsecutive(Literal literal) {
        return Integer.MAX_VALUE;
    }

    @Override
    protected Literal[] getLiterals() {
        return LITERALS;
    }

}

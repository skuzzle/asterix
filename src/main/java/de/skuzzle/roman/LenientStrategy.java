package de.skuzzle.roman;

class LenientStrategy extends AbstractRomanLiteralStrategy {

    private static final Literal[] LITERALS = new Literal[] {
            Literal.define("M", 1000),
            Literal.define("IM", 999),
            Literal.define("IIM", 998),
            Literal.define("XM", 990),
            Literal.define("XXM", 980),
            Literal.define("CM", 900),
            Literal.define("CCM", 800),
            Literal.define("D", 500),
            Literal.define("ID", 499),
            Literal.define("IID", 498),
            Literal.define("XD", 490),
            Literal.define("XXD", 480),
            Literal.define("CD", 400),
            Literal.define("CCD", 300),
            Literal.define("C", 100),
            Literal.define("IC", 99),
            Literal.define("IIC", 98),
            Literal.define("XC", 90),
            Literal.define("XXC", 80),
            Literal.define("L", 50),
            Literal.define("IL", 49),
            Literal.define("IIL", 48),
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

    public static final RomanLiteralStrategy INSTANCE = new LenientStrategy();

    @Override
    public int getMaxValue() {
        return Settings.ARTIFICIAL_MAX_VALUE;
    }

    @Override
    public int getMaxSameConsecutive(Literal literal) {
        return literal.length() == 1
                ? Integer.MAX_VALUE
                : 1;
    }

    @Override
    protected Literal[] getLiterals() {
        return LITERALS;
    }

}

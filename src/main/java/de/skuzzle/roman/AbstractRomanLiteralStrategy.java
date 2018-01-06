package de.skuzzle.roman;

import java.text.ParsePosition;

abstract class AbstractRomanLiteralStrategy implements RomanLiteralStrategy {

    protected abstract Literal[] getLiterals();

    @Override
    public boolean isZeroSupported() {
        return Settings.ALLOW_ZERO;
    }

    @Override
    public boolean isIllegalPrefix(Literal prefix, Literal current) {
        return prefix.intValue() < current.intValue();
    }

    @Override
    public Literal findNext(char[] stream, ParsePosition position) {
        for (int i = 0; i < getLiterals().length; ++i) {
            final Literal test = getLiterals()[i];
            if (test.matches(stream, position.getIndex())) {
                position.setIndex(position.getIndex() + test.length());
                return test;
            }
        }
        position.setErrorIndex(position.getIndex());
        return null;
    }

    @Override
    public Literal findNext(long val) {
        for (int i = 0; i < getLiterals().length; ++i) {
            final Literal test = getLiterals()[i];
            if (val / test.intValue() > 0) {
                return test;
            }
        }
        throw new IllegalStateException(
                "there is no literal to represent the value: " + val);
    }

}
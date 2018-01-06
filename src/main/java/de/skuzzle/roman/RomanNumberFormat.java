package de.skuzzle.roman;

import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;

class RomanNumberFormat extends NumberFormat {

    private static final long serialVersionUID = -9126248676409239905L;

    private final RomanNumeralType type;

    private RomanNumberFormat(RomanNumeralType type) {
        this.type = type;
    }

    public static NumberFormat getInstance(RomanNumeralType type) {
        return new RomanNumberFormat(type);
    }

    @Override
    public StringBuffer format(double number, StringBuffer toAppendTo,
            FieldPosition pos) {

        return format((long) number, toAppendTo, pos);
    }

    @Override
    public StringBuffer format(long number, StringBuffer toAppendTo, FieldPosition pos) {
        format(number, toAppendTo, this.type);
        return toAppendTo;
    }

    private void format(long value, StringBuffer b, RomanNumeralType type) {
        final RomanLiteralStrategy strategy = type.getStrategy();
        if (value > strategy.getMaxValue()) {
            throw new IllegalArgumentException(
                    String.format("'%d' can not be displayed as roman number using '%s'. "
                            + "The maximum allowed value is '%d'",
                            value, type, type.getMaxValue()));
        }

        long val = value;
        while (val != 0) {
            final Literal lit = strategy.findNext(val);

            lit.appendTo(b);
            val = val - lit.intValue();
        }
    }

    @Override
    public Number parse(String source, ParsePosition parsePosition) {
        return parse(source, parsePosition, this.type);
    }

    private int parse(String s, ParsePosition position, RomanNumeralType type) {
        if (s == null) {
            throw new IllegalArgumentException(
                    "Can not parse null value as roman numeral");
        }
        final RomanLiteralStrategy strategy = type.getStrategy();
        final int initialIdx = position.getIndex();
        final char[] stream = s.toCharArray();
        int sum = 0;
        int consecutives = 0;
        Literal last = null;
        int lastPos = -1;

        while (position.getIndex() < stream.length) {
            final Literal lit = strategy.findNext(stream, position);
            if (position.getErrorIndex() != -1) {
                position.setIndex(initialIdx);
                return -1;
            }

            if (last != null) {
                if (strategy.isIllegalPrefix(last, lit)) {
                    position.setErrorIndex(lastPos - last.length());
                    position.setIndex(initialIdx);
                    return -1;
                } else if (last.intValue() == lit.intValue()) {
                    ++consecutives;
                    if (consecutives == strategy.getMaxSameConsecutive(lit)) {
                        position.setErrorIndex(position.getIndex() - lit.length());
                        position.setIndex(initialIdx);
                        return -1;
                    }
                } else {
                    consecutives = 0;
                }
            }
            last = lit;
            lastPos = position.getIndex();
            sum += lit.intValue();
        }

        return sum;
    }
}

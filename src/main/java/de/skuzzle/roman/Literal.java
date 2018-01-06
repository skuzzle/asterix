package de.skuzzle.roman;

final class Literal {

    private final int value;
    private final char[] chars;

    private Literal(int value, char[] chars) {
        this.value = value;
        this.chars = chars;
    }

    public static Literal define(String display, int value) {
        return new Literal(value, display.toCharArray());
    }

    public boolean matches(char[] stream, int start) {
        final int lastCharInStreamIdx = start + this.chars.length - 1;
        if (lastCharInStreamIdx >= stream.length) {
            return false;
        }
        for (int i = 0; i < this.chars.length; ++i) {
            final int j = start + i;
            if (this.chars[i] != stream[j]) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return this.value;
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this || obj instanceof Literal
                && this.value == ((Literal) obj).value;
    }

    public void appendTo(StringBuffer b) {
        b.append(this.chars);
    }

    @Override
    public String toString() {
        return new String(this.chars);
    }

    public int intValue() {
        return this.value;
    }

    public int length() {
        return this.chars.length;
    }
}

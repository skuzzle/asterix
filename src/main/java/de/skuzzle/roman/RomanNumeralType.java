package de.skuzzle.roman;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Optional;
import java.util.OptionalInt;

/**
 * Represents the way in which a roman numeral is represented. This has influence on
 * parsing Strings as numerals as well as formatting numbers back to a String of numerals.
 *
 * <p>
 * When parsing a roman numeral String, every type's rules are applied strictly. You may
 * however use {@link RomanNumeralType#LENIENT} for parsing arbitrary roman numerals.
 * </p>
 *
 * @author Simon Taddiken
 */
public enum RomanNumeralType {

    /**
     * This type is the one which is mostly used in modern times. It allows to place some
     * literals in front of bigger ones in order to subtract their value. It only allows
     * to place a single smaller literal in front of another.
     *
     * <p>
     * Single literals that are multiples of 5 ({@code V, L, D}) may not be placed in
     * front of bigger literals. All other literals may be placed in front of the two next
     * bigger literals. For example, {@code I} may be placed in front of {@code V} and
     * {@code X} but not in front of {@code L}.
     * </p>
     *
     * <p>
     * This type is naturally bounded to the maximum integer value of 3999 which is
     * represented as {@code MMMCMXCIX}. This is because the biggest single literal is
     * {@code M} and literals are not allowed to occur consecutively more than three
     * times.
     * <p>
     */
    SINGLE_SUBTRACT(SingleSubtractionStrategy.INSTANCE),

    /**
     * This types extends the rules of the {@link #SINGLE_SUBSTRACT} rule to allow placing
     * the same literal up to two times in front of a bigger literal. To eliminate
     * duplicate representations of the same number, all literals except {@code M} may
     * only occur consecutively up to two times.
     * <p>
     * Thus, instead of representing the value {@code 30} as {@code XXX} this type will
     * only allow {@code XXL}.
     * </p>
     */
    DOUBLE_SUBTRACT(DoubleSubtractionStrategy.INSTANCE),

    /**
     * This type does not allow to place smaller literals in front of other to subtract.
     * Instead it allows an arbitrary amount of consecutive literals which are simply
     * summed up. The only constraint that this type places on the numerals is that their
     * literals must be strictly ordered, starting with the highest value literal.
     */
    ADDITIVE(AdditiveStrategy.INSTANCE),

    /**
     * This type combines the rules of all other types.
     * <ul>
     * <li>It allows to place an arbitrary amount of the same literal consecutively.</li>
     * <li>It allows to subtract up to two literals by placing them in front of bigger
     * ones.</li>
     * <li>It allows to subtract smaller literals from ones that are way bigger. For
     * example, it allows the literal {@code IM} to represent the value {@code 999}.</li>
     * </ul>
     * <p>
     * It is still not allowed to place the literals that represent multiples of 5 in
     * front of other ones. Still this type allows multiple representations of the same
     * value. For example both {@code XXX} and {@code XXL} are valid to represent the
     * value {@code 30}. {@code IM} and {@code CMXCIX} both represent the value
     * {@code 999}.
     * </p>
     *
     * <p>
     * This type is very convenient for parsing numerals which come in an arbitrary form.
     * Likewise it is not very useful for formatting values because the above rules will
     * be applied in a strict manner:
     * <ul>
     * <li>It will always try to display numerals by subtracting two literals instead of
     * summing three (like {@link #DOUBLE_SUBTRACT}).</li>
     * <li>It will always try to subtract lower values from very high ones. (for example,
     * {@code 999} is displayed as {@code IM} and {@code 49} as {@code IL}).</li>
     * </ul>
     * </p>
     */
    LENIENT(LenientStrategy.INSTANCE);

    private final RomanLiteralStrategy strategy;

    private RomanNumeralType(RomanLiteralStrategy strategy) {
        this.strategy = strategy;
    }

    RomanLiteralStrategy getStrategy() {
        return this.strategy;
    }

    /**
     * Returns the biggest integer value which can be represented as roman numeral String
     * using this type. Some types are bounded naturally because of their limited set of
     * symbols and their allowed number of consecutive same literals. Others may be
     * bounded artificially because it makes no sense displaying a roman numeral with
     * thousands of characters.
     *
     * @return The maximum displayable integer value using this type.
     */
    public int getMaxValue() {
        return this.strategy.getMaxValue();
    }

    /**
     * Creates a {@link NumberFormat} instance which is able to parse and format roman
     * numerals using this numeral type. The returned {@link NumberFormat} instance will
     * not be able to format values greater than the one returned by
     * {@link #getMaxValue()}. An exception will be thrown by {@code format(...)} if such
     * a value is encountered.
     *
     * @return A new number format instance.
     */
    public NumberFormat getNumberFormat() {
        return RomanNumberFormat.getInstance(this);
    }

    /**
     * Parses the given String as a roman numeral using the current type. If the String
     * does not comply with the rules of this type, a {@link IllegalArgumentException} is
     * thrown.
     *
     * @param s The String to parse.
     * @return The resulting int value.
     * @throws ParseException If the String
     * @throws IllegalArgumentException If the String is null or does not form a valid
     *             roman numeral of this numer type.
     */
    public int parse(String s) {
        return tryParse(s).orElseThrow(() -> new IllegalArgumentException(String.format(
                "'%s' can not be parsed as a roman numeral according to the type '%s'", s,
                this)));
    }

    /**
     * Tries to parse the given String as a roman numeral by applying the rules of this
     * type. If the String does not comply with these rules an empty Optional is returned.
     * Otherwise the result will contain the int value.
     *
     * @param s The String to parse.
     * @return The parsed value if successful.
     * @throws IllegalArgumentException If the String is null.
     */
    public OptionalInt tryParse(String s) {
        final NumberFormat nf = RomanNumberFormat.getInstance(this);
        final ParsePosition ps = new ParsePosition(0);
        final int result = nf.parse(s, ps).intValue();
        if (ps.getIndex() == 0) {
            return OptionalInt.empty();
        }
        return OptionalInt.of(result);
    }

    /**
     * Formats the given int value into a roman numeral representation using the rules of this type.
     *  
     * @param value The value to format.
     * @return The formatted number.
     */
    public String format(int value) {
        return RomanNumberFormat.getInstance(this).format(value);
    }

    /**
     * Parses the given String as a roman numeral by applying the rules of this type, then
     * formats the resulting int value using the given target type.
     *
     * @param target The target (output) type.
     * @param romanNumeral The String to parse.
     * @return The roman numeral, formatted by the rules of the given target type.
     * @throws IllegalArgumentException If either argument is null or the given
     *             romanNumeral can not be parsed according to the current type or the
     *             parsed value can not be displayed using the target type.
     */
    public String convertTo(RomanNumeralType target, String romanNumeral) {
        final int parsed = parse(romanNumeral);
        return target.format(parsed);
    }

    /**
     * Tries to parse the given String as a roman numeral by applying the rules of this
     * type. If the String could not be parsed, an empty Optional is returned. Otherwise
     * the parsed value is formatted using the given target type.
     *
     * @param target The target (output) type.
     * @param romanNumeral The String to parse.
     * @return The roman numeral, formatted by the rules of the given target type.
     * @throws IllegalArgumentException If either argument is null or the parsed value can
     *             not be dispalyed using the target type.
     */
    public Optional<String> tryConvertTo(RomanNumeralType target, String romanNumeral) {
        final OptionalInt parsed = tryParse(romanNumeral);
        return parsed.isPresent()
                ? Optional.of(target.format(parsed.getAsInt()))
                : Optional.empty();
    }
}

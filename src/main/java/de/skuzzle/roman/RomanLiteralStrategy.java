package de.skuzzle.roman;

import java.text.ParsePosition;

/**
 * Internal strategy interface that defines how to handle a certain type of roman
 * numerals.
 *
 * @author Simon Taddiken
 */
interface RomanLiteralStrategy {

    /**
     * Defines the maximum int value that can be displayed as roman number using this
     * strategy.
     *
     * @return The maxium displayable value.
     */
    int getMaxValue();

    /**
     * Defines the maximum number of same literals that may occur consecutively.
     *
     * @param literal The literal for which to determine the maximum consecutive
     *            occurrences.
     * @return The maximum number of same consecutive literals.
     */
    int getMaxSameConsecutive(Literal literal);

    /**
     * Determines the greatest {@link Literal} which can be started at the given position
     * in the given stream.
     *
     * @param stream the stream.
     * @param position The position at which parsing starts. If successful, position will
     *            be advanced. Otherwise the position's error index will be set and the
     *            normal parse position remains unchanged.
     * @return
     */
    Literal findNext(char[] stream, ParsePosition position);

    /**
     * Determines the greatest {@link Literal} which value is lower than or equal to the
     * given int value.
     *
     * @param val
     * @return
     * @throws IllegalStateException If there is no such Literal.
     */
    Literal findNext(long val);

    /**
     * Tests whether the literal 'prefix' is allowed to occur directly prior to the
     * literal 'current'.
     *
     * @param prefix
     * @param current
     * @return <code>true</code> if prefix is not allowed in front of current.
     */
    boolean isIllegalPrefix(Literal prefix, Literal current);

    /**
     * Whether this strategy is able to format the int value {@code 0} to a String.
     *
     * @return Whether this strategy supports zeroes.
     */
    boolean isZeroSupported();
}

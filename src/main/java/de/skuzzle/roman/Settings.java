package de.skuzzle.roman;

/**
 * Global framework settings. Not yet exposed as public API. use at own risk.
 * 
 * @author Simon Taddiken
 */
final class Settings {

    /**
     * The maximum allowed value to format with a {@link RomanNumeralType} which is not
     * naturally bounded. Defaults to {@code 10000}.
     */
    public static final int ARTIFICIAL_MAX_VALUE = getOrDefault(
            "asterix.ARTIFICIAL_MAX_VALUE", 10000);

    public static final boolean ALLOW_ZERO = false;

    private Settings() {
        // hidden
    }

    private static int getOrDefault(String key, int defaultValue) {
        final String property = System.getProperty(key);
        if (property == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(property.trim());
        } catch (final NumberFormatException e) {
            throw new IllegalArgumentException(String.format(
                    "Illegal value of system property '%s'. It is expected to be "
                            + "positive int value but was '%s'",
                    key, property));
        }
    }
}

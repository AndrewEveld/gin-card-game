
/**
 * An enumeration to represent generic actions the player can perform.
 *
 * <p>
 * Copyright 2017 Brent Yorgey. This work is licensed under a
 * <a rel="license" href= "http://creativecommons.org/licenses/by/4.0/">Creative
 * Commons Attribution 4.0 International License</a>.
 * </p>
 *
 * @author Brent Yorgey
 * @version August 21, 2017
 *
 */
public enum Value {
	ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, KING, QUEEN, QUIT, UNKNOWN;

	/**
	 * Try to parse a string into a Verb in a case-insensitive way.
	 *
	 * @param s  The string to be parsed
	 * @return   The parsed Verb, or UNKNOWN if the verb is not recognized.
	 */
	public static Value parse(String s) {
		try {
			return Value.valueOf(s.toUpperCase().trim());
		} catch (IllegalArgumentException e) {
			return UNKNOWN;
		}
	}
}

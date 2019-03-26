package net.nextinfinity.core.utils;

/**
 * Basic time utility which is used to convert raw seconds into a readable format
 */
public class TimeUtil {

	/**
	 * Gets the amount of full minutes contained in an amount of seconds.
	 *
	 * @param seconds the amount of seconds to convert
	 * @return the amount of full minutes in the time
	 */
	private static int getMinutes(int seconds) {
		return Math.floorDiv(seconds, 60);
	}

	/**
	 * Converts an amount of seconds into a readable string of seconds and minutes.
	 *
	 * @param seconds the amount of seconds to convert
	 * @return the time in a readable format
	 */
	public static String getTimeAsString(int seconds) {
		int minutes = getMinutes(seconds);
		int newSeconds = seconds - minutes*60;
		return minutes + "m" + newSeconds + "s";
	}
}

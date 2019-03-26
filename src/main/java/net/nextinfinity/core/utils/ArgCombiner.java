package net.nextinfinity.core.utils;

public class ArgCombiner {

	/**
	 * Combines the entries in an array of Strings into one String with a space between each entry beginning at
	 * the specified index.
	 *
	 * @param args the args to combine
	 * @param startIndex the index to start combination at
	 * @return the String of combined args
	 */
	public static String combineArgs(String[] args, int startIndex) {
		StringBuilder builder = new StringBuilder(args[startIndex]);
		for (int i = startIndex + 1; i < args.length; i++) {
			builder.append(" ");
			builder.append(args[i]);
		}
		return builder.toString();
	}

	/**
	 * Combines all entries in an array of Strings into one String with a space between each entry.
	 *
	 * @param args the args to combine
	 * @return the String of combined args
	 */
	public static String combineArgs(String[] args) {
		return combineArgs(args, 0);
	}

}

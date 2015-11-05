package com.dig.www.util;

import java.util.Scanner;

public abstract class NameBuilder {

	private static final char[] cons = { 'q', 'w', 'r', 't', 'p', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b', 'n', 'm' };
	private static final char[] vow = { 'e', 'y', 'u', 'i', 'o', 'a' };
	//private static final char[] caps = { 'Q', 'W', 'R', 'T', 'P', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'Z', 'X', 'C', 'V', 'B', 'N', 'M' };

	private static final int SYL_MAX = 3;
	private static StringBuilder name = new StringBuilder();

	public static String getName() {

		int len = Statics.RAND.nextInt(SYL_MAX) + 1;

		wipe(name);

		name.append(getFirstSyllable());

		for (int i = 0; i < len; i++) {
			name.append(getNewSyllable());
		}

		name.append(getEndSyllable());

		return name.toString();
	}

	public static String getName(String cannotBe) {

		String s = getName();

		while (s.equals(cannotBe))
			s = getName();
		
		return s;
	}

	private static String getNewSyllable() {

		return "" + cons[Statics.RAND.nextInt(cons.length)] + vow[Statics.RAND.nextInt(vow.length)];
	}

	private static String getFirstSyllable() {

		return "" + Character.toUpperCase(cons[Statics.RAND.nextInt(cons.length)]) + vow[Statics.RAND.nextInt(vow.length)];
	}

	private static String getEndSyllable() {

		switch (Statics.RAND.nextInt(3)) {

		case 0:
			return cons[Statics.RAND.nextInt(cons.length)] + "e";

		case 1:
			return getNewSyllable();

		default:
			return cons[Statics.RAND.nextInt(cons.length)] + "";
		}
	}

	private static void wipe(StringBuilder syl) {
		if (syl.length() > 0)
			syl.delete(0, syl.length());
	}

	public static void run() {
		String answer = "y";
		final String sep = "------------------------------";
		Scanner input = new Scanner(System.in);

		while (!answer.startsWith("n")) {
			System.out.println(getName() + "\n" + sep);
			System.out.print("Another name? ");

			answer = input.nextLine();
			System.out.println(sep);
		}

		input.close();
	}
}

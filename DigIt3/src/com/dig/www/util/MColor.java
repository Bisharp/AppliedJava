package com.dig.www.util;

import java.awt.Color;

public class MColor extends Color {

	public MColor(int r, int g, int b) {
		super(roundHex(r), roundHex(g), roundHex(b));
	}

	public MColor(int r, int g, int b, int a) {
		super(roundHex(r), roundHex(g), roundHex(b), roundHex(a));
	}

	protected static int roundHex(int toRound) {
		if (toRound < 0)
			return 0;
		else if (toRound > 255)
			return 255;
		else
			return toRound;
	}
}
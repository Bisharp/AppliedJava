package com.dig.www.games.Climb;

public class Ladder extends Object {

	protected boolean topRung;
	
	public Ladder(int x, int y, String loc, Climb owner, boolean tR) {
		super(x, y, loc, owner);
		topRung = tR;
	}
	
	protected boolean isTopRung() {
		return topRung;
	}
}

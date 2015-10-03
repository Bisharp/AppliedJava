package com.dig.www.objects;

import com.dig.www.start.Board;
import com.dig.www.util.Statics;

public class Collectible extends Objects {
	
	private int value;
	private static final String[] POS = {"coin0", "coin1"};

	public Collectible(int x, int y, Board owner, int value) {
		super(x, y, Collectible.createLoc(), false, owner);
		
		this.value = value;
	}
	
	public Collectible(int x, int y, String loc, Board owner, int value) {
		super(x, y, loc, false, owner);
		
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	
	public static String createLoc() {
		return "images/objects/collectibles/" + POS[Statics.RAND.nextInt(POS.length)] + ".png";
	}
}

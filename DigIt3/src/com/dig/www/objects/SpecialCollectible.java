package com.dig.www.objects;

import com.dig.www.start.Board;

public class SpecialCollectible extends Objects {
	
	public final int id;

	public SpecialCollectible(int x, int y, String loc, Board owner, int id) {
		super(x, y, loc, false, owner);
		this.id = id;
		// TODO Auto-generated constructor stub
	}
}
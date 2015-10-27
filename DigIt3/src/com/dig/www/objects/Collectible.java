package com.dig.www.objects;

import com.dig.www.character.Items;
import com.dig.www.start.Board;
import com.dig.www.util.Statics;

public abstract class Collectible extends Objects {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Items type;
	
	public Collectible(int x, int y, String loc, Board owner, Items type) {
		super(x, y, loc, false, owner);
		this.type = type;
	}

	public Items getType() {
		return type;
	}

	public boolean collectible() {
		return true;
	}
}

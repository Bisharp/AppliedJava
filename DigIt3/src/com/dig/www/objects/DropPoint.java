package com.dig.www.objects;

import com.dig.www.character.Items;
import com.dig.www.start.Board;
import com.dig.www.util.Statics;

public class DropPoint extends Objects {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private boolean hasDrop = false;
	private Items type;

	public DropPoint(int x, int y, Board owner) {
		super(x, y, Statics.DUMMY, false, owner);
	}

	public void setSpecs(Items itemType) {
		hasDrop = true;
		type = itemType;
	}

	public boolean hasDrop() {
		return hasDrop;
	}

	public Items type() {
		return type;
	}
}

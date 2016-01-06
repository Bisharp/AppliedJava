package com.dig.www.objects;

import java.awt.Graphics2D;

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
		super(x, y, null, false, owner,null);
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
	
	@Override
	public void draw(Graphics2D g2d) {
		
	}
}

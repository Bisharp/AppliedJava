package com.dig.www.objects;

import com.dig.www.character.Items;
import com.dig.www.start.Board;

public class MoneyObject extends Collectible {

	private int value;
	
	public MoneyObject(int x, int y, String loc, Board owner, int value) {
		super(x, y, loc, owner, Items.NULL);
		
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}

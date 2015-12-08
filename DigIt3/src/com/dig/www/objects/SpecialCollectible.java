package com.dig.www.objects;

import com.dig.www.start.Board;
import com.dig.www.character.Items;

public class SpecialCollectible extends Collectible {
	
	public final int id;

	public SpecialCollectible(int x, int y, String loc, Board owner, int id) {
		super(x, y, loc,false, owner, Items.SPECIAL_COLLECTIBLE);
		this.id = id;
	}
}
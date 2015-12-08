package com.dig.www.objects;

import com.dig.www.character.Items;
import com.dig.www.start.Board;

public class CollectibleObject extends Collectible {

	public CollectibleObject(int x, int y, String loc,boolean wall, Board owner, Items type) {
		super(x, y, loc,wall, owner, type);
	}
}

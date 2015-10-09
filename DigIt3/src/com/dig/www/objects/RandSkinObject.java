package com.dig.www.objects;

import com.dig.www.start.Board;
import com.dig.www.util.Statics;

public class RandSkinObject extends Objects {
	public RandSkinObject(int x, int y, String loc, boolean wall, Board owner) {
		super(x, y, loc + Statics.RAND.nextInt(Statics.getFolderCont(loc)) + ".png", wall, owner);
	}
}

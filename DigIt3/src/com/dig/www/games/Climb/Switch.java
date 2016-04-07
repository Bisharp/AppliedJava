package com.dig.www.games.Climb;

import com.dig.www.util.Statics;

public class Switch extends Object {

	protected boolean pressed = false;
	protected boolean lastOne;

	public Switch(int x, int y, Climb owner, boolean lastOne) {
		super(x, y -= lastOne ? 0 : 50, getLoc(lastOne) + ".png", owner);
		this.lastOne = lastOne;
	}

	protected void press() {
		if (lastOne)
			image = Statics.newImage(getLoc(lastOne) + "P.png");
		pressed = true;
	}

	protected static String getLoc(boolean lastOne) {
		if (lastOne)
			return "images/climb/other/switch";
		else
			return "images/climb/other/continue";
	}
}

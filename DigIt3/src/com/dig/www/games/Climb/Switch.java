package com.dig.www.games.Climb;

import com.dig.www.util.Statics;

public class Switch extends Object {
	
	protected boolean pressed = false;

	public Switch(int x, int y, Climb owner) {
		super(x, y, "images/climb/other/switch.png", owner);
	}
	
	protected void press() {
		image = Statics.newImage("images/climb/other/switchP.png");
		pressed = true;
	}
}

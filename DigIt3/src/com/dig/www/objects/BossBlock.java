package com.dig.www.objects;

import com.dig.www.start.Board;

public class BossBlock extends Objects{

	public BossBlock(int x, int y,  Board owner) {
		super(x, y, "images/portals/bossWall.png", true, owner);
		// TODO Auto-generated constructor stub
	}
public void remove(){
	owner.getObjects().remove(this);
}
}

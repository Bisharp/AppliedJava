package com.dig.www.objects;

import java.awt.Graphics2D;

import com.dig.www.start.Board;

public class BossBlock extends Objects{

	public BossBlock(int x, int y,  Board owner) {
		super(x, y, "images/portals/bossWall.png", true, owner,"BossBlock");
		// TODO Auto-generated constructor stub
	}
	@Override
	public void draw(Graphics2D g2d) {
		g2d.drawImage(image, x, y, owner);
		if (owner.darkenWorld())
			g2d.drawImage(shadow, x, y, owner);
	}
public void remove(){
	owner.getObjects().remove(this);
}
}

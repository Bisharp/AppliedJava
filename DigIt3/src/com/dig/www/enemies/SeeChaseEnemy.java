package com.dig.www.enemies;

import java.awt.Point;

import com.dig.www.start.Board;
import com.dig.www.util.Statics;

public class SeeChaseEnemy extends SeeEnemy {

	private double d;
	private int speed = 5;

	public SeeChaseEnemy(int x, int y, String loc, Board owner, boolean flying, int health) {
		super(x, y, loc, owner, flying, health);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void act() {
		// TODO Auto-generated method stub

		d = Statics.pointTowards(new Point((int) x, (int) y), owner.getCharPoint());
		x += Math.cos((double) Math.toRadians((double) d)) * speed;
		y += Math.sin((double) Math.toRadians((double) d)) * speed;
	}
}

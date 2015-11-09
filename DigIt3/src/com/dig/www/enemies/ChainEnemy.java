package com.dig.www.enemies;

import java.awt.Graphics2D;
import java.util.ArrayList;

import com.dig.www.start.Board;
import com.dig.www.util.Sprite;
import com.dig.www.util.Statics;

public class ChainEnemy extends WalkingEnemy {

	private ArrayList<ChainEnemy> linkList;
	private boolean added = false;
	private ChainEnemy follows;
	private int links;

	// private MyPoint[] pastPoints;

	public ChainEnemy(int x, int y, String loc, Board owner, boolean flying, int health, int links) {
		super(x, y, loc, owner, flying, health);

		this.links = links;
		follows = null;

		// pastPoints = new MyPoint[links];
		// for (int i = 0; i < links; i++)
		// pastPoints[i] = new MyPoint(x, y);
	}

	public ChainEnemy(int x, int y, String loc, Board owner, boolean flying, int health, ChainEnemy follows) {
		super(x, y, loc, owner, flying, health);

		this.follows = follows;
		added = true;
	}

	private int FOLLOW = 100;

	public void animate() {

		if (!added) {
			added = true;
			linkList = new ArrayList<ChainEnemy>();
			for (int i = 0; i < links; i++)
				linkList.add(new ChainEnemy(x, y, loc, owner, flying, health / 2, i != 0 ? linkList.get(i - 1) : this));
			owner.getEnemies().addAll(linkList);
		}

		// super.animate();
		//
		// int i;
		// for (i = 0; i < pastPoints.length; i++) {
		// pastPoints[i].x += owner.getScrollX();
		// pastPoints[i].y += owner.getScrollY();
		// }
		// for (i = pastPoints.length - 1; i >= 0; i--)
		// if (i != 0) {
		// pastPoints[i].x = pastPoints[i - 1].x;
		// pastPoints[i].y = pastPoints[i - 1].y;
		// } else {
		// pastPoints[i].x = x;
		// pastPoints[i].y = y;
		// }
		//
		// for (i = 0; i < linkList.size(); i++) {
		// linkList.get(i).setX(pastPoints[i].x);
		// linkList.get(i).setY(pastPoints[i].y);
		// }

		if (follows != null) {
			
			if (!follows.isAlive()) {
				// Another rare occurrence of a do/while() loop
				do {
					follows = follows.getFollows();
				} while (!follows.isAlive());
			}
			
			basicAnimate();
			int dist = (int) Math.pow(follows.getX() - x, 2) + (int) Math.pow(follows.getY() - y, 2);

			if (Math.sqrt(dist) > FOLLOW) {
				int amount = getSpeed();

				if (follows.getX() < x)
					x -= amount;
				else if (follows.getX() > x)
					x += amount;

				if (follows.getY() < y)
					y -= amount;
				else if (follows.getY() > y)
					y += amount;
			}
		} else {
			super.animate();
		}
	}

	public void draw(Graphics2D g2d) {

		super.draw(g2d);
	}

	@Override
	public void turnAround(int wallX, int wallY) {
		if (follows == null)
			super.turnAround(wallX, wallY);
	}

	@Override
	public boolean takeDamage(int amount) {
		if (!super.takeDamage(amount) && follows == null) {
			for (Enemy e : linkList)
				e.setAlive(false);
		}

		return alive;
	}
	
	public ChainEnemy getFollows() {
		return follows;
	}

	// private class MyPoint {
	// protected int x;
	// protected int y;
	//
	// protected MyPoint(int x2, int y2) {
	// x = x2;
	// y = y2;
	// }
	// }
}

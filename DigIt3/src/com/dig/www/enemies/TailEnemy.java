package com.dig.www.enemies;

import java.util.ArrayList;

import com.dig.www.start.Board;

public class TailEnemy extends ChainEnemy {

	private Enemy myFollows;

	public TailEnemy(int x, int y, String loc, Board owner, boolean flying, int health, int links, Enemy myOwner) {
		super(x, y, loc, owner, flying, health, links);

		myFollows = myOwner;
	}

	public void animate() {

		if (!added) {
			added = true;
			linkList = new ArrayList<ChainEnemy>();
			for (int i = 0; i < links; i++)
				linkList.add(new ChainEnemy(x, y, loc, owner, flying, health / 2, i != 0 ? linkList.get(i - 1) : this));
			owner.getEnemies().addAll(linkList);
		}

		if (!myFollows.isAlive()) {
			alive = false;
			for (Enemy e : linkList)
				e.setAlive(false);
		}

		basicAnimate();
		int dist = (int) Math.pow(myFollows.getX() - x, 2) + (int) Math.pow(myFollows.getY() - y, 2);

		if (Math.sqrt(dist) > FOLLOW) {
			int amount = getSpeed();

			if (myFollows.getX() < x)
				x -= amount;
			else if (myFollows.getX() > x)
				x += amount;

			if (myFollows.getY() < y)
				y -= amount;
			else if (myFollows.getY() > y)
				y += amount;
		}
	}
}

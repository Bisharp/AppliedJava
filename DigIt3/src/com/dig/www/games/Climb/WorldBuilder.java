package com.dig.www.games.Climb;

import java.util.ArrayList;

import com.dig.www.util.Statics;

public class WorldBuilder {

	private static final int BLOCK = 50;

	public ArrayList<Object> getWorld(Climb owner, int level) {
		ArrayList<Object> world = new ArrayList<Object>();

		final int width = Climb.GW / BLOCK - 4;
		final int height = Climb.GH * (Statics.RAND.nextInt(5) + 3) / BLOCK;
		char[][] c = new char[width][height + 1];

		int x;
		int y;
		int i;
		int i2;
		int l;
		int l2;
		int blockNum = 0;

		for (x = 0; x < width; x++)
			for (y = 0; y < height; y++)
				c[x][y] = '0';

		final int levels = Statics.RAND.nextInt(3) + 3;
		final int amount = height / levels;
		final int scramble = Statics.RAND.nextInt(10);
		int[] discrepency = new int[levels];
		for (i = 0; i < levels; i++)
			discrepency[i] = amount - 1;

		if (scramble != 0) {
			for (i = 0; i < scramble; i++) {
				l = Statics.RAND.nextInt(discrepency.length);
				l2 = Statics.RAND.nextInt(discrepency.length);
				i2 = Statics.RAND.nextInt(amount / 2);

				if (discrepency[l] - i2 <= 1 || discrepency[l2] + i2 <= 1) {
					i--;
					continue;
				}

				discrepency[l] -= i2;
				discrepency[l2] += i2;
			}
		}

		y = height - 2;
		x = Statics.RAND.nextInt(width);
		int change = Statics.RAND.nextInt(3) + 1;
		for (int disc : discrepency) {
			y++;
			for (i = 0; i < disc; i++) {
				y--;
				c[x][y] = 'L';
			}
			x = x - change >= 0 ? x - change : 0;

			for (i = 0; i < change * 2 && x < width; i++) {
				if (c[x][y] != 'L')
					c[x][y] = '1';
				x++;
				blockNum++;
			}

			do
				x = Statics.RAND.nextInt(width);
			while (c[x][y] != '1');

			y--;
			change = Statics.RAND.nextInt(3) + 1;
		}

		i = 0;
		boolean dropCat = false;
		while (i < blockNum / 10) {
			x = Statics.RAND.nextInt(width);
			y = Statics.RAND.nextInt(height);
			if (c[x][y] == '1' && c[x][y - 1] != 'L' && (y == height || c[x][y + 1] == '0')) {
				c[x][y - 1] = dropCat ? 'C' : 'E';
				i++;

				if (i >= blockNum / 10 && !dropCat) {
					i--;
					dropCat = true;
				}
			}
		}

		boolean hasVSwitch = false;
		for (y = 0; y < height; y++) {
			for (x = 0; x < width; x++) {

				if (c[x][y] == '1' && !hasVSwitch) {
					world.add(new Switch((x + 2) * BLOCK, y * BLOCK - 20, owner));
					hasVSwitch = true;
				}

				switch (c[x][y]) {

				case '1':
					world.add(new Object((x + 2) * BLOCK, y * BLOCK, BLOCK, BLOCK, owner));
					break;
				case 'E':
					if (Statics.RAND.nextInt(level) > 20)
						world.add(new Enemy((x + 2) * BLOCK, y * BLOCK, "images/climb/evil/tank.gif", owner, Enemy.Type.WALK, true));
					else if (Statics.RAND.nextInt(level * 2) > 20)
						world.add(new Enemy((x + 2) * BLOCK, y * BLOCK, "images/climb/evil/nightmare.png", owner, Enemy.Type.CONTRARY));
					else if (Statics.RAND.nextInt(level * 3) > 20)
						world.add(new Enemy((x + 2) * BLOCK, y * BLOCK, "images/climb/evil/slug.png", owner, Enemy.Type.WALK));
					else
						world.add(new Enemy((x + 2) * BLOCK, y * BLOCK, "images/climb/evil/ghostRock.png", owner, Enemy.Type.STAND));
					break;
				case 'L':
					world.add(new Ladder((x + 2) * BLOCK, y * BLOCK, "images/climb/other/ladder.png", owner, c[x][y - 1] == '0'));
					if (c[x][y - 1] == '0')
						world.add(new Object((x + 2) * BLOCK, y * BLOCK, BLOCK, 7, owner));
					break;

				case 'C':
					world.add(new Cat((x + 2) * BLOCK, y * BLOCK, owner));
				}
				System.out.print(c[x][y]);
			}
			System.out.println();
			
			if (y + 1 == height)
				world.add(new Object(0, y * BLOCK, Climb.GW, BLOCK, owner));
				
		}

		// world.add(new Object(0, Climb.GH - 50, 600, 100, owner));
		// for (i = 0; i < 11 * 50; i += 50)
		// world.add(new Ladder(500, i, "images/climb/other/ladder.png",
		// owner));
		// world.add(new Cat(50, Climb.GH - 100, owner));
		// world.add(new ShootingEnemy(50, Climb.GH / 2,
		// "images/climb/evil/ghostRock.png", owner, Enemy.Type.WALK));
		// world.add(new Object(40, Climb.GH - Climb.GH / 3, 250, 50, owner));
		// world.add(new Object(70, Climb.GH / 2, 250, 50, owner));

		return world;
	}
}

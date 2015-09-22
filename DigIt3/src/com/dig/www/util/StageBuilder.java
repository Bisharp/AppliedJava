package com.dig.www.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import com.dig.www.blocks.*;
import com.dig.www.enemies.*;
import com.dig.www.start.Board;

public class StageBuilder {

	private static final int OFF = Statics.BOARD_WIDTH / 2 - 50;
	private static StageBuilder me;

	public static StageBuilder getInstance() {

		if (me == null)
			me = new StageBuilder();

		return me;
	}

	public ArrayList<Block> read(String loc, Board par) {

		ArrayList<Block> world = new ArrayList<Block>();

		int ln = 0;

		try {

			String tryLoc = StageBuilder.class.getProtectionDomain().getCodeSource().getLocation().getFile() + "maps/" + loc + ".txt";

			File map = new File(tryLoc);

			if (map.exists()) {

				BufferedReader reader = new BufferedReader(new FileReader(tryLoc));
				String line;

				while ((line = reader.readLine()) != null) {
					// System.out.println(line);
					for (int i = 0; i < line.length(); i++) {
						switch (line.charAt(i)) {

						case 'O':
							par.setSpawnX(-Statics.BLOCK_HEIGHT * i + OFF);
							par.setSpawnY(-Statics.BLOCK_HEIGHT * ln + OFF);
						case '1':
							world.add(new Block(Statics.BLOCK_HEIGHT * i, Statics.BLOCK_HEIGHT * ln, Statics.DUMMY, par, Block.Blocks.GROUND));
							break;

						case 'W':
							world.add(new HardBlock(Statics.BLOCK_HEIGHT * i, Statics.BLOCK_HEIGHT * ln, Statics.DUMMY, par, Block.Blocks.WALL));
							break;

						case 'P':
							world.add(new Block(Statics.BLOCK_HEIGHT * i, Statics.BLOCK_HEIGHT * ln, Statics.DUMMY, par, Block.Blocks.PIT));
							break;

						case 'R':
							world.add(new HardBlock(Statics.BLOCK_HEIGHT * i, Statics.BLOCK_HEIGHT * ln, Statics.DUMMY, par, Block.Blocks.ROCK));
							break;

						case 'C':
							world.add(new HardBlock(Statics.BLOCK_HEIGHT * i, Statics.BLOCK_HEIGHT * ln, Statics.DUMMY, par, Block.Blocks.CARPET));
							break;

						case '*':
							world.add(new HardBlock(Statics.BLOCK_HEIGHT * i, Statics.BLOCK_HEIGHT * ln, Statics.DUMMY, par, Block.Blocks.CRYSTAL));
							break;

						case '>':
							world.add(new HardBlock(Statics.BLOCK_HEIGHT * i, Statics.BLOCK_HEIGHT * ln, Statics.DUMMY, par, Block.Blocks.SWITCH));
							break;
						}
					}
					ln++;
				}
				reader.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return world;
	}

	public ArrayList<Enemy> loadEn(String loc, Board owner) {
		ArrayList<Enemy> enemies = new ArrayList<Enemy>();
		try {
			ArrayList<String> strings = new ArrayList<String>();
			File saveFile = new File(StageBuilder.class.getProtectionDomain().getCodeSource().getLocation().getFile() + "maps/" + loc + "E.txt");
			if (saveFile.exists()) {
				BufferedReader reader = new BufferedReader(new FileReader(saveFile));
				String line;
				while ((line = reader.readLine()) != null) {
					strings.add(line);
				}
				reader.close();
				for (int c = 0; c < strings.size(); c++) {
					ArrayList<String> stuff = new ArrayList<String>();// should
																		// have
																		// 5
					String currentS = "";
					for (int c2 = 0; c2 < strings.get(c).length(); c2++) {

						if (strings.get(c).charAt(c2) == ',') {
							stuff.add(currentS);
							currentS = "";

						} else {
							currentS += strings.get(c).charAt(c2);
						}
					}
					if (currentS != "") {
						stuff.add(currentS);
					}
					try {
						int enX = Integer.parseInt(stuff.get(1));
						int enY = Integer.parseInt(stuff.get(2));
						char ch = stuff.get(0).charAt(0);
						String enImg = stuff.get(3);
						boolean flying = stuff.get(4).charAt(0) == 't';
						int health = Integer.parseInt(stuff.get(5));
						switch (ch) {
						case 'L':
							enemies.add(new Launch(enX, enY, enImg, owner, Integer.parseInt(stuff.get(6)), flying, health));
							break;
						case 'P':
							enemies.add(new PursuingLaunch(enX, enY, enImg, owner, Integer.parseInt(stuff.get(6)), flying, health));
							break;
						case 'S':
							enemies.add(new StandEnemy(enX, enY, enImg, owner, flying, health));
							break;
						case 'T':
							enemies.add(new TrackingEnemy(enX, enY, enImg, owner, flying, health));
							break;
						case 'C':
							enemies.add(new ChargeEnemy(enX, enY, enImg, owner, flying, health));
							break;
						case 'W':
							enemies.add(new WalkingEnemy(enX, enY, enImg, owner, flying, health));
							break;
						case 'E':
							enemies.add(new ExplosivesSpawner(enX, enY, enImg, owner, Integer.parseInt(stuff.get(6)), flying, health));
							break;

						// Lowercase denotes an enemy that must see you before
						// attacking.
						case 'w':
							enemies.add(new SeeChaseEnemy(enX, enY, enImg, owner, flying, health));
							break;
						case 'l':
							enemies.add(new SeeShootEnemy(enX, enY, enImg, owner, Integer.parseInt(stuff.get(6)), flying, health));
							break;
						case 's':
							enemies.add(new SecurityEnemy(enX, enY, enImg, owner, flying, health, stuff.get(6)));
							break;
						}
					} catch (IndexOutOfBoundsException ex) {
						ex.printStackTrace();

					}
				}

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return enemies;
	}
}
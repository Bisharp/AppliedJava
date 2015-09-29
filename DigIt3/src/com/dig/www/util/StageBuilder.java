package com.dig.www.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.dig.www.blocks.*;
import com.dig.www.enemies.*;
import com.dig.www.start.Board;

public class StageBuilder {

	private static final int OFF = Statics.BOARD_WIDTH / 2 - 50;
	private static StageBuilder me;
	private String loc;
	private Board owner;

	public static StageBuilder getInstance(String loc, Board owner) {

		if (me == null)
			me = new StageBuilder(loc, owner);

		return me;
	}

	public StageBuilder(String loc, Board owner) {
		this.loc = loc;
		this.owner = owner;
	}

	public void changeState(String loc, Board owner) {
		this.owner = owner;
		this.loc = loc;
	}

	public ArrayList<Block> read() {
		ArrayList<Block> world = new ArrayList<Block>();

		try {
			int ln = 0;
			boolean first = true;
			String tryLoc = StageBuilder.class.getProtectionDomain().getCodeSource().getLocation().getFile() + "maps/" + loc + "/" + loc + ".txt";

			File map = new File(tryLoc);

			if (map.exists()) {

				BufferedReader reader = new BufferedReader(new FileReader(tryLoc));
				String line;

				while ((line = reader.readLine()) != null) {
					// System.out.println(line);

					if (first) {
						first = false;
					} else {
						for (int i = 0; i < line.length(); i++) {
							switch (line.charAt(i)) {

							case 'O':
								owner.setSpawnX(-Statics.BLOCK_HEIGHT * i + OFF);
								owner.setSpawnY(-Statics.BLOCK_HEIGHT * ln + OFF - 299);
							case '1':
								world.add(new Block(Statics.BLOCK_HEIGHT * i, Statics.BLOCK_HEIGHT * ln, Statics.DUMMY, owner, Block.Blocks.GROUND));
								break;
							case '2':
								world.add(new Block(Statics.BLOCK_HEIGHT * i, Statics.BLOCK_HEIGHT * ln, Statics.DUMMY, owner, Block.Blocks.DIRT));
								break;
							case 'L':
								world.add(new Block(Statics.BLOCK_HEIGHT * i, Statics.BLOCK_HEIGHT * ln, Statics.DUMMY, owner, Block.Blocks.LIQUID));
								break;
							case 'W':
								world.add(new HardBlock(Statics.BLOCK_HEIGHT * i, Statics.BLOCK_HEIGHT * ln, Statics.DUMMY, owner, Block.Blocks.WALL));
								break;
							case 'I':
								world.add(new HardBlock(Statics.BLOCK_HEIGHT * i, Statics.BLOCK_HEIGHT * ln, Statics.DUMMY, owner, Block.Blocks.WALL));
								world.get(world.size() - 1).setVisible(false);
								break;

							case 'P':
								world.add(new Block(Statics.BLOCK_HEIGHT * i, Statics.BLOCK_HEIGHT * ln, Statics.DUMMY, owner, Block.Blocks.PIT));
								break;

							case 'R':
								world.add(new HardBlock(Statics.BLOCK_HEIGHT * i, Statics.BLOCK_HEIGHT * ln, Statics.DUMMY, owner, Block.Blocks.ROCK));
								break;

							case 'C':
								world.add(new HardBlock(Statics.BLOCK_HEIGHT * i, Statics.BLOCK_HEIGHT * ln, Statics.DUMMY, owner,
										Block.Blocks.CARPET));
								break;

							case '*':
								world.add(new HardBlock(Statics.BLOCK_HEIGHT * i, Statics.BLOCK_HEIGHT * ln, Statics.DUMMY, owner,
										Block.Blocks.CRYSTAL));
								break;

							case '>':
								world.add(new HardBlock(Statics.BLOCK_HEIGHT * i, Statics.BLOCK_HEIGHT * ln, Statics.DUMMY, owner,
										Block.Blocks.SWITCH));
								break;

							}
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

	public ArrayList<Enemy> loadEn() {

		ArrayList<Enemy> enemies = new ArrayList<Enemy>();
		try {
			ArrayList<String> strings = new ArrayList<String>();
			File saveFile = new File(StageBuilder.class.getProtectionDomain().getCodeSource().getLocation().getFile() + "maps/" + loc + "/" + loc
					+ "E.txt");
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
						case 'b':
							// TODO securityEnemy
							if (stuff.get(8).charAt(0) == 'B')
								enemies.add(new BackwardSecurityEnemy(enX, enY, enImg, owner, flying, health, stuff.get(6), createArray(stuff.get(7))));
							else
								enemies.add(new PatrolSecurityEnemy(enX, enY, enImg, owner, flying, health, stuff.get(6), createArray(stuff.get(7))));
							break;
						case 'p':
							// TODO securityEnemy
							if (stuff.get(7).charAt(0) == 'B')
								enemies.add(new BackwardPathEnemy(enX, enY, enImg, owner, flying, health, createArray(stuff.get(6))));
							else
								enemies.add(new PathEnemy(enX, enY, enImg, owner, flying, health, createArray(stuff.get(6))));
							break;
						case 's':
							enemies.add(new SecurityEnemy(enX, enY, enImg, owner, flying, health, stuff.get(6)));
							break;
						case 'c':
							enemies.add(new LookChaseEnemy(enX, enY, enImg, owner, flying, health));
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

	private int[][] createArray(String string) {
		// TODO Auto-generated method stub

		System.out.println(string);
		String[] split = string.split("\'");
		int splitLength = split.length / 2;

		int splitCounter = 0;
		int p2;
		int[][] toReturn = new int[splitLength][2];

		for (int p1 = 0; p1 < splitLength; p1++) {

			System.out.println("In loop");
			for (p2 = 0; p2 < toReturn[p1].length; p2++) {
				toReturn[p1][p2] = Integer.parseInt(split[splitCounter]);
				splitCounter++;
			}
		}

		for (int k = 0; k < toReturn.length; k++) {
			for (int c = 0; c < toReturn[k].length; c++)
				System.out.print(toReturn[k][c] + " ");
			System.out.println();
		}

		return toReturn;
	}

	public TexturePack readText() {
		// TODO Auto-generated method stub
		String tryLoc = StageBuilder.class.getProtectionDomain().getCodeSource().getLocation().getFile() + "maps/" + loc + "/" + loc + ".txt";
		TexturePack pack = TexturePack.GRASSY;
		File map = new File(tryLoc);

		if (map.exists()) {

			try {
				String line;
				BufferedReader reader = new BufferedReader(new FileReader(tryLoc));
				if ((line = reader.readLine()) != null) {
					switch (line.charAt(0)) {
					case 'D':
						pack = TexturePack.DESERT;
						break;
					case 'S':
						pack = TexturePack.SNOWY;
						break;
					case 'I':
						pack = TexturePack.ISLAND;
						break;
					case 'V':
						pack = TexturePack.VOLCANO;
						break;
					case 'G':
					default:
						pack = TexturePack.GRASSY;
					}
					reader.close();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return pack;
	}

	public ArrayList<Portal> loadPortals() {
		// TODO Auto-generated method stub
		ArrayList<Portal> portals = new ArrayList<Portal>();
		try {
			ArrayList<String> strings = new ArrayList<String>();
			File saveFile = new File(StageBuilder.class.getProtectionDomain().getCodeSource().getLocation().getFile() + "maps/" + loc + "/" + loc
					+ "P.txt");
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

					int enX = Integer.parseInt(stuff.get(0));
					int enY = Integer.parseInt(stuff.get(1));
					String area = stuff.get(2);
					int collectibleNum = Integer.parseInt(stuff.get(3));
					String type = stuff.get(4);
					
					portals.add(new Portal(enX, enY, owner, area, collectibleNum, type));
				}

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return portals;
	}
}
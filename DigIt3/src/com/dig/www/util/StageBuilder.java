package com.dig.www.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.dig.www.blocks.*;
import com.dig.www.enemies.*;
import com.dig.www.npc.*;
import com.dig.www.objects.*;
import com.dig.www.character.GameCharacter;
import com.dig.www.character.Items;
import com.dig.www.character.GameCharacter.Types;
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
		setLoc(loc);
		this.owner = owner;
	}

	public void changeState(String loc, Board owner) {
		this.owner = owner;
		setLoc(loc);
	}

	private void setLoc(String loc) {
		String tryLoc = StageBuilder.class.getProtectionDomain().getCodeSource().getLocation().getFile() + "maps/" + loc + "/" + loc + ".txt";
		File map = new File(tryLoc);
		if (!map.exists())
			loc = Board.DEFAULT;
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

							// case '>':
							// world.add(new HardBlock(Statics.BLOCK_HEIGHT * i,
							// Statics.BLOCK_HEIGHT * ln, Statics.DUMMY, owner,
							// Block.Blocks.SWITCH));
							// break;

							}
						}
					}
					ln++;
				}
				reader.close();
			}
		} catch (Exception e) {
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
						case '~':
							enemies.add(new ChainEnemy(enX, enY, enImg, owner, flying, health, Integer.parseInt(stuff.get(6))));
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
							if (stuff.get(8).charAt(0) == 'B')
								enemies.add(new BackwardSecurityEnemy(enX, enY, enImg, owner, flying, health, stuff.get(6), createArray(stuff.get(7))));
							else
								enemies.add(new PatrolSecurityEnemy(enX, enY, enImg, owner, flying, health, stuff.get(6), createArray(stuff.get(7))));
							break;
						case 'p':
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
						case 't':
							enemies.add(new SideToPlayer(enX, enY, enImg, owner, flying, health));
							break;
						case 'B':
							switch (enImg) {
							case "Head":
								enemies.add(new HeadBoss(enX, enY, owner));
								break;
							case "Lizard-Man":
								enemies.add(new LizardMan(enX, enY, owner));
								break;
							case "Pod":
								enemies.add(new SpinnyBoss(enX, enY, owner));
								break;
							}

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

	public ArrayList<NPC> loadNPC() {

		ArrayList<NPC> npcs = new ArrayList<NPC>();
		int questCount = 0;

		try {
			ArrayList<String> strings = new ArrayList<String>();
			File saveFile = new File(StageBuilder.class.getProtectionDomain().getCodeSource().getLocation().getFile() + "maps/" + loc + "/" + loc
					+ "N.txt");
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
						int nX = Integer.parseInt(stuff.get(0));
						int nY = Integer.parseInt(stuff.get(1));
						String identity = stuff.get(2);

						switch (identity) {
						case NPC.WIZARD:
							npcs.add(new WizardGuy(nX, nY, "images/npcs/map/stationary/wizard.png", owner, loc));
							break;
						case NPC.KEPLER:
							npcs.add(new Kepler(nX, nY, "images/npcs/map/stationary/kepler.png", owner, loc));
							break;
						case NPC.SIR_COBALT:
							boolean has = false;
							if (owner.getCharacter().getType() == Types.SIR_COBALT)
								has = true;
							if (!has)
								for (GameCharacter chara : owner.getFriends()) {
									if (chara.getType() == Types.SIR_COBALT) {
										has = true;
										break;
									}
								}
							if (!has || !stuff.get(3).startsWith("t"))
								npcs.add(new SirCobalt(nX, nY, "images/npcs/map/stationary/sirCobalt.png", owner, loc, stuff.get(3).startsWith("t")));
							break;
						case NPC.SHOPKEEP:
							npcs.add(new Shopkeep(nX, nY, "images/npcs/map/stationary/shopkeep.png", owner, loc));
							break;
						case NPC.GATEKEEPER:
							npcs.add(new Gatekeeper(nX, nY, "images/npcs/map/stationary/gatekeeper.png", owner, loc, Integer.parseInt(stuff.get(3))));
							break;
						case NPC.MACARONI:
							has = false;
							if (owner.getCharacter().getType() == Types.SIR_COBALT)
								has = true;
							if (!has)
								for (GameCharacter chara : owner.getFriends()) {
									if (chara.getType() == Types.SIR_COBALT) {
										has = true;
										break;
									}
								}
							if (!has)
								npcs.add(new Macaroni(nX, nY, "images/npcs/map/stationary/macaroni.png", owner, loc));
							break;
						case NPC.QUEST:
							npcs.add(new QuestNPC(nX, nY, "images/npcs/map/stationary/reyzu.png", owner, loc, questCount));
							questCount++;
							break;
						case NPC.PLATO:
							npcs.add(new PLATO(nX, nY, "images/npcs/map/stationary/plato.png", owner, loc));
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

		return npcs;
	}

	public ArrayList<Objects> loadObjects() {

		ArrayList<Objects> npcs = new ArrayList<Objects>();
		int count = 0;
		try {
			ArrayList<String> strings = new ArrayList<String>();
			File saveFile = new File(StageBuilder.class.getProtectionDomain().getCodeSource().getLocation().getFile() + "maps/" + loc + "/" + loc
					+ "O.txt");
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
						int nX = Integer.parseInt(stuff.get(0));
						int nY = Integer.parseInt(stuff.get(1));
						String loc = stuff.get(2);
						boolean wall = false;
						int val = stuff.size() > 4 ? Integer.parseInt(stuff.get(4)) : 0;
						if (stuff.get(3).charAt(0) == 't')
							wall = true;

						if (stuff.size() < 6)
							if (val == 0)
								npcs.add(new Objects(nX, nY, loc, wall, owner));
							else if (val == -1) {
								npcs.add(new SpecialCollectible(nX, nY, loc, owner, count));
								count++;
							} else if (val == -2)
								npcs.add(new RandSkinObject(nX, nY, loc, wall, owner));
							else if (val == -3) {
								owner.setSpawnX(-nX + OFF);
								owner.setSpawnY(-nY + OFF - 299);
							} else if (val == -4) {
								npcs.add(new BossBlock(nX, nY, owner));
							} else if (val == -5) {
								npcs.add(new HookObject(nX, nY, owner));
							} else if (val == -6) {
								npcs.add(new DropPoint(nX, nY, owner));
							} else
								npcs.add(new MoneyObject(nX, nY, loc, owner, val));
						else if (Items.translate(stuff.get(5)).equals(Items.NULL.toString()))
							npcs.add(new CollectibleCharacter(nX, nY, loc, owner));
						else
							npcs.add(new CollectibleObject(nX, nY, loc, owner, Items.translate(stuff.get(5))));

					} catch (IndexOutOfBoundsException ex) {
						ex.printStackTrace();

					}
				}

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return npcs;
	}

	private int[][] createArray(String string) {

		String[] split = string.split("\'");
		int splitLength = split.length / 2;

		int splitCounter = 0;
		int p2;
		int[][] toReturn = new int[splitLength][2];

		for (int p1 = 0; p1 < splitLength; p1++) {

			for (p2 = 0; p2 < toReturn[p1].length; p2++) {
				toReturn[p1][p2] = Integer.parseInt(split[splitCounter]);
				splitCounter++;
			}
		}

		return toReturn;
	}

	public TexturePack readText() {
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
					case 'H':
						pack = TexturePack.HAUNTED;
						break;
					case 'L':
						pack = TexturePack.LAB;
						break;
					case 'G':
					default:
						pack = TexturePack.GRASSY;
					}
					reader.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return pack;
	}

	public ArrayList<Portal> loadPortals() {
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

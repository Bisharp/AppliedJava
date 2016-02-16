package com.dig.www.util;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.dig.www.blocks.Block;
import com.dig.www.blocks.Door;
import com.dig.www.blocks.HardBlock;
import com.dig.www.blocks.Portal;
import com.dig.www.blocks.TexturePack;
import com.dig.www.character.GameCharacter;
import com.dig.www.character.GameCharacter.Types;
import com.dig.www.character.Items;
import com.dig.www.enemies.*;
import com.dig.www.npc.*;
import com.dig.www.objects.*;
import com.dig.www.start.Board;
import com.dig.www.start.Board.DayNight;

public class StageBuilder {

	private static final int OFF = Statics.BOARD_WIDTH / 2 - 50;
	private static StageBuilder me;
	private String loc;
	private Board owner;
	private int level = 1;
	private int spawnNum;
	private Point spawnPoint;
	private String mode;
	private DayNight time;
	
	protected DayNight getTime() {
		return time;
	}

	public static StageBuilder getInstance(String mode, String loc, Board owner, int spawnNum) {

		if (me == null)
			me = new StageBuilder(mode, loc, owner, spawnNum);

		return me;
	}

	public StageBuilder(String mode, String loc, Board owner, int spawnNum) {
		setLoc(loc);
		this.mode = mode;
		this.owner = owner;
		this.spawnNum = spawnNum;
	}

	public void changeState(String mode, String loc, Board owner, int spawnNum) {
		this.owner = owner;
		this.mode = mode;
		this.spawnNum = spawnNum;
		setLoc(loc);
	}

	public int getSpawnNum() {
		return spawnNum;
	}

	private void setLoc(String loc) {
		String tryLoc = Statics.getBasedir() + "maps/" + mode + "/" + loc + "/" + loc + ".txt";
		File map = new File(tryLoc);
		if (!map.exists())
			loc = Board.DEFAULT;
		this.loc = loc;
	}

	public int getLevel() {
		return level;
	}

	public ArrayList<Block> read() {
		ArrayList<Block> world = new ArrayList<Block>();

		try {
			int ln = 0;
			boolean first = true;
			String tryLoc = Statics.getBasedir() + "maps/" + mode + "/" + loc + "/" + loc + ".txt";

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

							// Someone delete this abomination.
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
			File saveFile = new File(Statics.getBasedir() + "maps/" + mode + "/" + loc + "/" + loc + "E.txt");
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
						String ch = stuff.get(0);
						String enImg = stuff.get(3);
						boolean flying = stuff.get(4).charAt(0) == 't';
						int health = (int) (Integer.parseInt(stuff.get(5)) * (double) (1 + (double) ((level - 1) / (double) 10)));
						//enemies.add(new PopChaseEnemy(enX, enY, owner, health));
						//if(false)
						switch (ch) {
						case "Launch":
							enemies.add(new Launch(enX, enY, enImg, owner, Integer.parseInt(stuff.get(6)), flying, health));
							break;
						case "Pursuing Launch":
							enemies.add(new PursuingLaunch(enX, enY, enImg, owner, Integer.parseInt(stuff.get(6)), flying, health));
							break;
						case "Standing":
							enemies.add(new StandEnemy(enX, enY, enImg, owner, flying, health));
							break;
						case "Tracking":
							enemies.add(new TrackingEnemy(enX, enY, enImg, owner, flying, health));
							break;
						case "Charge":
							enemies.add(new ChargeEnemy(enX, enY, enImg, owner, flying, health));
							break;
						case "Walking":
							enemies.add(new WalkingEnemy(enX, enY, enImg, owner, flying, health));
							break;
						case "Explosive Spawning":
							enemies.add(new ExplosivesSpawner(enX, enY, enImg, owner, Integer.parseInt(stuff.get(6)), flying, health));
							break;
						case "Chain":
							enemies.add(new ChainEnemy(enX, enY, enImg, owner, flying, health, Integer.parseInt(stuff.get(6))));
							break;

						case "Tail":
							Enemy e = enemies.get(enemies.size() - 1);
							enemies.remove(e);
							enemies.add(new TailEnemy(enX, enY, enImg, owner, flying, health, Integer.parseInt(stuff.get(6)), e));
							((ChainEnemy) enemies.get(enemies.size() - 1)).setDistance(Integer.parseInt(stuff.get(7)));
							enemies.add(e);
							break;
						case "Slime":
							enemies.add(new Slime(enX, enY, enImg, owner, flying, health));
							break;

						// Lowercase denotes an enemy that must see you before
						// attacking.
						case "See Chase":
							enemies.add(new SeeChaseEnemy(enX, enY, enImg, owner, flying, health));
							break;
						case "See Shoot":
							enemies.add(new SeeShootEnemy(enX, enY, enImg, owner, Integer.parseInt(stuff.get(6)), flying, health));
							break;
						case "Path Security":
							if (stuff.get(8).charAt(0) == 'B')
								enemies.add(new BackwardSecurityEnemy(enX, enY, enImg, owner, flying, health, stuff.get(6), createArray(stuff.get(7))));
							else
								enemies.add(new PatrolSecurityEnemy(enX, enY, enImg, owner, flying, health, stuff.get(6), createArray(stuff.get(7))));
							break;
						case "Path":
							if (stuff.get(7).charAt(0) == 'B')
								enemies.add(new BackwardPathEnemy(enX, enY, enImg, owner, flying, health, createArray(stuff.get(6))));
							else
								enemies.add(new PathEnemy(enX, enY, enImg, owner, flying, health, createArray(stuff.get(6))));
							break;
						case "PatrolChase":
							if (stuff.get(7).charAt(0) == 'B')
								enemies.add(new BackwardPatrolChaseEnemy(enX, enY, enImg, owner, flying, health, createArray(stuff.get(6))));
							else
								enemies.add(new PatrolChaseEnemy(enX, enY, enImg, owner, flying, health, createArray(stuff.get(6))));
							break;
						case "Security":
							enemies.add(new SecurityEnemy(enX, enY, enImg, owner, flying, health, stuff.get(6)));
							break;
						case "Look Chase":
							enemies.add(new LookChaseEnemy(enX, enY, enImg, owner, flying, health));
							break;
						case "Side To Player":
							enemies.add(new SideToPlayer(enX, enY, enImg, owner, flying, health));
							break;

						case "Head Boss":
							enemies.add(new HeadBoss(enX, enY, owner));
							break;
						case "Lizard-Man":
							enemies.add(new LizardMan(enX, enY, owner));
							break;
						case "Pod":
							enemies.add(new SpinnyBoss(enX, enY, owner));
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
		int blockerCount = 0;

		try {
			ArrayList<String> strings = new ArrayList<String>();
			File saveFile = new File(Statics.getBasedir() + "maps/" + mode + "/" + loc + "/" + loc + "N.txt");
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
							npcs.add(new WizardGuy(nX, nY, owner, loc));
							break;
						case NPC.KEPLER:
							npcs.add(new Kepler(nX, nY, owner, loc));
							break;
						case "TutorialSirCobalt":
							npcs.add(new TutorialSirCobalt(nX, nY, owner, loc));
							break;
						case "Tutorial1":
							npcs.add(new Tutorial1(nX, nY, owner, loc));
							break;
						case "Tutorial2":
							npcs.add(new Tutorial2(nX, nY, owner, loc));
							break;
						case "Tutorial3":
							npcs.add(new Tutorial3(nX, nY, owner, loc));
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
							if (!has)
								npcs.add(new SirCobalt(nX, nY, owner, loc, stuff.get(3).startsWith("t")));
							break;
						case NPC.SHOPKEEP:
							npcs.add(new Shopkeep(nX, nY, "images/npcs/map/stationary/shopkeep.png", owner, loc));
							break;
						case NPC.GATEKEEPER:
							npcs.add(new Gatekeeper(nX, nY, "images/npcs/map/stationary/gatekeeper.png", owner, loc, Integer.parseInt(stuff.get(3)),
									blockerCount));
							blockerCount++;
							break;
						case NPC.MACARONI:
							has = false;
							if (owner.getCharacter().getType() == Types.MACARONI)
								has = true;
							if (!has)
								for (GameCharacter chara : owner.getFriends()) {
									if (chara.getType() == Types.MACARONI) {
										has = true;
										break;
									}
								}
							if (!has) {
								npcs.add(new CopyOfMacaroni(nX, nY, owner, loc, questCount));
							}
							questCount++;// It should stay outside the brackets.
							break;
						case NPC.REYZU:
							npcs.add(new Reyzu(nX, nY, owner, loc, questCount));
							questCount++;
							break;
						case NPC.PLATO:
							npcs.add(new PLATO(nX, nY, owner, loc));
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
		int spawnCount = 0;
		try {
			ArrayList<String> strings = new ArrayList<String>();
			File saveFile = new File(Statics.getBasedir() + "maps/" + mode + "/" + loc + "/" + loc + "O.txt");
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

						String value = stuff.size() > 4 ? stuff.get(4) : null;
						int val;
						try {
							val = Integer.parseInt(value);
						} catch (Exception ex) {
							val = 0;
						}
						if (stuff.get(3).charAt(0) == 't')
							wall = true;

						if (stuff.size() < 6)
							if (val == 0)
								npcs.add(new Objects(nX, nY, loc, wall, owner, value));
							else if (val == -1) {
								npcs.add(new SpecialCollectible(nX, nY, loc, owner, count));
								count++;
							} else if (val == -2)
								npcs.add(new RandSkinObject(nX, nY, loc, wall, owner));
							else if (val == -3) {
								if (spawnCount <= spawnNum)
									spawnPoint = new Point(-nX + OFF, -nY + OFF - 299);
								if (wall)
									npcs.add(new CheckPoint(nX, nY, owner, spawnCount));
								else if (stuff.get(3).equals("invisible"))
									npcs.add(new InvisibleCheckPoint(nX, nY, owner, spawnCount));
								spawnCount++;
							} else if (val == -4) {
								npcs.add(new BossBlock(nX, nY, owner));
							} else if (val == -5) {
								npcs.add(new HookObject(nX, nY, owner));
							} else if (val == -6) {
								npcs.add(new DropPoint(nX, nY, owner));
							} else if (val == -7) {
								npcs.add(new ActivatedBossWallActivator(nX, nY, owner));
							} else if (val == -8) {
								npcs.add(new ActivatedBossWall(nX, nY, owner));
							} else if (val == -9) {
								npcs.add(new PushCube(nX, nY, owner, wall));
							} else if (val == -9) {
								npcs.add(new PushCube(nX, nY, owner, wall));
							} else if (val == -10) {
								npcs.add(new BombCube(nX, nY, wall, owner));
							} else if (val == -11) {
								npcs.add(new CubeButtonMoneyGiver(nX, nY, owner));
							} else
								npcs.add(new MoneyObject(nX, nY, loc, owner, val));
						else if (Items.translate(stuff.get(5)).equals(Items.NULL.toString()))
							npcs.add(new CollectibleCharacter(nX, nY, loc, owner));
						else
							npcs.add(new CollectibleObject(nX, nY, loc, wall, owner, Items.translate(stuff.get(5))));

					} catch (IndexOutOfBoundsException ex) {
						ex.printStackTrace();

					}
				}

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		try {
			owner.setSpawnLoc(spawnPoint);
			owner.setSpawnX(spawnPoint.x);
			owner.setSpawnY(spawnPoint.y);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(owner, "No spawn point. Leaving game.");
			System.exit(0);
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
		String tryLoc = Statics.getBasedir() + "maps/" + mode + "/" + loc + "/" + loc + ".txt";
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

				String[] array = line.split(",");

				try {
					level = Integer.parseInt(array[2].trim());
				} catch (Exception ex) {
					System.err.println("WARNING: No map level. Setting map level to 1000 to punish cheaters. Expect EXTREME difficulty.");
					level = 1000;
				}
				try {
					int spawnNum = Integer.parseInt(array[3].trim());
					if (this.spawnNum == -1)
						this.spawnNum = spawnNum;

					spawnPoint = null;
				} catch (Exception ex) {
					if (spawnNum == -1) {
						System.err.println("WARNING: No Spawn number. Setting spawn number to 0.");

						spawnNum = 0;
					}
					spawnPoint = null;
				}

				try {
					time = DayNight.translate(array[4]);
				} catch (Exception ex) {
					time = DayNight.ANY;
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return pack;
	}
	
	public static void setTime(Board set) {
		set.setDayNight(me.getTime());
	}

	public ArrayList<Portal> loadPortals() {
		ArrayList<Portal> portals = new ArrayList<Portal>();
		try {
			ArrayList<String> strings = new ArrayList<String>();
			File saveFile = new File(StageBuilder.class.getProtectionDomain().getCodeSource().getLocation().getFile() + "maps/" + mode + "/" + loc
					+ "/" + loc + "P.txt");
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
					int spawnNum = Integer.parseInt(stuff.get(5));
					String type2 = stuff.size() > 7 ? stuff.get(6) : "brown";
					if (type.equals("normal") || type.equals("boss"))
						portals.add(new Portal(enX, enY, owner, area, collectibleNum, type, spawnNum));
					else
						portals.add(new Door(enX, enY, owner, area, collectibleNum, type, type2, spawnNum));

				}

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return portals;
	}

	public String readWeather() {
		String tryLoc = StageBuilder.class.getProtectionDomain().getCodeSource().getLocation().getFile() + "maps/" + mode + "/" + loc + "/" + loc
				+ ".txt";
		File map = new File(tryLoc);

		if (map.exists()) {

			try {
				String line;
				BufferedReader reader = new BufferedReader(new FileReader(tryLoc));
				if ((line = reader.readLine()) != null) {
					reader.close();
					String weather = line.split(",")[1];
					System.out.println("Weather: " + weather);
					if (weather.equals("none"))
						return null;
					else
						return weather;
				}
				reader.close();
			} catch (Exception e) {
			}
		}

		return null;
	}
}

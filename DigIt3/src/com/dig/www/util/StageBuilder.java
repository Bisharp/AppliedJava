package com.dig.www.util;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.dig.www.blocks.*;
import com.dig.www.character.GameCharacter;
import com.dig.www.character.GameCharacter.Types;
import com.dig.www.character.Items;
import com.dig.www.enemies.*;
import com.dig.www.npc.*;
import com.dig.www.npc.betweenTimes.*;
import com.dig.www.objects.*;
import com.dig.www.start.Board;
import com.dig.www.start.Board.DayNight;

public class StageBuilder {

	private static final int OFF = Statics.BOARD_WIDTH / 2 - 50;
	private static final int OFFH = Statics.BOARD_HEIGHT/2-50;
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
		// String tryLoc = Statics.getBasedir() + "maps/" + mode + "/" + loc +
		// "/" + loc + ".txt";
		// File map = new File(tryLoc);
		// if (!map.exists())
		// loc = Board.DEFAULT;
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
			// String tryLoc = Statics.getBasedir() + "maps/" + mode + "/" + loc
			// + "/" + loc + ".txt";

			// File map = new File(tryLoc);

			// if (true) {

			// BufferedReader reader = new BufferedReader(new
			// FileReader(tryLoc));
			// String line;
			String lines = Statics.readFromNotJarFile("/maps/" + mode + "/" + loc + "/" + loc + ".txt");
			for (String line : lines.split("\n")) {
				// while ((line = reader.readLine()) != null) {
				// System.out.println(line);

				if (first) {
					first = false;
				} else {
					for (int i = 0; i < line.length(); i++) {
						switch (line.charAt(i)) {
						case '1':
							world.add(new TerrainBlock(Statics.BLOCK_HEIGHT * i, Statics.BLOCK_HEIGHT * ln, owner, Block.Blocks.GROUND));
							break;
						case '2':
							world.add(new TerrainBlock(Statics.BLOCK_HEIGHT * i, Statics.BLOCK_HEIGHT * ln, owner, Block.Blocks.DIRT));
							break;
						case 'L':
							world.add(new LiquidBlock(Statics.BLOCK_HEIGHT * i, Statics.BLOCK_HEIGHT * ln, owner));
							break;
						case 'W':
							world.add(new WallBlock(Statics.BLOCK_HEIGHT * i, Statics.BLOCK_HEIGHT * ln, owner));
							break;
						case 'I':
							world.add(new WallBlock(Statics.BLOCK_HEIGHT * i, Statics.BLOCK_HEIGHT * ln, owner));
							world.get(world.size() - 1).setVisible(false);
							break;

						case 'P':
							world.add(new TerrainBlock(Statics.BLOCK_HEIGHT * i, Statics.BLOCK_HEIGHT * ln, owner, Block.Blocks.PIT));
							break;

						case 'R':
							world.add(new StoneBlock(Statics.BLOCK_HEIGHT * i, Statics.BLOCK_HEIGHT * ln, owner, false));
							break;

						case 'C':
							world.add(new CarpetBlock(Statics.BLOCK_HEIGHT * i, Statics.BLOCK_HEIGHT * ln, owner));
							break;

						case '*':
							world.add(new StoneBlock(Statics.BLOCK_HEIGHT * i, Statics.BLOCK_HEIGHT * ln, owner, true));
							break;

						// Someone delete this abomination.
						// TODO delete this abomination
						// case '>':
						// world.add(new HardBlock(Statics.BLOCK_HEIGHT * i,
						// Statics.BLOCK_HEIGHT * ln, Statics.DUMMY, owner,
						// Block.Blocks.SWITCH));
						// break;

						}
					}
				}
				ln++;
				// }
				// reader.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return world;
	}

	public ArrayList<Enemy> loadEn() {
		ArrayList<Enemy> enemies = new ArrayList<Enemy>();
		try {
			// ArrayList<String> strings = new ArrayList<String>();
			// File saveFile = new File(Statics.getBasedir() + "maps/" + mode +
			// "/" + loc + "/" + loc + "E.txt");
			// if (saveFile.exists()) {
			// BufferedReader reader = new BufferedReader(new
			// FileReader(saveFile));
			// String line;
			// while ((line = reader.readLine()) != null) {
			// strings.add(line);
			// }
			// reader.close();
			// for (int c = 0; c < strings.size(); c++) {
			String lines = Statics.readFromNotJarFile("/maps/" + mode + "/" + loc + "/" + loc + "E.txt");
			for (String line : lines.split("\n")) {
				ArrayList<String> stuff = new ArrayList<String>();// should
																	// have
																	// 5
				String currentS = "";
				for (int c2 = 0; c2 < line.length(); c2++) {

					if (line.charAt(c2) == ',') {
						stuff.add(currentS);
						currentS = "";

					} else {
						currentS += line.charAt(c2);
					}
				}
				if (currentS != "") {
					stuff.add(currentS);
				}
				if (stuff.size() > 0)
					try {
						int enX = Integer.parseInt(stuff.get(1));
						int enY = Integer.parseInt(stuff.get(2));
						String ch = stuff.get(0);
						String enImg = stuff.get(3);
						boolean flying = stuff.get(4).charAt(0) == 't';
						int health = (int) (Integer.parseInt(stuff.get(5)) * (double) (1 + (double) ((level - 1) / (double) 10)));
						// enemies.add(new PopChaseEnemy(enX, enY, owner,
						// health));
						// if(false)
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
						case "PoisonOrFlame":
							enemies.add(new PoisonOrFlame(enX, enY,enImg ,owner,flying));
							break;
						case "Head Boss":
							enemies.add(new HeadBoss(enX, enY, owner));
							break;
						case "Lizard Man":
							enemies.add(new LizardMan(enX, enY, owner));
							break;
						case "Pod":
							enemies.add(new SpinnyBoss(enX, enY, owner));
							break;
						case "Ryo2":
							enemies.add(new RyoBoss2(enX, enY, owner));
							break;
						case "Stone":
							enemies.add(new StoneBoss(enX, enY, owner));
							break;
						case "GiantBoss":
							enemies.add(new GiantBoss(enX, enY, owner));
							break;
						case "VineBoss":
							enemies.add(new VineBoss(enX, enY, owner));
							break;
						case "Troll Boss":
							enemies.add(new TrollBoss(enX, enY, owner));
							break;
							
						}
					} catch (IndexOutOfBoundsException ex) {
						ex.printStackTrace();

					}
			}

			// }
		} catch (Exception ex) {
			ex.printStackTrace();
			System.err.println("ERROR: could not load Enemies.");
		}

		return enemies;
	}

	public ArrayList<NPC> loadNPC() {

		ArrayList<NPC> npcs = new ArrayList<NPC>();
		int questCount = 0;
		int blockerCount = 0;

		try {
			// ArrayList<String> strings = new ArrayList<String>();
			// File saveFile = new File(Statics.getBasedir() + "maps/" + mode +
			// "/" + loc + "/" + loc + "N.txt");
			// if (saveFile.exists()) {
			// BufferedReader reader = new BufferedReader(new
			// FileReader(saveFile));
			// String line;
			// while ((line = reader.readLine()) != null) {
			// strings.add(line);
			// }
			// reader.close();
			// for (int c = 0; c < strings.size(); c++) {
			String lines = Statics.readFromNotJarFile("/maps/" + mode + "/" + loc + "/" + loc + "N.txt");
			for (String line : lines.split("\n")) {
				ArrayList<String> stuff = new ArrayList<String>();// should
																	// have
																	// 5
				String currentS = "";
				for (int c2 = 0; c2 < line.length(); c2++) {

					if (line.charAt(c2) == ',') {
						stuff.add(currentS);
						currentS = "";

					} else {
						currentS += line.charAt(c2);
					}
				}
				if (currentS != "") {
					stuff.add(currentS);
				}
				if (stuff.size() > 0)
					try {
						int nX = Integer.parseInt(stuff.get(0));
						int nY = Integer.parseInt(stuff.get(1));
						String identity = stuff.get(2);

						switch (identity) {
						case NPC.WIZARD:
							npcs.add(new TowerWizard(nX, nY, owner, loc));
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
						case "Chest":
							npcs.add(new Chest(nX, nY, owner, loc, Items.translate(stuff.get(3))));
							break;
						case "RyoBoss2Start":
							npcs.add(new RyoBoss2Start(nX, nY, owner, loc));
							break;
						case "EnterFirstTimeWizTower":
							npcs.add(new EnterFirstTimeWizTower(nX, nY, owner, loc));
							break;
						case "TutorialWizard":
							npcs.add(new TutorialWizard(nX, nY, owner, loc));
							break;
						case "FirstCutscene":
							npcs.add(new FirstCutscene(nX, nY, owner, loc));
							break;
						case "WizardTowerSirCobalt":
							npcs.add(new WizardTowerSirCobalt(nX, nY, owner, loc));
							break;
						case "Peasant":
							npcs.add(new GenericPeasant(nX, nY, owner, loc, stuff.get(3)));
							break;
						case "OldPeasant":
							npcs.add(new OldPeasant(nX, nY, owner, loc));
							break;
						case "FutureGuard":
							npcs.add(new FutureGuard(nX, nY, owner, loc));
							break;
						case "FutureScientist":
							npcs.add(new FutureScientist(nX, nY, owner, loc));
							break;
						case "EnterFirstTimeWizTowerGiftShop":
							npcs.add(new EnterFirstTimeWizTowerGiftShop(nX, nY, owner, loc));
							break;
						case "TutorialWizard2":
							npcs.add(new TutorialWizard2(nX, nY, owner, loc));
							break;
						case "FirstTimeEnterBotanus":
							npcs.add(new FirstTimeEnterBotanus(nX, nY, owner, loc));
							break;
						case "FactoryEntryCutScene":
							npcs.add(new FactoryEntryCutScene(nX, nY, owner, loc));
							break;
						}

					} catch (IndexOutOfBoundsException ex) {
						ex.printStackTrace();

					}
			}

			// }
		} catch (Exception ex) {
			System.err.println("ERROR: could not load NPCs.");
ex.printStackTrace();
		}

		return npcs;
	}

	public ArrayList<Objects> loadObjects() {

		ArrayList<Objects> npcs = new ArrayList<Objects>();
		int count = 0;
		int spawnCount = 0;
		try {
			// ArrayList<String> strings = new ArrayList<String>();
			// File saveFile = new File(Statics.getBasedir() + "maps/" + mode +
			// "/" + loc + "/" + loc + "O.txt");
			// if (saveFile.exists()) {
			// BufferedReader reader = new BufferedReader(new
			// FileReader(saveFile));
			// String line;
			// while ((line = reader.readLine()) != null) {
			// strings.add(line);
			// }
			// reader.close();
			// for (int c = 0; c < strings.size(); c++) {
			String lines = Statics.readFromNotJarFile("/maps/" + mode + "/" + loc + "/" + loc + "O.txt");
			for (String line : lines.split("\n")) {
				ArrayList<String> stuff = new ArrayList<String>();// should
																	// have
																	// 5
				String currentS = "";
				for (int c2 = 0; c2 < line.length(); c2++) {

					if (line.charAt(c2) == ',') {
						stuff.add(currentS);
						currentS = "";

					} else {
						currentS += line.charAt(c2);
					}
				}
				if (currentS != "") {
					stuff.add(currentS);
				}
				if (stuff.size() > 0)
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
								npcs.add(new KeyCrystal(nX, nY, loc, owner, count));
								count++;
							} else if (val == -2)
								npcs.add(new RandSkinObject(nX, nY, loc, wall, owner));
							else if (val == -3) {
								if (spawnCount <= spawnNum){
									spawnPoint = new Point(-nX + OFF, -nY+OFFH);
									System.err.println((768-owner.getHeight()));
								}
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
								npcs.add(new PushCube(nX, nY, owner));
							} else if (val == -10) {
								npcs.add(new BombCube(nX, nY, owner));
							} else if (val == -11) {
								npcs.add(new CubeButtonMoneyGiver(nX, nY, owner));
							} else if (val == -12)
								npcs.add(new BigRedButton(nX, nY, owner));
							else if (val == -13)
								npcs.add(new Lamp(nX, nY, loc, owner, Integer.parseInt(stuff.get(3))));
							else if(val==-14)
								npcs.add(new StrangeBookTable(nX, nY, wall, owner,loc));
							else if(val==-15)
								npcs.add(new Gate(nX, nY, loc, wall, owner));
							else if(val==-16)
								npcs.add(new Trap(nX, nY, owner));
							else if(val==-17)
								npcs.add(new Trap2(nX,nY,owner));
							else if(val==-18)
								npcs.add(new Trap3(nX,nY,owner));
							else if(val==-19)
								npcs.add(new DestroySideToPlayerGate(nX, nY, loc, wall, owner));
							else if(val==-20)
								npcs.add(new BossActivator(nX, nY,owner));
							else if(val==-21)
								npcs.add(new WaveMaker(nX, nY, loc, owner));
							else if(val==-22)
								npcs.add(new MacaroniCage(nX, nY, this.loc, owner));
							else if(val==-23)
								npcs.add(new InvisibleWallObject(nX, nY, loc,stuff.get(3), owner));
							else if(val==-24)
								npcs.add(new TutorialWizardNext(nX, nY, owner));
							else if(val==-25)
								npcs.add(new FoldedBridge(nX, nY, owner));
							else if(val==-26)
								npcs.add(new TutorialWizardActivator(nX, nY, owner));
							else if(val==-27)
								npcs.add(new TutorialWizardNext2(nX, nY, owner));
							else if(val==-28)
								npcs.add(new FactoryBridge(nX, nY, owner));
							else if(val==-29)
								npcs.add(new TutorialWizard2Nexter(nX, nY, stuff.get(3), owner));
							else if(val==-30)
								npcs.add(new Tree(nX,nY,loc,owner));
							else if(val==-31)
								npcs.add(new Toilet(nX,nY,loc,owner));
							else if(val==-32)
								npcs.add(new Sink(nX,nY,loc,owner));
							else
								npcs.add(new MoneyObject(nX, nY, loc, owner, val));
						else if (stuff.get(5).equals("Null"))
							npcs.add(new CollectibleCharacter(nX, nY, loc, owner));
						else{
							npcs.add(new CollectibleObject(nX, nY, loc, wall, owner, Items.translate(stuff.get(5))));
						System.out.println(stuff.get(5));
						}
					} catch (IndexOutOfBoundsException ex) {
						ex.printStackTrace();

					}
			}

			// }a
		} catch (Exception ex) {
			System.err.println("ERROR: could not load objects.");
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
		// String tryLoc = Statics.getBasedir() + "maps/" + mode + "/" + loc +
		// "/" + loc + ".txt";
		TexturePack pack = TexturePack.GRASSY;
		// File map = new File(tryLoc);

		// if (map.exists()) {

		try {
			String line;
			// BufferedReader reader = new BufferedReader(new
			// FileReader(tryLoc));
			String lines = Statics.readFromNotJarFile("/maps/" + mode + "/" + loc + "/" + loc + ".txt");
			// for(String line:lines.split("\n")){
			if (lines.split("\n").length != 0) {
				line = lines.split("\n")[0];
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
				// reader.close();

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

			}
		} catch (Exception e) {
			System.err.println("ERROR: This map most likely doesn't exist.");
			JOptionPane.showMessageDialog(owner, "This map most likely doesn't exist. Leaving game.");
			System.exit(0);
			//e.printStackTrace();
			// }

		}

		return pack;
	}

	public static void setTime(Board set) {
		set.setDayNight(me.getTime());
	}

	public ArrayList<Portal> loadPortals() {
		ArrayList<Portal> portals = new ArrayList<Portal>();
		try {
			// ArrayList<String> strings = new ArrayList<String>();
			// File saveFile = new
			// File(StageBuilder.class.getProtectionDomain().getCodeSource().getLocation().getFile()
			// + "maps/" + mode + "/" + loc
			// + "/" + loc + "P.txt");
			// if (saveFile.exists()) {
			// BufferedReader reader = new BufferedReader(new
			// FileReader(saveFile));
			// String line;
			// while ((line = reader.readLine()) != null) {
			// strings.add(line);
			// }
			// reader.close();
			// for (int c = 0; c < strings.size(); c++) {
			String lines = Statics.readFromNotJarFile("/maps/" + mode + "/" + loc + "/" + loc + "P.txt");
			for (String line : lines.split("\n")) {
				ArrayList<String> stuff = new ArrayList<String>();// should
																	// have
																	// 5
				String currentS = "";
				for (int c2 = 0; c2 < line.length(); c2++) {

					if (line.charAt(c2) == ',') {
						stuff.add(currentS);
						currentS = "";

					} else {
						currentS += line.charAt(c2);
					}
				}

				if (currentS != "") {
					stuff.add(currentS);
				}
				if (stuff.size() > 0) {
					int enX = Integer.parseInt(stuff.get(0));
					int enY = Integer.parseInt(stuff.get(1));
					String area = stuff.get(2);
					String collectibleNum = stuff.get(3);
					String type = stuff.get(4);
					int spawnNum = Integer.parseInt(stuff.get(5));
					
					if (type.startsWith("normal"))
						portals.add(new Portal(enX, enY, owner, area, type, spawnNum));
					else if(type.startsWith("boss")){
						String type2 = stuff.size()>=7?stuff.get(6):"any";
						PlayerList p=new PlayerList(type2,owner);
portals.add(new BossPortal(enX, enY, owner, area, type, spawnNum,Integer.parseInt(collectibleNum), p));
					}
					else{
						String type2 = stuff.size() > 7 ? stuff.get(6) : "brown";
						portals.add(new Door(enX, enY, owner, area, type, type2, spawnNum,collectibleNum.startsWith("t")));
					}
				}
			}

			// }
		} catch (Exception ex) {
			System.err.println("ERROR: could not load Portals.");
		}

		return portals;
	}

	public String readWeather() {
		// String tryLoc =
		// StageBuilder.class.getProtectionDomain().getCodeSource().getLocation().getFile()
		// + "maps/" + mode + "/" + loc + "/" + loc
		// + ".txt";
		// File map = new File(tryLoc);

		// if (map.exists()) {

		try {
			// String line;
			// BufferedReader reader = new BufferedReader(new
			// FileReader(tryLoc));
			String lines = Statics.readFromNotJarFile("/maps/" + mode + "/" + loc + "/" + loc + ".txt");

			if (lines.split("\n").length != 0) {
				String weather = lines.split("\n")[0].split(",")[1];
				System.out.println("Weather: " + weather);
				if (weather.equals("none"))
					return null;
				else
					return weather;
			}
		} catch (Exception e) {
			// }
		}

		return null;
	}
}

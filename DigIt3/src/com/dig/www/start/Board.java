package com.dig.www.start;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Area;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import com.dig.www.MultiPlayer.ChatClient;
import com.dig.www.MultiPlayer.ChatServer;
import com.dig.www.MultiPlayer.IChatServer;
import com.dig.www.MultiPlayer.State.ActionState;
import com.dig.www.MultiPlayer.State.AddEnemy;
import com.dig.www.MultiPlayer.State.AttackState;
import com.dig.www.MultiPlayer.State.BlockState;
import com.dig.www.MultiPlayer.State.BreakCrystal;
import com.dig.www.MultiPlayer.State.DigPit;
import com.dig.www.MultiPlayer.State.EnemyState;
import com.dig.www.MultiPlayer.State.GameState;
import com.dig.www.MultiPlayer.State.MoneyState;
import com.dig.www.MultiPlayer.State.MoveObjectState;
import com.dig.www.MultiPlayer.State.NPCState;
import com.dig.www.MultiPlayer.State.ObjectPickUpState;
import com.dig.www.MultiPlayer.State.ObjectState;
import com.dig.www.MultiPlayer.State.PlayerState;
import com.dig.www.MultiPlayer.State.RemoveEnemy;
import com.dig.www.MultiPlayer.State.StartState;
import com.dig.www.MultiPlayer.State.SwitchState;
import com.dig.www.blocks.Block;
import com.dig.www.blocks.Block.Blocks;
import com.dig.www.blocks.CarpetBlock;
import com.dig.www.blocks.Door;
import com.dig.www.blocks.LiquidBlock;
import com.dig.www.blocks.Portal;
import com.dig.www.blocks.SpecialDoor;
import com.dig.www.blocks.StoneBlock;
import com.dig.www.blocks.TerrainBlock;
import com.dig.www.blocks.TexturePack;
import com.dig.www.blocks.WallBlock;
import com.dig.www.character.CharData;
import com.dig.www.character.Club;
import com.dig.www.character.Diamond;
import com.dig.www.character.FProjectile;
import com.dig.www.character.Field;
import com.dig.www.character.GameCharacter;
import com.dig.www.character.GameCharacter.Direction;
import com.dig.www.character.GameCharacter.Types;
import com.dig.www.character.Heart;
import com.dig.www.character.Inventory;
import com.dig.www.character.Items;
import com.dig.www.character.Macaroni;
import com.dig.www.character.Moves;
import com.dig.www.character.PathPoint;
import com.dig.www.character.Puddle;
import com.dig.www.character.Ryo;
import com.dig.www.character.Shield;
import com.dig.www.character.SirCobalt;
import com.dig.www.character.Spade;
import com.dig.www.character.Wizard;
import com.dig.www.enemies.Boss;
import com.dig.www.enemies.Enemy;
import com.dig.www.enemies.Projectile;
import com.dig.www.npc.Chest;
import com.dig.www.npc.NPC;
import com.dig.www.npc.TouchNPC;
import com.dig.www.objects.CheckPoint;
import com.dig.www.objects.Collectible;
import com.dig.www.objects.CollectibleCharacter;
import com.dig.www.objects.CollectibleObject;
import com.dig.www.objects.Dispenser;
import com.dig.www.objects.DropPoint;
import com.dig.www.objects.KeyCrystal;
import com.dig.www.objects.Light;
import com.dig.www.objects.Mirror;
import com.dig.www.objects.MoneyObject;
import com.dig.www.objects.Objects;
import com.dig.www.objects.PushCube;
import com.dig.www.objects.ThrownObject;
import com.dig.www.start.Switch.ActionMenu;
import com.dig.www.start.Switch.OrderMenu;
import com.dig.www.start.Switch.SwitchMenu;
import com.dig.www.util.Irregular;
import com.dig.www.util.Preferences;
import com.dig.www.util.Sprite;
import com.dig.www.util.StageBuilder;
import com.dig.www.util.Statics;
import com.dig.www.util.Time;
import com.dig.www.util.GameControllerRunnable;

public class Board extends MPanel implements ActionListener {

	/**
	 * 
	 */
	private SwitchMenu switchMenu;
	private ArrayList<String> actionStrings = new ArrayList<String>();
	private ArrayList<String> actionIcons = new ArrayList<String>();
	private int actionTimer;
	private static final int ACTIONMAX = 250;

	public void addAction(String string, String icon) {
		actionStrings.add(string);
		actionIcons.add(icon);
		actionTimer = 0;
	}

	private boolean lagPrevention = false;

	public boolean lagPre() {
		return lagPrevention;
	}

	public int mult() {
		if (lagPrevention)
			return 2;
		return 1;
	}

	private int sendInt = 0;
	private Board board = this;
	private int times;
	private int fps;
	private int fpsT;
	private long longTime;
	protected ArrayList<String> chats = new ArrayList<String>();
	protected ChatBox chatBox;
	private String passWord;
	private ArrayList<String> goneFriends = new ArrayList<String>();
	private static final long serialVersionUID = 1L;
	ArrayList<GameState> states = new ArrayList<GameState>();
	IChatServer theServer;
	GameState currentState;
	String mpName = "Server";

	ChatServer server;// maybe
	ChatClient me;// maybe
	/**
	 * 
	 */
	public Point pointedPoint;
	public int pointedPointType = -1;

	private int doorStateTimer = 0;
	private final int DOORSTATETMAX = 50;
	private String doorStateLev = "";
	private int spawnNum;
	private Point spawnLoc;
	private boolean corruptedWorld = false;

	public enum State {
		INGAME, PAUSED, QUIT, SHOP, LOADING, DEAD, NPC, DOOROPEN// ,SWITCHING;
	};

	public enum Weather {
		RAIN, FOG {

			public int special() {
				return 500;
			}
		},
		NORMAL, NONE, OBSCURE;

		public int special() {
			return 0;
		}

		public static Weather translate(String s) {
			if (s == null)
				return Weather.NONE;

			for (Weather w : Weather.values())
				if (w.toString().equalsIgnoreCase(s)) {
					System.out.println("Weather confirmed: " + w.toString());
					return w;
				}

			System.out.println("No weather.");
			return Weather.NONE;
		}
	}

	public enum DayNight {
		DAY, NIGHT, ANY;

		public static DayNight translate(String name) {
			for (DayNight d : DayNight.values())
				if (d.name().equalsIgnoreCase(name))
					return d;
			return DayNight.ANY;
		}
	}

	public static Preferences preferences;

	static {
		preferences = new Preferences();
	}

	public static final String DEFAULT = "Start";
	private Timer timer;
	private static final int NORMAL_TIMER = 15;
	private static final int LAG_TIMER = 32;
	private int timerWait = NORMAL_TIMER;
	protected String userName;
	protected String mode;
	protected String level;
	private GameCharacter character;
	protected ArrayList<GameCharacter> friends = new ArrayList<GameCharacter>();
	protected ArrayList<FProjectile> fP = new ArrayList<FProjectile>();
	protected ArrayList<Objects> objects = new ArrayList<Objects>();
	private ArrayList<Objects> movingObjects = new ArrayList<Objects>();
	private State state;
	// private boolean debug = false;

	private int deadTimer = 100;

	private CharData data;
	private ArrayList<Block> world = new ArrayList<Block>();
	private ArrayList<Block> wallList = new ArrayList<Block>();
	private ArrayList<NPC> npcs = new ArrayList<NPC>();
	private NPC current = null;
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private ArrayList<Enemy> onScreenEnemies = new ArrayList<Enemy>();
	private ArrayList<Portal> portals = new ArrayList<Portal>();

	private int scrollX = 0;
	private int scrollY = 0;
	private int spawnX;
	private int spawnY;
	public static final int DEFAULT_X = 325;
	public static final int DEFAULT_Y = 350;
	boolean levelChanged;
	private DigIt owner;

	private boolean switching = false;
	private TexturePack texturePack = TexturePack.GRASSY;

	private Weather weather = Weather.NORMAL;
	private int weatherTimer = 0;
	private DayNight dN = DayNight.NIGHT;
	private ArrayList<int[]> weatherList = new ArrayList<int[]>();

	private int startPoint = 0;
	private Time time;
	private String consumePath;
	private int consumeTimer;
	private boolean consumeStop;

	public void consume(String consumePath, int consumeTimer, boolean consumeStop) {
		this.consumePath = consumePath;
		this.consumeTimer = consumeTimer;
		this.consumeStop = consumeStop;
		if (consumeStop)
			character.stop();
	}

	public ArrayList<String> getGoneFriends() {
		return goneFriends;
	}

	public void heyIaddedAFriendBack(GameCharacter chara, String typeToString) {
		for (int c = 0; c < goneFriends.size(); c++) {
			if (goneFriends.get(c).equals(typeToString)) {
				chara.load(goneFriends.get(c));
				goneFriends.remove(c);
			}
		}
	}

	public int getStartPoint() {
		return startPoint;
	}

	public ArrayList<Block> getWorld() {
		return world;
	}

	public int getScrollX() {
		return scrollX;
	}

	public void setScrollX(int scrollX) {
		this.scrollX = scrollX;
	}

	public int getScrollY() {
		return scrollY;
	}

	public void setScrollY(int scrollY) {
		this.scrollY = scrollY;
	}

	public DigIt getOwner() {
		return owner;
	}

	public void setOwner(DigIt owner) {
		this.owner = owner;
	}

	// * _
	// */ \
	// * | Getters/setters for owner
	public Board(DigIt dM) {

		this.userName = null;
		// character = new Spade(Statics.BOARD_WIDTH / 2 - 50,
		// Statics.BOARD_HEIGHT / 2 - 50, this, true);
		// friends.clear();
		// friends.add(new Heart(Statics.BOARD_WIDTH / 2 + 150,
		// Statics.BOARD_HEIGHT / 2 - 50, this, false));
		// friends.add(new Diamond(Statics.BOARD_WIDTH / 2 + 150,
		// Statics.BOARD_HEIGHT / 2 + 50, this, false));
		// friends.add(new Club(Statics.BOARD_WIDTH / 2,
		// Statics.BOARD_HEIGHT / 2 + 150, this, false));
		// friends.add(new Macaroni(Statics.BOARD_WIDTH / 2,
		// Statics.BOARD_HEIGHT / 2 + 150, this, false));

		this.addMouseListener(new PersonalMouse());

		owner = dM;
		timer = new Timer(timerWait, this);
		time = new Time(this);

		owner.setFocusable(false);

		addKeyListener(new TAdapter());
		addMouseListener(new MouseL());
		setFocusable(true);

		setDoubleBuffered(true);
		state = State.LOADING;
		setSize(Statics.BOARD_WIDTH, Statics.BOARD_HEIGHT);
		this.mpName = JOptionPane.showInputDialog(this, "What would you like to be called?", System.getProperty("user.name"));
		try {

			me = new ChatClient(
					this,
					JOptionPane
							.showInputDialog(
									"What is the server's Host Code?\nThe server can find their Host Code by clicking Get Host Code in the Main Menu.\nThe Host Code below is your Host Code.",
									InetAddress.getLocalHost().getHostAddress()), mpName, JOptionPane.showInputDialog(
							"What is the server's password?\nNone is the default.", "None"));
		} catch (HeadlessException | RemoteException | AlreadyBoundException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// timer.start();
		// time.start();
		Collections.sort(friends);
	}

	public Board(DigIt dM, String mode, String name) {
		this.mode = mode;
		this.userName = name;
		if (mode.equals(Statics.MAIN))
			GameCharacter.storyInt = 0;
		this.addMouseListener(new PersonalMouse());

		owner = dM;
		timer = new Timer(timerWait, this);
		time = new Time(this);

		owner.setFocusable(false);

		addKeyListener(new TAdapter());
		addMouseListener(new MouseL());
		setFocusable(true);

		setDoubleBuffered(true);
		state = State.INGAME;
		setSize(Statics.BOARD_WIDTH, Statics.BOARD_HEIGHT);

		timer.start();
		time.start();
		Collections.sort(friends);
	}

	public void changeArea() {
		this.changeArea(-1);
	}

	public boolean isServer() {
		return server != null;
	}

	public void changeArea(int num) {
		pointedPoint = null;
		fP.clear();
		scrollX = 0;
		scrollY = 0;
		if (levelChanged) {
			if (character instanceof Heart)
				((Heart) character).end();
			else
				for (GameCharacter g : friends)
					if (g instanceof Heart)
						((Heart) g).end();
		}
		scrollX = 0;
		scrollY = 0;
		// TODO finish
		StageBuilder sB = StageBuilder.getInstance(mode, level, this, num);
		sB.changeState(mode, level, this, num);
		setTexturePack(sB.readText());
		world = sB.read();
		enemies = sB.loadEn();
		portals = sB.loadPortals();
		npcs = sB.loadNPC();
		objects = sB.loadObjects();

		StageBuilder.setTime(this);

		weather = Weather.translate(sB.readWeather());

		if (data != null)
			data.enterLevel(level);
		else {
			data = new CharData(level, this);
			data.enterLevel(level);
		}
		objects = data.filter(objects);
		npcs = data.filterNPC(npcs);
		portals = data.filterPortals(portals);
		for (Objects o : objects)
			if (o instanceof DropPoint)
				if (((DropPoint) o).hasDrop()) {
					npcs.add(new Chest(o.getX(), o.getY(), this, level, ((DropPoint) o).type()));
				}

		if (character == null) {
			System.err.println("Character is never intialized. Leaving game.");
			System.exit(0);
		}

		if (character.getType() == Types.SPADE) {
			((Spade) character).resetDirt();
		} else if (character.getType() == Types.DIAMOND) {
			((Diamond) character).newArea();
		} else if (character.getType() == Types.WIZARD)
			((Wizard) character).clearMagic();

		character.setX(Statics.BOARD_WIDTH / 2 - 50);
		character.setY(Statics.BOARD_HEIGHT / 2 - 50);
		for (int c = 0; c < friends.size(); c++) {
			friends.get(c).setDead(false);
			if (c < 3) {
				friends.get(c).setX(Statics.BOARD_WIDTH / 2 - 50 - 100 + (c * 100));
				friends.get(c).setY(Statics.BOARD_HEIGHT / 2 - 50 - 100);
			} else if (c == 3) {
				friends.get(c).setX(Statics.BOARD_WIDTH / 2 - 50 - 100);
				friends.get(c).setY(Statics.BOARD_HEIGHT / 2 - 50);
			} else if (c == 4) {
				friends.get(c).setX(Statics.BOARD_WIDTH / 2 - 50 + 100);
				friends.get(c).setY(Statics.BOARD_HEIGHT / 2 - 50);
			} else if (c < 8) {
				friends.get(c).setX(Statics.BOARD_WIDTH / 2 - 50 - 100 + ((c - 5) * 100));
				friends.get(c).setY(Statics.BOARD_HEIGHT / 2 - 50 + 100);
			} else {
				friends.get(c).setX(Statics.BOARD_WIDTH / 2 - 50);
				friends.get(c).setY(Statics.BOARD_HEIGHT / 2 - 50);
			}
			if (friends.get(c).getType() == Types.SPADE) {
				((Spade) friends.get(c)).resetDirt();
			} else if (friends.get(c).getType() == Types.DIAMOND) {
				((Diamond) friends.get(c)).newArea();
			} else if (friends.get(c).getType() == Types.WIZARD)
				((Wizard) friends.get(c)).clearMagic();

		}
		for (int c = 0; c < enemies.size(); c++) {
			enemies.get(c).resetImage(this);
		}

		wallList = new ArrayList<Block>();
		for (Block b : world) {

			b.initialAnimate(spawnX, spawnY);

			// Deals with line-of-sight
			if (b.getType() == Block.Blocks.WALL)
				wallList.add(b);
		}

		for (Portal p : portals)
			p.initialAnimate(spawnX, spawnY);

		for (Enemy e : enemies) {
			e.initialAnimate(spawnX, spawnY);
			e.setAlive(true);
			e.resetImage(this);
		}

		for (NPC n : npcs)
			n.initialAnimate(spawnX, spawnY);

		for (Objects n : objects)
			n.initialAnimate(spawnX, spawnY);

		// TODO lightspot
		// objects.add(new Lamp(character.getX() - 100, character.getY() - 500,
		// "images/objects/floweryLamp.png", this, 5));

		if (spawnLoc != null) {
			spawnLoc.x -= spawnX - Statics.BOARD_WIDTH / 2 - 50 + 100;
			spawnLoc.y -= spawnY - Statics.BOARD_HEIGHT / 2 - 50 + 100;
		}
		if (GameCharacter.getInventory().contains(Items.PLAINKEY))
			GameCharacter.getInventory().decrementItem(Items.PLAINKEY, GameCharacter.getInventory().getItemNum(Items.PLAINKEY));
		changeWeather();
		updateBackground();
		Statics.wipeColors();

		long freeMem = Runtime.getRuntime().freeMemory();
		System.gc();
		System.out.println("Before: " + freeMem + " After: " + Runtime.getRuntime().freeMemory());
		spawnNum = sB.getSpawnNum();
		save();
	}

	public void changeClientArea() {
		scrollX = 0;
		scrollY = 0;
		System.out.println("New Level");
		// this.level = "hauntedTest";
		preferences = new Preferences();
		GameCharacter.setInventory(new Inventory(this));
		pointedPoint = null;
		// fP.clear();
		scrollX = 0;
		scrollY = 0;
		if (levelChanged) {
			if (character instanceof Heart)
				((Heart) character).end();
			else
				for (GameCharacter g : friends)
					if (g instanceof Heart)
						((Heart) g).end();
		}
		StartState st = null;
		try {
			st = theServer.getStartState();
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// enemies.clear();
		world.clear();
		objects.clear();
		movingObjects.clear();
		enemies.clear();
		npcs.clear();
		fP.clear();

		texturePack = st.getTexture();
		GameCharacter.getInventory().setMoney(st.getMoney());
		for (BlockState b : st.getWorld()) {

			// world.add(new Block(b.getX(), b.getY(), Statics.DUMMY, this,
			// b.getB()));

			switch (b.getB()) {
			case CARPET:
				world.add(new CarpetBlock(b.getX(), b.getY(), this));
				break;
			case CRYSTAL:
				world.add(new StoneBlock(b.getX(), b.getY(), this, true));
				break;
			case DIRT:
				world.add(new TerrainBlock(b.getX(), b.getY(), this, Blocks.DIRT));
				break;
			case GROUND:
				world.add(new TerrainBlock(b.getX(), b.getY(), this, Blocks.GROUND));
				break;
			case LIQUID:
				world.add(new LiquidBlock(b.getX(), b.getY(), this));
				break;
			case PIT:
				world.add(new TerrainBlock(b.getX(), b.getY(), this, Blocks.PIT));
				break;
			case ROCK:
				world.add(new StoneBlock(b.getX(), b.getY(), this, false));
				break;
			case WALL:
				world.add(new WallBlock(b.getX(), b.getY(), this));
				break;
			default:
				System.err.println("Type " + b.getB() + " does not have a creation case. Please add a creation case for " + b.getB() + ".");
				world.add(new TerrainBlock(b.getX(), b.getY(), this, Blocks.GROUND));
				break;
			}
			if (!b.getInv())
				world.get(world.size() - 1).setVisible(false);
		}
		for (ObjectState o : st.getObjects()) {
			switch (o.getType()) {
			case NORMAL:
				System.out.println(o.getLoc());
				objects.add(new Objects(o.getX(), o.getY(), o.getLoc(), o.isWall(), this, o.getIdent()));
				break;
			case MONEY:
				objects.add(new MoneyObject(o.getX(), o.getY(), o.getLoc(), this, o.getI()));
				break;
			case CUBE:
				objects.add(new Objects(o.getX(), o.getY(), o.getLoc(), true, this, o.getIdent()));
				break;
			}
		}
		for (Enemy e : st.getEnemies()) {
			// switch(e.getType()){
			// case STAND:
			// enemies.add(new StandEnemy(e.getX(), e.getY(), e.getLoc(), this,
			// e.isFlying(), e.getHealth()));
			// }
			enemies.add(e);
			e.setOwner(this);
			e.setImage(e.newImage(e.getLoc()));
			e.setShadow(e.newShadow());
		}
		for (NPC n : st.getNPCs()) {
			npcs.add(n);
			n.setOwner(this);
			n.setImage(n.newImage(n.getLoc()));
			n.setShadow(n.newShadow(n.getLoc()));
		}
		// for(PlayerState p:st.getPlayers()){
		//
		// }
		// TODO finish
		// StageBuilder sB = StageBuilder.getInstance(mode, level, this, -1);
		// sB.changeState(mode, level, this, -1);
		// setTexturePack(sB.readText());
		// world = sB.read();
		// enemies = sB.loadEn();
		// portals = sB.loadPortals();
		// npcs = sB.loadNPC();
		// objects = sB.loadObjects();

		// StageBuilder.setTime(this);

		// weather = Weather.translate(sB.readWeather());

		if (data != null)
			data.enterLevel(level);
		else
			data = new CharData(level, this);

		objects = data.filter(objects);
		npcs = data.filterNPC(npcs);
		portals = data.filterPortals(portals);
		for (Objects o : objects)
			if (o instanceof DropPoint)
				if (((DropPoint) o).hasDrop()) {
					npcs.add(new Chest(o.getX(), o.getY(), this, level, ((DropPoint) o).type()));
				}
		if (character == null) {
			System.err.println("Character is never intialized. Leaving game.");
			System.exit(0);
		}

		if (character.getType() == Types.SPADE) {
			((Spade) character).resetDirt();
		} else if (character.getType() == Types.DIAMOND) {
			((Diamond) character).newArea();
		}

		character.setX(Statics.BOARD_WIDTH / 2 - 50);
		character.setY(Statics.BOARD_HEIGHT / 2 - 50);
		for (int c = 0; c < friends.size(); c++) {
			friends.get(c).setDead(false);
			if (c < 3) {
				friends.get(c).setX(Statics.BOARD_WIDTH / 2 - 50 - 100 + (c * 100));
				friends.get(c).setY(Statics.BOARD_HEIGHT / 2 - 50 - 100);
			} else if (c == 3) {
				friends.get(c).setX(Statics.BOARD_WIDTH / 2 - 50 - 100);
				friends.get(c).setY(Statics.BOARD_HEIGHT / 2 - 50);
			} else if (c == 4) {
				friends.get(c).setX(Statics.BOARD_WIDTH / 2 - 50 + 100);
				friends.get(c).setY(Statics.BOARD_HEIGHT / 2 - 50);
			} else if (c < 8) {
				friends.get(c).setX(Statics.BOARD_WIDTH / 2 - 50 - 100 + ((c - 5) * 100));
				friends.get(c).setY(Statics.BOARD_HEIGHT / 2 - 50 + 100);
			} else {
				friends.get(c).setX(Statics.BOARD_WIDTH / 2 - 50);
				friends.get(c).setY(Statics.BOARD_HEIGHT / 2 - 50);
			}
			if (friends.get(c).getType() == Types.SPADE) {
				((Spade) friends.get(c)).resetDirt();
			} else if (friends.get(c).getType() == Types.DIAMOND) {
				((Diamond) friends.get(c)).newArea();
			}

		}
		for (int c = 0; c < enemies.size(); c++) {
			enemies.get(c).resetImage(this);
		}

		wallList = new ArrayList<Block>();
		for (Block b : world) {

			b.initialAnimate(spawnX, spawnY);

			// Deals with line-of-sight
			if (b.getType() == Block.Blocks.WALL)
				wallList.add(b);
		}

		for (Portal p : portals)
			p.initialAnimate(spawnX, spawnY);

		for (Enemy e : enemies) {
			e.initialAnimate(spawnX, spawnY);
			e.setAlive(true);
			e.resetImage(this);
		}

		for (NPC n : npcs)
			n.initialAnimate(spawnX, spawnY);

		for (Objects n : objects)
			n.initialAnimate(spawnX, spawnY);

		// TODO lightspot
		// objects.add(new Lamp(character.getX() - 100, character.getY() - 500,
		// "images/objects/floweryLamp.png", this, 5));

		if (spawnLoc != null) {
			spawnLoc.x -= spawnX - Statics.BOARD_WIDTH / 2 - 50 + 100;
			spawnLoc.y -= spawnY - Statics.BOARD_HEIGHT / 2 - 50 + 100;
		}
		spawnLoc = st.getSpawnLoc();
		changeWeather();
		updateBackground();
		Statics.wipeColors();

		long freeMem = Runtime.getRuntime().freeMemory();
		System.gc();
		System.out.println("Before: " + freeMem + " After: " + Runtime.getRuntime().freeMemory());
		// spawnNum = sB.getSpawnNum();
		// save();
		longTime = System.currentTimeMillis();
		fpsT = 0;
	}

	protected boolean fogCompute(int x, int y) {
		return Statics.dist(x, y, character.getX(), character.getY()) <= Weather.FOG.special()
				&& (y > Statics.BLOCK_HEIGHT && y < Statics.BOARD_HEIGHT - Statics.BLOCK_HEIGHT);
	}

	public void paint(Graphics g) {
		super.paint(g);

		// TODO work on this stuff
		updateBackground();

		Graphics2D g2d = (Graphics2D) g;

		switch (state) {

		case NPC:
		case INGAME:
		case DOOROPEN:
			// case SWITCHING:
			// Tag boolean part of line-of-sight
			boolean tag = true;
			int i;
			Enemy e;
			Polygon poly;
			Block b;

			// World draw
			for (i = startPoint; i < world.size(); i++) {
				b = world.get(i);
				if (b.isOnScreen() && b.isVisible())
					switch (weather) {
					case FOG:
						if ((!b.canSee() && !character.isDead()) || !fogCompute(b.getX(), b.getY()))
							break;

						if (b.traversable())
							b.draw(g2d);

					default:
						b.draw(g2d);
						break;
					}
			}

			// Enemy draw
			for (i = 0; i < enemies.size(); i++) {
				if (enemies.get(i).isOnScreen() || enemies.get(i) instanceof Boss) {

					e = enemies.get(i);
					// Line-of-sight mechanics
					int[] xs = { e.getMidX() - 10, character.getMidX() - 10, character.getMidX() + 10, e.getMidX() + 10 };
					int[] ys = { e.getMidY() - 10, character.getMidY() - 10, character.getMidY() + 10, e.getMidY() + 10 };
					poly = new Polygon(xs, ys, xs.length);

					for (int x = 0; x < wallList.size(); x++) {
						if (wallList.get(x).isOnScreen() && poly.intersects(wallList.get(x).getBounds())) {
							tag = false;
							break;
						}

						tag = true;
					}
					// end of that code
					if (tag || e instanceof Boss || character.isDead())
						switch (weather) {

						case FOG:
							if (!fogCompute(e.getX(), e.getY()) && !(e instanceof Boss)) {
								g2d.drawImage(e.getShadow(), e.getX(), e.getY(), this);
								break;
							}

						default:
							enemies.get(i).draw(g2d);
						}
				}
			}

			// FProjectile draw
			FProjectile p;
			for (i = 0; i < fP.size(); i++) {

				if (fP.get(i).isOnScreen()) {

					p = fP.get(i);
					if (!(fP.get(i) instanceof Field)) {
						// Line-of-sight mechanics
						int[] xs = { p.getMidX() - 10, character.getMidX() - 10, character.getMidX() + 10, p.getMidX() + 10 };
						int[] ys = { p.getMidY() - 10, character.getMidY() - 10, character.getMidY() + 10, p.getMidY() + 10 };
						poly = new Polygon(xs, ys, xs.length);

						for (int x = 0; x < wallList.size(); x++) {
							if (wallList.get(x).isOnScreen() && poly.intersects(wallList.get(x).getBounds())) {
								tag = false;
								break;
							}

							tag = true;
						}
						// end of that code
					} else
						tag = true;

					if (tag || character.isDead())
						p.draw(g2d);
				}
			}

			// Objects draw
			for (Objects obj : objects)

				if (obj.isOnScreen()) {
					// Line-of-sight mechanics
					int[] xs = { obj.getMidX() - 10, character.getMidX() - 10, character.getMidX() + 10, obj.getMidX() + 10 };
					int[] ys = { obj.getMidY() - 10, character.getMidY() - 10, character.getMidY() + 10, obj.getMidY() + 10 };
					poly = new Polygon(xs, ys, xs.length);

					for (int x = 0; x < wallList.size(); x++) {
						if (wallList.get(x).isOnScreen() && poly.intersects(wallList.get(x).getBounds())) {
							tag = false;
							break;
						}

						tag = true;
					}
					// end of that code
					if (tag || character.isDead())
						switch (weather) {
						case FOG:
							if (!fogCompute(obj.getX(), obj.getY())) {

								if (obj instanceof Mirror)
									((Mirror) obj).drawLight(g2d);

								g2d.drawImage(obj.getShadow(), obj.getX(), obj.getY(), this);
								break;
							}
						default:
							obj.draw(g2d);
						}
				}

			// Portal draw
			for (Portal p2 : portals)
				if (p2.isOnScreen()) {
					// Line-of-sight mechanics
					int[] xs = { p2.getMidX() - 10, character.getMidX() - 10, character.getMidX() + 10, p2.getMidX() + 10 };
					int[] ys = { p2.getMidY() - 10, character.getMidY() - 10, character.getMidY() + 10, p2.getMidY() + 10 };
					poly = new Polygon(xs, ys, xs.length);

					for (int x = 0; x < wallList.size(); x++) {
						if (wallList.get(x).isOnScreen() && poly.intersects(wallList.get(x).getBounds())) {
							tag = false;
							break;
						}

						tag = true;
					}
					// end of that code

					if (tag || character.isDead())
						p2.draw(g2d);
				}

			// NPC draw
			for (NPC npc : npcs)
				if (npc.isOnScreen()) {
					// Line-of-sight mechanics
					int[] xs = { npc.getMidX() - 10, character.getMidX() - 10, character.getMidX() + 10, npc.getMidX() + 10 };
					int[] ys = { npc.getMidY() - 10, character.getMidY() - 10, character.getMidY() + 10, npc.getMidY() + 10 };
					poly = new Polygon(xs, ys, xs.length);

					for (int x = 0; x < wallList.size(); x++) {
						if (wallList.get(x).isOnScreen() && poly.intersects(wallList.get(x).getBounds())) {
							tag = false;
							break;
						}

						tag = true;
					}
					// end of that code

					if (tag || character.isDead())
						switch (weather) {
						case FOG:
							if (!fogCompute(npc.getX(), npc.getY())) {
								g2d.drawImage(npc.getShadow(), npc.getX(), npc.getY(), this);
								break;
							}
						default:
							npc.draw(g2d);
						}
				}

			// g2d.setColor(Color.ORANGE);
			// //boolean back=false;
			//
			// int roundX=(int)Math.ceil((character.getX()+40)/100);
			// int modX=Math.abs((int)((world.get(0).getX())%100));
			// if(modX<11&&(world.get(0).getX()<0)){
			// roundX--;
			// }
			// roundX*=100;
			// g2d.fillRect(roundX
			// +(world.get(0).getX()<0?100:0)+
			// ((world.get(0).getX()<0?-1:1)*Math.abs(world.get(0).getX()%100))
			// ,
			// ((int)((character.getY()+30)/100))*100
			// +(world.get(0).getY()%100)+100//-11
			// , 100, 100)
			// ;
			if (pointedPoint != null) {
				if (pointedPointType == -1)
					g2d.drawImage(DigIt.lib.checkLibrary("/images/pointed/go.png"), (int) pointedPoint.getX() - 50, (int) pointedPoint.getY() - 50,
							this);
				else
					g2d.drawImage(DigIt.lib.checkLibrary("/images/pointed/attack.png"), (int) pointedPoint.getX() - 50,
							(int) pointedPoint.getY() - 50, this);

			}
			for (int c = 0; c < friends.size(); c++) {

				// g2d.setColor(Color.GREEN);
				// //
				// g2d.fillRect(((int)Math.floor((character.getX())/100))*100+(world.get(0).getX()%100)+100
				// // ,
				// // ((int)((character.getY()+40)/100))*100
				// // +(world.get(0).getY()%100)+100-11
				// // , 100, 100)
				// int roundX2=(int)Math.ceil((character.getX()+40)/100);
				// int modX2=Math.abs((int)((world.get(0).getX())%100));
				// if(modX2<11&&(world.get(0).getX()<0)){
				// roundX2--;
				// }
				// roundX2*=100;
				// g2d.fillRect(roundX2
				// +(world.get(0).getX()<0?100:0)+
				// ((world.get(0).getX()<0?-1:1)*Math.abs(world.get(0).getX()%100))
				// ,
				// ((int)((character.getY()+30)/100))*100
				// +(world.get(0).getY()%100)+100//-11
				// , 100, 100)
				// ;
				friends.get(c).draw(g2d);
				// if (character.getPPath() != null) {
				//
				//
				// for (int c =character.getPPath().getPoints().size()-1 ; c >=
				// 0; c--) {
				// g2d.setColor(new Color(255, 255, 0));
				// if (c == character.getPPath().getPoints().size()-1)
				// g2d.fillRect(character.getPPath().getPoint(c).x,
				// character.getPPath().getPoint(c).y, 10, 10);
				// else
				// g2d.drawLine(character.getPPath().getPoint(c).x,
				// character.getPPath().getPoint(c).y,
				// character.getPPath().getPoint(c+1).x,
				// character.getPPath().getPoint(c+1).y);
				// g2d.setColor(new Color(255, 0, 0));
				// g2d.drawString(""+c, character.getPPath().getPoint(c).x,
				// character.getPPath().getPoint(c).y);
				//
				// // else
				// // g2d.drawString("" +c, character.getPPath().getPoint(c).x,
				// // character.getPPath().getPoint(c).y);
				// }
				// }
			}
			if (character != null)
				character.draw(g2d);
			if (consumeTimer > 0) {
				g2d.drawImage(Statics.newImage(consumePath), character.getX() + character.getEatX(), character.getY() + character.getEatY(), 50, 50,
						this);
				if (character.getDirection() == Direction.UP)
					g2d.drawImage(character.getImage(), character.getX(), character.getY(), this);
			}// g2d.setColor(Color.BLUE);
				// g2d.fillRect(character.getX()+40, character.getY()+40, 5, 5);
			time.draw(g2d);
			g2d.setColor(Color.WHITE);
			g2d.setFont(new Font(Statics.FONT, Font.PLAIN, 25));
			int startX = Statics.BOARD_WIDTH - 250;
			int startY = (me != null || server != null) ? 135 : 10;
			if (Statics.MAC)
				startY += 23;
			// System.out.println(actionStrings.size());
			for (int c = 0; c < actionStrings.size(); c++) {
				g2d.drawImage(new ImageIcon(Statics.newImage(actionIcons.get(c))).getImage(), startX, startY + (c * 60), 50, 50, this);
				g2d.drawString(actionStrings.get(c), startX + 55, startY + 35 + (c * 60));

			}
			if (actionStrings.size() > 0 && state == State.INGAME) {
				if (actionTimer > ACTIONMAX - (actionStrings.size() - 1)) {
					actionStrings.remove(0);
					actionIcons.remove(0);
					actionTimer = 0;
				} else
					actionTimer += mult();
			}

			switch (weather) {
			case RAIN:

				g2d.setStroke(new BasicStroke(3));
				g2d.setColor(Statics.LIGHT_BLUE);

				if (state != State.NPC)
					while (weatherList.size() > 0)
						weatherList.remove(0);

				int x2;
				int y2;
				for (int runs = 0; runs < Statics.RAND.nextInt(10) + 5; runs++) {

					if (state != State.NPC) {
						x2 = Statics.RAND.nextInt(Statics.BOARD_WIDTH);
						y2 = Statics.RAND.nextInt(Statics.BOARD_HEIGHT);
						weatherList.add(new int[] { x2, y2 });
					} else if (runs < weatherList.size()) {
						x2 = weatherList.get(runs)[0];
						y2 = weatherList.get(runs)[1];
					} else {
						x2 = -100;
						y2 = -100;
					}
					g2d.drawLine(x2, y2, x2 + 5, y2 + 10);
				}
				break;

			case OBSCURE:
				switch (texturePack) {
				case DESERT:
					g2d.setColor(Statics.LIGHT_OFF_TAN);

					if (state != State.NPC)
						while (weatherList.size() > 0)
							weatherList.remove(0);

					for (int runs = 0; runs < Statics.RAND.nextInt(500) + 100; runs++) {
						if (state != State.NPC) {
							x2 = Statics.RAND.nextInt(Statics.BOARD_WIDTH);
							y2 = Statics.RAND.nextInt(Statics.BOARD_HEIGHT);
							weatherList.add(new int[] { x2, y2 });
						} else if (runs < weatherList.size()) {
							x2 = weatherList.get(runs)[0];
							y2 = weatherList.get(runs)[1];
						} else {
							x2 = -100;
							y2 = -100;
						}
						g2d.fillRect(x2, y2, 10, 10);
					}
					break;

				default:

					if (weatherList.isEmpty())
						for (int i2 = 0; i2 < 700; i2++) {
							weatherList
									.add(new int[] { Statics.RAND.nextInt(Statics.BOARD_WIDTH - 5), Statics.RAND.nextInt(Statics.BOARD_HEIGHT - 5) });
						}

					if (weatherList.size() < 1000 && state != State.NPC)
						for (int i2 = 0; i2 < Statics.RAND.nextInt(5) + 1; i2++) {
							weatherList.add(new int[] { Statics.RAND.nextInt(Statics.BOARD_WIDTH - 5), 0 });
						}

					switch (time.getGeneralTime()) {
					case Time.SUNRISE:
						g2d.setColor(Statics.sunriseColor(Color.lightGray, time.getTime()));
						break;
					case Time.SUNSET:
						g2d.setColor(Statics.sunsetColor(Color.lightGray, time.getTime()));
						break;
					case Time.NIGHT:
						g2d.setColor(Statics.darkenColor(Color.lightGray));
						break;
					case Time.DAY:
					default:
						g2d.setColor(Color.white);
						break;

					}

					g2d.setStroke(new BasicStroke());
					for (int runs = 0; runs < weatherList.size(); runs++) {
						x2 = weatherList.get(runs)[0];
						y2 = weatherList.get(runs)[1];
						g2d.fillRect(x2, y2, 5, 5);

						if (time.getGeneralTime() != Time.NIGHT) {
							g2d.setColor(Color.darkGray);
							g2d.drawRect(x2, y2, 5, 5);
							switch (time.getGeneralTime()) {
							case Time.SUNRISE:
								g2d.setColor(Statics.sunriseColor(Color.lightGray, time.getTime()));
								break;
							case Time.SUNSET:
								g2d.setColor(Statics.sunsetColor(Color.lightGray, time.getTime()));
								break;
							case Time.DAY:
							default:
								g2d.setColor(Color.white);
								break;
							}
						}

						if (state != State.NPC) {
							weatherList.remove(runs);

							if (x2 >= 0 && x2 <= Statics.BOARD_WIDTH && y2 <= Statics.BOARD_HEIGHT) {
								x2 += (Statics.RAND.nextBoolean() ? -3 : 3);
								y2 += 5;
								weatherList.add(runs, new int[] { x2, y2 });
							} else
								runs--;
						}
					}
					break;
				}

			default:
				break;
			}

			if (state == State.NPC)
				current.drawOption(g2d);

			break;

		case PAUSED:
			if (GameCharacter.getInventory() != null)
				GameCharacter.getInventory().draw(g2d);
			break;

		case DEAD:
			g2d.setColor(Color.BLACK);
			g2d.fill(getScreen());

			g2d.setColor(Color.RED);
			g2d.setFont(Statics.MENU);
			g2d.drawString("GAME OVER", getWidth() / 3, getHeight() / 3);
			break;
		case LOADING:
			g2d.setColor(Color.ORANGE);
			g2d.fill(getScreen());
			g2d.drawImage(Statics.newImage("images/Loading.gif"), Statics.BOARD_WIDTH - 100, Statics.BOARD_HEIGHT - 100, this);
			// g2d.drawImage(new
			// ImageIcon(getClass().getResource("images/icon.png")).getImage(),
			// 0, 0, this);
			break;
		case QUIT:
			break;
		case SHOP:
			break;
		default:
			break;

		// case QUIT:
		// g2d.setColor(Color.BLACK);
		// g2d.fill(getScreen());
		//
		// g2d.setColor(Color.RED);
		// g2d.setFont(Statics.WARNING);
		// g2d.drawString("", 100, getHeight() / 3);
		// break;
		}
		g2d.setFont(new Font(Statics.FONT, Font.PLAIN, 15));
		g2d.setColor(Color.WHITE);
		g2d.drawString("FPS:" + 1000 / (fps == 0 ? 1 : fps) + ":" + fps, 3, 170 + (Statics.MAC ? 23 : 0));
		if (corruptedWorld)
			drawCorruptedBorder(g2d);
		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	// String decision;
	public void setCharacter(GameCharacter chara) {
		character = chara;
	}

	public void openSwitchDialogue() {
		switching = false;
		if (actionMenu == null && switchMenu == null && orderMenu == null) {
			character.releaseAll();
			character.stop();
			new ActionMenu(this);
		} else {
			if (actionMenu != null) {
				actionMenu.dispose();
				actionMenu = null;
			}
			if (orderMenu != null) {
				orderMenu.dispose();
				orderMenu = null;
			}
			if (switchMenu != null) {
				switchMenu.dispose();
				switchMenu = null;
			}

		}
		// char[] names = {'S', 'C', 'D', 'H'};
		// scrollX *= -2;
		// scrollY *= -2;
		// reAnimate();
		// repaint();

		// timer.stop();
		// time.pause();

		// character.stop();
		// scrollX = 0;
		// scrollY = 0;
		// Thread t = new Thread(new Runnable() {
		// public void run() {
		// // state=State.SWITCHING;
		// // JOptionPane.showMessageDialog(null, "Hello");
		//
		// decision = ((String) JOptionPane.showInputDialog(board,
		// (character.isDead() ? "Your current character has been defeated.\n" :
		// "")
		// + "Please select a character: ", DigIt.NAME,
		// JOptionPane.PLAIN_MESSAGE, Statics.ICON, getCharacters(), null));
		//
		// if (decision == null) {
		// // timer.restart();
		// // time.resume();
		// if (character.isDead()) {
		// openSwitchDialogue();
		// }
		// return;
		// }
		//
		// if (!decision.equals(character.getType().charName())) {
		// character.releaseAll();
		// if (currentState != null)
		// currentState.getActions().add(new
		// SwitchState(character.getType().charName(), decision));
		// GameCharacter current = character;
		// int friendNum = getFriend(decision);
		// friends.get(friendNum).releaseAll();
		// character = friends.get(friendNum);
		// friends.set(friendNum, current);
		// character.setPlayer(true);
		// friends.get(friendNum).setPlayer(false);
		// character.stop();
		// scroll(Statics.BOARD_WIDTH / 2 - 50 - character.getX(), (int)
		// Statics.BOARD_HEIGHT / 2 - 50 - character.getY());
		// Collections.sort(friends);
		// }
		// // state=State.INGAME;
		// }
		// });
		// t.start();

		// timer.restart();
		// time.resume();
		// decision = null;
	}

	public void setSwitchingMenu(SwitchMenu switchM) {
		this.switchMenu = switchM;
	}

	public String[] getCharacters() {
		ArrayList<GameCharacter> friends = getAliveFriends();
		ArrayList<String> s0 = new ArrayList<String>();

		for (GameCharacter friend : friends)
			s0.add(friend.getType().charName());
		//
		String[] s1 = new String[friends.size()];
		boolean clark = false;
		boolean club = false;
		boolean heart = false;
		boolean diamond = false;
		boolean sirCobalt = false;
		boolean wizard = false;
		boolean kepler = false;
		boolean macaroni = false;
		boolean ryo = false;
		for (String a : s0) {
			if (a.equals(GameCharacter.Types.SPADE.charName()))
				clark = true;
			else if (a.equals(GameCharacter.Types.CLUB.charName()))
				club = true;
			else if (a.equals(GameCharacter.Types.HEART.charName()))
				heart = true;
			else if (a.equals(GameCharacter.Types.DIAMOND.charName()))
				diamond = true;
			else if (a.equals(GameCharacter.Types.SIR_COBALT.charName()))
				sirCobalt = true;
			else if (a.equals(GameCharacter.Types.WIZARD.charName()))
				wizard = true;
			else if (a.equals(GameCharacter.Types.RYO.charName()))
				ryo = true;
			// else if(a.equals(GameCharacter.Types.KEPLER.charName()))
			// kepler=true;
			else if (a.equals(GameCharacter.Types.MACARONI.charName()))
				macaroni = true;
		}
		int c = 0;
		if (clark) {
			s1[c] = GameCharacter.Types.SPADE.charName();
			c++;
		}
		if (club) {
			s1[c] = GameCharacter.Types.CLUB.charName();
			c++;
		}
		if (heart) {
			s1[c] = GameCharacter.Types.HEART.charName();
			c++;
		}
		if (diamond) {
			s1[c] = GameCharacter.Types.DIAMOND.charName();
			c++;
		}
		if (sirCobalt) {
			s1[c] = GameCharacter.Types.SIR_COBALT.charName();
			c++;
		}
		if (wizard) {
			s1[c] = GameCharacter.Types.WIZARD.charName();
			c++;
		}
		if (ryo) {
			s1[c] = GameCharacter.Types.RYO.charName();
			c++;
		}
		// if(kepler){
		// s1[c]=GameCharacter.Types.KEPLER.charName();
		// c++;
		// }
		if (macaroni) {
			s1[c] = GameCharacter.Types.MACARONI.charName();
			c++;
		}
		return s1;
	}

	public void scroll(int x, int y) {
		character.setX(character.getX() + x);
		character.setY(character.getY() + y);

		for (Block b : world) {
			b.setX(b.getX() + x);
			b.setY(b.getY() + y);
		}
		if (pointedPoint != null)
			pointedPoint.setLocation(pointedPoint.getX() + x, pointedPoint.getY() + y);

		if (spawnLoc != null)
			spawnLoc.setLocation(spawnLoc.getX() + x, spawnLoc.getY() + y);

		for (GameCharacter b : friends) {
			b.setX(b.getX() + x);
			b.setY(b.getY() + y);
			if (b.getPPath() != null)
				for (PathPoint p : b.getPPath().getPoints()) {
					p.setLocation(p.getX() + x, p.getY() + y);
				}
		}
		for (Enemy b : enemies) {
			b.setX(b.getX() + x);
			b.setY(b.getY() + y);
			b.doScroll(x, y);
		}
		for (FProjectile b : fP) {
			b.setX(b.getX() + x);
			b.setY(b.getY() + y);
		}
		for (Portal b : portals) {
			b.setX(b.getX() + x);
			b.setY(b.getY() + y);
		}

		for (NPC b : npcs) {
			b.setX(b.getX() + x);
			b.setY(b.getY() + y);
			b.doScroll(x, y);
		}
		for (Objects b : objects) {
			b.setX(b.getX() + x);
			b.setY(b.getY() + y);
			b.doScroll(x, y);
		}
	}

	public int getFriend(String decision) {
		for (int c = 0; c < friends.size(); c++) {
			if (friends.get(c).getType().charName().equals(decision)) {
				return c;
			}
		}
		return 0;
	}

	public void actionPerformed(ActionEvent e) {

		corruptedWorld = false;
		switch (state) {

		case INGAME:

			character.animate();
			if (consumeTimer > 0) {
				consumeTimer -= mult();
				if (consumeStop)
					character.stop();
			}
			boolean notMe = me == null;
			for (GameCharacter character : friends) {
				character.animate();
			}
			onScreenEnemies.clear();
			for (int i = 0; i < enemies.size(); i++) {

				if (notMe) {
					enemies.get(i).animate();
					if (!enemies.get(i).isAlive()) {
						enemies.remove(i);
						if (isServer()) {
							currentState.getActions().add(new RemoveEnemy(i));
						}
						i--;
						continue;
					}
				} else
					enemies.get(i).basicAnimate();
				Shape ebounds = enemies.get(i) instanceof Irregular ? ((Irregular) enemies.get(i)).getIrregularBounds() : enemies.get(i).getBounds();
				if (ebounds.intersects(getScreen())) {
					enemies.get(i).setOnScreen(true);
					onScreenEnemies.add(enemies.get(i));
				} else
					enemies.get(i).setOnScreen(false);
				// /\
				// || Nightmare Fuel
			}
			if (pointedPoint != null) {
				pointedPoint.x += scrollX;
				pointedPoint.y += scrollY;
			}
			if (spawnLoc != null) {
				spawnLoc.x += scrollX;
				spawnLoc.y += scrollY;
			}
			for (int i = 0; i < fP.size(); i++) {
				if (fP.get(i).isDead()) {
					fP.remove(i);
					i--;
					continue;
				}
				fP.get(i).animate();
				// if (fP.get(i).getMove() == Moves.CHAIN) {
				// if (fP.get(i).getCharNum() == -2) {
				// fP.add(new FProjectile(fP.get(i).getD() - 180, fP
				// .get(i).getX(), fP.get(i).getY(), fP.get(i)
				// .getSpeed(), fP.get(i).getMaker(), fP
				// .get(i).getLoc(), fP.get(i).getOwner(),
				// Moves.CHAIN, -1, false));
				// fP.remove(i);
				// } else {
				// fP.get(i).setCharNum(-1);
				// fP.get(i).basicAnimate();
				// }
				// } else {
				// fP.remove(i);
				// i--;
				// continue;
				// }
				//
				// } else if (fP.get(i).getCharNum() != -2) {
				// GameCharacter chara;
				// int charNum = fP.get(i).getCharNum();
				// if (charNum == -1) {
				// chara = character;
				// } else {
				// chara = friends.get(charNum);
				//
				// }
				// if (fP.get(i)
				// .getBounds()
				// .contains(
				// new Point(chara.getMidX(), chara.getMidY()))) {
				// fP.remove(i);
				// i--;
				// continue;
				// }
				// }
				//
				// fP.get(i).animate();
				// fP.get(i).setOnScreen(
				// fP.get(i).getBounds().intersects(getScreen()));
				// /\
				// || Nightmare Fuel
			}

			if (switching)
				openSwitchDialogue();

			if (weather != Weather.NORMAL && weatherTimer <= 0) {
				if (Statics.RAND.nextInt(100) == 0)
					switch (weather) {
					case RAIN:
						weatherTimer = Statics.RAND.nextInt(10) + 5;
						setBackground(getTextureBack());
						break;
					default:
						break;
					}
			} else if (weatherTimer > 0) {
				weatherTimer--;
				if (weatherTimer == 0)
					switch (weather) {
					case RAIN:
						setBackground(Statics.darkenColor(getTextureBack()));
						break;
					default:
						break;
					}
			}

			setCharacterStates(character.getCollisionBounds());
			repaint();

			for (int c = 0; c < friends.size(); c++) {
				for (int c2 = 0; c2 < friends.size(); c2++) {
					if (c == c2) {

					} else {
						if (!friends.get(c).getWallBound() && !friends.get(c2).getWallBound()) {

							if (friends.get(c).getBounds().intersects(friends.get(c2).getBounds()) && !friends.get(c2).isPlayer()) {
								friends.get(c).collision(friends.get(c2), true);
							}
						}
					}
				}
			}
			break;

		case DEAD:
			deadTimer--;
			if (deadTimer == 0) {
				GameCharacter.setLevel(0);
				GameCharacter.setXP(0);
				owner.quit();
			}
			repaint();
			break;
		case DOOROPEN:
			if (me != null) {
				state = State.INGAME;
				break;
			}
			if (doorStateTimer <= 0) {

				timer.stop();
				time.pause();
				level = doorStateLev;
				changeArea(spawnNum);
				setState(State.INGAME);
				timer.restart();
				time.resume();
			} else
				doorStateTimer--;
			break;
		default:
			break;
		}
		if (switchMenu != null) {
			switchMenu.updateList();
		}
		// character.setMpName(null);
		// for(int c=0;c<friends.size();c++)
		// friends.get(c).setMpName(null);
		if (server != null) {

			try {
				Block b = world.get(0);
				for (int s = 0; s < states.size(); s++) {
					// if(states.get(s)==null||states.get(s).getPlayerStates()==null)
					// System.out.println("null");
					// else
					for (PlayerState playerState : states.get(s).getPlayerStates()) {
						for (GameCharacter friend : friends) {
							if (playerState.isPlayer() && friend.getType().toString().equals(playerState.getTypeToString())) {
								friend.setX(playerState.getX() + b.getX());
								friend.setY(playerState.getY() + b.getY());
								friend.setPlayer(true);
								friend.setDirection(playerState.getDir());
								friend.setImage(friend.newImage(playerState.getS()));
								friend.setMpName(playerState.getMpName());
								friend.setHealth(playerState.getHealth());
								friend.setEnergy(playerState.getEnergy());
								friend.setDire(playerState.getDire());
								friend.setDead(playerState.isDead());
								// friend.setActing(playerState.getAttackNum(),playerState.getAttackTimer());
							}
						}
					}
					if (// states.get(s)!=null&&states.get(s).getActions()!=null
						// &&
					!states.get(s).isServer())
						for (ActionState actionState : states.get(s).getActions()) {
							switch (actionState.getActionType()) {
							case SWITCH:
								SwitchState realState = (SwitchState) actionState;
								for (GameCharacter chara : friends) {
									if (chara.getType().charName().equals(realState.getFrom())) {
										chara.setPlayer(false);
										chara.setMpName(null);
										chara.setWaiting(false);
									}
								}
								break;
							case MONEY:
								MoneyState realState2 = (MoneyState) actionState;
								GameCharacter.getInventory().addMoney(realState2.getVal());
								Statics.playSound(this, "weapons/whop.wav");
								try {
									objects.remove(realState2.getI());
								} catch (Exception ex) {
									ex.printStackTrace();
								}
								break;
							case BREAK:
								BreakCrystal breakC = (BreakCrystal) actionState;
								// if(world.get(breakC.getI()).getType()==Blocks.CRYSTAL)
								System.out.println(world.get(breakC.getI()).getType());
								world.get(breakC.getI()).doType(Blocks.ROCK);
								Statics.playSound(this, "blocks/shatter.wav");
								break;
							case DIG:
								BreakCrystal digC = (BreakCrystal) actionState;
								// if(world.get(breakC.getI()).getType()==Blocks.CRYSTAL)
								world.get(digC.getI()).digDo();
								break;
							case ATTACK:
								AttackState attack = (AttackState) actionState;
								for (int c = 0; c < friends.size(); c++)
									if (friends.get(c).getType().toString().equals(attack.getCharName())) {
										// enemies.get(attack.getI()).interact(attack.getMove(),
										// friends.get(c), attack.isFromP());

										friends.get(c).clientAttack(attack.getAttackNum());
										break;
									}
								break;
							case ADDEN:
								break;
							case MOVE:
								break;
							case PICKUP:
								break;
							case REMOVEENN:
								break;
							default:
								break;

							}
						}
				}
				states.clear();
				if (sendInt <= 0) {
					currentState.getTalks().clear();
					currentState.getTalks().addAll(chats);
					currentState.getPlayerStates().add(
							new PlayerState(character.getX() - b.getX(), character.getY() - b.getY(), character.getTimers(),
									character.getDirection(), character.getS(), true, character.getType().toString(), mpName, character.getHealth(),
									character.getEnergy(), character.getDire(), character.isDead()));

					for (GameCharacter character : friends) {
						currentState.getPlayerStates().add(
								new PlayerState(character.getX() - b.getX(), character.getY() - b.getY(), character.getTimers(), character
										.getDirection(), character.getS(), character.isPlayer(), character.getType().toString(), character
										.getMpName(), character.getHealth(), character.getEnergy(), character.getDire(), character.isDead()));
					}

					for (Enemy en : enemies) {
						currentState.getEnemyStates().add(new EnemyState(en.getX() - b.getX(), en.getY() - b.getY(), en.getHealth()));
					}
					for (NPC en : npcs) {
						currentState.getNPCStates().add(new NPCState(en.getX() - b.getX(), en.getY() - b.getY(), false, null, en.isObstacle()));
					}
					sendInt = 3;
					server.broadcast(mpName, currentState);
					currentState.clear(level);
				} else
					sendInt -= mult();
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		} else if (me != null && character != null) {
			try {

				Block b = world.get(0);
				for (int s = 0; s < states.size(); s++) {
					if (states.get(s) == null)
						continue;
					if (states.get(s).isServer())
						if (!states.get(s).getLevel().equals(level)) {

							timer.stop();
							time.pause();
							level = doorStateLev;
							changeClientArea();
							setState(State.INGAME);
							level = states.get(s).getLevel();
							timer.restart();
							time.resume();
							return;
						}

					// if(states.get(s).getPlayerStates()==null)
					// System.out.println("null");
					// else
					for (PlayerState playerState : states.get(s).getPlayerStates()) {

						boolean hasGone = false;
						if (character.getType().toString().equals(playerState.getTypeToString()))
							hasGone = true;
						else
							for (GameCharacter friend : friends) {
								if (friend.getType().toString().equals(playerState.getTypeToString())) {
									hasGone = true;
									clientFriendStuff(friend, playerState, b);
								}
							}
						if (!hasGone) {
							GameCharacter chara = getACharacter(playerState.getTypeToString());
							if (chara != null) {
								if (character == null && !playerState.isPlayer()) {
									character = chara;
									// xPos=playerState.getX();
									// yPos=state.getPlayerStates().get(c).getY();
									chara.setPlayer(true);
								} else {
									friends.add(chara);
									clientFriendStuff(chara, playerState, b);
								}
							}
						}
					}

					if (states.get(s).isServer()) {
						chats.clear();
						chats = states.get(s).getTalks();
					}
					for (ActionState actionState : states.get(s).getActions()) {
						switch (actionState.getActionType()) {
						case SWITCH:
							// SwitchState realState=(SwitchState)actionState;
							// for(GameCharacter chara:friends){
							// if(chara.getType().charName().equals(realState.getFrom())){
							// chara.setPlayer(false);
							// }
							// }
							break;
						case MONEY:
							MoneyState realState = (MoneyState) actionState;
							GameCharacter.getInventory().addMoney(realState.getVal());
							Statics.playSound(this, "weapons/whop.wav");
							try {
								objects.remove(realState.getI());
							} catch (Exception ex) {
								ex.printStackTrace();
							}
							break;
						case PICKUP:
							ObjectPickUpState realState2 = (ObjectPickUpState) actionState;
							try {
								objects.remove(realState2.getI());
							} catch (Exception ex) {
								ex.printStackTrace();
							}
							break;
						case MOVE:
							MoveObjectState move = (MoveObjectState) actionState;
							objects.get(move.getI()).setX(world.get(0).getX() + move.getX());
							objects.get(move.getI()).setY(world.get(0).getY() + move.getY());
							break;
						case BREAK:
							BreakCrystal breakC = (BreakCrystal) actionState;
							// if(world.get(breakC.getI()).getType()==Blocks.CRYSTAL)
							world.get(breakC.getI()).doType(Blocks.ROCK);
							Statics.playSound(this, "blocks/shatter.wav");
							break;
						case REMOVEENN:
							RemoveEnemy reEnn = ((RemoveEnemy) actionState);
							enemies.remove(reEnn.getI());
							break;
						case DIG:
							DigPit digC = (DigPit) actionState;
							// if(world.get(breakC.getI()).getType()==Blocks.CRYSTAL)
							world.get(digC.getI()).digDo();
							break;
						case ADDEN:
							AddEnemy add = (AddEnemy) actionState;
							add.getEnemy().setOwner(this);
							add.getEnemy().setImage(add.getEnemy().newImage(add.getEnemy().getLoc()));
							add.getEnemy().setShadow(add.getEnemy().newShadow());
							enemies.add(add.getEnemy());
							break;
						default:
							break;

						}
					}
					for (int c = 0; c < states.get(s).getEnemyStates().size(); c++) {
						if (enemies.size() <= c) {
							// changeClientArea();
							break;
						}
						enemies.get(c).setX(states.get(s).getEnemyStates().get(c).getX() + b.getX());
						enemies.get(c).setY(states.get(s).getEnemyStates().get(c).getY() + b.getY());
						enemies.get(c).setHealth(states.get(s).getEnemyStates().get(c).getHealth());
					}
					for (int c = 0; c < states.get(s).getNPCStates().size(); c++) {
						if (npcs.size() <= c) {
							// changeClientArea();
							break;
						}
						npcs.get(c).setX(states.get(s).getNPCStates().get(c).getX() + b.getX());
						npcs.get(c).setY(states.get(s).getNPCStates().get(c).getY() + b.getY());
						if (states.get(s).getNPCStates().get(c).isChange())
							npcs.get(c).newImage(states.get(s).getNPCStates().get(c).getChange());
						// npcs.get(c).setHealth(states.get(s).getEnemyStates().get(c).getHealth());
					}
				}
				states.clear();
				if (sendInt <= 0)
					currentState.getPlayerStates().add(
							new PlayerState(character.getX() - b.getX(), character.getY() - b.getY(), character.getTimers(),
									character.getDirection(), character.getS(), true, character.getType().toString(), mpName, character.getHealth(),
									character.getEnergy(), character.getDire(), character.isDead()));
				if (sendInt <= 0) {
					sendInt = 3;
					theServer.getTold(currentState);
					currentState.clear(level);
				} else
					sendInt -= mult();
			} catch (RemoteException e1) {
				// e1.printStackTrace();
				JOptionPane.showMessageDialog(this, "Lost connection to server. Leaving game.");
				System.exit(0);
			}
		} else
			states.clear();

		if (chatBox != null) {
			while (chats.size() > 6) {
				chats.remove(0);
			}
			chatBox.set(chats);
		}
		// if(server!=null||me!=null)
		// currentState=new GameState(mode, level);
		fpsT++;
		if (System.currentTimeMillis() - longTime >= 1000) {
			fps = fpsT;
			// if(fps>30){
			// times++;
			// if(times>5){
			// timer.setDelay(50);
			// }
			// }else
			// times=0;
			// System.out.println(fps);
			// System.out.println(tpf());
			fpsT = 0;
			longTime = System.currentTimeMillis();
		}
	}

	// Beginning of checkCollisions()-related code
	public void clientFriendStuff(GameCharacter friend, PlayerState playerState, Block b) {
		friend.setX(playerState.getX() + b.getX());
		friend.setY(playerState.getY() + b.getY());
		friend.setPlayer(playerState.isPlayer());
		friend.setDirection(playerState.getDir());
		friend.setImage(friend.newImage(playerState.getS()));
		friend.setMpName(playerState.getMpName());
		friend.setHealth(playerState.getHealth());
		friend.setEnergy(playerState.getEnergy());
		friend.setActing(playerState.getMeleeT(), playerState.getRangedT(), playerState.getSpecialT());
		friend.setDire(playerState.getDire());
		friend.setDead(playerState.isDead());
	}

	public void setCharacterStates(Rectangle r3) {
		// TODO Start
		Block b;
		Shape o;

		boolean tag = false;
		boolean started = false;

		for (int i = 0; i < world.size(); i++) {

			b = world.get(i);

			b.animate();
			b.setOnScreen(b.getBounds().intersects(getScreen()));

			if (!started && b.isOnScreen()) {
				started = true;
				startPoint = i;
			}

			if (b.isVisible()) {

				// Line-of-sight
				if (b.isOnScreen())
					if (b.getType() != Block.Blocks.WALL) {
						int[] xs = { b.getMidX() - 10, character.getMidX() - 10, character.getMidX() + 10, b.getMidX() + 10 };
						int[] ys = { b.getMidY() - 10, character.getMidY() - 10, character.getMidY() + 10, b.getMidY() + 10 };

						for (int x = 0; x < wallList.size(); x++) {
							if (wallList.get(x).isOnScreen() && new Polygon(xs, ys, xs.length).intersects(wallList.get(x).getBounds())) {
								tag = false;
								break;
							}

							tag = true;
						}
						// end of that code
					} else
						tag = true;
				b.setCanSee(tag);
				// End of line-of-sight
			}
			// working
			for (GameCharacter character : friends) {
				Rectangle r2 = character.getCollisionBounds();

				if (Statics.dist(b.getX(), b.getY(), character.getX(), character.getY()) < 200) {
					if (!b.traversable() && b.getBounds().intersects(r2)) {

						switch (b.getType()) {
						case PIT:
						case WALL:
						case CRYSTAL:
						case LIQUID:
							character.collision(b, false);
							break;
						default:
							break;
						}
					}

					if ((b.getType() == Blocks.CRYSTAL && character.getMove() == Moves.CLUB && !character.hasMeleed())
							|| (character.getMove() == Moves.PIT && !character.hasSpecialed() && (b.getType() == Blocks.GROUND
									|| b.getType() == Blocks.DIRT || b.getType() == Blocks.PIT))) {
						if (b.getBounds().intersects(character.getActBounds()) && !b.getBounds().intersects(character.getCollisionBounds())) {

							if ((character.getMove() == Moves.CLUB && !character.hasMeleed() && b.getType() == Blocks.CRYSTAL)
									|| (character.getMove() == Moves.PIT && !character.hasSpecialed() && (b.getType() == Blocks.GROUND
											|| b.getType() == Blocks.DIRT || b.getType() == Blocks.PIT)))
								if (b.getBounds().intersects(character.getActBounds()) && !b.getBounds().intersects(character.getCollisionBounds())) {

									b.interact(i);
									character.endAction();
								}
						}
					}
				}
			}
			Enemy e;
			boolean bashHit = false;
			int shieldNum = -1;
			boolean wizHit = false;
			int wizNum = -1;
			for (int u = 0; u < enemies.size(); u++) {

				e = enemies.get(u);
				Shape bounds = e.getBounds();
				if (e instanceof Irregular) {
					bounds = ((Irregular) e).getIrregularBounds();
				}
				if (atAllOnScreen(bounds)) {
					if (bounds.intersects(b.getBounds())) {
						switch (b.getType()) {
						case PIT:
							if (!e.flying)
								e.setAlive(false);
							break;

						case CRYSTAL:
						case WALL:
							e.turnAround(b.getX(), b.getY());
							break;
						case LIQUID:
							if (!e.flying)
								e.turnAround(b.getX(), b.getY());
							break;
						default:
							break;
						}
					}

					if (character.getActing() > 0 && bounds.intersects(character.getActBounds())) {
						if (me != null) {
							// if(currentState!=null)
							// currentState.getActions().add(new AttackState(u,
							// character.getMove(),
							// false,character.getType().toString()));
							// return;
						} else
							e.interact(character.getMove(), character, false);
						if (character.getMove() == Moves.BASH)
							bashHit = true;
						else if (character.getMove() == Moves.WIZ_S)
							wizHit = true;
					}
					for (int c = 0; c < fP.size(); c++) {
						FProjectile character = fP.get(c);
						o = character instanceof Irregular ? ((Irregular) character).getIrregularBounds() : character.getBounds();

						// This modification would allow us to make certain
						// projectiles behave differently with their bounds;
						// could be implemented with other objects.

						if (polygonsInt(bounds, o) && character.isOnScreen()
						// && character.getHarming()
						) {
							if ((!(e instanceof Projectile) || (character instanceof Field))
									&& (!(fP.get(c) instanceof Shield) || (((Shield) fP.get(c)).isHarming()))) {
								if (me != null) {
									// if(currentState!=null)
									// currentState.getActions().add(new
									// AttackState(u, character.getMove(),
									// true,character.getMaker().getType().toString()));
									// return;
								} else {
									e.interact(character.getMove(), character.getMaker(), true);
								}
								fP.get(c).setOnScreen(false);

							}
						}
					}

					for (int c = 0; c < friends.size(); c++) {
						GameCharacter character = friends.get(c);
						if (character.getActing() > 0 && bounds.intersects(character.getActBounds())) {
							if (me == null)
								e.interact(character.getMove(), character, false);
							if (character.getMove() == Moves.BASH) {
								bashHit = true;
								shieldNum = c;
							} else if (character.getMove() == Moves.WIZ_S) {
								wizHit = true;
								wizNum = c;
							}
						}
					}
					if (bounds.intersects(r3) && e.willHarm()) {
						e.turnAround(character.getX(), character.getY());
						character.takeDamage(e.getDamage(), e.poisons(), e.isPoison());

					}

					for (GameCharacter character : friends) {
						Rectangle r2 = character.getBounds();
						if (bounds.intersects(r2) && e.willHarm()) {
							e.turnAround(character.getX(), character.getY());
							character.takeDamage(e.getDamage(), e.poisons(), e.isPoison());
						}
					}
				}
			}
			if (bashHit) {
				if (shieldNum == -1)
					character.endAction();
				else
					friends.get(shieldNum).endAction();
			}
			if (wizHit) {
				if (wizNum == -1)
					character.endAction();
				else
					friends.get(wizNum).endAction();
			}
			// end of enemy loop
			if (b.isOnScreen()) {

				if (!b.traversable()) {
					if (b.getBounds().intersects(r3)) {

						switch (b.getType()) {

						// Cases for the floor
						case GROUND:
						case ROCK:
						case CARPET:
						case DIRT:
							break;

						// Cases for raised obstructions
						case PIT:
						case WALL:
						case CRYSTAL:
						case LIQUID:
							character.collision(b, false);
							break;
						}
					}

					for (int rI = 0; rI < character.getDirBounds().length; rI++)
						if (b.getBounds().intersects(character.getDirBounds()[rI]))
							character.presetCollisionFlag(rI);
				}

				if ((character.getMove() == Moves.CLUB && !character.hasMeleed() && b.getType() == Blocks.CRYSTAL)
						|| (character.getMove() == Moves.PIT && !character.hasSpecialed() && (b.getType() == Blocks.GROUND
								|| b.getType() == Blocks.DIRT || b.getType() == Blocks.PIT))) {
					if (b.getBounds().intersects(character.getActBounds()) && !b.getBounds().intersects(character.getCollisionBounds())) {

						b.interact(i);
						character.endAction();
					}
				}

				FProjectile p;
				for (int u = 0; u < fP.size(); u++) {

					p = fP.get(u);
					if (p.isOnScreen()) {
						o = p instanceof Irregular ? ((Irregular) p).getIrregularBounds() : p.getBounds();
						if (o.getBounds().intersects(b.getBounds()) && p.getMove() != Moves.DISPENSER) {
							switch (b.getType()) {

							case CRYSTAL:
							case WALL:
								p.setOnScreen(false);
								break;

							default:
								break;
							}
						}
					}
				}

				if (movingObjects.size() > 0) {

					Shape s;
					for (Objects o0 : movingObjects) {
						s = o0 instanceof Irregular ? ((Irregular) o0).getIrregularBounds() : o0.getBounds();
						if (s.intersects(b.getBounds())) {
							if (!b.traversable())
								o0.collideWall();
							if (o0 instanceof Light)
								b.setIlluminated(true);
						}
					}
				}
			}

		}// TODO END WORLD LOOP

		for (GameCharacter friend : friends) {
			if (friend.getMove() == Moves.AURA && !friend.hasMeleed()) {
				boolean healed = false;
				if (friend.getActBounds().intersects(character.getBounds())) {
					character.heal(friend.getMeleeDamage() / 3);
					healed = true;
				}
				for (GameCharacter friend2 : friends) {
					if (friend.getActBounds().intersects(friend2.getBounds())) {
						friend2.heal(friend.getMeleeDamage() / 3);
						healed = true;
					}
				}

				if (healed) {
					friend.heal(character.getMeleeDamage() / 3);
					friend.endAction();
				}

			} else if (friend instanceof Heart && ((Heart) friend).usingField()) {
				// fieldUsed = true;
				Polygon rB = new Polygon();

				for (FProjectile f : fP)
					if (f instanceof Field) {
						rB = ((Field) f).getIrregularBounds();
						break;
					}
				if (rB.intersects(character.getBounds())) {
					character.heal(Heart.FIELD_HEAL);
				}
				for (GameCharacter friend2 : friends) {
					if (rB.intersects(friend2.getBounds())) {
						friend2.heal(Heart.FIELD_HEAL);
					}
				}

			}
		}
		if (character.getMove() == Moves.AURA && !character.hasMeleed()) {

			boolean healed = false;

			for (GameCharacter friend2 : friends) {

				if (character.getActBounds().intersects(friend2.getBounds())) {
					friend2.heal(character.getMeleeDamage() / 3);
					healed = true;
				}
			}

			if (healed) {
				character.heal(character.getMeleeDamage() / 3);
				character.endAction();
			}

		} else if (character instanceof Heart && ((Heart) character).usingField()) {
			// fieldUsed = true;
			Polygon rB = new Polygon();

			for (FProjectile f : fP)
				if (f instanceof Field) {
					rB = ((Field) f).getIrregularBounds();
					break;
				}

			for (GameCharacter friend2 : friends) {
				if (rB.intersects(friend2.getBounds())) {
					friend2.heal(Heart.FIELD_HEAL);
				}
			}

			if (rB.intersects(character.getBounds()))
				character.heal(Heart.FIELD_HEAL);
		}
		// end

		Portal p;
		for (int i = 0; i < portals.size(); i++) {

			p = portals.get(i);
			p.animate();
			p.setOnScreen(p.getBounds().intersects(getScreen()));

			if (r3.intersects(p.getBounds())) {
				if (me != null) {
					character.collision(p, false);
					for (int rI = 0; rI < character.getDirBounds().length; rI++)
						if (p.getBounds().intersects(character.getDirBounds()[rI]))
							character.presetCollisionFlag(rI);
				} else if (p.interact()) {
					if (!(p instanceof Door || p instanceof SpecialDoor)) {

						timer.stop();
						time.pause();
						level = p.getArea();
						changeArea();
						timer.restart();
						time.resume();
					} else {
						spawnNum = p.getSpawnNum();
						setState(State.DOOROPEN);
						doorStateLev = p.getArea();
					}
				} else {
					character.collision(p, false);
					for (int rI = 0; rI < character.getDirBounds().length; rI++)
						if (p.getBounds().intersects(character.getDirBounds()[rI]))
							character.presetCollisionFlag(rI);
				}
			}
		}

		Rectangle bounds = character.getTalkBounds();
		for (NPC n : npcs) {
			if (me == null) {
				n.animate();
			} else
				Sprite.basicAnimate(n);
			n.setOnScreen(n.getBounds().intersects(getScreen()));

			if (n.isOnScreen()) {
				if (r3.intersects(n.getBounds())) {
					if (n.isObstacle())
						character.collision(n, false);
					if (n instanceof TouchNPC && n.willTalk()) {
						current = n;
						current.setLine();
						state = State.NPC;
						character.setImage(character.newImage("n"));
						for (GameCharacter character : friends)
							character.setImage(character.newImage("n"));
						bounds = null;
					}
				}
				if (bounds != null && n.getBounds().intersects(bounds) && (!(n instanceof TouchNPC) || ((TouchNPC) n).buttonTalk()) && n.willTalk()) {
					current = n;
					current.setLine();
					state = State.NPC;
					character.setImage(character.newImage("n"));
					for (GameCharacter character : friends)
						character.setImage(character.newImage("n"));
					bounds = null;
				}
				if (n.isObstacle())
					for (int rI = 0; rI < character.getDirBounds().length; rI++)
						if (n.getBounds().intersects(character.getDirBounds()[rI]))
							character.presetCollisionFlag(rI);
			}
			for (int c = 0; c < friends.size(); c++) {

				if (n.getBounds().intersects(friends.get(c).getCollisionBounds())) {

					if (n.isObstacle())
						friends.get(c).collision(n, false);

				}
			}
		}

		Objects n;
		boolean hasTalked = false;
		for (int u = 0; u < objects.size(); u++) {
			boolean beenPicked = false;
			n = objects.get(u);
			n.animate();
			n.setOnScreen(n.getBounds().intersects(getScreen()));
			o = n instanceof Irregular ? ((Irregular) n).getIrregularBounds() : n.getBounds();

			if (n.isOnScreen()) {
				if (o.intersects(character.getCollisionBounds())) {
					n.collidePlayer(-1);
					if (n instanceof PushCube && currentState != null)
						currentState.getActions().add(new MoveObjectState(-world.get(0).getX() + n.getX(), -world.get(0).getY() + n.getY(), u));
					// if(me==null)
					if (n instanceof Collectible && ((Collectible) n).collectible()) {
						if (n instanceof MoneyObject) {
							Statics.playSound(this, "collectibles/marioCoin.wav");

							GameCharacter.getInventory().addMoney(((MoneyObject) n).getValue());
							objects.remove(u);
							if (currentState != null)
								currentState.getActions().add(new MoneyState(((MoneyObject) n).getValue(), u));
							u--;
							beenPicked = true;
						} else if (n instanceof KeyCrystal) {
							Statics.playSound(this, "collectibles/marioCoin.wav");
							GameCharacter.getInventory().addItem(((Collectible) n).getType(), ((KeyCrystal) n).getValue());
							if (((KeyCrystal) n).id > -1)
								data.collect(((KeyCrystal) n).id);
							objects.remove(u);

							beenPicked = true;
							if (currentState != null)
								currentState.getActions().add(new ObjectPickUpState(u));
							u--;
						}

					}
				}
				if (n.isWall())
					for (int rI = 0; rI < character.getDirBounds().length; rI++)
						if (n.getBounds().intersects(character.getDirBounds()[rI]))
							character.presetCollisionFlag(rI);
			}

			if (!beenPicked && state != State.NPC// &&state!=State.SWITCHING
					&& bounds != null && o.intersects(bounds) && !hasTalked && !(n instanceof DropPoint)) {
				if (n.interact()) {
					if (n instanceof PushCube && currentState != null)
						currentState.getActions().add(new MoveObjectState(-world.get(0).getX() + n.getX(), -world.get(0).getY() + n.getY(), u));
					hasTalked = true;
					if (n instanceof CheckPoint) {
						spawnLoc = new Point(n.getX(), n.getY());
						save(((CheckPoint) n).getSpawnNum());
						System.out.println("SAVED");
					} else if (n instanceof Collectible && ((Collectible) n).collectible()) {
						if (n instanceof MoneyObject) {
							Statics.playSound(this, "collectibles/marioCoin.wav");
							GameCharacter.getInventory().addMoney(((MoneyObject) n).getValue());
							objects.remove(u);

							if (currentState != null)
								currentState.getActions().add(new MoneyState(((MoneyObject) n).getValue(), u));
							u--;
						} else if (n instanceof KeyCrystal) {
							Statics.playSound(this, "collectibles/marioCoin.wav");
							GameCharacter.getInventory().addItem(((Collectible) n).getType(), ((KeyCrystal) n).getValue());
							if (((KeyCrystal) n).id > -1)
								data.collect(((KeyCrystal) n).id);
							objects.remove(u);
							u--;

						} else if (n instanceof CollectibleCharacter) {
							// This code would, once fully implemented, add an
							// extra
							// character following you. You would be able to
							// switch
							// to him.
							((CollectibleCharacter) n).makeCharacter();
							objects.remove(u);
							u--;
						} else if (n instanceof CollectibleObject) {
							GameCharacter.getInventory().addItem(((Collectible) n).getType(), 1);
							objects.remove(u);
							u--;
						}
						if (!(n instanceof MoneyObject) && currentState != null)
							currentState.getActions().add(new ObjectPickUpState(u + 1));

					}

				}
			}
			for (int c = 0; c < friends.size(); c++) {

				if (n.getBounds().intersects(friends.get(c).getCollisionBounds())) {
					n.collidePlayer(c);
					// if (n instanceof Collectible
					// && ((Collectible) n).collectible())
					// if (n instanceof MoneyObject) {
					// Statics.playSound(this,
					// "collectibles/marioCoin.wav");
					//
					// GameCharacter.getInventory().addMoney(
					// ((MoneyObject) n).getValue());
					// objects.remove(u);
					// u--;
					// beenPicked = true;
					// } else if (n instanceof SpecialCollectible) {
					// Statics.playSound(this,
					// "collectibles/marioCoin.wav");
					// GameCharacter.getInventory().addItem(
					// ((Collectible) n).getType(), 1);
					// data.collect(((SpecialCollectible) n).id);
					// objects.remove(u);
					// u--;
					// beenPicked = true;
					//
					// }
				}
			}
		}

		// Moving objects colliding stuff
		if (movingObjects.size() > 0) {
			Shape s0;
			for (Objects o0 : movingObjects) {
				s0 = o0 instanceof Irregular ? ((Irregular) o0).getIrregularBounds() : o0.getBounds();
				for (Objects o1 : objects)
					if (s0.intersects(o1.getBounds()) && o0 != o1) {
						if (o1.isWall())
							o0.collideWall();
						if (o0 instanceof Light)
							o1.setIlluminated(true);
					}
				for (NPC n2 : npcs) {
					if (s0.intersects(n2.getBounds())) {
						o0.collideWall();
						if (o0 instanceof Light)
							n2.setIlluminated(true);
					}
				}
				for (Portal n2 : portals)
					if (s0.intersects(n2.getBounds()))
						o0.collideWall();

				for (GameCharacter f : friends)
					if (s0.intersects(f.getBounds())) {
						o0.collideWall();
						if (o0 instanceof Light)
							f.setIlluminated(true);
					}
				for (Enemy e : enemies)
					if (s0.intersects(e.getBounds())) {
						o0.collideWall();
						if (o0 instanceof Light)
							e.setIlluminated(true);
					}

				if (s0.intersects(character.getBounds())) {
					o0.collideWall();
					if (o0 instanceof Light)
						character.setIlluminated(true);
				}
			}
		}
		// TODO end
	}

	public void multiplayer() {
		if (server == null && me == null) {
			mpName = JOptionPane.showInputDialog("What would you like to be called?", System.getProperty("user.name"));
			if (mpName == null)
				return;
			passWord = JOptionPane.showInputDialog("What would you like the entry password to be?\nNone is the default.", "None");
			if (passWord == null)
				return;
			server = new ChatServer(this, passWord);
			currentState = new GameState(mode, level, true);
			chatBox = new ChatBox(this);
			addAction("started server", "images/icon.png");
		}

	}

	public void talk(NPC n) {
		current = n;
		current.setLine();
		state = State.NPC;
		character.setImage(character.newImage("n"));
		for (GameCharacter character : friends)
			character.setImage(character.newImage("n"));
	}

	public boolean polygonsInt(Shape poly1, Shape poly2) {
		Area area = new Area(poly1);
		area.intersect(new Area(poly2));
		return !area.isEmpty();
	}

	public void toggleLagPrevention() {
		lagPrevention = !lagPrevention;
		if (lagPrevention)
			timerWait = LAG_TIMER;
		else
			timerWait = NORMAL_TIMER;

		timer.setDelay(timerWait);
	}

	@Override
	public void keyPress(int key) {
		// Show me ya moves! }(B-)
		if (key == KeyEvent.VK_8) {
			toggleLagPrevention();
		} else if (key == KeyEvent.VK_J) {
			if (pointedPoint == null) {

				pointedPoint = MouseInfo.getPointerInfo().getLocation();
			} else
				pointedPoint = null;
		}
		if (key == Preferences.CHAR_CHANGE() && state != State.NPC && GameCharacter.storyInt > 3)
			switching = true;
		else if (key == KeyEvent.VK_EQUALS)
			JOptionPane.showMessageDialog(owner, Preferences.getControls(), DigIt.NAME, JOptionPane.INFORMATION_MESSAGE);

		else if (state != State.NPC && key == KeyEvent.VK_ESCAPE) {

			if (state != State.DEAD && state != State.LOADING)
				setState(State.PAUSED);
			Statics.exit(this);
		}
		switch (state) {
		case NPC:
			if (key == Preferences.NPC())
				current.exit();

			break;

		case PAUSED:
			pausedHandler(key);
			break;

		case INGAME:

			if (key == Preferences.PAUSE()) {
				setState(State.PAUSED);
				repaint();
				return;
			}

			ingameHandler(key);
			break;

		default:
			break;
		}

		// repaint();
	}

	@Override
	public void keyRelease(int key) {
		if (character != null)
			character.keyReleased(key);
	}

	private class TAdapter extends KeyAdapter {

		public void keyReleased(KeyEvent e) {
			keyRelease(e.getKeyCode());
		}

		public void keyPressed(KeyEvent e) {
			keyPress(e.getKeyCode());
		}
	}

	private class MouseL implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			int button = e.getButton();
			switch (button) {
			case 1:
				keyPress(Preferences.PROJECTILE());
				break;
			case 3:
				keyPress(Preferences.SPECIAL());
				break;
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			int button = e.getButton();
			switch (button) {
			case 1:
				keyRelease(Preferences.PROJECTILE());
				break;
			case 3:
				keyRelease(Preferences.SPECIAL());
				break;
			}
		}

	}

	public void ingameHandler(int key) {
		if (character != null)
			character.keyPressed(key);
	}

	private void pausedHandler(int key) {
		if (key == Preferences.PAUSE())
			setState(State.INGAME);
	}

	public void setState(State state) {

		this.state = state;

		if (state == State.PAUSED || state == State.NPC || state == State.LOADING// ||state==State.SWITCHING
		)
			time.pause();
		else if (state == State.INGAME)
			time.resume();
		else if (state == State.DOOROPEN) {
			doorStateTimer = DOORSTATETMAX;
			character.stop();
			character.setImage(character.newImage("n"));
			for (GameCharacter character : friends) {
				character.stop();
				character.setImage(character.newImage("n"));
			}
		} else if (state == State.DEAD)
			time.end();
	}

	public Board getMe() {
		return this;
	}

	public void appendScrollX(int toAppend) {
		scrollX += toAppend;
	}

	public int getCharacterX() {
		return character.getX();
	}

	public int getCharacterY() {
		return character.getY();
	}

	public void reAnimate() {
		int i;
		if (pointedPoint != null) {
			pointedPoint.x += scrollX;
			pointedPoint.y += scrollY;
		}
		if (spawnLoc != null) {
			spawnLoc.x += scrollX;
			spawnLoc.y += scrollY;
		}
		for (i = 0; i < world.size(); i++)
			world.get(i).basicAnimate();

		for (i = 0; i < enemies.size(); i++)
			enemies.get(i).basicAnimate();

		for (i = 0; i < portals.size(); i++)
			portals.get(i).basicAnimate();

		for (i = 0; i < friends.size(); i++) {
			friends.get(i).basicAnimate();
			if (friends.get(i).getPPath() != null)
				for (int c = 0; c < friends.get(i).getPPath().getPoints().size(); c++) {
					friends.get(i).getPPath().getPoints().get(c).update(scrollX, scrollY);
				}
		}
		// if(points!=null)
		// for( i=0;i<points.size();i++){
		// points.get(i).update(scrollX, scrollY);
		// }
		for (i = 0; i < npcs.size(); i++)
			npcs.get(i).basicAnimate();

		for (i = 0; i < objects.size(); i++)
			objects.get(i).basicAnimate();
	}

	public GameCharacter getCharacter() {
		return character;
	}

	public void setSpawnX(int spawnX) {
		this.spawnX = spawnX;
	}

	public void setSpawnY(int spawnY) {
		this.spawnY = spawnY;
	}

	public State getState() {
		return state;
	}

	public ArrayList<Block> getWallList() {
		return wallList;
	}

	public Rectangle getScreen() {

		return new Rectangle(0, 0, Statics.BOARD_WIDTH, Statics.BOARD_HEIGHT);
	}

	public void addEnemy(Enemy toAdd) {
		enemies.add(toAdd);
	}

	public Point getCharPoint() {
		return new Point(character.getX(), character.getY());
	}

	public ArrayList<GameCharacter> getFriends() {
		return friends;
	}

	public void setFriends(ArrayList<GameCharacter> friends) {
		this.friends = friends;
	}

	public ArrayList<FProjectile> getfP() {
		return fP;
	}

	public void setfP(ArrayList<FProjectile> fP) {
		this.fP = fP;
	}

	public TexturePack getTexturePack() {
		return texturePack;
	}

	public void setTexturePack(TexturePack texturePack2) {
		this.texturePack = texturePack2;
	}

	public Color getTextureBack() {
		switch (texturePack) {
		case DESERT:
			return Statics.OFF_TAN;
		case ISLAND:
			return Color.BLUE;
		case SNOWY:
			return Color.WHITE;
		case VOLCANO:
			return Color.DARK_GRAY;
		case HAUNTED:
			return Statics.SAND_BLUE;

		case EVIL:
			return Color.BLACK;

		case GRASSY:
		default:
			return Statics.OFF_GREEN;
		}
	}

	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}

	public ArrayList<Enemy> getOnScreenEnemies() {
		return onScreenEnemies;
	}

	public void save(int spawnNum) {
		this.spawnNum = spawnNum;
		this.save();
	}

	public void save() {
		if (userName != null) {
			String location = (Statics.getBasedir() + "/saveFiles/" + userName + "/");
			File loc = new File(location);
			if (loc.exists()) {
				File locFile = new File(location + userName + ".txt");
				try {
					BufferedWriter writer = new BufferedWriter(new FileWriter(locFile));
					writer.write(mode + "," + level + "," + GameCharacter.getLevel() + "," + GameCharacter.getXP() + "," + spawnNum + ","
							+ GameCharacter.storyInt);
					writer.newLine();
					// if (normalPlayer(character.getType()))
					writer.write(character.getSave() + ",true");
					for (int c = 0; c < friends.size(); c++) {
						// if (normalPlayer(friends.get(c).getType())) {

						writer.newLine();
						writer.write(friends.get(c).getSave() + ",true");
						// }
					}
					for (int c = 0; c < goneFriends.size(); c++) {
						writer.write(goneFriends.get(c) + ",false");
					}
					// writer.newLine();
					// writer.write(character != null ? "" +
					// character.getInventory().getMoney() : "0");
					writer.close();

					ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(location + "data.ser"));
					os.writeObject(data);
					os.close();

					os = new ObjectOutputStream(new FileOutputStream(location + "inventory.ser"));
					os.writeObject(GameCharacter.getInventory());
					os.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			} else {
				JOptionPane.showMessageDialog(owner, "Could not save to " + location);
			}
		}
	}

	public String withoutFalse(String without) {
		if (without.endsWith(",false"))
			without.replaceAll(",false", "");
		return without;
	}

	public void loadSave() {
		level = DEFAULT;
		try {
			String location = (Statics.getBasedir() + "/saveFiles/" + userName + "/");
			File saveFile = new File(location + userName + ".txt");

			if (saveFile.exists()) {
				BufferedReader reader = new BufferedReader(new FileReader(saveFile));
				String line;
				ArrayList<String> lines = new ArrayList<String>();

				while ((line = reader.readLine()) != null)
					lines.add(line);

				if (lines.size() > 0) {
					ArrayList<String> stuff = new ArrayList<String>();
					// should have 5
					String currentS = "";
					for (int c2 = 0; c2 < lines.get(0).length(); c2++) {

						if (lines.get(0).charAt(c2) == ',') {
							stuff.add(currentS);
							currentS = "";

						} else {
							currentS += lines.get(0).charAt(c2);
						}
					}
					if (currentS != "") {
						stuff.add(currentS);
					}
					try {
						String mode = stuff.get(0);
						this.mode = mode;
						String lev = stuff.get(1);
						level = lev;
						int levUp = Integer.parseInt(stuff.get(2));
						GameCharacter.setLevel(levUp);
						int xp = Integer.parseInt(stuff.get(3));
						int spawnNum = Integer.parseInt(stuff.get(4));
						GameCharacter.storyInt = Integer.parseInt(stuff.get(5));
						this.spawnNum = spawnNum;
						GameCharacter.setXP(xp);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				for (int c = 1; c < lines.size(); c++) {
					if (lines.get(c).endsWith("false")) {
						goneFriends.add(withoutFalse(lines.get(c)));
						continue;
					}
					// int pos=-1;
					String name = "shovel";
					if (lines.get(c).startsWith("shovel"))
						name = "shovel";
					else if (lines.get(c).startsWith("heart"))
						name = "heart";
					else if (lines.get(c).startsWith("diamond"))
						name = "diamond";
					else if (lines.get(c).startsWith("club"))
						name = "club";
					else if (lines.get(c).startsWith("sirCobalt"))
						name = "sirCobalt";
					else if (lines.get(c).startsWith("wizard"))
						name = "wizard";
					else if (lines.get(c).startsWith("macaroni"))
						name = "macaroni";
					else if (lines.get(c).startsWith("ryo"))
						name = "ryo";
					if (character == null) {
						character = getACharacter(name);
						character.setPlayer(true);
						character.load(lines.get(c).substring(name.length() + 1));
					} else {
						friends.add(getACharacter(name));
						friends.get(friends.size() - 1).load(lines.get(c).substring(name.length() + 1));
					}
				}
				reader.close();

				try {
					ObjectInputStream is = new ObjectInputStream(new FileInputStream(location + "data.ser"));
					data = ((CharData) is.readObject());
					data.setOwner(this);
					is.close();

					is = new ObjectInputStream(new FileInputStream(location + "preferences.ser"));
					preferences = ((Preferences) is.readObject());
					is.close();

					// reader = new BufferedReader(new FileReader(location +
					// "inventory.txt"));
					is = new ObjectInputStream(new FileInputStream(location + "inventory.ser"));
					Inventory w = (Inventory) is.readObject();
					is.close();
					GameCharacter.setInventory(w);
					GameCharacter.getInventory().setOwner(this);

					GameControllerRunnable.renewKeys();
				} catch (Exception badThing) {
					badThing.printStackTrace();
				}
				changeArea(spawnNum);
			} else {
				throw new FileNotFoundException();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			newGame(owner.getLevel());
		}
	}

	public void newGame(String level) {
		this.level = level;
		preferences = new Preferences();
		GameCharacter.setInventory(new Inventory(this));
		if (me == null)
			try {
				String location = (Statics.getBasedir() + "maps/" + mode + "/");
				File saveFile = new File(location + "info.txt");

				if (saveFile.exists()) {
					BufferedReader reader = new BufferedReader(new FileReader(saveFile));
					String line;
					ArrayList<String> lines = new ArrayList<String>();

					while ((line = reader.readLine()) != null)
						lines.add(line);
					if (lines.size() > 1) {
						for (int c = 0; c < lines.size(); c++) {
							GameCharacter chara = getACharacter(lines.get(c));

							if (chara != null) {
								if (character == null) {
									character = chara;
									chara.setPlayer(true);
								} else {
									friends.add(chara);
								}
							}
						}
					}

					reader.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		changeArea();
		if (userName != null)
			preferences.save(Statics.getBasedir() + "/saveFiles/" + owner.getUserName() + "/");
		// for(Items i:Items.values())
		// GameCharacter.getInventory().addItem(i, 100);
	}

	public ArrayList<Objects> getObjects() {
		return objects;
	}

	public ArrayList<Portal> getPortals() {
		return portals;
	}

	public boolean normalPlayer(GameCharacter.Types type) {
		switch (type) {
		case SPADE:
		case DIAMOND:
		case HEART:
		case CLUB:
			return true;
		default:
			return false;
		}
	}

	public CharData getData() {
		return data;
	}

	public String getUserName() {
		return userName;
	}

	public String getMPName() {
		return mpName;
	}

	public void removeDispensers(Heart ender) {

		int i;
		for (i = 0; i < objects.size(); i++)
			if (objects.get(i) instanceof Dispenser) {
				objects.remove(i);
				i--;
			}
		for (i = 0; i < fP.size(); i++) {
			if (fP.get(i) instanceof Field) {
				fP.remove(i);
				i--;
			}
		}
	}

	// / TODO personalMouse
	public class PersonalMouse implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent arg0) {

		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			if (state == State.PAUSED)
				GameCharacter.getInventory().mouseClick(arg0);
			else if (state == State.NPC)
				current.mouseClick(arg0);
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
		}
	}

	public void addItem(Items useItem) {
		if (useItem != Items.NULL) {
			ThrownObject o = new ThrownObject(character.getX(), character.getY(), useItem.getPath(), this, useItem);
			objects.add(o);
			movingObjects.add(o);
		}
	}

	public ArrayList<Objects> getMovingObjects() {
		return movingObjects;
	}

	public ArrayList<NPC> getNPCs() {
		return npcs;
	}

	public void setDayNight(DayNight time) {
		dN = time;
	}

	public boolean isDay() {

		switch (dN) {
		case DAY:
			return true;
		case NIGHT:
			return false;
		default:
			return time.getGeneralTime() == Time.DAY;
		}
	}

	public void updateBackground() {
		if (corruptedWorld) {
			setBackground(Color.BLACK);
			return;
		}
		if (weather == Weather.FOG)
			updateFog();
		else if (weather == Weather.RAIN)
			updateRain();
		else
			updateNormal();
	}

	// These three methods are used by updateBackground
	protected void updateFog() {

		switch (dN) {

		case DAY:
			setBackground(Color.gray);
			break;
		case NIGHT:
			setBackground(Color.darkGray);
			break;

		default:
			switch (time.getGeneralTime()) {
			case Time.DAY:
				setBackground(Color.gray);
				break;

			case Time.NIGHT:
				setBackground(Color.darkGray);
				break;

			case Time.SUNRISE:
				setBackground(Statics.sunriseColor(Color.gray, time.getTime()));
				break;

			case Time.SUNSET:
				setBackground(Statics.sunsetColor(Color.gray, time.getTime()));
				break;
			}
		}

	}

	protected void updateRain() {
		if ((time.getGeneralTime() == Time.DAY || dN == DayNight.DAY) && dN != DayNight.NIGHT)
			setBackground(weatherTimer <= 0 ? Statics.sunriseColor(getTextureBack(), Statics.HALF_DARK) : getTextureBack());
		else
			setBackground(weatherTimer <= 0 ? Statics.darkenColor(getTextureBack()) : getTextureBack());
	}

	protected void updateNormal() {

		switch (dN) {
		case DAY:
			setBackground(getTextureBack());
			break;

		case NIGHT:
			setBackground(Statics.darkenColor(getTextureBack()));
			break;

		default:
			switch (time.getGeneralTime()) {
			case Time.DAY:
				setBackground(getTextureBack());
				break;

			case Time.NIGHT:
				setBackground(Statics.darkenColor(getTextureBack()));
				break;

			case Time.SUNRISE:
				setBackground(Statics.sunriseColor(getTextureBack(), time.getTime()));
				break;

			case Time.SUNSET:
				setBackground(Statics.sunsetColor(getTextureBack(), time.getTime()));
				break;
			}
			break;
		}
	}

	public boolean darkenWorld() {
		if (weather == Weather.RAIN)
			return weatherTimer <= 0;
		else if (dN == DayNight.ANY)
			return time.getGeneralTime() == Time.NIGHT;
		else
			return dN == DayNight.NIGHT;
	}

	public boolean thunderStrike() {
		return weather == Weather.RAIN && weatherTimer > 0;
	}

	public boolean sunRise() {

		// switch (texturePack) {
		// case LAB:
		// case HAUNTED:
		// return false;
		//
		// default:
		return time.getGeneralTime() == Time.SUNRISE && dN == DayNight.ANY;
		// }
	}

	public boolean sunSet() {

		// switch (texturePack) {
		// case LAB:
		// case HAUNTED:
		// return false;
		//
		// default:
		return time.getGeneralTime() == Time.SUNSET && dN == DayNight.ANY;
		// }
	}

	private Weather lastWeather = Weather.NORMAL;

	// TODO at work
	public void changeWeather() {
		if (weather == Weather.NORMAL) {
			int rand = Statics.RAND.nextInt(50);
			if (rand > 1 && rand < 17)
				weather = lastWeather;
			else
				switch (rand) {
				case 0:
					weather = Weather.RAIN;
					break;
				case 1:
					weather = Weather.FOG;
					break;
				default:
					weather = Weather.NORMAL;
					break;
				}
		}
		lastWeather = weather;
	}

	public float getTime() {
		return time.getTime();
	}

	public boolean lighterDark() {
		return (weather == Weather.RAIN && (time.getGeneralTime() == Time.DAY || dN == DayNight.DAY)) && dN != DayNight.NIGHT;
	}

	public int getSpawnNum() {
		return spawnNum;
	}

	public Point getSpawnLoc() {
		return spawnLoc;
	}

	public void setSpawnLoc(Point p) {
		spawnLoc = p;
	}

	public ArrayList<GameCharacter> getAliveFriends() {
		ArrayList<GameCharacter> alive = new ArrayList<GameCharacter>();
		for (GameCharacter c : friends)
			if (!c.isDead() && !c.isPlayer())
				alive.add(c);
		return alive;
	}

	public void setSwitching(boolean b) {
		switching = b;
	}

	public void getTold(GameState state) {
		if (server != null && state.isServer())
			return;
		if (server == null && state.isServer() && mode == null) {
			mode = state.getPack();
			level = state.getLevel();
			currentState = new GameState(mode, level, false);
			// timer=new Timer(delay, listener)
			int xPos = 0;
			int yPos = 0;
			try {
				if (state.getPlayerStates().size() > 0) {
					for (int c = 0; c < state.getPlayerStates().size(); c++) {
						GameCharacter chara = getACharacter(state.getPlayerStates().get(c).getTypeToString());
						if (chara != null) {
							if (character == null && !state.getPlayerStates().get(c).isPlayer()) {
								character = chara;
								xPos = state.getPlayerStates().get(c).getX();
								yPos = state.getPlayerStates().get(c).getY();
								chara.setPlayer(true);
							} else {
								friends.add(chara);
							}
						}
					}
				}

			} catch (Exception ex) {
				ex.printStackTrace();
			}
			if (character != null) {
				// newGame(level);
				changeClientArea();
				setState(State.INGAME);
				Block b = world.get(0);
				int x = character.getX();
				int y = character.getY();
				scroll((character.getX() - b.getX()) - (xPos), (character.getY() - b.getY()) - (yPos));
				character.setX(x);
				character.setY(y);
				timer.start();
				timer.start();
				chatBox = new ChatBox(this);
			} else {
				mode = state.getPack();
				level = state.getLevel();
				currentState = null;
				friends.clear();
			}
		} else if (mode != null
				&& (state.isServer() || (server != null && server.contains(state.getPlayerStates())) || (me != null && me.contains(state
						.getPlayerStates()))))
			states.add(state);

		for (int c = 0; c < state.getTalks().size(); c++) {
			chats.add(state.getTalks().get(c));
		}

	}

	public void setOtherServer(IChatServer server) {
		theServer = server;
	}

	public IChatServer getOtherServer() {
		return theServer;
	}

	public String getLevel() {
		return level;
	}

	public String getMode() {
		return mode;
	}

	public ChatClient getClient() {
		return me;
	}

	public GameCharacter getACharacter(String typeString) {
		GameCharacter chara = null;
		switch (typeString) {
		case "club":
			chara = new Club(0, 0, this, false);
			break;
		case "heart":
			chara = new Heart(0, 0, this, false);
			break;
		case "shovel":
			chara = new Spade(0, 0, this, false);
			break;
		case "diamond":
			chara = new Diamond(0, 0, this, false);
			break;
		case "sirCobalt":
			chara = (new SirCobalt(0, 0, this, false));
			break;
		case "wizard":
			chara = (new Wizard(0, 0, this, false));
			break;
		case "macaroni":
			chara = (new Macaroni(0, 0, this, false));
			break;
		case "ryo":
			chara = new Ryo(0, 0, this, false);
			break;
		}
		return chara;
	}

	public GameState getCurrentState() {
		return currentState;
	}

	public void puddleTimers() {
		for (int c = 0; c < fP.size(); c++) {
			if (fP.get(c) instanceof Puddle) {
				((Puddle) fP.get(c)).timerGo();
			}
		}
	}

	public boolean atAllOnScreen(Shape rect) {
		if (rect.intersects(new Rectangle(0, 0, Statics.BOARD_WIDTH, Statics.BOARD_HEIGHT)))
			return true;
		for (int c = 0; c < friends.size(); c++) {
			if (rect.intersects(new Rectangle(friends.get(c).getX() - Statics.BOARD_WIDTH / 2, friends.get(c).getY() - Statics.BOARD_HEIGHT / 2,
					Statics.BOARD_WIDTH, Statics.BOARD_HEIGHT)))
				return true;
		}

		return false;
	}

	public boolean isCorruptedWorld() {
		return corruptedWorld;
	}

	public void setCorruptedWorld(boolean corruptedWorld) {
		this.corruptedWorld = corruptedWorld;
	}

	// protected static final Color c = new Color(255, 96, 0);

	protected static final Color[] list = { Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.MAGENTA, Color.CYAN, Color.ORANGE, Color.PINK,
			Color.WHITE };
	protected static final int BH_10 = Statics.BOARD_HEIGHT / 10;
	protected static final int BW_10 = Statics.BOARD_WIDTH / 10;
	protected static final int BH_20 = Statics.BOARD_HEIGHT / 20;
	protected static final int BW_20 = Statics.BOARD_WIDTH / 20;

	protected void drawCorruptedBorder(Graphics2D g2d) {

		g2d.setStroke(new BasicStroke(5));
		// g2d.setColor(Statics.PURPLE);
		g2d.setColor(Color.GREEN);
		g2d.fillRect(0, Statics.BOARD_HEIGHT / 2, BW_10, BH_20);
		g2d.setColor(Color.BLUE);
		g2d.fillRect(Statics.BOARD_HEIGHT - BW_10, Statics.BOARD_HEIGHT / 2, BW_10, BH_20);
		g2d.setColor(Color.RED);
		g2d.fillRect(Statics.BOARD_WIDTH / 2, 0, BW_20, BH_10);
		g2d.setColor(Color.YELLOW);
		g2d.fillRect(Statics.BOARD_WIDTH / 2, Statics.BOARD_HEIGHT - BH_10, BW_20, BH_10);
		// g2d.setColor(Color.MAGENTA);
		// g2d.fillRect(Statics.BOARD_WIDTH - BW_10, 0, BW_10, BH_10);
		// g2d.setColor(Statics.PURPLE);
		// g2d.fillRect(Statics.BOARD_WIDTH - BW_20, Statics.BOARD_HEIGHT -
		// BH_10, BW_20, BH_10);
		g2d.setColor(Statics.ORANGE);
		g2d.fillRect(BW_10, Statics.BOARD_HEIGHT - BH_10 * 2, BW_20, (int) (BH_10 * 1.5));
		g2d.setColor(Color.ORANGE);
		g2d.fillRect(Statics.BOARD_WIDTH - BW_10, Statics.BOARD_HEIGHT / 3, BW_20, BH_10);

		for (int i = 0; i < Statics.RAND.nextInt(8) + 3; i++) {
			g2d.setColor(list[Statics.RAND.nextInt(list.length)]);
			g2d.drawLine(Statics.RAND.nextInt(Statics.BOARD_WIDTH), Statics.RAND.nextInt(Statics.BOARD_HEIGHT),
					Statics.RAND.nextInt(Statics.BOARD_WIDTH), Statics.RAND.nextInt(Statics.BOARD_HEIGHT));
		}
	}

	private ActionMenu actionMenu;

	public void setActionMenu(ActionMenu actionMenu) {
		this.actionMenu = actionMenu;
	}

	private OrderMenu orderMenu;

	public void setOrderMenu(OrderMenu orderMenu) {
		// TODO Auto-generated method stub
		this.orderMenu = orderMenu;
	}

	public int getGeneralTime() {
		return time.getGeneralTime();
	}
	public void newLevel(String name){
		timer.stop();
		time.pause();
		level = name;
		changeArea();
		timer.restart();
		time.resume();
	}
}

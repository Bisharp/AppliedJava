package com.dig.www.start;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;

import javax.swing.JOptionPane;
import javax.swing.Timer;

import com.dig.www.blocks.*;
import com.dig.www.blocks.Block.Blocks;
import com.dig.www.npc.*;
import com.dig.www.objects.*;
import com.dig.www.util.*;
import com.dig.www.character.*;
import com.dig.www.character.GameCharacter.Types;
import com.dig.www.enemies.*;

public class Board extends MPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public Point pointedPoint;
public int pointedPointType=-1;
	public enum State {
		INGAME, PAUSED, QUIT, SHOP, LOADING, DEAD, NPC;
	};

	public static Preferences preferences;
	static {
		preferences = new Preferences();
	}

	public static final String DEFAULT = "LuigisMansion";
	private Timer timer;
	protected String userName;
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
	private ArrayList<Portal> portals = new ArrayList<Portal>();

	private int scrollX = 0;
	private int scrollY = 0;
	private int spawnX;
	private int spawnY;
	public static final int DEFAULT_X = 325;
	public static final int DEFAULT_Y = 350;
	boolean levelChanged;
	private DigIt owner;
	private Image sky = Statics.newImage("images/sky.png");
	private boolean isDay = true;
	private boolean switching = false;
	private TexturePack texturePack = TexturePack.GRASSY;
	private int startPoint = 0;

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

	public Board(DigIt dM, String name) {
		this.userName = name;
		character = new Spade(Statics.BOARD_WIDTH / 2 - 50, Statics.BOARD_HEIGHT / 2 - 50, this, true);
		friends.clear();
		friends.add(new Heart(Statics.BOARD_WIDTH / 2 + 150, Statics.BOARD_HEIGHT / 2 - 50, this, false));
		friends.add(new Diamond(Statics.BOARD_WIDTH / 2 + 150, Statics.BOARD_HEIGHT / 2 + 50, this, false));
		friends.add(new Club(Statics.BOARD_WIDTH / 2, Statics.BOARD_HEIGHT / 2 + 150, this, false));

		this.addMouseListener(new PersonalMouse());

		owner = dM;
		timer = new Timer(15, this);

		owner.setFocusable(false);

		addKeyListener(new TAdapter());
		setFocusable(true);

		setDoubleBuffered(true);
		state = State.INGAME;
		setSize(Statics.BOARD_WIDTH, Statics.BOARD_HEIGHT);

		timer.start();
		Collections.sort(friends);
	}

	public void changeArea() {
pointedPoint=null;
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

		StageBuilder sB = StageBuilder.getInstance(level, this);
		sB.changeState(level, this);
		setTexturePack(sB.readText());
		world = sB.read();
		enemies = sB.loadEn();
		portals = sB.loadPortals();
		npcs = sB.loadNPC();
		objects = sB.loadObjects();

		if (data != null)
			data.enterLevel(level);
		else
			data = new CharData(level, this);

		objects = data.filter(objects);
		npcs = data.filterNPC(npcs);

		for (Objects o : objects)
			if (o instanceof DropPoint)
				if (((DropPoint) o).hasDrop()) {
					npcs.add(new Chest(o.getX(), o.getY(), "images/objects/chestC.png", this, level, ((DropPoint) o).type()));
				}

		if (character.getType() == Types.SPADE) {
			((Spade) character).resetDirt();
		}

		for (int c = 0; c < friends.size(); c++) {
			if (friends.get(c).getType() == Types.SPADE) {
				((Spade) friends.get(c)).resetDirt();
			}

			if (c > 1) {
				friends.get(c).setX(Statics.BOARD_WIDTH / 2 - 50);
				friends.get(c).setY(Statics.BOARD_HEIGHT / 2 - 50 - ((c - 1) * 100));
			} else {
				friends.get(c).setX(Statics.BOARD_WIDTH / 2 - 50 + (c * 100));
				friends.get(c).setY(Statics.BOARD_HEIGHT / 2 - 50);
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

		setBackground(getTextureBack());

		save();
		System.gc();
	}

	public int spawnTreasure(int i, int keyNum) {
		return 0;
	}

	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2d = (Graphics2D) g;

		switch (state) {

		case NPC:
		case INGAME:

			// Tag boolean part of line-of-sight
			boolean tag = true;
			int i;
			Enemy e;
			Polygon poly;

			// World draw
			for (i = startPoint; i < world.size(); i++) {
				if (world.get(i).isOnScreen() && world.get(i).isVisible())
					world.get(i).draw(g2d);
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
					if (tag || e instanceof Boss)
						enemies.get(i).draw(g2d);
				}
			}

			// FProjectile draw
			FProjectile p;
			for (i = 0; i < fP.size(); i++) {

				if (fP.get(i).isOnScreen()) {

					if (!(fP.get(i) instanceof Field)) {
						p = fP.get(i);
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

					if (tag)
						fP.get(i).draw(g2d);
				}
			}

			// Objects draw
			for (Objects npc : objects)

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
					if (tag)
						npc.draw(g2d);
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

					if (tag)
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

					if (tag)
						npc.draw(g2d);
				}

//			 g2d.setColor(Color.ORANGE);
//			 //boolean back=false;
//			
//			 int roundX=(int)Math.ceil((character.getX()+40)/100);
//			 int modX=Math.abs((int)((world.get(0).getX())%100));
//			 if(modX<11&&(world.get(0).getX()<0)){
//			 roundX--;
//			 }
//			 roundX*=100;
//			 g2d.fillRect(roundX
//			 +(world.get(0).getX()<0?100:0)+
//			 ((world.get(0).getX()<0?-1:1)*Math.abs(world.get(0).getX()%100))
//			 ,
//			 ((int)((character.getY()+30)/100))*100
//			 +(world.get(0).getY()%100)+100//-11
//			 , 100, 100)
//			 ;
			if(pointedPoint!=null){
				if(pointedPointType==-1)
				g2d.drawImage(DigIt.lib.checkLibrary("/images/pointed/go.png"),(int)pointedPoint.getX()-50, (int)pointedPoint.getY()-50,this);
				else
					g2d.drawImage(DigIt.lib.checkLibrary("/images/pointed/attack.png"),(int)pointedPoint.getX()-50, (int)pointedPoint.getY()-50,this);
				
			}
			for (GameCharacter character : friends) {

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
				character.draw(g2d);
//				 if (character.getPPath() != null) {
//				
//				
//				 for (int c =character.getPPath().getPoints().size()-1 ; c >=
//				 0; c--) {
//				 g2d.setColor(new Color(255, 255, 0));
//				 if (c == character.getPPath().getPoints().size()-1)
//				 g2d.fillRect(character.getPPath().getPoint(c).x,
//				 character.getPPath().getPoint(c).y, 10, 10);
//				 else
//				 g2d.drawLine(character.getPPath().getPoint(c).x,
//				 character.getPPath().getPoint(c).y,
//				 character.getPPath().getPoint(c+1).x,
//				 character.getPPath().getPoint(c+1).y);
//				 g2d.setColor(new Color(255, 0, 0));
//				 g2d.drawString(""+c, character.getPPath().getPoint(c).x,
//				 character.getPPath().getPoint(c).y);
//				
////				 else
////				 g2d.drawString("" +c, character.getPPath().getPoint(c).x,
////				  character.getPPath().getPoint(c).y);
//				 }
//				 }
			}

			character.draw(g2d);
//			 g2d.setColor(Color.BLUE);
//			 g2d.fillRect(character.getX()+40, character.getY()+40, 5, 5);
			if (!isDay)
				g2d.drawImage(sky, 0, 0, Statics.BOARD_WIDTH, Statics.BOARD_HEIGHT, this);

			if (state == State.NPC) {

				current.drawOption(g2d);
			}
			break;

		case PAUSED:

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

		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	public void openSwitchDialogue() {

		scrollX *= -2;
		scrollY *= -2;
		reAnimate();
		repaint();

		timer.stop();

		character.stop();
		scrollX = 0;
		scrollY = 0;

		switching = false;

		// char[] names = {'S', 'C', 'D', 'H'};
		String decision;

		decision = ((String) JOptionPane.showInputDialog(this, "Please select a character: ", DigIt.NAME, JOptionPane.PLAIN_MESSAGE, Statics.ICON,
				getCharacters(), null));

		if (decision == null) {
			timer.restart();
			return;
		}

		if (!decision.equals(character.getType().charName())) {

			GameCharacter current = character;
			int friendNum = getFriend(decision);
			character = friends.get(friendNum);
			friends.set(friendNum, current);
			character.setPlayer(true);
			friends.get(friendNum).setPlayer(false);
			character.stop();
			scroll(Statics.BOARD_WIDTH / 2 - 50 - character.getX(), (int) Statics.BOARD_HEIGHT / 2 - 50 - character.getY());
			Collections.sort(friends);
		}
		timer.restart();
	}

	private String[] getCharacters() {

		ArrayList<String> s0 = new ArrayList<String>();

		for (GameCharacter friend : friends)
			s0.add(friend.getType().charName());

		String[] s = new String[friends.size()];

		if (!s0.contains(GameCharacter.Types.SPADE.charName())) {
			s[0] = GameCharacter.Types.CLUB.charName();
			s[1] = GameCharacter.Types.HEART.charName();
			s[2] = GameCharacter.Types.DIAMOND.charName();
		} else if (!s0.contains(GameCharacter.Types.CLUB.charName())) {
			s[0] = GameCharacter.Types.SPADE.charName();
			s[1] = GameCharacter.Types.HEART.charName();
			s[2] = GameCharacter.Types.DIAMOND.charName();
		} else if (!s0.contains(GameCharacter.Types.HEART.charName())) {
			s[0] = GameCharacter.Types.SPADE.charName();
			s[1] = GameCharacter.Types.CLUB.charName();
			s[2] = GameCharacter.Types.DIAMOND.charName();
		} else if (!s0.contains(GameCharacter.Types.DIAMOND.charName())) {
			s[0] = GameCharacter.Types.SPADE.charName();
			s[1] = GameCharacter.Types.CLUB.charName();
			s[2] = GameCharacter.Types.HEART.charName();
		} else {
			s[0] = GameCharacter.Types.SPADE.charName();
			s[1] = GameCharacter.Types.CLUB.charName();
			s[2] = GameCharacter.Types.HEART.charName();
			s[3] = GameCharacter.Types.DIAMOND.charName();
		}

		int i = 3;
		if (s0.size() > i)
			for (String s1 : s0)
				if (!normalPlayer(GameCharacter.Types.translateCharName(s1))) {
					s[i] = s1;
					i++;
				}

		return s;
	}

	private void scroll(int x, int y) {

		character.setX(character.getX() + x);
		character.setY(character.getY() + y);

		for (Block b : world) {
			b.setX(b.getX() + x);
			b.setY(b.getY() + y);
		}
		for (GameCharacter b : friends) {
			b.setX(b.getX() + x);
			b.setY(b.getY() + y);
		}
		for (Enemy b : enemies) {
			b.setX(b.getX() + x);
			b.setY(b.getY() + y);
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
		}
		for (Objects b : objects) {
			b.setX(b.getX() + x);
			b.setY(b.getY() + y);
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

		switch (state) {

		case INGAME:

			character.animate();
			for (GameCharacter character : friends) {
				character.animate();
			}

			for (int i = 0; i < enemies.size(); i++) {

				if (!enemies.get(i).isAlive()) {
					enemies.remove(i);
					i--;
					continue;
				}

				enemies.get(i).animate();
				enemies.get(i).setOnScreen(enemies.get(i).getBounds().intersects(getScreen()));
				// /\
				// || Nightmare Fuel
			}
if(pointedPoint!=null){
	pointedPoint.x+=scrollX;
pointedPoint.y+=scrollY;}
			for (int i = 0; i < fP.size(); i++) {

				if (!fP.get(i).isOnScreen()) {

					if (fP.get(i).getMove() == Moves.CHAIN) {
						if(fP.get(i).getCharNum()==-2){
						fP.add(new FProjectile(fP.get(i).getD() - 180, fP.get(i).getX(), fP.get(i).getY(), fP.get(i).getSpeed(),
								fP.get(i).getMaker(), fP.get(i).getLoc(), fP.get(i).getOwner(), Moves.CHAIN, -1, false));
						fP.remove(i);
						}
						}
					else
					fP.remove(i);
					i--;
					continue;
				} else if (fP.get(i).getCharNum() != -2) {
					GameCharacter chara;
					int charNum = fP.get(i).getCharNum();
					if (charNum == -1) {
						chara = character;
					} else {
						chara = friends.get(charNum);

					}
					if (fP.get(i).getBounds().contains(new Point(chara.getMidX(),chara.getMidY()))) {
						fP.remove(i);
						i--;
						continue;
					}
				}

				fP.get(i).animate();
				fP.get(i).setOnScreen(fP.get(i).getBounds().intersects(getScreen()));
				// /\
				// || Nightmare Fuel
			}

			if (switching)
				openSwitchDialogue();

			setCharacterStates(character.getCollisionBounds());
			repaint();
			for (int c = 0; c < friends.size(); c++) {
				for (int c2 = 0; c2 < friends.size(); c2++) {
					if (c == c2) {

					} else {
						if (!friends.get(c).getWallBound() && !friends.get(c2).getWallBound()) {

							if (friends.get(c).getBounds().intersects(friends.get(c2).getBounds())) {
								friends.get(c).collision(friends.get(c2).getMidX(), friends.get(c2).getMidY(), true);
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

		default:
			break;
		}
	}

	// Beginning of checkCollisions()-related code

	public void setCharacterStates(Rectangle r3) {

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

			if (!b.traversable() && b.getBounds().intersects(r3)) {

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
					character.collision(b.getMidX(), b.getMidY(), false);
					break;
				}
			} else if ((character.getMove() == Moves.CLUB &&!character.hasMeleed()&& b.getType() == Blocks.CRYSTAL)
					|| (character.getMove() == Moves.PIT &&!character.hasSpecialed()&& (b.getType() == Blocks.GROUND || b.getType() == Blocks.DIRT || b.getType() == Blocks.PIT))) {
				if (b.getBounds().intersects(character.getActBounds()) && !b.getBounds().intersects(character.getCollisionBounds())) {

					b.interact();
					character.endAction();
				}
			}
			for (GameCharacter character : friends) {
				Rectangle r2 = character.getCollisionBounds();
				if (b.getType() != Block.Blocks.GROUND && b.getBounds().intersects(r2)) {

					switch (b.getType()) {

					// Cases for the floor
					case GROUND:
					case ROCK:
					case CARPET:
					case DIRT:
						break;
					case PIT:
					case WALL:
					case CRYSTAL:
					case LIQUID:
						character.collision(b.getMidX(), b.getMidY(), false);
					}
				} else if ((character.getMove() == Moves.CLUB&&!character.hasMeleed() && b.getType() == Blocks.CRYSTAL)
						|| (character.getMove() == Moves.PIT&&!character.hasSpecialed() && (b.getType() == Blocks.GROUND || b.getType() == Blocks.DIRT || b.getType() == Blocks.PIT))) {
					if (b.getBounds().intersects(character.getActBounds()) && !b.getBounds().intersects(character.getCollisionBounds())) {

						b.interact();
						character.endAction();
					}
				}
			}

			if (b.isOnScreen()) {
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
				Enemy e;
				boolean bashHit=false;
				int shieldNum=-1;
				for (int u = 0; u < enemies.size(); u++) {

					e = enemies.get(u);
					if (e.isOnScreen()) {
						if (e.getBounds().intersects(b.getBounds())) {
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
						
						if (character.getActing() > 0 && character.getActBounds().intersects(e.getBounds())) {
							e.interact(character.getMove(), character, false);
							if(character.getMove()==Moves.BASH)
								bashHit=true;
						}
						for (int c = 0; c < fP.size(); c++) {
							FProjectile character = fP.get(c);
							o = character instanceof Irregular ? ((Irregular) character).getIrregularBounds() : character.getBounds();

							// This modification would allow us to make certain
							// projectiles behave differently with their bounds;
							// could be implemented with other objects.

							if (o.intersects(e.getBounds()) && character.isOnScreen() && character.getHarming()) {
								if (!(e instanceof Projectile) || (character instanceof Field)) {
									e.interact(character.getMove(), character.getMaker(), true);
									fP.get(c).setOnScreen(false);
								}
							}
						}

						for (int c=0;c<friends.size();c++) {
							GameCharacter character=friends.get(c);
							if (character.getActing() > 0 && character.getActBounds().intersects(e.getBounds())) {
								e.interact(character.getMove(), character, false);
							if(character.getMove()==Moves.BASH){
								bashHit=true;
								shieldNum=c;}
							}
						}
						
							
						if (e.getBounds().intersects(r3) && e.willHarm()) {
							e.turnAround(character.getX(), character.getY());
							character.takeDamage(e.getDamage());
							
						}

						for (GameCharacter character : friends) {
							Rectangle r2 = character.getBounds();
							if (e.getBounds().intersects(r2) && e.willHarm()) {
								e.turnAround(character.getX(), character.getY());
								character.takeDamage(e.getDamage());
							}
						}
					}
				}
				if(bashHit){
					if(shieldNum==-1)
						character.endAction();
					else
						friends.get(shieldNum).endAction();
				}
				// end of enemy loop

				if (movingObjects.size() > 0)
					for (Objects o0 : movingObjects)
						if (o0.getBounds().intersects(b.getBounds()) && !b.traversable())
							o0.collideWall();

				
			}
		}
for(GameCharacter friend:friends){
	if (friend.getMove() == Moves.AURA&&!friend.hasMeleed()) {

		boolean healed = false;
		if (character.getActBounds().intersects(friend.getBounds())) {
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
		if (character.getMove() == Moves.AURA&&!character.hasMeleed()) {

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

		// Moving objects colliding stuff
		if (movingObjects.size() > 0)
			for (Objects o0 : movingObjects) {
				for (Objects o1 : objects)
					if (o0.getBounds().intersects(o1.getBounds()) && o1.isWall() && o0 != o1)
						o0.collideWall();
				for (NPC n : npcs)
					if (o0.getBounds().intersects(n.getBounds()))
						o0.collideWall();
				for (Portal n : portals)
					if (o0.getBounds().intersects(n.getBounds()))
						o0.collideWall();
			}
		// end

		Portal p;
		for (int i = 0; i < portals.size(); i++) {

			p = portals.get(i);
			p.animate();
			p.setOnScreen(p.getBounds().intersects(getScreen()));

			if (r3.intersects(p.getBounds())) {
				timer.stop();
				level = p.getArea();
				changeArea();
				timer.restart();
			}
		}

		Rectangle bounds = character.getTalkBounds();
		for (NPC n : npcs) {
			n.animate();
			n.setOnScreen(n.getBounds().intersects(getScreen()));

			if (n.isOnScreen()) {
				if (r3.intersects(n.getBounds()))
					character.collision(n.getMidX(), n.getMidY(), false);
				if (bounds != null && n.getBounds().intersects(bounds)) {
					current = n;
					current.setLine();
					state = State.NPC;
					bounds = null;
				}
			}
		}

		Objects n;
		for (int u = 0; u < objects.size(); u++) {

			n = objects.get(u);
			n.animate();
			n.setOnScreen(n.getBounds().intersects(getScreen()));
			o = n instanceof Irregular ? ((Irregular) n).getIrregularBounds() : n.getBounds();

			// TODO collectible
			if (o.intersects(character.getCollisionBounds())) {
				n.collidePlayer(-1);

				if (n instanceof Collectible && ((Collectible) n).collectible())
					if (n instanceof MoneyObject) {
						Statics.playSound(this, "collectibles/marioCoin.wav");
						GameCharacter.getInventory().addMoney(((MoneyObject) n).getValue());
						objects.remove(u);
						u--;
					} else if (n instanceof SpecialCollectible) {
						Statics.playSound(this, "collectibles/marioCoin.wav");
						GameCharacter.getInventory().addItem(((Collectible) n).getType(), 1);
						data.collect(((SpecialCollectible) n).id);
						objects.remove(u);
						u--;

						// This code would, once fully implemented, add an extra
						// character following you. You would be able to switch
						// to him.
					} else if (n instanceof CollectibleCharacter) {
						friends.add(((CollectibleCharacter) n).getCharacter());
						objects.remove(u);
						u--;
					} else if (n instanceof CollectibleObject) {
						GameCharacter.getInventory().addItem(((Collectible) n).getType(), 1);
						objects.remove(u);
						u--;
					}
			}

			for (int c = 0; c < friends.size(); c++) {

				if (n.getBounds().intersects(friends.get(c).getCollisionBounds())) {
					n.collidePlayer(c);
				}
			}
		}

	}

	@Override
	public void keyPress(int key) {
		// Show me ya moves! }(B-)
if(key==KeyEvent.VK_J){
	if(pointedPoint==null){
		
		pointedPoint=MouseInfo.getPointerInfo().getLocation();
	}else
		pointedPoint=null;
}
		if (key == Preferences.CHAR_CHANGE() && state != State.NPC)
			switching = true;
		else if (key == KeyEvent.VK_EQUALS)
			JOptionPane.showMessageDialog(owner, Preferences.getControls(), DigIt.NAME, JOptionPane.INFORMATION_MESSAGE);

		else if (state != State.NPC && key == KeyEvent.VK_ESCAPE) {

			if (state != State.DEAD)
				state = State.PAUSED;

			Statics.exit(this);
		}
		switch (state) {

		// TODO npc
		case NPC:
			if (key == Preferences.NPC())
				current.exit();

			break;

		case PAUSED:
			pausedHandler(key);
			break;

		case INGAME:

			if (key == Preferences.PAUSE()) {
				state = State.PAUSED;
				repaint();
				return;
			}

			ingameHandler(key);
			break;

		default:
			break;
		}

		repaint();
	}

	@Override
	public void keyRelease(int key) {
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

	public void ingameHandler(int key) {

		character.keyPressed(key);
	}

	private void pausedHandler(int key) {
		if (key == Preferences.PAUSE())
			state = State.INGAME;
	}

	public void setState(State state) {
		this.state = state;
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
if(pointedPoint!=null){
	pointedPoint.x+=scrollX;
	pointedPoint.y+=scrollY;
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
		case GRASSY:
		default:
			return Statics.OFF_GREEN;
		}
	}

	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}

	public void save() {
		String location = (GameStartBoard.class.getProtectionDomain().getCodeSource().getLocation().getFile().toString() + "saveFiles/" + userName + "/");
		File loc = new File(location);
		if (loc.exists()) {
			File locFile = new File(location + userName + ".txt");
			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter(locFile));
				writer.write(level + "," + GameCharacter.getLevel() + "," + GameCharacter.getXP());
				writer.newLine();
				if (normalPlayer(character.getType()))
					writer.write(character.getSave());
				for (int c = 0; c < friends.size(); c++) {
					if (normalPlayer(friends.get(c).getType())) {

						writer.newLine();
						writer.write(friends.get(c).getSave());
					}
				}
				// writer.newLine();
				// writer.write(character != null ? "" +
				// character.getInventory().getMoney() : "0");
				writer.close();

				ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(location + "data.ser"));
				os.writeObject(data);
				os.close();

				// TODO working on inventory
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

	public void startGame() {

	}

	public void loadSave() {
		level = DEFAULT;
		try {
			String location = (GameStartBoard.class.getProtectionDomain().getCodeSource().getLocation().getFile().toString() + "saveFiles/"
					+ userName + "/");
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
						String lev = stuff.get(0);
						level = lev;
						int levUp = Integer.parseInt(stuff.get(1));
						GameCharacter.setLevel(levUp);
						int xp = Integer.parseInt(stuff.get(2));
						GameCharacter.setXP(xp);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				for (int c = 1; c < 5; c++) {
					// int pos=-1;
					String name = "spade";
					if (lines.get(c).startsWith("shovel"))
						name = "shovel";
					else if (lines.get(c).startsWith("heart"))
						name = "heart";
					else if (lines.get(c).startsWith("diamond"))
						name = "diamond";
					else if (lines.get(c).startsWith("club"))
						name = "club";

					if (character.getType().toString().equals(name)) {
						character.load(lines.get(c).substring(name.length() + 1));

					} else {
						for (int cA = 0; cA < friends.size(); cA++) {
							if (friends.get(cA).getType().toString().equals(name)) {
								friends.get(cA).load(lines.get(c).substring(name.length() + 1));
								break;
							}
						}
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
				} catch (Exception badThing) {
					badThing.printStackTrace();
				}
				changeArea();
			} else {
				throw new FileNotFoundException();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			newGame();
		}
	}

	public void newGame() {
		level = "hauntedTest";
		preferences = new Preferences();
		GameCharacter.setInventory(new Inventory(this));
		changeArea();
		preferences.save(Preferences.class.getProtectionDomain().getCodeSource().getLocation().getFile().toString() + "saveFiles/"
				+ owner.getUserName() + "/");
	}

	public ArrayList<Objects> getObjects() {
		return objects;
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

		// TODO inventory
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
}

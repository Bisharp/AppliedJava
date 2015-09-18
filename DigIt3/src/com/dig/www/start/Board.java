package com.dig.www.start;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.Timer;

import com.dig.www.blocks.*;
import com.dig.www.util.*;
import com.dig.www.character.*;
import com.dig.www.enemies.*;

public class Board extends MPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public enum State {
		INGAME, PAUSED, QUIT, SHOP, LOADING, DEAD;
	};

	private Timer timer// = new Timer(15, this)
	;
	private GameCharacter character;
	private State state;
	private boolean debug = false;

	private int deadTimer = 100;

	private ArrayList<Block> world;
	private ArrayList<Block> wallList;
	private ArrayList<Enemy> enemies;

	private int scrollX = 0;
	private int scrollY = 0;
	private int spawnX;
	private int spawnY;
	public static final int DEFAULT_X = 325;
	public static final int DEFAULT_Y = 350;

	private DigIt owner;
	private Image sky = Statics.newImage("images/sky.png");
	private boolean isDay = true;
	private boolean switching = false;

	// Yes, I put my getters/setters for scrollX & scrollY here.
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

	// Get used to it.

	public DigIt getOwner() {
		return owner;
	}

	public void setOwner(DigIt owner) {
		this.owner = owner;
	}

	// * _
	// */ \
	// * | Getters/setters for owner

	public Board(DigIt dM, String name, int which) {

		character = new Spade(Statics.BOARD_WIDTH / 2 - 50, Statics.BOARD_HEIGHT / 2 - 50, this);
		world = StageBuilder.getInstance().read("funLittleMap", this);
		enemies = StageBuilder.getInstance().loadEn("funLittleMap", this);

		wallList = new ArrayList<Block>();
		owner = dM;
		timer = new Timer(15, this);

		owner.setFocusable(false);

		for (Block b : world) {

			b.initialAnimate(spawnX, spawnY);

			// if (b instanceof EnemyBlock) {
			// spawnEnemy(((EnemyBlock) b).getEnemyType(), b.getX(), b.getY());
			// }

			// Deals with line-of-sight
			if (b.getType() == Block.Blocks.WALL)
				wallList.add(b);

		}

		for (Enemy e : enemies) {
			e.initialAnimate(spawnX, spawnY);
			e.setAlive(true);
			e.resetImage(this);
		}

		addKeyListener(new TAdapter());
		setFocusable(true);
		setBackground(Statics.OFF_GREEN);

		setDoubleBuffered(true);
		state = State.INGAME;
		setSize(Statics.BOARD_WIDTH, Statics.BOARD_HEIGHT);

		timer.start();
	}

	public int spawnTreasure(int i, int keyNum) {
		return 0;
	}

	public void spawnEnemy(char c, int x, int y) {

		switch (c) {
		case '0':
			enemies.add(new Launch(x, y, "images/enemies/turrets/" + Statics.RAND.nextInt(Statics.getFolderCont("images/enemies/turrets/")) + ".png",
					this, 75, false));
			break;

		case '1':
			enemies.add(new Launch(x, y, "images/enemies/unique/machineLaunch.png", this, 20, false));
			break;

		case 'W':
			enemies.add(new WalkingEnemy(x, y, "images/enemies/unique/tv.png", this, false));
			break;

		case 'T':
			enemies.add(new TrackingEnemy(x, y, "images/enemies/unique/chair.png", this, false));
			break;

		case 'F':
			enemies.add(new WalkingEnemy(x, y, "images/enemies/unique/ghost.png", this, true));
			break;

		case 'S':
			enemies.add(new StandEnemy(x, y, "images/enemies/unique/tires.png", this, true));
			break;
		}
	}

	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2d = (Graphics2D) g;

		switch (state) {

		case INGAME:

			// Tag boolean part of line-of-sight
			boolean tag = true;
			Enemy e;

			for (int i = 0; i < world.size(); i++) {
				if (world.get(i).isOnScreen())
					world.get(i).draw(g2d);
			}

			for (int i = 0; i < enemies.size(); i++) {

				if (enemies.get(i).isOnScreen()) {

					e = enemies.get(i);
					// Line-of-sight mechanics
					int[] xs = { e.getMidX() - 10, character.getMidX() - 10, character.getMidX() + 10, e.getMidX() + 10 };
					int[] ys = { e.getMidY() - 10, character.getMidY() - 10, character.getMidY() + 10, e.getMidY() + 10 };

					for (int x = 0; x < wallList.size(); x++) {
						if (wallList.get(x).isOnScreen() && new Polygon(xs, ys, xs.length).intersects(wallList.get(x).getBounds())) {
							tag = false;
							break;
						}

						tag = true;
					}
					// end of that code

					if (tag)
						enemies.get(i).draw(g2d);
				}
			}

			character.draw(g2d);

			if (!isDay)
				g2d.drawImage(sky, 0, 0, this);

			break;

		case PAUSED:
			g2d.setColor(Color.BLACK);
			g2d.fill(getScreen());

			g2d.setColor(Color.GREEN);
			g2d.setFont(Statics.MENU);
			g2d.drawString(state.toString(), getWidth() / 3, getHeight() / 3);
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
		// TODO

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
				new String[] { "Clark", "Carl", "Destiny", "Cain" }, null));

		if (decision == null) {
			timer.restart();
			return;
		}

		if (!decision.equals(character.getType().charName()))
			switch (decision) {
			case "Clark":
				character = new Spade(Statics.BOARD_WIDTH / 2 - 50, Statics.BOARD_HEIGHT / 2 - 50, this);
				break;

			case "Carl":
				character = new Club(Statics.BOARD_WIDTH / 2 - 50, Statics.BOARD_HEIGHT / 2 - 50, this);
				break;

			case "Cain":
				character = new Diamond(Statics.BOARD_WIDTH / 2 - 50, Statics.BOARD_HEIGHT / 2 - 50, this);
				break;

			case "Destiny":
				character = new Heart(Statics.BOARD_WIDTH / 2 - 50, Statics.BOARD_HEIGHT / 2 - 50, this);
				break;
			}

		timer.restart();
	}

	public void actionPerformed(ActionEvent e) {

		switch (state) {

		case INGAME:

			character.animate();

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

			if (switching)
				openSwitchDialogue();

			checkCollisions();
			repaint();
			break;

		case DEAD:
			deadTimer--;
			if (deadTimer == 0)
				owner.quit();
			repaint();
			break;

		default:
			break;
		}
	}

	// Beginning of checkCollisions()-related code

	public void checkCollisions() {

		Rectangle r3 = character.getCollisionBounds();

		setCharacterStates(r3);
	}

	public void setCharacterStates(Rectangle r3) {

		Block b;

		// GameCharacter.Types type = character.getType();
		boolean acting = character.isActing();
		boolean tag = false;

		for (int i = 0; i < world.size(); i++) {

			b = world.get(i);

			b.animate();
			b.setOnScreen(b.getBounds().intersects(getScreen()));

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

			if (b.getType() != Block.Blocks.GROUND && b.getBounds().intersects(r3)) {

				switch (b.getType()) {

				// Cases for the floor
				case GROUND:
				case ROCK:
				case CARPET:
				case DIRT:
					break;

				// Cases for raised obstructions
				case SWITCH:
					switching = true;
				case PIT:
				case WALL:
				case CRYSTAL:
					character.collision(b.getMidX(), b.getMidY());
				}
			} else if (acting) {
				if (b.getBounds().intersects(character.getActBounds()) && !b.getBounds().intersects(character.getCollisionBounds())) {

					b.interact();
					character.endAction();
				}
			}

			if (b.isOnScreen()) {
				Enemy e;
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
								e.turnAround();
								break;
								
							default:
								break;
							}
						}

						if (character.isActing() && character.getActBounds().intersects(e.getBounds())) {
							// TODO implement proper interaction code here
							e.interact(character.getType());
						}

						if (e.getBounds().intersects(r3) && e.willHarm()) {
							e.turnAround();
							character.takeDamage();
						}
					}
				}
				// end of enemy loop

			}
		}
	}

	@Override
	public void keyPress(int key) {
		// TODO Auto-generated method stub
		// Show me ya moves! }(B-)

		if (key == KeyEvent.VK_O)
			debug = !debug;
		else if (key == KeyEvent.VK_ESCAPE)
			System.exit(0);
		switch (state) {

		case PAUSED:
			pausedHandler(key);
			break;

		case INGAME:

			if (key == KeyEvent.VK_SHIFT) {
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
		// TODO Auto-generated method stub
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
		// if (key == KeyEvent.VK_SPACE) {
		// state = State.QUIT;
		// try {
		// Thread.sleep(100);
		// } catch (Exception ex) {
		// ex.printStackTrace();
		// }
		// } else
		if (key == KeyEvent.VK_BACK_SPACE || key == KeyEvent.VK_SHIFT) {
			state = State.INGAME;
		}
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
		// TODO Auto-generated method stub
		return character.getX();
	}

	public int getCharacterY() {
		return character.getY();
	}

	public void reAnimate() {
		// TODO Auto-generated method stub
		int i;

		for (i = 0; i < world.size(); i++)
			world.get(i).basicAnimate();

		for (i = 0; i < enemies.size(); i++)
			enemies.get(i).basicAnimate();
	}

	public GameCharacter getCharacter() {
		// TODO Auto-generated method stub
		return character;
	}

	public void setSpawnX(int spawnX) {
		this.spawnX = spawnX;
	}

	public void setSpawnY(int spawnY) {
		this.spawnY = spawnY;
	}

	public State getState() {
		// TODO Auto-generated method stub
		return state;
	}

	public Rectangle getScreen() {

		return new Rectangle(0, 0, Statics.BOARD_WIDTH, Statics.BOARD_HEIGHT);
	}

	public void addEnemy(Enemy toAdd) {
		// TODO Auto-generated method stub
		enemies.add(toAdd);
	}

	public Point getCharPoint() {
		// TODO Auto-generated method stub
		return new Point(character.getX(), character.getY());
	}
}
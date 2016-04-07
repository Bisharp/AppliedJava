package com.dig.www.games.Climb;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.dig.www.games.Climb.Character.States;
import com.dig.www.util.Statics;

public class Climb extends JFrame implements KeyListener, ActionListener {

	static final int TIMER_REFRAIN = 10;
	static final int GH = 600;
	static final int GW = 600;
	static final int SWITCH_MAX = 50;
	static final int TIMER_ADD = 10;
	static final Font CLIMB = new Font("Trebuchet MS", Font.BOLD, 30);
	static final int TIME_MAX = 15;
	static final int TIME_DIV = 10;
	static final int CAT_BONUS = 5;

	static final Image TIMER = Statics.newImage("images/climb/timer/timer.png");
	static final Image CLOCK = Statics.newImage("images/climb/other/clock.png");
	static final Image[] NUMS;
	static {
		NUMS = new Image[10];
		for (int i = 0; i < 10; i++)
			NUMS[i] = Statics.newImage("images/climb/timer/" + i + ".png");
	}

	private Timer t;
	private Pane pane;
	protected float timer = 0;

	protected float timeTimer = TIME_MAX;

	private enum GameState {
		SWITCH, GAME, MAIN, HIGH_SCORES
	}

	protected GameState myState;
	protected boolean flux = false;
	protected int switchTimer = 0;

	private Character player;
	private ArrayList<Object> world;
	private ArrayList<Object> toRemove;
	private ArrayList<Object> toAdd;

	private Color worldColor = new Color(0, 90, 0);
	private Color backColor = new Color(0, 150, 0);

	class CRunnable implements Runnable {

		protected int timer = 0;
		protected boolean keepGoing = true;

		@Override
		public void run() {
			while (keepGoing) {
				try {
					Thread.sleep(1);
					if (timer < Integer.MAX_VALUE)
						timer++;
				} catch (InterruptedException e) {
					e.printStackTrace();
					keepGoing = false;
				}
			}
		}

		protected int getClear() {
			int temp = timer;
			timer = 0;
			return temp;
		}

		protected void end() {
			keepGoing = false;
		}
	}

	protected CRunnable c;

	public Climb() {
		setBackground(backColor);
		pane = new Pane();
		getContentPane().add(pane);
		setSize(GW, GH);
		addKeyListener(this);
		setAlwaysOnTop(true);
		setFocusable(true);
		setResizable(false);
		setUndecorated(true);
		requestFocus();
		PersonalMouse p = new PersonalMouse();
		addMouseListener(p);
		addMouseMotionListener(p);

		mainMenu();

		setVisible(true);
		t = new Timer(TIMER_REFRAIN, this);
		t.start();
	}

	protected int level = 1;

	protected void newGame() {
		flux = true;
		timeTimer = TIME_DIV * 3;
		level = 1;
		world = new WorldBuilder().getWorld(this, level);
		player = new Character(GW / 2, world.get(world.size() - 1).getY(), this);
		toRemove = new ArrayList<Object>();
		toAdd = new ArrayList<Object>();
		myState = GameState.GAME;
		timer = TIME_DIV * 4;
		c = new CRunnable();
		new Thread(c).start();
		centerScreen();
		flux = false;
	}

	protected void change() {
		flux = true;
		level++;
		world = new WorldBuilder().getWorld(this, level);
		
		if (player.hasCat())
			timer += CAT_BONUS;
		
		switchTimer = 0;
		myState = GameState.GAME;

		player.switchArea(world.get(world.size() - 1).getY());

		// TODOif (level % 4 == 0)
		world.add(new Boss("boss", this, true, level));

		c.getClear();
		centerScreen();
		flux = false;
	}

	protected void mainMenu() {
		flux = true;
		if (c != null)
			c.end();
		myState = GameState.MAIN;
		world = new ArrayList<Object>();
		world.add(new Object(50, 20, "images/climb/menu/climb.png", this));
		world.add(new MenuObject(50, 220, "images/climb/menu/start.png", this));
		world.add(new MenuObject(50, 320, "images/climb/menu/highScores.png", this));
		flux = false;
	}

	public class Pane extends JPanel {

		public void paint(Graphics g) {

			Graphics2D g2d = (Graphics2D) g;

			switch (myState) {

			case MAIN:
				for (Object o : world)
					g2d.drawImage(o.getImage(), o.getX(), o.getY(), this);
				break;

			case GAME:
			case SWITCH:
				g2d.setColor(worldColor);
				for (Object o : world)
					if (o.isOnScreen())
						if (o.getImage() == null) {
							if (o instanceof Enemy)
								g2d.setColor(Color.black);
							g2d.fill(o.getBounds());

							if (o instanceof Enemy)
								g2d.setColor(worldColor);
						} else if (o instanceof Enemy)
							((Enemy) o).draw(g2d);
						else
							g2d.drawImage(o.getImage(), o.getX(), o.getY(), this);

				// TODO character
				if (player.facingRight)
					g2d.drawImage(player.getImage(), player.getX(), player.getY(), this);
				else
					g2d.drawImage(player.getImage(), player.getX() + player.getWidth(), player.getY(), -player.getWidth(), player.getHeight(), this);

				if (player.fallenTooFar())
					g2d.drawImage(Statics.newImage("images/climb/other/!.png"), player.getX(), player.getY() - 40, this);

				// g2d.setFont(CLIMB);
				g2d.drawImage(TIMER, GW - GW / 4, GH / 10, this);
				// g2d.drawString("" + (int) timer, GW - GW / 3, GH / 10);
				g2d.drawImage(NUMS[calc(0)], GW - GW / 4 + 3, GH / 10 + 3, this);
				g2d.drawImage(NUMS[calc(1)], GW - GW / 4 + 33, GH / 10 + 3, this);

				for (int i = 0; i < getAdd() / 5; i++) {
					g2d.drawImage(CLOCK, 50 * i + 50, 25 /*
														 * +
														 * (Statics.RAND.nextInt
														 * (5) *
														 * (Statics.RAND.nextBoolean
														 * ()? -1 : 1))
														 */, this);
				}

				break;
			}
		}

		protected int calc(int which) {
			if (which == 0 && timer < 10)
				return 0;
			
			if (timer < 0)
				return 0;
			
			String s = timer + "";
			if (timer >= 10)
				return Integer.parseInt("" + s.charAt(which));
			else
				return Integer.parseInt("" + s.charAt(0));
		}
	}

	@Override
	public void actionPerformed(ActionEvent action) {

		switch (myState) {
		case GAME:
			gameRun();
			break;
		case SWITCH:
			switchTimer--;
			if (switchTimer <= 0)
				change();
		case MAIN:
		default:
		}

		repaint();
	}

	protected void centerScreen() {
		final int off = GH / 2 - player.getY();

		for (Object o : world)
			o.scrollY(off);
		player.scrollY(off);
	}

	protected void gameRun() {
		boolean falling = true;
		boolean eFall = true;
		Object o;
		Enemy e;
		int i;

		float gC = c.getClear() / 1000.0f;

		timer -= gC;
		timeTimer -= gC;
		if (timer <= 0)
			die();

		while (toRemove.size() > 0) {
			world.remove(toRemove.get(0));
			toRemove.remove(0);
		}
		while (toAdd.size() > 0) {
			world.add(toAdd.get(0));
			toAdd.remove(0);
		}

		player.animate();

		for (i = 0; i < world.size(); i++) {

			o = world.get(i);
			o.animate();
			o.setOnScreen(o.getBounds().intersects(getBounds()));
			if (o.isOnScreen() && o.getBounds().intersects(player.getBounds())) {
				if (o instanceof Ladder && o.getBounds().intersects(player.getCoreBounds())) {
					player.catchLadder(o.getX(), o.getY());
					falling = false;
				} else if (o instanceof Cat) {
					player.setCat(true);
					world.remove(o);
					i--;
				} else if (o instanceof Enemy) {
					if (player.hasCat()) {
						((Enemy) o).bump(player.getMidX());
						player.bump(((Enemy) o).getMidX());
					} else
						die();
				} else if (o instanceof Switch) {
					progress(((Switch) o));
					falling = false;
				} else {
					player.breakFall(o.getY());
					falling = false;
				}
			}

			if (o instanceof Enemy) {
				e = (Enemy) o;
				for (Object o2 : world) {
					if (o2 instanceof Enemy || o2 instanceof Switch || o2 instanceof Cat || o2 instanceof Ladder)
						continue;
					if (e.getBounds().intersects(o2.getBounds())) {
						e.setOnGround(true, o2.getY());
						eFall = false;
						break;
					}
				}

				if (eFall)
					e.setOnGround(false, 0);

				if (e.isOnGround()) {
					eFall = true;
					for (Object o2 : world) {
						if (o2 instanceof Enemy || o2 instanceof Ladder || o2 instanceof Cat)
							continue;
						if (e.getGBounds().intersects(o2.getBounds())) {
							eFall = false;
							break;
						}
					}
					if (eFall)
						e.switchDir();
				}
				eFall = true;
			}
		}

		if (falling)
			player.startFalling();
	}

	protected void progress(Switch o) {
		o.press();
		myState = GameState.SWITCH;

		if (level % 4 == 0) {
			switchTimer = SWITCH_MAX;
			timer += getAdd();
			if (timer > 99)
				timer = 99;
			timeTimer = TIME_DIV * 3;
		}
	}

	protected int getAdd() {
		if (timeTimer >= TIME_DIV * 2)
			return 15;
		else if (timeTimer >= TIME_DIV)
			return 10;
		else if (timeTimer >= 0)
			return 5;
		else
			return 0;
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		if (flux)
			return;

		switch (myState) {
		case GAME:
			player.press(arg0.getKeyCode());
			break;
		}

		if (arg0.getKeyCode() == KeyEvent.VK_ESCAPE)
			Statics.exit(pane);
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		if (flux)
			return;

		switch (myState) {
		case GAME:
			player.release(arg0.getKeyCode());
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}

	protected void die() {
		if (flux)
			return;

		JOptionPane.showMessageDialog(this, "You died.", "Climb", JOptionPane.WARNING_MESSAGE);
		// System.exit(0);
		mainMenu();
	}

	public static void main(String[] args) {
		new Climb();

		// String[] names = { "c_true" };
		// File f;
		//
		// for (String name : names) {
		//
		// f = new File("C:/Users/Owner/Desktop/stuff/" + name + ".png");
		// BufferedImage i = new BufferedImage(50, 50,
		// BufferedImage.TYPE_4BYTE_ABGR_PRE);
		// Graphics2D g2d = ((Graphics2D) i.getGraphics());
		// g2d.drawImage(Statics.newImage("images/climb/super/" + name +
		// ".png"), 0, 0, null);
		// g2d.drawImage(Statics.newImage("images/climb/other/cat2.png"), 5, 8,
		// null);
		// try {
		// ImageIO.write(i, "png", f);
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// }

		// int out = 0;
		//
		// for (int i = 0; i < 50; i++) {
		// if (i > 10)
		// out += 10;
		// else
		// out += i;
		// }
		//
		// System.out.println(out);
	}

	protected void addObj(Object toAdd) {
		this.toAdd.add(toAdd);
	}

	protected void removeObj(Object toRemove) {
		this.toRemove.add(toRemove);
	}

	protected class PersonalMouse implements MouseListener, MouseMotionListener {

		@Override
		public void mouseClicked(MouseEvent arg0) {
			if (flux)
				return;

			if (arg0.getButton() == MouseEvent.BUTTON1)
				switch (myState) {
				case MAIN:
					Rectangle mClick = new Rectangle(arg0.getX(), arg0.getY(), 5, 5);
					if (mClick.intersects(world.get(1).getBounds())) {
						newGame();
					}
					break;

				case HIGH_SCORES:
					myState = GameState.MAIN;
					break;
				default:
					break;
				}
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {

		}

		@Override
		public void mouseExited(MouseEvent arg0) {

		}

		@Override
		public void mousePressed(MouseEvent arg0) {

		}

		@Override
		public void mouseReleased(MouseEvent arg0) {

		}

		@Override
		public void mouseDragged(MouseEvent arg0) {
			mouseMoved(arg0);
		}

		@Override
		public void mouseMoved(MouseEvent arg0) {
			if (flux)
				return;

			switch (myState) {
			case MAIN:
				if (world.size() != 3)
					return;

				Rectangle mClick = new Rectangle(arg0.getX(), arg0.getY(), 10, 10);

				if (mClick.intersects(world.get(1).getBounds())) {
					((MenuObject) world.get(1)).setLight(true);
					((MenuObject) world.get(2)).setLight(false);
				} else if (mClick.intersects(world.get(2).getBounds())) {
					((MenuObject) world.get(2)).setLight(true);
					((MenuObject) world.get(1)).setLight(false);
				} else {
					((MenuObject) world.get(2)).setLight(false);
					((MenuObject) world.get(1)).setLight(false);
				}
				break;

			case HIGH_SCORES:
				myState = GameState.MAIN;
				break;
			default:
				break;
			}
		}

	}

	public Character getPlayer() {
		return player;
	}
}

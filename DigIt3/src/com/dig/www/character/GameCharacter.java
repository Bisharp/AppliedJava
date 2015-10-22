package com.dig.www.character;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Window.Type;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.dig.www.enemies.Enemy;
import com.dig.www.enemies.Projectile;
import com.dig.www.objects.Dispenser;
import com.dig.www.start.Board;
import com.dig.www.start.Board.State;
import com.dig.www.start.DigIt;
import com.dig.www.util.GameControllerRunnable;
import com.dig.www.util.Preferences;
import com.dig.www.util.Sprite;
import com.dig.www.util.Statics;

public abstract class GameCharacter extends Sprite implements Comparable<GameCharacter> {

	/**
	 * 
	 */
	Enemy enPoint;
	int enUp;
	protected LevelUp levMen;
	protected boolean levUp = false;
	protected PointPath path;
	protected static int xp;
	protected static int level;
	protected int myLevel;
	protected int enTimer = 0;
	private int wallX;
	private int wallY;
	private Rectangle collideRect;
	private Point getToPoint;
	private static final long serialVersionUID = 1L;
	private int dir = 0;
	private int pointTimer;
	private boolean meleePress = false;
	private boolean rangedPress = false;
	private boolean specialPress = false;
	private int me = -1;
	protected Wallet wallet;

	private boolean player;
	private boolean onceNotCollidePlayer;
boolean goTo=true;
	public enum Direction {
		UP, DOWN, LEFT, RIGHT;
	}

	public enum Types {

		CLUB {
			public String toString() {
				return "club";
			}

			@Override
			public String charName() {

				return "Carl";
			}
		},
		HEART {
			public String toString() {
				return "heart";
			}

			@Override
			public String charName() {

				return "Destiny";
			}
		},
		SPADE {
			public String toString() {
				return "shovel";
			}

			@Override
			public String charName() {

				return "Clark";
			}
		},
		DIAMOND {
			public String toString() {
				return "diamond";
			}

			@Override
			public String charName() {

				return "Cain";
			}
		},
		PROJECTILE {
			public String toString() {
				return "projectile";
			}

			@Override
			public String charName() {

				return null;
			}
		};

		public abstract String charName();

	}

	private boolean willTalk = false;

	private int deltaX = 0;
	private int deltaY = 0;
	protected Direction direction = Direction.RIGHT;
	protected Types type;

	private boolean moveX = false;
	private boolean moveY = false;
	private boolean moveL = false;
	private boolean moveU = false;

	private boolean wallBound = false;
	// private int wallX = 0;
	// private int wallY = 0;

	// protected boolean acting = false;
	// protected int actTimer = 0;
	protected transient int meleeTimer = -1;
	protected transient int rangedTimer = -1;
	protected transient int specialTimer = -1;
	protected transient int energy;
	protected int MAX_ENERGY;
	private int SPEED;

	private int counter = 0;
	private int animationTimer = 7;

	private static final int ANIMAX = 7;
	private static final int MAX = 4;
	private String charName;
	protected int HP_MAX;
	private int HP_TIMER_MAX = 50;
	private int HITSTUN_MAX = 10;

	protected int NEG_TIMER_MELEE;
	protected int NEG_TIMER_RANGED;
	protected int NEG_TIMER_SPECIAL;
	protected int TIMER_MELEE;
	protected int TIMER_RANGED;
	protected int TIMER_SPECIAL;
	protected int health = HP_MAX;
	private int hpTimer;
	private int hitstunTimer = 0;
	private boolean isPlayerCollide;
	protected int MEnC;
	protected int REnC;
	protected int SEnC;
	protected int pathUpdateTimer;
	protected int meleeDamage;
	protected int rangedDamage;
	protected int specialDamage;

	public GameCharacter(int x, int y, Board owner, Types type, String charName, boolean player, int NEG_TIMER_MELEE, int NEG_TIMER_RANGED,
			int NEG_TIMER_SPECIAL, int TIMER_MELEE, int TIMER_RANGED, int TIMER_SPECIAL, int HP_MAX, int SPEED, int MAX_ENERGY, int MEnC, int REnC,
			int SEnC, int meleeDamage, int rangedDamage, int specialDamage) {
		super(x, y, "n", owner);
		this.player = player;
		this.charName = charName;
		this.type = type;
		this.NEG_TIMER_MELEE = NEG_TIMER_MELEE;
		this.NEG_TIMER_RANGED = NEG_TIMER_RANGED;
		this.NEG_TIMER_SPECIAL = NEG_TIMER_SPECIAL;
		this.TIMER_MELEE = TIMER_MELEE;
		this.TIMER_RANGED = TIMER_RANGED;
		this.TIMER_SPECIAL = TIMER_SPECIAL;
		this.HP_MAX = HP_MAX;
		this.SPEED = SPEED;
		this.MAX_ENERGY = MAX_ENERGY;
		energy = MAX_ENERGY;
		health = HP_MAX;
		this.MEnC = MEnC;
		this.REnC = REnC;
		this.SEnC = SEnC;
		this.meleeDamage = meleeDamage;
		this.rangedDamage = rangedDamage;
		this.specialDamage = specialDamage;
		meleeTimer=this.NEG_TIMER_MELEE;
		rangedTimer=this.NEG_TIMER_RANGED;
		specialTimer=this.NEG_TIMER_SPECIAL;
	}

	@Override
	public void animate() {

		if (player) {

		} else {
			
			x += owner.getScrollX();
			y += owner.getScrollY();
			if (me == -1) {
				for (int c = 0; c < owner.getFriends().size(); c++) {
					if (owner.getFriends().get(c) == this) {
						me = c;
						break;
					}
				}
			}
			if (path != null) {
				path.update();
				if(pointTimer<=0&&path.getPoints().size()>0){
					if(path.getPoints().size()<4){
					path.removeLast();}
					else{
						path.findPath();
					}
					pointTimer=18;
				}else{
					pointTimer--;
				}
				if (path.getPoints().size() > 0 && new Point(x, y).distance(path.getCurrentFind()) < 11// &&JOptionPane.showConfirmDialog(owner,
																										// getType().charName()+" wants to remove PathPoint")==JOptionPane.YES_OPTION
				) {
					pointTimer=18;
					path.removeLast();
				}
				if (new Point(x, y).distance(owner.getCharPoint()) < 140) {
					// System.out.println(getType().charName()+" CLOSE:"+new
					// Date());
					path = null;
				}
			}
			if (!wallBound) {
				// System.out.println(path);
				if(path==null&&owner.pointedPoint==null){
					if(enPoint==null){
					if(enUp<=0){
						if(new Point(x,y).distance(owner.getCharPoint())<500){
						enUp=5;
						int maxDis=250;
						for(Enemy en:owner.getEnemies()){
							int distance=(int) new Point(getMidX(),getMidY()).distance(new Point(en.getX(),en.getY()));
							if(!(en instanceof Projectile)&&!en.isInvincible()&&distance<maxDis){
								enPoint=en;
							maxDis=distance;
							}
						}
						if(maxDis==250){
							meleePress=false;
						}
						if(!(this instanceof Diamond)&&!(this instanceof Heart)&&(double)health/(double)HP_MAX>0.5)
						{
							goTo=true;
							}
						else{
								goTo=false;
							//enPoint=null;
							//enUp=20;
							//meleePress=false;
						}
						}
						
					}else{
						enUp--;
					}
					}
				}else{
					enPoint=null;
					enUp=5;
					meleePress=false;
				}
				if(enPoint!=null){
					if((double)health/(double)HP_MAX<0.5&&goTo){
						enPoint=null;
						meleePress=false;
						enUp=1;
						goTo=true;
					}
					if(new Point(x,y).distance(owner.getCharPoint())>=500){
						enPoint=null;
						meleePress=false;
						enUp=50;
						goTo=true;
					}if(!owner.getEnemies().contains(enPoint)){
						enPoint=null;
						meleePress=false;
						goTo=true;
						enUp=50;
					}
					else{
						//meleePress=true;
					}
				}
				if (path != null) {

					if (path.getPoints().size() > 0) {
						getToPoint = path.getCurrentFind();
						enPoint=null;
						enUp=25;
						meleePress=false;
						
						goTo=true;
					} else {
						pathUpdateTimer=25;
						enUp=5;
						path = null;
						getToPoint = owner.getCharacter().getBounds().getLocation();
				
						goTo=true;}
					
					
				}
				else if(owner.pointedPoint!=null){
					getToPoint=owner.pointedPoint;
					goTo=true;
				}
				else if(enPoint!=null){
					getToPoint=new Point(enPoint.getMidX(),enPoint.getMidY());
				}
				else{
					getToPoint = owner.getCharacter().getBounds().getLocation();
					goTo=true;
				}
				if (path != null || getToPoint.distance(x, y) > (enPoint!=null?110:125)) {
					int amount = 2;
					if (path != null) {
						if (Math.abs(x - getToPoint.x) > Math.abs(y - getToPoint.y)) {
							amount = 0;
						} else {
							amount = 1;
						}
					}
					// if(path!=null)
					// System.out.println("walking");
					if (amount != 1 && x > getToPoint.x + (path == null ? (SPEED * 2) : 0)) {
						int times=goTo?1:-1;
						deltaX = -SPEED*times;
						moveX = true;
						if(goTo)
						direction = Direction.LEFT;
						else
							direction=Direction.RIGHT;
					} else if (amount != 1 && x < getToPoint.x - (path == null ? (SPEED * 2) : 0)) {
						int times=goTo?1:-1;
						deltaX = SPEED*times;
						moveX = true;
						if(goTo)
							direction = Direction.RIGHT;
							else
								direction=Direction.LEFT;

					} else {
						deltaX = 0;
						moveX = false;
					}
					if (amount != 0 && y > getToPoint.y + (path == null ? (SPEED * 2) : 0)) {
						int times=goTo?1:-1;
						deltaY = -SPEED*times;
						moveY = true;
						//if (||y > getToPoint.y + 50)
						if(goTo)
							direction = Direction.UP;
							else
								direction=Direction.DOWN;

					} else if (amount != 0 && y < getToPoint.y - (path == null ? (SPEED * 2) : 0)) {
						int times=goTo?1:-1;
						deltaY = SPEED*times;
						moveY = true;
						//if (y < getToPoint.y - 50)
						if(goTo)
							direction = Direction.DOWN;
							else
								direction=Direction.UP;

					} else {
						moveY = false;
						deltaY = 0;
					}
				} else {
					deltaX = 0;
					deltaY = 0;
					moveX = false;
					moveY = false;
				}
				if(enPoint!=null){
					boolean xway=false;
					if(Math.abs(enPoint.getX()-x)>Math.abs(enPoint.getY()-y)){
						xway=true;
					}
					if(xway){
						if(x>enPoint.getX()){
							direction=Direction.LEFT;
						}else{
							direction=Direction.RIGHT;
						}
					}else{
						if(y>enPoint.getY()){
							direction=Direction.UP;
						}else{
							direction=Direction.DOWN;
						
						}
					}
					if(getActBounds().intersects(enPoint.getBounds())){
						meleePress=true;
					}else{
						meleePress=false;
					}
				}
			}
//			if (new Point(x, y).distance(new Point(owner.getBounds().getLocation())) > Statics.BOARD_WIDTH) {
//				x = owner.getCharacterX();
//				y = owner.getCharacterY();
//			}
		}

		if (hitstunTimer > 0) {
			hitstunTimer--;
			flicker();

			if (hitstunTimer == 0) {
				visible = true;
			}
		}

		if (hpTimer > 0) {
			hpTimer--;

		}
		if (hpTimer <= 0 && health < HP_MAX) {
			health += 3;
			if (health > HP_MAX) {
				health = HP_MAX;
			}
			hpTimer = HP_TIMER_MAX;
		}

		if (!wallBound) {

			if (animationTimer >= ANIMAX) {

				if (moveX || moveY) {
					animationTimer = 0;
					image = newImage("w" + counter);

					counter++;

					if (counter == MAX)
						counter = 0;
				} else
					image = newImage("n");
			} else
				animationTimer++;
			if (player) {

				if (moveX)
					if (moveL) {
						deltaX++;
						if (deltaX > SPEED)
							deltaX = SPEED;
					} else {
						deltaX--;
						if (deltaX < -SPEED)
							deltaX = -SPEED;
					}
				else {
					if (deltaX > 0)
						deltaX--;
					else if (deltaX < 0)
						deltaX++;
				}

				if (moveY)
					if (!moveU) {
						deltaY--;
						if (deltaY < -SPEED)
							deltaY = -SPEED;
					} else {
						deltaY++;
						if (deltaY > SPEED)
							deltaY = SPEED;
					}
				else {
					if (deltaY > 0)
						deltaY--;
					else if (deltaY < 0)
						deltaY++;
				}

				owner.setScrollX(deltaX);
				owner.setScrollY(deltaY);
			} else {
				x += deltaX;
				y += deltaY;
			}
		} else {

			if (player) {
				owner.setScrollX(-owner.getScrollX());
				owner.setScrollY(-owner.getScrollY());

				owner.reAnimate();
			} else {
				if (!isPlayerCollide) {
					if (new Point(getMidX(), getMidY()).distance(new Point(wallX, getMidY())) < 100) {
						deltaX = -deltaX;
						x += deltaX;
					}
					if (new Point(getMidX(), getMidY()).distance(new Point(getMidX(), wallY)) < 100) {
						deltaY = -deltaY;
						y += deltaY;
					}

					x += deltaX;
					y += deltaY;
				} else {
					deltaX = 0;
					deltaY = 0;
					moveX = false;
					moveY = false;
					image = newImage("n");
				}
				if (pathUpdateTimer > 0)
					pathUpdateTimer--;
				if (pathUpdateTimer <= 0 && onceNotCollidePlayer && path == null && new Point(x, y).distance(owner.getCharPoint()) > 140) {
					// if (new Point(getMidX(), getMidY()).distance(new Point(
					// wallX, getMidY())) < 100) {
					// deltaX = -deltaX;
					// x += deltaX;
					// }
					// if (new Point(getMidX(), getMidY()).distance(new Point(
					// getMidX(), wallY)) < 100) {
					// deltaY = -deltaY;
					// y += deltaY;
					// }

					for (int c = 0; c < owner.getFriends().size(); c++) {
						if (owner.getFriends().get(c) == this) {
							me = c;
							break;
						}
					}
					path = new PointPath(me, owner);
					pathUpdateTimer=50;
					enUp=50;
					enPoint=null;
					meleePress=false;
					pointTimer=18;
					path.update();
				}

			}
			onceNotCollidePlayer = false;
			wallBound = false;
		}
setAttacks();
	}

	public void keyPressed(int keyCode) {
		if(keyCode==KeyEvent.VK_H){
			energy=0;
			for(int c=0;c<owner.getFriends().size();c++){
				if(new Point(x,y).distance(new Point(owner.getFriends().get(c).getX(),owner.getFriends().get(c).getY()))<50){
				owner.getFriends().get(c).setX(x);	
				owner.getFriends().get(c).setY(y);	
				owner.getFriends().get(c).setEnergy(0);
				}
			}
		}
	else if (keyCode == KeyEvent.VK_MINUS) {
			level++;
		}else if(keyCode==KeyEvent.VK_0){
			wallet.setMoney(Integer.MAX_VALUE);
		}

		if (keyCode == Preferences.LEVEL_UP()) {
			if (levMen == null)
				OpenLevelUp();
			else {
				owner.setFocusable(true);
				owner.requestFocus();
				levMen.dispose();
				levMen = null;
			}
		} else {

			// Left
			if (keyCode == Preferences.LEFT()) {
				direction = Direction.LEFT;
				moveX = true;
				moveL = true;
			}

			// Right
			else if (keyCode == Preferences.RIGHT()) {
				direction = Direction.RIGHT;
				moveX = true;
				moveL = false;
			}

			// Up
			else if (keyCode == Preferences.UP()) {
				direction = Direction.UP;
				moveY = true;
				moveU = true;
			}

			// Down
			else if (keyCode == Preferences.DOWN()) {
				direction = Direction.DOWN;
				moveY = true;
				moveU = false;
			}

			// Attack
			else if (keyCode == Preferences.ATTACK()) {
				meleePress = true;
			}

			// Projectile
			else if (keyCode == Preferences.PROJECTILE()) {
				rangedPress = true;
			}

			// Special
			else if (keyCode == Preferences.SPECIAL()) {
				specialPress = true;
			}

			// Talk to NPCs
			else if (keyCode == Preferences.NPC()) {
				willTalk = true;
			}

		}
	}

	private void OpenLevelUp() {

		levMen = new LevelUp();
	}

	public abstract Moves getRangedMove();

	public Moves getSpecialProMove() {
		return Moves.NONE;

	}

	public void keyReleased(int keyCode) {

		// Left/Right
		if (keyCode == Preferences.LEFT() || keyCode == Preferences.RIGHT()) {

			if (direction == Direction.LEFT || direction == Direction.RIGHT) {
				if (deltaY > 0)
					direction = Direction.UP;
				else if (deltaY < 0)
					direction = Direction.DOWN;
			}

			deltaX = 0;
			moveX = false;
		}

		// Up/Down
		else if (keyCode == Preferences.DOWN() || keyCode == Preferences.UP()) {
			// deltaY = 0;
			if (direction == Direction.UP || direction == Direction.DOWN) {
				if (deltaX > 0)
					direction = Direction.LEFT;
				else if (deltaX < 0)
					direction = Direction.RIGHT;
			}

			deltaY = 0;
			moveY = false;
		}

		// Melee
		else if (keyCode == Preferences.ATTACK()) {
			meleePress = false;
			if (meleeTimer > 0)
				meleeTimer = 0;
		}
		// Ranged
		else if (keyCode == Preferences.PROJECTILE()) {
			rangedPress = false;
			if (rangedTimer > 0)
				rangedTimer = 0;
		}

		// Special
		else if (keyCode == Preferences.SPECIAL()) {
			specialPress = false;
			if (!(type == Types.CLUB)) {

				if (specialTimer > 0)
					specialTimer = 0;
			}
		}

		// End
	}

	public Rectangle getTalkBounds() {
		if (willTalk) {
			willTalk = false;

			switch (direction) {
			case UP:
				return new Rectangle(x, y - height, width, height);
			case DOWN:
				return new Rectangle(x, y + height, width, height);
			case RIGHT:
				return new Rectangle(x + width, y, width, height);
			case LEFT:
			default:
				return new Rectangle(x - width, y, width, height);
			}
		}

		return null;
	}

	public void collision(int midX, int midY, boolean isPlayer) {
if(isPlayer){
	if((!goTo)&&(enPoint!=null)){
		return;
	}
}
		wallBound = true;
		if (!player) {
			this.isPlayerCollide = isPlayer;
			if (isPlayer == false) {
				onceNotCollidePlayer = true;
			}
			wallX = midX;
			wallY = midY;
		}
	}

	public Rectangle getActBounds() {

		switch (direction) {
		case UP:
			return new Rectangle(x + 47, y - 30, 6, 6);
		case DOWN:
			return new Rectangle(x + 47, y + Statics.BLOCK_HEIGHT + 40, 6, 6);
		case RIGHT:
			return new Rectangle(x + Statics.BLOCK_HEIGHT + 15, y + Statics.BLOCK_HEIGHT - 6, 6, 6);
		case LEFT:
		default:
			return new Rectangle(x - 40, y + Statics.BLOCK_HEIGHT - 6, 6, 6);
		}
	}

	private Font HUD = new Font("Calibri", Font.BOLD, 30);

	private Point setAttacks() {
		Point shieldPos = null;
		if (meleePress) {
			if (meleeTimer <= NEG_TIMER_MELEE && energy >= MEnC// ||this
																// instanceof
																// Diamond
			) {
				meleeTimer = TIMER_MELEE;
				energy -= MEnC;
			}
		}
		if (specialPress && !meleePress && !rangedPress) {
			if (specialTimer <= NEG_TIMER_SPECIAL && (energy >= SEnC || this instanceof Heart)) {
			
					specialTimer = TIMER_SPECIAL;
				if (type == Types.HEART) {
					if (!((Heart) this).usingField()) {
						owner.getObjects().add(new Dispenser(x, y, this, "images/objects/dispenser.png", owner, dir));
						((Heart) this).start();
						specialTimer=NEG_TIMER_SPECIAL+20;
					} else {
						((Heart) this).end();
					}
				} else{
					energy -= SEnC;}
			}
		}
		if (rangedPress && !meleePress) {
			if (rangedTimer <= NEG_TIMER_RANGED && energy >= REnC) {
				rangedTimer = TIMER_RANGED;
				energy -= REnC;
				String s = "images/enemies/blasts/0.png";
				if (type == Types.DIAMOND)
					s = getPath() + "diamond.png";
				owner.getfP().add(new FProjectile(dir, x, y, 25, this, s, owner, getRangedMove()));

			}

		}
		if (this instanceof Club) {
			if (specialTimer >= 0 && specialTimer % 50 == 0) {
				String s = "images/enemies/blasts/0.png";
				owner.getfP().add(new FProjectile(dir, x + (this.getWidth() / 2), y + (this.getHeight() / 2), 30, this, s, owner, Moves.MPITCH));

			}
		} else if (type == Types.DIAMOND) {
			boolean found = false;
			for (int c = 0; c < owner.getfP().size(); c++) {

				if (owner.getfP().get(c).getMove() == Moves.CHAIN) {

					specialTimer = 0;
					meleeTimer = 0;
					rangedTimer = 0;
					found = true;
					FProjectile fp = owner.getfP().get(c);
					shieldPos = new Point(fp.getX(), fp.getY());
					break;
				}
			}
			if (!found) {

				if (rangedTimer > 0)
					rangedTimer = 0;
			}
		}
		return shieldPos;
	}

	@Override
	public void draw(Graphics2D g2d) {

		if (deltaX != 0 || deltaY != 0) {
			dir = 0;
			boolean changed = false;
			if (deltaX < 0) {
				dir = 0;
				changed = true;
			} else if (deltaX > 0) {
				dir = 180;
				changed = true;
			}
			if (deltaY < 0) {
				if (changed) {

					if (dir == 180) {
						dir -= 45;
					} else {
						dir += 45;
					}
				} else {
					dir = 90;
				}
			} else if (deltaY > 0) {
				if (changed) {
					if (dir == 180) {
						dir += 45;
					} else {
						dir -= 45;
					}
				} else {
					dir = 270;
				}
			}
		}
		Point p = setAttacks();
		if (visible) {

			if (direction != Direction.LEFT && direction != Direction.UP)
				g2d.drawImage(image, x, y, owner);
			else if (direction != Direction.UP)
				g2d.drawImage(image, x + width, y, -width, height, owner);

			if (p != null) {
				g2d.setColor(Color.black);
				g2d.drawLine(getMidX(), getMidY(), (int) p.getX() + Statics.BLOCK_HEIGHT / 2, (int) p.getY() + Statics.BLOCK_HEIGHT / 2);
			}
			if (direction == Direction.UP)
				g2d.drawImage(image, x, y, owner);
		}

		drawTool(g2d);
		if (player) {
			g2d.setColor(Color.BLACK);
			// 30 + (int) Math.ceil((double) wallet.getDigits()) * 30 + 340;
//			if (normWidth < (int) Math.ceil((double) 75//HP_MAX
//					/ (double) 10) * 30 + 30) {
//				normWidth = (int) Math.ceil((double) 75//HP_MAX
//						/ (double) 10) * 30 + 30;
//			}
			
			int	normWidth=300;
			g2d.fillRect(10, 20, normWidth, 130);

//			for (int i = 1; i <= (int) Math.ceil((double) HP_MAX/ (double) 10); i++) {
//				g2d.setColor((int) Math.ceil((double) health / (double) 10) >= i ? Color.RED : Color.DARK_GRAY);
//				g2d.fillRect(i * 30, 70, 20, 20);
//				g2d.setColor(Color.WHITE);
//				g2d.drawRect(i * 30, 70, 20, 20);
//			}
			//g2d.setColor((int) Math.ceil((double) health / (double) 10) >= i ? Color.RED : Color.DARK_GRAY);
			g2d.setColor(Color.GREEN);
			g2d.fillRect(normWidth-50, 70+(int)(Math.max(0, ((double)meleeTimer/(double)NEG_TIMER_MELEE))*20), 20, 20-(int)(Math.max(0, ((double)meleeTimer/(double)NEG_TIMER_MELEE))*20));
			g2d.setColor(Color.WHITE);
			g2d.drawRect(normWidth-50, 70, 20, 20);
			g2d.setColor(Color.GREEN);
			g2d.fillRect(normWidth-50, 95+(int)(Math.max(0, ((double)rangedTimer/(double)NEG_TIMER_RANGED))*20), 20, 20-(int)(Math.max(0, ((double)rangedTimer/(double)NEG_TIMER_RANGED))*20));
			g2d.setColor(Color.WHITE);
			g2d.drawRect(normWidth-50, 95, 20, 20);
			g2d.setColor(Color.GREEN);
			g2d.fillRect(normWidth-50, 120+(int)(Math.max(0, ((double)specialTimer/(double)NEG_TIMER_SPECIAL))*20), 20, 20-(int)(Math.max(0, ((double)specialTimer/(double)NEG_TIMER_SPECIAL))*20));
			g2d.setColor(Color.WHITE);
			g2d.drawRect(normWidth-50, 120, 20, 20);
			g2d.setColor(Color.RED);
			g2d.setFont(HUD);
		//	g2d.drawString("HEALTH:     |     MONEY: " + wallet.getMoney(), 30, 50);
			g2d.drawString("MONEY: " + wallet.getMoney(), 30, 50);
			drawTHBar((double) health / (double) HP_MAX,normWidth-75,g2d);
			drawTEnBar((double) energy / (double) MAX_ENERGY, normWidth-75, g2d);

			drawTLBar(normWidth-75, g2d);
			drawCSHUD(g2d);
			// drawEnBar((double) energy / (double) MAX_ENERGY, g2d);
		} else {
			drawBar2((double) health / (double) HP_MAX, (double) energy / (double) MAX_ENERGY, g2d);

		}
		timersCount();
	}

	protected void drawTool(Graphics2D g2d) {
		int dX = 0;
		int dY = 0;

		switch (direction) {
		case UP:
			dX = x + 20;
			dY = y - Statics.BLOCK_HEIGHT + 70;
			break;

		case DOWN:
			dX = x - 10;
			dY = y + Statics.BLOCK_HEIGHT - 50;
			break;

		case RIGHT:
			dX = x + Statics.BLOCK_HEIGHT - 50;
			dY = y;
			break;

		case LEFT:
			dX = x - 100;
			dY = y;
			break;
		}

		if (toMoveString() != null)
			g2d.drawImage(newImage(toMoveString()), dX, dY, owner);

		if (direction == Direction.UP)
			g2d.drawImage(image, x, y, owner);
		

	}

	protected abstract void drawCSHUD(Graphics2D g2d);

	// Works only for integers
	protected int numOfDigits(int num) {

		int x = 10;
		int i = 0;

		// if (num < 0)
		// num = -num;

		while (x <= num) {

			i++;
			x = x * 10;
		}

		return i;
	}

	// public boolean isActing() {
	// return acting;
	// }

	public void endAction() {

		if (meleeTimer > 0)
			meleeTimer = 0;
		if (rangedTimer > 0)
			rangedTimer = 0;
		if (specialTimer > 0)
			specialTimer = 0;
	}

	public Image newImage(String name) {
		return super.newImage(getPath() + name + ".png");
	}

	private String getPath() {

		String dir;

		if (direction != null)
			switch (direction) {
			case UP:
				dir = "back";
				break;

			case DOWN:
				dir = "front";
				break;

			default:
				dir = "side";
				break;
			}
		else
			dir = "side";
		return "images/characters/" + (charName != null ? charName : "spade") + "/" + dir + "/";
	}

	public Rectangle getCollisionBounds() {

		return new Rectangle(x + 40, y + 40, width - 80, height - 40);
	}

	public void takeDamage(int amount) {

		if (hitstunTimer <= 0) {
			health -= amount;
			hpTimer = 100;
			hitstunTimer = HITSTUN_MAX;

			if (health <= 0)
				owner.setState(Board.State.DEAD);
		}
	}

	public abstract boolean canAct();

	public abstract void getsActor();

	public Types getType() {

		return type;
	}

	public void stop() {

		moveX = false;
		moveY = false;
		deltaX = 0;
		deltaY = 0;
	}

	protected void timersCount() {
		if (enTimer == 0) {
			energy += 1;
			enTimer = 5;
		} else
			enTimer--;
		if (energy > MAX_ENERGY) {
			energy = MAX_ENERGY;
		}
		if (meleeTimer > NEG_TIMER_MELEE && (type != Types.DIAMOND || ((type == Types.DIAMOND) && meleeTimer <= 0))) {
			meleeTimer--;
		}
		if (rangedTimer > NEG_TIMER_RANGED) {
			if ((!(type == Types.DIAMOND)) || rangedTimer <= 0)
				rangedTimer--;
		}
		if (specialTimer > NEG_TIMER_SPECIAL) {
			specialTimer--;
		}
	}

	public int getActing() {

		if (meleeTimer > 0)
			return 1;
		else if (rangedTimer > 0)
			return 2;
		else if (specialTimer > 0)
			return 3;

		else
			return 0;
	}

	public abstract Moves getMove();

	public abstract String toMoveString();

	public Direction getDirection() {

		return direction;
	}

	public void setPlayer(boolean b) {

		player = b;
	}

	public void heal(int i) {

		health += i;
		if (health > HP_MAX) {
			health = HP_MAX;
		}
	}

	public void setMelee(int i) {

		meleeTimer = i;
	}

	public boolean getWallBound() {

		return wallBound;
	}

	@Override
	public int compareTo(GameCharacter g) {
		return Integer.compare(this.SPEED, g.SPEED);

	}

	public String getSave() {

		return "" + getType() + "," + HP_MAX + "," + MAX_ENERGY + "," + myLevel+ "," + meleeDamage+ "," + rangedDamage+ "," + specialDamage;
	}

	public void setMaxHealth(int setter) {
		HP_MAX = setter;
	}

	public void load(String string) {
		ArrayList<String> stuff = new ArrayList<String>();// should
		// have
		// 5
		String currentS = "";
		for (int c2 = 0; c2 < string.length(); c2++) {

			if (string.charAt(c2) == ',') {
				stuff.add(currentS);
				currentS = "";

			} else {
				currentS += string.charAt(c2);
			}
		}
		if (currentS != "") {
			stuff.add(currentS);
		}
		try {

			int health = Integer.parseInt(stuff.get(0));
			int energy = Integer.parseInt(stuff.get(1));
			int myLevel = Integer.parseInt(stuff.get(2));
			int melee = Integer.parseInt(stuff.get(3));
			int ranged = Integer.parseInt(stuff.get(4));
			int special = Integer.parseInt(stuff.get(5));
			this.myLevel = myLevel;
			HP_MAX = health;
			MAX_ENERGY = energy;
			this.health = HP_MAX;
			this.energy = MAX_ENERGY;
			this.meleeDamage=melee;
			this.rangedDamage=ranged;
			this.specialDamage=special;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Wallet getWallet() {
		return wallet;
	}

	public void setWallet(Wallet w) {
		wallet = w;
	}

	public void drawEnBar(double per, Graphics2D g2d) {
		g2d.setColor(Color.BLACK);
		g2d.fillRect(x, y - 10, width, 10);
		g2d.setColor(Color.BLUE);
		g2d.fillRect(x, y - 10, (int) ((double) width * (double) per), 10);
		g2d.setColor(Color.WHITE);
		g2d.drawRect(x - 1, y - 11, width + 1, 11);

	}

	// public void drawBar2(double per, double per2, Graphics2D g2d) {
	// g2d.setColor(Color.BLACK);
	// g2d.fillRect(x, y - 10, width, 10);
	// g2d.setColor(Color.RED);
	// g2d.fillRect(x, y - 10, (int) ((double) width * (double) per), 10);
	//
	// g2d.setColor(new Color(0, 0, 255, 150));
	// g2d.fillRect(x, y - 10, (int) ((double) width * (double) per2), 10);
	// g2d.setColor(Color.WHITE);
	// g2d.drawRect(x - 1, y - 11, width + 1, 11);
	//
	// }
	public void drawBar2(double per, double per2, Graphics2D g2d) {
		g2d.setColor(Color.BLACK);
		g2d.fillRect(x, y - 22, width, 22);
		g2d.setColor(Color.RED);
		g2d.fillRect(x, y - 21, (int) ((double) width * (double) per), 10);
		g2d.setColor(Color.WHITE);
		g2d.drawRect(x - 1, y - 11, width + 1, 11);

		g2d.setColor(Color.BLUE);
		g2d.fillRect(x, y - 10, (int) ((double) width * (double) per2), 10);
		g2d.setColor(Color.WHITE);
		g2d.drawRect(x - 1, y - 22, width + 1, 11);

	}
public void drawTHBar(double per, int total, Graphics2D g2d) {
		total -= 10;
		g2d.setColor(Color.BLACK);
		g2d.fillRect(30, 72, total, 16);
		g2d.setColor(Color.RED);
		g2d.fillRect(30, 72, (int) ((double) total * (double) per), 16);
		g2d.setColor(Color.WHITE);
		g2d.drawRect(30 - 1, 72 - 1, total + 1, 17);

	}
	public void drawTEnBar(double per, int total, Graphics2D g2d) {
		total -= 10;
		g2d.setColor(Color.BLACK);
		g2d.fillRect(30, 100, total, 10);
		g2d.setColor(Color.BLUE);
		g2d.fillRect(30, 100, (int) ((double) total * (double) per), 10);
		g2d.setColor(Color.WHITE);
		g2d.drawRect(30 - 1, 100 - 1, total + 1, 11);

	}
	
	public void drawTLBar(int total, Graphics2D g2d) {
		total -= 10;
		double per = (double) xp / (double) (Math.pow(level + 1, 2) * 10);
		g2d.setColor(Color.BLACK);
		g2d.fillRect(30, 125, total, 10);
		g2d.setColor(Color.YELLOW);
		g2d.fillRect(30, 125, (int) ((double) total * (double) per), 10);
		g2d.setColor(Color.WHITE);
		g2d.drawRect(30 - 1, 125 - 1, total + 1, 11);

	}

	public static void plusXP(int adder) {
		xp += adder;
		if (xp >= (int) Math.pow(level + 1, 2) * 10) {
			xp -= (int) Math.pow(level + 1, 2) * 10;
			level++;
		}
	}

	public static void setLevel(int setter) {
		level = setter;
	}

	public static void setXP(int setter) {
		xp = setter;
	}

	public static int getLevel() {
		return level;
	}

	public static int getXP() {
		return xp;
	}
public void setEnergy(int setter){
	energy=setter;
	if(energy>MAX_ENERGY)
		energy=MAX_ENERGY;
}
	public class LevelUp extends JFrame {
		JLabel levelLabel;
		JButton mHealth;
		JButton mEn;
		JButton melee;
		JButton ranged;
		JButton special;
		LevelUp l = this;

		public LevelUp() {
			deltaX = 0;
			deltaY = 0;
			moveX = false;
			moveY = false;
			levUp = true;
			this.setFocusable(true);
			owner.setFocusable(false);
			this.setResizable(false);
			
			this.setTitle(DigIt.NAME);
			this.setSize(400, 200);
			this.setLocation(Statics.BOARD_WIDTH / 2 - this.getWidth() / 2, Statics.BOARD_HEIGHT / 2 - this.getHeight() / 2);
			this.setAlwaysOnTop(true);
			this.setLayout(new BorderLayout());

			levelLabel = new JLabel("Skill Points: " + (level - myLevel) + "  |  " + "Level: " + level + "  |  " + "XP: " + xp + "  |  "
					+ "XP needed: " + (int) Math.pow(level + 1, 2) * 10, SwingConstants.CENTER);

			this.add(levelLabel, BorderLayout.NORTH);
			JPanel panels = new JPanel();
			panels.setLayout(new BoxLayout(panels, BoxLayout.Y_AXIS));
			panels.add(new JLabel(" "));
			JPanel panel = new JPanel();
			mHealth = new JButton("Health: " + HP_MAX);
			mEn = new JButton("Energy: " + MAX_ENERGY);
			melee = new JButton("Melee: " + meleeDamage);
			ranged = new JButton("Ranged: " + rangedDamage);
			special = new JButton("Special: " + specialDamage);
			melee.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					if (myLevel < level) {
						meleeDamage++;
						myLevel++;
						melee.setText("Melee: " + meleeDamage);
						levelLabel.setText("Skill Points: " + (level - myLevel) + "  |  " + "Level: " + level + "  |  " + "XP: " + xp + "  |  "
								+ "XP needed: " + (int) Math.pow(level + 1, 2) * 10);
					}
				}
			});
			ranged.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					if (myLevel < level) {
						rangedDamage++;
						myLevel++;
						ranged.setText("Ranged: " + rangedDamage);
						levelLabel.setText("Skill Points: " + (level - myLevel) + "  |  " + "Level: " + level + "  |  " + "XP: " + xp + "  |  "
								+ "XP needed: " + (int) Math.pow(level + 1, 2) * 10);
					}
				}
			});
			special.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					if (myLevel < level) {
						specialDamage++;
						myLevel++;
						special.setText("Special: " + specialDamage);
						levelLabel.setText("Skill Points: " + (level - myLevel) + "  |  " + "Level: " + level + "  |  " + "XP: " + xp + "  |  "
								+ "XP needed: " + (int) Math.pow(level + 1, 2) * 10);
					}
				}
			});
			mHealth.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					if (myLevel < level) {
						HP_MAX += 5;
						myLevel++;
						mHealth.setText("Health: " + HP_MAX);
						levelLabel.setText("Skill Points: " + (level - myLevel) + "  |  " + "Level: " + level + "  |  " + "XP: " + xp + "  |  "
								+ "XP needed: " + (int) Math.pow(level + 1, 2) * 10);
					}
				}
			});
			this.addKeyListener(new KeyListener() {

				@Override
				public void keyTyped(KeyEvent e) {

				}

				@Override
				public void keyReleased(KeyEvent e) {

				}

				@Override
				public void keyPressed(KeyEvent e) {

					if (e.getKeyCode() == KeyEvent.VK_L) {
						owner.setFocusable(true);
						owner.requestFocus();
						levUp = false;
						l.dispose();
						levMen = null;
					}

				}
			});
			panel.add(mHealth);
			mEn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					if (myLevel < level) {
						MAX_ENERGY += 8;
						myLevel++;
						mEn.setText("Energy: " + MAX_ENERGY);
						levelLabel.setText("Skill Points: " + (level - myLevel) + "  |  " + "Level: " + level + "  |  " + "XP: " + xp + "  |  "
								+ "XP needed: " + (int) Math.pow(level + 1, 2) * 10);
					}
				}
			});
			panel.add(mEn);

			JPanel panel2 = new JPanel();
			panel2.add(melee);
			panel2.add(ranged);
			panel2.add(special);
			panels.setBackground(Color.black);
			panel.setBackground(Color.black);
			panel2.setBackground(Color.black);
			levelLabel.setForeground(Color.WHITE);
			levelLabel.setOpaque(true);
			levelLabel.setBackground(Color.black);
			panels.add(panel);
			panels.add(panel2);
			this.add(panels, BorderLayout.CENTER);

			this.addWindowListener(new WindowListener() {

				@Override
				public void windowOpened(WindowEvent e) {

				}

				@Override
				public void windowIconified(WindowEvent e) {

				}

				@Override
				public void windowDeiconified(WindowEvent e) {

				}

				@Override
				public void windowDeactivated(WindowEvent e) {

				}

				@Override
				public void windowClosing(WindowEvent e) {

					owner.setFocusable(true);
					owner.requestFocus();
					levUp = false;
				}

				@Override
				public void windowClosed(WindowEvent e) {

				}

				@Override
				public void windowActivated(WindowEvent e) {

				}
			});
			this.setVisible(true);
			this.requestFocus();
		}
	}

	public PointPath getPPath() {

		return path;
	}

	public int getMeleeDamage() {
		return meleeDamage;
	}

	public int getRangedDamage() {
		return rangedDamage;
	}

	public int getSpecialDamage() {
		return specialDamage;
	}
}
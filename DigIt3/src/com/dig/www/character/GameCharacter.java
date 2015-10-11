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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.dig.www.start.Board;
import com.dig.www.start.Board.State;
import com.dig.www.util.Sprite;
import com.dig.www.util.Statics;

public abstract class GameCharacter extends Sprite implements
		Comparable<GameCharacter> {

	/**
	 * 
	 */
	LevelUp levMen;
	boolean levUp=false;
	protected PointPath path;
	protected static int xp;
	protected static int level;
	protected int myLevel;
	private int enTimer=0;
	private int wallX;
	private int wallY;
	private Rectangle collideRect;
	private Point getToPoint;
	private static final long serialVersionUID = 1L;
	private int dir = 0;
	private boolean meleePress = false;
	private boolean rangedPress = false;
	private boolean specialPress = false;
	private int me = -1;
	protected Wallet wallet;

	private boolean player;
private boolean onceNotCollidePlayer;
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
				// TODO Auto-generated method stub
				return "Carl";
			}
		},
		HEART {
			public String toString() {
				return "heart";
			}

			@Override
			public String charName() {
				// TODO Auto-generated method stub
				return "Destiny";
			}
		},
		SPADE {
			public String toString() {
				return "shovel";
			}

			@Override
			public String charName() {
				// TODO Auto-generated method stub
				return "Clark";
			}
		},
		DIAMOND {
			public String toString() {
				return "diamond";
			}

			@Override
			public String charName() {
				// TODO Auto-generated method stub
				return "Cain";
			}
		},
		PROJECTILE {
			public String toString() {
				return "projectile";
			}

			@Override
			public String charName() {
				// TODO Auto-generated method stub
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

	// TODO moveL moveD
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
	private int health = HP_MAX;
	private int hpTimer;
	private int hitstunTimer = 0;
	private boolean isPlayerCollide;
	int MEnC;
	int REnC;
	int SEnC;
	public GameCharacter(int x, int y, Board owner, Types type,
			String charName, boolean player, int NEG_TIMER_MELEE,
			int NEG_TIMER_RANGED, int NEG_TIMER_SPECIAL, int TIMER_MELEE,
			int TIMER_RANGED, int TIMER_SPECIAL, int HP_MAX, int SPEED,
			int MAX_ENERGY,int MEnC,int REnC,int SEnC) {
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
		this.MEnC=MEnC;
		this.REnC=REnC;
		this.SEnC=SEnC;
	}

	@Override
	public void animate() {
		// TODO Auto-generated method stub
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
			if(path!=null){
path.update();
if(path.getPoints().size()>0&&new Point(x,y).distance(path.getCurrentFind())<11){
	path.removeLast();
}
if(new Point(x, y).distance(owner.getCharPoint())<140){
	System.out.println(getType().charName()+" CLOSE:"+new Date());
	path=null;
}
}
			if (!wallBound) {
				//System.out.println(path);
				if(path!=null){

					if(path.getPoints().size()>0){
					getToPoint=path.getCurrentFind();}else{
						path=null;
						getToPoint = owner.getCharacter().getBounds().getLocation();}
					}
				else
				getToPoint = owner.getCharacter().getBounds().getLocation();
				
				if (path!=null||getToPoint.distance(x, y) > 125) {
					int amount=2;
				if(path!=null){
					if(Math.abs(x-getToPoint.x)>Math.abs(y-getToPoint.y)){
					amount=0;	
					}else{
						amount=1;
					}
				}
					//if(path!=null)
					//System.out.println("walking");
					if (amount!=1&&x > getToPoint.x + (path==null?(SPEED * 2):0)) {
						deltaX = -SPEED;
						moveX = true;
						direction = Direction.LEFT;

					} else if (amount!=1&&x < getToPoint.x - (path==null?(SPEED * 2):0)) {
						deltaX = SPEED;
						moveX = true;
						direction = Direction.RIGHT;

					} else {
						deltaX = 0;
						moveX = false;
					}
					if (amount!=0&&y > getToPoint.y + (path==null?(SPEED * 2):0)) {
						deltaY = -SPEED;
						moveY = true;
						if (y > getToPoint.y + 50)
							direction = Direction.UP;

					} else if (amount!=0&&y < getToPoint.y - (path==null?(SPEED * 2):0)) {
						deltaY = SPEED;
						moveY = true;
						if (y < getToPoint.y - 50)
							direction = Direction.DOWN;

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
			}
			if (new Point(x, y).distance(new Point(owner.getBounds()
					.getLocation())) > Statics.BOARD_WIDTH) {
				x = owner.getCharacterX();
				y = owner.getCharacterY();
			}
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
					if (new Point(getMidX(), getMidY()).distance(new Point(
							wallX, getMidY())) < 100) {
						deltaX = -deltaX;
						x += deltaX;
					}
					if (new Point(getMidX(), getMidY()).distance(new Point(
							getMidX(), wallY)) < 100) {
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
			if(onceNotCollidePlayer&&path==null&&new Point(x,y).distance(owner.getCharPoint())>140){
				for(int c=0;c<owner.getFriends().size();c++){
					if(owner.getFriends().get(c)==this){
						me=c;
						break;
					}
				}
				path=new PointPath(me, owner);
			}
			}
onceNotCollidePlayer=false;
			wallBound = false;
		}

	}

	public void keyPressed(int keyCode) {
if(keyCode==KeyEvent.VK_L){
	if(levMen==null)
	OpenLevelUp();
	else{
		owner.setFocusable(true);
		owner.requestFocus();
		levMen.dispose();
		levMen=null;}
}else
		switch (keyCode) {
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_A:

			// deltaX = SPEED;
			direction = Direction.LEFT;
			moveX = true;
			moveL = true;

			break;
		case KeyEvent.VK_RIGHT:
		case KeyEvent.VK_D:

			// deltaX = -SPEED;
			direction = Direction.RIGHT;
			moveX = true;
			moveL = false;

			break;

		case KeyEvent.VK_UP:
		case KeyEvent.VK_W:

			// deltaY = SPEED;
			direction = Direction.UP;
			moveY = true;
			moveU = true;

			break;
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_S:

			// deltaY = -SPEED;
			direction = Direction.DOWN;
			moveY = true;
			moveU = false;

			break;

		case KeyEvent.VK_SPACE:// Melee
			meleePress = true;

			break;
		case KeyEvent.VK_E:// Ranged
		case KeyEvent.VK_C:
			rangedPress = true;

			break;
		case KeyEvent.VK_Q:// Special
		case KeyEvent.VK_V:
			specialPress = true;
			break;

		case KeyEvent.VK_T:
		case KeyEvent.VK_X:
			willTalk = true;
			break;
		
		
		case KeyEvent.VK_O:
			level++;
			break;
		}
	}

	private void OpenLevelUp() {
		// TODO Auto-generated method stub
	levMen=	new LevelUp();
	}

	public abstract Moves getRangedMove();

	public Moves getSpecialProMove() {
		return Moves.NONE;

	}

	public void keyReleased(int keyCode) {

		switch (keyCode) {
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_A:
		case KeyEvent.VK_RIGHT:
		case KeyEvent.VK_D:

			if (direction == Direction.LEFT || direction == Direction.RIGHT) {
				if (deltaY > 0)
					direction = Direction.UP;
				else if (deltaY < 0)
					direction = Direction.DOWN;
			}

			deltaX = 0;
			moveX = false;

			break;

		case KeyEvent.VK_UP:
		case KeyEvent.VK_W:
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_S:
			// deltaY = 0;
			if (direction == Direction.UP || direction == Direction.DOWN) {
				if (deltaX > 0)
					direction = Direction.LEFT;
				else if (deltaX < 0)
					direction = Direction.RIGHT;
			}

			deltaY = 0;
			moveY = false;

			break;
		case KeyEvent.VK_SPACE:// Melee
			meleePress = false;
			if (meleeTimer > 0)
				meleeTimer = 0;
			break;
		case KeyEvent.VK_E:// Ranged
		case KeyEvent.VK_C:
			rangedPress = false;
			if (rangedTimer > 0)
				rangedTimer = 0;
			break;
		case KeyEvent.VK_Q:// Special
		case KeyEvent.VK_V:
			specialPress = false;
			if (!(type == Types.CLUB)) {

				if (specialTimer > 0)
					specialTimer = 0;
			}
			break;
		}
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
		// TODO Auto-generated method stub
		wallBound = true;
		if (!player) {
			this.isPlayerCollide = isPlayer;
			if(isPlayer==false){
				onceNotCollidePlayer=true;
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
			return new Rectangle(x + Statics.BLOCK_HEIGHT + 15, y
					+ Statics.BLOCK_HEIGHT - 6, 6, 6);
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
			if (specialTimer <= NEG_TIMER_SPECIAL&& energy >= SEnC) {
				specialTimer = TIMER_SPECIAL;
				energy -= SEnC;
			}
		}
		if (rangedPress && !meleePress) {
			if (rangedTimer <= NEG_TIMER_RANGED&& energy >= REnC) {
				rangedTimer = TIMER_RANGED;
				energy -= REnC;
				String s = "images/enemies/blasts/0.png";
				if (type == Types.DIAMOND)
					s = getPath() + "diamond.png";
				owner.getfP().add(
						new FProjectile(dir, x, y, 25, this, s, owner,
								getRangedMove()));

			
			}
			
		}
		if (this instanceof Club) {
			if (specialTimer >= 0 && specialTimer % 50 == 0) {
				String s = "images/enemies/blasts/0.png";
				owner.getfP().add(
						new FProjectile(dir, x + (this.getWidth() / 2), y
								+ (this.getHeight() / 2), 30, this, s, owner,
								Moves.MPITCH));

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
		// TODO Auto-generated method stub
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
				g2d.drawLine(getMidX(), getMidY(), (int) p.getX()
						+ Statics.BLOCK_HEIGHT / 2, (int) p.getY()
						+ Statics.BLOCK_HEIGHT / 2);
			}
			if (direction == Direction.UP)
				g2d.drawImage(image, x, y, owner);
		}

		drawTool(g2d);
		if (player) {
			g2d.setColor(Color.BLACK);
			int normWidth = 
					 30 + (int) Math.ceil((double) wallet.getDigits()) * 30
					+ 340;
if(normWidth<(int) Math.ceil( (double) HP_MAX / (double) 10) * 30+30){
	normWidth=(int) Math.ceil( (double) HP_MAX / (double) 10) * 30+30;
}
			g2d.fillRect(10, 20, (normWidth > 170 ? normWidth : 170), 130);

			for (int i = 1; i <= (int) Math.ceil((double) HP_MAX / (double) 10); i++) {
				g2d.setColor((int) Math.ceil((double) health / (double) 10) >= i ? Color.RED
						: Color.DARK_GRAY);
				g2d.fillRect(i * 30, 70, 20, 20);
				g2d.setColor(Color.WHITE);
				g2d.drawRect(i * 30, 70, 20, 20);
			}

			g2d.setColor(Color.RED);
			g2d.setFont(HUD);
			g2d.drawString("HEALTH:     |     MONEY: " + wallet.getMoney(), 30,
					50);
			drawTEnBar((double) energy / (double) MAX_ENERGY,
					(int) Math.ceil((double) HP_MAX / (double) 10) * 30, g2d);
			
			drawTLBar(
					(int) Math.ceil((double) HP_MAX / (double) 10) * 30, g2d);
			drawCSHUD(g2d);
			//drawEnBar((double) energy / (double) MAX_ENERGY, g2d);
		} else {
			drawBar2((double) health / (double) HP_MAX, (double) energy
					/ (double) MAX_ENERGY, g2d);

		}
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
		timersCount();

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
		// TODO Auto-generated method stub
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
		return "images/characters/" + (charName != null ? charName : "spade")
				+ "/" + dir + "/";
	}

	public Rectangle getCollisionBounds() {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return type;
	}

	public void stop() {
		// TODO Auto-generated method stub
		moveX = false;
		moveY = false;
		deltaX = 0;
		deltaY = 0;
	}

	protected void timersCount() {
		if(enTimer==0){
		energy+=1;
		enTimer=5;}
		else
			enTimer--;
		if(energy>MAX_ENERGY){
			energy=MAX_ENERGY;
		}
		if (meleeTimer > NEG_TIMER_MELEE
				&& (type != Types.DIAMOND || ((type == Types.DIAMOND) && meleeTimer <= 0))) {
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
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return direction;
	}

	public void setPlayer(boolean b) {
		// TODO Auto-generated method stub
		player = b;
	}

	public void heal(int i) {
		// TODO Auto-generated method stub
		health += i;
		if (health > HP_MAX) {
			health = HP_MAX;
		}
	}

	public void setMelee(int i) {
		// TODO Auto-generated method stub
		meleeTimer = i;
	}

	public boolean getWallBound() {
		// TODO Auto-generated method stub
		return wallBound;
	}

	@Override
	public int compareTo(GameCharacter g) {
		return Integer.compare(this.SPEED, g.SPEED);

	}

	public String getSave() {
		// TODO Auto-generated method stub
		return "" + getType() + "," + HP_MAX + "," + MAX_ENERGY+ "," + myLevel;
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
			this.myLevel=myLevel;
			HP_MAX = health;
			MAX_ENERGY = energy;
			this.health = HP_MAX;
			this.energy = MAX_ENERGY;

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

//	public void drawBar2(double per, double per2, Graphics2D g2d) {
//		g2d.setColor(Color.BLACK);
//		g2d.fillRect(x, y - 10, width, 10);
//		g2d.setColor(Color.RED);
//		g2d.fillRect(x, y - 10, (int) ((double) width * (double) per), 10);
//
//		g2d.setColor(new Color(0, 0, 255, 150));
//		g2d.fillRect(x, y - 10, (int) ((double) width * (double) per2), 10);
//		g2d.setColor(Color.WHITE);
//		g2d.drawRect(x - 1, y - 11, width + 1, 11);
//
//	}
	public void drawBar2(double per, double per2, Graphics2D g2d) {
		g2d.setColor(Color.BLACK);
		g2d.fillRect(x, y - 22, width, 22);
		g2d.setColor(Color.RED);
		g2d.fillRect(x, y - 10, (int) ((double) width * (double) per), 10);
g2d.setColor(Color.WHITE);
		g2d.drawRect(x - 1, y - 11, width + 1, 11);
	
		g2d.setColor(Color.BLUE);
		g2d.fillRect(x, y - 21, (int) ((double) width * (double) per2), 10);
		g2d.setColor(Color.WHITE);
		g2d.drawRect(x - 1, y - 22, width + 1, 11);

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
	public void drawTLBar( int total, Graphics2D g2d) {
		total -= 10;
		double per=(double)xp/(double)(Math.pow(level+1, 2)*10);
		g2d.setColor(Color.BLACK);
		g2d.fillRect(30, 120, total, 10);
		g2d.setColor(Color.YELLOW);
		g2d.fillRect(30, 120, (int) ((double) total * (double) per), 10);
		g2d.setColor(Color.WHITE);
		g2d.drawRect(30 - 1, 120 - 1, total + 1, 11);

	}
	
	public static void plusXP(int adder){
		xp+=adder;
		if(xp>=(int)Math.pow(level+1, 2)*10){
			xp-=(int)Math.pow(level+1, 2)*10;
			level++;
		}
	}
	public static void setLevel(int setter){
		level=setter;
	}
	public static void setXP(int setter){
		xp=setter;
	}
	public static int getLevel(){
		return level;
	}
	public static int getXP(){
		return xp;
	}
	public class LevelUp extends JFrame{
		JLabel levelLabel;
		JButton mHealth;
		JButton mEn;
		LevelUp l=this;
		public LevelUp(){
			deltaX=0;
			deltaY=0;
			moveX=false;
			moveY=false;
			levUp=true;
			this.setFocusable(true);
			owner.setFocusable(false);
			this.setSize(400, 200);
			this.setLocation(Statics.BOARD_WIDTH/2-this.getWidth()/2, Statics.BOARD_HEIGHT/2-this.getHeight()/2);
			this.setAlwaysOnTop (true);
		this.setLayout(new BorderLayout());
		
	levelLabel=new JLabel("Skill Points: "+(level-myLevel)+"  |  "+"Level: "+level+"  |  "+"XP: "+xp+"  |  "+"XP needed: "+(int)Math.pow(level+1, 2)*10, SwingConstants.CENTER);
	
	this.add(levelLabel,BorderLayout.NORTH);	 
	JPanel panel=new JPanel();
	mHealth=new JButton("Health: "+HP_MAX);
	mEn=new JButton("Energy: "+MAX_ENERGY);
	mHealth.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(myLevel<level){
			HP_MAX+=5;
			myLevel++;
			mHealth.setText("Health: "+HP_MAX);
			levelLabel.setText("Skill Points: "+(level-myLevel)+"  |  "+"Level: "+level+"  |  "+"XP: "+xp+"  |  "+"XP needed: "+(int)Math.pow(level+1, 2)*10);
		}}
	});
	this.addKeyListener(new KeyListener() {
		
		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
		if(e.getKeyCode()==KeyEvent.VK_L){
			owner.setFocusable(true);
			owner.requestFocus();
			levUp=false;
			l.dispose();
		levMen=null;
		}
		
		}
	});
	panel.add(mHealth);
mEn.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(myLevel<level){
			MAX_ENERGY+=8;
			myLevel++;
			mEn.setText("Energy: "+MAX_ENERGY);
			levelLabel.setText("Skill Points: "+(level-myLevel)+"  |  "+"Level: "+level+"  |  "+"XP: "+xp+"  |  "+"XP needed: "+(int)Math.pow(level+1, 2)*10);
		}}
	});
	panel.add(mEn);
	
	this.add(panel,BorderLayout.CENTER);
	this.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				owner.setFocusable(true);
				owner.requestFocus();
				levUp=false;
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		this.setVisible(true);
		this.requestFocus();}
	}
	public PointPath getPPath() {
		// TODO Auto-generated method stub
		return path;
	}
}
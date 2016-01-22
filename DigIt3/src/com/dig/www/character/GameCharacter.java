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
import java.util.Hashtable;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.dig.www.character.GameCharacter.Types;
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
	protected String nameDraw;
	protected GameCharacter healing;
	protected boolean dead;
	protected Enemy enPoint;
	protected int enUpTimer;
	protected static final int MAX_ATTACK_DISTANCE = 250;
	protected boolean waiting;
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
	protected static Inventory inventory;

	private boolean player;
	private boolean onceNotCollidePlayer;
	protected boolean goTo = true;

	public enum Direction {
		UP {
			public Direction getOpposite() {
				return DOWN;
			}

			public boolean isYAxis() {
				return true;
			}
		},
		DOWN {
			public Direction getOpposite() {
				return UP;
			}

			public boolean isYAxis() {
				return true;
			}
		},
		LEFT {
			public Direction getOpposite() {
				return RIGHT;
			}
		},
		RIGHT {
			public Direction getOpposite() {
				return LEFT;
			}
		},
		DIAG_DR {
			public Direction getOpposite() {
				return DIAG_UL;
			}

			public boolean isDiag() {
				return true;
			}
		},
		DIAG_DL {
			public Direction getOpposite() {
				return DIAG_UR;
			}

			public boolean isDiag() {
				return true;
			}
		},
		DIAG_UR {
			public Direction getOpposite() {
				return DIAG_DL;
			}

			public boolean isDiag() {
				return true;
			}
		},
		DIAG_UL {
			public Direction getOpposite() {
				return DIAG_DR;
			}

			public boolean isDiag() {
				return true;
			}
		},
		NONE {
			public Direction getOpposite() {
				return RIGHT;
			}
		};

		public static int getDir(Direction dir) {
			switch (dir) {
			case UP:
				return 270;
			case DOWN:
				return 90;
			case LEFT:
				return 180;
			case RIGHT:
			default:
				return 0;
			}
		}

		public boolean isYAxis() {
			return false;
		}

		public boolean isDiag() {
			return false;
		}

		public abstract Direction getOpposite();
	}

	protected boolean meleeHit;
	protected boolean specialHit;

	public boolean hasMeleed() {
		return meleeHit;
	}

	protected int poisonTimer;
	protected int poisonHurtTimer;

	public void poison() {
		poisonTimer = 375;
		poisonHurtTimer = 15;
	}

	public boolean hasSpecialed() {
		return specialHit;
	}
public boolean isPlayer(){
	return player;
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
		SIR_COBALT {
			@Override
			public String charName() {
				return "Sir Cobalt";
			}

			public String toString() {
				return "sirCobalt";
			}
		},
		WIZARD {
			@Override
			public String charName() {
				return "The Wizard";
			}

			public String toString() {
				return "wizard";
			}
		},
		MACARONI {
			@Override
			public String charName() {
				return "Super Macaroni Noodle";
			}

			public String toString() {
				return "macaroni";
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

		public static Types translate(String string) {
			for (Types i : Types.values())
				if (i.toString().equals(string))
					return i;

			return PROJECTILE;
		}

		public static Types translateCharName(String string) {
			for (Types i : Types.values())
				if (i.charName().equals(string))
					return i;

			return PROJECTILE;
		}

	}

	private boolean willTalk = false;

	private int deltaX = 0;
	private int deltaY = 0;
	protected Direction direction = Direction.RIGHT;
	protected Direction diagBackup = Direction.NONE;
	protected Types type;

	private boolean move = true;
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
	protected transient float energy;

	private int SPEED;

	private int counter = 0;
	private int animationTimer = 7;

	private static final int ANIMAX = 7;
	private static final int MAX = 4;
	private String charName;

	private int HP_TIMER_MAX = 50;
	private int HITSTUN_MAX = 10;

	protected int NEG_TIMER_MELEE;
	protected int NEG_TIMER_RANGED;
	protected int NEG_TIMER_SPECIAL;
	protected int TIMER_MELEE;
	protected int TIMER_RANGED;
	protected int TIMER_SPECIAL;

	private int hpTimer;
	private int hitstunTimer = 0;
	private boolean isPlayerCollide;
	protected int MEnC;
	protected int REnC;
	protected int SEnC;
	protected int pathUpdateTimer;

	protected int BHP_MAX;
	protected int BMAX_ENERGY;
	protected int BmeleeDamage;
	protected int BrangedDamage;
	protected int BspecialDamage;
	protected static final float HP_MAX_M = 1;
	protected static final float MAX_ENERGY_M = 1;
	protected static final float meleeDamage_M = 1;
	protected static final float rangedDamage_M = 1;
	protected static final float specialDamage_M = 1;

	protected float HP_MAX;
	protected float MAX_ENERGY;
	protected float meleeDamage;
	protected float rangedDamage;
	protected float specialDamage;

	protected float health = HP_MAX;
	protected Hashtable<Direction, Boolean> collisionFlags;
	// TODO strength stuff
	protected int strength;
	// private int strengthIncrementer = 0;
	// private int timesIncremented = 1;

	protected int itemTimer = 0;
	protected static final int ITEM_MAX = 20;

	public GameCharacter(int x, int y, Board owner, Types type, String charName, boolean player, int NEG_TIMER_MELEE, int NEG_TIMER_RANGED,
			int NEG_TIMER_SPECIAL, int TIMER_MELEE, int TIMER_RANGED, int TIMER_SPECIAL, int HP_MAX, int SPEED, int MAX_ENERGY, int MEnC, int REnC,
			int SEnC, int meleeDamage, int rangedDamage, int specialDamage, int strength) {
		super(x, y, "images/dummy.png", owner);
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
		this.BHP_MAX = HP_MAX;
		this.SPEED = SPEED;

		this.MAX_ENERGY = MAX_ENERGY;
		this.BMAX_ENERGY = MAX_ENERGY;
		energy = MAX_ENERGY;
		health = HP_MAX;
		this.MEnC = MEnC;
		this.REnC = REnC;
		this.SEnC = SEnC;
		this.meleeDamage = meleeDamage;
		this.rangedDamage = rangedDamage;
		this.specialDamage = specialDamage;

		this.BmeleeDamage = Math.max(meleeDamage, 10);
		this.BrangedDamage = Math.max(rangedDamage, 10);
		this.BspecialDamage = Math.max(specialDamage, 10);

		meleeTimer = this.NEG_TIMER_MELEE;
		rangedTimer = this.NEG_TIMER_RANGED;
		specialTimer = this.NEG_TIMER_SPECIAL;

		this.strength = strength;
		direction = Direction.DOWN;
		image = newImage("n");

		resetFlags();
	}

	@Override
	public void animate() {
		if(!(this==owner.getCharacter()||(owner.getClient()==null&&!player))){
			basicAnimate();
			onScreen = getBounds().intersects(owner.getScreen());
		}else{
		if (dead) {
			basicAnimate();
			if (owner.getCharacter() != this && !owner.getCharacter().isDead() && getBounds().intersects(owner.getCharacter().getBounds()))
				dead = false;
			onScreen = getBounds().intersects(owner.getScreen());
		} else {
			collisionFlagged = false;
			if (poisonTimer > 0) {
				if (poisonHurtTimer <= 0) {
					health -= 1;
					hpTimer = 100;
					if (health <= 0)
						takeDamage(0, false);
					poisonHurtTimer = 15;
				} else
					poisonHurtTimer--;
			}

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
				if (waiting && (owner.getCharPoint().distance(x, y) < 250 || owner.pointedPoint != null)) {
					waiting = false;
				}
				if (path != null) {
					path.update();
					if (pointTimer <= 0 && path.getPoints().size() > 0) {
						if (path.getPoints().size() < 4) {
							path.removeLast();
						} else {
							path.findPath();
						}
						pointTimer = 18;
					} else {
						pointTimer--;
					}
					if (path.getPoints().size() > 0 && new Point(x, y).distance(path.getCurrentFind()) < 11// &&JOptionPane.showConfirmDialog(owner,
																											// getType().charName()+" wants to remove PathPoint")==JOptionPane.YES_OPTION
					) {
						pointTimer = 18;
						path.removeLast();
					}
					if (new Point(x, y).distance(owner.getCharPoint()) < 100 && owner.pointedPoint == null) {
						// System.out.println(getType().charName()+" CLOSE:"+new
						// Date());
						path = null;
					}
				}
				if (path == null && owner.pointedPoint == null && type == Types.HEART) {
					if (healing == null && enUpTimer <= 0) {
						int chosenNum = -2;
						int theDis = Integer.MAX_VALUE;
						if (owner.getCharacter() != this && owner.getCharPoint().distance(new Point(x, y)) < MAX_ATTACK_DISTANCE * 1.75
								&& owner.getCharacter().health < (owner.getCharacter().HP_MAX * 0.75) && theDis > owner.getCharacter().health) {
							theDis = (int) owner.getCharacter().health;
							chosenNum = -1;
						}
						for (int c = 0; c < owner.getFriends().size(); c++) {
							Point currentEn = new Point(owner.getFriends().get(c).getX(), owner.getFriends().get(c).getY());
							if (owner.getFriends().get(c) != this && currentEn.distance(new Point(x, y)) < MAX_ATTACK_DISTANCE * 1.75
									&& owner.getFriends().get(c).health < (owner.getFriends().get(c).HP_MAX * 0.75)
									&& theDis > owner.getFriends().get(c).health) {
								theDis = (int) owner.getFriends().get(c).health;
								chosenNum = c;
							}
						}

						if (chosenNum > -1) {
							healing = owner.getFriends().get(chosenNum);
						} else if (chosenNum == -1)
							healing = owner.getCharacter();
					}
				} else {
					healing = null;
				}
				if (path == null && owner.pointedPoint == null && healing == null) {
					if (enPoint == null && enUpTimer <= 0) {
						int chosenNum = -1;
						int theDis = MAX_ATTACK_DISTANCE;
						for (int c = 0; c < owner.getEnemies().size(); c++) {
							Point currentEn = new Point(owner.getEnemies().get(c).getX(), owner.getEnemies().get(c).getY());
							if (!owner.getEnemies().get(c).isInvincible() && !(owner.getEnemies().get(c) instanceof Projectile)
									&& currentEn.distance(new Point(x, y)) < theDis
									&& owner.getCharPoint().distance(currentEn) > currentEn.distance(new Point(x, y))) {
								theDis = (int) currentEn.distance(new Point(x, y));
								chosenNum = c;
							}
						}

						if (chosenNum != -1) {
							enPoint = owner.getEnemies().get(chosenNum);

							goTo = true;
							if (this instanceof Diamond || this instanceof Heart || health < HP_MAX / 2)
								goTo = false;
						}
					} else if (enUpTimer > 0) {
						enUpTimer--;
					}
				} else {
					enPoint = null;
				}
				if (enPoint != null
						&& ((new Point(enPoint.getX(), enPoint.getY()).distance(owner.getCharPoint()) > MAX_ATTACK_DISTANCE * 1.75 || !enPoint
								.isAlive()) || new Point(x, y).distance(owner.getCharPoint()) > MAX_ATTACK_DISTANCE * 1.75 || (health < HP_MAX / 2 && goTo))) {
					enPoint = null;
				}
				if (healing != null
						&& ((new Point(healing.getX(), healing.getY()).distance(owner.getCharPoint()) > MAX_ATTACK_DISTANCE * 1.75 || healing
								.isDead()) || new Point(x, y).distance(owner.getCharPoint()) > MAX_ATTACK_DISTANCE * 2.75 || healing.health > healing.HP_MAX * 0.75)) {
					healing = null;
				}
				if (!wallBound && !waiting) {
					// System.out.println(path);

					if (path != null) {

						if (path.getPoints().size() > 0) {
							getToPoint = path.getCurrentFind();

							meleePress = false;

							goTo = true;
						} else {
							path = null;
							getToPoint = owner.getCharacter().getBounds().getLocation();

							goTo = true;
						}

					} else if (owner.pointedPoint != null) {
						getToPoint = owner.pointedPoint;
						goTo = true;
					} else if (enPoint != null) {
						if (goTo == false)
							getToPoint = owner.getCharPoint();
						else
							getToPoint = new Point(enPoint.getX(), enPoint.getY());

					} else if (healing != null) {
						getToPoint = new Point(healing.getX(), healing.getY());
						goTo = true;
					} else {
						getToPoint = owner.getCharPoint();
						goTo = true;
					}
					if (

					path != null
							|| ((enPoint == null && healing == null && getToPoint.distance(x, y) > 125)
									|| (enPoint != null && !getActBounds().intersects(enPoint.getBounds()) && !rangedPress) || (healing != null
									&& !getBounds().intersects(healing.getBounds()) && new Point(x, y).distance(healing.getX(), healing.getY()) > 50)

							)

					) {
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

							deltaX = -SPEED;
							moveX = true;

						} else if (amount != 1 && x < getToPoint.x - (path == null ? (SPEED * 2) : 0)) {

							deltaX = SPEED;
							moveX = true;

						} else {
							deltaX = 0;
							moveX = false;
						}
						if (amount != 0 && y > getToPoint.y + (path == null ? (SPEED * 2) : 0)) {

							deltaY = -SPEED;
							moveY = true;
							// if (||y > getToPoint.y + 50)

						} else if (amount != 0 && y < getToPoint.y - (path == null ? (SPEED * 2) : 0)) {

							deltaY = SPEED;
							moveY = true;
							// if (y < getToPoint.y - 50)

						} else {
							moveY = false;
							deltaY = 0;
						}

					}

					else {
						deltaX = 0;
						deltaY = 0;
						moveX = false;
						moveY = false;
						// if(type==Types.HEART)
						// {
						// System.out.println(deltaX);
						// System.out.println(deltaY);
						// }
					}

					if (!goTo && enPoint != null) {
						if (moveX && moveY) {
							deltaY = 0;
							moveY = false;
						} else if (moveX) {
							if (y < enPoint.getY()) {
								deltaY = -10;
								moveY = true;
								moveU = false;
							} else {
								deltaY = 10;
								moveY = true;
								moveU = true;
							}

						} else {
							if (x < enPoint.getX()) {
								deltaY = -10;
								moveX = true;
								moveL = true;
							} else {
								deltaX = 10;
								moveX = true;
								moveL = false;
							}
						}

					}
					// if(enPoint==null||!goTo){
					// if (deltaX == 0) {
					// if (deltaY != 0) {
					// if (deltaY < 0)
					// direction = Direction.UP;
					// else
					// direction = Direction.DOWN;
					// }} else {
					// if (deltaX > 0)
					// direction = Direction.RIGHT;
					// else
					// direction = Direction.LEFT;
					// }
					//
					//
					if (deltaX != 0 || deltaY != 0) {
						if (deltaX == 0) {
							if (deltaY > 0)
								direction = Direction.DOWN;
							else
								direction = Direction.UP;
						} else if (deltaY == 0) {
							if (deltaX > 0)
								direction = Direction.RIGHT;
							else
								direction = Direction.LEFT;
						} else {
							if (deltaX > 0) {
								if (deltaY > 0) {
									direction = Direction.DIAG_DR;
								} else {
									direction = Direction.DIAG_UR;
								}
							} else {
								if (deltaY > 0) {
									direction = Direction.DIAG_DL;
								} else {
									direction = Direction.DIAG_UL;
								}
							}
						}
					}
					// }
					// else{
					// if(Math.abs(x-enPoint.getX())>Math.abs(y-enPoint.getY())){
					// if(y<enPoint.getY())
					// direction=Direction.UP;
					// else
					// direction=Direction.DOWN;
					// }
					// else{
					// if(x<enPoint.getX())
					// direction=Direction.RIGHT;
					// else
					// direction=Direction.LEFT;
					// }
					//
					// }
					boolean shouldPressMelee = false;
					boolean shouldRangedPress = false;
					Rectangle rect;
					switch (direction) {
					case UP:
						rect = new Rectangle(x + rangedAddX(), y + rangedAddY() - Statics.BOARD_HEIGHT, 20, Statics.BOARD_HEIGHT);
						break;
					case DOWN:
						rect = new Rectangle(x + rangedAddX(), y + rangedAddY(), 20, Statics.BOARD_HEIGHT);
						break;
					case LEFT:
						rect = new Rectangle(x + rangedAddX() - Statics.BOARD_WIDTH, y + rangedAddY(), Statics.BOARD_WIDTH, 20);
						break;
					case RIGHT:
						rect = new Rectangle(x + rangedAddX(), y + rangedAddY(), Statics.BOARD_WIDTH, 20);
						break;
					default:
						rect = new Rectangle();
					}

					for (int c = 0; c < owner.getEnemies().size(); c++) {
						if (((this instanceof Diamond && owner.getEnemies().get(c) instanceof Projectile && new Point(getMidX(), getMidY()).distance(
								owner.getEnemies().get(c).getMidX(), owner.getEnemies().get(c).getMidY()) < 750) || getActBounds().intersects(
								owner.getEnemies().get(c).getBounds()))
								&& !owner.getEnemies().get(c).isInvincible()
								&& (!(owner.getEnemies().get(c) instanceof Projectile) || this instanceof Diamond)) {
							shouldPressMelee = true;

							break;
						} else {
							if (!shouldRangedPress && rect.intersects(owner.getEnemies().get(c).getBounds())
									&& !owner.getEnemies().get(c).isInvincible() && !(owner.getEnemies().get(c) instanceof Projectile)) {
								shouldRangedPress = true;
							}
						}
					}
					if (type == Types.HEART) {
						if (owner.getCharacter().getBounds().intersects(getActBounds()) && owner.getCharacter() != this
								&& owner.getCharacter().health < owner.getCharacter().HP_MAX * 0.75)
							shouldPressMelee = true;
						else {
							for (GameCharacter g : owner.getFriends()) {
								if (g != this && g.getBounds().intersects(getActBounds()) && g.health < g.HP_MAX * 0.75) {
									shouldPressMelee = true;
									break;
								}

							}
						}
					}
					meleePress = shouldPressMelee;

					if (meleePress)
						shouldRangedPress = false;
					rangedPress = shouldRangedPress;

					// if (enPoint != null) {
					// boolean xway = false;
					// if (Math.abs(enPoint.getX() - x) >
					// Math.abs(enPoint.getY() -
					// y)) {
					// xway = true;
					// }
					// if (xway) {
					// if (x > enPoint.getX()) {
					// direction = Direction.LEFT;
					// } else {
					// direction = Direction.RIGHT;
					// }
					// } else {
					// if (y > enPoint.getY()) {
					// direction = Direction.UP;
					// } else {
					// direction = Direction.DOWN;
					//
					// }
					// }
					// if (getActBounds().intersects(enPoint.getBounds())) {
					// meleePress = true;
					// } else {
					// meleePress = false;
					// }
					// }
				}
				if (waiting) {
					deltaX = 0;
					deltaY = 0;
					moveX = false;
					moveY = false;
					image = newImage("n");
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
					if (move) {
						if (moveX) {
							// if (moveL ? collisionFlags.get(Direction.LEFT) :
							// collisionFlags.get(Direction.RIGHT))
							if (moveL) {
								if(deltaX<0)
									deltaX+=2;
								deltaX++;
								if (deltaX > SPEED)
									deltaX = SPEED;
							} else {
								if(deltaX>0)
									deltaX-=2;
								deltaX--;
								if (deltaX < -SPEED)
									deltaX = -SPEED;
							}
						}
						else {
							if (deltaX > 0){
								deltaX-=3;
							if(deltaX<0)
								deltaX=0;
							}
							if (deltaX < 0){
								deltaX+=3;
							if(deltaX>0)
								deltaX=0;
							}
						}

						if (moveY) {
							// if (moveU ? collisionFlags.get(Direction.UP) :
							// collisionFlags.get(Direction.DOWN))
							if (!moveU) {
								if(deltaY>0)
									deltaY-=2;
								deltaY--;
								if (deltaY < -SPEED)
									deltaY = -SPEED;
							} else {
								if(deltaY<0)
								deltaY+=2;
								deltaY++;
								if (deltaY > SPEED)
									deltaY = SPEED;
							}
						}
							else {
								if (deltaY > 0){
									deltaY-=3;
								if(deltaY<0)
									deltaY=0;
								}
								if (deltaY < 0){
									deltaY+=3;
								if(deltaY>0)
									deltaY=0;
								}
						}

						owner.setScrollX(deltaX);
						owner.setScrollY(deltaY);
					} else {
						move = true;
					}
				} else if(!(this instanceof Diamond)||((Diamond)this).getShield()==null||!((Diamond)this).getShield().collideWithHook()){
					x += deltaX;
					y += deltaY;
				}
			} else {

				if (player) {
					double tempD = Statics.pointTowards(new Point(wallX, wallY), owner.getCharPoint()) + 180;
					if (tempD > 360)
						tempD -= 360;
					owner.setScrollX((int) (Math.cos(Math.toRadians((double) tempD)) * SPEED));
					owner.setScrollY((int) (Math.sin(Math.toRadians((double) tempD)) * SPEED));

					owner.reAnimate();
					wallBound = false;
				} else {
					if (onceNotCollidePlayer) {
						enPoint = null;
						healing = null;
						enUpTimer = 25;
						// if (new Point(getMidX(), getMidY()).distance(new
						// Point(wallX, getMidY())) < 100) {
						// deltaX = -deltaX;
						// x += deltaX;
						// }
						// if (new Point(getMidX(), getMidY()).distance(new
						// Point(getMidX(), wallY)) < 100) {
						// deltaY = -deltaY;
						// y += deltaY;
						// }

						double tempD = Statics.pointTowards(new Point(wallX, wallY), new Point(x, y)) + 180;
						if (tempD > 360)
							tempD -= 360;
						x -= (int) (Math.cos(Math.toRadians((double) tempD)) * SPEED);
						y -= (int) (Math.sin(Math.toRadians((double) tempD)) * SPEED);

						// x += deltaX;
						// y += deltaY;
					} else if (healing == null && enPoint == null) {
						deltaX = 0;
						deltaY = 0;
						moveX = false;
						moveY = false;
						image = newImage("n");
					}
					if (pathUpdateTimer > 0)
						pathUpdateTimer--;
					// if(onceNotCollidePlayer)
					// JOptionPane.showMessageDialog(owner, "try");
					if (pathUpdateTimer <= 0 && onceNotCollidePlayer && path == null
					// && new Point(x, y).distance(owner.getCharPoint()) > 50
					) {
						// JOptionPane.showMessageDialog(owner, "work");
						for (int c = 0; c < owner.getFriends().size(); c++) {
							if (owner.getFriends().get(c) == this) {
								me = c;
								break;
							}
						}
						int realX = 0;
						int realY = 0;
						boolean changedP = false;
						if (owner.pointedPoint != null) {
							changedP = true;
							realX = owner.getCharacterX();
							realY = owner.getCharacterY();
							owner.getCharacter().setX(owner.pointedPoint.x);
							owner.getCharacter().setY(owner.pointedPoint.y);
						}

						path = new PointPath(me, owner);
						if (changedP) {
							owner.getCharacter().setX(realX);
							owner.getCharacter().setY(realY);

						}

						if (path.getPoints().size() > 0) {
							// x=path.getCurrentFind().x;
							// y=path.getCurrentFind().y;
							// JOptionPane.showMessageDialog(owner,
							// "size greater than 0");
						} else {
							// JOptionPane.showMessageDialog(owner, "size 0");
							pathUpdateTimer = 50;
						}
						meleePress = false;
						pointTimer = 18;
						path.update();
					}

				}
				onceNotCollidePlayer = false;
				wallBound = false;
			}
			setAttacks();

			onScreen = getBounds().intersects(owner.getScreen());
			resetFlags();
		}
		
		illuminated = false;
	}}

	public void setWaiting(boolean setter) {
		waiting = setter;
	}

	public void keyPressed(int keyCode) {
		if (keyCode == KeyEvent.VK_H) {
			energy = 0;
			for (int c = 0; c < owner.getFriends().size(); c++) {
				if (new Point(x, y).distance(new Point(owner.getFriends().get(c).getX(), owner.getFriends().get(c).getY())) < 50) {
					owner.getFriends().get(c).setX(x);
					owner.getFriends().get(c).setY(y);
					owner.getFriends().get(c).setEnergy(0);
				}
			}
		} else if (keyCode == KeyEvent.VK_MINUS) {
			level++;
		} else if (keyCode == KeyEvent.VK_0) {
			inventory.setMoney(Integer.MAX_VALUE);
		}

		// Left
		if (keyCode == Preferences.LEFT()) {
			//
			// direction = Direction.LEFT;
			if (deltaY == 0) {
				if (!collisionFlags.get(Direction.LEFT)) {
					// moveX = false;
					// moveL = false;
					return;
				}

				direction = Direction.LEFT;
				diagBackup = Direction.NONE;
			} else {
				Direction diag = deltaY < 0 ? Direction.DIAG_UL : Direction.DIAG_DL;
				if (!collisionFlags.get(diag)) {
					// moveX = false;
					// moveL = false;
					return;
				}

				direction = diag;
				diagBackup = Direction.LEFT;
			}

			moveX = true;
			moveL = true;
			move = false;
		}

		// Right
		else if (keyCode == Preferences.RIGHT()) {
			//
			// direction = Direction.RIGHT;
			if (deltaY == 0) {
				if (!collisionFlags.get(Direction.RIGHT)) {
					// moveX = false;
					// moveL = false;
					return;
				}

				direction = Direction.RIGHT;
				diagBackup = Direction.NONE;
			} else {
				Direction diag = deltaY < 0 ? Direction.DIAG_UR : Direction.DIAG_DR;
				if (!collisionFlags.get(diag)) {
					// moveX = false;
					// moveL = false;
					return;
				}

				direction = diag;
				diagBackup = Direction.RIGHT;
			}

			moveX = true;
			moveL = false;
			move = false;
		}

		// Up
		else if (keyCode == Preferences.UP()) {
			//
			// direction = Direction.UP;
			if (deltaX == 0) {
				if (!collisionFlags.get(Direction.UP)) {
					// moveY = false;
					// moveU = false;
					return;
				}

				direction = Direction.UP;
				diagBackup = Direction.NONE;
				moveY = true;
				moveU = true;
				move = false;
			} else {
				Direction diag = deltaX < 0 ? Direction.DIAG_UR : Direction.DIAG_UL;
				if (!collisionFlags.get(diag)) {
					// moveY = false;
					// moveU = false;
					return;
				}

				direction = diag;
				diagBackup = Direction.UP;
				moveY = true;
				moveU = true;
				move = false;
			}
		}

		// Down
		else if (keyCode == Preferences.DOWN()) {
			//
			// direction = Direction.DOWN;
			if (deltaX == 0) {
				if (!collisionFlags.get(Direction.DOWN)) {
					// moveY = false;
					// moveU = false;
					return;
				}

				direction = Direction.DOWN;
				diagBackup = Direction.NONE;
				moveY = true;
				moveU = false;
				move = false;
			} else {
				Direction diag = deltaX < 0 ? Direction.DIAG_DR : Direction.DIAG_DL;
				if (!collisionFlags.get(diag)) {
					// moveY = false;
					// moveU = false;
					return;
				}

				direction = diag;
				diagBackup = Direction.DOWN;
				moveY = true;
				moveU = false;
				move = false;
			}
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

		// Use current item
		else if (keyCode == Preferences.ITEM()) {

			if (itemTimer <= 0) {
				itemTimer = ITEM_MAX;
				Items i = inventory.useItem();

				if (!i.isWeapon() && i != Items.FOOD_NORMAL)
					owner.addItem(i);
				else if (i == Items.FOOD_NORMAL) {
					// Add food code here
				} else {
					owner.getfP().add(new FProjectile(dir, x, y, 25, this, i.getPath(), owner, Moves.ITEM));
				}

				// TODO More strength stuff; this code would be removed if we
				// don't allow characters to get stronger. Currently removed
				// until I actually present the idea.

				// strengthIncrementer++;
				// if (strengthIncrementer == 5 * timesIncremented) {
				// strength++;
				// strengthIncrementer = 0;
				// timesIncremented++;
				// }
			}
		}
	}

	protected void resetFlags() {

		collisionFlags = new Hashtable<Direction, Boolean>();
		for (Direction d : Direction.values())
			collisionFlags.put(d, true);
	}

	protected void OpenLevelUp() {

		levMen = new LevelUp();
	}

	public abstract Moves getRangedMove();

	public Moves getSpecialProMove() {
		return Moves.NONE;

	}

	public void keyReleased(int keyCode) {

		// Left/Right
		if (keyCode == Preferences.LEFT() || keyCode == Preferences.RIGHT()) {

			if (direction.isDiag()) {
				if (diagBackup.isYAxis())
					direction = diagBackup;
				else if (deltaY > 0)
					direction = Direction.UP;
				else if (deltaY < 0)
					direction = Direction.DOWN;
			} else if (direction == Direction.LEFT || direction == Direction.RIGHT)
				if (deltaY > 0)
					direction = Direction.UP;
				else if (deltaY < 0)
					direction = Direction.DOWN;

			diagBackup = Direction.NONE;
			//deltaX = 0;
			moveX = false;
		}

		// Up/Down
		else if (keyCode == Preferences.DOWN() || keyCode == Preferences.UP()) {
			if (direction.isDiag()) {
				if (!diagBackup.isYAxis())
					direction = diagBackup;
				else if (deltaX > 0)
					direction = Direction.LEFT;
				else if (deltaX < 0)
					direction = Direction.RIGHT;
			} else if (direction == Direction.UP || direction == Direction.DOWN) {
				if (deltaX > 0)
					direction = Direction.LEFT;
				else if (deltaX < 0)
					direction = Direction.RIGHT;
			}

			diagBackup = Direction.NONE;
			//deltaY = 0;
			moveY = false;
		}

		// Melee
		if (keyCode == Preferences.ATTACK()) {
			meleePress = false;

		}
		// Ranged
		else if (keyCode == Preferences.PROJECTILE()) {
			rangedPress = false;
			if (this instanceof Spade)
				((Spade) this).keyReleased = true;
			// if (rangedTimer > 0)
			// rangedTimer = 0;
		}

		// Special
		else if (keyCode == Preferences.SPECIAL()) {
			specialPress = false;
			// if (!(type == Types.CLUB)) {
			//
			// // if (specialTimer > 0)
			// // specialTimer = 0;
			// }
		}

		// End
	}

	private boolean collisionFlagged = false;

	public void setCollisionFlag(Sprite wall) {

		if (collisionFlagged)
			return;

		collisionFlags.replace(direction, false);

		switch (!direction.isDiag() ? direction : getPlacement(wall)) {
		case UP:
		case DOWN:
			collisionFlags.replace(deltaY < 0 ? Direction.UP : Direction.DOWN, false);

			deltaY = 0;
			moveY = false;
			moveU = false;
			break;

		case LEFT:
		case RIGHT:
			collisionFlags.replace(deltaX < 0 ? Direction.LEFT : Direction.RIGHT, false);

			deltaX = 0;
			moveX = false;
			moveL = false;
			break;

		default:
			deltaY = 0;
			moveY = false;
			moveU = false;
			deltaX = 0;
			moveX = false;
			moveL = false;
			break;
		}

		if (getStationaryCollisionBounds().intersects(wall.getBounds())) {
			wallBound = true;
			wallX = wall.getX();
			wallY = wall.getY();
		}

		collisionFlagged = true;
	}

	public void presetCollisionFlag(int i) {
		collisionFlags.replace(placement[i], false);
	}

	// TODO stuff
	// private static final Direction[] placement = new Direction[] {
	// Direction.UP, Direction.RIGHT, Direction.DOWN, Direction.LEFT };
	private static final Direction[] placement = new Direction[] { Direction.UP, Direction.RIGHT, Direction.DOWN, Direction.LEFT, Direction.DIAG_UL,
			Direction.DIAG_UR, Direction.DIAG_DL, Direction.DIAG_DR };

	public Direction getPlacement(Sprite check) {
		Rectangle[] r = getDirBounds();
		Rectangle r2 = check.getBounds();

		for (int i = 0; i < r.length; i++)
			if (r2.intersects(r[i]))
				return placement[i];

		return Direction.NONE;
	}

	public int round(int round) {
		return Math.round(round / 100) * 100;
	}

	public int getMidY() {
		return super.getMidY() + CLIP;
	}

	// TODO collision
	public void collision(Sprite collide, boolean isPlayer) {
		// if (isPlayer) {
		// if ((!goTo) && (enPoint != null)) {
		// return;
		// }
		// }
		if (player)
			setCollisionFlag(collide);
		else {
			if (!isPlayer || !(enPoint != null || healing != null))
				wallBound = true;
			this.isPlayerCollide = isPlayer;
			if (!isPlayer) {
				onceNotCollidePlayer = true;

			}
			wallX = collide.getX();
			wallY = collide.getY();
		}
	}

	// TODO forwardBounds
	public static final int CLIP = 40;

	public Rectangle[] getForwardBounds() {

		return new Rectangle[] { new Rectangle(x + CLIP, y + CLIP - 10, width - 2 * CLIP, 10),
				new Rectangle(x + width - CLIP, y + CLIP, 10, height - CLIP), new Rectangle(x + CLIP, y + height, width - 2 * CLIP, 10),
				new Rectangle(x + CLIP - 10, y + CLIP, 10, height - CLIP) };
	}

	public Rectangle[] getDirBounds() {

		return new Rectangle[] { new Rectangle(x + CLIP, y + CLIP - SPEED, width - 2 * CLIP, SPEED),
				new Rectangle(x + width - CLIP, y + CLIP, SPEED, height - CLIP), new Rectangle(x + CLIP, y + height, width - 2 * CLIP, SPEED),
				new Rectangle(x + CLIP - SPEED, y + CLIP, SPEED, height - CLIP), new Rectangle(x + CLIP - SPEED, y + CLIP - SPEED, SPEED, SPEED),
				new Rectangle(x + width - CLIP, y + CLIP - SPEED, SPEED, SPEED), new Rectangle(x + CLIP - SPEED, y + height, SPEED, SPEED),
				new Rectangle(x + width - CLIP, y + height, SPEED, SPEED) };
	}

	public Rectangle getForwardBound() {
		if (deltaX == 0 || deltaY == 0)
			switch (direction) {
			case UP:
				return new Rectangle(x + CLIP, y + CLIP - SPEED, width - 2 * CLIP, SPEED);
			case DOWN:
				return new Rectangle(x + CLIP, y + height, width - 2 * CLIP, SPEED);
			case LEFT:
				return new Rectangle(x + CLIP - SPEED, y + CLIP, SPEED, height - CLIP - SPEED / 2);
			case RIGHT:
			default:
				return new Rectangle(x + width - CLIP, y + CLIP, SPEED, height - CLIP - SPEED / 2);
			}
		else {
			return new Rectangle(moveL ? this.x + CLIP - SPEED : this.x + width - CLIP + SPEED, moveU ? this.y + CLIP - SPEED : this.y + height
					+ SPEED, SPEED, SPEED);
		}
	}

	public Rectangle getTalkBounds() {
		if (willTalk) {
			willTalk = false;

			switch (direction.isDiag() ? diagBackup : direction) {

			case LEFT:
				return new Rectangle(x, y + CLIP, CLIP, height - CLIP);
			case RIGHT:
				return new Rectangle(x + width - CLIP, y + CLIP, CLIP, height - CLIP);
			case UP:
				return new Rectangle(x + CLIP, y, width - 2 * CLIP, CLIP);
			case DOWN:
				return new Rectangle(x + CLIP, y + height, width - 2 * CLIP, CLIP);
			default:
				return new Rectangle(x - 50, y - 50, width + 100, height + 100);
			}
		}

		return null;
	}

	public abstract Rectangle getActBounds();

	private Font HUD = new Font("Calibri", Font.BOLD, 30);

	public abstract String getRangedString();

	private Point setAttacks() {
		Point shieldPos = null;
		if (meleePress && rangedTimer <= 0 && specialTimer <= 0) {
			if (meleeTimer <= NEG_TIMER_MELEE && energy >= MEnC// ||this
																// instanceof
																// Diamond
			) {
				meleeTimer = TIMER_MELEE;
				energy -= MEnC;
				meleeHit = false;
			}
		}
		if (specialPress && rangedTimer <= 0 && meleeTimer <= 0) {
			if (specialTimer <= NEG_TIMER_SPECIAL && (energy >= SEnC || this instanceof Heart)) {

				specialTimer = TIMER_SPECIAL;
				specialHit = false;
				if (getType() == Types.MACARONI) {
					owner.getfP().add(new Puddle(x, y, this, owner));
					if (getType() == Types.MACARONI)
						owner.getfP().get(owner.getfP().size() - 1).setTurning(true);
				}
				if (type == Types.HEART) {
					if (!((Heart) this).usingField()) {
						owner.getObjects().add(new Dispenser(x, y, this, "images/characters/projectiles/dispenser.gif", owner, dir));
						((Heart) this).start();
						specialTimer = NEG_TIMER_SPECIAL + 40;
					} else {
						((Heart) this).end();
					}
				} else {

					energy -= SEnC;
				}
			}
		}
		if (rangedPress && meleeTimer <= 0 && specialTimer <= 0) {
			if (rangedTimer <= NEG_TIMER_RANGED && energy >= REnC) {
				rangedTimer = TIMER_RANGED;
				energy -= REnC;
				String s = "images/characters/projectiles" + "/" + getRangedString();
if(getType()==Types.DIAMOND){
	owner.getfP().add(new Shield(dir, x+rangedAddX(), y+rangedAddY(), 25, this, owner));
}
else if (getType() != Types.SPADE) {
					owner.getfP().add(new FProjectile(dir, x + rangedAddX(), y + rangedAddY(), 25, this, s, owner, getRangedMove()));
					if (getType() == Types.MACARONI)
						owner.getfP().get(owner.getfP().size() - 1).setTurning(true);
				} else
					((Spade) this).keyReleased = false;
				if (this instanceof SirCobalt)
					owner.getfP().get(owner.getfP().size() - 1).setTurning(true);
			}

		}

		if (this instanceof Club) {
			if (specialTimer >= 0 && specialTimer % 50 == 0) {
				String s = "images/characters/projectiles" + "/" + getRangedString();
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

	public int rangedAddX() {
		return 25;
	}

	public int rangedAddY() {
		return height / 2 - 16;
	}

	@Override
	public void draw(Graphics2D g2d) {
		if (!player) {
			deltaX = -deltaX;
			deltaY = -deltaY;
		}
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
		if (!player) {
			deltaX = -deltaX;
			deltaY = -deltaY;
		}
		if (player)
			dir = getCurrentDir();
		
		if(this instanceof Macaroni)
		if (toMoveString() != null) {
			g2d.drawImage(newImage(toMoveString()), x-20, y-20, owner);
			if (owner.darkenWorld())
				g2d.drawImage(newShadow(toMoveString()), x-20, y-20, owner);
		}
		Point p = setAttacks();
		if (visible && onScreen) {
			
				if (direction == Direction.LEFT || direction == Direction.DIAG_UL || direction == Direction.DIAG_DL) {
					g2d.drawImage(image, x + width, y, -width, height, owner);
					if (owner.darkenWorld() && !illuminated)
						g2d.drawImage(shadow, x + width, y, -width, height, owner);
				} else // ((direction.isDiag() ? (diagBackup != Direction.LEFT)
						// : (direction != Direction.LEFT)) && direction !=
						// Direction.UP)
				{
					g2d.drawImage(image, x, y, owner);
					drawShadow(g2d);

				
			}
			if (p != null) {
				g2d.setColor(Color.black);

				if (direction == Direction.LEFT || direction == Direction.RIGHT)
					g2d.drawLine(direction == Direction.LEFT ? x + 39 : x + width - 65, y + 55, (int) p.getX() + Statics.BLOCK_HEIGHT / 2,
							(int) p.getY() + Statics.BLOCK_HEIGHT / 2);
				else
					g2d.drawLine(direction == Direction.DOWN ? x + 34 : x + width - 34, y + 59, (int) p.getX() + Statics.BLOCK_HEIGHT / 2,
							(int) p.getY() + Statics.BLOCK_HEIGHT / 2);

			}

			if (!(this instanceof Diamond))
				drawTool(g2d);

			// if (direction == Direction.UP) {
			// g2d.drawImage(image, x, y, owner);
			// if (owner.darkenWorld())
			// g2d.drawImage(shadow, x, y, owner);
			// }

			if (this instanceof Diamond)
				drawTool(g2d);
		} else if (!onScreen) {
			int xI = x, yI = y;

			if (x < 0)
				xI = 0;
			else if (x > Statics.BOARD_WIDTH - 50)
				xI = Statics.BOARD_WIDTH - 50;

			if (y < 0)
				yI = 0;
			else if (y > Statics.BOARD_HEIGHT - 50)
				yI = Statics.BOARD_HEIGHT - 50;

			g2d.drawImage(Statics.newImage("images/characters/" + (charName != null ? charName : "spade") + "/icon.png"), xI, yI, owner);
		}

		if (owner.getCharacter()==this) {
			g2d.setColor(Color.BLACK);
			// 30 + (int) Math.ceil((double) wallet.getDigits()) * 30 + 340;
			// if (normWidth < (int) Math.ceil((double) 75//HP_MAX
			// / (double) 10) * 30 + 30) {
			// normWidth = (int) Math.ceil((double) 75//HP_MAX
			// / (double) 10) * 30 + 30;
			// }

			int normWidth = 300;
			g2d.fillRect(-10, 0, normWidth, 130);

			// for (int i = 1; i <= (int) Math.ceil((double) HP_MAX/ (double)
			// 10); i++) {
			// g2d.setColor((int) Math.ceil((double) health / (double) 10) >= i
			// ? Color.RED : Color.DARK_GRAY);
			// g2d.fillRect(i * 30, 70, 20, 20);
			// g2d.setColor(Color.WHITE);
			// g2d.drawRect(i * 30, 70, 20, 20);
			// }
			// g2d.setColor((int) Math.ceil((double) health / (double) 10) >= i
			// ? Color.RED : Color.DARK_GRAY);
			g2d.setColor(Color.GREEN);
			g2d.fillRect(normWidth - 50, 50 + (int) (Math.max(0, ((double) meleeTimer / (double) NEG_TIMER_MELEE)) * 20), 20,
					20 - (int) (Math.max(0, ((double) meleeTimer / (double) NEG_TIMER_MELEE)) * 20));
			g2d.setColor(Color.WHITE);
			g2d.drawRect(normWidth - 50, 50, 20, 20);
			g2d.setColor(Color.GREEN);
			g2d.fillRect(normWidth - 50, 75 + (int) (Math.max(0, ((double) rangedTimer / (double) NEG_TIMER_RANGED)) * 20), 20,
					20 - (int) (Math.max(0, ((double) rangedTimer / (double) NEG_TIMER_RANGED)) * 20));
			g2d.setColor(Color.WHITE);
			g2d.drawRect(normWidth - 50, 75, 20, 20);
			g2d.setColor(Color.GREEN);
			g2d.fillRect(normWidth - 50, 100 + (int) (Math.max(0, ((double) specialTimer / (double) NEG_TIMER_SPECIAL)) * 20), 20,
					20 - (int) (Math.max(0, ((double) specialTimer / (double) NEG_TIMER_SPECIAL)) * 20));
			g2d.setColor(Color.WHITE);
			g2d.drawRect(normWidth - 50, 100, 20, 20);
			g2d.setColor(Color.RED);
			g2d.setFont(HUD);
			// g2d.drawString("HEALTH:     |     MONEY: " + wallet.getMoney(),
			// 30, 50);
			g2d.drawString("MONEY: " + inventory.getMoney(), 10, 30);
			drawTHBar((double) health / (double) HP_MAX, normWidth - 75, g2d);
			drawTEnBar((double) energy / (double) MAX_ENERGY, normWidth - 75, g2d);

			drawTLBar(normWidth - 75, g2d);
			drawCSHUD(g2d);
			// drawEnBar((double) energy / (double) MAX_ENERGY, g2d);
		} else {
			drawBar2((double) health / (double) HP_MAX, (double) energy / (double) MAX_ENERGY, g2d);

		}
		if (poisonTimer > 0)
			g2d.drawImage(DigIt.lib.checkLibrary("/images/effects/poison.gif"), x, y, owner);
		if (owner.getState() == State.INGAME) {
			timersCount();
		}

		// if (player) {
		// willTalk = true;
		// g2d.setColor(Color.magenta);
		// g2d.draw(getTalkBounds());
		// }
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

		if (toMoveString() != null) {
			g2d.drawImage(newImage(toMoveString()), dX, dY, owner);
			if (owner.darkenWorld())
				g2d.drawImage(newShadow(toMoveString()), dX, dY, owner);
		}

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

		meleeHit = true;
		specialHit = true;
	}

	public Image newImage(String name) {
		if (!name.contains("/")) {
			if (isCharacterSkin(name)) {
				shadow = newShadow(name);
			}
			return super.newImage(getPath() + name + ".png");
		} else {
			return super.newImage(name);
		}
	}

	public Image newShadow(String name) {
		return super.newShadow(getPath() + name + ".png");
	}

	protected static String[] playerSkins = new String[] { "n", "w0", "w1", "w2", "w3", "g" };

	public boolean isCharacterSkin(String name) {
		for (String s : playerSkins)
			if (s.equals(name))
				return true;
		return false;
	}

	protected String getPath() {

		String dir;

		if (direction != null) {
			Direction direction = this.direction;
			// if (direction.isDiag())
			// direction = diagBackup;

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
		} else
			dir = "side";
		return "images/characters/" + (charName != null ? charName : "spade") + "/" + dir + "/";
	}

	public Rectangle getCollisionBounds() {

		if (!player)
			return new Rectangle(x + CLIP, y + CLIP, width - 2 * CLIP, height - CLIP);
		else
			return new Rectangle(x + CLIP - deltaX, y + CLIP - deltaY, width - 2 * CLIP, height - CLIP);
	}

	public Rectangle getStationaryCollisionBounds() {

		return new Rectangle(x + CLIP, y + CLIP, width - 2 * CLIP, height - CLIP);
	}

	// public Rectangle getLargerBounds() {
	// return new Rectangle(x + CLIP - Math.abs(deltaX), y + CLIP -
	// Math.abs(deltaY), width - 2 * CLIP + Math.abs(deltaX * 2), height - CLIP
	// + Math.abs(deltaY * 2));
	// // return new Rectangle(x + CLIP - SPEED, y + CLIP - SPEED, width - 2 *
	// CLIP + SPEED, height - CLIP
	// // + SPEED);
	// }

	public void takeDamage(int amount, boolean poison) {
		if (poison)
			poison();
		if (amount > 0)
			if (hitstunTimer <= 0) {
				health -= amount;
				hpTimer = 100;
				hitstunTimer = HITSTUN_MAX;
			}
		if (health <= 0) {

			if (owner.getAliveFriends().size() == 0)
				owner.setState(Board.State.DEAD);
			else {
				x = owner.getSpawnLoc().x;
				y = owner.getSpawnLoc().y;
				health = (float) 0.01;
				dead = true;
				image = newImage("n");
				if (player)
					owner.setSwitching(true);
			}
		}

	}

	public abstract boolean canAct();

	public abstract void getsActor();

	public Types getType() {

		return type;
	}

	public void stop() {
if(!player){
			x-=deltaX;
			y-=deltaY;
		}
		moveX = false;
		moveY = false;
		deltaX = 0;
		deltaY = 0;
		
	}

	protected void timersCount() {
		if (getMove() == Moves.SHIELD && meleePress == false) {
			meleeTimer = 0;
		}
		if (poisonTimer > 0)
			poisonTimer--;
		if (enTimer == 0) {
			energy += 1;
			enTimer = 5;
		} else if (specialTimer <= 0)
			enTimer--;
		if (energy > MAX_ENERGY) {
			energy = MAX_ENERGY;
		}
		if (meleeTimer > NEG_TIMER_MELEE && (type != Types.DIAMOND || ((type == Types.DIAMOND) && meleeTimer <= 0))) {
			meleeTimer -= 2;
		}
		if (rangedTimer > NEG_TIMER_RANGED) {
			if ((!(type == Types.DIAMOND)) || rangedTimer <= 0)
				if (getType() == Types.SPADE) {
					if (rangedTimer == 30 - (TIMER_RANGED % 2)) {
						if (((Spade) this).keyReleased) {
							owner.getfP().add(
									new FProjectile(dir, x + rangedAddX(), y + rangedAddY(), 25, this, "images/characters/projectiles" + "/"
											+ getRangedString(), owner, getRangedMove()));
							owner.getfP().get(owner.getfP().size() - 1).setTurning(true);
							rangedTimer -= 2;
						}
					} else
						rangedTimer -= 2;
					if (rangedTimer >= 0)
						enTimer = 5;
				} else
					rangedTimer -= 2;
		}
		if (specialTimer > NEG_TIMER_SPECIAL) {
			specialTimer -= 2;
		}
		if (itemTimer > 0)
			itemTimer--;

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
		poisonTimer = 0;
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

		return "" + getType() + "," + HP_MAX + "," + MAX_ENERGY + "," + myLevel + "," + meleeDamage + "," + rangedDamage + "," + specialDamage;
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

			float health = Float.parseFloat(stuff.get(0));
			float energy = Float.parseFloat(stuff.get(1));
			int myLevel = Integer.parseInt(stuff.get(2));
			float melee = Float.parseFloat(stuff.get(3));
			float ranged = Float.parseFloat(stuff.get(4));
			float special = Float.parseFloat(stuff.get(5));
			this.myLevel = myLevel;
			HP_MAX = health;
			MAX_ENERGY = energy;
			this.health = HP_MAX;
			this.energy = MAX_ENERGY;
			this.meleeDamage = melee;
			this.rangedDamage = ranged;
			this.specialDamage = special;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Inventory getInventory() {
		return inventory;
	}

	public static void setInventory(Inventory w) {
		inventory = w;
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
		if(nameDraw!=null)
		g2d.drawString(nameDraw, x, y-25);

	}

	public void drawTHBar(double per, int total, Graphics2D g2d) {
		total -= 10;
		g2d.setColor(Color.BLACK);
		g2d.fillRect(10, 52, total, 16);
		g2d.setColor(Color.RED);
		g2d.fillRect(10, 52, (int) ((double) total * (double) per), 16);
		g2d.setColor(Color.WHITE);
		g2d.drawRect(10 - 1, 52 - 1, total + 1, 17);

	}

	public void drawTEnBar(double per, int total, Graphics2D g2d) {
		total -= 10;
		g2d.setColor(Color.BLACK);
		g2d.fillRect(10, 80, total, 10);
		g2d.setColor(Color.BLUE);
		g2d.fillRect(10, 80, (int) ((double) total * (double) per), 10);
		g2d.setColor(Color.WHITE);
		g2d.drawRect(10 - 1, 80 - 1, total + 1, 11);

	}

	public void drawTLBar(int total, Graphics2D g2d) {
		total -= 10;
		double per = (double) xp / (double) (Math.pow(level + 1, 2) * 10);
		g2d.setColor(Color.BLACK);
		g2d.fillRect(10, 105, total, 10);
		g2d.setColor(Color.YELLOW);
		g2d.fillRect(10, 105, (int) ((double) total * (double) per), 10);
		g2d.setColor(Color.WHITE);
		g2d.drawRect(10 - 1, 105 - 1, total + 1, 11);

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

	public void setEnergy(int setter) {
		energy = setter;
		if (energy > MAX_ENERGY)
			energy = MAX_ENERGY;
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
						meleeDamage += BmeleeDamage / 10 * meleeDamage_M;
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
						rangedDamage += BrangedDamage / 10 * rangedDamage_M;
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
						specialDamage += BspecialDamage / 10 * specialDamage_M;
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
						HP_MAX += BHP_MAX / 10 * HP_MAX_M;
						myLevel++;
						mHealth.setText("Health: " + HP_MAX);
						levelLabel.setText("Skill Points: " + (level - myLevel) + "  |  " + "Level: " + level + "  |  " + "XP: " + xp + "  |  "
								+ "XP needed: " + (int) Math.pow(level + 1, 2) * 10);
					}
				}
			});
			panel.add(mHealth);
			mEn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					if (myLevel < level) {
						MAX_ENERGY += BMAX_ENERGY / 10 * MAX_ENERGY_M;
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
		return (int) meleeDamage;
	}

	public int getRangedDamage() {
		return (int) rangedDamage;
	}

	public int getSpecialDamage() {
		return (int) specialDamage;
	}

	public int getStrength() {
		return strength;
	}

	public int getCurrentDir() {
		int scrollX = owner.getScrollX();
		int scrollY = owner.getScrollY();

		int dir;
		if (scrollX != 0 || scrollY != 0) {
			dir = 0;
			boolean changed = false;
			if (scrollX < 0) {
				dir = 0;
				changed = true;
			} else if (scrollX > 0) {
				dir = 180;
				changed = true;
			}
			if (scrollY < 0) {
				if (changed) {

					if (dir == 180) {
						dir -= 45;
					} else {
						dir += 45;
					}
				} else {
					dir = 90;
				}
			} else if (scrollY > 0) {
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
		} else {
			dir = GameCharacter.Direction.getDir(owner.getCharacter().getDirection());
		}
		return dir;
	}

	public void releaseAll() {
		meleePress = false;
		rangedPress = false;
		specialPress = false;
		if (this instanceof Spade)
			((Spade) this).keyReleased = true;
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean setter) {
		dead = setter;
	}
	public void setDirection(Direction setter){
		direction=setter;
	}
	public void setMpName(String setter){
		nameDraw=setter;
	}
	public String getMpName(){
		return nameDraw;
	}
}

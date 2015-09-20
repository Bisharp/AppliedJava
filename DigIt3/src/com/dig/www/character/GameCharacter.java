package com.dig.www.character;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Window.Type;
import java.awt.event.KeyEvent;

import com.dig.www.start.Board;
import com.dig.www.util.Sprite;
import com.dig.www.util.Statics;

public abstract class GameCharacter extends Sprite {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
int dir=0;
boolean meleePress=false;
boolean rangedPress=false;
boolean specialPress=false;
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
		}, HEART {
			public String toString() {
				return "heart";
			}

			@Override
			public String charName() {
				// TODO Auto-generated method stub
				return "Destiny";
			}
		}, SPADE {
			public String toString() {
				return "shovel";
			}

			@Override
			public String charName() {
				// TODO Auto-generated method stub
				return "Clark";
			}
		}, DIAMOND {
			public String toString() {
				return "diamond";
			}

			@Override
			public String charName() {
				// TODO Auto-generated method stub
				return "Cain";
			}
		},PROJECTILE {
			public String toString() {
				return "projectile";
			}

			@Override
			public String charName() {
				// TODO Auto-generated method stub
				return null;
			}
		} ;
		
		public abstract String charName();

		
	}

	private int deltaX = 0;
	private int deltaY = 0;
	protected Direction direction = Direction.RIGHT;
	protected Types type;

	private boolean moveX = false;
	private boolean moveY = false;

	private boolean wallBound = false;
//	private int wallX = 0;
//	private int wallY = 0;

//	protected boolean acting = false;
//	protected int actTimer = 0;
protected transient int meleeTimer;
protected transient int rangedTimer;
protected transient int specialTimer;
protected transient int energy=100;
	private final int SPEED = 10;

	private int counter = 0;
	private int animationTimer = 7;

	private static final int ANIMAX = 7;
	private static final int MAX = 4;
	private String charName = "reyzu";

	private static final int HP_MAX =50;
	private static final int HP_TIMER_MAX = 50;
	private static final int HITSTUN_MAX = 10;
	protected  int NEG_TIMER_NORM = -20;
protected    int TIMER_NORM = 10;
	private int health = HP_MAX;
	private int hpTimer = 0;
	private int hitstunTimer = 0;

	public GameCharacter(int x, int y, Board owner, Types type, String charName) {
		super(x, y, "n", owner);
		
		this.charName = charName;
		this.type = type;
	}

	@Override
	public void animate() {
		// TODO Auto-generated method stub

		if (hitstunTimer > 0) {
			hitstunTimer--;
			flicker();

			if (hitstunTimer == 0) {
				visible = true;
			}
		}

		if (hpTimer > 0) {
			hpTimer--;

			if (hpTimer <= 0 && health < HP_MAX) {
				health+=3;
				if(health>HP_MAX){
					health=HP_MAX;
				}
				hpTimer = HP_TIMER_MAX;
			}
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

			owner.setScrollX(deltaX);
			owner.setScrollY(deltaY);
		} else {

//			int tempX = 0;
//			int tempY = 0;
//
//			if (wallX - 10 > getMidX())
//				tempX = SPEED;
//			else if (wallX + 10 < getMidX())
//				tempX = -SPEED;
//			else
//				tempX = 0;
//
//			if (wallY - 10 > getMidY())
//				tempY = SPEED;
//			else if (wallY + 10 < getMidY())
//				tempY = -SPEED;
//			else
//				tempY = 0;

			owner.setScrollX(-owner.getScrollX());
			owner.setScrollY(-owner.getScrollY());

			owner.reAnimate();

			owner.setScrollX(0);
			owner.setScrollY(0);

			wallBound = false;
		}
	}

	public void keyPressed(int keyCode) {

		switch (keyCode) {
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_A:

			deltaX = SPEED;
			direction = Direction.LEFT;
			moveX = true;

			break;
		case KeyEvent.VK_RIGHT:
		case KeyEvent.VK_D:

			deltaX = -SPEED;
			direction = Direction.RIGHT;
			moveX = true;

			break;

		case KeyEvent.VK_UP:
		case KeyEvent.VK_W:

			deltaY = SPEED;
			direction = Direction.UP;
			moveY = true;

			break;
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_S:

			deltaY = -SPEED;
			direction = Direction.DOWN;
			moveY = true;

			break;

		case KeyEvent.VK_SPACE://Melee
			meleePress=true;
			
			break;  
		case KeyEvent.VK_E://Ranged
		case KeyEvent.VK_C:
			rangedPress=true;
			
			break;
		case KeyEvent.VK_Q://Special
		case KeyEvent.VK_V:
			specialPress=true;
			
			break;
		}
	}

	public abstract Moves getRangedMove();
public Moves getSpecialProMove(){
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
		case KeyEvent.VK_SPACE://Melee
			meleePress=false;
			if(meleeTimer>0)
			meleeTimer=0; 
			break;
		case KeyEvent.VK_E://Ranged
		case KeyEvent.VK_C:
			rangedPress=false;
			if(rangedTimer>0)
			rangedTimer=0; 
			break;
		case KeyEvent.VK_Q://Special
		case KeyEvent.VK_V:
			specialPress=false;
			if(specialTimer>0)
			specialTimer=0; 
			break;
		}
		}
	

	public void collision(int midX, int midY) {
		// TODO Auto-generated method stub
		wallBound = true;
//		wallX = midX;
//		wallY = midY;
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

	private Font HUD = new Font("Broadway", Font.BOLD, 30);
private Point setAttacks(){
	Point shieldPos=null;
	if(meleePress){
		System.out.println(meleeTimer);
		if(meleeTimer<=NEG_TIMER_NORM//||this instanceof Diamond
				){
			meleeTimer=TIMER_NORM*(this instanceof Club?2:1);}
	}
	
	if(specialPress&&!meleePress&&!rangedPress){
		if(specialTimer<=NEG_TIMER_NORM*(this instanceof Diamond?2:1)){
			specialTimer=TIMER_NORM;	
			if(this instanceof Club){
				
			}
		}
	}if(rangedPress&&!meleePress){
		if(rangedTimer<=NEG_TIMER_NORM){
			rangedTimer=TIMER_NORM;	
String s="images/enemies/blasts/0.png";
if(type==Types.DIAMOND)
	s=getPath()+"diamond.png";
			owner.getfP().add(new FProjectile(dir, x, y, 25, this,s, owner,getRangedMove()));
		
		}
	}
	
	if(type==Types.DIAMOND){
		boolean found=false;
		for(int c=0;c<owner.getfP().size();c++){
			
			if(owner.getfP().get(c).getMove()==Moves.CHAIN){
				
			specialTimer=0;
			meleeTimer=0;
			rangedTimer=0;
			found=true;
			FProjectile fp=owner.getfP().get(c);
			shieldPos=new Point(fp.getX(),fp.getY());
			break;}
		}
		if(!found){
			
			if(rangedTimer>0)
			rangedTimer=0;}
		}
	return shieldPos;
}
	@Override
	public void draw(Graphics2D g2d) {
		// TODO Auto-generated method stub
		if(deltaX!=0||deltaY!=0){
		dir=0;
			boolean changed=false;
			if(deltaX<0){
				dir=0;
				changed=true;
			}else if(deltaX>0){
				dir=180;
				changed=true;
			}
			if(deltaY<0){
				if(changed){
				
					if(dir==180){
						dir-=45;
					}else{
						dir+=45;
					}
				}else{
					dir=90;
				}
			} else if(deltaY>0){
				if(changed){
					if(dir==180){
						dir+=45;
					}else{
						dir-=45;
					}
				}else{
					dir=270;
				}
			}}
		Point p=setAttacks();
		if (visible)
			if (direction != Direction.LEFT){
				g2d.drawImage(image, x, y, owner);
				if(p!=null){
				g2d.setColor(Color.black);
				g2d.drawLine(x, y+height, (int)p.getX(), (int)p.getY());}}
			else{
				g2d.drawImage(image, x + width, y, -width, height, owner);
				if(p!=null){
					g2d.setColor(Color.black);
					g2d.drawLine(x+ width, y, (int)p.getX(), (int)p.getY());}
			}
		
			drawTool(g2d);
		
		
		g2d.setColor(Color.BLACK);
		int normWidth=(int)Math.ceil((double)HP_MAX/(double)10)*30+30;
		g2d.fillRect(10, 20, (normWidth>170?normWidth:170), 80);

		for (int i = 1; i <= (int)Math.ceil((double)HP_MAX/(double)10); i++) {
			g2d.setColor((int)Math.ceil((double)health/(double)10) >= i ? Color.RED : Color.DARK_GRAY);
			g2d.fillRect(i * 30, 70, 20, 20);
			g2d.setColor(Color.WHITE);
			g2d.drawRect(i * 30, 70, 20, 20);
		}

		g2d.setColor(Color.RED);
		g2d.setFont(HUD);
		g2d.drawString("HEALTH:", 30, 50);
		
		drawCSHUD(g2d);
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
if(toMoveString()!=null){
		g2d.drawImage(newImage(toMoveString()), dX, dY, owner);
}
		if (direction == Direction.UP)
			g2d.drawImage(image, x, y, owner);
timersCount();
		
	}
	
	protected abstract void drawCSHUD(Graphics2D g2d) ;
	
	// Works only for integers
	protected int numOfDigits(int num) {
		
		int x = 10;
		int i = 0;
		
//		if (num < 0)
//			num = -num;
		
		while (x <= num) {
			
			i++;
			x = x * 10;
		}
		
		return i;
	}

//	public boolean isActing() {
//		return acting;
//	}

	public void endAction() {
		// TODO Auto-generated method stub
		if(meleeTimer>0)
			meleeTimer=0;
		if(rangedTimer>0)
			rangedTimer=0;
		if(specialTimer>0)
			specialTimer=0;
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
		// TODO Auto-generated method stub
		return new Rectangle(x + 40, y + 40, width - 80, height - 40);
	}

	public void takeDamage(int amount) {

		if (hitstunTimer <= 0) {
			health-=amount;
			hpTimer = 100;
			hitstunTimer = HITSTUN_MAX;

			if (health <= 0)
				owner.setState(Board.State.DEAD);
		}
	}
	
	public abstract boolean canAct() ;
	
	public abstract void getsActor() ;

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
	protected void timersCount(){
		if(meleeTimer>NEG_TIMER_NORM&&(type!=Types.DIAMOND||((type==Types.DIAMOND)&&meleeTimer<=0))){
			meleeTimer--;
		}
		if(rangedTimer>NEG_TIMER_NORM){
			if((!(type==Types.DIAMOND))||rangedTimer<=0)
			rangedTimer--;
		}
		if(specialTimer>NEG_TIMER_NORM*(this instanceof Diamond?2:1)){
			specialTimer--;
		}
	}

	public int getActing() {
		// TODO Auto-generated method stub
		if(meleeTimer>0)
			return 1; 
		else if(rangedTimer>0)
			return 2;
		else if(specialTimer>0)
			return 3;
		 
		else
			return 0;
	}

	public abstract Moves getMove();
	public abstract String toMoveString();
}
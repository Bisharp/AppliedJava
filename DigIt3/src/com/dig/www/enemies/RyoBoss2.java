package com.dig.www.enemies;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;

import com.dig.www.blocks.Block;
import com.dig.www.objects.BossBlock;
import com.dig.www.start.Board;
import com.dig.www.util.Statics;

public class RyoBoss2 extends Boss{
private boolean gone;
private Kyseryx sword;
private int imNum;
private int imTimer;
private boolean moveActed;
private static final int IM_TIMER_MAX=6;
private boolean notOnce;
	public RyoBoss2(int x, int y, Board owner) {
		super(x, y, "images/characters/ryo/front/n.png", owner, true, 1000, "Ryo", 7,"music/zeldaCopyright.mp3","gunSFX/explosion-2.wav",
				"gunSFX/explosion-2.wav");
		damage=0;
		sequence=2;
		
		//loc="images/characters/ryo/";
	}
	public void  makeDeadExplosion(){
		super.makeDeadExplosion();
		owner.getEnemies().add(new Explosion(x, y, owner));
		
	}
	@Override
	public void animate() {
		if(!gone){
			gone=true;
			sword=new Kyseryx(x, y, owner,this,speed);
			owner.getEnemies().add(sword);
			actTimer=500;
		}
		sword.setSpeed((speed*speedMulti)+(sequence<2?1:0));
		// TODO Auto-generated method stub
	if(health<(maxHealth/3)&&phase<2){
		phase=2;
		Statics.playSound(owner,bossPhaseS);
		owner.getEnemies().add(new Explosion(x, y, owner));
	}if(health<(maxHealth/3)*2&&phase<1){
			phase=1;
			Statics.playSound(owner,bossPhaseS);
			owner.getEnemies().add(new Explosion(x, y, owner));
				
	}
	
		basicAnimate();
		boolean acted=sortAction();
		if(!acted&&active){
			if(!getBounds().intersects(getOwnerPlusBounds())){
				//realSeq=sequence;
				follow(5, 0, 1.5);
			}
			if(actTimer<=0){
				if(sequence==0){
					chargeAttack(150, 40, 1.5);
					sequence++;
				}else if(sequence==1){
					if(phase>0)
						createProjectile(10,
								Statics.pointTowards(new Point((int) x,
								(int) y), new Point(owner.getCharacterX()+40,owner.getCharacterY()+40)),true,40,width/2,height/4,10);
					sequence++;
				}
			else if(sequence==2){
				follow(200, 40,1);
					sequence++;}
					
				else if(sequence==3){
				
					createProjectile(10,
							Statics.pointTowards(new Point((int) x,
							(int) y), new Point(owner.getCharacterX()+40,owner.getCharacterY()+40)),true,40,width/2,height/4,30);
					sequence++;
					}
				else if(sequence==4&&phase==2){
					createProjectile(10,
							Statics.pointTowards(new Point((int) x,
							(int) y), new Point(owner.getCharacterX()+40,owner.getCharacterY()+40)),true,40,width/2,height/4,10);
					sequence++;
				}
				else
					sequence=0;
					
				}
					
				
			}
				
			
		//image=newImage(getPath());
		if(!acted&&actTimer>0)
			actTimer--;
if(!moveActed){
	if(notOnce){
			notOnce=false;
			image=newImage("n");}
		}else{
			//System.out.println(imTimer+","+imNum);
	if(imTimer<=0){
		notOnce=true;
		image=newImage("w"+imNum);
		
		imNum++;
		if(imNum>3){
			imNum=0;
		}
		imTimer=IM_TIMER_MAX;
	}else{
		imTimer-=owner.mult();
	}}
	}
	@Override
	public int getKillXP() {
		// TODO Auto-generated method stub
		return 0;
	}
	public Rectangle getOwnerPlusBounds(){
		return new Rectangle(-100,-100,owner.getWidth()+200,owner.getHeight()+220);	
		}
	@Override
	public void removeSpecWalls() {
		// TODO Auto-generated method stub
		super.removeSpecWalls();
		for (int c = 0; c < owner.getEnemies().size(); c++) {
			if (owner.getEnemies().get(c) instanceof PoisonOrFlame) {
				((PoisonOrFlame) owner.getEnemies().get(c)).setInvincible(false);
				owner.getEnemies().get(c).setHealth(1);
				owner.getEnemies().get(c).takeDamage(10, null);
			//	c--;
			}
		}
	}
	public void createProjectile(int speed, double dir, boolean flying, int timer, int addX, int addY, int damage) {
		owner.getEnemies().add(new RyoBoss2Projectile(dir, x + addX, y + addY, speed, this, owner, damage));
		actTimer = timer;

	}
	public boolean sortAction() {
		moveActed=false;
		if (attackNum == 0) {
			moveActed=true;
			moveDForward();
			for (Block b : owner.getWorld()) {
				if (b.getBounds().intersects(getBounds())) {
					if(!b.traversable()){
					followTimer = 0;
					attackNum = -1;
					dir += 180;

					// while(b.getBounds().intersects(getBounds()))
					// moveDForward();

					moveDForward();
					speedMulti = 1;
					break;}
				}

			}
			followTimer-=owner.mult();
			if (followTimer == 0) {
				attackNum = -1;
				speedMulti = 1;
			}
			return true;
		} else if (attackNum == 1) {
			moveActed=true;
			pointAndMove();
			followTimer-=owner.mult();
			if (followTimer == 0) {
				attackNum = -1;
			}
			return true;
		}
		return false;
	}
public String getPath(){
	String dire="side";
	boolean xFir=Math.abs(Math.sin(Math.toRadians(dir)))<0.67;
	if(!xFir){
		if(dir<180)
			dire="front";
		else
			dire="back";
	}
		return "images/characters/ryo/"+dire+"/";
	}
@Override
public void myDraw(Graphics2D g2d) {
	if(burnTimer>0)
g2d.drawString(""+burnTimer,10,400);
	if (stunTimer > 0) {
		int x = this.x + (Statics.RAND.nextInt(5) * (Statics.RAND.nextBoolean() ? 1 : -1));
		int y = this.y + (Statics.RAND.nextInt(5) * (Statics.RAND.nextBoolean() ? 1 : -1));
		if(dir>270||90>dir)
		g2d.drawImage(hitstunRenders() ? image : null, x, y, owner);
		else
			g2d.drawImage(hitstunRenders() ? image : null, x+100, y,-100,100, owner);
		//drawShadow(g2d, x, y);
	} else {
		if(hitstunRenders()){
			if(dir>270||90>dir)
		g2d.drawImage(image, x, y, owner);
			else
				g2d.drawImage(image, x+100, y,-100,100, owner);
		//drawShadow(g2d);}
	}
	drawStatus(g2d);
}
	drawBar((double) health / (double) maxHealth, g2d);
	}
public Image newImage(String name) {
	if(name.contains("/"))
	return super.newImage(name);	
	else{
		System.out.println(getPath() + name + ".png");
		shadow=newShadow(getPath() + name + ".png");
	return super.newImage(getPath() + name + ".png");}
}
}

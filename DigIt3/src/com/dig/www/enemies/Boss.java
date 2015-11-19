package com.dig.www.enemies;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import com.dig.www.blocks.Block;
import com.dig.www.objects.BossBlock;
import com.dig.www.objects.Objects;
import com.dig.www.start.Board;
import com.dig.www.start.DigIt;
import com.dig.www.util.SoundPlayer;
import com.dig.www.util.Statics;

public abstract class Boss extends Enemy{
	protected String name;
	protected boolean vulnerable;
	protected int attackNum=-1;
protected int actTimer;
protected int speed;
protected boolean active;
protected boolean acting;
protected int followTimer;
protected int maxDis;
protected int dir;
protected int phase;
protected int sequence;
protected double speedMulti=1;
protected String musicLoc;
protected String bossKillS;
protected String bossPhaseS;

	public Boss(int x, int y, String loc, Board owner, boolean flying,
			int health,String name,int speed,String musicLoc,String bossKillS,String  bossPhaseS) {
		super(x, y, loc, owner, flying, health);
		// TODO Auto-generated constructor stub
	this.name=name;
	this.speed=speed;
	onScreen=false;
	this.musicLoc=musicLoc;
	this.bossKillS=bossKillS;
	this.bossPhaseS=bossPhaseS;
	}
public void createProjectile(String loc,int speed,double dir,boolean flying,int timer,int addX,int addY,int damage){
	owner.getEnemies().add(new Projectile(dir, x+addX, y+addY, speed, this, loc, owner, flying,damage));
actTimer=timer;

}
public void createProjectile(String loc,int speed,double dir,boolean flying,int timer,int addX,int addY,int damage,boolean poisons){
	owner.getEnemies().add(new Projectile(dir, x+addX, y+addY, speed, this, loc, owner, flying,damage,poisons));
actTimer=timer;

}
public void createTProjectile(String loc,int speed,double dir,boolean flying,int timer,int damage){
	owner.getEnemies().add(new HomingProjectile(dir, x, y, speed, this, loc, owner, flying,damage));
actTimer=timer;
}
public void chargeAttack(int max,int timer,double speedMulti){
	acting=true;
	dir = (int) Statics.pointTowards(new Point((int) x, (int) y), owner.getCharPoint());
this.speedMulti=speedMulti;
	this.followTimer=max;
	actTimer=timer;
	attackNum=0;
}
public void follow(int time,int timer,double speedMulti){
	acting=true;
	this.followTimer=time;
	actTimer=timer;
	attackNum=1;
	this.speedMulti=speedMulti;
}
public void moveDForward(){
	
	x += Math.cos((double) Math.toRadians((double) dir)) * speed*speedMulti;
	y += Math.sin((double) Math.toRadians((double) dir)) * speed*speedMulti;
}
public void pointAndMove(){
	dir = (int) Statics.pointTowards(new Point((int) x, (int) y), owner.getCharPoint());
	moveDForward();
}
public boolean sortAction(){
	if(attackNum==0){
		moveDForward();
		for(Block b: owner.getWorld()){
			if(!b.traversable()&&b.getBounds().intersects(getBounds())){
			followTimer=0;
			attackNum=-1;
			dir+=180;
			
//			while(b.getBounds().intersects(getBounds()))
//			moveDForward();
			
			moveDForward();
			speedMulti=1;
			break;
			}
				
		}
		followTimer--;
		if(followTimer==0){
			attackNum=-1;
			speedMulti=1;
		}
		return true;
	}else if(attackNum==1){
		pointAndMove();
		followTimer--;
		if(followTimer==0){
			attackNum=-1;
		}
		return true;
	}
	return false;
}
@Override
public void draw(Graphics2D g2d){
	if(isOnScreen())
		active=true;
	
		
	if(active){
	if(!alive&&DigIt.soundPlayer.playerThread!=null){
		makeDeadExplosion();
		removeSpecWalls();
	}else
		if(DigIt.soundPlayer.playerThread==null||!DigIt.soundPlayer.isPlaying()){
			Statics.playSound(owner, musicLoc);
		}
	}
	if(isOnScreen()){
		myDraw(g2d);
	}else if(active){
		drawBar((double) health / (double) maxHealth, g2d);
	}
}
@Override
public void drawBar(double per,Graphics2D g2d){
	g2d.setColor(Color.BLACK);
	g2d.fillRect(Statics.BOARD_WIDTH/4, 72, Statics.BOARD_WIDTH/2, 30);
	g2d.setColor(Color.RED);
	g2d.fillRect(Statics.BOARD_WIDTH/4, 72, (int) ((double)(per)*(double)(Statics.BOARD_WIDTH/2)), 30);
	g2d.setColor(Color.WHITE);
	g2d.drawRect(Statics.BOARD_WIDTH/4-1, 72-1, Statics.BOARD_WIDTH/2+2, 32);
g2d.setFont(Statics.BOSS);
g2d.drawString(name,(Statics.BOARD_WIDTH/2)-(name.length()*20/2)
		, 72+30);
}
@Override
public abstract int getKillXP();

@Override
public void turnAround(int wallX, int wallY){
	//if(!getBounds().intersects(owner.getCharacter().getBounds())){
	//followTimer=0;
//	int myX = round(x, 2);
//	int myY = round(y, 2);
//	wallX = round(wallX, 2);
//	wallY = round(wallY, 2);
//
//	if (wallX > myX)
//		x -= BLOCK;
//	else if (wallX < myX)
//		x += BLOCK;
//
//	if (wallY > myY)
//		y -= BLOCK;
//	else if (wallY < myY)
//		y += BLOCK;
	}
//}
public void makeDeadExplosion(){
	DigIt.soundPlayer.playerThread.stop();
	Statics.playSound(owner,bossKillS);
}
public void myDraw(Graphics2D g2d){
	super.draw(g2d);
}
public void removeSpecWalls(){
	for(int c=0;c<owner.getObjects().size();c++){
		if(owner.getObjects().get(c) instanceof BossBlock){
			((BossBlock) owner.getObjects().get(c)).remove();
			c--;
		}
	}
}
}

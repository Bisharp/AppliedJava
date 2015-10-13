package com.dig.www.enemies;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import com.dig.www.start.Board;
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
	public Boss(int x, int y, String loc, Board owner, boolean flying,
			int health,String name,int speed) {
		super(x, y, loc, owner, flying, health);
		// TODO Auto-generated constructor stub
	this.name=name;
	this.speed=speed;
	onScreen=false;
	}
public void createProjectile(String loc,int speed,double dir,boolean flying,int timer){
	owner.getEnemies().add(new Projectile(dir, x, y, speed, this, loc, owner, flying));
actTimer=timer;

}
public void createTProjectile(String loc,int speed,double dir,boolean flying,int timer){
	owner.getEnemies().add(new HomingProjectile(dir, x, y, speed, this, loc, owner, flying));
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
public void follow(int time,int timer){
	acting=true;
	this.followTimer=time;
	actTimer=timer;
	attackNum=1;
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
	if(isOnScreen()){
		super.draw(g2d);
	}else if(active){
		drawBar((double) health / (double) maxHealth, g2d);
	}
}
@Override
public void drawBar(double per,Graphics2D g2d){
	g2d.setColor(Color.BLACK);
	g2d.fillRect(Statics.BOARD_WIDTH/4, 150, Statics.BOARD_WIDTH/2, 30);
	g2d.setColor(Color.RED);
	g2d.fillRect(Statics.BOARD_WIDTH/4, 150, (int) ((double)(per)*(double)(Statics.BOARD_WIDTH/2)), 30);
	g2d.setColor(Color.WHITE);
	g2d.drawRect(Statics.BOARD_WIDTH/4-1, 150-1, Statics.BOARD_WIDTH/2+2, 32);
g2d.setFont(Statics.BOSS);
g2d.drawString(name,(Statics.BOARD_WIDTH/2)-(name.length()*20/2)
		, 150+30);
}
@Override
public abstract int getKillXP();
}

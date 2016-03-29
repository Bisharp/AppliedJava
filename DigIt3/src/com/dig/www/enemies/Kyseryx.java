package com.dig.www.enemies;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;

import com.dig.www.character.GameCharacter;
import com.dig.www.start.Board;
import com.dig.www.util.Irregular;
import com.dig.www.util.Statics;

public class Kyseryx extends TailEnemy{
private double speed;
private double dir;
	public Kyseryx(int x, int y,Board owner,RyoBoss2 myOwner,int speed) {
		super(x, y, "images/characters/ryo/side/kSpecial.gif", owner, true, -10, 0, myOwner);
		// TODO Auto-generated constructor stub
		this.speed=speed;
		this.damage=2;
	}
	@Override
		public boolean poisons() {
			// TODO Auto-generated method stub
			return true;
		}
	@Override
		public boolean isPoison() {
			// TODO Auto-generated method stub
			return false;
		}
	@Override
	public void draw(Graphics2D g2d) {
		//boolean shooting=((RyoBoss2)myFollows).sequence>0&&((RyoBoss2)myFollows).sequence<4;
		g2d.rotate(dir,x+102,y+40);
		g2d.drawImage(image, x, y, owner);
		g2d.rotate(-dir,x+102,y+40);
	}
	@Override
	public Image newShadow() {
		// TODO Auto-generated method stub
		return image;
	}
	@Override
	public int getSpeed() {
		// TODO Auto-generated method stub
		return (int)speed;
	}public void animate() {
		if (!myFollows.isAlive()) {
			alive = false;
		}

		basicAnimate();
		int dist = (int) Math.pow(myFollows.getX()+50 - (x+102), 2) + (int) Math.pow(myFollows.getY()+50 - (y+40), 2);
		if (Math.sqrt(dist) > FOLLOW) {
			int amount = getSpeed()*owner.mult();
dir=Math.toRadians(pointTowards(new Point(myFollows.getX()+50,myFollows.getY()+50)));
x-=Math.cos(dir)*amount*owner.mult();
y-=Math.sin(dir)*amount*owner.mult();
		}
	}
	protected double pointTowards(Point a) {
		double d;
		// Point at something, This will be useful for enemies, also in
		// ImportantLook class
		Point b = new Point(x+102, y+40);
		d = (double) (Math.toDegrees(Math.atan2(a.getY() + -b.getY(), a.getX() + -b.getX())) + 180);
		return d;
	}
	public void setSpeed(double setter){
		speed=setter;
	}
	@Override
		public boolean takeDamage(int amount, GameCharacter chara) {
			// TODO Auto-generated method stub
			return myFollows.takeDamage(amount, chara);
		}
}

package com.dig.www.games.Climb;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;

import com.dig.www.util.Statics;

public class Object {

	protected int x, y, width, height;
	protected int health;
	protected boolean alive, hostile;
	protected Image image;
	protected Climb owner;
	
	public Object(int x, int y, int health, String loc, Climb owner) {
		
		this.x = x;
		this.y = y;
		this.health = health;
		
		image = Statics.newImage(loc);
		width = image.getWidth(null);
		height = image.getHeight(null);
		
		this.owner = owner;
	}
	
	protected Shape getBounds() {
		return new Rectangle(x, y, width, height);
	}
	
	protected void animate(int sX, int sY) {
		x += sX;
		y += sY;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}
	
	public int getDX() {
		return Math.round(x / 10) * 10;
	}
	public int getDY() {
		return Math.round(y / 10) * 10;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public boolean isHostile() {
		return hostile;
	}

	public void setHostile(boolean hostile) {
		this.hostile = hostile;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
}

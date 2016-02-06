package com.dig.www.games.Climb;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;

import com.dig.www.util.Statics;

public class Object {
	
	protected int x, y, width, height;
	protected boolean alive, hostile;
	protected Image image;
	protected Climb owner;
	protected boolean onScreen = false;
	
	public Object(int x, int y, String loc, Climb owner) {
		
		this.x = x;
		this.y = y;
		
		image = Statics.newImage(loc);
		width = image.getWidth(null);
		height = image.getHeight(null);
		
		this.owner = owner;
	}
	
	public Object(int x, int y, int width, int height, Climb owner) {
		
		this.x = x;
		this.y = y;
		
		image = null;
		this.width = width;
		this.height = height;
		
		this.owner = owner;
	}
	
	protected Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}
	
	protected void animate() {
		
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
	
	protected int getWidth() {
		return width;
	}
	protected int getHeight() {
		return height;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public void scrollY(int sY) {
		y += sY;
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
	
	public boolean isOnScreen() {
		return onScreen;
	}
	
	public void setOnScreen(boolean oS) {
		onScreen = oS;
	}
}

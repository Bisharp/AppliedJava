package com.dig.www.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.Serializable;

import javax.swing.ImageIcon;

import com.dig.www.start.Board;
import com.dig.www.start.DigIt;
import com.dig.www.character.GameCharacter;
import com.dig.www.enemies.Boss;
import com.dig.www.npc.NPC;

public abstract class Sprite implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7867261812992933976L;

	protected String loc;

	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected boolean visible;
	protected boolean onScreen = true;
	protected boolean illuminated = false;
	protected transient Image image;
	protected transient Image shadow;

	protected transient Board owner;

	public Sprite(int x, int y, String loc, Board owner) {
		if(loc!=null){
		image = newImage(loc);
		if (!(this instanceof GameCharacter) && !(this instanceof Boss))
			shadow = newShadow(loc);

		width = image.getWidth(null);
		height = image.getHeight(null);}else{
			image=new ImageIcon().getImage();
			shadow=new ImageIcon().getImage();
			width=100;
			height=100;//by default null images have bounds of 100x100
		}
		visible = true;

		this.owner = owner;

		this.loc = loc;
		this.x = x;
		this.y = y;
	}

	public abstract void animate();

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int newX) {
		x = newX;
	}

	public void setY(int newY) {
		y = newY;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public boolean isIlluminated() {
		return illuminated;
	}

	public void setIlluminated(boolean illuminated) {
		this.illuminated = illuminated;
	}

	public Image getImage() {
		return image;
	}

	public Image newImage(String loc) {
		return DigIt.lib.checkLibrary("/" + loc);
	}

	public Image newShadow(String loc) {
		return DigIt.lib.checkShadowLibrary("/" + loc);
	}
	
	public Image getShadow() {
		return shadow;
	}
	
	public void drawShadow(Graphics2D g2d) {
		if (owner.darkenWorld() && !illuminated)
			g2d.drawImage(shadow, x, y, owner);
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}

	public int getWidth() {
		// TODO Auto-generated method stub
		return width;
	}

	public int getHeight() {
		// TODO Auto-generated method stub
		return height;
	}

	public void resetImage(Board b) {
		if(loc==null){
			image=new ImageIcon().getImage();
			shadow=new ImageIcon().getImage();
			width=100;
			height=100;//by default null images have bounds of 100x100
		}else{
		image = newImage(loc);
		if (!(this instanceof GameCharacter) && !(this instanceof Boss))
			shadow = newShadow(loc);

		width = image.getWidth(null);
		height = image.getHeight(null);}
		owner = b;
	}

	public int getMidX() {
		return x + width / 2;
	}

	public int getMidY() {
		return y + height / 2;
	}

	public void flicker() {
		if (visible)
			visible = false;
		else
			visible = true;
	}

	public abstract void draw(Graphics2D g2d);

	public void basicAnimate() {
		x += owner.getScrollX();
		y += owner.getScrollY();
		illuminated = false;
	}

	public void initialAnimate(int sX, int sY) {
		x += sX;
		y += sY;
	}

	public void drawBar(double per, Graphics2D g2d) {
		g2d.setColor(Color.BLACK);
		g2d.fillRect(x, y - 10, width, 10);
		g2d.setColor(Color.RED);
		g2d.fillRect(x, y - 10, (int) ((double) width * (double) per), 10);
		g2d.setColor(Color.WHITE);
		g2d.drawRect(x - 1, y - 11, width + 1, 11);
	}

	public boolean isOnScreen() {
		return onScreen;
	}

	public void setOnScreen(boolean onScreen) {
		this.onScreen = onScreen;
	}

	public Board getOwner() {
		return owner;
	}

	public void setImage(Image i) {
		image = i;
	}
	public void setShadow(Image i) {
		shadow = i;
	}
	public void removeOwner(){
		owner=null;
	}
	public void setOwner(Board b){
		owner=b;
	}

	public static void basicAnimate(NPC n) {
		// TODO Auto-generated method stub
		n.setX(n.getX()+n.getOwner().getScrollX());
		n.setY(n.getY()+n.getOwner().getScrollY());
	}
}

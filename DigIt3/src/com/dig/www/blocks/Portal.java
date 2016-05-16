package com.dig.www.blocks;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.dig.www.character.GameCharacter;
import com.dig.www.start.Board;
import com.dig.www.util.Sprite;

public class Portal extends Sprite {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected final String area;
	protected String type;
	protected static final int TIMER_MAX = 10;
	protected static final int SKIN_MAX = 3;
protected int spawnNum=-1;
	protected int skin = 0;
	protected int timer = TIMER_MAX;
	protected boolean animated = true;
private int storyInt;
	public Portal(int x, int y, Board owner, String area,String type,int spawnNum) {
		super(x, y, "images/portals/" + type.split("\\|")[0] + "/0.png", owner);
this.spawnNum=spawnNum;
		this.area = area;
		this.type = type.split("\\|")[0];
if(type.contains("|")){
	storyInt=Integer.parseInt(type.split("\\|")[1]);
}
		if (type.equals("default"))
			animated = false;
	}
	public Portal(int x, int y, Board owner, String area, String type,String loc,int spawnNum) {
		super(x, y,loc, owner);

		this.area = area;
		this.type = type;
this.spawnNum=spawnNum;
		if (type.equals("default"))
			animated = false;
	}
	public String getArea() {
		return area;
	}
public int getSpawnNum(){
	return spawnNum;
}
	@Override
	public void animate() {
		basicAnimate();

		if (animated) {
			timer--;
			if (timer <= 0) {

				image = newImage("images/portals/" + type + "/" + skin + ".png");
				skin++;
				timer = TIMER_MAX;

				if (skin > SKIN_MAX)
					skin = 0;
			}
		}
		
	}

	@Override
	public void draw(Graphics2D g2d) {
		g2d.drawImage(image, x, y, owner);
	}
	public boolean interact() {
		// TODO Auto-generated method stub
		owner.getCharacter().stop();
		return true;
	}
	public void doBoolean(Boolean boolean1) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Rectangle getBounds() {
		if(storyInt<=GameCharacter.storyInt)
		return super.getBounds();
		return new Rectangle();
	}
}

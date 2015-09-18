package com.dig.www.character;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import com.dig.www.start.Board;

public class Diamond extends GameCharacter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Diamond(int x, int y, Board owner) {
		super(x, y,  owner, Types.DIAMOND, "diamond");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void drawCSHUD(Graphics2D g2d) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean canAct() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void getsActor() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void keyPressed(int key) {
		
		super.keyPressed(key);
		
		if (key == KeyEvent.VK_SPACE)
			actTimer = 40;
	}
	
	private static final int RANGE = 20;
	@Override
	public Rectangle getActBounds() {
		
		return new Rectangle(x - RANGE, y - RANGE, width + RANGE * 2, height + RANGE * 2);
	}
	
	protected void drawTool(Graphics2D g2d) {

		g2d.drawImage(newImage(type.toString()), x, y, owner);
		//g2d.draw(getActBounds());

		actTimer--;
		
		if (actTimer == 0)
			acting = false;
	}
	
	@Override
	public void endAction() {
		
	}
}

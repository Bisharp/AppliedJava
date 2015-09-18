package com.dig.www.character;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.dig.www.start.Board;
import com.dig.www.util.Statics;

public class Heart extends GameCharacter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Heart(int x, int y, Board owner) {
		super(x, y, owner, Types.HEART, "heart");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void drawCSHUD(Graphics2D g2d) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean canAct() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void getsActor() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Rectangle getActBounds() {

		switch (direction) {
		case UP:
			return new Rectangle(x, y - Statics.BLOCK_HEIGHT, Statics.BLOCK_HEIGHT, Statics.BLOCK_HEIGHT);
		case DOWN:
			return new Rectangle(x, y + Statics.BLOCK_HEIGHT, Statics.BLOCK_HEIGHT, Statics.BLOCK_HEIGHT);
		case RIGHT:
			return new Rectangle(x + Statics.BLOCK_HEIGHT, y, Statics.BLOCK_HEIGHT, Statics.BLOCK_HEIGHT);
		case LEFT:
		default:
			return new Rectangle(x - Statics.BLOCK_HEIGHT, y, Statics.BLOCK_HEIGHT, Statics.BLOCK_HEIGHT);
		}
	}
	
	@Override
	public void drawTool(Graphics2D g2d) {
		int dX = 0;
		int dY = 0;

		switch (direction) {
		case UP:
			dX = x;
			dY = y - Statics.BLOCK_HEIGHT;
			break;

		case DOWN:
			dX = x;
			dY = y + Statics.BLOCK_HEIGHT;
			break;

		case RIGHT:
			dX = x + Statics.BLOCK_HEIGHT;
			dY = y;
			break;

		case LEFT:
			dX = x - Statics.BLOCK_HEIGHT;
			dY = y;
			break;
		}

		g2d.drawImage(newImage(type.toString()), dX, dY, owner);

		if (direction == Direction.UP)
			g2d.drawImage(image, x, y, owner);

		actTimer--;
		
		if (actTimer == 0)
			acting = false;
	}
}

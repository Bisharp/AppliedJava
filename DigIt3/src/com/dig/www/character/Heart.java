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

	public Heart(int x, int y, Board owner,boolean player) {
		super(x, y, owner, Types.HEART, "heart",player
				,-10, 
				-20,
				-50,
				20,
				10,
				10, 
				75,
				10,
				80,10,15,30,1,1,0);
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

		if(toMoveString()!=null){
			g2d.drawImage(newImage(toMoveString()), dX, dY, owner);
	}

		if (direction == Direction.UP)
			g2d.drawImage(image, x, y, owner);

		timersCount();
	}

	@Override
	public Moves getMove() {
		// TODO Auto-generated method stub
		switch(getActing()){
		case 1:
			return Moves.AURA;
		case 2:
			return Moves.HAZE;
		case 3:
			return Moves.DISPENSER;
			default:
			return Moves.NONE;	
		}
	}
	@Override
	public String toMoveString(){
		switch(getActing()){
		case 1:
		return getType().toString();
		
		
		case 3:
		return "dispenser";
		default:
			case 2:
			return null;
		}
	}@Override
	public Moves getRangedMove() {
		// TODO Auto-generated method stub
		return Moves.HAZE;
	}
}

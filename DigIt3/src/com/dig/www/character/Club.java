package com.dig.www.character;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.dig.www.start.Board;
import com.dig.www.util.Statics;

public class Club extends GameCharacter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Club(int x, int y, Board owner) {
		super(x, y, owner, Types.CLUB, "club");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void drawCSHUD(Graphics2D g2d) {
		// TODO Auto-generated method stub
		//g2d.draw(getActBounds());
	}
	
	@Override
	public void drawTool(Graphics2D g2d) {
		int dX = 0;
		int dY = 0;

		switch (direction) {
		case UP:
			dX = x - 20;
			dY = y - Statics.BLOCK_HEIGHT + 70;
			break;

		case DOWN:
			dX = x + 20;
			dY = y + Statics.BLOCK_HEIGHT - 50;
			break;

		case RIGHT:
			dX = x + Statics.BLOCK_HEIGHT - 50;
			dY = y;
			break;

		case LEFT:
			dX = x - 100;
			dY = y;
			break;
		}
		if(toMoveString()!=null){
		if(getMove()==Moves.MPITCH){
		
		
		if(specialTimer>0&&specialTimer%50<20)
		g2d.drawImage(newImage(toMoveString()), dX, dY, owner);
	}
		else
			g2d.drawImage(newImage(toMoveString()), dX, dY, owner);
	}

		if (direction == Direction.UP)
			g2d.drawImage(image, x, y, owner);

		timersCount();
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
	public Rectangle getActBounds() {

		switch (direction) {
		case UP:
			return new Rectangle(x + 40, y - 30 - 18, 20, 80);
		case DOWN:
			return new Rectangle(x + 40, y + Statics.BLOCK_HEIGHT, 20, 80);
		case RIGHT:
			return new Rectangle(x + Statics.BLOCK_HEIGHT + 10, y + 15, 20, 80);
		case LEFT:
		default:
			return new Rectangle(x - 40, y + 15, 20, 80);
		}
	}
	@Override
	public Moves getMove() {
		// TODO Auto-generated method stub
		switch(getActing()){
		case 1:
			return Moves.CLUB;
		case 2:
			return Moves.PITCH;
		case 3:
			return Moves.MPITCH;
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
		return getType().toString();
		default:
			case 2:
			return null;
		}
	}
	@Override
	public Moves getRangedMove() {
		// TODO Auto-generated method stub
		return Moves.PITCH;
	}
}

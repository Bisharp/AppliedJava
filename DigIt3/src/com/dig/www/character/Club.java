package com.dig.www.character;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import com.dig.www.character.GameCharacter.Direction;
import com.dig.www.start.Board;
import com.dig.www.util.Statics;

public class Club extends GameCharacter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Club(int x, int y, Board owner,boolean player) {
		super(x, y, owner, Types.CLUB, "club",player
				,-20, 
				-40,
				-50,
				20,
				10,
				140, 
				100,
				10,
				100,15,25,70,20,10,20, Statics.STRENGTH + 5);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void drawCSHUD(Graphics2D g2d) {
		// TODO Auto-generated method stub
		//g2d.draw(getActBounds());
	}
	public int rangedAddX(){
		return 38;
	}
	@Override
	public void drawTool(Graphics2D g2d) {
		
		
	
	
		
			
			if(toMoveString()!=null){int dX = 0;
		int dY = 0;
		switch (direction) {
		case UP:
			dX = x;
			dY = y - Statics.BLOCK_HEIGHT + 50;
			break;

		case DOWN:
			dX = x;
			dY = y + Statics.BLOCK_HEIGHT - 25;
			break;

		case RIGHT:
			dX = x + Statics.BLOCK_HEIGHT - 50;
			dY = y;
			break;

		case LEFT:
			dX = x+50;
			dY = y;
			break;
		}
		if(getMove()==Moves.MPITCH){
		
		
		if(specialTimer>0&&specialTimer%50<20)
			if(direction==Direction.LEFT){
				Image anImg=newImage(toMoveString());
				g2d.drawImage(anImg, dX, dY,-anImg.getWidth(owner),anImg.getHeight(owner), owner);
			}else
				g2d.drawImage(newImage(toMoveString()), dX, dY, owner);
	}
		else
			if(direction==Direction.LEFT){
				Image anImg=newImage(toMoveString());
				g2d.drawImage(anImg, dX, dY,-anImg.getWidth(owner),anImg.getHeight(owner), owner);
			}else
				g2d.drawImage(newImage(toMoveString()), dX, dY, owner);
	}
		

	

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
			return new Rectangle(x + 42, y - 25 - 18, 25, 80);
		case DOWN:
			return new Rectangle(x + 33, y + Statics.BLOCK_HEIGHT-13, 25, 80);
		case RIGHT:
			return new Rectangle(x +  62, y + 43, 80, 25);
		case LEFT:
		default:
			return new Rectangle(x - 40, y + 43, 80, 25);
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
	@Override
	public String getRangedString(){
		return "baseball.png";
	}
}

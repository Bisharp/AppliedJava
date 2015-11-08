package com.dig.www.character;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import com.dig.www.start.Board;
import com.dig.www.util.Statics;

public class SirCobalt extends GameCharacter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SirCobalt(int x, int y, Board owner,boolean player) {
		super(x, y, owner, Types.SIR_COBALT, "sirCobalt",player
				,-20, 
				-40,
				-4000,
				20,
				10,
				140, 
				100,
				10,
				100,15,25,70,30,20,10, Statics.STRENGTH + 10);
		height=109;
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
if(specialTimer>0)
	energy=0;
		switch (direction) {
		case UP:
			dX = x;
			dY = y - Statics.BLOCK_HEIGHT + 30;
			break;

		case DOWN:
			dX = x;
			dY = y + Statics.BLOCK_HEIGHT - 50;
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
		if(toMoveString()!=null){
	
		if(direction==Direction.LEFT){
			Image anImg=newImage(toMoveString());
			g2d.drawImage(anImg, dX, dY,-anImg.getWidth(owner),anImg.getHeight(owner), owner);
		}else
			g2d.drawImage(newImage(toMoveString()), dX, dY, owner);
			
			
	}

		if (direction == Direction.UP)
			g2d.drawImage(image, x, y, owner);

	}
	@Override
	public int rangedAddY(){
		return height/2-24;
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
			return new Rectangle(x + 40, y - 50 - 18, 20, 100);
		case DOWN:
			return new Rectangle(x + 40, y-15 + Statics.BLOCK_HEIGHT, 20, 80);
		case RIGHT:
			return new Rectangle(x + Statics.BLOCK_HEIGHT - 35, y + height/2-22, 100, 20);
		case LEFT:
		default:
			return new Rectangle(x - Statics.BLOCK_HEIGHT + 35, y + height/2-22, 100, 20);
		}
	}
	@Override
	public Moves getMove() {
		// TODO Auto-generated method stub
		switch(getActing()){
		case 1:
			return Moves.STAB;
		case 2:
			return Moves.DIMENSION;
		case 3:
			return Moves.WARP;
			default:
			return Moves.NONE;	
		}
	}
	@Override
	public String toMoveString(){
		if(getMove()==Moves.NONE)
			return null;
		else
		return "rapier";
		
	}
	@Override
	public Moves getRangedMove() {
		// TODO Auto-generated method stub
		return Moves.DIMENSION;
	}
	@Override
	public String getRangedString(){
		return "portal.gif";
	}
}

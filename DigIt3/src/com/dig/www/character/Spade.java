package com.dig.www.character;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.ObjectInputStream.GetField;

import com.dig.www.character.GameCharacter.Direction;
import com.dig.www.start.Board;
import com.dig.www.util.Statics;

public class Spade extends GameCharacter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int dirt = 0;

	public Spade(int x, int y, Board owner,boolean player) {
		super(x, y,  owner, Types.SPADE, "spade",player
				,-20, 
				-25,
				-50,
				15,
				15,
				10, 
				80,
				10,
				100,10,20,50,10,30,0, Statics.STRENGTH - 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void drawCSHUD(Graphics2D g2d) {
		// TODO Auto-generated method stub
		int normWidth=300;
		
		g2d.setColor(Color.BLACK);
		g2d.fillRect(normWidth, 20, 150 + ( g2d.getFont().getSize()/2* numOfDigits(dirt)), 50);
		g2d.setColor(Statics.BROWN);
		g2d.drawString("DIRT: " + dirt, normWidth+20, 55);
	}

	public boolean canAct() {

		if (dirt > 0) {
			dirt--;
			return true;
		} else
			return false;
	}

	public void getsActor() {
		dirt++;
	}
	@Override
	public Moves getMove() {
		// TODO Auto-generated method stub
		switch(getActing()){
		case 1:
			return Moves.SPADE;
		case 2:
			return Moves.ARROW;
		case 3:
			return Moves.PIT;
			default:
			return Moves.NONE;	
		}
	}
	@Override
	public String toMoveString(){
		switch(getActing()){
		case 1:
		return getType().toString();
		case 2:
		return "bowD";
		case 3:
		return getType().toString();
		default:
			
			return null;
		}
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

		timersCount();
	}
	@Override
	public Moves getRangedMove() {
		// TODO Auto-generated method stub
		return Moves.ARROW;
	}
@Override
public int rangedAddX(){
	return 3;
}
	public void resetDirt() {
		// TODO Auto-generated method stub
		dirt=0;
	}
	@Override
	public String getRangedString(){
		return "arrow.png";
	}
}

package com.dig.www.character;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
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
				-20,
				-50,
				15,
				55,
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
			if(rangedTimer>=30)
		return "bowD";
			else
				return "bowN";
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
			dY = y - Statics.BLOCK_HEIGHT + 50;
			break;

		case DOWN:
			dX = x;
			dY = y + Statics.BLOCK_HEIGHT - 50;
			break;

		case RIGHT:
			dX = x + Statics.BLOCK_HEIGHT - 70;
			dY = y;
			break;

		case LEFT:
			dX = x+70;
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
	public Rectangle getActBounds() {
if(1==getActing()){
	switch (direction) {
	case UP:
		return new Rectangle(x + 45, y - 50, 19, 60);
	case DOWN:
		return new Rectangle(x + 35, y + Statics.BLOCK_HEIGHT - 10, 19, 60);
	case RIGHT:
		return new Rectangle(x + 70, y + 45, 60, 19);
	case LEFT:
	default:
		return new Rectangle(x - 30, y + 45, 60, 19);
	}
}else
		switch (direction) {
		case UP:
			return new Rectangle(x + 47, y - 30, 6, 6);
		case DOWN:
			return new Rectangle(x + 47, y + Statics.BLOCK_HEIGHT + 40, 6, 6);
		case RIGHT:
			return new Rectangle(x + Statics.BLOCK_HEIGHT + 15, y + Statics.BLOCK_HEIGHT - 6, 6, 6);
		case LEFT:
		default:
			return new Rectangle(x - 40, y + Statics.BLOCK_HEIGHT - 6, 6, 6);
		}
	}
}

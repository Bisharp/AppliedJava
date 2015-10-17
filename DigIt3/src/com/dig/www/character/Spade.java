package com.dig.www.character;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.ObjectInputStream.GetField;

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
				100,10,20,50,10,30,0);
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
		return Moves.ARROW;
	}

	public void resetDirt() {
		// TODO Auto-generated method stub
		dirt=0;
	}
}

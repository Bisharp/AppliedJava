package com.dig.www.character;

import java.awt.Color;
import java.awt.Graphics2D;

import com.dig.www.start.Board;
import com.dig.www.util.Statics;

public class Spade extends GameCharacter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int dirt = 0;

	public Spade(int x, int y, Board owner) {
		super(x, y,  owner, Types.SPADE, "spade");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void drawCSHUD(Graphics2D g2d) {
		// TODO Auto-generated method stub

		g2d.setColor(Color.BLACK);
		g2d.fillRect(10, 120, 150 + (20 * numOfDigits(dirt)), 50);
		g2d.setColor(Statics.BROWN);
		g2d.drawString("DIRT: " + dirt, 20, 150);
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
}

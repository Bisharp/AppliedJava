package com.dig.www.npc;

import java.awt.Rectangle;

import javax.swing.JOptionPane;

import com.dig.www.character.GameCharacter;
import com.dig.www.start.Board;
import com.dig.www.start.DigIt;
import com.dig.www.start.Board.State;

public class Shopkeep extends NPC {

	private static final String rebuffLine = "Sorry; I can't give credit.";
	private static final int PRICE0 = 1;
	private static final int PRICE1 = 1;

	public Shopkeep(int x, int y, String loc, Board owner, String location) {
		super(x, y, loc, owner, new String[] { "Welcome to my shop! Is there anything you wish to buy?" }, SHOPKEEP, location, new NPCOption[] {
				new NPCOption("Buy health\n(Price: " + PRICE0 + ")", "Enjoy, my friend!", new String[] { "I would like to buy some health.",
						"Give me some health.", "...", "Ooh, health looks good!", "Some health, if you will." }, true, owner),
				new NPCOption("Buy energy\n(Price: " + PRICE1 + ")", "Enjoy my friend!", new String[] { "I would like to buy some energy.",
						"Give me some energy.", "...", "Some energy sounds delightful!",
						"Some energy, please." }, true, owner), });
	}

	@Override
	public void act(NPCOption option) {

		if (option.ID == 0) {
			if (GameCharacter.getInventory().getMoney() > PRICE0) {
				GameCharacter.getInventory().spendMoney(PRICE0);
				owner.getCharacter().heal(100);
			} else
				line = rebuffLine;
		} else if (option.ID == 1) {
			if (GameCharacter.getInventory().getMoney() > PRICE1) {
				GameCharacter.getInventory().spendMoney(PRICE1);
				owner.getCharacter().setEnergy(100);
			} else
				line = rebuffLine;
		}
	}

	@Override
	public String exitLine() {
		return "Come back anytime!";
	}

	@Override
	public String getShowName() {
		return "Strange shopkeeper";
	}
	@Override
	public Rectangle getBounds(){
		
		return new Rectangle(x+10,y+5,80,95);
		
	}
}
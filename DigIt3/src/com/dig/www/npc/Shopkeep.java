package com.dig.www.npc;

import java.awt.Rectangle;

import javax.swing.JOptionPane;

import com.dig.www.character.GameCharacter;
import com.dig.www.character.Items;
import com.dig.www.start.Board;
import com.dig.www.start.DigIt;
import com.dig.www.start.Board.State;

public class Shopkeep extends NPC {

	private static final String rebuffLine = "Sorry; I can't give credit.";
	private static final int PRICE0 = 10;
	private static final int PRICE1 = 10;

	public Shopkeep(int x, int y, String loc, Board owner, String location) {
		super(x, y, loc, owner, new String[] { "Welcome to my shop! Is there anything you wish to buy?" }, SHOPKEEP, location, new NPCOption[] {
				new NPCOption("Buy N&Ns\n(Price: " + PRICE0 + ")", "Enjoy the candy, my friend!", new String[] { "I would like to buy some N&Ns.",
						"Give me the candy.", "...", "Ooh, those N&Ns looks good!", "Some N&Ns, if you will." }, true, owner),
				new NPCOption("Buy a donut\n(Price: " + PRICE1 + ")", "Enjoy my friend!", new String[] { "I would like to buy a donut.",
						"Give me a donut.", "...", "That donut seems delightful!",
						"A donut, please." }, true, owner), });
	}

	@Override
	public void act(NPCOption option) {

		if (option.ID == 0) {
			if (GameCharacter.getInventory().getMoney() >= PRICE0) {
				GameCharacter.getInventory().spendMoney(PRICE0);
     GameCharacter.getInventory().addItem(Items.NandNs, 1);
			} else
				line = rebuffLine;
		} else if (option.ID == 1) {
			if (GameCharacter.getInventory().getMoney() >= PRICE1) {
				GameCharacter.getInventory().spendMoney(PRICE1);
				 GameCharacter.getInventory().addItem(Items.DONUT, 1);
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
package com.dig.www.npc;

import javax.swing.JOptionPane;

import com.dig.www.character.GameCharacter;
import com.dig.www.start.Board;
import com.dig.www.start.DigIt;
import com.dig.www.start.Board.State;

public class Shopkeep extends ServiceNPC {
	
	private String crucialString = "Do you want me to heal you? (Enter ==> yes)";

	public Shopkeep(int x, int y, String loc, Board owner) {
		super(x, y, loc, owner, new String[] { "",
				"Lamp oil, rope, bo... never mind. " }, "shopkeep");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void service() {
		// if (JOptionPane.showConfirmDialog(owner,
		// "Do you want me to heal you?", DigIt.NAME, JOptionPane.YES_NO_OPTION)
		// == JOptionPane.YES_OPTION) {
		for (GameCharacter c : owner.getFriends())
			c.heal(100);
		owner.getCharacter().heal(100);
		// }

		owner.setState(State.INGAME);
	}
	
	public String getLine() {
		return dialogs[line] + crucialString;
	}
}

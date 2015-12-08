package com.dig.www.objects;

import com.dig.www.character.GameCharacter;
import com.dig.www.character.Items;
import com.dig.www.npc.NPC;
import com.dig.www.start.Board;

public class CollectibleCharacter extends Collectible {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CollectibleCharacter(int x, int y, String loc, Board owner) {
		super(x, y, loc,true, owner, Items.NULL);
	}

	public GameCharacter getCharacter() {
		
		String[] s = loc.split("/");
		
		switch (s[s.length - 1].split(".")[0]) {
		case NPC.SIR_COBALT:
			// add Sir Cobalt code here
		case NPC.MACARONI:
			// add Super Macaroni Noodle Man code here
		}
		
		return null;
	}

}

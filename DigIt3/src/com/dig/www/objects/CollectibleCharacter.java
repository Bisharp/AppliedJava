package com.dig.www.objects;

import javax.swing.JOptionPane;

import com.dig.www.character.GameCharacter;
import com.dig.www.character.Items;
import com.dig.www.character.GameCharacter.Types;
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
	public void makeCharacter() {
		
		String[] s = loc.split("/");
		///System.out.println(s[s.length - 3]);
		//System.out.println(s[s.length - 2].split("\\.")[0]);
		switch (s[s.length - 3]) {
		case NPC.SIR_COBALT:
			// add Sir Cobalt code here
			owner.getFriends().add(new com.dig.www.character.SirCobalt(x, y, owner, false));
			owner.heyIaddedAFriendBack(owner.getFriends().get(owner.getFriends().size()-1),Types.SIR_COBALT.toString());
	break;
		case NPC.MACARONI:
			// add Super Macaroni Noodle Man code here
			owner.getFriends().add(new com.dig.www.character.Macaroni(x, y, owner, false));
			owner.heyIaddedAFriendBack(owner.getFriends().get(owner.getFriends().size()-1),Types.MACARONI.toString());
		break;
		}
	}
@Override
public boolean interact() {
	// TODO Auto-generated method stub
	JOptionPane.showMessageDialog(owner, "human... Well, not always.");
	return true;
}
}

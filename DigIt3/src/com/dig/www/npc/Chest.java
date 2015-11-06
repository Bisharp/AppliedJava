package com.dig.www.npc;

import java.awt.Image;

import com.dig.www.character.GameCharacter;
import com.dig.www.character.Items;
import com.dig.www.start.Board;

public class Chest extends ServiceNPC {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean opened = false;
	private String[] dialogs2 = new String[] { "It appears to be empty.", "It's empty. Can I break it, then?", "Oh; it's empty. How depressing.",
			"... *It's empty*", "Nothing to see in here." };
	private Items type;

	public Chest(int x, int y, String loc, Board owner, Items type) {
		super(x, y, loc, owner, new String[] { "This chest holds: " + type.toString() + ".", "This hunk of junk holds: " + type.toString() + ".",
				"Oh, uh, this chest appears to hold a/an " + type.toString() + ".",
				"... *Chest contains a/an " + type.toString() + "*", "It's got a/an " + type.toString() + " in it." }, "shovel", "I guess we don't need " + type.toString() + " yet.");
		this.type = type;
	}

	@Override
	public void service() {

		opened = true;
		image = newImage("images/objects/chestO.png");
		GameCharacter.getInventory().addItem(type, 1);
		type = Items.NULL;
	}

	@Override
	public String getLine() {

		if (opened)
			switch (owner.getCharacter().getType()) {
			case CLUB:
				return dialogs2[1];
			case HEART:
				return dialogs2[2];
			case DIAMOND:
				return dialogs2[3];
			case SIR_COBALT:
				return dialogs2[4];
			default:
				return dialogs2[0];
			}
		else
			switch (owner.getCharacter().getType()) {
			case CLUB:
				return dialogs[1];
			case HEART:
				return dialogs[2];
			case DIAMOND:
				return dialogs[3];
			case SIR_COBALT:
				return dialogs[4];
			default:
				return dialogs[0];
			}
	}

	private static final String path = "images/npcs/talking/";

	@Override
	public Image getGif() {
		return newImage(path + owner.getCharacter().getType().toString() + ".gif");
	}
}

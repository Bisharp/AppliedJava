package com.dig.www.npc;

import java.awt.Image;

import com.dig.www.character.GameCharacter;
import com.dig.www.character.Items;
import com.dig.www.start.Board;

public class Chest extends NPC {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean opened = false;
	private boolean sayWhat = false;
	private String[] dialogs2 = new String[] { "Never mind, it's empty.", "Oh, it's empty. Can I break it, then?", "Oh; it's empty. How depressing.",
			"... (You can tell from his blanker than normal stare that it's empty)", "Nothing to see in here." };
	private Items type;

	public Chest(int x, int y, String loc, Board owner, String location, Items type) {
		super(x, y, loc, owner, new String[] { "It's a chest!", "It appears to be a chest.", "Oh fun, it's a chest!",
				"... (You can tell from the slight upward curve of the corners of his mouth that it's a chest)", "It's a chest." }, "shovel", location,
				new NPCOption[] { new NPCOption("Check inside", type.toString(), new String[] { "This chest contains:",
						"This hunk of junk contains:", "...", "Ooh, what's inside? I can hardly bear the suspense... It holds a..." }, true, owner) });
		this.type = type;
	}

	@Override
	public void act(NPCOption o) {

		if (!opened) {
			sayWhat = true;
			opened = true;
			image = newImage("images/objects/chestO.png");
			GameCharacter.getInventory().addItem(type, 1);
		}
	}

	@Override
	public String getLine() {

		if (sayWhat)
			switch (owner.getCharacter().getType()) {
			case CLUB:
				return "Hey! it actually held a/an " + type.toString() + "!";
			case HEART:
				return "Oh cool! A/an " + type.toString() + ".";
			case DIAMOND:
				return "... (You can tell from the arch of his eyebrows that the chest held a/an " + type.toString() + ")";
			case SIR_COBALT:
				return "It holds a/an " + type.toString() + ".";
			default:
				return "It's got a/an " + type.toString() + " in it.";
			}
		if (opened && !exiting)
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
		else if (exiting)
			switch (owner.getCharacter().getType()) {
			case CLUB:
				return "We need to go.";
			case HEART:
				return "Never mind, I guess.";
			case DIAMOND:
				return "...";
			case SIR_COBALT:
				return "We have other business to attend to.";
			default:
				return "We need to go.";
			}
		else
			switch (owner.getCharacter().getType()) {
			case CLUB:
				return greetingDialogs[1];
			case HEART:
				return greetingDialogs[2];
			case DIAMOND:
				return greetingDialogs[3];
			case SIR_COBALT:
				return greetingDialogs[4];
			default:
				return greetingDialogs[0];
			}
	}

	private static final String path = "images/npcs/talking/";

	@Override
	public Image getGif() {
		return newImage(path + owner.getCharacter().getType().toString() + ".gif");
	}

	@Override
	public String exitLine() {
		return "Whatever it is, we don't need it now.";
	}

	@Override
	protected String getGreeting() {
		switch (owner.getCharacter().getType()) {
		case CLUB:
			return "What's this piece of trash?";
		case DIAMOND:
			return "...";
		case HEART:
			return "Oh! What's this?";
		case SIR_COBALT:
			return "Hmm...";
		default:
			return "Eh?";
		}
	}

	@Override
	protected String getFarewell() {

		sayWhat = false;
		switch (owner.getCharacter().getType()) {
		case CLUB:
			return "Forget this.";
		case DIAMOND:
			return "...;";
		case HEART:
			return "Uh... we have to go, don't we?";
		case SIR_COBALT:
			return "Leave it for later.";
		default:
			return "We don't have time for this;";
		}
	}
}

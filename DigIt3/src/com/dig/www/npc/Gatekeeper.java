package com.dig.www.npc;

import com.dig.www.character.Items;
import com.dig.www.start.Board;

public class Gatekeeper extends BlockerNPC {

	public static final String COLLECTIBLE = Items.SPECIAL_COLLECTIBLE.toString();

	public Gatekeeper(int x, int y, String loc, Board owner, String location, int value) {
		super(x, y, loc, owner, new String[] { "You want me to let you by? Forget that! Well, maybe if you have enough " + COLLECTIBLE
				+ "s, I couldn't stop you... I won't say how many that would be, and it totally isn't " + value + "." }, GATEKEEPER, location,
				new NPCOption[] { new NPCOption("Challenge!", "Ha! What a joke!", new String[] {
						"Uh... this isn't wise, guys, but OK. Mr. Ham-fisted-dude, I challenge you to a fight.", "Put up your dukes!", "...",
						"Wait, I'm supposed to challenge *HIM!?|", "Engarde!" }, true, owner) }, value);
		
		line = greetingDialogs[0];
	}

	@Override
	public void checkWall() {
		if (!acts && owner.getData().getCollectibleNum() >= value)
			isWall = false;
	}

	@Override
	public String getLine() {
		return line;
	}

	@Override
	public String exitLine() {
			return "Come back when you are ready to surrender to the superior might of Drusk!";
	}

	@Override
	protected String moveLine() {
		return "NO! HOW CAN THIS BE? I AM UNBEATABLE!";
	}
}

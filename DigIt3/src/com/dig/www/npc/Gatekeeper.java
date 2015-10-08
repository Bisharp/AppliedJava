package com.dig.www.npc;

import com.dig.www.start.Board;

public class Gatekeeper extends BlockerNPC {
	private static String crucialString = "(Enter ==> Show 'em!)";

	public Gatekeeper(int x, int y, String loc, Board owner, int value) {
		super(x, y, loc, owner, new String[] { "You want me to let you by? Forget that! Well, maybe if you have enough " + COLLECTIBLE
				+ "s, I couldn't stop you... I won't say how many that would be, and it totally isn't " + value + "." }, NPC.GATEKEEPER, value, "Fool. I knew you couldn't.");
	}

	@Override
	public void service() {
		if (owner.getData().getCollectibleNum() >= value)
			isWall = false;
	}

	@Override
	public String getLine() {
		return dialogs[0] + (owner.getData().getCollectibleNum() >= value ? " " + crucialString : "");
	}
}

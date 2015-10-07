package com.dig.www.npc;

import com.dig.www.start.Board;

public class Gatekeeper extends BlockerNPC {
	
	public Gatekeeper(int x, int y, String loc, Board owner, String[] dialogs, int value) {
		super(x, y, loc, owner, new String[]{"You want me to let you by? Forget that, friend! Well, maybe if you have enough " + COLLECTIBLE + "s, I could... My price is " + value + "."}, NPC.GATEKEEPER, value, "Fool. I knew you couldn't.", "Step this way, buddies!");
	}

	@Override
	public void service() {
		// TODO Change to owner.getData().getCollectibleNum() when possible
		if (owner.getCharacter().getWallet().getMoney() > value)
			isWall = false;
	}
}

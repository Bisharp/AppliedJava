package com.dig.www.npc;

import com.dig.www.start.Board;

public class WizardGuy extends NPC {
	public WizardGuy(int x, int y, String loc, Board owner) {
		super(x, y, loc, owner, new String[]{"Magic is cool!", "It's dangerous to go alone. Take this!", "Mom told me I could grow up to be anything I wanted, so I became a wizard!", "Can you believe that guy? He thinks science is superior to magic!"}, WIZARD);
		//	*	*	*	*	*	/=\
		//	*	*	*	*	*	 | Magic. Do not touch.
	}
}
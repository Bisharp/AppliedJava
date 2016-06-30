package com.dig.www.npc;

import com.dig.www.start.Board;

public class SunSetNPC extends CutScene{

	public SunSetNPC(Board owner, String location) {
		super(0, 0, owner, location, new CSDialog[]{new CSDialog("Oh look! The sun is setting! Doesn't it look great?", NPC.HEART, null),new CSDialog("You only see something like that once every twenty-four hours.", NPC.CLUB, null),new CSDialog("...", NPC.DIAMOND, null)});
	}

}

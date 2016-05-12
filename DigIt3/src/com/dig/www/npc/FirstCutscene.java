package com.dig.www.npc;

import com.dig.www.start.Board;
import com.dig.www.util.OnlyFirstTimeEnteringMap;

public class FirstCutscene extends CutScene implements OnlyFirstTimeEnteringMap{


	public FirstCutscene(int x, int y, Board owner, String location) {
		super(x, y, owner, location, new CSDialog[]{new CSDialog("Ow ow ow, everything hurts.", NPC.SPADE, null),new CSDialog("We're here!", NPC.HEART, null),new CSDialog("This place is a dump. The last place was a dump. I wonder where we'll go next.", NPC.CLUB, null),new CSDialog("...", NPC.DIAMOND, null),new CSDialog("I think we should meet this Wizard person!", NPC.HEART, null),new CSDialog("I think we should go home, but I guess we don't have a choice.", NPC.SPADE, null),new CSDialog("There's the door!(Press the +Arrow Keys| to move)", NPC.HEART, null)});}

	@Override
	protected String getGreeting() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	protected String getFarewell() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String exitLine() {
		// TODO Auto-generated method stub
		return "";
	}

}

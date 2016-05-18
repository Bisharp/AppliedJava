package com.dig.www.npc;

import com.dig.www.character.GameCharacter;
import com.dig.www.start.Board;
import com.dig.www.util.ConditionEnteringMap;

public class EnterFirstTimeWizTowerGiftShop extends CutScene implements ConditionEnteringMap{

	public EnterFirstTimeWizTowerGiftShop(int x, int y, Board owner, String location) {
		super(x, y, owner, location,new CSDialog[]{ new CSDialog("HI", NPC.SPADE, null)});
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void end() {
		if(GameCharacter.storyInt<5)
			GameCharacter.storyInt=5;
		super.end();
	}
	@Override
	public boolean enter() {
		return GameCharacter.storyInt<5;
	}
}

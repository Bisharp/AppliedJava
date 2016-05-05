package com.dig.www.npc;

import com.dig.www.character.GameCharacter;
import com.dig.www.start.Board;
import com.dig.www.util.ConditionEnteringMap;
import com.dig.www.util.OnlyFirstTimeEnteringMap;

public class EnterFirstTimeWizTower extends InvisibleNormalOnceTouchNPC implements ConditionEnteringMap{

	public EnterFirstTimeWizTower(int x, int y, Board owner,String location) {
		super(x, y, owner, new String[]{"words"}, NPC.DIAMOND, location, new NPCOption[]{});
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getGreeting() {
		// TODO Auto-generated method stub
		return "Second cutscene(Although it was scripted along with the first)";
	}

	@Override
	protected String getFarewell() {
		// TODO Auto-generated method stub
		return "more words";
	}

	@Override
	public String exitLine() {
		// TODO Auto-generated method stub
		return "cut";
	}
@Override
protected void end() {
	if(GameCharacter.storyInt==0)
	GameCharacter.storyInt=1;
	super.end();
}

@Override
public boolean enter() {
	return GameCharacter.storyInt==0;
}
}

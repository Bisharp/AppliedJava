package com.dig.www.npc;

import com.dig.www.character.GameCharacter;
import com.dig.www.start.Board;
import com.dig.www.util.ConditionEnteringMap;
import com.dig.www.util.OnlyFirstTimeEnteringMap;

public class EnterFirstTimeWizTower extends CutScene implements ConditionEnteringMap{

public EnterFirstTimeWizTower(int x, int y, Board owner, String location) {
		super(x, y, owner, location,new CSDialog[]{new CSDialog("There will be stuff(Press the +X Key| while facing someone to talk)", "futureScientist", null)});
		// TODO Auto-generated constructor stub
	}
@Override
public void act(NPCOption npcOption){
	if(c==0)
		for(int c=0;c<owner.getNPCs().size();c++)
			if(owner.getNPCs().get(c) instanceof WizardTowerSirCobalt)
				((WizardTowerSirCobalt)owner.getNPCs().get(c)).visible=true;
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

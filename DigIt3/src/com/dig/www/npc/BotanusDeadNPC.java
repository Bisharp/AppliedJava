package com.dig.www.npc;

import com.dig.www.character.GameCharacter;
import com.dig.www.start.Board;

public class BotanusDeadNPC extends CutScene{

	public BotanusDeadNPC( Board owner) {
		super(-1000, -1000, owner,"United Country",new CSDialog[]{new CSDialog("Stuff to be added", "futureScientist", null)});
	}
@Override
public void act(NPCOption npcOption) {
	if(c>=cutScenes.length){
	if(GameCharacter.storyInt==7)
		GameCharacter.storyInt++;
		owner.newLevel("Hub");
	}
		
}
}

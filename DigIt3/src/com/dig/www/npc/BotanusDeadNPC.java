package com.dig.www.npc;

import com.dig.www.start.Board;

public class BotanusDeadNPC extends CutScene{

	public BotanusDeadNPC( Board owner) {
		super(-1000, -1000, owner,"United Country",new CSDialog[]{new CSDialog("Botanus: Ow! You shot/hit me once or twice", "black", null)});
	}
@Override
public void act(NPCOption npcOption) {
	if(c>=cutScenes.length){
		owner.newLevel("Hub");
	}
		
}
}

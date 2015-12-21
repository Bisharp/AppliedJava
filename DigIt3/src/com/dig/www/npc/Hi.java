package com.dig.www.npc;

import com.dig.www.start.Board;

public class Hi extends NPC implements TouchNPC{
private boolean hasTalked=false;
	public Hi(int x, int y, Board owner,
			String location) {
		super(x, y, "images/icon.png",owner, new String[]{"hi","hi","hi","hi","hi"}, NPC.MACARONI,location,new NPCOption[]
			{
			new NPCOption("hi", "ok",new String[]{"hi","hi","hi","hi","hi"}, false,new NPCOption[0], owner, NPC.CLUB, NPC.GATEKEEPER)
			},NPC.SPADE,NPC.HEART,NPC.KEPLER,false
	);
				// TODO Auto-generated constructor stub
	}

	@Override
	public String exitLine() {
		// TODO Auto-generated method stub
		return "bye";
	}

	@Override
	public String getShowName() {
		// TODO Auto-generated method stub
		return "thing";
	}
@Override
public boolean willTalk(){
	boolean willTalk=!hasTalked;
	hasTalked=true;
	return willTalk;
}
}

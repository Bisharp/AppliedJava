package com.dig.www.npc;


import java.awt.Graphics2D;

import com.dig.www.start.Board;
import com.dig.www.util.Statics;

public class Hi extends InvisbleNormalOnceTouchNPC{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private boolean hasTalked=false;
	public Hi(int x, int y, Board owner,
			String location) {
		super(x, y,owner, new String[]{"hi","hi","hi","hi","hi"}, NPC.MACARONI,location,new NPCOption[]
			{
			new NPCOption("hi", "ok",new String[]{"hi","hi","hi","hi","hi"}, false,new NPCOption[0], owner, NPC.CLUB, NPC.GATEKEEPER)
			},NPC.SPADE,NPC.HEART,NPC.KEPLER,false,new String[]{}
	);
	}

	@Override
	public String exitLine() {
		return "bye";
	}

	@Override
	protected String getGreeting() {
		// TODO Auto-generated method stub
		return "hi";
	}

	@Override
	protected String getFarewell() {
		// TODO Auto-generated method stub
		return"hi";
	}


}

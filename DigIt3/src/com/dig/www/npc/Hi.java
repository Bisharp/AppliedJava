package com.dig.www.npc;


import java.awt.Color;
import java.awt.Graphics2D;

import com.dig.www.start.Board;
import com.dig.www.util.Statics;

public class Hi extends InvisibleNormalOnceTouchNPC{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private boolean hasTalked=false;
	public Hi(int x, int y, Board owner,
			String location) {
		super(x, y,owner, new String[]{"hi","hi","hi","hi","hi"}, NPC.MACARONI,location,new NPCOption[]
			{
			
			},NPC.SPADE,NPC.HEART,NPC.KEPLER,false,new String[]{},new CutSceneImage("images/npcs/cutScenes/RyoBoss2/out.gif"),new CutSceneImage("images/gifTest.gif",new Color(34,177,76)),new CutSceneImage("images/objects/bombCube.png"),new CutSceneImage("images/Loading.gif")
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

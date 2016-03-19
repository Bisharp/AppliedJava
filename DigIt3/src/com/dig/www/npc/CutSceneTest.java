package com.dig.www.npc;

import java.awt.Color;

import com.dig.www.start.Board;

public class CutSceneTest extends CutScene{

	public CutSceneTest(int x, int y, Board owner, String location) {
		super(x, y, owner, location,new CSDialog[]{new CSDialog("", "black",new CutSceneImage("images/npcs/cutScenes/RyoBoss2/out.gif")), new CSDialog( "Whew, it's hot! We should've brought sunscreen.",NPC.SPADE,new CutSceneImage("images/npcs/cutScenes/RyoBoss2/hot.gif"))});
		// TODO Auto-generated constructor stub
	}

	

}

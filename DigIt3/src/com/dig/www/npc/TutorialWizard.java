package com.dig.www.npc;

import javax.swing.ImageIcon;

import com.dig.www.start.Board;

public class TutorialWizard extends MoveNPC{

	public TutorialWizard(int x, int y, Board owner, String location,
			NPCOption[] options, MovePoint[] movePoints, int type, int speed) {
		super(x, y, "images/characters/wizard/", owner, new String[]{"Do you require my assistance?"}, NPC.WIZARD, location, new NPCOption[]{}, new MovePoint[]{}, 0, 6);
	}

	@Override
	public String exitLine() {
		// TODO Auto-generated method stub
		return "Ask me if you need any more help. Good luck!";
	}

	@Override
	public String getShowName() {
		// TODO Auto-generated method stub
		return "The Wizard";
	}

}

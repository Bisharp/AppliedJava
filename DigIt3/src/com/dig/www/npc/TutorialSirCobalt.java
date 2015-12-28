package com.dig.www.npc;

import java.awt.Rectangle;

import com.dig.www.start.Board;

public class TutorialSirCobalt extends MoveNPC {

	public TutorialSirCobalt(int x, int y, Board owner, String location) {
		super(x, y, "images/npcs/map/stationary/sirCobalt.png", owner,
				new String[] { "Sure, we need to hurry, though." }, NPC.SIR_COBALT, location,
				new NPCOption[] {}, new MovePoint[] {
						new MovePoint(x + 1020, y + 200, true),
						new MovePoint(x + 1800, y +200, true),
						new MovePoint(x + 2170, y +1000, false),
						new MovePoint(x + 2470, y +1200, false),
						new MovePoint(x + 3470, y +1200, false),
						new MovePoint(x + 3870, y +800, false),
						new MovePoint(x + 4270, y +800, false)}, 0, 4);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String exitLine() {
		// TODO Auto-generated method stub
		return "Follow me, friend.";
	}

	@Override
	public String getShowName() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x + 20, y, 60, 100);
	}

	protected String getGreeting() {
		return "Can I ask a few questions? (Good job, you figured out that +X|"
				+ " is the interact button. You don't need to   know this yet, "
				+ "but not only can you interact with people, but also with most objects.)";
	}

	protected String getFarewell() {
		return "I'm ready,let's go.";
	}
}

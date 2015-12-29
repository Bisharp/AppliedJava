package com.dig.www.npc;

import java.awt.Rectangle;

import com.dig.www.start.Board;

public class Tutorial2 extends InvisibleNormalOnceTouchNPC {

	public Tutorial2(int x, int y, Board owner, String location) {
		super(
				x,
				y,
				owner,
				new String[] { "The red stuff is an evil aura, it controls the object inside." },
				NPC.SIR_COBALT,
				location,
				new NPCOption[] { new NPCOption(
						"It's not moving.",
						"Some of them don't move at all, Some of them charge, some chase, some shoot. Why? I don't know. This one isn't moving, so just leave it alone.",
						new String[] { "It's not moving.", "It's not moving.",
								"It's not moving.", "It's not moving.",
								"It's not moving.", "It's not moving.",
								"It's not moving.", "It's not moving." }, true,
						owner) }, NPC.SPADE, NPC.SIR_COBALT, NPC.SPADE, true,
				new String[0]);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void act(NPCOption option) {
		options = new NPCOption[] {};
		cantExit = false;
		for (NPC npc : owner.getNPCs()) {
			if (npc instanceof TutorialSirCobalt) {
				((TutorialSirCobalt) npc).setMovePointFirstWait(1, false);
			}

		}
	};

	@Override
	public String exitLine() {
		// TODO Auto-generated method stub
		return "It shouldn't lunge. Good rule of thumb: If it's surrounded by a red glowy stuff, avoid it.";
	}

	@Override
	protected String getGreeting() {
		// TODO Auto-generated method stub
		return "What's that?";
	}

	@Override
	protected String getFarewell() {
		// TODO Auto-generated method stub
		return "OK, We'll just move by. It won't lunge, right?";
	}

	@Override
	public boolean isChars() {
		for (NPC npc : owner.getNPCs()) {
			if (npc instanceof TutorialSirCobalt && npc.getBounds().intersects(getBounds())) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean isObstacle() {
		// TODO Auto-generated method stub
		if (!hasTalked)
			return true;
		else
			return false;
	}
}

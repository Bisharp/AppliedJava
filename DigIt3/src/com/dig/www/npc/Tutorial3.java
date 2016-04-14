package com.dig.www.npc;

import java.awt.Rectangle;

import com.dig.www.blocks.Portal;
import com.dig.www.start.Board;

public class Tutorial3 extends InvisibleNormalOnceTouchNPC {
	private boolean hasTouched;

	public Tutorial3(int x, int y, Board owner, String location) {
		super(
				x,
				y,
				owner,
				new String[] { "I'm creating a portal." },
				NPC.SIR_COBALT,
				location,
				new NPCOption[] { new NPCOption(
						"Portal?",
						"Yes, one of the things that teleported you here.",
						new String[] { "Portal?", "Portal?", "Portal?",
								"Portal?", "Portal?", "Portal?", "Portal?",
								"Portal?" },
						false,
						new NPCOption[] { new NPCOption(
								"You can make one?",
								"Yes, though I can't teleport too far.",
								new String[] { "You can make one?",
										"You can make one?",
										"You can make one?",
										"You can make one?",
										"You can make one?",
										"You can make one?",
										"You can make one?",
										"You can make one?" },
								false,
								new NPCOption[] { new NPCOption(
										"Did you teleport us here?",
										"No, that was the sorcerer, the one you made the red aura. He is more powerful than I.",
										new String[] {
												"Did you teleport us here?",
												"Did you teleport us here?",
												"Did you teleport us here?",
												"Did you teleport us here?",
												"Did you teleport us here?",
												"Did you teleport us here?",
												"Did you teleport us here?",
												"Did you teleport us here?" },
												false,new NPCOption[]{
												new NPCOption("Why in this cave?", "The auras can use portals too, we're going somewhere we don't want them to find.", new String[]{
														"Why in this cave?","Why in this cave?",
														"Why in this cave?","Why in this cave?","Why in this cave?","Why in this cave?",
														"Why in this cave?","Why in this cave?"}, true, owner)
										},
										owner) }, owner) }, owner) },
				NPC.SPADE, NPC.SPADE, NPC.SIR_COBALT, true, new String[0]);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void act(NPCOption option) {
		owner.getPortals().add(
				new Portal(x + 100, y - 100, owner, "wizardTower",
						"normal", -1));
		options = new NPCOption[] {};
		cantExit = false;
	};

	@Override
	public String exitLine() {
		// TODO Auto-generated method stub
		return "Just walk into it? OK.";
	}

	@Override
	protected String getGreeting() {
		// TODO Auto-generated method stub
		return "Why are we in a cave?";
	}

	@Override
	protected String getFarewell() {
		// TODO Auto-generated method stub
		return "Walk into the portal, we're going to a safe place.";
	}

	@Override
	public boolean isChars() {
		return !hasTouched;
	}

	@Override
	public void animate() {
		super.animate();
		if (!hasTouched)
			for (NPC npc : owner.getNPCs()) {
				if (npc instanceof TutorialSirCobalt && npc.getBounds().intersects(getBounds())) {
					hasTouched = true;
					break;
				}
			}
	};

	@Override
	public boolean isObstacle() {
		// TODO Auto-generated method stub
		if (!hasTalked)
			return true;
		else
			return false;
	}
}

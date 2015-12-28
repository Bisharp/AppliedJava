package com.dig.www.npc;

import java.awt.Rectangle;

import com.dig.www.start.Board;

public class Tutorial1 extends InvisbleNormalOnceTouchNPC {
private boolean once;
	public Tutorial1(int x, int y, Board owner, String location) {
		super(
				x,
				y,
				owner,
				new String[] { "Just follow me. It isn't safe here. (+Click the black boxes| to talk)" },
				NPC.SIR_COBALT,
				location,
				new NPCOption[] { new NPCOption(
						"Where are we going?",
						"This land is bursting with dangers. We're going somewhere safe. (You can press the +X| key to exit)",
						new String[] { "Where are we going?",
								"Where are we going?", "Where are we going?",
								"Where are we going?", "Where are we going?",
								"Where are we going?", "Where are we going?",
								"Where are we going?" }, true, owner) },
				NPC.SPADE, NPC.SIR_COBALT, NPC.SPADE, true, new String[0]);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void act(NPCOption option) {
		if (option.question().equals("Could you be a little more specific?")) {
			options = new NPCOption[0];
		} else {
			cantExit = false;
			for(NPC npc:owner.getNPCs()){
				if(npc instanceof TutorialSirCobalt){
					((TutorialSirCobalt)npc).hasWaited();
					((TutorialSirCobalt)npc).setMovePointFirstWait(0,false);
				}
					
			}
			options = new NPCOption[1];
			options[0] = new NPCOption(
					"Could you be a little more specific?",
					"No friend, You wouldn't know where we are going. You'll find out when you get there. (Press the +X| key toexit)",
					new String[] { "Could you be a little more specific?",
							"Can you be a little more specific?",
							"Can you be a little more specific?",
							"Can you be a little more specific?",
							"Can you be a little more specific?",
							"Can you be a little more specific?",
							"Can you be a little more specific?",
							"Can you be a little more specific?" }, true, owner);
		}
	}

	@Override
	public String exitLine() {
		// TODO Auto-generated method stub
		return "Follow me. (Use the +arrow keys| to move)";
	}

	@Override
	protected String getGreeting() {
		// TODO Auto-generated method stub
		return "What do I do? (Press the +X| key to continue)";
	}

	@Override
	protected String getFarewell() {
		// TODO Auto-generated method stub
		return "OK, I'm ready.";
	}
	@Override
	public boolean isChars() {
		if(!once){
			once=true;
			return true;}
		else
		return false;
	}
}

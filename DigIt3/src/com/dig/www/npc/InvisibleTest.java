package com.dig.www.npc;

import com.dig.www.start.Board;

public class InvisibleTest extends InvisbleNormalOnceTouchNPC {

	public InvisibleTest(int x, int y, Board owner, String location) {
		super(x, y, owner, new String[] { "..." }, NPC.DIAMOND, location,
				new NPCOption[] { new NPCOption("(stay silent)", "...",
						new String[] { "Come on, just say anything. Anything?",
								"Come on, just say anything. Anything?",
								"Come on, just say anything. Anything?",
								"Come on, just say anything. Anything?",
								"Come on, just say anything. Anything?",
								"Come on, just say anything. Anything?",
								"Come on, just say anything. Anything?",
								"Come on, just say anything. Anything?" },
						true, new NPCOption[0], owner, NPC.CLUB, NPC.DIAMOND) }// new
																		// NPCOption[]{new
																		// NPCOption(q,
																		// a,
																		// qS,
																		// acts,
																		// newOptions,
																		// owner,
																		// charQuestion,
																		// charAnswer)}
				, NPC.CLUB, NPC.DIAMOND, NPC.CLUB, true,new String[]{NPC.CLUB,NPC.DIAMOND});
		// TODO Auto-generated constructor stub
	}

	@Override
	public void act(NPCOption option) {
		cantExit=false;
		NPCOption[] options2=new NPCOption[options.length-1];
		int amount=0;
		for(NPCOption op:options){
			if(!(option==op)&&amount<options2.length){
			options2[amount]=op;
		}
			
			}
		options=options2;
	}

	@Override
	public String exitLine() {
		// TODO Auto-generated method stub
		return "...";
	}

	protected String getGreeting() {
		return "Cain, do you +ever| talk? +Ever?|";
	}

	protected String getFarewell() {
		return "Fine then. Don't answer.";
	}
}

package com.dig.www.npc;

import javax.swing.JOptionPane;

import com.dig.www.character.GameCharacter;
import com.dig.www.start.Board;

public class CopyOfMacaroni extends QuestNPC {
	public CopyOfMacaroni(int x, int y, Board owner, String location, int id) {
		super(
				x,
				y,
				"images/npcs/map/stationary/macaroni.png",
				owner,
				new String[] { "Who are you?" },
				MACARONI,
				location,
				new NPCOption[] {
						new NPCOption(
								"I am your friendly neigborhood wizard!",
								"I am Super Macaroni Noodle Man! I *herve| a problem, can you hear me out?",
								new String[] {
										"I am your friendly neigborhood wizard!",
										"I am your friendly neigborhood wizard!",
										"I am your friendly neigborhood wizard!",
										"I am your friendly neigborhood wizard!",
										"I am your friendly neigborhood wizard!" },
								true,
								new NPCOption[] { new NPCOption(
										"Let's hear it..",
										"If you haven't noticed, we are in a BLANK factory?. If I stay I am sure the workers will *edam| me, but if I leave, they will surely catch me...",
										new String[] { "Fine.", "Fine.",
												"Fine.", "Fine.", "Fine." },
										false,
										new NPCOption[] {
												new NPCOption(
														"Where do we fit into this?",
														"In canada.",
														new String[] { "Fine.", "Fine.",
																"Fine.", "Fine.", "Fine." }, false,
														
														owner),
												new NPCOption(
														"Enough. You're just crazy.",
														"Oh... Maybe later!",
														new String[] {
																"I've heard enough. You're crazy.",
																"I've had enough of your nonsense!",
																"...",
																"I am sorry, but I think the workers are to nice to eat you.",
																"I don't think there is a problem my cheesy friend." },
														false, owner) }, owner) },
								owner), }, id);

	}

	@Override
	public void act(NPCOption option) {
		if (!questAccepted) {

			if (option.question().equals("I am your friendly neigborhood wizard!")) {
				owner.getData().registerQuest(this);
				questAccepted = true;
				setAcceptedVals(0);
			}

		}else if(!questCompleted){
			switch(owner.getData().getAcceptedPhase(this)){
			case 0:
				if(option.question().equals("OK. We will help you.")){
					owner.getData().setAcceptedPhase(this,1);
					setAcceptedVals(1);
				}break;
			}
		}

			
		}
	

	@Override
	public String exitLine() {
		if(questAccepted&&owner.getData().getAcceptedPhase(this)>1)
		return "Good-*brie|!";
		else
		return "Oh! Good-*brie|!";
	}
	@Override
	protected String getFarewell() {
		if(questAccepted)
			return super.getFarewell();
		else
		switch (owner.getCharacter().getType()) {
		case CLUB:
			return "I am going now.";
		case DIAMOND:
			return "...";
		case HEART:
			return "Um, I need to go, bye!";
		case SIR_COBALT:
			return "Uh... Until our paths cross again.";
		default:
			return "Um... Good-bye.";
		}
	}

	@Override
	public String getShowName() {
		return "Super Macaroni Noodle Man";
	}

	@Override
	protected void setCompletedVals() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void setAcceptedVals(int phase) {
		// TODO Auto-generated method stub
		System.out.println("phase:"+phase);
		if (phase == 1) {
			greetingDialogs = new String[1];
			greetingDialogs[0] = "*Teleme| you have ?????.";
			options = new NPCOption[1];
			options[0] = new NPCOption("Let's check our standings.",
					quest.getIncompleteLine(), new String[] { "?", "?", "...",
							"?", "?" }, true, owner);
		} else if (phase == 0) {
			greetingDialogs = new String[1];
			greetingDialogs[0] = "Oh, it's you. Will you hear the problem I *herve| now?";
			options = options[0].newOptions;
		}
	}

}

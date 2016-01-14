package com.dig.www.npc;

import java.awt.Rectangle;

import javax.swing.JOptionPane;

import com.dig.www.character.GameCharacter;
import com.dig.www.character.Items;
import com.dig.www.character.Macaroni;
import com.dig.www.start.Board;
import com.dig.www.util.Quest;

public class CopyOfMacaroni extends QuestNPC {
	private boolean once;
private boolean isWall=true;
private boolean exitingA;
	public CopyOfMacaroni(int x, int y, Board owner, String location, int id) {
		super(
				x,
				y,
				"images/npcs/map/stationary/macaroni.png",
				owner,
				new String[] { "Who are you?" },
				MACARONI,
				location,
				new NPCOption[] { new NPCOption(
						"I am your friendly neigborhood wizard!",
						"I am Super Macaroni Noodle Man! I *herve| a problem, can you hear me out?",
						new String[] {
								"I am your friendly neigborhood wizard!",
								"I am your friendly neigborhood wizard!",
								"I am your friendly neigborhood wizard!",
								"I am your friendly neigborhood wizard!",
								"I am your friendly neigborhood wizard!" },
						true,
						new NPCOption[] {
								new NPCOption(
										"Let's hear it.",
										"If you haven't noticed, we are in a BLANK factory?. If I stay I am sure the workers will *edam| me, but ifI leave, they will surely catch me...",
										new String[] { "OK. Let's hear it.",
												"Fine.", "...",
												"Sure. Let's hear it.",
												"Go ahead." },
										false,
										new NPCOption[] {
												new NPCOption(
														"Where do we fit into this?",
														"If you turn the power off, they will be distracted trying to fix it. Can you press the big red button to   shut it off? Please *anster| yes.",
														new String[] {
																"Where do we fit into this?.",
																"Is there anything to smash?",
																"...",
																"How can we help?",
																"Where does this put us?" },
														false,
														new NPCOption[] {
																new NPCOption(
																		"OK. We will help you.",
																		"Thanks",
																		new String[] {
																				"Sure",
																				"Sure",
																				"Sure",
																				"Sure",
																				"Sure" },
																		true,
																		owner),
																new NPCOption(
																		"No",
																		"Oh, maybe later!",
																		new String[] {
																				"No",
																				"No",
																				"No",
																				"No",
																				"No" },
																		false,
																		owner) },
														owner),
												new NPCOption(
														"Enough. You're just crazy.",
														"Oh... Maybe later!",
														new String[] {
																"I've heard enough. You're crazy.",
																"I've had enough of your nonsense!",
																"...",
																"I'm sorry, but I think the workers are to nice to eat you.",
																"I don't think there is a problem my cheesy friend." },
														false, owner) }, owner),
								new NPCOption(
										"I can't. Sorry.",
										"Oh... Maybe later!",
										new String[] {
												"I really can't. Sorry.",
												"No.",
												"...",
												"I'm really sorry, but I can't.",
												"I am afraid I cannot, friend." },
										owner) }, owner), }, id);
		specify(Items.DONUT, "hauntedTest", Quest.Quests.FETCH);
		// System.out.println(owner.getData().areas.get("LuigisMansion"));//.get(currentKey).getQuest(npc.id).setAppearPhase(setter);
		//
		// owner.getData().setAppearPhase(this,1);

	}

	@Override
	public void animate() {
		super.animate();
		if (!once) {
			owner.getData().setAppearPhase(this, 10);
			once = true;
		}
	}

	@Override
	public void act(NPCOption option) {
		if (!questAccepted) {

			if (option.question().equals(
					"I am your friendly neigborhood wizard!")) {
				owner.getData().registerQuest(this);
				questAccepted = true;
				setAcceptedVals(0);
			}

		} else if (!questCompleted) {
			switch (owner.getData().getAcceptedPhase(this)) {
			case 0:
				if (option.question().equals("OK. We will help you.")) {
					owner.getData().setAcceptedPhase(this, 1);
					setAcceptedVals(1);
				}
				break;
			case 2:
				if (option.question().equals("I've done it.")) {
						owner.getData().completeQuest(this);
						line = "Oh! Thank you so!";
						questCompleted = true;
						setCompletedVals();
					
				}
				break;
			}
		}else if(option.question().equals("Will you join us now?")){
			isWall=false;
			owner.getFriends().add(new Macaroni(x, y, owner, false));
		exitingA=true;
		exiting=true;
		}
	}
	
	@Override
	public void exit() {

		if (!exitingA || !iTalk) {
			super.exit();
			if (inDialogue && !iTalk && exitingA)
				act(BLANK);
		} else {
			end();
		}
	}
	@Override
	protected String getFarewell() {
	if(exitingA){
		return options[1].questionAsked();
	}
		else if (questAccepted)
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
		greetingDialogs = new String[4];
		greetingDialogs[0] = "Please do a *gouda| job!";
		greetingDialogs[1] = "I don't know anyone who could handle it *cheddar| than you!";
		greetingDialogs[2] = "Don't be *blue|, you can beat that *muenster|!";
		greetingDialogs[3] = "I *swiss| I could say this is *nacho| fight, but it kind of is...";
		options = new NPCOption[2];
		options[0] = new NPCOption("Why were you in there?",
				"*Curds| I was saving the day!", new String[] {
						"Why were you in there, anyway?",
						"How did you get yourself stuck?", "...",
						"How did you get in that frightful situation?",
						"How did you find yourself in that predicament?" },
				false, owner);
		options[1] = new NPCOption("Will you join us now?",
				"Sure! *Aura| we going now?", new String[] {
						"Will you join us now?", "Join us!", "...",
						"Can you join us?", "Care to join the cause?" }, true,
				owner);
	}
	protected String getGreeting() {
		switch (owner.getCharacter().getType()) {
		case CLUB:
			return "Hi. Do you have something I can break?";
		case DIAMOND:
			return "...";
		case HEART:
			return "Hi!";
		case SIR_COBALT:
			return "Greetings.";
		default:
			return "Hello.";
		}
	}
	@Override
	protected void setAcceptedVals(int phase) {
		// TODO Auto-generated method stub
		System.out.println("phase:" + phase);
		if (phase == 1) {
			greetingDialogs = new String[1];
			greetingDialogs[0] = "*Teleme| you have the Donut.";
			options = new NPCOption[1];
			options[0] = new NPCOption("I've done it.",
					"Either I'm seeing things *orda| power is still on. Can you look again?", new String[] {
							"I've done it.",
							"I've done it.", "...",
							"I've done it.",
							"I've done it." }, true, owner);
		} else if (phase == 0) {
			greetingDialogs = new String[1];
			greetingDialogs[0] = "Oh, it's you. Will you hear the problem I *herve| now?";
			options = options[0].newOptions;
		}
	}
	@Override
	public Rectangle getBounds(){
		if(isWall)
			return new Rectangle(x,y+10,80,90);
		else
		return new Rectangle();	
	}
	@Override
	public String exitLine() {
		if (exitingA)
			return "";
		else{
			if (questCompleted)
				return "Good-*brie|!";
			else
				return "Oh! Good-*brie|!";	
		}
	}
}

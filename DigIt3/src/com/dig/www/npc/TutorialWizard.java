package com.dig.www.npc;

import java.awt.Rectangle;

import javax.swing.ImageIcon;

import com.dig.www.character.GameCharacter;
import com.dig.www.start.Board;
import com.dig.www.util.ConditionEnteringMap;

public class TutorialWizard extends AnimatedMoveNPC implements ConditionEnteringMap {
	public int nextInt;
	private boolean readyForNext;

	public TutorialWizard(int x, int y, Board owner, String location) {
		super(x, y, "images/characters/wizard/", owner, new String[] { "Do you require my assistance?" }, NPC.WIZARD,
				location,
				new NPCOption[] { new NPCOption("How do we use these?",
						"You see that I've given you each different weapons. You each can do three things with them.",
						new String[] { "How do we use these?", "How do we use these?", "How do we use these?",
								"How do we use these?" },
						false,
						new NPCOption[] { new NPCOption("What am I supposed to do with this spade?",
								"You can hit enemies with the shovel, you can shoot with the bow and arrow I gave you, and you can also dig and fill pits with the shovel.",
								new String[] { "What am I supposed to do with this shovel? It's not a weapon.",
										"What am I supposed to do with this shovel? It's not a weapon.",
										"What am I supposed to do with this shovel? It's not a weapon.",
										"What am I supposed to do with this shovel? It's not a weapon.",
										"What am I supposed to do with this shovel? It's not a weapon." },
								true, new NPCOption[0], owner)

				}, owner) },
				new MovePoint[] { new MovePoint(x, y + 100, false), new MovePoint(x + 200, y + 100, false),
						new MovePoint(x + 300, y, false), new MovePoint(x + 1000, y - 300, GameCharacter.storyInt < 3),
						new MovePoint(x + 2150, y - 300, true),new MovePoint(x+2000,y+100, true),new MovePoint(x+2200, y+350, false) },
				0, 6);
		cantExit = true;
		if (GameCharacter.storyInt > 2) {
			next();
			currentOptions = options.clone();
		}
	}
	public void next() {
		readyForNext = false;
		nextInt++;
		cantExit = false;
		hasWaited();
		if (nextInt == 1) {
			options = new NPCOption[] { new NPCOption("What do I do?",
					"Next to the sign is a patch of grass. You can dig up and carry the dirt.(By pressing the +V Key| while     facing the patch of grass)",
					new String[] { "How do I get past?", "How do I get past?", "How do I get past?",
							"How do I get past?" },
					false,
					new NPCOption[] { new NPCOption("What do I do then?",
							"With the dirt you now have, fill in the pit that is blocking your way.(Press the +V Key| while you have    dirt and are facing a pit to fill it)",
							new String[] { "What do I do after that?", "What do I do after that?",
									"What do I do after that?", "What do I do after that?" },
							false, new NPCOption[0], owner) },
					owner) };
		} else if (nextInt == 2) {
			if (GameCharacter.storyInt == 3)
				GameCharacter.storyInt++;
			options = new NPCOption[] { new NPCOption("What am I supposed to do now?",
					"No, Carl can break through with the club I gave him.(Press the +SPACE Key| as Carl to use his club. Do it while facing a crystal to break it)",
					new String[] { "What am I supposed to do now? You can't expect me to tunnel under.",
							"What am I supposed to do now? You can't expect me to tunnel under.",
							"What am I supposed to do now? You can't expect me to tunnel under.",
							"What am I supposed to do now? You can't expect me to tunnel under.",
							"What am I supposed to do now? You can't expect me to tunnel under." },
					false,
					new NPCOption[] { new NPCOption("Carl + club = disaster", "Hey! I could do it just fine!(Press the +R Key|, +Click| switch, and then +Click| switch to put Carl in the   lead)",
							new String[] {
									"Yeah, I don't think giving Carl a club was a good idea. Giving him the lead might not be either.","Yeah, I don't think giving Carl a club was a good idea. Giving him the lead might not be either.","Yeah, I don't think giving Carl a club was a good idea. Giving him the lead might not be either.","Yeah, I don't think giving Carl a club was a good idea. Giving him the lead might not be either."},
							false, new NPCOption[0], owner, NPC.SPADE, NPC.CLUB, null, null) },
					owner) };
		}else if(nextInt==3){
			options=new NPCO
		} else {
			options = new NPCOption[0];
		}
		resetCurrentOptions();
	}

	@Override
	public void act(NPCOption option) {
		cantExit = false;
		readyForNext = true;
		options = new NPCOption[0];
	}

	@Override
	public String[] getDialog() {
		if (GameCharacter.storyInt == 2)
			return new String[] { "Here are the weapons!" };
		return super.getDialog();
	}

	@Override
	protected String getGreeting() {
		if (GameCharacter.storyInt == 2)
			return "I'm ready.";
			switch(owner.getCharacter().getType()){
			case CLUB:
				return "Hey weird wizard.";
			case DIAMOND:
				return "...";
			default:
		return "Hello again.";}
	}

	@Override
	protected String getFarewell() {
		if (GameCharacter.storyInt == 2)
			return "Thanks for the weapons. *I guess...|";
		else if(nextInt==2)
			return "I guess nothing bad can happen.";
		return "Thanks.";
	}

	@Override
	public String exitLine() {
		return (GameCharacter.storyInt == 2 ? "Follow me to the obstacle! " : "")
				+ "Ask me if you need any more help. Good luck!";
	}

	@Override
	public String getShowName() {
		return "The Wizard";
	}

	@Override
	protected void end() {
		if (GameCharacter.storyInt == 2) {
			GameCharacter.storyInt = 3;
			// hasWaited();
		}
		if (readyForNext) {
			next();
		}
		super.end();
	}

	@Override
	public boolean enter() {
		// TODO Auto-generated method stub
		int storyInt = GameCharacter.storyInt;
		// System.out.println("Wizard"+storyInt+(storyInt>=1&&storyInt<=2));
		return storyInt >= 2 && storyInt <= 3;
	}
}

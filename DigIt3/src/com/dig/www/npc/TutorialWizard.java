package com.dig.www.npc;

import java.awt.Rectangle;

import javax.swing.ImageIcon;

import com.dig.www.character.GameCharacter;
import com.dig.www.character.GameCharacter.Types;
import com.dig.www.start.Board;
import com.dig.www.util.ConditionEnteringMap;

public class TutorialWizard extends AnimatedMoveNPC implements ConditionEnteringMap {
	public int nextInt;
	private boolean readyForNext;
	boolean fiveOnce;
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
								true, new NPCOption[]{new NPCOption("That seems tiring.", "It won't be too bad. Plus, you could rest for a moment afterward.(The blue bar in the top left corner is your energy. The red is your health.)", new String[]{"That seems exhausting.","That seems exhausting.","That seems exhausting.","That seems exhausting."}, owner)}, owner)

				}, owner) },
				new MovePoint[] { new MovePoint(x, y + 100, false), new MovePoint(x + 200, y + 100, false),
						new MovePoint(x + 300, y, false), new MovePoint(x + 1000, y - 300, true),
						new MovePoint(x + 2150, y - 300, true),new MovePoint(x+2000,y+100, true),new MovePoint(x+2200, y+350, false),new MovePoint(x+1800, y+600, true),new MovePoint(x+1800,y+925,false)
						,new MovePoint(x+1300,y+1000,false),new MovePoint(x+500, y+1000, true),new MovePoint(x+500, y+1100, true)
						,new MovePoint(x-300, y+1100, false),new MovePoint(x-1200, y+900, false)},
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
					new String[] { "How do I get past?", "How do we get past?", "...",
							"How do we get past?" },
					false,
					new NPCOption[] { new NPCOption("What do I do then?",
							"With the dirt you now have, fill in the pit that is blocking your way.(Press the +V Key| while you have    dirt and are facing a pit to fill it)",
							new String[] { "What do I do after that?", "What then?",
									"...", "Oh, what after that?" },
							false, new NPCOption[0], owner) },
					owner) };
		} else if (nextInt == 2) {
			if (GameCharacter.storyInt == 3)
				GameCharacter.storyInt++;
			options = new NPCOption[] { new NPCOption("What am I supposed to do now?",
					"Carl can break through with the club I gave him.(Press the +SPACE Key| as Carl to use his club. Do it     while facing a crystal to break it)",
					new String[] { "What am I supposed to do now? You can't expect me to tunnel under.",
							"What now? You can't expect that useless Clark to tunnel under.",
							"...",
							"What's next?" },
					false,
					new NPCOption[] { new NPCOption("Carl + club = disaster", "Hey! I could do it just fine!(Press the +R Key|, +Click| switch, and then +Click| switch to put Carl in the  lead)",
							new String[] {
									"Yeah, I don't think giving Carl a club was a good idea. Giving him the lead might not be either.","Yeah, I don't think giving Carl a club was a good idea. Giving him the lead might not be either.","Yeah, I don't think giving Carl a club was a good idea. Giving him the lead might not be either.","Yeah, I don't think giving Carl a club was a good idea. Giving him the lead might not be either."},
							false, new NPCOption[0], owner, NPC.SPADE, NPC.CLUB, null, null) },
					owner) };
		}else if(nextInt==3){
			options=new NPCOption[]{new NPCOption("What now? ", "There are Tennis Buddies ahead, but Cain can block the balls.(Press the +SPACE Key| after switching to    Cain to block projectiles)", new String[]{"What now?","Now what?","...","Ooh! What's next?"}, false, new NPCOption[]{new NPCOption("Won't we get hurt if we follow him?", "You can wait here.(Press the +R Key|, +Click| order, and then +Click| wait to make everyone but the leader   wait.)", new String[]{"Won't we get hurt if we follow him?","What about us?","...","What do we do?"}, false,new NPCOption[]{ 
					new NPCOption("How will walking past the Tennis Buddies get him across this water?", "There is a hook past the Tennis Buddies. Cain can throw his shield to grapple to the hook.(Press the +C   Key| to throw Cain's shield. Aim it at the hook to grapple)", new String[]{"How will walking past the Tennis Buddies get him across this water?","He needs to get across the water.","...","Cool!... But how will he get across the water?"}, false, new NPCOption[]{
					new NPCOption("What about the rest of us?", "Cain can walk towards the bridge after he crosses the water to knock it down.(Then he can order the      others to \"follow me\")", new String[]{"What about the rest of us?","What about us?","...","What's after that?"}, false, new NPCOption[0], owner)}, owner)		
					}, owner)}, owner),new NPCOption("Someone just died. What do I do?", "Go back to the entrance of the tower. If you walk up to them, they will be revived.", new String[]{"Someone just died. What do I do?","Someone just got themself killed.","...","My friend just got killed! What can I do?"}, owner)};
		}else if(nextInt==4){
			options=new NPCOption[]{
					new NPCOption("How do we get past those?", "Destiny can stun enemies with her Aura Wand. The Aura Wand can also heal friends.(Press the +SPACE Key| as Destiny to use her Aura Wand)", new String[]{"How do we get past those monsters?","It doesn't seem like those can be killed.","...","How do we get past?"}, false, new NPCOption[0], owner),new NPCOption("Someone just died. What do I do?", "Go back to the entrance of the tower. If you walk up to them, they will be revived.", new String[]{"Someone just died. What do I do?","Someone just got themself killed.","...","My friend just got killed! What can I do?"}, owner)
			};
		}
		else if(nextInt==5){
			if(nextInt==5)
				cantExit=true;
			options=new NPCOption[]{
					new NPCOption("What's the obstacle?","There is another monster ahead, but this one can be killed.(In order to attack, the inside of the respective white boxes must be black. If any part is green, you cannot attack with that ability)", new String[]{"What is it?","Fine. What is it?","...","Ooh! What is it?"}, true, new NPCOption[0], owner)
			};
		}
		else if(nextInt==6){
			options=new NPCOption[]{new NPCOption("This door is locked.", "Yes, you can only enter after you unlock it.(Walk up to a portal or door to interact with it. Then +Click|  the buttons to perform actions)", new String[]{"This door is locked.","There is a lock on this door.","...","Oh no! This door is locked!"}, false,new NPCOption[]{
			new NPCOption("Where's the key?", "There is a key across the water. Cain can grapple across.", new String[]{"Where's the key?","Where is this key?","...","Where is the key?"},false,new NPCOption[]{
					new NPCOption("Is that it?", "Yep, just get the key to enter the gift shop.(Press the +X Key| while facing an object to interact with it. Then +Click| the buttons to perform actions)", new String[]{"Is that it? No monsters or anything?","Is that really it?","...","Is that everything?"}, owner)}, owner)	
			}, owner)};
		}
		else {
			options = new NPCOption[0];
		}
		resetCurrentOptions();
	}

	@Override
	public void act(NPCOption option) {
		if(nextInt==5&&!fiveOnce){
			cantExit=false;
			fiveOnce=true;
			options=new NPCOption[]{new NPCOption("How can Clark attack?", "Clark can hit with his shovel(+SPACE Key|), shoot with his bow(+C Key|), and dig with the shovel(+V Key|).", new String[]{"What can I do?","Can Clark do anything?","...","What can Clark do?"}, owner),
					new NPCOption("How can Carl attack?", "Carl can hit with his club(+SPACE Key|), throw a baseball(+C Key|), and toss then whack baseballs(+V Key|).",new String[]{"What can Carl do?","List all the things I can do.","...","What can Carl do?"}, owner),
					new NPCOption("How can Cain attack?", "Cain can block with his shield(+SPACE Key|), throw his shield(+C Key|)(He can also tug it with the +C Key|  if it gets stuck), and he can bash with his shield(+V Key|).", new String[]{"What can Cain do?","Cain must be able to do something.","...","What does Cain have?"}, owner),
					new NPCOption("How can Destiny attack?","Destiny can stun enemies and heal friends with her Aura Wand(+SPACE Key|), shoot from her Aura Wand(+C  Key|),and deploy a health dispenser(+V Key|).", new String[]{"What can Destiny do?","Can Destiny do anything significant?","...","Ooh! What can I do?"}, owner)		};
		}else{
		cantExit = false;
		readyForNext = true;
		options = new NPCOption[0];}
		
	}

	@Override
	public String[] getDialog() {
		if (GameCharacter.storyInt == 2)
			return new String[] { "Here are the weapons!" };
		else if(nextInt==5&&!fiveOnce)
			return new String[]{"Wait a minute. I need to tell you about the next obstacle."};
		return super.getDialog();
	}	@Override
	protected String getGreeting() {
		if (GameCharacter.storyInt == 2)
			return "I'm ready.";
			switch(owner.getCharacter().getType()){
			case CLUB:
				return "Hey weird wizard.";
			case DIAMOND:
				return "...";
			case HEART:
				return "Hello!";
			default:
		return "Hello again.";}
	}

	@Override
	protected String getFarewell() {
		if (GameCharacter.storyInt == 2)
			return "Thanks for the weapons. *I guess...|";
		else if(nextInt==2&&owner.getCharacter().getType()==Types.SPADE)
			return "I guess nothing bad can happen.";
		else{
			switch(owner.getCharacter().getType()){
			case CLUB:
				return "I'm done talking to you.";
			case DIAMOND:
				return "...";
			case HEART:
				return "Bye!";
			default:
			return "Thanks.";}
		}
		
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
			owner.addAction("added","images/weapons.png");
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
		return storyInt >= 2 && storyInt <= 4;
	}
}

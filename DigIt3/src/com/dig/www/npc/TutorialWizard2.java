package com.dig.www.npc;

import com.dig.www.character.GameCharacter;
import com.dig.www.start.Board;

public class TutorialWizard2 extends AnimatedMoveNPC{
private boolean readyForNext=false;
private int nextInt;
	public TutorialWizard2(int x, int y, Board owner, String location) {
		super(x, y, "images/characters/wizard/", owner, new String[]{"Hello! Do you require my assistance?"}, NPC.WIZARD, location, new NPCOption[]{
				new NPCOption("How do we get to Botanus?", "You need to collect Key Crystals and put them in this Dimensional Key.", new String[]{"How do we get to Botanus?","How do I get to Botanus?","...","How do we get to Botanus?"}, false, new NPCOption[]{new NPCOption("Is that a Key Crystal?", "Yes. There is another one across this pit. Grab it and continue!(KC is for Key Crystals)", new String[]{"Is that a Key Crystal?","Is that thing a Key Crystal?","...","Is that shiny thing a Key Crystal?"}, true, new NPCOption[]{}, owner)}, owner)}, new MovePoint[]{new MovePoint(x+200, y, true),new MovePoint(x+200, y-200, false),new MovePoint(x+500, y-200, false),new MovePoint(x+800, y+500, false),new MovePoint(x+1400, y+950, false),new MovePoint(x+2000, y+900, true),new MovePoint(x+2900, y-300, false),new MovePoint(x+2900, y-500, true)}, 0,6);
		
	}
	public void setCantExit(boolean set){
		cantExit=set;
	}
	public void next() {
		readyForNext = false;
		nextInt++;
		cantExit = false;
		hasWaited();
		if(nextInt==1){
			//options=new NPCOption[]{new NPCOption("How do we get to Botanus?", "You need to collect Key Crystals and put them in this Dimensional Key.", new String[]{"How do we get to Botanus?","How do I get to Botanus?","...","How do we get to Botanus?"}, false, new NPCOption[]{}, owner)};
		}else if(nextInt==2){
			options = new NPCOption[]{new NPCOption("What do I do?", "Get the special Key Crystal and then continue onward.", new String[]{"What do I do?","What do I do?","...","What do we do?"}, owner)};
		}else if(nextInt==3){
			options=new NPCOption[]{new NPCOption("That's a weird portal?", "That is a boss portal. You must use the Dimensional Key and Key Crystals to enter it and defeat Botanus.", new String[]{"That is a weird portal.","What's up with that portal.","...","Ooh! That portal looks different. I'm not sure I like it though."},false,  new NPCOption[]{new NPCOption("Is that a Key Crystal?", "Yes. That rare Key Crystal is worth five normal ones.", new String[]{"Is that a Key Crystal too?","Is that thing a Key Crystal?","...","Ooh! Is that thing a Key Crystal too?"}, false,new NPCOption[]{new NPCOption("I'm ready.", "Good! I'll give you a spell to make you invincible! Oh no... My wand is out of battery.", new String[]{"OK. I'm ready.","I'm ready to beat up this Botanus.","...","I'm ready!"}, true, owner)}, owner)}, owner)};
		}
		else {
			options = new NPCOption[0];
		}
		resetCurrentOptions();
	}
@Override
	protected String getFarewell() {
	if(nextInt==3)
		switch (owner.getCharacter().getType()) {
		case CLUB:
			return "Magic... *Right|...";
		case DIAMOND:
			return "...";
		case HEART:
			return "Oh no.";
		default:
			return "Um...";
		}
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
@Override
	protected String getGreeting() {
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
	public void act(NPCOption option) {
		if(GameCharacter.storyInt==5){
			GameCharacter.storyInt++;
			options=new NPCOption[]{new NPCOption("How do we get to Botanus?", "You need to collect Key Crystals and put them in this Dimensional Key.", new String[]{"How do we get to Botanus?","How do I get to Botanus?","...","How do we get to Botanus?"}, false, new NPCOption[]{}, owner)};}
		cantExit = false;
		//readyForNext = true;
		//options = new NPCOption[0];
		
	}
	@Override
	public String exitLine() {
		if(nextInt==3)
		return "I'm sure you'll do fine without it! Good luck!";
		return "Ask me if you need any more help. Good luck!";
	}

	@Override
	public String getShowName() {
		return "The Wizard";
	}

}

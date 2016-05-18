package com.dig.www.npc.betweenTimes;

import com.dig.www.character.GameCharacter;
import com.dig.www.character.Items;
import com.dig.www.start.Board;
import com.dig.www.npc.NPC;
import com.dig.www.npc.NPCOption;

public class FutureScientist extends NPC {

	public FutureScientist(int x, int y, Board owner, String location) {
		super(x, y, "images/npcs/map/stationary/futureScientist.png", owner, new String[] { "A good " + OldPeasant.getTime(owner) + " to you!" },
				"futureScientist", location, new NPCOption[] {
						new NPCOption("Who are you?", "Just a scientist. Nothing interesting, really.", new String[] { "Who are you?",
								"Your name. Now.", "...", "Hi there! What's your name?", "May I have your name?" }, owner),
						getSDialog(owner)
				// ,
				// new NPCOption(
				// "Where are you from?",
				// "I'm from the town just ahead. Be warned: last I checked, a wicked wizard had taken up residence there. That's why I'm standing here.",
				// new String[] {
				// "Where are you from? I don't see a town on-screen.",
				// "Where do you come from?", "...",
				// "So, where do you come from?", "From where do you hail?" },
				// false, owner)
				});
	}

	public void act(NPCOption d) {

		if (GameCharacter.getInventory().contains(Items.BLACK_ORB)) {
			GameCharacter.getInventory().decrementItem(Items.BLACK_ORB, 1);
			GameCharacter.getInventory().addItem(Items.KEYCRYSTAL, 5);
		} else if (GameCharacter.getInventory().contains(Items.URN)) {
			GameCharacter.getInventory().decrementItem(Items.URN, 1);
			GameCharacter.getInventory().addMoney(500);
		}
		refreshSDialog();
	}

	@Override
	public String exitLine() {
		return "See you around.";
	}

	@Override
	public String getShowName() {
		return "Future Scientist";
	}

	protected static String getQText() {
		if (GameCharacter.getInventory().contains(Items.BLACK_ORB))
			return "Give him the Sinister Black Orb of Ultimate Agony and Suffering";
		else if (GameCharacter.getInventory().contains(Items.URN))
			return "Give him the Ancient Urn";
		else
			return "OK";
	}

	protected static String getAText() {
		if (GameCharacter.getInventory().contains(Items.BLACK_ORB))
			return "Oh my! It's a Sinister Black Orb of Ultimate Agony and Suffering! These are very ancient and very dangerous, but hold incredible power. One moment... here you go! It's been purified into a powerful Key Crystal!";
		else if (GameCharacter.getInventory().contains(Items.URN))
			return "Interesting... it appears truly primeval at first glance, but even then it is still ancient. I will pay you for finding this!";
		else
			return "Yeah, not that interesting to most people, but I really like it!";
	}

	protected static String[] getDialogs() {
		if (GameCharacter.getInventory().contains(Items.BLACK_ORB))
			return new String[] { "If you work with ancient technology, what do you make of this creepy thing?",
					"Someone this weird deserves some*thing| this weird!", "...", "Sorry to trouble you, but do you know a good use for this thing?",
					"Do you know a way to put this sinister crystal to good use?" };
		else if (GameCharacter.getInventory().contains(Items.URN))
			return new String[] { "Do you know what this weird urn is?", "This is a fake, isn't it?", "...",
					"We found this ancient urn in a field. What do you think of it?",
					"We found this counterfeit urn in a field. Does it mean anything to you?" };
		else
			return new String[] { "To each his own.", "Weirdo.", "...", "That sounds like so much fun!", "I understand." };
	}
	
	protected static NPCOption getSDialog(Board owner) {
		return new NPCOption("What do you do?", "I work with ancient technology.", new String[] { "What do you do here?",
				"So, what keeps you busy in here?", "...", "What do you do for a living?",
				"So, what do you do with all this equipment?" }, false, new NPCOption[] { new NPCOption(getQText(), getAText(),
				getDialogs(), getActs(), owner)}, owner);
	}
	
	protected void refreshSDialog() {
		options[1] = getSDialog(owner);
	}

	protected static boolean getActs() {
		return GameCharacter.getInventory().contains(Items.BLACK_ORB) || GameCharacter.getInventory().contains(Items.URN);
	}
	
	@Override
	public void setLine() {
		super.setLine();
		refreshSDialog();
	}
}

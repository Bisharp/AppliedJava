package com.dig.www.npc.betweenTimes;

import com.dig.www.start.Board;
import com.dig.www.npc.NPC;
import com.dig.www.npc.NPCOption;
import com.dig.www.util.Time;

public class OldPeasant extends NPC {

	public OldPeasant(int x, int y, Board owner, String location) {
		super(
				x,
				y,
				"images/npcs/map/stationary/oldPeasant.png",
				owner,
				new String[] { "Hello traveler.", "A splendid " + getTime(owner) + " to you!" },
				"oldPeasant",
				location,
				new NPCOption[] {
						new NPCOption("Who are you?",
								"I'm just an old man. I used to live in a cave and give swords to random people, but the rent got too high.",
								new String[] { "Who are you?", "Your name. Now.", "...", "Hi there! What's your name?", "May I have your name?" },
								false, new NPCOption[] {
										new NPCOption("Uh... OK...", "Hey, a guy's got to make a living *somehow|.", new String[] { "Um... OK...?",
												"Weirdo.", "...", "That sounds like so much fun!", "Right." }, false, owner),
										new NPCOption("Can I have one?", "Sorry, I'm all out.",
												new String[] { "Could I have one? *Sheesh, why did I ask?|", "Gimme one.", "...",
														"Ooh, that sounds so cool, can i have one?",
														"Could you give me one? A sword could be very useful." }, owner) }, owner)
//						, new NPCOption(
//								"Where are you from?",
//								"I'm from the town just ahead. Be warned: last I checked, a wicked wizard had taken up residence there. That's why I'm standing here.",
//								new String[] { "Where are you from? I don't see a town on-screen.", "Where do you come from?", "...", "So, where do you come from?",
//										"From where do you hail?" }, false, owner)
						}
				);
	}

	@Override
	public String exitLine() {
		return "Be wary of the town down the road: last I checked, a strange monster had taken up residence there.";
	}

	@Override
	public String getShowName() {
		return "Old Peasant";
	}
	
	protected static String getTime(Board owner) {
		
		//return "time";
		final int t = owner.getGeneralTime();
		switch (t) {
			case  Time.SUNRISE:
				return "morning";
			case Time.DAY:
				return "day";
			case Time.SUNSET:
				return "evening";
			case Time.NIGHT:
				return "night";
			default:
				return "uh... time, I guess...";
		}
	}

	// @Override
	// public void act(NPCOption option) {
	// GameCharacter.getInventory().addItem(Types.SWORD, num);
	// }
}

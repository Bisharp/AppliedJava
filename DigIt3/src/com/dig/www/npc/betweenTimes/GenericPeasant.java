package com.dig.www.npc.betweenTimes;

import com.dig.www.start.Board;
import com.dig.www.npc.NPC;
import com.dig.www.npc.NPCOption;

public class GenericPeasant extends NPC {

	public GenericPeasant(int x, int y, Board owner, String location, String name) {
		super(x, y, "images/npcs/map/stationary/peasant" + name + ".png", owner, new String[] { "Hi there!", "Hello!", "How do you do?" }, "peasant" + name,
				location, new NPCOption[] { new NPCOption("Who are you?", "I'm a generic peasant!", new String[] { "Who are you?", "Your name. Now.", "...",
						"Hi there! What's your name?", "May I have your name?" }, false, owner) });
	}

	@Override
	public String exitLine() {
		return "Bye!";
	}

	@Override
	public String getShowName() {
		return "Peasant";
	}
}

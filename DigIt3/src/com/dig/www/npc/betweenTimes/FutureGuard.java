package com.dig.www.npc.betweenTimes;

import com.dig.www.start.Board;
import com.dig.www.npc.NPC;
import com.dig.www.npc.NPCOption;

public class FutureGuard extends NPC {

	public FutureGuard(int x, int y, Board owner, String location) {
		super(x, y, "images/npcs/map/stationary/futureGuard.png", owner, new String[] { "Hi there!", "Hello!", "How do you do?" }, "futureGuard",
				location, new NPCOption[] { new NPCOption("Who are you?", "I'm just a faceless guard.", new String[] { "Who are you?", "Your name. Now.", "...",
						"Hi there! What's your name?", "May I have your name?" }, false, owner) });
	}

	@Override
	public String exitLine() {
		return "Good-bye.";
	}

	@Override
	public String getShowName() {
		return "Futuristic Guard";
	}
}

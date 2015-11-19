package com.dig.www.npc;

import com.dig.www.start.Board;

public class Macaroni extends NPC {
	public Macaroni(int x, int y, Board owner, String location) {
		super(x, y,  "images/npcs/map/stationary/macaroni.png", owner, new String[] { "Please do a *gouda| job!", "I don't know anyone who could handle it *cheddar| than you!",
				"Don't be *blue|, you can beat that *muenster|!", "I *swiss| I could say this is *nacho| fight, but it kind of is..." },
				MACARONI, location, new NPCOption[] {

				});
	}

	@Override
	public String exitLine() {
		return "Good-*brie|!";
	}

	@Override
	public String getShowName() {
		return "Super Macaroni Noodle Man";
	}
}

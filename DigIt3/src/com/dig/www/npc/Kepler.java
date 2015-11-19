package com.dig.www.npc;

import com.dig.www.start.Board;

public class Kepler extends NPC {

	public Kepler(int x, int y, Board owner, String location) {
		super(x, y, "images/npcs/map/stationary/kepler.png", owner, new String[] { "Science is cool!", "There are enemies ahead. Defeat them with SCIENCE!", "I miss my robot...",
				"Can you believe that guy? He thinks magic is superior to science!" }, KEPLER, location, new NPCOption[] {
		});
	}

	@Override
	public String exitLine() {
		return "Continue onward-- for SCIENCE!";
	}

	@Override
	public String getShowName() {
		return "Kepler";
	}
}

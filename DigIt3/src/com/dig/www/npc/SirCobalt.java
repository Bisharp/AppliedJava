package com.dig.www.npc;

import com.dig.www.start.Board;

public class SirCobalt extends NPC {

	public SirCobalt(int x, int y, String loc, Board owner) {
		super(x, y, loc, owner, new String[]{"So you're here to fight Drusk? Seems like we have a common goal...", "This land is bursting with dangers. Good rule of thumb: If it's surrounded by a red glowy stuff, avoid it.", "You think this whole thing is ridiculous? Same.", "Control recap: Arrow keys move, Space attacks, C uses projectiles, and V is the special move. As you know, X is talk. Shift pauses, and Esc. exits. Space exits talking."}, SIR_COBALT);
	}
}

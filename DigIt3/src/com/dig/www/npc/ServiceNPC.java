package com.dig.www.npc;

import com.dig.www.start.Board;

public abstract class ServiceNPC extends NPC {

	public ServiceNPC(int x, int y, String loc, Board owner, String[] dialogs, String s) {
		super(x, y, loc, owner, dialogs, s);
		// TODO Auto-generated constructor stub
	}
	
	public abstract void service() ;
}

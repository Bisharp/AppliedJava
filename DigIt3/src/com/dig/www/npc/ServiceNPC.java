package com.dig.www.npc;

import com.dig.www.start.Board;

public abstract class ServiceNPC extends NPC {
	
	protected boolean rebuffed = false;
	protected String rebuffLine;

	public ServiceNPC(int x, int y, String loc, Board owner, String[] dialogs, String s, String rebuff) {
		super(x, y, loc, owner, dialogs, s);
		
		rebuffLine = rebuff;
		// TODO Auto-generated constructor stub
	}
	
	public abstract void service() ;

	public void unService() {
		rebuffed = true;
	}
	
	@Override
	public String getLine() {
		if (!rebuffed)
			return super.getLine();
		else
			return rebuffLine;
	}

	public void reset() {
		rebuffed = false;
	}
}

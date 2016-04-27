package com.dig.www.npc;

import com.dig.www.enemies.Enemy;
import com.dig.www.enemies.SpinnyBoss;
import com.dig.www.start.Board;

public class CagedMacaroni extends NPC{
private boolean open;
	public CagedMacaroni(int x, int y, Board owner, String location,boolean open) {
		super(x, y, null, owner, new String[0], NPC.MACARONI, location, new NPCOption[0]);
		// TODO Auto-generated constructor stub
	this.open=open;
	greetingDialogs=getD(open);
	}
	public String[] getD(boolean open){
		if(open)
			return new String[]{"You *aura| hero!"};
		else
			return new String[]{"*Teleme| you will free me."};
			
	}
	@Override
	public String exitLine() {
		// TODO Auto-generated method stub
		if(open)
			return "Yes! I've deactivated his shield!";
		else
			return "The key is left of the entrance.";
	}

	@Override
	public String getShowName() {
		// TODO Auto-generated method stub
	return "Super Macaroni Noodle Man";
	}
@Override
	protected String getGreeting() {
		if(open)
			return "You're free!";
		else
			return "I don't have a key.";
	}
@Override
	protected String getFarewell() {
		if(open)
			return "Ready?";
		else
			return "Where's the key?";
	}
@Override
	protected void end() {
	if(open)
		for(Enemy e:owner.getEnemies())
			if(e instanceof SpinnyBoss)
				((SpinnyBoss)e).deactivateShield();
		super.end();
	}
}

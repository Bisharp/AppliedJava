package com.dig.www.npc;

import java.awt.Rectangle;

import com.dig.www.character.GameCharacter.Types;
import com.dig.www.start.Board;

public class SirCobalt extends BlockerNPC {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean willJoin;

	public SirCobalt(int x, int y, Board owner, String location, boolean canBe) {
		super(x, y, "images/npcs/map/stationary/sirCobalt.png", owner, new String[] { "So you're here to fight Drusk? Seems like we have a common goal...",
				"This land is bursting with dangers. Good rule of thumb: If it's surrounded by a red glowy stuff, avoid it.",
				"You think this whole thing is ridiculous? Same." }, SIR_COBALT, location, new NPCOption[] { new NPCOption("Will you join us?",
				"I am waiting on some important information and cannot leave.", new String[] { "Will you join us?", "Come with us! Right now!",
						"...", "Will you come with us, *please?|", "Wait... I'm talking to *myself?| How is this possible?" }, true, owner) }, 0, -1);
		willJoin = canBe;
	}

	@Override
	public String byeLine() {
		return "Always be on your guard.";
	}
	
	@Override
	public String getLine() {
		return line;
	}

	@Override
	protected void checkWall() {
		
		if (willJoin && !iTalk) {
			isWall = false;
			owner.getFriends().add(new com.dig.www.character.SirCobalt(x, y, owner, false));
		owner.heyIaddedAFriendBack(owner.getFriends().get(owner.getFriends().size()-1),Types.SIR_COBALT.toString());
		}
	}

	@Override
	protected String moveLine() {
		return "I will.";
	}

	@Override
	public String getShowName() {
		return "Sir Cobalt";
	}
	@Override
	public Rectangle getBounds(){
		if(isWall)
		return new Rectangle(x+20,y,60,100);
		else
		return new Rectangle();	
	}
}

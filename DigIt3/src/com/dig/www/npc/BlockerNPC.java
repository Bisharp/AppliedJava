package com.dig.www.npc;

import java.awt.Rectangle;

import com.dig.www.character.Items;
import com.dig.www.start.Board;

public abstract class BlockerNPC extends NPC {

	protected final int value;
	protected boolean isWall = true;
	protected boolean acts = false;
	protected boolean initiatedAct = false;

	public BlockerNPC(int x, int y, String loc, Board owner, String[] dialogs, String s, String location, NPCOption[] o, int value) {
		super(x, y, loc, owner, dialogs, s, location, o);

		this.value = value;
	}

	public int getValue() {
		return value;
	}

	@Override
	public Rectangle getBounds() {
		if (isWall)
			return super.getBounds();
		else
			return new Rectangle();
	}

	@Override
	public void act(NPCOption o) {

		acts = !acts;
		initiatedAct = true;

		if (!acts) {
			checkWall();
			exiting = true;
			line = isWall ? options[0].answer() : moveLine();
		}
	}
	
	@Override
	public String getCharLine() {
		if (!initiatedAct)
			return super.getCharLine();
		else
			return options[index].questionAsked();
	}

	protected abstract void checkWall();

	protected abstract String moveLine();

	@Override
	public void exit() {
		super.exit();
		if (inDialogue && !iTalk && acts)
			act(BLANK);
	}
	
	@Override
	public void setLine() {
		super.setLine();
		initiatedAct = false;
	}
}
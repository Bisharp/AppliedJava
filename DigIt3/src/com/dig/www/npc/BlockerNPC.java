package com.dig.www.npc;

import java.awt.Rectangle;

import com.dig.www.character.Items;
import com.dig.www.start.Board;

public abstract class BlockerNPC extends NPC {

	protected final int value;
	protected boolean isWall = true;
	protected boolean acts = false;
	protected boolean initiatedAct = false;
	public final int id;

	public BlockerNPC(int x, int y, String loc, Board owner, String[] dialogs, String s, String location, NPCOption[] o, int value, int id) {
		super(x, y, loc, owner, dialogs, s, location, o);

		this.value = value;
		this.id = id;
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
			line = isWall ? currentOptions[0].answer() : moveLine();
			if (!isWall)
				owner.getData().clearBlocker(id);
		}
	}

	@Override
	public String getCharLine() {
		if (!initiatedAct)
			return super.getCharLine();
		else if (index == -1 && !exiting) {
			return getGreeting();
		} else if (exiting)
			return getFarewell();
		else
			return currentOptions[index].questionAsked();
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
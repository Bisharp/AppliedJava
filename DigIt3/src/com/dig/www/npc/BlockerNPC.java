package com.dig.www.npc;

import java.awt.Rectangle;

import com.dig.www.character.Items;
import com.dig.www.start.Board;

public abstract class BlockerNPC extends NPC {

	protected final int value;
	protected boolean isWall = true;
	protected boolean acts = false;
	public final int id;

	protected String checkDialog;

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

		acts = true;

		if (acts) {
			checkWall();
			exiting = true;
			line = isWall ? currentOptions[0].answer() : moveLine();
			if (!isWall)
				owner.getData().clearBlocker(id);
		}
	}

	@Override
	public String getCharLine() {
		if (!acts)
			return super.getCharLine();
		else
			return checkDialog;
	}

	protected abstract void checkWall();

	protected abstract String moveLine();

	protected abstract String byeLine();

	@Override
	public void exit() {

		if (!acts || !iTalk) {
			super.exit();
			if (inDialogue && !iTalk && acts)
				act(BLANK);
		} else {
			end();
		}
	}

	@Override
	public void setLine() {
		super.setLine();
		acts = false;
		checkDialog = options[0].questionAsked();
	}

	@Override
	public String exitLine() {
		if (acts)
			return "";
		else
			return byeLine();
	}
}
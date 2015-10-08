package com.dig.www.npc;

import java.awt.Rectangle;

import com.dig.www.start.Board;

public abstract class BlockerNPC extends ServiceNPC {

	protected final int value;
	protected boolean isWall = true;

	public static final String COLLECTIBLE = "Golden Troll Face";

	public BlockerNPC(int x, int y, String loc, Board owner, String[] dialogs, String s, int value, String rebuffLine) {
		super(x, y, loc, owner, dialogs, s, rebuffLine);

		this.value = value;
		// TODO Auto-generated constructor stub
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
}
package com.dig.www.npc;

import java.awt.Rectangle;

import com.dig.www.start.Board;

public abstract class BlockerNPC extends ServiceNPC {
	
	protected final int value;
	protected final String OK_Line;
	protected boolean isWall;
	
	public static final String COLLECTIBLE = "Golden Troll Face";

	public BlockerNPC(int x, int y, String loc, Board owner, String[] dialogs, String s, int value, String rebuffLine, String OK_Line) {
		super(x, y, loc, owner, dialogs, s, rebuffLine);
		
		this.value = value;
		this.OK_Line = OK_Line;
		// TODO Auto-generated constructor stub
	}
	
	
	public int getValue()  {
		return value;
	}
	
	@Override
	public String getLine() {
		
		if (!isWall)
			return OK_Line;
		else
			return super.getLine();
	}
	
	@Override
	public Rectangle getBounds() {
		if (isWall)
			return super.getBounds();
		else
			return new Rectangle();
	}
}
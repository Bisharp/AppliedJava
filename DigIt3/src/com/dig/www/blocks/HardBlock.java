package com.dig.www.blocks;

import com.dig.www.start.Board;
import com.dig.www.util.Statics;

public class HardBlock extends Block {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HardBlock(int x, int y, String loc, Board owner, Blocks block) {
		super(x, y, loc, owner, block);
		// TODO Auto-generated constructor stub
	}

	public void setType(Blocks type) {
		Statics.playSound(owner, "blocks/hard.wav");
	}
}

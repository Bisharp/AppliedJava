package com.dig.www.MultiPlayer.State;

import java.io.Serializable;

import com.dig.www.blocks.Block;

public class BlockState extends SpriteState implements Serializable{
Block.Blocks b;
private boolean visible;
	public BlockState(int x, int y,Block.Blocks b,boolean visble) {
		super(x, y);
		this.visible=visble;
		this.b=b;
		// TODO Auto-generated constructor stub
	}
	public boolean getInv(){
		return visible;
	}
public Block.Blocks getB(){
	return b;
}
}

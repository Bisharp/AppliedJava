package com.dig.www.blocks;

import com.dig.www.start.Board;


public class EnemyBlock extends Block {
	
	
	private char enemyType;

	public EnemyBlock(int x, int y, String loc, Board owner, char c) {
		super(x, y, loc, owner, Blocks.GROUND);
		
		enemyType = c;
		// TODO Auto-generated constructor stub
	}

	public char getEnemyType() {
		return enemyType;
	}
}

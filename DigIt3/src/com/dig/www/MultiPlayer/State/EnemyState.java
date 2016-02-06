package com.dig.www.MultiPlayer.State;

import java.io.Serializable;

public class EnemyState extends SpriteState implements Serializable{
	protected int health;
	public EnemyState(int x, int y,int health) {
		super(x, y);
		this.health=health;
	}
	public int getHealth(){
		return health;
	}
}

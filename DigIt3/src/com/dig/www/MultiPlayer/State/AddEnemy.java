package com.dig.www.MultiPlayer.State;

import com.dig.www.enemies.Enemy;

public class AddEnemy extends ActionState{
protected Enemy enemy;
	public AddEnemy(Enemy enemy) {
		super(ActionType.ADDEN);
		this.enemy=enemy.getClone();
		this.enemy.setOwner(null);
	}
public Enemy getEnemy(){
	return enemy;
}
}

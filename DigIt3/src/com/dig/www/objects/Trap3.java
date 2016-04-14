package com.dig.www.objects;

import com.dig.www.enemies.ExplosionLightningSpawn;
import com.dig.www.enemies.SideToPlayer;
import com.dig.www.start.Board;

public class Trap3 extends SensorObject {

	public Trap3(int x, int y, Board owner) {
		super(x, y, owner);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
owner.getEnemies().add(new ExplosionLightningSpawn(x+400,y-288,owner));
	}

}

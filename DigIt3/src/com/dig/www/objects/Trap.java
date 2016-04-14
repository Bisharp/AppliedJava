package com.dig.www.objects;

import com.dig.www.enemies.SideToPlayer;
import com.dig.www.start.Board;

public class Trap extends SensorObject {

	public Trap(int x, int y, Board owner) {
		super(x, y, owner);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		owner.getEnemies().add(new SideToPlayer(x-700, y-300, "images/effects/fire.gif", owner, true, -10));
		((SideToPlayer) owner.getEnemies().get(owner.getEnemies().size()-1)).setTurning(false);
		owner.getEnemies().add(new SideToPlayer(x-700, y-200, "images/effects/fire.gif", owner, true, -10));
		((SideToPlayer) owner.getEnemies().get(owner.getEnemies().size()-1)).setTurning(false);
		owner.getEnemies().add(new SideToPlayer(x-700, y-100, "images/effects/fire.gif", owner, true, -10));
		((SideToPlayer) owner.getEnemies().get(owner.getEnemies().size()-1)).setTurning(false);
owner.getEnemies().add(new SideToPlayer(x-700, y, "images/effects/fire.gif", owner, true, -10));
((SideToPlayer) owner.getEnemies().get(owner.getEnemies().size()-1)).setTurning(false);
owner.getEnemies().add(new SideToPlayer(x-700, y+100, "images/effects/fire.gif", owner, true, -10));
((SideToPlayer) owner.getEnemies().get(owner.getEnemies().size()-1)).setTurning(false);
	}

}

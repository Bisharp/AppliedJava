package com.dig.www.objects;

import com.dig.www.enemies.SideToPlayer;
import com.dig.www.start.Board;

public class Trap2 extends SensorObject {

	public Trap2(int x, int y, Board owner) {
		super(x, y, owner);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		owner.getEnemies().add(new SideToPlayer(x-100, y-700-288, "images/effects/lightning.gif", owner, false, -10));
		((SideToPlayer) owner.getEnemies().get(owner.getEnemies().size()-1)).setTurning(false);
		owner.getEnemies().add(new SideToPlayer(x, y-700-288, "images/effects/lightning.gif", owner, false, -10));
		((SideToPlayer) owner.getEnemies().get(owner.getEnemies().size()-1)).setTurning(false);
		owner.getEnemies().add(new SideToPlayer(x+100, y-700-288, "images/effects/lightning.gif", owner,false, -10));
		((SideToPlayer) owner.getEnemies().get(owner.getEnemies().size()-1)).setTurning(false);
owner.getEnemies().add(new SideToPlayer(x+200, y-700-288, "images/effects/lightning.gif", owner, false, -10));
((SideToPlayer) owner.getEnemies().get(owner.getEnemies().size()-1)).setTurning(false);
owner.getEnemies().add(new SideToPlayer(x+300, y-700-288, "images/effects/lightning.gif", owner, false, -10));
((SideToPlayer) owner.getEnemies().get(owner.getEnemies().size()-1)).setTurning(false);
	}

}

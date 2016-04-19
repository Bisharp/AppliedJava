package com.dig.www.objects;

import com.dig.www.enemies.Boss;
import com.dig.www.start.Board;

public class BossActivator extends SensorObject{

	public BossActivator(int x, int y, Board owner) {
		super(x, y, owner);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		for(int c=0;c<owner.getEnemies().size();c++)
			if(owner.getEnemies().get(c) instanceof Boss)
				((Boss)owner.getEnemies().get(c)).activate();
				
		
	}

}

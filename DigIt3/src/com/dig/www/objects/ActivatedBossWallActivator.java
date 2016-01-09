package com.dig.www.objects;

import com.dig.www.start.Board;

public class ActivatedBossWallActivator extends SensorObject{

	public ActivatedBossWallActivator(int x, int y, Board owner) {
		super(x, y, owner);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		for(Objects o:owner.getObjects())
			if(o instanceof ActivatedBossWall)
				((ActivatedBossWall) o).activate();
	}

}

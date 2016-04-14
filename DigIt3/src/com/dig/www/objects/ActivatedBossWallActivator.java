package com.dig.www.objects;

import java.awt.Point;

import com.dig.www.character.GameCharacter;
import com.dig.www.start.Board;

public class ActivatedBossWallActivator extends SensorObject{
	public ActivatedBossWallActivator(int x, int y, Board owner) {
		super(x, y, owner);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void action() {
		
		// TODO Auto-generated method stub
		for(Objects o:owner.getObjects()){
			if(o instanceof ActivatedBossWall)
				((ActivatedBossWall) o).activate();}
		Point p=owner.getCharPoint();
	for(GameCharacter chara:owner.getFriends()){
		if(!chara.isDead()){
		chara.setX(p.x);
		chara.setY(p.y);}}
	}

}

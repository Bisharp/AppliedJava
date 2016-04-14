package com.dig.www.blocks;

import com.dig.www.start.Board;

public class SpecialDoor extends Door{

	public SpecialDoor(int x, int y, Board owner, String area, String type, String doorType,int spawnNum,boolean locked) {
		super(x, y, owner, area, type, doorType,spawnNum,locked);
		// TODO Auto-generated constructor stub
		path="images/portals/" + type+"/";
		image=newImage(path+"c.png");
	}

}

package com.dig.www.blocks;

import com.dig.www.start.Board;

public class SpecialDoor extends Door{

	public SpecialDoor(int x, int y, Board owner, String area,
			int collectibles, String type, String doorType) {
		super(x, y, owner, area, collectibles, type, doorType);
		// TODO Auto-generated constructor stub
		path="images/portals/" + type+"/";
		image=newImage(path+"c.png");
	}

}

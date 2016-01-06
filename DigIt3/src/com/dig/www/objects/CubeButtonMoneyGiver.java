package com.dig.www.objects;

import com.dig.www.character.GameCharacter;
import com.dig.www.start.Board;

public class CubeButtonMoneyGiver extends CubeButton{
	public CubeButtonMoneyGiver(int x, int y, Board owner) {
		super(x, y, "images/objects/buttons/default/", owner,true);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void action() {
		if(acts){
		GameCharacter.getInventory().addMoney(1000);
		acts=false;}
	}

}

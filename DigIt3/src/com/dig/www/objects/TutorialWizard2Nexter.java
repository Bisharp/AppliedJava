package com.dig.www.objects;

import com.dig.www.character.GameCharacter;
import com.dig.www.character.Items;
import com.dig.www.npc.TutorialWizard2;
import com.dig.www.start.Board;

public class TutorialWizard2Nexter extends SensorObject{
private int num;
	public TutorialWizard2Nexter(int x, int y, String wall, Board owner) {
		super(x, y, true, owner);
		int num=Integer.parseInt(wall);
		this.num=num;
	switch(num){
	case 0:
		width=300;
		height=100;
		break;
	case 1:
		width=600;
		height=100;
	}
	}

	@Override
	public void action() {
		for(int c=0;c<owner.getNPCs().size();c++){//worse than Java
			if(owner.getNPCs().get(c) instanceof TutorialWizard2){
				TutorialWizard2 npc =(TutorialWizard2) owner.getNPCs().get(c);
				npc.next();
				npc.setCantExit(true);
				owner.talk(npc);
				num=250;
				break;
			}
		}
	}
	public void collidePlayer(int playerNum){
		if(!isWall())
		super.collidePlayer(playerNum);
		else{
			
		if (playerNum == -1) {
				owner.getCharacter().collision(this, false);

		} else {
				owner.getFriends().get(playerNum).collision(this, false);

		}}
	}
@Override
	public boolean isWall() {
		return num==1&&(!GameCharacter.getInventory().contains(Items.KEYCRYSTAL)||GameCharacter.getInventory().getItemNum(Items.KEYCRYSTAL)<5);
	}
}

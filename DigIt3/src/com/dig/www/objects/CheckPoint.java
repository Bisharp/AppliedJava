package com.dig.www.objects;

import com.dig.www.start.Board;

public class CheckPoint extends Objects{
private int spawnNum;
private static final String onLoc="images/objects/checkpoints/tempOn.png";
private static final String offLoc="images/objects/checkpoints/tempOff.png";
	public CheckPoint(int x, int y, Board owner,int spawnNum) {
		super(x, y, offLoc, false, owner);
		this.spawnNum=spawnNum;
		// TODO Auto-generated constructor stub
	}
@Override
public void collidePlayer(int playerNum){
	if(playerNum==-1){
		if(owner.getSpawnNum()!=spawnNum){
			//set checkpoint
		owner.save(spawnNum);	
		}
	}
}
@Override
public void animate(){
	super.animate();
	if(owner.getSpawnNum()==spawnNum){
		image=newImage(onLoc);
		shadow=newShadow(onLoc);
		}
	else{
		image=newImage(offLoc);
		shadow=newShadow(offLoc);
		}
}
}

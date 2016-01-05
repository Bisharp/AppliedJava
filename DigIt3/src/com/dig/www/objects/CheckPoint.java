package com.dig.www.objects;

import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.dig.www.character.Items;
import com.dig.www.start.Board;
import com.dig.www.start.DigIt;

public class CheckPoint extends Objects{
private int spawnNum;
private static final String onLoc="images/objects/checkpoints/tempOn.png";
private static final String offLoc="images/objects/checkpoints/tempOff.png";
	public CheckPoint(int x, int y, Board owner,int spawnNum) {
		super(x, y, offLoc, false, owner,"update");
		this.spawnNum=spawnNum;
		// TODO Auto-generated constructor stub
	}
@Override
public void collidePlayer(int playerNum){
	if(playerNum==-1){
		if(owner.getSpawnNum()!=spawnNum){
			//set checkpoint
		owner.setSpawnLoc(new Point(x,y));
		owner.save(spawnNum);	
		}
	}
}
public int getSpawnNum(){
	return spawnNum;
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
@Override
public boolean interact(){
	String[]options={"Leave","Save"};
	return JOptionPane.showOptionDialog(owner,"Do you want to save?", DigIt.NAME
			+ " Item Description",JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
			new ImageIcon(image),options,"Save"
			)==1;
}
}

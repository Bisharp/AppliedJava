package com.dig.www.objects;

import java.awt.Image;

import javax.swing.ImageIcon;

import com.dig.www.start.Board;

public class ActivatedBossWall extends BossBlock{
protected boolean activated;
protected Image actImage;
protected Image actShadow;
	public ActivatedBossWall(int x, int y, Board owner) {
		super(x, y, owner);
		// TODO Auto-generated constructor stub
		actImage=image;
		actShadow=shadow;
		image=new ImageIcon().getImage();
		shadow=new ImageIcon().getImage();
	}
	
	public void activate(){
		activated=true;
		image=actImage;
		shadow=actShadow;
	}
	@Override
	public void collidePlayer(int i){
		if(activated)
			super.collidePlayer(i);
			
	}
	@Override
	public boolean isWall(){
		if(activated)
			return super.isWall();
		else
			return false;
	}
	@Override
public boolean interact(){
	if(activated)
		return super.interact();
	else
		return false;
}
}

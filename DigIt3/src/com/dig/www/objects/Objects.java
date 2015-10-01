package com.dig.www.objects;

import java.awt.Graphics2D;
import java.awt.Image;

import com.dig.www.start.Board;
import com.dig.www.util.Sprite;

public  class Objects extends Sprite{
protected boolean wall;
	public Objects(int x, int y, String loc,boolean wall) {
		super(x, y, loc);
		this.setWall(wall);
		// TODO Auto-generated constructor stub
	}
	public Objects(int x, int y, String loc,boolean wall,Board owner) {
		super(x, y, loc,owner);
		// TODO Auto-generated constructor stub
		this.setWall(wall);
	}
	@Override
	public void animate() {
		// TODO Auto-generated method stub
		basicAnimate();
	}
	@Override
	public void draw(Graphics2D g2d) {
		// TODO Auto-generated method stub
		g2d.drawImage(image, x, y, owner);
	}
	public boolean isWall() {
		return wall;
	}
	public void setWall(boolean wall) {
		this.wall = wall;
	}

	public void collidePlayer(int playerNum){
		if(loc.equals("images/objects/Leaves")){
			loc="images/objects/LeavesSc";
			image=newImage(loc);
		}
		if(playerNum==-1){
			if(wall)
				owner.getCharacter().collision(getMidX(), getMidY(), false);
				
			
		}
		else{
			if(wall)
				owner.getFriends().get(playerNum).collision(getMidX(), getMidY(), false);
				
		}
	}
}

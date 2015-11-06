package com.dig.www.enemies;

import java.awt.Image;

import com.dig.www.start.Board;

public class CycleExplosion extends Explosion{
private int cycleNum=0;
private int cycleTim=10;
private int numItemsInFolder;
	public CycleExplosion(int x, int y, String loc, Board owner, int damage,int numItemsInFolder) {
		super(x, y, loc, owner, damage);
		this.numItemsInFolder=numItemsInFolder;
		boomTimer=100;
		image=newImage();
		// TODO Auto-generated constructor stub
	}
	@Override
	public void animate(){
		super.animate();
		if(cycleTim<=0){
			cycleTim=10;
			cycleNum++;
			if(cycleNum>=numItemsInFolder){
				cycleNum=0;
			}
			image=newImage();
		}else
			cycleTim--;
	}
	public Image newImage() {
		
		return super.newImage(loc+"/" + cycleNum+".png");
	}
	

}

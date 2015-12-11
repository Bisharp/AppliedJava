package com.dig.www.enemies;

import java.awt.Image;

import com.dig.www.start.Board;
import com.dig.www.util.Statics;

public class CycleExplosion extends Explosion{
private int cycleNum=0;
private int cycleTim=10;
private int numItemsInFolder;
	public CycleExplosion(int x, int y, String loc, Board owner, int damage,int numItemsInFolder,int timer) {
		super(x, y, loc+"/", owner, damage);
		this.numItemsInFolder=numItemsInFolder;
		boomTimer=timer;
		this.loc=loc;
		// TODO Auto-generated constructor stub
	}
	@Override
	public void animate(){
		basicAnimate();
		if(cycleTim<=0){
			cycleTim=10;
			cycleNum++;
			if(cycleNum>=numItemsInFolder){
				cycleNum=0;
			}
			image=newImage();
		}else
			cycleTim--;
		
		boomTimer--;
		if(boomTimer<0)
			alive = false;
			
	}
	public Image newImage() {
		
		return Statics.newImage(loc +"/"+ cycleNum+".png");
	}
	

}

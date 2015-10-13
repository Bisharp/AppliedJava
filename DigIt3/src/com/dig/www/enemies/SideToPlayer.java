package com.dig.www.enemies;

import com.dig.www.start.Board;

public class SideToPlayer extends Enemy{
boolean xAxis=true;
int speed=1;
	public SideToPlayer(int x, int y, String loc, Board owner, boolean xAxis,int health) {
		super(x, y, loc, owner, true, health);
		this.xAxis=xAxis;
		this.damage=Integer.MAX_VALUE;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void animate() {
		// TODO Auto-generated method stub
		basicAnimate();
		if(xAxis){
			if(x-speed>owner.getCharacterX()){
				x-=speed;
			}
			else if(x+speed<owner.getCharacterX()){
				x+=speed;
			}
		}
		else{
			if(y-speed>owner.getCharacterY()){
				y-=speed;
			}
			else if(y+speed<owner.getCharacterY()){
				y+=speed;
			}
		}
	}
	@Override
	public void turnAround(int wallX, int wallY){
		
	}

}

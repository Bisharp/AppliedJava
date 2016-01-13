package com.dig.www.games.Jump;

public class JumpObject {
private int type;
private int x;
private int y;
private int flap=0;
private boolean dir=true;
	public JumpObject() {
	type=(int)(Math.random()*5);
	x=610;
	y=(int)(Math.random()*31);
	}
	public void mx() {
		// TODO Auto-generated method stub
		x-=3;
		if(dir){
			flap++;
		}else{
			flap--;
		}
		if(flap>7){
			dir=false;
		}
		if(flap<-7){
			dir=true;
		}
	}
	public int getX() {
		// TODO Auto-generated method stub
		return x;
	}
	public int getType() {
		// TODO Auto-generated method stub
		return type;
	}
	public int getY(){
		return y;
	}
	public int getFlap() {
		// TODO Auto-generated method stub
		return flap;
	}

}

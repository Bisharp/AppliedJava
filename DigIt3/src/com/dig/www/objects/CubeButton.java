package com.dig.www.objects;

import com.dig.www.start.Board;

public abstract class CubeButton extends Objects{
protected boolean acts;
	public CubeButton(int x, int y, String loc, Board owner,boolean acts) {
		super(x, y, loc+"n.png", false, owner, "CubeButton");
		// TODO Auto-generated constructor stub
		this.acts=acts;
		this.loc=loc;
	}
public void animate(){
	super.animate();
	boolean pushed=false;
	for(Objects o:owner.getObjects()){
		if(o instanceof PushCube&&o.getBounds().intersects(getBounds())){
			action();
			pushed=true;}
	}
	if(pushed){
		image=newImage(loc+"p.png");
		shadow=newShadow(loc+"p.png");
	}else{
		image=newImage(loc+"n.png");
		shadow=newShadow(loc+"n.png");}
}
public abstract void action();
}
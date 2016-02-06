package com.dig.www.character;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;

import com.dig.www.objects.HookObject;
import com.dig.www.start.Board;
import com.dig.www.util.Statics;

public class Shield extends FProjectile{
protected boolean harming=true;
protected boolean moving=true;
protected boolean collideWithHook;
Point lastP;
protected ArrayList<HookObject>hooks=new ArrayList<HookObject>();
	public Shield(double dir, int x, int y, int speed, GameCharacter maker,
			 Board owner) {
		super(dir, x, y, speed, maker, "images/characters/projectiles/diamond.png", owner, Moves.CHAIN);
		// TODO Auto-generated constructor stub
		for(int c=0;c<owner.getObjects().size();c++){
			if(owner.getObjects().get(c) instanceof HookObject)
				hooks.add((HookObject)owner.getObjects().get(c));
		}
	}
	public void animate() {

		basicAnimate();
		if(harming){
		x += Math.cos((double) Math.toRadians((double) getD())) * getSpeed();
		y += Math.sin((double) Math.toRadians((double) getD())) * getSpeed();}
		
		if(harming){
		onScreen=getBounds().intersects(new Rectangle(getMaker().getX()-Statics.BOARD_WIDTH/2,getMaker().getY()-Statics.BOARD_HEIGHT/2, Statics.BOARD_WIDTH, Statics.BOARD_HEIGHT));
		harming=onScreen;}
		
		onScreen=true;
		if(!harming){
			if(moving){
			setD(Statics.pointTowards(new Point(x,y), new Point(getMaker().getX(),getMaker().getY())));
			lastP=new Point(x,y);
			x += Math.cos((double) Math.toRadians((double) getD())) * getSpeed();
			y += Math.sin((double) Math.toRadians((double) getD())) * getSpeed();
			
			}
			if(collideWithHook){
				if(getBounds().intersects(getMaker().getBounds()))
					dead=true;}
			else
			if(getBounds().intersects(getMaker().getActBounds()))
				dead=true;
		}
		if(!collideWithHook)
		for (HookObject hook:hooks) {
			if(hook.getBounds().intersects(getBounds())){
				harming=false;
				moving=false;
				//x -= Math.cos((double) Math.toRadians((double) getD())) * getSpeed()*10;
				//y -= Math.sin((double) Math.toRadians((double) getD())) * getSpeed()*10;
				collideWithHook=true;
				x=hook.getX();
				y=hook.getY();
				break;
			}
			
		}
		//dead=!onScreen;
	}
	@Override
		public void setOnScreen(boolean onScreen) {
			// TODO Auto-generated method stub
		if(harming){
			super.setOnScreen(onScreen);
			harming=onScreen;
			//setD(Statics.pointTowards(new Point(x,y), new Point(getMaker().getX(),getMaker().getY())));
			//x += Math.cos((double) Math.toRadians((double) getD())) * getSpeed();
			//y += Math.sin((double) Math.toRadians((double) getD())) * getSpeed();
			}
		else{
			x=lastP.x;
			y=lastP.y;
			//x -= Math.cos((double) Math.toRadians((double) lastD)) * getSpeed();
			//y -= Math.sin((double) Math.toRadians((double) lastD)) * getSpeed();
			moving=false;
			}
		}
	public boolean isHarming(){
		return harming;
	}
	public boolean collideWithHook(){
		return collideWithHook;
	}
	public void pull(){
		if(getBounds().intersects(owner.getScreen()))
		moving=true;
	}
}

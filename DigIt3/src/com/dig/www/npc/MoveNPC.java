package com.dig.www.npc;

import com.dig.www.start.Board;

public abstract class MoveNPC extends NPC{

protected int type;
//0=stop
//1=move to first
//2=patrol
protected int moveAt;
protected boolean movingBack;
protected MovePoint[]movePoints;
protected boolean hasWaited;
protected int speed;
	public MoveNPC(int x, int y, String loc, Board owner, String[] dialogs,
			String s, String location, NPCOption[] options,MovePoint[]movePoints,int type,int speed) {
		super(x, y, loc, owner, dialogs, s, location, options);
		this.movePoints=movePoints;
		this.type=type;
		this.speed=speed;
		// TODO Auto-generated constructor stub
	}
	@Override
	public void initialAnimate(int sX, int sY) {
		super.initialAnimate(sX, sY);
		for(MovePoint p:movePoints){
		p.x += sX;
		p.y += sY;}
	}
	@Override
	public void basicAnimate() {
		super.basicAnimate();
		for(MovePoint p:movePoints){
		p.x+=owner.getScrollX();
		p.y+=owner.getScrollY();
	}
	};
@Override
public void animate(){
	basicAnimate();
	
	if(movePoints.length>0){
		if(moveAt==movePoints.length){
			if(type==2){
				moveAt--;
				movingBack=true;
			}else if(type==1){
				moveAt=0;
			}else{
				movePoints=new MovePoint[0];
				return;
			}
		}
		if(moveAt<0){
			movingBack=false;
			moveAt=0;
		}
		int speed=this.speed*owner.mult();
		if(!movePoints[moveAt].getWaitFirst()||hasWaited){
		if(x-speed>movePoints[moveAt].getX())
			x-=speed;
		else if(x+speed<movePoints[moveAt].getX())
			x+=speed;
		else
			x=movePoints[moveAt].getX();
		
		if(y-speed>movePoints[moveAt].getY())
			y-=speed;
		else if(y+speed<movePoints[moveAt].getY())
			y+=speed;
		else
			y=movePoints[moveAt].getY();
//		System.out.println();
//		System.out.println(y-movePoints[moveAt].getY());
		
		if(x==movePoints[moveAt].getX()&&y==movePoints[moveAt].getY()){
			hasWaited=false;
			if(movingBack)
				moveAt--;
			else
				moveAt++;
		}
		}
		
	}
}
public void hasWaited(){
	hasWaited=true;
}
public void setMovePointFirstWait(int i,boolean setter){
	movePoints[i].waitFirst=setter;
}
}

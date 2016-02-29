package com.dig.www.npc;

import com.dig.www.start.Board;

public abstract class AnimatedMoveNPC extends MoveNPC{
protected boolean right=true;
protected String direction="side";
protected int counter;
protected int animationTimer;
private static final int ANIMAX = 7;
	public AnimatedMoveNPC(int x, int y, String loc, Board owner, String[] dialogs, String s, String location,
			NPCOption[] options, MovePoint[] movePoints, int type, int speed) {
		super(x, y, loc+"front/n.png", owner, dialogs, s, location, options, movePoints, type, speed);
		// TODO Auto-generated constructor stub
		this.loc=loc;
	}
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
				boolean xmove=false;
			if(x-speed>movePoints[moveAt].getX()){
				x-=speed;
				xmove=true;
				direction="side";
				right=false;}
			else if(x+speed<movePoints[moveAt].getX()){
				x+=speed;
				xmove=true;
				direction="side";
				right=true;}
			else
				x=movePoints[moveAt].getX();
			
			if(y-speed>movePoints[moveAt].getY()){
				y-=speed;
			if(!xmove)	
			direction="back";
			}
			else if(y+speed<movePoints[moveAt].getY()){
				y+=speed;
				if(!xmove)
			direction="front";
			}
			else
				y=movePoints[moveAt].getY();
			if((x!=movePoints[moveAt].getX()||y!=movePoints[moveAt].getY())){
				if(animationTimer>=ANIMAX/speed*10){
				animationTimer = 0;
				image=newImage(loc+direction+"/"+"w" + counter+".png");
				counter++;
				if (counter == MAX)
					counter = 0;
				}
				else
					animationTimer+=owner.mult();
			}else{
				image=newImage(loc+direction+"/n.png");
			}
//			System.out.println();
//			System.out.println(y-movePoints[moveAt].getY());
			
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
}

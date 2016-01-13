package com.dig.www.objects;

import java.awt.Point;

import com.dig.www.character.GameCharacter;
import com.dig.www.start.Board;

public class ActivatedBossWallActivator extends SensorObject{
		protected int minX=Integer.MIN_VALUE;
		protected int minY=Integer.MIN_VALUE;
		protected int maxX=Integer.MIN_VALUE;
		protected int maxY=Integer.MIN_VALUE;
	public ActivatedBossWallActivator(int x, int y, Board owner) {
		super(x, y, owner);
		// TODO Auto-generated constructor stub
	}
public ActivatedBossWallActivator(int x,int y,Board owner,String loc){
	// TODO Auto-generated constructor stub
	super(x, y, owner);
	try{
		String[]locSplit=loc.split("'");
		if(!locSplit[0].equals("N"))
		minX=Integer.parseInt(locSplit[0]);
		if(!locSplit[1].equals("N"))
		minY=Integer.parseInt(locSplit[1]);
		if(!locSplit[2].equals("N"))
		maxX=Integer.parseInt(locSplit[2]);
		if(!locSplit[3].equals("N"))
		maxY=Integer.parseInt(locSplit[3]);
	}catch(Exception ex){
		//ex.printStackTrace();
	}
}
@Override
public void animate() {
	super.animate();
	int sx=owner.getScrollX();
	int sy=owner.getScrollY();
	if(minX!=Integer.MIN_VALUE)
	minX+=sx;
	if(minY!=Integer.MIN_VALUE)
	minY+=sy;
	if(maxX!=Integer.MIN_VALUE)
	maxX+=sx;
	if(maxY!=Integer.MIN_VALUE)
	maxY+=sy;
};
@Override
public void initialAnimate(int sX, int sY) {
// TODO Auto-generated method stub
super.initialAnimate(sX, sY);
if(minX!=Integer.MIN_VALUE)
	minX+=sX;
	if(minY!=Integer.MIN_VALUE)
	minY+=sY;
	if(maxX!=Integer.MIN_VALUE)
	maxX+=sX;
	if(maxY!=Integer.MIN_VALUE)
	maxY+=sY;
}
	@Override
	public void action() {
		
		// TODO Auto-generated method stub
		for(Objects o:owner.getObjects()){
			if(o instanceof ActivatedBossWall)
				((ActivatedBossWall) o).activate();}
		Point p=owner.getCharPoint();
		System.out.println(maxY);
		for(GameCharacter chara:owner.getFriends()){
			if(minX!=Integer.MIN_VALUE){
				if(chara.getX()<minX){
					chara.setX(p.x);
					chara.setY(p.y);
				}}else if(minY!=Integer.MIN_VALUE){
					if(chara.getY()<minY){
						chara.setX(p.x);
						chara.setY(p.y);
					}}else if(maxY!=Integer.MIN_VALUE){
						if(chara.getY()>maxY){
							chara.setX(p.x);
							chara.setY(p.y);
						}}else if(maxX!=Integer.MIN_VALUE){
							if(chara.getX()>maxX){
								chara.setX(p.x);
								chara.setY(p.y);
							}}
		}
	}

}

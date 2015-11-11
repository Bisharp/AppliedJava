package com.dig.www.character;


import java.awt.Point;
import java.awt.Rectangle;
import java.security.acl.Owner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import javax.swing.JOptionPane;

import com.dig.www.blocks.Block;
import com.dig.www.enemies.Enemy;
import com.dig.www.objects.Objects;
import com.dig.www.start.Board;
import com.dig.www.util.Statics;





public class PointPath {
 ArrayList<PathPoint>points=new ArrayList<PathPoint>();
 ArrayList<GameCharacter> us=new ArrayList<GameCharacter>();
 int backwards=0;
 GameCharacter player;
private int me;
ArrayList<Block>world;
Board owner;
short updateTimer=0;
Point playerPoint;
boolean playerGo=true;
public PointPath(int me,Board owner){
	this.us=owner.getFriends();
	this.player=owner.getCharacter();
	this.me=me;
	this.world=owner.getWorld();
	this.owner=owner;
	
	findPath();
}
public void findPath() {
	//System.out.println("finding at "+new Date());
	points.clear();
	int roundX=(int)Math.ceil((player.getX()+40)/100);
	 int modX=Math.abs((int)((world.get(0).getX())%100));
	if(modX<11){
		roundX--;
	}
	int yWorld=0;
	 if(world.get(0).getY()>0){
		 yWorld=-100;
	 }
	roundX*=100;
	points.add(new PathPoint(roundX
			+(world.get(0).getX()<0?100:0)+
			((world.get(0).getX()<0?-1:1)*Math.abs(world.get(0).getX()%100))
			
			,yWorld+((int)((player.getY()+30)/100))*100
			+(world.get(0).getY()%100)+100
			-11
			, 0, getDistance(new Point((player.getX()), (player.getY()))),-1));
//	System.out.println("added: "+getCurrentFind().x+","+getCurrentFind().y);
	//points.add(new PathPoint(round100(player.getX()), round100(player.getY()), 0, getDistance(new Point(round100(player.getX()), round100(player.getY()))),-1));
//System.out.println("Mod: "+(world.get(0).getX()%100)+","+(world.get(0).getY()%100));
//	//Point pA=points.get(0);
//	System.out.println("point: "+points.get(0).x+","+points.get(0).y);
//System.out.println("world: "+world.get(0).getX()+","+world.get(0).getY());
	boolean found=false;
	while(!found){
	int[] adj={0,0,0,0};
	for(int c=0;c<world.size();c++){
		Block b=world.get(c);
		
	int adjNum=adj(new Point(round100(b.getX()),round100(b.getY())),new Point(round100((int) getCurrentFind().getX()),round100((int) getCurrentFind().getY())));
		//int adjNum=adj(new Point((b.getX()),(b.getY())),new Point(((int) getCurrentFind().getX()),((int) getCurrentFind().getY())));
		int notWall=1;
if(!b.traversable())
	notWall=-1;
Rectangle bBo=b.getBounds();
		for(Objects e:owner.getObjects()){
			if(e.isWall()&&bBo.intersects(e.getBounds()))
				notWall=-1;
		}

		for(Point p:points){
			if(p==getCurrentFind()){
			continue;
			}
			Point myPoint=getPoint(adjNum);
			if(myPoint.x==p.x&&myPoint.y==p.y){
				notWall=-1;
				break;
			}
		}
	if(adjNum==-1)
	continue;
	else{
		//System.out.println(" d: "+adjNum+" type: "+b.getType());
 if(adjNum==0)
	adj[0]=notWall;
else if(adjNum==1)
	adj[1]=notWall;
else if(adjNum==2)
	adj[2]=notWall;
else
	adj[3]=notWall;
	}
	if(adj[0]!=0&&adj[1]!=0&&adj[2]!=0&&adj[3]!=0)
		break;
	}
	
	

		int value=Integer.MAX_VALUE;
		int sel=0;
		for(int c=0;c<adj.length;c++){
			//System.out.println("dir: "+c+" score: "+pointPlusRDScore(c));
			if(adj[c]==1&&pointPlusRDScore(c)<value){
				sel=c;
				value=pointPlusRDScore(c);
			//System.out.println("added:"+c);
				//System.out.println(" d: "+c+" type: "+adj[c]);
			}
			
		}
		if(value<Integer.MAX_VALUE){
			
		int addX=0;
		int addY=0;
		if(sel==0)
			addX=100;
		else if(sel==1)
			addY=-100;
		else if(sel==2)
			addX=-100;
		else if(sel==3)
			addY=100;
		Point p=getCurrentFind();
		Point newP=new Point(p.x+addX,p.y+addY);
		points.add(new PathPoint(newP.x, newP.y, 0, getDistance(newP),sel));
		//System.out.println("added: "+newP.x+","+newP.y);
		//System.out.print("Options");
//		for(int c: adj){
//			System.out.print(", "+c);
//		}
		//System.out.println();
//		System.out.println("added: "+getCurrentFind().x+" and "+getCurrentFind().y);
//		System.out.println("dir: "+sel);
//		System.out.println();
backwards=0;
		}else{
			//System.out.println("not added");
			
			if(-backwards==points.size()-1){
			System.out.println("WAIT");
			System.out.println("BACKWARDS");
//			points.clear();
//			updateTimer=25;
//			playerPoint=new Point(owner.getFriends().get(me).getX(),owner.getFriends().get(me).getY());
	//		Statics.playSound(owner, "wait-a-minute.wav");
//			break;
//			
			
				
			
			if(!playerGo){
				 GameCharacter temp=player;
				 player=us.get(me);
				 us.set(me, temp);
			
			}
			playerGo=true;
			us.get(me).pathUpdateTimer=50;
				points.clear();
				us.get(me).setWaiting(true);
			//owner.getFriends().get(me).setX(player.getX());	
			//owner.getFriends().get(me).setY(player.getY());
			break;
			
			}
			else{
				//System.out.println(getCurrentFind().x+" : "+getCurrentFind().y);
			backwards--;}
					
		}
	
	if(getDistance(getCurrentFind())<100){
		//System.out.println(us.get(me).getType().charName()+","+us.get(me).getX()+","+us.get(me).getY()//+" WORKED AT:"+new Date()
		//);
		//System.out.println("player,"+player.getX()+","+player.getY());	//Statics.playSound(owner, "gunSFX/cyberCrossbow.wav");
		//System.out.println("Size before optimising: "+points.size());
//		for(int c=0;c<points.size();c++){
//			System.out.println(points.get(c));
//		}
		if(!playerGo){
			 GameCharacter temp=player;
			 player=us.get(me);
			 us.set(me, temp);
			 Collections.reverse(points);
		}
		playerGo=true;
		optimise();
	//points.remove(0);
		//System.out.println("Size after optimising: "+points.size());
		playerPoint=null;
		break;}
	else if(points.size()>50){
		//System.out.println("WAIT");
		points.clear();
		if(playerGo){
		 playerGo=false;
		 GameCharacter temp=player;
		 player=us.get(me);
		 us.set(me, temp);
		//JOptionPane.showMessageDialog(owner, player.getType().charName()+" alternate path");
		 findPath();
		}else{
			 GameCharacter temp=player;
			 player=us.get(me);
			 us.set(me, temp);
			 playerGo=true;
		System.out.println(us.get(me).getType().charName()+" TOO BIG at "+new Date());
		us.get(me).pathUpdateTimer=25;
		
		}
		//		for(PathPoint p:points){
//			for(Block b:world){
//		if(b.getBounds().contains(p)){
//			System.out.println(b.getType());
//		break;}
//				
//			}
		
		//owner.showPoints(points);
		//}
//		points.clear();
	
//		playerPoint=new Point(owner.getFriends().get(me).getX(),owner.getFriends().get(me).getY());
		//Statics.playSound(owner, "wait-a-minute.wav");
		
		//owner.getFriends().get(me).setX(player.getX());	
		//owner.getFriends().get(me).setY(player.getY());	
		break;
		
	}
//	else if(points.size()>1000){
//		System.out.println("to big");
//		break;}
	}
	
	
}
public void optimise() {
	outer:
	for(int c=points.size()-1;c>=0;c--){
		for(int i=c-2;i>=0;i--){
		if(getDistance(points.get(c),points.get(i))<=100){
			c--;
			while(c>i){
				points.remove(c);
				c--;
			}
			
			optimise();
			break outer;
		}}
	}
}
public int pointPlusDScore(int c) {
	Point p=getCurrentFind();
	int addX=0;
	int addY=0;
	if(c==0)
		addX=100;
	else if(c==1)
		addY=-100;
	else if(c==2)
		addX=-100;
	else if(c==3)
		addY=100;
	
	return getDistance(new Point(p.x+addX,p.y+addY));
}
public int pointPlusRDScore(int c) {
	Point p=getCurrentFind();
	int addX=0;
	int addY=0;
	if(c==0)
		addX=100;
	else if(c==1)
		addY=-100;
	else if(c==2)
		addX=-100;
	else if(c==3)
		addY=100;
	
	return getRDistance(new Point(p.x+addX,p.y+addY));
}
public int adj(Point pointOther, Point pointMe) {
	if(pointOther.getX()-100==pointMe.getX()&&pointOther.getY()==pointMe.getY()){
		return 0;
	
	}
	else if(pointOther.getY()+100==pointMe.getY()&&pointOther.getX()==pointMe.getX()){
		return 1;
	
	}
	else if(pointOther.getX()+100==pointMe.getX()&&pointOther.getY()==pointMe.getY()){
		return 2;
	
	}
	else if(pointOther.getY()-100==pointMe.getY()&&pointOther.getX()==pointMe.getX()){
		return 3;
	
	}else{
		return -1;
	}
}
public Point getPoint(int c) {//Manhattan
	Point p=getCurrentFind();
	int addX=0;
	int addY=0;
	if(c==0)
		addX=100;
	else if(c==1)
		addY=-100;
	else if(c==2)
		addX=-100;
	else if(c==3)
		addY=100;
	Point me=new Point(p.x+addX,p.y+addY);
	return me;
}
public int getDistance(Point me) {//Manhattan
	return (int) (Math.abs(me.getX()-us.get(this.me).getX())+Math.abs(me.getY()-us.get(this.me).getY()));
}
public int getRDistance(Point me) {//Euclidian
	return (int)(me.distance(us.get(this.me).getX(),us.get(this.me).getY()));
}
public int round100(int position){
	return ((int)((double)position/(double)100))*100;
}
public PathPoint getCurrentFind(){
	return points.get(points.size()-1+backwards);
}
public PathPoint getFirst(){
	return points.get(0);
}



public int getMe() {
	return me;
}
public void setMe(int me) {
	this.me = me;
}
public ArrayList<PathPoint> getPoints(){
	return points;
}
public int getDistance(Point me,Point other){//Manhatan
	return (int) (Math.abs(me.getX()-other.getX())+Math.abs(me.getY()-other.getY()));
}
public void update(){
	//System.out.println(updateTimer);
	for(PathPoint p: points){
		p.update(owner.getScrollX(),owner.getScrollY());
	}
	backwards=0;
	
//	if((playerPoint!=null&&playerPoint.distance(player.getX(),player.getY())>=200)||(points.size()!=0&&getFirst().distance(player.getX(),player.getY())>300)||(playerPoint==null&&points.size()==0&&owner.getFriends().get(me).getBounds().getLocation().distance(owner.getCharacter().getBounds().getLocation())>300)){
//		if(updateTimer==0){
//			updateTimer=0;
//		points.clear();
//		findPath();}
//		
//			//System.out.println(getCurrentFind().distance(new Rectangle(us.get(me).getBounds()).getLocation()));
//			
//			
//		
////		if(points.size()>0&&getCurrentFind().distance(new Rectangle(us.get(me).getBounds()).getLocation())<100){
////				removeLast();
////			}
//	}
	if(updateTimer>0){
			updateTimer--;}
	//else{
//				//System.out.println("points size: "+points.size());
//				//System.out.println("playerPoint: "+playerPoint);
////				if(playerPoint!=null){
////				//System.out.println("playerPoint distance: "+	playerPoint.distance(player.getX(),player.getY()));
////				}
//				
//			}
	//System.out.println(updateTimer);
}
public void removeLast(){
	points.remove(points.size()-1);
}
}

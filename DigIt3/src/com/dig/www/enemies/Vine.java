package com.dig.www.enemies;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

import com.dig.www.character.GameCharacter;
import com.dig.www.character.Moves;
import com.dig.www.start.Board;
import com.dig.www.util.Irregular;
import com.dig.www.util.Statics;

public class Vine extends StandEnemy implements Irregular{
	transient Image vineMid=new ImageIcon(getClass().getResource("/images/enemies/bosses/vineBoss/vines/middle.gif")).getImage();
	transient Image vineEnd=new ImageIcon(getClass().getResource("/images/enemies/bosses/vineBoss/vines/end.gif")).getImage();
	Point en;
	boolean updating=true;
	Point goTo;
	public Vine(int x, int y, Board owner,Enemy maker) {
		super(x, y, "images/enemies/bosses/vineBoss/vines/middle.gif", owner, true, -10);
		// TODO Auto-generated constructor stub
		en=new Point(maker.getX(),maker.getY());
		damage=4;
	}
	public void setP(int x,int y){
		en.setLocation(x, y);
	}
	@Override
	public void basicAnimate() {
		// TODO Auto-generated method stub
		super.basicAnimate();
		//if(!still){
			en.x+=owner.getScrollX();
			en.y+=owner.getScrollY();
			if(goTo!=null){
			goTo.x+=owner.getScrollX();
			goTo.y+=owner.getScrollY();}
			//}
	}
@Override
public void draw(Graphics2D g2d) {
	// TODO Auto-generated method stub
	if(goTo!=null){
		int vineSpeed=10;
		if(Statics.dist(x, y, goTo.x, goTo.y)<vineSpeed*1.2){
			x=goTo.x;
			y=goTo.y;
			goTo=null;
		}
		else{
			double d = Statics.pointTowards(new Point((int) x, (int) y), goTo);
			x += Math.cos((double) Math.toRadians((double) d)) *vineSpeed*owner.mult();
			y += Math.sin((double) Math.toRadians((double) d)) *vineSpeed*owner.mult();
//			int s=5;
//			if(x>=goTo.x+s){
//				x-=s;
//			}else if(x<=goTo.x-s){
//				x+=s;
//			}else
//				x=goTo.x;
//			if(y>=goTo.y+s){
//				y-=s;
//			}else if(y<=goTo.y-s){
//				y+=s;
//			}else
//				y=goTo.y;
		}
	}
	int x=this.x;
	int y=this.y;
	if(stunTimer>0){
		x+=Statics.RAND.nextInt(7)-3;
		y+=Statics.RAND.nextInt(7)-3;
	}
	double pointTo=Statics.pointTowards(new Point(x, y-vineMid.getHeight(owner)/2), new Point(en.x+50, en.y+50-vineMid.getHeight(owner)/2));
	g2d.setColor(Color.WHITE);
	g2d.fillRect(x-3, y-3, 6, 6);
	g2d.rotate(Math.toRadians(pointTo), x, y);
//g2d.drawImage(img, startX, startY, imNum, IM_TIMER_MAX, observer)
	int dist=(int) Statics.dist(x, y-vineMid.getHeight(owner)/2, en.x+50, en.y+50-vineMid.getHeight(owner)/2);
	if(dist<280){
		g2d.drawImage(vineEnd, x+dist/2, y-vineEnd.getHeight(owner)/2,dist/2,vineEnd.getHeight(owner)
				, owner);
		g2d.drawImage(vineEnd, x+dist/2, y-vineEnd.getHeight(owner)/2,-dist/2,vineEnd.getHeight(owner)
				, owner);
	}else{
		g2d.drawImage(vineEnd, x+120, y-vineEnd.getHeight(owner)/2,-120,vineEnd.getHeight(owner)
				, owner);
		int midDist=dist-240;
		int links=midDist/100;
		int remander=midDist-links*100;
		if(remander>50||links==0)
			links++;
		int linkLength=(int)Math.ceil((double)midDist/((double)links));
		for(int c=0;c<links;c++)
			g2d.drawImage(vineMid, x+120+(c*linkLength), y-vineMid.getHeight(owner)/2,linkLength,vineMid.getHeight(owner)
					, owner);
		g2d.drawImage(vineEnd, x+dist-120, y-vineEnd.getHeight(owner)/2//,-120,vineEnd.getHeight(owner)
				, owner);
	}
	//g2d.drawImage(vineMid, startX, startY-vineMid.getHeight(owner)/2,dist,vineMid.getHeight(owner)
	//		, owner);
g2d.rotate(-Math.toRadians(pointTo), x, y);
g2d.draw(getIrregularBounds());
}
@Override
public Rectangle getBounds() {
	// TODO Auto-generated method stub
	return new Rectangle(x, y-25, (int) Statics.dist(x, y+vineMid.getHeight(owner)/2, en.x+50, en.y+50-vineMid.getHeight(owner)/2), height);
}

	@Override
	public Polygon getIrregularBounds() {
		if(vineMid==null){
			vineMid=new ImageIcon(getClass().getResource("/images/enemies/bosses/vineBoss/vines/middle.gif")).getImage();
			vineEnd=new ImageIcon(getClass().getResource("/images/enemies/bosses/vineBoss/vines/end.gif")).getImage();
			
		}
		// TODO Auto-generated method stub
		double d=pointTowards(new Point(en.x+50, en.y+50));
		
		return Statics.rtRect(new Rectangle(x, y-25, (int) Statics.dist(x, y+vineMid.getHeight(owner)/2, en.x+50, en.y+50-vineMid.getHeight(owner)/2)+(int)(Math.sin(Math.toRadians(d))*50), height), new Point(x//+(int)(Math.sin(Math.toRadians(d-90))*25)
				, y//+(int)(Math.cos(Math.toRadians(d-90))*25)
				), d);
//		Polygon p=new Polygon();
//		int vineHeight=vineMid.getHeight(owner);
//		double dir=Math.toRadians(Statics.pointTowards(new Point(x, y-vineHeight/2), new Point(en.getX()+50, en.getY()+50-vineHeight/2)));
//		p.addPoint(x, y-vineHeight/2);
//		double rightDir=1.5708+dir;
//		p.addPoint((int)(x+Math.cos(rightDir)*vineHeight/2), (int)(y+Math.sin(rightDir)*vineHeight/2));
//		p.addPoint(en.getX()+50, en.getY()+50);
//		return p;
	}
public void fillPoint(Graphics2D g2d,Point p){
	g2d.fillRect(p.x-2, p.y-2, 4, 4);
}
public void doScroll(int x,int y){
	
		en.x+=x;
		en.y+=y;
		if(goTo!=null){
		goTo.x+=x;
		goTo.y+=y;}
		//en.setLocation(en.x+x, en.y+y);
}
@Override
public void interact(Moves move, GameCharacter character, boolean fromP) {
if(fromP){
	stunTimer=STUN_MAX;
}
}
public void setGoTo(Point p) {
	// TODO Auto-generated method stub
	goTo=p;
}
public boolean goToNull(){
	return goTo==null;
}
}

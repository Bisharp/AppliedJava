package com.dig.www.enemies;

import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

import com.dig.www.start.Board;
import com.dig.www.util.Statics;

public class PopChaseEnemy extends TrackingEnemy{
	private static final int ANIMAX = 75;
	private boolean touchingDirt;
	private int goTo;
	private int animateTimer;
	private int scrollX;
	private int scrollY;
	public PopChaseEnemy(int x, int y, Board owner, int health) {
		super(x, y,"images/enemies/unique/goop.png", owner,true, health);
	}
	@Override
		public void animate() {
			// TODO Auto-generated method stub
		touchingDirt=false;
		int distance=Integer.MAX_VALUE;
		for(int c=0;c<owner.getWorld().size();c++){
			switch (owner.getWorld().get(c).getType()) {
			case DIRT:
			case GROUND:
			case PIT:
			if(owner.getCharacter().getCollisionBounds().intersects(owner.getWorld().get(c).getBounds())){
			touchingDirt=true;
			if(owner.getCharPoint().distance(new Point(x,y))<distance){
			goTo=-1;
			distance=(int) owner.getCharPoint().distance(new Point(x,y));}
			}
			else{
				for(int c2=0;c2<owner.getFriends().size();c2++){
					if(owner.getFriends().get(c2).getCollisionBounds().intersects(owner.getWorld().get(c).getBounds())){
						touchingDirt=true;
						if(new Point(owner.getFriends().get(c2).getX(),owner.getFriends().get(c2).getY()).distance(new Point(x,y))<distance){
						goTo=c2;
						distance=(int) new Point(owner.getFriends().get(c2).getX(),owner.getFriends().get(c2).getY()).distance(new Point(x,y));}
				}
			}}
			default:
				break;
				

			}
			
		}//end loop
if(touchingDirt){
	
}else{
	if (animateTimer > 0) {
		x += getSpeed() * scrollX;
		y += getSpeed() * scrollY;

		animateTimer--;
	} else {
		switch (Statics.RAND.nextInt(9)) {

		case 0:
			scrollX = 1;
			scrollY = 1;
			break;
		case 1:
			scrollX = 1;
			scrollY = 0;
			break;
		case 2:
			scrollX = 0;
			scrollY = 1;
			break;
		case 3:
			scrollX = 1;
			scrollY = -1;
			break;

		case 4:
			scrollX = -1;
			scrollY = -1;
			break;
		case 5:
			scrollX = -1;
			scrollY = 0;
			break;
		case 6:
			scrollX = 0;
			scrollY = -1;
			break;
		case 7:
			scrollX = -1;
			scrollY = 1;
			break;
		}

		animateTimer = Statics.RAND.nextInt(ANIMAX) + 50;
}}
		}
	public Rectangle getBounds(){
//		if(show)
			return new Rectangle(x,y,100,100);
		//return new Rectangle();
	}
}

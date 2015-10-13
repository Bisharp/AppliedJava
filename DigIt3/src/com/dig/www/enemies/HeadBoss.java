package com.dig.www.enemies;

import java.awt.Point;
import java.awt.Rectangle;

import com.dig.www.start.Board;
import com.dig.www.start.DigIt;
import com.dig.www.util.Statics;

public class HeadBoss extends Boss{

	public HeadBoss(int x, int y,Board owner) {
		super(x, y, "images/enemies/unique/Head.png", owner, true, 100, "Head of Doom", 5,"music/zeldaCopyright.mp3");
		// TODO Auto-generated constructor st0ub
	}
//int realSeq;
	@Override
	public void animate() {
		// TODO Auto-generated method stub
		
		basicAnimate();
		boolean acted=sortAction();
		if(!acted){
			if(!getBounds().intersects(getOwnerPlusBounds())){
				//realSeq=sequence;
				follow(5, 0, 5);
			}
			if(actTimer<=0){
				switch (sequence) {
				case 0:
				
					chargeAttack(150, 40,1.5);
					sequence++;
					break;
				case 1:
					
				follow(200, 40,1);
					sequence++;
					break;
				case 2:
				case 3:
				case 4:
				
					createProjectile("images/enemies/blasts/1.png",10,
							Statics.pointTowards(new Point((int) x,
							(int) y), owner.getCharPoint()),true,40);
					sequence++;
					break;
				case 5:
				
					createTProjectile("images/enemies/blasts/0.png",10,
							Statics.pointTowards(new Point((int) x,
							(int) y), owner.getCharPoint()),true,40);
					sequence++;
					break;
				default:
					sequence=0;
					break;
				}
					
				
			}
				
			
		}
		if(!acted&&actTimer>0)
			actTimer--;
	}
	public int getKillXP(){
		return 25;
	}
public Rectangle getOwnerPlusBounds(){
return new Rectangle(-100,-100,owner.getWidth()+200,owner.getHeight()+220);	
}
}

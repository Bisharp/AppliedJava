package com.dig.www.enemies;

import java.awt.Point;

import com.dig.www.start.Board;
import com.dig.www.util.Statics;

public class HeadBoss extends Boss{

	public HeadBoss(int x, int y,Board owner) {
		super(x, y, "images/enemies/unique/Head.png", owner, true, 100, "Head of Doom, I eat cake!", 5);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void animate() {
		// TODO Auto-generated method stub
		basicAnimate();
		boolean acted=sortAction();
		if(!acted){
			if(actTimer<=0){
				switch (sequence) {
				case 0:
					chargeAttack(50, 25);
					sequence++;
					break;
				case 1:
				follow(200, 25);
					sequence++;
					break;
				case 2:
					createProjectile("images/enemies/blasts/1.png",10,
							Statics.pointTowards(new Point((int) x,
							(int) y), owner.getCharPoint()),true,25);
					sequence++;
					break;
				case 3:
					createTProjectile("images/enemies/blasts/0.png",10,
							Statics.pointTowards(new Point((int) x,
							(int) y), owner.getCharPoint()),true,25);
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

}

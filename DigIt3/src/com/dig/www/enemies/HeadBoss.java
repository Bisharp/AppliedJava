package com.dig.www.enemies;

import java.awt.Point;
import java.awt.Rectangle;

import com.dig.www.start.Board;
import com.dig.www.start.DigIt;
import com.dig.www.util.Statics;

public class HeadBoss extends Boss{

	public HeadBoss(int x, int y,Board owner) {
		super(x, y, "images/enemies/bosses/Head.png", owner, true, 1000, "Head of Doom", 5,"music/zeldaCopyright.mp3","gunSFX/explosion-2.wav","gunSFX/explosion-2.wav");
		// TODO Auto-generated constructor st0ub
	}
//int realSeq;
	public void  makeDeadExplosion(){
		super.makeDeadExplosion();
		owner.getEnemies().add(new Explosion(x, y, "images/effects/explosion.png", owner));
		
	}
	@Override
	public void animate() {
		// TODO Auto-generated method stub
	if(health<(maxHealth/3)&&phase<2){
		phase=2;
		Statics.playSound(owner,bossPhaseS);
		owner.getEnemies().add(new Explosion(x, y, "images/effects/explosion.png", owner));
	}if(health<(maxHealth/3)*2&&phase<1){
			phase=1;
			Statics.playSound(owner,bossPhaseS);
			owner.getEnemies().add(new Explosion(x, y, "images/effects/explosion.png", owner));
				
	}
	
		basicAnimate();
		boolean acted=sortAction();
		if(!acted){
			if(!getBounds().intersects(getOwnerPlusBounds())){
				//realSeq=sequence;
				follow(5, 0, 5);
			}
			if(actTimer<=0){
				if(sequence==0){
				
				if(phase>0){
					chargeAttack(150, 40,1.5);
					}
			sequence++;
					
				}
				else if(sequence==1){
				follow(200, 40,1);
					sequence++;}
					
				else if(sequence>1&&sequence<5){
				if(phase+2>=sequence){
					createProjectile("images/enemies/blasts/1.png",10,
							Statics.pointTowards(new Point((int) x,
							(int) y), new Point(owner.getCharacterX()+40,owner.getCharacterY()+40)),true,40,width/2,height/4);
				}	sequence++;
					}
				else if(sequence==5){
				if(phase==2)
					createTProjectile("images/enemies/blasts/0.png",10,
							Statics.pointTowards(new Point((int) x,
							(int) y), owner.getCharPoint()),true,40);
					sequence++;
					}
				else
					sequence=0;
					
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

package com.dig.www.enemies;

import java.awt.Graphics2D;
import java.awt.Point;

import com.dig.www.objects.KeyCrystal;
import com.dig.www.start.Board;
import com.dig.www.util.Statics;

public class StoneBoss extends Boss{
boolean ab=false;
private boolean foughtBefore=false;
	public StoneBoss(int x, int y,Board owner) {
		super(x, y, "images/enemies/bosses/Stone.png", owner, true, 750, "Dangerous Stone", 0,"music/zeldaCopyright.mp3","gunSFX/explosion-2.wav","gunSFX/explosion-2.wav");
		// TODO Auto-generated constructor stub
	}
	public void  makeDeadExplosion(){
		super.makeDeadExplosion();
		owner.getEnemies().add(new StaticExplosion(x, y-144, "images/effects/lightning.gif", owner, 10));
		if(!foughtBefore){
			foughtBefore=true;
			owner.getObjects().add(new KeyCrystal(x, y, "5", owner, -100/*Troll money*/));
		}
		//Add the note here
	}
	@Override
	public void animate() {
		// TODO Auto-generated method stub
	if(health<(maxHealth/3)&&phase<2){
		phase=2;
		Statics.playSound(owner,bossPhaseS);
		owner.getEnemies().add(new StaticExplosion(x, y-144, "images/effects/lightning.gif", owner, 10));
	}if(health<(maxHealth/3)*2&&phase<1){
			phase=1;
			Statics.playSound(owner,bossPhaseS);
			owner.getEnemies().add(new StaticExplosion(x, y-144, "images/effects/lightning.gif", owner, 10));
				
	}
	
		basicAnimate();
		if(active){
			if(actTimer<=0){
				if(sequence<3){
				actTimer=100;
				owner.getEnemies().add(new Projectile(Statics.pointTowards(new Point((int) x,
							(int) y), new Point(owner.getCharacterX()+40,owner.getCharacterY()+40)), x, y-144, 6, this, "images/effects/lightning.gif", owner, false, 20, false));
				sequence++;
				}
				else if(sequence==3){
				actTimer=100;
					sequence++;}
				else if(sequence==4){
					actTimer=100;
					if(ab){
					int c=(int)(Math.random()*3)-1;
					int c2;
					do{
						c2=(int)(Math.random()*3)-1;
					}while(c2==0&&c==0);//Wow!
					owner.getEnemies().add(new ExplosionLightningSpawn(x-288+((int)(Math.random()*300+125)*c), y-288+((int)(Math.random()*300+125)*c2), owner));
					}
					ab=!ab;
					sequence++;
				}else if(sequence==5){
					fireAll("images/effects/lightning.gif", 6, 4, flying, 40, false);
					sequence++;
				}
				else
					sequence=0;
					
				}
					
				
			}
				
			
		
		if(actTimer>0)
			actTimer--;
	}
	@Override
	public int getKillXP() {
		// TODO Auto-generated method stub
		return 0;
	}
	public void fireAll(String loc,int speed,int times,boolean flying,int timer,boolean isTurning){
		int dirAdder=360/times;
		for(int c=0;c<times;c++){

		owner.getEnemies().add(new Projectile((dir+(c*dirAdder))%360, x+(int)getBounds().getWidth()/2, y+(int)getBounds().getHeight()/2-144, speed, this, loc, owner, flying,20));
		if(isTurning)
		((Projectile)(owner.getEnemies().get(owner.getEnemies().size()-1))).setTurning(true);
		}	actTimer=timer;

	}
}

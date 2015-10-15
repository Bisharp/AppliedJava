package com.dig.www.enemies;

import java.awt.Point;

import com.dig.www.blocks.Block;
import com.dig.www.start.Board;
import com.dig.www.util.Statics;

public class LizardMan extends Boss {
boolean phaseA=true;
boolean diagR=true;
int diagMove;
int lastX;
int lastY;
int bMove;
	public LizardMan(int x, int y, Board owner) {
		super(x, y, "images/enemies/unique/jello-O.png", owner, true, 99,
				"Lizard-Man of Doom", 2, "music/zeldaCopyright2.mp3",
				"gunSFX/explosion-2.wav", "enemy/LizHurt.wav");
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getKillXP() {
		// TODO Auto-generated method stub
		return 25;
	}

	@Override
	public void animate() {
		// TODO Auto-generated method stub
		if(health<(maxHealth/3)&&phase<2){
			phase=2;
			Statics.playSound(owner,bossPhaseS);
			owner.getEnemies().add(new Explosion(x, y, "images/effects/shadow.png", owner));
		phaseA=true;
		}if(health<(maxHealth/3)*2&&phase<1){
				phase=1;
				Statics.playSound(owner,bossPhaseS);
				owner.getEnemies().add(new Explosion(x, y, "images/effects/shadow.png", owner));
				phaseA=true;		
		}
		if((health<(maxHealth/6)*5)&&phase==0&&phaseA){
			phaseA=false;
			Statics.playSound(owner,bossPhaseS);
			owner.getEnemies().add(new Explosion(x, y, "images/effects/explosion.png", owner));
	
		}
		 
		basicAnimate();
		boolean acted=sortAction();
		
		if(!acted){
			if(actTimer<=0){
				if(phaseA){
					if(diagMove==0){
			int Adir=(diagR?315:225);
			diagnalMove(Adir,Integer.MAX_VALUE, 1, 10);
			diagMove++;}
					else if(diagMove==1){
						int Adir=(diagR?0:180);
						diagnalMove(Adir,100/speed*5, 1, 10);
						diagMove++;}
					else if(diagMove==2){
						createProjectile("images/enemies/blasts/0.png", 10, Statics.pointTowards(new Point((int) x,
							(int) y), new Point(owner.getCharacterX()+40,owner.getCharacterY()+40)), false, 20);
					diagMove++;
					}
					else if(diagMove==3){
						int Adir=(!diagR?0:180);
						diagnalMove(Adir,100/speed*5, 1, 10);
						diagMove=0;}
					
			acted=true;}else{
				if(actTimer<=0){
					if(bMove==0){
						int xPoint=(int)(15.5*100);
						int yPoint=Math.max(y, (int)(owner.getWorld().get(0).getY()+((maxHealth/health)*(-owner.getWorld().get(0).getY()+owner.getWorld().get(owner.getWorld().size()-1).getY()))));
						moveTo(xPoint,yPoint, 1, 10);
					}
					if(bMove<3){
					createProjectile("images/enemies/blasts/0.png", 10, Statics.pointTowards(new Point((int) x,
						(int) y), new Point(owner.getCharacterX()+40,owner.getCharacterY()+40)), false, 20);
					}
				}
			}
		}
		if(!acted&&actTimer>0)
			actTimer--;}
	}
	public void  makeDeadExplosion(){
		super.makeDeadExplosion();
		owner.getEnemies().add(new Explosion(x, y, "images/effects/shadow.png", owner));
		
	}
	public boolean sortAction(){
		if(attackNum==0){
			moveDForward();
			for(Block b: owner.getWorld()){
				if(!b.traversable()&&b.getBounds().intersects(getBounds())){
				followTimer=0;
				attackNum=-1;
				dir+=180;
				speedMulti=1;
//				while(b.getBounds().intersects(getBounds()))
//				moveDForward();
				x=lastX;
				y=lastY;
				moveDForward();
				diagR=!diagR;
				break;
				}
					
			}
			lastX=x;
			lastY=y;
			followTimer--;
			if(followTimer==0){
				attackNum=-1;
				speedMulti=1;
			}
			return true;
		}

			
		
		return false;
	}
	public void diagnalMove(int dir,int max,double speedMulti,int timer){
		acting=true;
		this.dir=dir;
		this.speedMulti=speedMulti;
		this.followTimer=max;
		actTimer=timer;
		attackNum=0;
	}
	public void moveTo(int moveX,int moveY,double multiSpeed,int timer){
		
	}
}

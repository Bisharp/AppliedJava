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
				"Jell-O of Destruction", 5, "music/zeldaCopyright2.mp3",
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
			
			followTimer=0;
			attackNum=-1;
			Statics.playSound(owner,bossPhaseS);
			owner.getEnemies().add(new Explosion(x, y, "images/effects/shadow.png", owner));
		phaseA=true;
		int xPoint=(int)(owner.getWorld().get(0).getX()+(15.5*100));
		int yPoint=(int) Math.max(
				(owner.getWorld().get(0).getY()+
				((double)((double)((maxHealth/3)*(2-phase))/(double)maxHealth)
				*(-owner.getWorld().get(0).getY()+owner.getWorld().get(owner.getWorld().size()-1).getY() ))),owner.getWorld().get(0).getY()+(100*4));//, Math.min(y, (int)(owner.getWorld().get(0).getY()+
				//((double)((double)health/(double)maxHealth)
				//*(-owner.getWorld().get(0).getY()+owner.getWorld().get(owner.getWorld().size()-1).getY())))));
		moveTo(xPoint,yPoint, 1.5, 10);
		phase=2;
		diagMove=1;
		}if(health<(maxHealth/3)*2&&phase<1){
				

				followTimer=0;
				attackNum=-1;
				Statics.playSound(owner,bossPhaseS);
				owner.getEnemies().add(new Explosion(x, y, "images/effects/shadow.png", owner));
				phaseA=true;		
				int xPoint=(int)(owner.getWorld().get(0).getX()+(15.5*100));
				int yPoint=(int) Math.max(
						(owner.getWorld().get(0).getY()+
						((double)((double)((maxHealth/3)*(2-phase))/(double)maxHealth)
						*(-owner.getWorld().get(0).getY()+owner.getWorld().get(owner.getWorld().size()-1).getY() ))),owner.getWorld().get(0).getY()+(100*4));//, Math.min(y, (int)(owner.getWorld().get(0).getY()+
						//((double)((double)health/(double)maxHealth)
						//*(-owner.getWorld().get(0).getY()+owner.getWorld().get(owner.getWorld().size()-1).getY())))));
				moveTo(xPoint,yPoint, 1.5, 10);
				phase=1;
				diagMove=1;
		}
		if(((health<(maxHealth/6)*(5-(2*phase)))||y<=(int) Math.max(
				(owner.getWorld().get(0).getY()+
				((double)((double)((maxHealth/3)*(2-phase))/(double)maxHealth)
				*(-owner.getWorld().get(0).getY()+owner.getWorld().get(owner.getWorld().size()-1).getY() ))),owner.getWorld().get(0).getY()+(100*4)))&&phaseA){
			phaseA=false;
			followTimer=0;
			attackNum=-1;
			Statics.playSound(owner,bossPhaseS);
			owner.getEnemies().add(new Explosion(x, y, "images/effects/explosion.png", owner));
	bMove=1;
		}
		 
		basicAnimate();
		if(x<owner.getWorld().get(0).getX()+(11*100)){
			x=owner.getWorld().get(0).getX()+(11*100);
		}
		if(x>owner.getWorld().get(0).getX()+(20*100)){
			x=owner.getWorld().get(0).getX()+(20*100);
		}
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
						int xPoint=(int)(owner.getWorld().get(0).getX()+(15.5*100));
						int yPoint=(int) Math.max(
								(owner.getWorld().get(0).getY()+
								((double)((double)((maxHealth/3)*(2-phase))/(double)maxHealth)
								*(-owner.getWorld().get(0).getY()+owner.getWorld().get(owner.getWorld().size()-1).getY() ))),owner.getWorld().get(0).getY()+(100*4));//, Math.min(y, (int)(owner.getWorld().get(0).getY()+
								//((double)((double)health/(double)maxHealth)
								//*(-owner.getWorld().get(0).getY()+owner.getWorld().get(owner.getWorld().size()-1).getY())))));
						moveTo(xPoint,yPoint, 1.5, 10);
						bMove++;
			
//						System.out.println("H: "+((double)health/(double)maxHealth));
//						System.out.println("Min: "+(int)(owner.getWorld().get(0).getY()));
//						System.out.println("Max: "+(int)(owner.getWorld().get(owner.getWorld().size()-1).getY()));
//						System.out.println("My: "+yPoint);
						
					}
					else if(bMove==1){
						float multiSpeed=1.5F;
						diagnalMove((int) Statics.pointTowards(new Point((int) x, (int) y), owner.getCharPoint()),(int)Math.max(125, (new Point(x,y).distance(owner.getCharPoint())+20)/(speed*multiSpeed))
								, multiSpeed,40);
						bMove++;
					}
					else	if(bMove<3){
					createProjectile("images/enemies/blasts/0.png", 10, Statics.pointTowards(new Point((int) x,
						(int) y), new Point(owner.getCharacterX()+40,owner.getCharacterY()+40)), false, 20);
					bMove++;
					}else{
						bMove=0;
					}
				}
			}
		}
		if(!acted&&actTimer>0)
			actTimer--;}
		if(x<owner.getWorld().get(0).getX()+(11*100)){
			x=owner.getWorld().get(0).getX()+(11*100);
		}
		if(x>owner.getWorld().get(0).getX()+(20*100)){
			x=owner.getWorld().get(0).getX()+(20*100);
		}
	}
	public void  makeDeadExplosion(){
		super.makeDeadExplosion();
		owner.getEnemies().add(new Explosion(x, y, "images/effects/shadow.png", owner));
		
	}
	public boolean sortAction(){
		
		if(attackNum==2){
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
		}else if(attackNum==3){
			moveDForward();
			
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
		attackNum=2;
	}
	public void moveTo(int moveX,int moveY,double multiSpeed,int timer){
	
		Point myPoint=new Point(x,y);
		Point thePoint=new Point(moveX,moveY);
	if((int)((int)myPoint.distance(thePoint)/(speed*multiSpeed))>speed*multiSpeed){
		diagnalMove((int)Statics.pointTowards(myPoint, thePoint),(int) ((int)myPoint.distance(thePoint)/(speed*multiSpeed)),multiSpeed,timer);
	
	attackNum=3;
	}}
}

package com.dig.www.enemies;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;

import com.dig.www.blocks.Block;
import com.dig.www.start.Board;
import com.dig.www.util.Statics;

public class LizardMan extends Boss {
private boolean phaseA=true;
private boolean diagR=true;
private int diagMove;
private int lastX;
private int lastY;
private int bMove;
private int imNum;
private int imTimer;
private boolean moveActed;
private static final int IM_TIMER_MAX=6;
int roarTimer;
	public LizardMan(int x, int y, Board owner) {
		super(x, y, "n", owner, true, 1000,
				"Lizard-Man of Destruction", 5, "music/zeldaCopyright2.mp3",
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
		moveActed=false;
		// TODO Auto-generated method stub
		
		if(health<(maxHealth/3)&&phase<2){
			
			followTimer=0;
			attackNum=-1;
			Statics.playSound(owner,bossPhaseS);
			owner.getEnemies().add(new CycleExplosion(x, y, "images/effects/shadow", owner,0,4,1000,this));
		phaseA=true;
		int xPoint=(int)(owner.getWorld().get(0).getX()+(15.5*100));
		int yPoint=(int) Math.max(
				(owner.getWorld().get(0).getY()+
				((double)((double)((maxHealth/3)*(2-phase))/(double)maxHealth)
				*(-owner.getWorld().get(0).getY()+owner.getWorld().get(owner.getWorld().size()-1).getY() ))),owner.getWorld().get(0).getY()+(100*4));//, Math.min(y, (int)(owner.getWorld().get(0).getY()+
				//((double)((double)health/(double)maxHealth)
				//*(-owner.getWorld().get(0).getY()+owner.getWorld().get(owner.getWorld().size()-1).getY())))));
		moveTo(xPoint,yPoint, 1.75, 10);
		phase=2;
		diagMove=1;
		} else if(health<(maxHealth/3)*2&&phase<1){
				

				followTimer=0;
				attackNum=-1;
				Statics.playSound(owner,bossPhaseS);
				owner.getEnemies().add(new CycleExplosion(x, y, "images/effects/shadow", owner,0,4,1000,this));
				phaseA=true;		
				int xPoint=(int)(owner.getWorld().get(0).getX()+(15.5*100));
				int yPoint=(int) Math.max(
						(owner.getWorld().get(0).getY()+
						((double)((double)((maxHealth/3)*(2-phase))/(double)maxHealth)
						*(-owner.getWorld().get(0).getY()+owner.getWorld().get(owner.getWorld().size()-1).getY() ))),owner.getWorld().get(0).getY()+(100*4));//, Math.min(y, (int)(owner.getWorld().get(0).getY()+
						//((double)((double)health/(double)maxHealth)
						//*(-owner.getWorld().get(0).getY()+owner.getWorld().get(owner.getWorld().size()-1).getY())))));
				moveTo(xPoint,yPoint, 1.75, 10);
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
			owner.getEnemies().add(new Explosion(x, y, owner));
	bMove=1;
		}
		 
		basicAnimate();
		if(x<owner.getWorld().get(0).getX()+(11*100)){
			x=owner.getWorld().get(0).getX()+(11*100);
		}
		else if(x>owner.getWorld().get(0).getX()+(20*100)){
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
						roarTimer=20;
						createProjectile("images/enemies/blasts/poison.png", 10, Statics.pointTowards(new Point((int) x,
							(int) y), new Point(owner.getCharacterX()+40,owner.getCharacterY()+40)), false, 20,width/2,height/6,10,true);
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
								*(-owner.getWorld().get(0).getY()+owner.getWorld().get(owner.getWorld().size()-1).getY() ))),owner.getWorld().get(0).getY()+(100*6));//, Math.min(y, (int)(owner.getWorld().get(0).getY()+
								//((double)((double)health/(double)maxHealth)
								//*(-owner.getWorld().get(0).getY()+owner.getWorld().get(owner.getWorld().size()-1).getY())))));
						moveTo(xPoint,yPoint,3, 10);
						bMove++;
			
//						System.out.println("H: "+((double)health/(double)maxHealth));
//						System.out.println("Min: "+(int)(owner.getWorld().get(0).getY()));
//						System.out.println("Max: "+(int)(owner.getWorld().get(owner.getWorld().size()-1).getY()));
//						System.out.println("My: "+yPoint);
						
					}
					else if(bMove==1){
						float multiSpeed=1.5F;
						diagnalMove((int) Statics.pointTowards(new Point((int) x, (int) y), owner.getCharPoint()),(int)Math.max(125, (new Point(x,y).distance(owner.getCharPoint())+20)/(speed*multiSpeed))
								, multiSpeed,20);
						bMove++;
					}
					else	if(bMove<3){
						roarTimer=20;
					createProjectile("images/enemies/blasts/poison.png", 10, Statics.pointTowards(new Point((int) x,
						(int) y), new Point(owner.getCharacterX()+40,owner.getCharacterY()+40)), false,20,width/2,height/6,10,true);
					bMove++;
					}
					else{
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
		else	if(x>owner.getWorld().get(0).getX()+(20*100)){
			x=owner.getWorld().get(0).getX()+(20*100);
		}
		if(roarTimer>0){
			image=newImage("r");
			roarTimer--;
		}else{
		if(!moveActed){
				
					image=newImage("n");
				}else{
					//System.out.println(imTimer+","+imNum);
			if(imTimer<=0){
				
				image=newImage("w"+imNum);
				imNum++;
				if(imNum>3){
					imNum=0;
				}
				imTimer=IM_TIMER_MAX;
			}else{
				imTimer--;
			}}}
	}
	public void  makeDeadExplosion(){
		super.makeDeadExplosion();
		owner.getEnemies().add(new Explosion(x, y, owner));
		
	}
	public boolean sortAction(){
		
		if(attackNum==2){
			moveActed=true;
			moveDForward();
			for(Block b: owner.getWorld()){
				if(!b.traversable()&&b.getBounds().intersects(getBounds())){
				followTimer=0;
				attackNum=-1;
				dir+=180;
				
//				while(b.getBounds().intersects(getBounds()))
//				moveDForward();
				x=lastX;
				y=lastY;
				moveDForward();
				speedMulti=1;
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
			moveActed=true;
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
	public Image newImage(String name) {
		if(name.contains("/"))
		return super.newImage(name);	
		else{
			shadow=newShadow(getPath() + name + ".png");
		return super.newImage(getPath() + name + ".png");}
	}
	
	private String getPath() {

		String dire= "side";

		if(Math.abs(dir-90)<45){
			dire="front";
		}else if(Math.abs(dir-270)<45){
			dire="back";
		}
			
			
			
		return "images/enemies/bosses/" + "LizardMan" + "/" + dire + "/";
	}
	public void myDraw(Graphics2D g2d){
		if (stunTimer > 0) {
			int x = this.x + (Statics.RAND.nextInt(5) * (Statics.RAND.nextBoolean() ? 1 : -1));
			int y = this.y + (Statics.RAND.nextInt(5) * (Statics.RAND.nextBoolean() ? 1 : -1));
		if(Math.abs(dir)<=50||Math.abs(dir)>=310){
			g2d.drawImage(image, x + width, y, -width, height, owner);
			if (owner.darkenWorld())
				g2d.drawImage(shadow, x+width, y,-width,height, owner);
		}else{
			g2d.drawImage(image, x, y, owner);
			if (owner.darkenWorld())
				g2d.drawImage(shadow, x, y, owner);
		}} else{if(Math.abs(dir)<=50||Math.abs(dir)>=310){
			g2d.drawImage(image, x + width, y, -width, height, owner);
			if (owner.darkenWorld())
				g2d.drawImage(shadow, x+width, y,-width,height, owner);
		}else{
			g2d.drawImage(image, x, y, owner);
		if (owner.darkenWorld())
			g2d.drawImage(shadow, x, y, owner);}
		}
		if (harmTimer > 0)
			g2d.drawImage(newImage("images/effects/heart.png"), x, y, owner);
		else if (slowTimer > 0)
			g2d.drawImage(newImage("images/effects/ice.png"), x, y, owner);

			// g2d.setFont(enFont);
			// g2d.setColor(Color.BLACK);
			// g2d.drawString("" + health, x, y - 10);
			drawBar((double) health / (double) maxHealth, g2d);
		
	}
}

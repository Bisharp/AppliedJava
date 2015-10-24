package com.dig.www.enemies;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.util.ArrayList;

import com.dig.www.start.Board;
import com.dig.www.util.Statics;

public class SpinnyBoss extends Boss{
private Image image2;
private Image image3;
private int rotate1=0;
private int rotate2=0;
	public SpinnyBoss(int x, int y, Board owner) {
		super(x, y, "images/enemies/bosses/pod/pod.png", owner, true, 1000,
				"SpinnyBoss", 5,
				"music/zeldaCopyright.mp3","gunSFX/explosion-2.wav",
				"gunSFX/explosion-2.wav");
		// TODO Auto-generated constructor stub
		image2=newImage("images/enemies/bosses/pod/ring1.png");//bigger
		image3=newImage("images/enemies/bosses/pod/ring0.png");//medium
	}

	@Override
	public int getKillXP() {
		// TODO Auto-generated method stub
		return 25;
	}

	@Override
	public void animate() {
		// TODO Auto-generated method stub
		if(attackNum!=0){
			//dir = (int) pointTowards(owner.getCharPoint());
			dir = (int) Statics.pointTowards(new Point((int) x, (int) y), owner.getCharPoint());

		}
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
								(int) y), new Point(owner.getCharacterX(),owner.getCharacterY())),true,40,(int)getBounds().getWidth()/2,(int)getBounds().getHeight()/2-((2-phase)*25));
					}	sequence++;
						}
					else if(sequence==5){
					if(phase==2)
						fireAll("images/enemies/blasts/0.png",10,
								8,true,40);
						sequence++;
						}
					else
						sequence=0;
						
					}
						
					
				}
					
				
			
			if(!acted&&actTimer>0)
				actTimer--;
	}
	public Rectangle getOwnerPlusBounds(){
		return new Rectangle(-100,-100,owner.getWidth()+200,owner.getHeight()+220);	
		}
//	public Image newImage(String name) {
//		if(name.contains("/"))
//		return super.newImage(name);	
//		else
//		return super.newImage(getPath() + name + ".png");
//	}
//	private String getPath() {
//
//		
//			
//			
//			
//		return "images/enemies/bosses/"
//		//+ "pod" + "/"
//				;
//	}
	public void myDraw(Graphics2D g2d){
		if (stunTimer > 0) {
			int x = this.x + (Statics.RAND.nextInt(5) * (Statics.RAND.nextBoolean() ? 1 : -1));
			int y = this.y + (Statics.RAND.nextInt(5) * (Statics.RAND.nextBoolean() ? 1 : -1));
		
			
			if(phase<1){
				g2d.rotate(Math.toRadians(rotate1), x+(image2.getWidth(owner)/2), y+(image2.getHeight(owner)/2));
				g2d.drawImage(image2,x,y,owner);
				g2d.rotate(-Math.toRadians(rotate1), x+(image2.getWidth(owner)/2), y+(image2.getHeight(owner)/2));	
			}
			if(phase<2){
				g2d.rotate(Math.toRadians(rotate2), x+(image3.getWidth(owner)/2)+((getBounds().getWidth()-image3.getWidth(owner))/2), y+(image3.getHeight(owner)/2)+((getBounds().getHeight()-image3.getHeight(owner))/2));
				g2d.drawImage(image3,x+(int)((getBounds().getWidth()-image3.getWidth(owner))/2),y+(int)((getBounds().getHeight()-image3.getHeight(owner))/2),owner);
				g2d.rotate(-Math.toRadians(rotate2), x+(image3.getWidth(owner)/2)+((getBounds().getWidth()-image3.getWidth(owner))/2), y+(image3.getHeight(owner)/2)+((getBounds().getHeight()-image3.getHeight(owner))/2));
			}g2d.rotate(Math.toRadians(dir), x+(width/2)+((getBounds().getWidth()-width)/2), y+(height/2)+((getBounds().getHeight()-height)/2));
			g2d.drawImage(image,(int) (x+((getBounds().getWidth()-width)/2)),(int) (y+((getBounds().getHeight()-height)/2)),owner);
			g2d.rotate(-Math.toRadians(dir), x+(width/2)+((getBounds().getWidth()-width)/2), y+(height/2)+((getBounds().getHeight()-height)/2));	
	
			
		
		}
		
		else{
			
			
			if(phase<1){
				g2d.rotate(Math.toRadians(rotate1), x+(image2.getWidth(owner)/2), y+(image2.getHeight(owner)/2));
				g2d.drawImage(image2,x,y,owner);
				g2d.rotate(-Math.toRadians(rotate1), x+(image2.getWidth(owner)/2), y+(image2.getHeight(owner)/2));	
			}
			if(phase<2){
				g2d.rotate(Math.toRadians(rotate2), x+(image3.getWidth(owner)/2)+((getBounds().getWidth()-image3.getWidth(owner))/2), y+(image3.getHeight(owner)/2)+((getBounds().getHeight()-image3.getHeight(owner))/2));
				g2d.drawImage(image3,x+(int)((getBounds().getWidth()-image3.getWidth(owner))/2),y+(int)((getBounds().getHeight()-image3.getHeight(owner))/2),owner);
				g2d.rotate(-Math.toRadians(rotate2), x+(image3.getWidth(owner)/2)+((getBounds().getWidth()-image3.getWidth(owner))/2), y+(image3.getHeight(owner)/2)+((getBounds().getHeight()-image3.getHeight(owner))/2));
			}g2d.rotate(Math.toRadians(dir), x+(width/2)+((getBounds().getWidth()-width)/2), y+(height/2)+((getBounds().getHeight()-height)/2));
			g2d.drawImage(image,(int) (x+((getBounds().getWidth()-width)/2)),(int) (y+((getBounds().getHeight()-height)/2)),owner);
			g2d.rotate(-Math.toRadians(dir), x+(width/2)+((getBounds().getWidth()-width)/2), y+(height/2)+((getBounds().getHeight()-height)/2));	
	
		}
		rotate1+=5;
		rotate2-=5;
		rotate1%=360;
		if(rotate2<0)
			rotate2+=360;
		
		if (harmTimer > 0)
			g2d.drawImage(newImage("images/effects/heart.png"), x, y, owner);
		else if (slowTimer > 0)
			g2d.drawImage(newImage("images/effects/ice.png"), x, y, owner);

			// g2d.setFont(enFont);
			// g2d.setColor(Color.BLACK);
			// g2d.drawString("" + health, x, y - 10);
			drawBar((double) health / (double) maxHealth, g2d);
		
	}
	@Override
	public Rectangle getBounds(){
		Rectangle rect;
		if(phase<1){
			rect=new Rectangle(x, y, image2.getWidth(owner), image2.getHeight(owner));
		}
		else if(phase<2){
			rect=new Rectangle(x, y, image3.getWidth(owner), image3.getHeight(owner));
		}else{
		rect=	new Rectangle(x, y, width, height);
		}
		return rect;
	}
	public void fireAll(String loc,int speed,int times,boolean flying,int timer){
		int dirAdder=360/times;
		for(int c=0;c<times;c++){

		owner.getEnemies().add(new Projectile((dir+(c*dirAdder))%360, x+(int)getBounds().getWidth()/2, y+(int)getBounds().getHeight()/2, speed, this, loc, owner, flying));
		
		}	actTimer=timer;

	}
	@Override
	protected double pointTowards(Point a) {
		double d;
		a.y+=(phase==0?100:0);
		// Point at something, This will be useful for enemies, also in
		// ImportantLook class
		Point b = new Point(x//+(int)getBounds().getWidth()/2
				, y//+(int)getBounds().getHeight()/2
				);
		d = (double) (Math.toDegrees(Math.atan2(b.getY() + -(a.getY()), b.getX() + -a.getX())) + 180);
		return d;
	}
}

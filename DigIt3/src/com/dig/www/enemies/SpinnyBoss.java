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
Image image2;
Image image3;
	public SpinnyBoss(int x, int y, Board owner) {
		super(x, y, "Head", owner, true, 1000,
				"Head of Doom", 5,
				"music/zeldaCopyright.mp3","gunSFX/explosion-2.wav",
				"gunSFX/explosion-2.wav");
		// TODO Auto-generated constructor stub
		image2=newImage("Head");
		image3=newImage("Head");
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
								(int) y), new Point(owner.getCharacterX()+40,owner.getCharacterY()+40)),true,40);
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
	public Rectangle getOwnerPlusBounds(){
		return new Rectangle(-100,-100,owner.getWidth()+200,owner.getHeight()+220);	
		}
	public Image newImage(String name) {
		if(name.contains("/"))
		return super.newImage(name);	
		else
		return super.newImage(getPath() + name + ".png");
	}
	private String getPath() {

		
			
			
			
		return "images/enemies/bosses/"
		//+ "pod" + "/"
				;
	}
	public void myDraw(Graphics2D g2d){
		if (stunTimer > 0) {
			int x = this.x + (Statics.RAND.nextInt(5) * (Statics.RAND.nextBoolean() ? 1 : -1));
			int y = this.y + (Statics.RAND.nextInt(5) * (Statics.RAND.nextBoolean() ? 1 : -1));
		
			g2d.drawImage(image,x,y,owner);
			if(phase<1){
				g2d.drawImage(image2,x,y,owner);
			}
			if(phase<2){
				g2d.drawImage(image3,x,y,owner);
			}
		
		}
		
		else{
			g2d.drawImage(image,x,y,owner);
			if(phase<1){
				g2d.drawImage(image2,x,y,owner);
			}
			if(phase<2){
				g2d.drawImage(image3,x,y,owner);
			}
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

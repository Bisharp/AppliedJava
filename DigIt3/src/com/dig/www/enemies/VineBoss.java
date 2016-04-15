package com.dig.www.enemies;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

import com.dig.www.blocks.Block;
import com.dig.www.start.Board;
import com.dig.www.start.DigIt;
import com.dig.www.util.Statics;

public class VineBoss extends Boss{
	private int imNum;
	private int imTimer;
	private boolean moveActed;
	private static final int IM_TIMER_MAX=6;
	private boolean aonce=false;
	private Vine vine;
	public VineBoss(int x, int y,Board owner) {
		super(x, y, "n", owner, true, 1000, "Botanus", 10,"music/zeldaCopyright.mp3","gunSFX/explosion-2.wav","gunSFX/explosion-2.wav");
	damage=5;
	}
	public void  makeDeadExplosion(){
		super.makeDeadExplosion();
		owner.getEnemies().add(new Explosion(x, y, owner));
		if(vine!=null)
			vine.setP(x, y);
	}
	@Override
	public void animate() {
		if(!aonce){
			vine=new Vine(x+50, y+50+500, owner, this);
			owner.getEnemies().add(vine);
			aonce=true;
		}
		moveActed=false;
		// TODO Auto-generated method stub
		if(health<(maxHealth/3)&&phase<2){
			phase=2;
			Statics.playSound(owner,bossPhaseS);
			owner.getEnemies().add(new Explosion(x, y, owner));
		}if(health<(maxHealth/3)*2&&phase<1){
				phase=1;
				Statics.playSound(owner,bossPhaseS);
				owner.getEnemies().add(new Explosion(x, y, owner));
					
		}
		basicAnimate();
		boolean acted=sortAction();
		if(!acted&&active){
			if(!getBounds().intersects(getOwnerPlusBounds())){
				//realSeq=sequence;
				follow(5, 0, 1.5);
			}else{
			if(actTimer<=0){
				if(sequence==0){
				follow(200, 50,0.7);
				sequence++;}
				else if(sequence==1){
					createProjectile("images/enemies/blasts/pollen.png",15,
							Statics.pointTowards(new Point((int) x,
							(int) y), new Point(owner.getCharacterX()+40,owner.getCharacterY()+40)),true,40,width/2,height/4,15);
					sequence++;
				}else
					sequence=0;
				}}
			}
		if(!acted&&actTimer>0)
			actTimer--;
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
		imTimer-=owner.mult();
	}}
		vine.setP(x, y);
	}
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
			dire="down";
		}else if(Math.abs(dir-270)<45){
			dire="up";
		}
			
			
			
		return "images/enemies/bosses/" + "vineBoss" + "/" + dire + "/";
	}
	@Override
	public int getKillXP() {
		// TODO Auto-generated method stub
		return 0;
	}
	public void myDraw(Graphics2D g2d){
		if (stunTimer > 0) {
			int x = this.x + (Statics.RAND.nextInt(5) * (Statics.RAND.nextBoolean() ? 1 : -1));
			int y = this.y + (Statics.RAND.nextInt(5) * (Statics.RAND.nextBoolean() ? 1 : -1));
			if(Math.abs(dir)>=140&&Math.abs(dir)<=220){
			g2d.drawImage(image, x + width, y, -width, height, owner);
			if (owner.darkenWorld())
				g2d.drawImage(shadow, x+width, y,-width,height, owner);
		}else{
			g2d.drawImage(image, x, y, owner);
			if (owner.darkenWorld())
				g2d.drawImage(shadow, x, y, owner);
		}} else{if(Math.abs(dir)>=140&&Math.abs(dir)<=220){
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
			//vine
			}
	public Rectangle getOwnerPlusBounds(){
		return new Rectangle(-100,-100,owner.getWidth()+200,owner.getHeight()+220);	
		}
	public boolean sortAction() {
		if (attackNum == 0) {
			moveActed=true;
			moveDForward();
			for (Block b : owner.getWorld()) {
				if (!b.traversable() && b.getBounds().intersects(getBounds())) {
					followTimer = 0;
					attackNum = -1;
					dir += 180;

					// while(b.getBounds().intersects(getBounds()))
					// moveDForward();

					moveDForward();
					speedMulti = 1;
					break;
				}

			}
			followTimer-=owner.mult();
			if (followTimer == 0) {
				attackNum = -1;
				speedMulti = 1;
			}
			return true;
		} else if (attackNum == 1) {
			moveActed=true;
			pointAndMove();
			followTimer-=owner.mult();
			if (followTimer == 0) {
				attackNum = -1;
			}
			return true;
		}
		return false;
	}
}

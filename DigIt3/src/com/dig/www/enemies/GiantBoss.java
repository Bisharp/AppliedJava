package com.dig.www.enemies;

import java.awt.Graphics2D;
import java.awt.Polygon;

import com.dig.www.start.Board;
import com.dig.www.util.Irregular;
import com.dig.www.util.Statics;

public class GiantBoss extends Boss{
	public GiantBoss(int x, int y,Board owner) {
		super(x, y, "images/enemies/bosses/giantBoss/Body.png", owner, true, 1000, "Giant Boss", 0,"music/zeldaCopyright.mp3","gunSFX/explosion-2.wav","gunSFX/explosion-2.wav");
		// TODO Auto-generated constructor stub
	shadow=newShadow(loc);
	}
	@Override
	public void animate() {
		// TODO Auto-generated method stub
		basicAnimate();
	}
	@Override
	public int getDamage() {
		// TODO Auto-generated method stub
		if(health<maxHealth/2)
		return super.getDamage();
		return 0;
	}
	@Override
	public void myDraw(Graphics2D g2d) {
					
						if(health<maxHealth/2)
						g2d.drawImage(image, x, y, owner);
						else
						g2d.drawImage(shadow, x, y, owner);
						drawBar((double) health / (double) maxHealth, g2d);
					
	}
	
	@Override
	public int getKillXP() {
		// TODO Auto-generated method stub
		return 0;
	}

}

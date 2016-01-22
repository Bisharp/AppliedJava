package com.dig.www.character;

import java.awt.Graphics2D;

import com.dig.www.start.Board;

public class Puddle extends FProjectile{
protected int timer=0;
	public Puddle( int x, int y, GameCharacter maker,
			 Board owner) {
		super(0, x-20-50, y-20, 0, maker, "images/characters/projectiles/splotch.png", owner, Moves.MAC_S);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void animate(){
		
		basicAnimate();
		if(timer>0){
			timer--;
			if(timer==0){
				dead=true;
			}
		}
		onScreen=
				getBounds().intersects(owner.getScreen());
	}
	@Override
		public void setOnScreen(boolean onScreen) {
		}
	@Override
		public void draw(Graphics2D g2d) {
			// TODO Auto-generated method stub
		
			super.draw(g2d);
		}
	private final int TIMER_MAX=getMaker().getSpecialDamage();
public void timerGo(){
	if(timer==0)
	timer=TIMER_MAX;
}
}

package com.dig.www.objects;

import java.awt.Image;

import com.dig.www.character.GameCharacter;
import com.dig.www.character.Items;
import com.dig.www.start.Board;
import com.dig.www.util.Statics;

public class ThrownObject extends CollectibleObject {

	private boolean collectible = false;
	private int flyTimer = 5;

	private int scrollX;
	private int scrollY;
	private final int SPEED;
	
	public ThrownObject(int x, int y, String loc, Board owner, Items useItem) {
		super(x, y, loc, owner, useItem);
		
		// This allows characters to throw with different force
		SPEED = owner.getCharacter().getStrength();
		
//		int scrollX = owner.getScrollX();
//		int scrollY = owner.getScrollY();
//
//		double dir;
//		if (scrollX != 0 || scrollY != 0) {
//			dir = 0;
//			boolean changed = false;
//			if (scrollX < 0) {
//				dir = 0;
//				changed = true;
//			} else if (scrollX > 0) {
//				dir = 180;
//				changed = true;
//			}
//			if (scrollY < 0) {
//				if (changed) {
//
//					if (dir == 180) {
//						dir -= 45;
//					} else {
//						dir += 45;
//					}
//				} else {
//					dir = 90;
//				}
//			} else if (scrollY > 0) {
//				if (changed) {
//					if (dir == 180) {
//						dir += 45;
//					} else {
//						dir -= 45;
//					}
//				} else {
//					dir = 270;
//				}
//			}
//		} else {
//			dir = GameCharacter.Direction.getDir(owner.getCharacter().getDirection());
//		}
int dir=owner.getCharacter().getCurrentDir();
		Image img = owner.getCharacter().getImage();
		int aSpeed;
		// Moves the ball away from center of launcher's image
		if (img.getWidth(null) >= img.getHeight(null)) {
			aSpeed = (int) (img.getWidth(null) / 2);
		} else {
			aSpeed = (int) (img.getHeight(null) / 2);
		}
		// This is the move
		this.scrollX = (int) (Math.cos((double) Math.toRadians((double) dir)) * aSpeed);
		this.scrollY = (int) (Math.sin((double) Math.toRadians((double) dir)) * aSpeed);
		basicAnimate();
		
		if (this.scrollX > 0)
			this.scrollX = SPEED;
		else if (this.scrollX < 0)
			this.scrollX = -SPEED;
		
		if (this.scrollY > 0)
			this.scrollY = SPEED;
		else if (this.scrollY < 0)
			this.scrollY = -SPEED;
		
//		this.scrollX -= scrollX;
//		this.scrollY -= scrollY;
	}

	@Override
	public void animate() {
		super.animate();

		if (x < 0 || y < 0 || x > Statics.BOARD_WIDTH - width || y > Statics.BOARD_HEIGHT - height) {
			
			collectTrue();
			if (x < 0)
				x = 0;
			else if (x > Statics.BOARD_WIDTH)
				x = Statics.BOARD_WIDTH - width;
			
			if (y < 0)
				y = 0;
			else if (y > Statics.BOARD_HEIGHT)
				y = Statics.BOARD_HEIGHT - height;
		}
		
		if (!collectible) {
			if (flyTimer > 0)
				flyTimer--;
			else {
				if (scrollX > 0)
					scrollX--;
				else if (scrollX < 0)
					scrollX++;

				if (scrollY > 0)
					scrollY--;
				else if (scrollY < 0)
					scrollY++;

				if (scrollX == 0 && scrollY == 0)
					collectTrue();
			}

			x += scrollX;
			y += scrollY;
		}
	}
	
	private void collectTrue() {
		collectible = true;
		owner.getMovingObjects().remove(this);
	}

	public boolean collectible() {
		return collectible;
	}

	public boolean isWall() {
		return !collectible;
	}
	
	public void collideWall() {
		collectible = true;
	}
}

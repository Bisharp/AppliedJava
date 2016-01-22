package com.dig.www.character;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;

import com.dig.www.character.GameCharacter.Types;
import com.dig.www.character.Moves;
import com.dig.www.enemies.Enemy;
import com.dig.www.enemies.Launch;
import com.dig.www.objects.HookObject;
import com.dig.www.start.Board;
import com.dig.www.util.Sprite;
import com.dig.www.util.Statics;

public class FProjectile extends Sprite {
	protected boolean onScreen = true;
	protected boolean dead=false;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double d;
	private int speed;
	//private int charHoming = -2;
	//private boolean harming = true;
	//private ArrayList<HookObject> hooks = new ArrayList<HookObject>();
	//private boolean collideHook;
	private Moves move;
	// half height of image
	private int hImgX = image.getWidth(null) / 2;
	private int hImgY = image.getHeight(null) / 2;
	private GameCharacter maker;
	private boolean isTurning;

	public FProjectile(double dir, int x, int y, int speed, GameCharacter maker, String loc, Board owner, Moves move) {
		super(x, y, loc, owner);
		this.maker = maker;
		this.setMove(move);
		d = dir;
		this.speed = speed;
		Image img = maker.getImage();
		int aSpeed;
		// Moves the ball away from center of launcher's image
		if (img.getWidth(null) >= img.getHeight(null)) {
			aSpeed = (int) (img.getWidth(null) / 2);
		} else {
			aSpeed = (int) (img.getHeight(null) / 2);
		}
		// This is the move
		this.x += Math.cos((double) Math.toRadians((double) dir)) * aSpeed;
		this.y += Math.sin((double) Math.toRadians((double) dir)) * aSpeed;
//		for (int c = 0; c < owner.getObjects().size(); c++) {
//			if (owner.getObjects().get(c) instanceof HookObject)
//				hooks.add((HookObject) owner.getObjects().get(c));
//		}
	}
	public void setTurning(boolean b) {
		isTurning = b;
	}

	public void animate() {

		basicAnimate();
		x += Math.cos((double) Math.toRadians((double) d)) * speed;
		y += Math.sin((double) Math.toRadians((double) d)) * speed;
		if(onScreen){
		setOnScreen(
				getBounds().intersects(owner.getScreen()));}
		dead=!onScreen;
	}

	public boolean isOnScreen() {
		return onScreen;
	}

	public void setOnScreen(boolean onScreen) {
		this.onScreen = onScreen;

	}

	@Override
	public void draw(Graphics2D g2d) {
		// TODO Auto-generated method stub
		//dead=!onScreen;
//		if (!collideHook) {
//			if (move == Moves.CHAIN)
//				for (HookObject hook : hooks) {
//					if (hook.getBounds().intersects(getBounds())) {
//						collideHook = true;
//						charHoming = -1;
//						x = hook.getX();
//						y = hook.getY();
//						break;
//
//					}
//				}
			//if (move != Moves.CHAIN || !collideHook) {
//				if (charHoming == -2) {
//
//				} else if (charHoming == -1) {
//					d = Statics.pointTowards(new Point(x, y), new Point(owner.getCharacter().getX(), owner.getCharacter().getY()));
//				} else {
//					d = Statics.pointTowards(new Point(x, y), new Point(owner.getFriends().get(charHoming).getX(), owner.getFriends().get(charHoming)
//							.getY()));
//				}
			
			//}
		//}

		if (isTurning)
			g2d.rotate(Math.toRadians(d), x + width / 2, y + height / 2);
		g2d.drawImage(image, x, y, owner);
		if (owner.darkenWorld() && move != Moves.HAZE && move != Moves.WARP && move != Moves.DIMENSION)
			g2d.drawImage(shadow, x, y, owner);
		if (isTurning)
			g2d.rotate(-Math.toRadians(d), x + width / 2, y + height / 2);

	}

	public Moves getMove() {
		return move;
	}

	public void setMove(Moves move) {
		this.move = move;
	}

	public double getD() {
		return d;
	}

	public int getSpeed() {
		return speed;
	}

	public String getLoc() {
		return loc;
	}

	public Board getOwner() {
		return owner;
	}

//	public int getCharNum() {
//		return charHoming;
//	}
//
//	public boolean getHarming() {
//		return harming;
//	}

	public GameCharacter getMaker() {
		return maker;
	}

//	public boolean collideWithHook() {
//		return collideHook;
//	}
//
//	public void setCharNum(int setter) {
//		this.charHoming = setter;
//	}
public boolean isDead(){
	return dead;
}
protected void setD(double pointTowards) {
	// TODO Auto-generated method stub
	d=pointTowards;
}
}
package com.dig.www.character;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;

import com.dig.www.character.GameCharacter.Types;
import com.dig.www.character.Moves;
import com.dig.www.enemies.Enemy;
import com.dig.www.enemies.Launch;
import com.dig.www.start.Board;
import com.dig.www.util.Sprite;
import com.dig.www.util.Statics;

public class FProjectile extends Sprite {
protected boolean onScreen=true;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	double d;
	int speed;
	int charHoming=-2;
	boolean harming=true;
private Moves move;
	// half height of image
	int hImgX = image.getWidth(null) / 2;
	int hImgY = image.getHeight(null) / 2;
private GameCharacter maker;
	public FProjectile(double dir, int x, int y, int speed, GameCharacter maker, String loc, Board owner,Moves move) {
		super(x, y, loc, owner);
		this.maker=maker;
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
	}
	public FProjectile(double dir, int x, int y, int speed, GameCharacter maker, String loc, Board owner,Moves move,int charHoming,boolean harming) {
		super(x, y, loc, owner);
		this.maker=maker;
	this.harming=harming;
		this.charHoming=charHoming;
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
	}
//	public FProjectile(double dir, int x, int y, int speed, int maxImg, String loc,
//			Board owner, Moves move, int charHoming,boolean harming) {
//		super(x, y, loc, owner);
//		
//		this.harming=harming;
//		this.charHoming=charHoming;
//		this.setMove(move);
//		d = dir;
//		this.speed = speed;
//
//		
//		int aSpeed=maxImg;
//		// Moves the ball away from center of launcher's image
//		
//		// This is the move
//		this.x += Math.cos((double) Math.toRadians((double) dir)) * aSpeed;
//		this.y += Math.sin((double) Math.toRadians((double) dir)) * aSpeed;
//		// TODO Auto-generated constructor stub
//	}
	public void animate() {

		basicAnimate();
		// Move, This is the code Micah it is also in the ImportantLook class

		
//		if (!onScreen)
//			owner.getfP().remove(owner.getfP().indexOf(this));
	}
	public boolean isOnScreen() {
		return onScreen;
	}

	public void setOnScreen(boolean onScreen) {
		this.onScreen = onScreen;
		
	}
//	@Override
//	public void turnAround() {
//		// TODO Auto-generated method stub
//		owner.getfP().remove(owner.getfP().indexOf(this));
//	}

	@Override
	public void draw(Graphics2D g2d) {
		// TODO Auto-generated method stub
		if(charHoming==-2){
			
		}else if(charHoming==-1){
			d=Statics.pointTowards(new Point(x, y), new Point(owner.getCharacter().getX(),owner.getCharacter().getY()));
		}else{
			d=Statics.pointTowards(new Point(x, y), new Point(owner.getFriends().get(charHoming).getX(),owner.getFriends().get(charHoming).getY()));
		}
			
			
		x += Math.cos((double) Math.toRadians((double) d)) * speed;
		y += Math.sin((double) Math.toRadians((double) d)) * speed;
		g2d.drawImage(image, x, y, owner);
	}

	public Moves getMove() {
		return move;
	}

	public void setMove(Moves move) {
		this.move = move;
	}

	

	/* 
	 * TODO The below method will be deprecated upon Jonah's finishing of adding
	 * extra move code. If this is still present in the second semester, delete
	 * without mercy
	 */
	
	//TODO delete without mercy
//	@Override
//	public void interact(Types type) {
//
////		if (type != Types.CLUB)
////			super.interact(type);
////		else
////			alive = false;
//	}

	public double getD(){
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
	public int getCharNum() {
		return charHoming;
	}
	public boolean getHarming(){
		return harming;
	}
	public GameCharacter getMaker(){
		return maker;
	}
}
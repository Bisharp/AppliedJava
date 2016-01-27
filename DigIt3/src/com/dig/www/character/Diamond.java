package com.dig.www.character;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import com.dig.www.blocks.Block;
import com.dig.www.blocks.Block.Blocks;
import com.dig.www.start.Board;
import com.dig.www.util.Sprite;
import com.dig.www.util.Statics;

public class Diamond extends GameCharacter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Shield shield;

	public Diamond(int x, int y, Board owner, boolean player) {
		super(x, y, owner, Types.DIAMOND, "diamond", player, -50, -50, -50, 10,
				10, 10, 80, 10, 100, 0, 30, 75, 0, 20, 50, Statics.STRENGTH);

	}

	@Override
	protected void drawCSHUD(Graphics2D g2d) {

	}

	@Override
	public boolean canAct() {
		return true;
	}

	@Override
	public void animate() {
		//if (shield == null || !shield.collideWithHook()){
			super.animate();
			//}
		//else if (owner.getCharacter() != this)
		//		basicAnimate();
		
		if (shield == null) {
			for (int c = 0; c < owner.getfP().size(); c++) {
				if (owner.getfP().get(c)instanceof Shield) {
					shield = ((Shield)owner.getfP().get(c));
					break;
				}
			}
		}
		
		
			if (shield!=null&&shield.collideWithHook()) {

				image = newImage("g");
				s="g";
				int xP = 0;
				int yP = 0;
				
				if (y + 12 < shield.getY()) {
direction=Direction.DOWN;
					yP = -15;
				} else if (y - 12 > shield.getY()) {
direction=Direction.UP;
					yP = 15;
				}if (x + 12 < shield.getX()) {
direction=Direction.RIGHT;
					xP = -15;
				} else if (x - 12 > shield.getX()) {
direction=Direction.LEFT;
					xP = 15;
				}

				if (owner.getCharacter() == this) {
					owner.setScrollX(xP);
					owner.setScrollY(yP);

				} else {
					x -= xP;
					y -= yP;
				}
				
				
			
		}
			
			if (shield!=null&&!owner.getfP().contains(shield)) {
				if(shield.collideWithHook()){
					if(this==owner.getCharacter()){
						owner.setScrollX(x-shield.getX());
						owner.setScrollY(y-shield.getY());
					}else{
					x=shield.getX();
					y=shield.getY();}
				}
				shield = null;
			}
	}

	// }
	@Override
	public void getsActor() {
		

	}

	@Override
	public void keyPressed(int key) {

		super.keyPressed(key);

	}

	private static final int RANGE = 20;

	@Override
	public Rectangle getActBounds() {

		return new Rectangle(x - RANGE, y - RANGE, width + RANGE * 2, height
				+ RANGE * 2);
	}

	protected void drawTool(Graphics2D g2d) {

		if (toMoveString() != null) {
			g2d.drawImage(newImage(toMoveString()), x, y, owner);
			if (owner.darkenWorld())
				g2d.drawImage(newShadow(toMoveString()), x, y, owner);
		}


	}
	@Override
	public int rangedAddX(){
		return 0;
	}
	@Override
	public int rangedAddY(){
		return 0;
	}
	@Override
	public Moves getMove() {
		
		switch (getActing()) {
		case 1:
			return Moves.SHIELD;
		case 2:
			return Moves.CHAIN;
		case 3:
			return Moves.BASH;
		default:
			return Moves.NONE;
		}
	}

	@Override
	public String toMoveString() {
		switch (getActing()) {
		case 1:
			return getType().toString();

		case 3:
			return getType().toString();
		default:
		case 2:
			return null;
		}
	}

	@Override
	public Moves getRangedMove() {
		return Moves.CHAIN;
	}

	@Override
	public String getRangedString() {
		return "diamond.png";
	}
	public Shield getShield(){
		return shield;
	}
	// TODO collision
		public void collision(Sprite collide, boolean isPlayer) {
			if(shield==null||!shield.collideWithHook()||!(collide instanceof Block)|| (((Block)collide).getType()!=Blocks.LIQUID&&((Block)collide).getType()!=Blocks.PIT))
				super.collision(collide, isPlayer);
			
		
}}

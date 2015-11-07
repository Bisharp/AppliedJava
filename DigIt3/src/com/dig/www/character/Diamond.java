package com.dig.www.character;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import com.dig.www.start.Board;
import com.dig.www.util.Statics;

public class Diamond extends GameCharacter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FProjectile shield;

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
		if (shield == null || !shield.collideWithHook())
			super.animate();
		else {
			if (owner.getCharacter() != this)
				basicAnimate();
		}
		if (shield == null) {
			for (int c = 0; c < owner.getfP().size(); c++) {
				if (owner.getfP().get(c).getMove() == Moves.CHAIN) {
					shield = owner.getfP().get(c);
					break;
				}
			}
		}
		if (shield != null) {
			if (!owner.getfP().contains(shield)) {
				shield = null;
			} else if (shield.collideWithHook()) {

				image = newImage("g");

				int xP = 0;
				int yP = 0;
				if (x + 12 < shield.getX()) {

					xP = -15;
				} else if (x - 12 > shield.getX()) {

					xP = 15;
				}
				if (y + 12 < shield.getY()) {

					yP = -15;
				} else if (y - 12 > shield.getY()) {

					yP = 15;
				}

				if (owner.getCharacter() == this) {
					owner.setScrollX(xP);
					owner.setScrollY(yP);

				} else {
					x -= xP;
					y -= yP;
				}
			}
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
		}

		timersCount();

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
}

package com.dig.www.character;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import com.dig.www.character.GameCharacter.Direction;
import com.dig.www.character.GameCharacter.Types;
import com.dig.www.objects.Dispenser;
import com.dig.www.start.Board;
import com.dig.www.util.Statics;

public class Heart extends GameCharacter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int FIELD_HEAL = 1;
	private boolean usingField = false;

	public boolean usingField() {
		return usingField;
	}

	public void end() {
		enTimer = 5;
		usingField = false;
		owner.removeDispensers(this);
		specialTimer = 0;

	}

	public void start() {
		enTimer = Integer.MAX_VALUE;
		usingField = true;

	}

	public void decrementEnergy(int amount) {
		energy -= amount*owner.mult();
		enTimer = Integer.MAX_VALUE; // To keep you from restoring energy while
										// using a field.

		if (energy < 0) {
			energy = 0;
			end();
		}
	}

	// @Override
	// protected void timersCount() {
	// super.timersCount();
	//
	// if (usingField && enTimer == 5) {
	// energy -= 1;
	// }
	// }

	public Heart(int x, int y, Board owner, boolean player) {
		super(x, y, owner, Types.HEART, "heart", player, -30,

		-20, -10000, 20, 10, 10, 75, 10, 80,

		25, 15, 80, 20, 10, 0, Statics.STRENGTH - 5);

	}

	@Override
	protected void drawCSHUD(Graphics2D g2d) {
	}

	@Override
	public boolean canAct() {
		return false;
	}

	@Override
	public void getsActor() {
	}

	@Override
	public Rectangle getActBounds() {

		switch (direction) {
		case UP:
			return new Rectangle(x, y - Statics.BLOCK_HEIGHT + 30, Statics.BLOCK_HEIGHT, Statics.BLOCK_HEIGHT);
		case DOWN:
			return new Rectangle(x, y + Statics.BLOCK_HEIGHT - 50, Statics.BLOCK_HEIGHT, Statics.BLOCK_HEIGHT);
		case RIGHT:
		case DIAG_DR:
		case DIAG_UR:
			return new Rectangle(x + Statics.BLOCK_HEIGHT - 50, y, Statics.BLOCK_HEIGHT, Statics.BLOCK_HEIGHT);
		case LEFT:
		case DIAG_DL:
		case DIAG_UL:
		default:
			return new Rectangle(x - Statics.BLOCK_HEIGHT + 50, y, Statics.BLOCK_HEIGHT, Statics.BLOCK_HEIGHT);
		}
	}

	public int rangedAddY() {
		return height / 2 - 20;
	}

	@Override
	public void drawTool(Graphics2D g2d) {

		int dX = 0;
		int dY = 0;
		switch (direction) {
		case UP:
			dX = x;
			dY = y - Statics.BLOCK_HEIGHT + 30;
			break;

		case DOWN:
			dX = x;
			dY = y + Statics.BLOCK_HEIGHT - 50;
			break;

		case RIGHT:
		case DIAG_UR:
		case DIAG_DR:
			dX = x + Statics.BLOCK_HEIGHT - 50;
			dY = y;
			break;

		case LEFT:
		case DIAG_UL:
		case DIAG_DL:
			dX = x + 50;
			dY = y;
			break;
		}
		if (toMoveString() != null) {

			if (direction == Direction.LEFT||direction == Direction.DIAG_UL||direction == Direction.DIAG_DL) {
				Image anImg = newImage(toMoveString());
				g2d.drawImage(anImg, dX, dY, -anImg.getWidth(owner), anImg.getHeight(owner), owner);
			} else
				g2d.drawImage(newImage(toMoveString()), dX, dY, owner);
		}

	}

	@Override
	public Moves getMove() {
		switch (getActing()) {
		case 1:
			return Moves.AURA;
		case 2:
			return Moves.HAZE;
		case 3:
			return Moves.DISPENSER;
		default:
			return Moves.NONE;
		}
	}

	@Override
	public String toMoveString() {
		switch (getActing()) {
		case 1:
		case 2:
			return getType().toString();

		case 3:
		default:

			return null;
		}
	}

	@Override
	public Moves getRangedMove() {
		return Moves.HAZE;
	}

	@Override
	public String getRangedString() {
		return "haze.png";
	}
}

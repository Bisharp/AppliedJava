package com.dig.www.character;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.ObjectInputStream.GetField;

import com.dig.www.character.GameCharacter.Direction;
import com.dig.www.start.Board;
import com.dig.www.util.Statics;

public class Macaroni extends GameCharacter {
	Direction lastDirection;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected boolean keyReleased;

	public Macaroni(int x, int y, Board owner, boolean player) {
		super(x, y, owner, Types.MACARONI, "macaroni", player, -20, -25, -2000,
				50, 55, 50, 80, 10, 100, 10, 20, 50, 
				30, 25, 50,
				Statics.STRENGTH - 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Moves getMove() {
		// TODO Auto-generated method stub
		switch (getActing()) {
		case 1:
			return Moves.MAC_M;
		case 2:
			return Moves.MAC_R;
		case 3:
			return Moves.MAC_S;
		default:
			return Moves.NONE;
		}
	}

	@Override
	public String toMoveString() {
//		switch (getActing()) {
//		case 1:
//			return null;
//		case 2:
//			return null;
//		case 3:
//			return "splotch";
//		default:

			return null;
		//}
	}

	@Override
	public void animate() {
		super.animate();
		if (meleeTimer >= 0) {
			if (lastDirection == null)
				lastDirection = getDirection();
			stop();
			image = newImage("spin");
			s="spin";
			if (meleeTimer >= TIMER_MELEE * 0.75)
				direction = Direction.RIGHT;
			else if (meleeTimer >= TIMER_MELEE * 0.5)
				direction = Direction.DOWN;
			else if (meleeTimer >= TIMER_MELEE * 0.25)
				direction = Direction.LEFT;
			else
				direction = Direction.UP;

		} else {
			lastDirection = null;
			if (rangedTimer >= 0) {
				image = newImage("s");
				s="s";
				stop();
			} else if (specialTimer >= 0) {
				stop();
				image = newImage("s");
				s="s";
			}
		}
	};

	@Override
	public void drawTool(Graphics2D g2d) {
//		if (toMoveString() != null) {
//			g2d.drawImage(newImage(toMoveString()), x, y, owner);
//			if (owner.darkenWorld())
//				g2d.drawImage(newShadow(toMoveString()), x, y, owner);
//		}
	}

	@Override
	public Moves getRangedMove() {
		// TODO Auto-generated method stub
		return Moves.WIZ_R;
	}

	@Override
	public int rangedAddX() {
		return 13;
	}

	public int rangedAddY() {
		return height / 2 - 14;
	}

	@Override
	public String getRangedString() {
		return "cheese.png";
	}

	private static final int RANGE = 20;

	public Rectangle getActBounds() {
		return new Rectangle(x - RANGE, y - RANGE, width + RANGE * 2, height
				+ RANGE * 2);
	}

	@Override
	protected void drawCSHUD(Graphics2D g2d) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean canAct() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void getsActor() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isCharacterSkin(String name) {
		String[] playerSkinsM = new String[] { "n", "w0", "w1", "w2", "w3",
				"g", "s", "spin" };
		for (String s : playerSkinsM)
			if (s.equals(name))
				return true;
		return false;
	}
}
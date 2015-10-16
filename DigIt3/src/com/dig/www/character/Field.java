package com.dig.www.character;

import java.awt.Color;
import java.awt.Graphics2D;

import com.dig.www.start.Board;
import com.dig.www.util.Sprite;
import com.dig.www.util.Statics;

public class Field extends FProjectile {

	public static final int SIDE = Statics.BLOCK_HEIGHT * 3;

	public static final Color FIELD0 = // new Color(63, 72, 204, 100);
	new Color(255, 0, 255, 50);
	public static final Color FIELD1 = // new Color(63, 72, 204, 100);
	new Color(255, 0, 255, 75);
	public static final Color FIELD2 = // new Color(63, 72, 204, 100);
	new Color(255, 0, 255, 100);
	public static final Color FIELD3 = // new Color(63, 72, 204, 100);
	new Color(255, 0, 255, 25);

	private int[][] vars;

	public Field(double dir, int x, int y, Sprite maker, String loc, Board owner) {
		super(dir, x, y, 0, maker, loc, owner, Moves.DISPENSER);

		this.x = x - SIDE / 3;
		this.y = y - SIDE / 3;

		width = SIDE;
		height = SIDE;
	}

	public void draw(Graphics2D g2d) {

		g2d.setColor(getColor());
		g2d.fillOval(x, y, SIDE, SIDE);

		vars = new int[4][2];
		for (int i = 0; i < 4; i++) {
			for (int c = 0; c < 2; c++) {
				vars[i][c] = getVal(c == 0);
			}
		}

		g2d.setColor(Color.WHITE);
		for (int[] var : vars)
			g2d.fillRect(var[0], var[1], 10, 10);
	}

	private int getVal(boolean x) {
		return (x ? getMidX() : getMidY()) + (Statics.RAND.nextInt(width / 2) * (Statics.RAND.nextBoolean() ? 1 : -1));
	}

	private Color getColor() {
		switch (Statics.RAND.nextInt(4)) {
		case 0:
			return FIELD0;
		case 1:
			return FIELD1;
		case 2:
			return FIELD2;
		default:
			return FIELD3;
		}
	}

	public void setOnScreen(boolean onScreen) {

	}

	public void basicAnimate() {

	}

	public void dBasicAnimate() {
		super.basicAnimate();
	}
}

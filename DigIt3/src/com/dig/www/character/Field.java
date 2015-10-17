package com.dig.www.character;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;

import com.dig.www.start.Board;
import com.dig.www.util.Irregular;
import com.dig.www.util.Sprite;
import com.dig.www.util.Statics;

public class Field extends FProjectile implements Irregular {

	public static final int SIDE = Statics.BLOCK_HEIGHT * 3;
	private Polygon field;

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

		field();
	}

	private void field() {
		int[] xs = new int[] { x,
				x + SIDE / 6,
				x + SIDE / 2,
				x + SIDE - SIDE / 6,
				x + SIDE,
				x + SIDE - SIDE / 6,
				x + SIDE / 2,
				x + SIDE / 6,
				};
		int[] ys = new int[] { y + SIDE / 2,
				y + SIDE / 10,
				y,
				y + SIDE / 10,
				y + SIDE / 2,
				y + SIDE - SIDE / 10,
				y + SIDE,
				y + SIDE - SIDE / 10,
				};

		field = new Polygon(xs, ys, xs.length);
	}

	public void draw(Graphics2D g2d) {

		g2d.setColor(getColor());
		g2d.fillOval(x - 10, y - 10, SIDE + 20, SIDE + 20);

		vars = new int[4][2];
		for (int i = 0; i < 4; i++) {
			for (int c = 0; c < 2; c++) {
				vars[i][c] = getVal(c == 0);
			}
		}

		g2d.setColor(Color.WHITE);
		for (int[] var : vars) {
			Rectangle rect = new Rectangle(var[0], var[1], 10, 10);

			if (field.intersects(rect))
				g2d.fill(rect);
		}
		
//		g2d.setColor(Color.blue);
//		g2d.draw(field);
	}

	private int getVal(boolean x) {
		return (x ? getMidX() : getMidY()) + (Statics.RAND.nextInt((int) (SIDE / 2)) * (Statics.RAND.nextBoolean() ? 1 : -1));
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

	public void animate() {
		super.animate();
		field();
	}
	
	public Polygon getIrregularBounds() {
		return field;
	}
}

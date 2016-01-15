package com.dig.www.objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

import com.dig.www.start.Board;
import com.dig.www.util.Irregular;
import com.dig.www.util.Statics;

public class LightSpot extends Light implements Irregular {
	
	private int size;
	private boolean active = false;
	private static final int OFFSET = 15;
	
	public LightSpot(int x, int y, String loc, Board owner, int size) {
		super(x - OFFSET, y - OFFSET, loc, owner, Statics.DUMMY);
		this.size = size * Statics.BLOCK_HEIGHT + OFFSET * 2;
		shadow = new ImageIcon().getImage();
	}

	@Override
	public void draw(Graphics2D g2d) {
//		g2d.setColor(Color.RED);
//		g2d.draw(getIrregularBounds());
	}
	
	protected void switchLight() {
		active = !active;
	}
	protected boolean isActive() {
		return active;
	}

	@Override
	public Polygon getIrregularBounds() {
		
		if (!active)
			return new Polygon();
		
		int[] xs = new int[] { x,
//				x + size / 6,
				x + size / 2,
//				x + size - size / 6,
				x + size,
//				x + size - size / 6,
				x + size / 2,
//				x + size / 6,
				};
		int[] ys = new int[] { y + size / 2,
//				y + size / 10,
				y,
//				y + size / 10,
				y + size / 2,
//				y + size - size / 10,
				y + size,
//				y + size - size / 10,
				};

		return new Polygon(xs, ys, xs.length);
	}
	
	@Override
	public boolean interact() {
		return false;
	}
}

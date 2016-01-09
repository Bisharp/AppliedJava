package com.dig.www.objects;

import java.awt.Graphics2D;

import com.dig.www.start.Board;
import com.dig.www.util.Statics;

public class Light extends Objects {

	private int distance;
	private int width;
	private boolean horizontal = false;

	public Light(int x, int y, Board owner, int width, boolean horizontal, boolean left) {
		super(x, y, Statics.DUMMY, false, owner, Statics.DUMMY);

		distance = left ? Integer.MIN_VALUE : Integer.MAX_VALUE;
		this.width = width;
		this.horizontal = horizontal;
	}

	// Will need some stuff changed in the superclass.
	@Override
	public void collideWall() {

	}

	@Override
	public void draw(Graphics2D g2d) {
//		if (owner.darkenWorld()) {
			g2d.setColor(Statics.LIGHT);
			g2d.fillRect(x, y, horizontal ? distance : width, horizontal ? width : distance);
//		}
			
			g2d.drawImage(image, x, y, owner);
	}
}

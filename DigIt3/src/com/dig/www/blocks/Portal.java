package com.dig.www.blocks;

import java.awt.Graphics2D;

import com.dig.www.start.Board;
import com.dig.www.util.Sprite;

public class Portal extends Sprite {

	protected final String area;
	protected final int collectibleNum;
	protected final String type;
	protected static final int TIMER_MAX = 10;
	protected static final int SKIN_MAX = 3;

	protected int skin = 0;
	protected int timer = TIMER_MAX;

	public Portal(int x, int y, Board owner, String area, int collectibles, String type) {
		super(x, y, "images/portals/" + type + "/0.png", owner);

		this.area = area;
		this.type = type;
		collectibleNum = collectibles;

		System.out.println("New portal");
		// TODO Auto-generated constructor stub
	}

	public String getArea() {
		return area;
	}

	@Override
	public void animate() {
		// TODO Auto-generated method stub
		basicAnimate();

		timer--;
		if (timer <= 0) {

			image = newImage("images/portals/" + type + "/" + skin + ".png");
			skin++;
			timer = TIMER_MAX;
			
			if (skin > SKIN_MAX)
				skin = 0;
		}
	}

	@Override
	public void draw(Graphics2D g2d) {
		// TODO Auto-generated method stub
		g2d.drawImage(image, x, y, owner);
	}

	public int getCollectibleNum() {
		return collectibleNum;
	}
}

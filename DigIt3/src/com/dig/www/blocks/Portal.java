package com.dig.www.blocks;

import java.awt.Graphics2D;

import com.dig.www.start.Board;
import com.dig.www.util.Sprite;

public class Portal extends Sprite {
	
	protected String area;
	protected int collectibleNum;

	public Portal(int x, int y, String loc, Board owner, String area, int collectibles) {
		super(x, y, loc, owner);
		
		this.area = area;
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

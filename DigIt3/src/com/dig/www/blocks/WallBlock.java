package com.dig.www.blocks;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.dig.www.start.Board;
import com.dig.www.util.Statics;

public class WallBlock extends HardBlock {

	public WallBlock(int x, int y, Board owner) {
		super(x, y, Statics.DUMMY, owner, Blocks.WALL);
		// TODO Auto-generated constructor stub
	}

	protected static Color c;

	@Override
	public void draw(Graphics2D g2d) {

		switch (owner.getTexturePack()) {
		case ISLAND:
		case SNOWY:
			g2d.setColor(getColor());
			g2d.fill(getBounds());
			g2d.setColor(computeColor(Statics.BROWN));
			g2d.fill(new Rectangle(x, y + 20, 100, 10));
			g2d.fill(new Rectangle(x, y + 70, 100, 10));
			g2d.setColor(Color.BLACK);
			g2d.draw(getBounds());
			break;
			
		default:
			g2d.setColor(getColor());
			g2d.fill3DRect(x, y, width, height, true);
			break;
		}
	}

	@Override
	public Color getColor() {
		switch (owner.getTexturePack()) {
		case DESERT:
			c = Statics.SAND_STONE;
			break;
		case SNOWY:
		case ISLAND:
			c = Statics.LIGHT_BROWN;
			break;
		case HAUNTED:
			c = Statics.DARK_SAND_BLUE;
			break;
		default:
			c = Color.DARK_GRAY;
			break;
		}

		return computeColor(c);
	}
}

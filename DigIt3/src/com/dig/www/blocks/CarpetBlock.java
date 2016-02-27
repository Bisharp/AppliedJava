package com.dig.www.blocks;

import java.awt.Color;
import java.awt.Graphics2D;

import com.dig.www.start.Board;
import com.dig.www.util.Statics;

public class CarpetBlock extends HardBlock {

	public CarpetBlock(int x, int y, Board owner) {
		super(x, y, Statics.DUMMY, owner, Blocks.CARPET);
	}
	
	public void draw(Graphics2D g2d) {
		
		if (!canSee(g2d))
			return;
		
		switch (owner.getTexturePack()) {
		case DESERT:
			drawStanTile(g2d, Statics.LIGHT_OFF_TAN);
			break;
		case HAUNTED:
			drawStanTile(g2d, Color.GRAY);
			break;
		case LAB:
			drawStanTile(g2d, Color.GRAY);
			break;
		case ISLAND:
		case SNOWY:
			g2d.setColor(getColor());
			g2d.fill(getBounds());
			g2d.setColor(computeColor(Statics.LIGHT_BROWN));
			g2d.fillRect(x, y + 20, 100, 10);
			g2d.fillRect(x, y + 70, 100, 10);
			break;
		case VOLCANO:
			g2d.setColor(getColor());
			g2d.fill(getBounds());
			g2d.setColor(computeColor(Color.LIGHT_GRAY));
			g2d.drawLine(x, y + 15, x + width, y + 15);
			g2d.drawLine(x, y + height - 14, x + width, y + height - 14);
			g2d.drawLine(x, y + height / 2, x + width, y + height / 2);
			g2d.drawLine(x + width / 2, y, x + width / 2, y + height);
			break;
		case GRASSY:
		default:
			drawStanTile(g2d, Color.RED);
			break;
		}
	}
	
	protected void drawStanTile(Graphics2D g2d, Color secondaryColor) {
		g2d.setColor(getColor());
		g2d.fill(getBounds());
		g2d.setColor(computeColor(secondaryColor));
		g2d.drawLine(x, y, x + width, y + height);
		g2d.drawLine(x + width, y, x, y + height);
		g2d.drawLine(x, y + height / 2, x + width, y + height / 2);
		g2d.drawLine(x + width / 2, y, x + width / 2, y + height);
	}

	protected static Color c;

	@Override
	public Color getColor() {
		switch (owner.getTexturePack()) {
		case DESERT:
			c = Statics.TAN;
			break;
		case HAUNTED:
			c = Statics.DARK_GREEN;
			break;
		case LAB:
			c = Color.WHITE;
			break;
		case ISLAND:
		case SNOWY:
			c = Statics.BROWN;
			break;
		case VOLCANO:
			c = Color.DARK_GRAY;
			break;
		case GRASSY:
		default:
			c = Statics.TAN;
			break;
		}
		
		return computeColor(c);
	}

}

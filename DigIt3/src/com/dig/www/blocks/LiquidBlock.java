package com.dig.www.blocks;

import java.awt.Color;
import java.awt.Graphics2D;

import com.dig.www.start.Board;
import com.dig.www.util.Statics;

public class LiquidBlock extends HardBlock {

	public LiquidBlock(int x, int y, Board owner) {
		super(x, y, Statics.DUMMY, owner, Blocks.LIQUID);
	}

	public void draw(Graphics2D g2d) {
		
		if (!canSee(g2d))
			return;

		switch (owner.getTexturePack()) {
		case DESERT:
			g2d.setColor(getColor());
			g2d.fill(getBounds());
			g2d.setColor(computeColor(Statics.LIGHT_BLUE));
			g2d.fillRect(x + 60, y + 67, 40, 3);
			g2d.fillRect(x, y + 70, 60, 3);
			break;
		case HAUNTED:
			g2d.setColor(getColor());
			g2d.fill(getBounds());
			g2d.setColor(computeColor(Statics.LIGHT_SAND_BLUE));
			g2d.fillRect(x, y + 30, 40, 4);
			g2d.fillRect(x + 80, y + 76, 20, 4);
			g2d.fillRect(x, y + 80, 80, 4);
			g2d.fillRect(x + 40, y + 26, 60, 4);
			break;
		case ISLAND:
			g2d.setColor(getColor());
			g2d.fill(getBounds());
			g2d.setColor(computeColor(Statics.BLUE));
			g2d.fillRect(x + 70, y + 65, 30, 5);
			g2d.fillRect(x, y + 70, 70, 5);
			g2d.fillRect(x + 40, y + 25, 60, 5);
			g2d.fillRect(x, y + 30, 40, 5);
			break;
		case LAB:
			g2d.setColor(getColor());
			g2d.fill(getBounds());

			g2d.setColor(computeColor(Color.GREEN));
			g2d.fillOval(x + 70, y + 10, 5, 5);
			g2d.fillOval(x + 30, y + 50, 10, 10);
			g2d.fillOval(x + 80, y + 80, 10, 10);

			break;
		case SNOWY:
			g2d.setColor(getColor());
			g2d.fill(getBounds());
			g2d.setColor(computeColor(Statics.LIGHT_BLUE));
			g2d.fillRect(x + 70, y + 65, 10, 10);
			g2d.fillRect(x + 30, y + 30, 15, 15);
			break;
		case VOLCANO:
			g2d.setColor(Statics.ORANGE);
			g2d.fill(getBounds());
			g2d.setColor(Color.ORANGE);
			g2d.fillRect(x + 70, y + 65, 10, 10);
			g2d.fillRect(x + 30, y + 30, 15, 15);
			break;
		case GRASSY:
		default:
			g2d.setColor(getColor());
			g2d.fill(getBounds());
			g2d.setColor(computeColor(Statics.LIGHT_BLUE));
			g2d.fillRect(x, y + 30, 40, 4);
			g2d.fillRect(x + 80, y + 76, 20, 4);
			g2d.fillRect(x, y + 80, 80, 4);
			g2d.fillRect(x + 40, y + 26, 60, 4);
			break;
		}
	}

	protected static Color c;

	@Override
	public Color getColor() {
		switch (owner.getTexturePack()) {
		case DESERT:
			c = Statics.DESERT_BLUE;
			break;
		case VOLCANO:
			c = Statics.ORANGE;
			break;
		case ISLAND:
			c = Color.BLUE;
			break;
		default:
			c = Statics.BLUE;
			break;
		}

		return computeColor(c);
	}

}

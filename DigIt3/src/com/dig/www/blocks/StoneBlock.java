package com.dig.www.blocks;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.dig.www.start.Board;
import com.dig.www.util.Statics;

public class StoneBlock extends HardBlock {

	public StoneBlock(int x, int y, Board owner, boolean hasCrystal) {
		super(x, y, Statics.DUMMY, owner, hasCrystal? Blocks.CRYSTAL : Blocks.ROCK);
	}
	
	protected static Color c;
	
	@Override
	public void draw(Graphics2D g2d) {
		
		if (!canSee(g2d))
			return;
		
		if (type == Blocks.CRYSTAL) {
			g2d.setColor(computeColor(Statics.LIGHT_BLUE));
			g2d.fill3DRect(x, y, width, height, true);
		} else
			switch (owner.getTexturePack()) {
			case GRASSY:
				g2d.setColor(getColor());
				g2d.fill(getBounds());
				g2d.setColor(computeColor(Statics.MED_GRAY));
				g2d.fill(new Rectangle(x, y + 30, 4, 4));
				g2d.fill(new Rectangle(x + 60, y + 26, 4, 4));
				g2d.fill(new Rectangle(x, y + 80, 4, 4));
				// g2d.draw(getBounds());
				break;
			case HAUNTED:
				g2d.setColor(getColor());
				g2d.fill(getBounds());
				g2d.setColor(Color.BLACK);

				// Brick pattern
				int i;
				for (i = 0; i < 10; i++)
					g2d.fill(new Rectangle(x, y + (10 * i) - (i != 0 ? 2 : 0), width, i != 0 ? 5 : 3));
				g2d.fill(new Rectangle(x, y + 98, width, 2));
				for (i = 0; i < 6; i++)
					g2d.fill(new Rectangle(x + (20 * i) - (i != 0 ? 2 : 0), y, i != 0 ? 5 : 3, height));
				break;
			case LAB:
				g2d.setColor(getColor());
				g2d.fill(getBounds());
				g2d.setColor(Color.BLACK);

				for (i = 0; i < 5; i++)
					g2d.drawLine(x, y + (20 * i), x + width - 1, y + (20 * i));

				for (i = 0; i < 5; i++)
					for (int c = 0; c < 5; c++)
						g2d.fillRect(x + (20 * c) + 8, y + (20 * i) + 2, 2, 2);

				break;
			case VOLCANO:
				g2d.setColor(getColor());
				g2d.fill(getBounds());
				g2d.setColor(computeColor(Statics.MED_GRAY));
				g2d.fillRect(x, y + 30, 4, 4);
				g2d.fillRect(x + 60, y + 26, 4, 4);
				g2d.fillRect(x, y + 80, 4, 4);
				break;
				
			// Texture add test
			case EVIL:
				g2d.setColor(getColor());
				g2d.fillRect(x, y, width, height);
				g2d.setColor(Color.RED);
				g2d.fillRect(x + 25, y + 25, width - 50, height - 50);
				break;
			// End
				
			default:
				g2d.setColor(getColor());
				g2d.fill(getBounds());
				break;
			}
	}

	@Override
	public Color getColor() {
		if (type == Blocks.CRYSTAL)
			c = Statics.LIGHT_BLUE;
		else
			switch (owner.getTexturePack()) {
			case DESERT:
				c = Statics.TAN;
				break;
			case HAUNTED:
				c = Statics.SAND_RED;
				break;
			case GRASSY:
				c = Color.LIGHT_GRAY;
				break;
			
			// Adding
			case EVIL:
				c = Color.black;
				break;
			// end
			
			default:
				c = Color.GRAY;
				break;
			}

			return computeColor(c);
	}

}

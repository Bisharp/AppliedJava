package com.dig.www.blocks;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.dig.www.start.Board;
import com.dig.www.util.Statics;

public class TerrainBlock extends Block {

	public TerrainBlock(int x, int y, Board owner, Blocks type) {
		super(x, y, Statics.DUMMY, owner, type);
	}

	protected static Color c;

	public void draw(Graphics2D g2d) {

		if (!canSee(g2d))
			return;

		if (type == Blocks.GROUND)
			drawGrass(g2d);
		else if (type == Blocks.DIRT)
			drawDirt(g2d);
	}

	public boolean canSee(Graphics2D g2d) {
		if (canSee && type != Blocks.PIT)
			return true;

		g2d.setColor(Color.BLACK);
		g2d.fillRect(x, y, width, height);
		return false;
	}

	protected void drawGrass(Graphics2D g2d) {
		switch (owner.getTexturePack()) {
		case DESERT:
			g2d.setColor(getColor());
			g2d.fill(getBounds());
			g2d.setColor(computeColor(Statics.LIGHT_OFF_TAN));
			g2d.fill(new Rectangle(x, y + 30, 60, 4));
			g2d.fill(new Rectangle(x + 60, y + 26, 40, 4));
			g2d.fill(new Rectangle(x, y + 80, 30, 4));
			g2d.fill(new Rectangle(x + 30, y + 76, 70, 4));
			g2d.draw(getBounds());
			break;
		case HAUNTED:
			g2d.setColor(getColor());
			g2d.fill(getBounds());
			g2d.setColor(computeColor(Statics.LIGHT_SAND_BLUE));
			g2d.fill(new Rectangle(x + 30, y + 15, 4, 10));
			g2d.fill(new Rectangle(x + 80, y + 20, 4, 10));
			g2d.fill(new Rectangle(x + 20, y + 80, 4, 10));
			g2d.setColor(computeColor(Statics.DARK_SAND_BLUE));
			g2d.fill(new Rectangle(x + 70, y + 30, 5, 5));
			g2d.fill(new Rectangle(x + 40, y + 70, 5, 5));
			g2d.fill(new Rectangle(x + 10, y + 15, 3, 3));
			g2d.draw(getBounds());
			break;
		case ISLAND:
			g2d.setColor(getColor());
			g2d.fill(getBounds());
			g2d.setColor(computeColor(Statics.OFF_TAN));
			g2d.fill(new Rectangle(x + 70, y + 30, 5, 5));
			g2d.fill(new Rectangle(x + 40, y + 70, 5, 5));
			g2d.fill(new Rectangle(x + 10, y + 15, 3, 3));
			g2d.setColor(computeColor(Statics.LIGHT_OFF_GREEN));
			g2d.fill(new Rectangle(x + 30, y + 15, 4, 10));
			g2d.fill(new Rectangle(x + 80, y + 20, 4, 10));
			g2d.fill(new Rectangle(x + 20, y + 80, 4, 10));
			g2d.draw(getBounds());
			break;
		case LAB:
			g2d.setColor(getColor());
			g2d.fill(getBounds());
			g2d.setColor(computeColor(Statics.DRAB_BROWN));
			g2d.fillRect(x + 10, y + 70, 5, 5);
			g2d.fillRect(x + 50, y + 30, 10, 10);
			g2d.fillRect(x + 80, y + 80, 10, 10);

			g2d.setColor(computeColor(Color.WHITE));
			g2d.fillRect(x + 85, y + 85, 5, 10);
			g2d.fillRect(x + 75, y + 35, 5, 10);
			g2d.fillRect(x + 45, y + 45, 5, 10);

			g2d.setColor(computeColor(Color.RED));
			g2d.fillOval(x + 80, y + 80, 15, 10);
			g2d.fillOval(x + 70, y + 30, 15, 10);
			g2d.fillOval(x + 40, y + 40, 15, 10);
			break;
		case SNOWY:
			g2d.setColor(getColor());
			g2d.fill(getBounds());
			g2d.setColor(computeColor(Color.LIGHT_GRAY));
			g2d.fill(new Rectangle(x + 60, y + 40, 4, 4));
			g2d.fill(new Rectangle(x + 30, y + 80, 3, 3));
			g2d.draw(getBounds());
			break;
		case VOLCANO:
			g2d.setColor(getColor());
			g2d.fill(getBounds());
			g2d.setColor(computeColor(Statics.OFF_GREEN));
			g2d.fill(new Rectangle(x + 30, y + 15, 4, 10));
			g2d.fill(new Rectangle(x + 80, y + 20, 4, 10));
			g2d.fill(new Rectangle(x + 20, y + 80, 4, 10));
			g2d.setColor(computeColor(Color.GRAY));
			g2d.fill(new Rectangle(x + 70, y + 30, 5, 5));
			g2d.fill(new Rectangle(x + 40, y + 70, 5, 5));
			g2d.fill(new Rectangle(x + 10, y + 15, 3, 3));
			g2d.setColor(computeColor(Color.DARK_GRAY));
			g2d.draw(getBounds());
			break;

		// Adding texture test
		case EVIL:
			g2d.setColor(getColor());
			g2d.fillRect(x, y, width, height);
			g2d.setColor(Color.RED);
			g2d.fillRect(x, y, 10, height);
			g2d.fillRect(x + width / 2 - 5, y, 10, height);
			g2d.fillRect(x + width - 10, y, 10, height);
			break;
		// End

		case GRASSY:
		default:
			g2d.setColor(getColor());
			g2d.fill(getBounds());
			g2d.setColor(computeColor(Statics.LIGHT_OFF_GREEN));
			g2d.fill(new Rectangle(x + 30, y + 15, 4, 10));
			g2d.fill(new Rectangle(x + 80, y + 20, 4, 10));
			g2d.fill(new Rectangle(x + 20, y + 80, 4, 10));
			g2d.fill(new Rectangle(x + 60, y + 60, 4, 10));
			g2d.draw(getBounds());
			break;
		}
	}

	protected void drawDirt(Graphics2D g2d) {
		switch (owner.getTexturePack()) {
		case DESERT:
			g2d.setColor(getColor());
			g2d.fill(getBounds());
			break;
		case HAUNTED:
			g2d.setColor(getColor());
			g2d.fill(getBounds());
			g2d.setColor(computeColor(Statics.SAND_BLUE));
			g2d.fill(new Rectangle(x + 70, y + 65, 10, 10));
			g2d.fill(new Rectangle(x + 30, y + 30, 15, 15));
			g2d.fill(new Rectangle(x + 65, y + 20, 15, 15));
			g2d.fill(new Rectangle(x + 50, y + 70, 20, 20));
			g2d.draw(getBounds());
			break;
		case ISLAND:
			g2d.setColor(getColor());
			g2d.fill(getBounds());
			g2d.setColor(computeColor(Statics.LIGHT_OFF_TAN));
			g2d.fill(new Rectangle(x, y + 30, 60, 4));
			g2d.fill(new Rectangle(x + 60, y + 26, 40, 4));
			g2d.fill(new Rectangle(x, y + 80, 30, 4));
			g2d.fill(new Rectangle(x + 30, y + 76, 70, 4));
			g2d.draw(getBounds());
			break;
		case LAB:
			g2d.setColor(getColor());
			g2d.fill(getBounds());
			g2d.setColor(computeColor(Statics.DRAB_BROWN));
			g2d.fillRect(x + 10, y + 70, 5, 5);
			g2d.fillRect(x + 50, y + 30, 10, 10);
			g2d.fillRect(x + 80, y + 80, 10, 10);
			break;
		case SNOWY:
			g2d.setColor(getColor());
			g2d.fill(getBounds());
			g2d.setColor(computeColor(Color.WHITE));
			g2d.fill(new Rectangle(x + 70, y + 30, 5, 5));
			g2d.fill(new Rectangle(x + 40, y + 70, 5, 5));
			g2d.fill(new Rectangle(x + 10, y + 15, 3, 3));
			g2d.setColor(computeColor(Color.LIGHT_GRAY));
			g2d.draw(getBounds());
			break;
		case VOLCANO:
			g2d.setColor(getColor());
			g2d.fill(getBounds());
			g2d.setColor(computeColor(Color.GRAY));
			g2d.fill(new Rectangle(x, y + 30, 4, 4));
			g2d.fill(new Rectangle(x + 60, y + 26, 4, 4));
			g2d.fill(new Rectangle(x, y + 80, 4, 4));
			g2d.setColor(computeColor(Color.DARK_GRAY));
			g2d.draw(getBounds());
			break;
			
		// Adding texture test
		case EVIL:
			g2d.setColor(getColor());
			g2d.fillRect(x, y, width, height);
			g2d.setColor(Color.RED);
			g2d.fillRect(x, y, width, 10);
			g2d.fillRect(x, y + height / 2 - 5, width, 10);
			g2d.fillRect(x, y + height - 10, width, 10);
			break;
		// End
			
		case GRASSY:
		default:
			g2d.setColor(getColor());
			g2d.fill(getBounds());
			g2d.setColor(computeColor(Statics.DRAB_BROWN));
			// g2d.fillRect(x + 70, y + 10, 20, 20);
			g2d.fillRect(x + 10, y + 70, 5, 5);
			g2d.fillRect(x + 50, y + 30, 10, 10);
			g2d.fillRect(x + 80, y + 80, 10, 10);
			// g2d.draw(getBounds());
			break;
		}
	}

	public Color getColor() {
		if (traversable() && owner.isCorruptedWorld())
			return corruptedColor1;

		if (type == Blocks.GROUND)
			c = getColorGrass();
		else if (type == Blocks.DIRT)
			c = getColorDirt();

		return computeColor(c);
	}

	protected Color getColorGrass() {

		switch (owner.getTexturePack()) {
		case DESERT:
			return Statics.OFF_TAN;
		case HAUNTED:
			return Statics.SAND_BLUE;
		case ISLAND:
			return Statics.OFF_GREEN;
		case LAB:
			return Statics.BROWN;
		case SNOWY:
			return Color.WHITE;
		case VOLCANO:
			return Statics.BROWN;

			// Adding texture
		case EVIL:
			return Color.BLACK;
			// end

		case GRASSY:
		default:
			return Statics.OFF_GREEN;
		}
	}

	protected Color getColorDirt() {

		switch (owner.getTexturePack()) {
		case DESERT:
			return Statics.OFF_TAN;
		case HAUNTED:
			return Statics.DRAB_BROWN;
		case ISLAND:
			return Statics.OFF_TAN;
		case LAB:
			return Statics.BROWN;
		case SNOWY:
			return Statics.BROWN;
		case VOLCANO:
			return Statics.BROWN;

		// Adding texture
		case EVIL:
			return Color.BLACK;
		// end

		case GRASSY:
		default:
			return Statics.BROWN;
		}
	}
}

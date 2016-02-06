package com.dig.www.blocks;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.PaintContext;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.ColorModel;

import com.dig.www.start.Board;
import com.dig.www.util.Sprite;
import com.dig.www.util.Statics;
import com.dig.www.MultiPlayer.State.BreakCrystal;
import com.dig.www.MultiPlayer.State.DigPit;
import com.dig.www.character.GameCharacter;

public class Block extends Sprite {

	public enum Blocks {
		GROUND, DIRT, WALL, PIT, ROCK, CARPET, CRYSTAL, LIQUID;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1866822784974593245L;

	protected static final Color[] list = { Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.MAGENTA, Color.CYAN, Color.ORANGE, Color.PINK, Color.WHITE };
	protected boolean canSee;
	protected Blocks type;
	protected Color corruptedColor = list[Statics.RAND.nextInt(list.length)];

	protected static Color[] darkColors;

	public Block(int x, int y, String loc, Board owner, Blocks block) {
		super(x, y, loc, owner);
		type = block;
	}

	@Override
	public void animate() {
		basicAnimate();
	}

	public Blocks getType() {
		return type;
	}
public void doType(Blocks type){
	this.type=type;
}
public void digDo(){
	if(type==Blocks.DIRT||type==Blocks.GROUND)
		type=Blocks.PIT;
	else if(type==Blocks.PIT)
		type=Blocks.DIRT;
}
	public void setType(Blocks type) {

		if (this.type != Blocks.PIT && this.type != Blocks.CRYSTAL && owner.getCharacter().getType() == GameCharacter.Types.SPADE)
			owner.getCharacter().getsActor();

		if (this.type != Blocks.CRYSTAL)
			if (this.type == type && owner.getCharacter().canAct())
				this.type = Blocks.DIRT;
			else
				this.type = type;
	}

	public void draw(Graphics2D g2d) {

		if (canSee) {

			if (!isStatic())
				switch (owner.getTexturePack()) {

				// TODO Desert Draw
				case DESERT:
					switch (type) {

					case GROUND:
						g2d.setColor(getColor());
						g2d.fill(getBounds());
						g2d.setColor(computeColor(Statics.LIGHT_OFF_TAN));
						g2d.fill(new Rectangle(x, y + 30, 60, 4));
						g2d.fill(new Rectangle(x + 60, y + 26, 40, 4));
						g2d.fill(new Rectangle(x, y + 80, 30, 4));
						g2d.fill(new Rectangle(x + 30, y + 76, 70, 4));
						g2d.draw(getBounds());
						break;
					case LIQUID:
						g2d.setColor(getColor());
						g2d.fill(getBounds());
						g2d.setColor(computeColor(Statics.LIGHT_BLUE));
						g2d.fill(new Rectangle(x + 60, y + 67, 40, 3));
						g2d.fill(new Rectangle(x, y + 70, 60, 3));
						break;
					case WALL:
						g2d.setColor(getColor());
						g2d.fill(getBounds());
						g2d.setColor(Color.BLACK);
						g2d.draw(getBounds());
						break;

					case CARPET:
						g2d.setColor(getColor());
						g2d.fill(getBounds());
						g2d.setColor(computeColor(Statics.LIGHT_OFF_TAN));
						g2d.drawLine(x, y, x + width, y + height);
						g2d.drawLine(x + width, y, x, y + height);
						g2d.drawLine(x, y + height / 2, x + width, y + height / 2);
						g2d.drawLine(x + width / 2, y, x + width / 2, y + height);
						break;

					default:
						g2d.setColor(getColor());
						g2d.fill(getBounds());
						break;
					}
					break;

				// TODO Snowy Draw
				case SNOWY:
					switch (type) {

					case GROUND:
						g2d.setColor(getColor());
						g2d.fill(getBounds());
						g2d.setColor(computeColor(Color.LIGHT_GRAY));
						g2d.fill(new Rectangle(x + 60, y + 40, 4, 4));
						g2d.fill(new Rectangle(x + 30, y + 80, 3, 3));
						g2d.draw(getBounds());
						break;
					case LIQUID:
						g2d.setColor(getColor());
						g2d.fill(getBounds());
						g2d.setColor(computeColor(Statics.LIGHT_BLUE));
						g2d.fill(new Rectangle(x + 70, y + 65, 10, 10));
						g2d.fill(new Rectangle(x + 30, y + 30, 15, 15));
						break;
					case WALL:
						g2d.setColor(getColor());
						g2d.fill(getBounds());
						g2d.setColor(computeColor(Statics.BROWN));
						g2d.fill(new Rectangle(x, y + 20, 100, 10));
						g2d.fill(new Rectangle(x, y + 70, 100, 10));
						g2d.setColor(Color.BLACK);
						g2d.draw(getBounds());
						break;

					case CARPET:
						g2d.setColor(getColor());
						g2d.fill(getBounds());
						g2d.setColor(computeColor(Statics.LIGHT_BROWN));
						g2d.fill(new Rectangle(x, y + 20, 100, 10));
						g2d.fill(new Rectangle(x, y + 70, 100, 10));
						break;

					case DIRT:
						g2d.setColor(getColor());
						g2d.fill(getBounds());
						g2d.setColor(computeColor(Color.WHITE));
						g2d.fill(new Rectangle(x + 70, y + 30, 5, 5));
						g2d.fill(new Rectangle(x + 40, y + 70, 5, 5));
						g2d.fill(new Rectangle(x + 10, y + 15, 3, 3));
						g2d.setColor(computeColor(Color.LIGHT_GRAY));
						g2d.draw(getBounds());
						break;
					default:
						g2d.setColor(getColor());
						g2d.fill(getBounds());
						break;
					}
					break;

				// TODO Island Draw
				case ISLAND:
					switch (type) {

					case GROUND:
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
					case LIQUID:
						g2d.setColor(getColor());
						g2d.fill(getBounds());
						g2d.setColor(computeColor(Statics.BLUE));
						g2d.fill(new Rectangle(x + 70, y + 65, 30, 5));
						g2d.fill(new Rectangle(x, y + 70, 70, 5));
						g2d.fill(new Rectangle(x + 40, y + 25, 60, 5));
						g2d.fill(new Rectangle(x, y + 30, 40, 5));
						break;
					case WALL:
						g2d.setColor(getColor());
						g2d.fill(getBounds());
						g2d.setColor(computeColor(Statics.BROWN));
						g2d.fill(new Rectangle(x, y + 20, 100, 10));
						g2d.fill(new Rectangle(x, y + 70, 100, 10));
						g2d.setColor(Color.BLACK);
						g2d.draw(getBounds());
						break;

					case CARPET:
						g2d.setColor(getColor());
						g2d.fill(getBounds());
						g2d.setColor(computeColor(Statics.LIGHT_BROWN));
						g2d.fill(new Rectangle(x, y + 20, 100, 10));
						g2d.fill(new Rectangle(x, y + 70, 100, 10));
						break;

					case DIRT:
						g2d.setColor(getColor());
						g2d.fill(getBounds());
						g2d.setColor(computeColor(Statics.LIGHT_OFF_TAN));
						g2d.fill(new Rectangle(x, y + 30, 60, 4));
						g2d.fill(new Rectangle(x + 60, y + 26, 40, 4));
						g2d.fill(new Rectangle(x, y + 80, 30, 4));
						g2d.fill(new Rectangle(x + 30, y + 76, 70, 4));
						g2d.draw(getBounds());
						break;
					default:
						g2d.setColor(getColor());
						g2d.fill(getBounds());
						break;
					}
					break;

				// TODO Volcano Draw
				case VOLCANO:
					switch (type) {

					case GROUND:
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
					case LIQUID:
						g2d.setColor(Statics.ORANGE);
						g2d.fill(getBounds());
						g2d.setColor(Color.ORANGE);
						g2d.fill(new Rectangle(x + 70, y + 65, 10, 10));
						g2d.fill(new Rectangle(x + 30, y + 30, 15, 15));
						break;
					case WALL:
						g2d.setColor(getColor());
						g2d.fill(getBounds());
						g2d.setColor(Color.BLACK);
						g2d.draw(getBounds());
						break;

					case CARPET:
						g2d.setColor(getColor());
						g2d.fill(getBounds());
						g2d.setColor(computeColor(Color.LIGHT_GRAY));
						g2d.drawLine(x, y + 15, x + width, y + 15);
						g2d.drawLine(x, y + height - 14, x + width, y + height - 14);
						g2d.drawLine(x, y + height / 2, x + width, y + height / 2);
						g2d.drawLine(x + width / 2, y, x + width / 2, y + height);
						break;

					case DIRT:
						g2d.setColor(getColor());
						g2d.fill(getBounds());
						g2d.setColor(computeColor(Color.GRAY));
						g2d.fill(new Rectangle(x, y + 30, 4, 4));
						g2d.fill(new Rectangle(x + 60, y + 26, 4, 4));
						g2d.fill(new Rectangle(x, y + 80, 4, 4));
						g2d.setColor(computeColor(Color.DARK_GRAY));
						g2d.draw(getBounds());
						break;
					case ROCK:
						g2d.setColor(getColor());
						g2d.fill(getBounds());
						g2d.setColor(computeColor(Statics.MED_GRAY));
						g2d.fill(new Rectangle(x, y + 30, 4, 4));
						g2d.fill(new Rectangle(x + 60, y + 26, 4, 4));
						g2d.fill(new Rectangle(x, y + 80, 4, 4));
						break;
					default:
						g2d.setColor(getColor());
						g2d.fill(getBounds());
						break;
					}
					break;

				// TODO Haunted Draw
				case HAUNTED:
					switch (type) {

					case GROUND:
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
					case LIQUID:
						g2d.setColor(getColor());
						g2d.fill(getBounds());
						g2d.setColor(computeColor(Statics.LIGHT_SAND_BLUE));
						g2d.fill(new Rectangle(x, y + 30, 40, 4));
						g2d.fill(new Rectangle(x + 80, y + 76, 20, 4));
						g2d.fill(new Rectangle(x, y + 80, 80, 4));
						g2d.fill(new Rectangle(x + 40, y + 26, 60, 4));
						break;
					case WALL:
						g2d.setColor(getColor());
						g2d.fill(getBounds());
						g2d.setColor(computeColor(Statics.LIGHT_SAND_BLUE));
						g2d.draw(getBounds());
						break;

					case CARPET:
						g2d.setColor(getColor());
						g2d.fill(getBounds());
						g2d.setColor(computeColor(Color.LIGHT_GRAY));
						g2d.drawLine(x, y, x + width, y + height);
						g2d.drawLine(x + width, y, x, y + height);
						g2d.drawLine(x, y + height / 2, x + width, y + height / 2);
						g2d.drawLine(x + width / 2, y, x + width / 2, y + height);
						break;

					case DIRT:
						g2d.setColor(getColor());
						g2d.fill(getBounds());
						g2d.setColor(computeColor(Statics.SAND_BLUE));
						g2d.fill(new Rectangle(x + 70, y + 65, 10, 10));
						g2d.fill(new Rectangle(x + 30, y + 30, 15, 15));
						g2d.fill(new Rectangle(x + 65, y + 20, 15, 15));
						g2d.fill(new Rectangle(x + 50, y + 70, 20, 20));
						g2d.draw(getBounds());
						break;

					case ROCK:
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

					default:
						g2d.setColor(getColor());
						g2d.fill(getBounds());
						break;
					}
					break;

				// TODO Lab Draw
				case LAB:
					switch (type) {

					case GROUND:
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

					case LIQUID:
						g2d.setColor(getColor());
						g2d.fill(getBounds());

						g2d.setColor(computeColor(Color.GREEN));
						g2d.fillOval(x + 70, y + 10, 5, 5);
						g2d.fillOval(x + 30, y + 50, 10, 10);
						g2d.fillOval(x + 80, y + 80, 10, 10);

						break;

					case WALL:
						g2d.setColor(getColor());
						g2d.fill(getBounds());
						g2d.setColor(Color.BLACK);
						g2d.draw(getBounds());
						break;

					case ROCK:
						g2d.setColor(getColor());
						g2d.fill(getBounds());
						g2d.setColor(Color.BLACK);

						int i;
						for (i = 0; i < 5; i++)
							g2d.drawLine(x, y + (20 * i), x + width - 1, y + (20 * i));

						for (i = 0; i < 5; i++)
							for (int c = 0; c < 5; c++)
								g2d.fillRect(x + (20 * c) + 8, y + (20 * i) + 2, 2, 2);

						break;

					case DIRT:
						g2d.setColor(getColor());
						g2d.fill(getBounds());
						g2d.setColor(computeColor(Statics.DRAB_BROWN));
						g2d.fillRect(x + 10, y + 70, 5, 5);
						g2d.fillRect(x + 50, y + 30, 10, 10);
						g2d.fillRect(x + 80, y + 80, 10, 10);
						break;

					case CARPET:
						g2d.setColor(getColor());
						g2d.fill(getBounds());
						g2d.setColor(computeColor(Color.GRAY));
						g2d.drawLine(x, y, x + width, y + height);
						g2d.drawLine(x + width, y, x, y + height);
						g2d.drawLine(x, y + height / 2, x + width, y + height / 2);
						g2d.drawLine(x + width / 2, y, x + width / 2, y + height);
						break;

					default:
						g2d.setColor(getColor());
						g2d.fill(getBounds());
						break;
					}
					break;// End Lab

				// TODO Grassy Draw
				case GRASSY:
				default:// Start grassy
					switch (type) {

					case GROUND:
						g2d.setColor(getColor());
						g2d.fill(getBounds());
						g2d.setColor(computeColor(Statics.LIGHT_OFF_GREEN));
						g2d.fill(new Rectangle(x + 30, y + 15, 4, 10));
						g2d.fill(new Rectangle(x + 80, y + 20, 4, 10));
						g2d.fill(new Rectangle(x + 20, y + 80, 4, 10));
						g2d.fill(new Rectangle(x + 60, y + 60, 4, 10));
						g2d.draw(getBounds());
						break;

					case LIQUID:
						g2d.setColor(getColor());
						g2d.fill(getBounds());
						g2d.setColor(computeColor(Statics.LIGHT_BLUE));
						g2d.fill(new Rectangle(x, y + 30, 40, 4));
						g2d.fill(new Rectangle(x + 80, y + 76, 20, 4));
						g2d.fill(new Rectangle(x, y + 80, 80, 4));
						g2d.fill(new Rectangle(x + 40, y + 26, 60, 4));
						break;

					case WALL:
						g2d.setColor(getColor());
						g2d.fill(getBounds());
						g2d.setColor(Color.BLACK);
						g2d.draw(getBounds());
						break;

					case ROCK:
						g2d.setColor(getColor());
						g2d.fill(getBounds());
						g2d.setColor(computeColor(Statics.MED_GRAY));
						g2d.fill(new Rectangle(x, y + 30, 4, 4));
						g2d.fill(new Rectangle(x + 60, y + 26, 4, 4));
						g2d.fill(new Rectangle(x, y + 80, 4, 4));
						// g2d.draw(getBounds());
						break;
					case DIRT:
						g2d.setColor(getColor());
						g2d.fill(getBounds());
						g2d.setColor(computeColor(Statics.DRAB_BROWN));
						// g2d.fillRect(x + 70, y + 10, 20, 20);
						g2d.fillRect(x + 10, y + 70, 5, 5);
						g2d.fillRect(x + 50, y + 30, 10, 10);
						g2d.fillRect(x + 80, y + 80, 10, 10);
						// g2d.draw(getBounds());
						break;

					case CARPET:
						g2d.setColor(getColor());
						g2d.fill(getBounds());
						g2d.setColor(computeColor(Color.RED));
						g2d.drawLine(x, y, x + width, y + height);
						g2d.drawLine(x + width, y, x, y + height);
						g2d.drawLine(x, y + height / 2, x + width, y + height / 2);
						g2d.drawLine(x + width / 2, y, x + width / 2, y + height);
						break;

					default:
						g2d.setColor(getColor());
						g2d.fill(getBounds());
						break;
					}
					break;
				}
			else {
				if (type != Blocks.PIT) {
					g2d.setColor(getColor());
					g2d.fill3DRect(x, y, width, height, true);
				} else {
					g2d.setColor(Color.BLACK);
					g2d.fill(getBounds());
				}
			}
			// End Switch of texturePacks
		} // end canSee
		else {
			g2d.setColor(Color.BLACK);
			g2d.fill(getBounds());
		}
	}

	public Color computeColor(Color c) {
		
//		if (traversable() && owner.isCorruptedWorld())
//			//return list[Statics.RAND.nextInt(list.length)];
//			return corruptedColor;

		if (!owner.thunderStrike() && !illuminated)
			if (owner.darkenWorld())
				if (owner.lighterDark())
					c = Statics.sunriseColor(c, Statics.HALF_DARK);
				else
					c = Statics.darkenColor(c);
			else if (owner.sunRise())
				c = Statics.sunriseColor(c, owner.getTime());
			else if (owner.sunSet())
				c = Statics.sunsetColor(c, owner.getTime());

		return c;
	}

	protected boolean isStatic() {
		return (type == Blocks.WALL && owner.getTexturePack() != TexturePack.ISLAND && owner.getTexturePack() != TexturePack.SNOWY)
				|| type == Blocks.CRYSTAL || type == Blocks.PIT;
	}

	public Color getColor() {
		
		if (traversable() && owner.isCorruptedWorld())
			//return list[Statics.RAND.nextInt(list.length)];
			return corruptedColor;

		Color c;
		switch (owner.getTexturePack()) {

		// TODO Desert Color
		case DESERT:
			switch (type) {
			case DIRT:
			case GROUND:
				c = Statics.OFF_TAN;
				break;
			case LIQUID:
				c = Statics.DESERT_BLUE;
				break;
			case WALL:
				c = Statics.SAND_STONE;
				break;
			case PIT:
				c = Color.BLACK;
				break;
			case ROCK:
				c = Statics.TAN;
				break;
			case CARPET:
				c = Statics.TAN;
				break;

			case CRYSTAL:
				c = Statics.LIGHT_BLUE;
				break;

			default:
				System.err.println("Type " + type + " does not have a color case");
				c = Color.RED;
				break;
			}
			break;

		// TODO Snowy Color
		case SNOWY:
			switch (type) {
			case DIRT:
				c = Statics.BROWN;
				break;
			case GROUND:
				c = Color.WHITE;
				break;
			case LIQUID:
				c = Statics.BLUE;
				break;
			case WALL:
				c = Statics.LIGHT_BROWN;
				break;
			case PIT:
				c = Color.BLACK;
				break;
			case ROCK:
				c = Color.GRAY;
				break;
			case CARPET:
				c = Statics.BROWN;
				break;
			case CRYSTAL:
				c = Statics.LIGHT_BLUE;
				break;

			default:
				System.err.println("Type " + type + " does not have a color case");
				c = Color.RED;
				break;
			}
			break;

		// TODO Island Color
		case ISLAND:
			switch (type) {
			case DIRT:
				c = Statics.OFF_TAN;
				break;
			case GROUND:
				c = Statics.OFF_GREEN;
				break;
			case LIQUID:
				c = Color.BLUE;
				break;
			case WALL:
				c = Statics.LIGHT_BROWN;
				break;
			case PIT:
				c = Color.BLACK;
				break;
			case ROCK:
				c = Color.GRAY;
				break;
			case CARPET:
				c = Statics.BROWN;
				break;
			case CRYSTAL:
				c = Statics.LIGHT_BLUE;
				break;

			default:
				System.err.println("Type " + type + " does not have a color case");
				c = Color.RED;
				break;
			}
			break;

		// TODO Volcano Color
		case VOLCANO:
			switch (type) {
			case DIRT:
			case GROUND:
				c = Statics.BROWN;
				break;
			case LIQUID:
				c = Statics.ORANGE;
				break;
			case CARPET:
			case WALL:
				c = Color.DARK_GRAY;
				break;
			case PIT:
				c = Color.BLACK;
				break;
			case ROCK:
				c = Color.GRAY;
				break;
			case CRYSTAL:
				c = Statics.LIGHT_BLUE;
				break;

			default:
				System.err.println("Type " + type + " does not have a color case");
				c = Color.RED;
				break;
			}
			break;

		// TODO Haunted Color
		case HAUNTED:
			switch (type) {
			case DIRT:
				c = Statics.DRAB_BROWN;
				break;
			case GROUND:
				c = Statics.SAND_BLUE;
				break;
			case LIQUID:
				c = Statics.BLUE;
				break;
			case WALL:
				c = Statics.DARK_SAND_BLUE;
				break;
			case PIT:
				c = Color.BLACK;
				break;
			case ROCK:
				c = Statics.SAND_RED;
				break;
			case CARPET:
				c = Statics.DARK_GREEN;
				break;

			case CRYSTAL:
				c = Statics.LIGHT_BLUE;
				break;

			default:
				System.err.println("Type " + type + " does not have a color case");
				c = Color.RED;
				break;
			}
			break;

		// TODO Lab Color
		case LAB:
			switch (type) {
			case GROUND:
			case DIRT:
				c = Statics.BROWN;
				break;
			case LIQUID:
				c = Statics.BLUE;
				break;
			case WALL:
				c = Color.DARK_GRAY;
				break;
			case PIT:
				c = Color.BLACK;
				break;
			case ROCK:
				c = Color.GRAY;
				break;
			case CARPET:
				c = Color.WHITE;
				break;
			case CRYSTAL:
				c = Statics.LIGHT_BLUE;
				break;
			default:
				System.err.println("Type " + type + " does not have a color case");
				c = Color.RED;
				break;
			}
			break;

		// TODO Grassy Color
		case GRASSY:
		default:
			switch (type) {
			case DIRT:
				c = Statics.BROWN;
				break;
			case GROUND:
				c = Statics.OFF_GREEN;
				break;
			case LIQUID:
				c = Statics.BLUE;
				break;
			case WALL:
				c = Color.DARK_GRAY;
				break;
			case PIT:
				c = Color.BLACK;
				break;
			case ROCK:
				c = Color.LIGHT_GRAY;
				break;
			case CARPET:
				c = Statics.TAN;
				break;

			case CRYSTAL:
				c = Statics.LIGHT_BLUE;
				break;

			default:
				System.err.println("Type " + type + " does not have a color case");
				c = Color.RED;
				break;
			}
			break;
		}

		return computeColor(c);
	}

	public void interact(int mInt) {

		switch (owner.getCharacter().getMove()) {
		case PIT:
			setType(Blocks.PIT);
			if(owner.getCurrentState()!=null)
				owner.getCurrentState().getActions().add(new DigPit(mInt));
			
			break;

		case CLUB:
			if (type == Blocks.CRYSTAL) {
				type = Blocks.ROCK;
				Statics.playSound(owner, "blocks/shatter.wav");
				if(owner.getCurrentState()!=null)
					owner.getCurrentState().getActions().add(new BreakCrystal(mInt));
				
				}
			break;

		default:
			break;
		}
	}

	public void setCanSee(boolean canSee) {
		this.canSee = canSee;
	}

	public boolean traversable() {
		return type == Blocks.GROUND || type == Blocks.CARPET || type == Blocks.DIRT || type == Blocks.ROCK;
	}

	public boolean canSee() {
		return canSee;
	}
}
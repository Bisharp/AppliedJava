package com.dig.www.blocks;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.dig.www.start.Board;
import com.dig.www.util.Sprite;
import com.dig.www.util.Statics;
import com.dig.www.character.GameCharacter;

public class Block extends Sprite {

	public enum Blocks {
		GROUND, DIRT, WALL, PIT, ROCK, CARPET, CRYSTAL, SWITCH, LIQUID;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1866822784974593245L;

	protected boolean canSee;
	protected Blocks type;

	public Block(int x, int y, String loc, Board owner, Blocks block) {
		super(x, y, loc, owner);
		type = block;
	}

	@Override
	public void animate() {
		x += owner.getScrollX();
		y += owner.getScrollY();
	}

	public Blocks getType() {
		return type;
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
			switch (owner.getTexturePack()) {

			// TODO Desert Draw
			case DESERT:
				switch (type) {

				case GROUND:
					g2d.setColor(getColor());
					g2d.fill(getBounds());
					g2d.setColor(Statics.LIGHT_OFF_TAN);
					g2d.fill(new Rectangle(x, y + 30, 60, 4));
					g2d.fill(new Rectangle(x + 60, y + 26, 40, 4));
					g2d.fill(new Rectangle(x, y + 80, 30, 4));
					g2d.fill(new Rectangle(x + 30, y + 76, 70, 4));
					g2d.draw(getBounds());
					break;
				case LIQUID:
					g2d.setColor(getColor());
					g2d.fill(getBounds());
					g2d.setColor(Statics.LIGHT_BLUE);
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
					g2d.setColor(Statics.LIGHT_OFF_TAN);
					g2d.drawLine(x, y, x + width, y + height);
					g2d.drawLine(x + width, y, x, y + height);
					g2d.drawLine(x, y + height / 2, x + width, y + height / 2);
					g2d.drawLine(x + width / 2, y, x + width / 2, y + height);
					break;

				case SWITCH:

					g2d.setFont(Statics.BLOCK);
					g2d.setColor(getColor());
					g2d.fill(getBounds());
					g2d.setColor(Color.BLUE);
					g2d.drawString("<->", x, y + 70);
					g2d.draw(getBounds());
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
					g2d.setColor(Color.LIGHT_GRAY);
					g2d.fill(new Rectangle(x + 60, y + 40, 4, 4));
					g2d.fill(new Rectangle(x + 30, y + 80, 3, 3));
					g2d.draw(getBounds());
					break;
				case LIQUID:
					g2d.setColor(getColor());
					g2d.fill(getBounds());
					g2d.setColor(Statics.LIGHT_BLUE);
					g2d.fill(new Rectangle(x + 70, y + 65, 10, 10));
					g2d.fill(new Rectangle(x + 30, y + 30, 15, 15));
					break;
				case WALL:
					g2d.setColor(getColor());
					g2d.fill(getBounds());
					g2d.setColor(Statics.BROWN);
					g2d.fill(new Rectangle(x, y + 20, 100, 10));
					g2d.fill(new Rectangle(x, y + 70, 100, 10));
					g2d.setColor(Color.BLACK);
					g2d.draw(getBounds());
					break;

				case CARPET:
					g2d.setColor(getColor());
					g2d.fill(getBounds());
					g2d.setColor(Statics.LIGHT_BROWN);
					g2d.fill(new Rectangle(x, y + 20, 100, 10));
					g2d.fill(new Rectangle(x, y + 70, 100, 10));
					break;

				case SWITCH:

					g2d.setFont(Statics.BLOCK);
					g2d.setColor(getColor());
					g2d.fill(getBounds());
					g2d.setColor(Color.BLUE);
					g2d.drawString("<->", x, y + 70);
					g2d.draw(getBounds());
					break;
				case DIRT:
					g2d.setColor(getColor());
					g2d.fill(getBounds());
					g2d.setColor(Color.WHITE);
					g2d.fill(new Rectangle(x + 70, y + 30, 5, 5));
					g2d.fill(new Rectangle(x + 40, y + 70, 5, 5));
					g2d.fill(new Rectangle(x + 10, y + 15, 3, 3));
					g2d.setColor(Color.LIGHT_GRAY);
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
					g2d.setColor(Statics.OFF_TAN);
					g2d.fill(new Rectangle(x + 70, y + 30, 5, 5));
					g2d.fill(new Rectangle(x + 40, y + 70, 5, 5));
					g2d.fill(new Rectangle(x + 10, y + 15, 3, 3));
					g2d.setColor(Statics.LIGHT_OFF_GREEN);
					g2d.fill(new Rectangle(x + 30, y + 15, 4, 10));
					g2d.fill(new Rectangle(x + 80, y + 20, 4, 10));
					g2d.fill(new Rectangle(x + 20, y + 80, 4, 10));
					g2d.draw(getBounds());
					break;
				case LIQUID:
					g2d.setColor(getColor());
					g2d.fill(getBounds());
					g2d.setColor(Statics.BLUE);
					g2d.fill(new Rectangle(x + 70, y + 65, 30, 5));
					g2d.fill(new Rectangle(x, y + 70, 70, 5));
					g2d.fill(new Rectangle(x + 40, y + 25, 60, 5));
					g2d.fill(new Rectangle(x, y + 30, 40, 5));
					break;
				case WALL:
					g2d.setColor(getColor());
					g2d.fill(getBounds());
					g2d.setColor(Statics.BROWN);
					g2d.fill(new Rectangle(x, y + 20, 100, 10));
					g2d.fill(new Rectangle(x, y + 70, 100, 10));
					g2d.setColor(Color.BLACK);
					g2d.draw(getBounds());
					break;

				case CARPET:
					g2d.setColor(getColor());
					g2d.fill(getBounds());
					g2d.setColor(Statics.LIGHT_BROWN);
					g2d.fill(new Rectangle(x, y + 20, 100, 10));
					g2d.fill(new Rectangle(x, y + 70, 100, 10));
					break;

				case SWITCH:

					g2d.setFont(Statics.BLOCK);
					g2d.setColor(getColor());
					g2d.fill(getBounds());
					g2d.setColor(Color.BLUE);
					g2d.drawString("<->", x, y + 70);
					g2d.draw(getBounds());
					break;
				case DIRT:
					g2d.setColor(getColor());
					g2d.fill(getBounds());
					g2d.setColor(Statics.LIGHT_OFF_TAN);
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
					g2d.setColor(Statics.OFF_GREEN);
					g2d.fill(new Rectangle(x + 30, y + 15, 4, 10));
					g2d.fill(new Rectangle(x + 80, y + 20, 4, 10));
					g2d.fill(new Rectangle(x + 20, y + 80, 4, 10));
					g2d.setColor(Color.GRAY);
					g2d.fill(new Rectangle(x + 70, y + 30, 5, 5));
					g2d.fill(new Rectangle(x + 40, y + 70, 5, 5));
					g2d.fill(new Rectangle(x + 10, y + 15, 3, 3));
					g2d.setColor(Color.DARK_GRAY);
					g2d.draw(getBounds());
					break;
				case LIQUID:
					g2d.setColor(getColor());
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
					g2d.setColor(Color.LIGHT_GRAY);
					g2d.drawLine(x, y, x + width, y + height);
					g2d.drawLine(x + width, y, x, y + height);
					g2d.drawLine(x, y + height / 2, x + width, y + height / 2);
					g2d.drawLine(x + width / 2, y, x + width / 2, y + height);
					break;

				case SWITCH:

					g2d.setFont(Statics.BLOCK);
					g2d.setColor(getColor());
					g2d.fill(getBounds());
					g2d.setColor(Color.BLUE);
					g2d.drawString("<->", x, y + 70);
					g2d.draw(getBounds());
					break;
				case DIRT:
					g2d.setColor(getColor());
					g2d.fill(getBounds());
					g2d.setColor(Color.GRAY);
					g2d.fill(new Rectangle(x, y + 30, 4, 4));
					g2d.fill(new Rectangle(x + 60, y + 26, 4, 4));
					g2d.fill(new Rectangle(x, y + 80, 4, 4));
					g2d.setColor(Color.DARK_GRAY);
					g2d.draw(getBounds());
					break;
				default:
					g2d.setColor(getColor());
					g2d.fill(getBounds());
					break;
				}
				break;

			// TODO Grassy Draw
			case GRASSY:
			default:// Start grassy
				switch (type) {

				case GROUND:
					g2d.setColor(getColor());
					g2d.fill(getBounds());
					g2d.setColor(Statics.LIGHT_OFF_GREEN);
					g2d.fill(new Rectangle(x + 30, y + 15, 4, 10));
					g2d.fill(new Rectangle(x + 80, y + 20, 4, 10));
					g2d.fill(new Rectangle(x + 20, y + 80, 4, 10));
					g2d.fill(new Rectangle(x + 60, y + 60, 4, 10));
					g2d.draw(getBounds());
					break;
				case LIQUID:
					g2d.setColor(getColor());
					g2d.fill(getBounds());
					g2d.setColor(Statics.LIGHT_BLUE);
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

				case CARPET:
					g2d.setColor(getColor());
					g2d.fill(getBounds());
					g2d.setColor(Color.RED);
					g2d.drawLine(x, y, x + width, y + height);
					g2d.drawLine(x + width, y, x, y + height);
					g2d.drawLine(x, y + height / 2, x + width, y + height / 2);
					g2d.drawLine(x + width / 2, y, x + width / 2, y + height);
					break;

				case SWITCH:

					g2d.setFont(Statics.BLOCK);
					g2d.setColor(getColor());
					g2d.fill(getBounds());
					g2d.setColor(Color.BLUE);
					g2d.drawString("<->", x, y + 70);
					g2d.draw(getBounds());
					break;

				default:
					g2d.setColor(getColor());
					g2d.fill(getBounds());
					break;
				}
				break;// End Grassy
			}// End Switch of texturePacks

		}// End canSee
		else {
			g2d.setColor(Color.black);
			g2d.fill(getBounds());
		}
	}

	public Color getColor() {
		switch (owner.getTexturePack()) {

		// TODO Desert Color
		case DESERT:
			switch (type) {
			case DIRT:

			case GROUND:
				return Statics.OFF_TAN;
			case LIQUID:
				return Statics.DESERT_BLUE;
			case WALL:
				return Statics.SAND_STONE;
			case PIT:
				return Color.BLACK;
			case ROCK:
				return Statics.TAN;
			case SWITCH:
				return Color.LIGHT_GRAY;
			case CARPET:
				return Statics.TAN;

			case CRYSTAL:
				return Statics.LIGHT_BLUE;

			default:
				System.err.println("Type " + type + " does not have a color case");
				return Color.RED;
			}

			// TODO Snowy Color
		case SNOWY:
			switch (type) {
			case DIRT:
				return Statics.BROWN;
			case GROUND:
				return Color.WHITE;
			case LIQUID:
				return Statics.BLUE;
			case WALL:
				return Statics.LIGHT_BROWN;
			case PIT:
				return Color.BLACK;
			case ROCK:
				return Color.GRAY;
			case SWITCH:
				return Color.LIGHT_GRAY;
			case CARPET:
				return Statics.BROWN;

			case CRYSTAL:
				return Statics.LIGHT_BLUE;

			default:
				System.err.println("Type " + type + " does not have a color case");
				return Color.RED;
			}

			// TODO Island Color
		case ISLAND:
			switch (type) {
			case DIRT:
				return Statics.OFF_TAN;
			case GROUND:
				return Statics.OFF_GREEN;
			case LIQUID:
				return Color.BLUE;
			case WALL:
				return Statics.LIGHT_BROWN;
			case PIT:
				return Color.BLACK;
			case ROCK:
				return Color.GRAY;
			case SWITCH:
				return Color.LIGHT_GRAY;
			case CARPET:
				return Statics.BROWN;

			case CRYSTAL:
				return Statics.LIGHT_BLUE;

			default:
				System.err.println("Type " + type + " does not have a color case");
				return Color.RED;
			}

			// TODO Volcano Color
		case VOLCANO:
			switch (type) {
			case DIRT:
			case GROUND:
				return Statics.BROWN;
			case LIQUID:
				return Statics.ORANGE;
			case WALL:
				return Color.DARK_GRAY;
			case PIT:
				return Color.BLACK;
			case ROCK:
				return Color.GRAY;
			case SWITCH:
			case CARPET:
				return Color.LIGHT_GRAY;

			case CRYSTAL:
				return Statics.LIGHT_BLUE;

			default:
				System.err.println("Type " + type + " does not have a color case");
				return Color.RED;
			}

			// TODO Grassy Color
		case GRASSY:
		default:
			switch (type) {
			case DIRT:
				return Statics.BROWN;
			case GROUND:
				return Statics.OFF_GREEN;
			case LIQUID:
				return Statics.BLUE;
			case WALL:
				return Color.DARK_GRAY;
			case PIT:
				return Color.BLACK;
			case ROCK:
			case SWITCH:
				return Color.LIGHT_GRAY;
			case CARPET:
				return Statics.TAN;

			case CRYSTAL:
				return Statics.LIGHT_BLUE;

			default:
				System.err.println("Type " + type + " does not have a color case");
				return Color.RED;
			}
		}
	}

	public void interact() {

		switch (owner.getCharacter().getMove()) {
		case PIT:
			setType(Blocks.PIT);
			break;

		case CLUB:
			if (type == Blocks.CRYSTAL) {
				type = Blocks.ROCK;
				Statics.playSound(owner, "blocks/shatter.wav");
			}
			break;

		default:
			break;
		}
	}

	public void setCanSee(boolean canSee) {
		this.canSee = canSee;
	}
}
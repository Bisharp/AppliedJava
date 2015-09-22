package com.dig.www.blocks;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.dig.www.start.Board;
import com.dig.www.start.TexturePack;
import com.dig.www.util.Sprite;
import com.dig.www.util.Statics;
import com.dig.www.character.GameCharacter;

public class Block extends Sprite {

	public enum Blocks {
		GROUND, DIRT, WALL, PIT, ROCK, CARPET, CRYSTAL, SWITCH;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1866822784974593245L;

	protected boolean canSee;
	protected boolean onScreen;
	protected Blocks type;

	public Block(int x, int y, String loc, Board owner, Blocks block) {
		super(x, y, loc, owner);

		type = block;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void animate() {
		// TODO Auto-generated method stub
		x += owner.getScrollX();
		y += owner.getScrollY();
	}

	public boolean isOnScreen() {
		return onScreen;
	}

	public void setOnScreen(boolean onScreen) {
		this.onScreen = onScreen;
	}

	public Blocks getType() {
		// TODO Auto-generated method stub
		return type;
	}

	public void setType(Blocks type) {
		// TODO Auto-generated method stub

		if (this.type != Blocks.PIT && this.type != Blocks.CRYSTAL && owner.getCharacter().getType() == GameCharacter.Types.SPADE)
			owner.getCharacter().getsActor();
		
		if (this.type != Blocks.CRYSTAL)
			if (this.type == type && owner.getCharacter().canAct())
				this.type = Blocks.DIRT;
			else
				this.type = type;
	}

	public void draw(Graphics2D g2d) {

		if (canSee){
			switch(owner.getTexturePack()){
			case DESERT://Start desert
			switch (type) {
			
			case GROUND:
				g2d.setColor(Statics.OFF_TAN);
				g2d.fill(getBounds());
				g2d.setColor(Statics.LIGHT_OFF_TAN);
				g2d.fill(new Rectangle(x, y + 30, 60, 4));
				g2d.fill(new Rectangle(x + 60, y + 26, 40, 4));
				g2d.fill(new Rectangle(x, y + 80, 30, 4));
				g2d.fill(new Rectangle(x + 30, y + 76, 70,4));
				g2d.draw(getBounds());
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
			break;
			
			case GRASSY:
			default://Start grassy
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
			
			
			}//End Switch of texturePacks
			
		}//End canSee
		else {
			g2d.setColor(Color.black);
			g2d.fill(getBounds());
		}
	}

	public Color getColor() {
		// TODO Auto-generated method stub
		switch (owner.getTexturePack()){
		case DESERT:
	switch (type) {
	case DIRT:
	case GROUND:
		return Statics.OFF_TAN;
	case WALL:
		return Color.DARK_GRAY;
	case PIT:
		return Statics.LIGHT_OFF_TAN;
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
	
		
		case GRASSY:
			default:
		switch (type) {
		case DIRT:
			return Statics.BROWN;
		case GROUND:
			return Statics.OFF_GREEN;
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
		// TODO Auto-generated method stub

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
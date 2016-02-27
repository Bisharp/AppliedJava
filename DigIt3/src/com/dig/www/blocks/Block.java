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

public abstract class Block extends Sprite {

	public enum Blocks {
		GROUND, DIRT, WALL, PIT, ROCK, CARPET, CRYSTAL, LIQUID;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1866822784974593245L;

	public static final Color[] list = { Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.MAGENTA, Color.CYAN, Color.ORANGE, Color.PINK, Color.WHITE };
	protected boolean canSee;
	protected Blocks type;
	protected Color corruptedColor1 = list[Statics.RAND.nextInt(list.length)];
//protected Color corruptedColor2=list[Statics.RAND.nextInt(list.length)];
protected Color current=new Color(0,0,0);
//private int colorTimer=0;
//private int maxTimer=Statics.RAND.nextInt(50)+50;
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

	public abstract void draw(Graphics2D g2d) ;
	
	public boolean canSee(Graphics2D g2d) {
		if (canSee)
			return true;
		
		g2d.setColor(Color.BLACK);
		g2d.fillRect(x, y, width, height);
		return false;
	}

	public Color computeColor(Color c) {
		
		if (traversable() && owner.isCorruptedWorld())
			//return list[Statics.RAND.nextInt(list.length)];
			return corruptedColor1;

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

	public abstract Color getColor() ;

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
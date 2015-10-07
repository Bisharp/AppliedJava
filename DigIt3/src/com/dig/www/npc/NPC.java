package com.dig.www.npc;

import java.awt.Graphics2D;
import java.awt.Image;

import com.dig.www.start.Board;
import com.dig.www.util.Sprite;
import com.dig.www.util.Statics;

public class NPC extends Sprite{
	
	public static final String WIZARD = "wizard";
	public static final String SIR_COBALT = "sirCobalt";
	public static final String KEPLER = "kepler";
	public static final String SHOPKEEP = "shopkeep";
	public static final String GATEKEEPER = "gateKeeper";
	
	protected String[]dialogs;
	protected Image gif;
	protected int line;
	
	public NPC(int x, int y, String loc, Board owner, String[]dialogs, String s) {
		super(x, y, loc);
		// TODO Auto-generated constructor stub
		image = newImage(loc);
		width = image.getWidth(null);
		height = image.getHeight(null);
		
		this.dialogs = dialogs;
		this.owner = owner;
		this.loc = loc;
		this.x = x;
		this.y = y;
		gif = newImage("images/npcs/talking/" + s + ".gif");
	}
	
	public void animate(){
		basicAnimate();
	}
	@Override
	public void draw(Graphics2D g2d) {
		// TODO Auto-generated method stub
		g2d.drawImage(image, x, y, owner);
	}
	
	public String[] getDialog(){
		return dialogs;
	}
	
	public Image getGif() {
		return gif;
	}

	public String getLine() {
		return dialogs[line];
	}
	
	public void setLine() {
		line = Statics.RAND.nextInt(dialogs.length);
	}
}

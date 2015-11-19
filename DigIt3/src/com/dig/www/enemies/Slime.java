package com.dig.www.enemies;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import com.dig.www.start.Board;
import com.dig.www.util.Statics;

public class Slime extends WalkingEnemy {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected static final int RISE_POINT = 30;
	protected static final int RAND = 100;
	protected static final int MIN = 50;
	protected static final int MAX_REPS = 10;

	protected int timer;
	protected int repeats;
	protected double dir;
	protected Image risenImage;
	protected Image projectileImage;
	
	protected final Color tint;
	protected static final int SPEED = 10;
	
	public Slime(int x, int y, String loc, Board owner, boolean flying, int health) {
		super(x, y, loc, owner, flying, health);

		tint = getColor();
		initializeImages();
	}

	public Slime(int x, int y, String loc, Board owner, boolean flying, int health, Color c) {
		super(x, y, loc, owner, flying, health);

		tint = c;
		initializeImages();
	}
	
	protected void initializeImages() {
		image = tintImage(newImage(loc, 0), tint);
		risenImage = tintImage(newImage(loc.replace(".png", "R.png"), 0), tint);
		projectileImage = tintImage(newImage("images/enemies/blasts/slime.png", 0), tint);
		repeats = Statics.RAND.nextInt(MAX_REPS) + 1;
	}

	@Override
	public void animate() {
		if (timer > RISE_POINT)
			super.animate();
		else {
			basicAnimate();
			if (repeats <= 0) {
				Projectile proj = new Projectile(dir, x, y, SPEED, this, Statics.DUMMY, owner, flying,damage);
				proj.setImage(projectileImage);
				owner.getEnemies().add(proj);
				repeats = Statics.RAND.nextInt(MAX_REPS) + 1;
			}
		}
		
		if (timer > 0)
			timer--;
		else {
			timer = Statics.RAND.nextInt(RAND) + MIN;
			repeats--;
		}
		
		dir = Statics.pointTowards(new Point((int) x, (int) y), owner.getCharPoint());
	}

	@Override
	public Image getImage() {
		if (timer > RISE_POINT)
			return image;
		else
			return risenImage;
	}

	protected static Image tintImage(Image icon, Color newColor) {

		final int AMOUNT = lightColors.contains(newColor) ? 75 : 50;
		BufferedImage input = new BufferedImage(icon.getWidth(null), icon.getHeight(null), BufferedImage.TYPE_4BYTE_ABGR_PRE);
		input.getGraphics().drawImage(icon, 0, 0, null);
		WritableRaster rr = input.getRaster();

		int[] currentPixelVals;
		int[] vals = new int[] { newColor.getRed(), newColor.getGreen(), newColor.getBlue(), newColor.getAlpha() };
		int[] darkVals = new int[] { newColor.getRed() - AMOUNT, newColor.getGreen() - AMOUNT, newColor.getBlue() - AMOUNT, newColor.getAlpha() };
		for (int i = 0; i < darkVals.length - 1; i++)
			if (darkVals[i] < 0)
				darkVals[i] = 0;

		int[] recolor = new int[] { 255, 255, 255, 255 };
		int[] darkRecolor = new int[] { 205, 205, 205, 255 };
		for (int i = 0; i < darkRecolor.length - 1; i++)
			if (darkRecolor[i] < 0)
				darkRecolor[i] = 0;

		for (int x = 0; x < input.getWidth(); x++) {
			for (int y = 0; y < input.getHeight(); y++) {
				currentPixelVals = rr.getPixel(x, y, (int[]) null);

				if (currentPixelVals[0] == recolor[0] && currentPixelVals[1] == recolor[1] && currentPixelVals[2] == recolor[2]) {
					rr.setPixel(x, y, vals);
				} else if (currentPixelVals[0] == darkRecolor[0] && currentPixelVals[1] == darkRecolor[1] && currentPixelVals[2] == darkRecolor[2]) {
					rr.setPixel(x, y, darkVals);
				}
			}
		}

		return input;
	}

	protected static final Color[] colors = new Color[] { Color.red, Color.green, Color.yellow, Statics.ORANGE, Color.blue, Color.darkGray, Color.white,
			Statics.PURPLE, Statics.BROWN };
	protected static final ArrayList<Color> lightColors;
	static {
		lightColors = new ArrayList<Color>();
		lightColors.add(Color.red);
		lightColors.add(Color.green);
		lightColors.add(Color.yellow);
		lightColors.add(Color.blue);
		lightColors.add(Color.white);
	}

	protected Color getColor() {
		return colors[Statics.RAND.nextInt(colors.length)];
	}

	protected Color randColor() {
		return new Color(Statics.RAND.nextInt(256), Statics.RAND.nextInt(256), Statics.RAND.nextInt(256), 255);
	}

	protected Image newImage(String loc, int nul) {
		URL url = getClass().getResource("/" + loc);

		try {
			return new ImageIcon(url).getImage();
		} catch (NullPointerException ex) {
			System.err.println("ERROR: " + loc);
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public void resetImage(Board b) {
		health = maxHealth;
		owner = b;
	}

	@Override
	public int getSpeed() {
		return slowTimer <= 0 ? 3 : 1;
	}

	@Override
	public void draw(Graphics2D g2d) {

		if (stunTimer > 0) {
			int x = this.x + (Statics.RAND.nextInt(5) * (Statics.RAND.nextBoolean() ? 1 : -1));
			int y = this.y + (Statics.RAND.nextInt(5) * (Statics.RAND.nextBoolean() ? 1 : -1));
			g2d.drawImage(getImage(), x + (scrollX < 0 ? width : 0), y, width * (scrollX < 0 ? -1 : 1), height, owner);
		} else
			g2d.drawImage(getImage(), x + (scrollX < 0 ? width : 0), y, width * (scrollX < 0 ? -1 : 1), height, owner);

		if (harmTimer > 0)
			g2d.drawImage(newImage("images/effects/heart.png"), x, y, owner);
		else if (slowTimer > 0)
			g2d.drawImage(newImage("images/effects/ice.png"), x, y, owner);

		drawBar((double) health / (double) maxHealth, g2d);
	}
}
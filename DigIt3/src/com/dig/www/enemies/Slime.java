package com.dig.www.enemies;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.net.URL;

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

	protected int timer;
	protected Image risenImage;
	protected Color tint// = getColor();
	;

	public Slime(int x, int y, String loc, Board owner, boolean flying, int health) {
		super(x, y, loc, owner, flying, health);

		tint = getColor();
		image = tintImage(newImage(loc, 0), tint);
		risenImage = tintImage(newImage(loc.replace(".png", "R.png"), 0), tint);
	}

	@Override
	public void animate() {
		if (timer > RISE_POINT)
			super.animate();
		else
			basicAnimate();

		if (timer > 0)
			timer--;
		else
			timer = Statics.RAND.nextInt(RAND) + MIN;
	}

	@Override
	public Image getImage() {
		if (timer > RISE_POINT)
			return image;
		else
			return risenImage;
	}

	protected static Image tintImage(Image icon, Color newColor) {

		final int AMOUNT = 50;
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
		int[] darkRecolor = new int[] { 255 - AMOUNT, 255 - AMOUNT, 255 - AMOUNT, 255 };
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

	protected Color[] colors = new Color[] { Color.red, Color.green, Color.yellow, Statics.ORANGE, Color.blue, Color.magenta, Color.cyan,
			Color.darkGray, Color.pink, Color.white, Statics.PURPLE, Statics.BROWN };

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
}
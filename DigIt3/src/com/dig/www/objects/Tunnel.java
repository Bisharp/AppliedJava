package com.dig.www.objects;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.dig.www.start.Board;
import com.dig.www.util.Statics;

public class Tunnel extends Objects {

	public Tunnel(int x, int y, String loc, boolean wall, Board owner, String identifier) {
		super(x, y, loc, wall, owner, identifier);
	}

	protected static String BRIDGE = "bridge";
	protected static String STAIR = "stair";
	protected static final int HORIZ = 270;

	public static void main(String[] args) {
		createImage(BRIDGE, 10, 5, false);
	}

	protected static Image createImage(String skin, int width, int height, boolean vertical) {

		if (vertical) {
			int temp = width;
			width = height;
			height = temp;
		}

		BufferedImage toReturn = newBImage(width, height);
		Graphics2D g2d = (Graphics2D) toReturn.getGraphics();

		final String loc = "images/objects/tunnel/" + skin + "/";

		for (int x = 0; x < width; x++)
			for (int y = 0; y < height; y++)
				// if (y == 0 || y == height - 1)
				// drawRailing()
				// else
				g2d.drawImage(Statics.newImage(loc + (vertical ? "V" : "H") + ".png"), x * Statics.BLOCK_HEIGHT, y * Statics.BLOCK_HEIGHT, null);

		g2d.setStroke(new BasicStroke(5));
		g2d.setColor(Color.BLACK);

		if (vertical) {
			g2d.drawLine(5, 5, 5, height * Statics.BLOCK_HEIGHT - 8);
			g2d.drawLine(width * Statics.BLOCK_HEIGHT - 5, 5, width * Statics.BLOCK_HEIGHT - 5, height * Statics.BLOCK_HEIGHT - 8);
		} else {
			g2d.drawLine(5, 5, width * Statics.BLOCK_HEIGHT - 8, 5);
			g2d.drawLine(5, height * Statics.BLOCK_HEIGHT - 5, width * Statics.BLOCK_HEIGHT - 8, height * Statics.BLOCK_HEIGHT - 5);
		}

		File outputfile = new File(Statics.getBasedir() + loc + "2.png");
		outputfile.mkdirs();
		try {
			ImageIO.write(toReturn, "png", outputfile);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return toReturn;
	}

	protected static BufferedImage newBImage(int width, int height) {
		return new BufferedImage(width * Statics.BLOCK_HEIGHT, height * Statics.BLOCK_HEIGHT, BufferedImage.TYPE_4BYTE_ABGR_PRE);
	}
}

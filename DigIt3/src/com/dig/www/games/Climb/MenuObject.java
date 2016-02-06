package com.dig.www.games.Climb;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class MenuObject extends Object {

	protected static final int[] light = { 85, 255, 85, 255 };
	
	protected boolean lit = false;
	protected BufferedImage litImage;

	public MenuObject(int x, int y, String loc, Climb owner) {
		super(x, y, loc, owner);

		litImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_4BYTE_ABGR_PRE);
		((Graphics2D) litImage.getGraphics()).drawImage(image, 0, 0, null);
		WritableRaster rr = litImage.getRaster();

		
		int[] pixel;

		for (int x2 = 0; x2 < litImage.getWidth(); x2++)
			for (int y2 = 0; y2 < litImage.getHeight(); y2++) {
				pixel = rr.getPixel(x2, y2, (int[]) null);
				if (pixel[pixel.length - 1] > 0)
					rr.setPixel(x2, y2, light);
			}
	}

	protected void setLight(boolean light) {
		lit = light;
	}

	public Image getImage() {
		if (lit)
			return litImage;
		else
			return image;
	}
}

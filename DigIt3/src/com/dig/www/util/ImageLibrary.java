package com.dig.www.util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.net.URL;
import java.util.HashMap;

import javax.swing.ImageIcon;

public class ImageLibrary {

	private HashMap<String, Image> images;
	private HashMap<String, Image> shadows;	
	
	private static ImageLibrary me;
	private Image image;
	private static final String ADDRESS = ImageLibrary.class
			.getProtectionDomain().getCodeSource().getLocation().getFile();

	public static ImageLibrary getInstance() {

		if (me == null)
			me = new ImageLibrary();

		return me;
	}

	private ImageLibrary() {
		images = new HashMap<String, Image>();
		shadows = new HashMap<String, Image>();
	}

	public Image checkLibrary(String loc) {
		image = images.get(loc);

		if (image == null)
			registerImage(loc);

		return image;
	}

	public void registerImage(String loc) {
		URL url = getClass().getResource(loc);

		try {
			image = new ImageIcon(url).getImage();
			images.put(loc, image);
		} catch (NullPointerException ex) {
			System.err.println("ERROR: " + loc);
			ex.printStackTrace();
		}
	}
	
	// Shadow registry; left in just in case we need to create shadows of characters.

	public Image checkShadowLibrary(String loc) {
		image = shadows.get(loc);

		if (image == null)
			registerShadow(loc);

		return image;
	}
	
	public void registerShadow(String loc) {

		try {
			image = createShadow(images.get(loc));
			shadows.put(loc, image);
		} catch (NullPointerException ex) {
			System.err.println("ERROR: " + loc);
			ex.printStackTrace();
		}
	}

	private static Image createShadow(Image i) {
		BufferedImage toReturn = new BufferedImage(i.getWidth(null), i.getHeight(null), BufferedImage.TYPE_4BYTE_ABGR_PRE);
		((Graphics2D) toReturn.getGraphics()).drawImage(i, 0, 0, null);
		WritableRaster rr = toReturn.getRaster();
		
		final int[] shadow = { 0, 0, 0, 200 };
		int[] pixel;

		for (int x = 0; x < toReturn.getWidth(); x++)
			for (int y = 0; y < toReturn.getHeight(); y++) {
				pixel = rr.getPixel(x, y, (int[]) null);
				
				if (pixel[pixel.length - 1] > 0)
					rr.setPixel(x, y, shadow);
			}
		
		return toReturn;
	}

	// public void deleteLibrary() {
	// images = null;
	// }
	//
	// public void resetLibrary() {
	// images = new HashMap<String, Image>();
	// }
}

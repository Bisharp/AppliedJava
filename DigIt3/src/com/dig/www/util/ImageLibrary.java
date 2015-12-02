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
	private static final String ADDRESS = ImageLibrary.class.getProtectionDomain().getCodeSource().getLocation().getFile();

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

	// Shadow registry

	public Image checkShadowLibrary(String loc) {
		image = shadows.get(loc);

		if (image == null)
			registerShadow(loc);

		return image;
	}

	public void registerShadow(String loc) {

		try {
			image = createShadow(checkLibrary(loc), loc.startsWith("/images/enemies/"));
			shadows.put(loc, image);
		} catch (NullPointerException ex) {
			System.err.println("ERROR: " + loc);
			ex.printStackTrace();
		}
	}

	private static final int[] RED = new int[] { 255, 0, 0, 255 };
	private static final int[] YELLOW = new int[] { 255, 242, 0, 255 };

	private static Image createShadow(Image i, boolean isEnemy) {
		BufferedImage toReturn = new BufferedImage(i.getWidth(null), i.getHeight(null), BufferedImage.TYPE_4BYTE_ABGR_PRE);
		((Graphics2D) toReturn.getGraphics()).drawImage(i, 0, 0, null);
		WritableRaster rr = toReturn.getRaster();

		final int[] shadow = { 0, 0, 0, 200 };
		int[] pixel;

		for (int x = 0; x < toReturn.getWidth(); x++)
			for (int y = 0; y < toReturn.getHeight(); y++) {
				pixel = rr.getPixel(x, y, (int[]) null);

				if (pixel[pixel.length - 1] > 50 && (isEnemy? !equals(pixel, RED) && !equals(pixel, YELLOW) : true))
					rr.setPixel(x, y, shadow);
			}

		return toReturn;
	}
	
	public static boolean equals(int[] array0, int[] array1) {
		for (int i = 0; i < array0.length; i++)
			if (array0[i] != array1[i])
				return false;
		return true;
	}

	// public void deleteLibrary() {
	// images = null;
	// }
	//
	// public void resetLibrary() {
	// images = new HashMap<String, Image>();
	// }
}

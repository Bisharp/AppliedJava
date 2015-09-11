package com.dig.www.util;

import java.awt.Image;
import java.net.URL;
import java.util.HashMap;

import javax.swing.ImageIcon;

public class ImageLibrary {

	private HashMap<String, Image> images;
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

//	public void deleteLibrary() {
//		images = null;
//	}
//
//	public void resetLibrary() {
//		images = new HashMap<String, Image>();
//	}
}

package com.dig.www.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

import javax.swing.JOptionPane;

import com.dig.www.npc.NPC;
//import com.manor.www.start.Board;
import com.dig.www.start.DigIt;

public final class Statics {
	
	public static final String MAIN="Story Mode";
	public static final int BLOCK_HEIGHT = 100;
	public static final String FONT = "Trebuchet MS";

	// static {
	// if (System.getProperty("os.name").startsWith("Windows"))
	// FONT = "Trebuchet MS";
	// else if (System.getProperty("os.name").startsWith("Mac"))
	// FONT =
	// }

	public static final int BOARD_WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	public static final int BOARD_HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();;
	public static final ImageIcon ICON = createImageIcon("/images/icon.png");

	public static final Random RAND = new Random();

	public static final Color NIGHT_SKY = new Color(0, 0, 0, 100);

	public static final Color PURPLE = new Color(128, 0, 128);
	public static final Color ORANGE = new Color(254, 83, 1);
	public static final Color BROWN = new Color(128, 41, 0);
	public static final Color TAN = new Color(221, 182, 108);
	public static final Color LIGHT_GREEN = new Color(120, 255, 0);
	public static final Color OFF_GREEN = new Color(148, 190, 50);
	public static final Color LIGHT_OFF_GREEN = new Color(167, 207, 73);
	public static final Color LIGHT_BLUE = new Color(132, 255, 255);
	public static final Color DESERT_BLUE = new Color(100, 200, 255);
	public static final Color BLUE = new Color(50, 100, 255);
	public static final Color LIGHT_OFF_TAN = new Color(225, 180, 0);
	public static final Color OFF_TAN = new Color(220, 185, 60);
	public static final Color SAND_STONE = new Color(110, 90, 30);
	public static final Color LIGHT_BROWN = new Color(175, 75, 0);
	public static final Color MED_GRAY = new Color(150, 150, 160);

	// Colors likely used exclusively in the haunted skin.
	public static final Color DARK_GREEN = new Color(30, 72, 30);
	public static final Color DRAB_BROWN = new Color(68, 21, 15);
	public static final Color LIGHT_SAND_BLUE = new Color(63, 85, 116);
	public static final Color SAND_BLUE = new Color(48, 65, 88);
	public static final Color DARK_SAND_BLUE = new Color(32, 44, 60);
	public static final Color SAND_RED = new Color(155, 36, 36);
	
	public static final Color LIGHT = new Color(255, 255, 255, 20);

	private static HashMap<Color, Color> darkColors = new HashMap<Color, Color>();
	private static HashMap<Float, HashMap<Color, Color>> sunriseColors = new HashMap<Float, HashMap<Color, Color>>();
	private static HashMap<Float, HashMap<Color, Color>> sunsetColors = new HashMap<Float, HashMap<Color, Color>>();

	public static Color darkenColor(Color c) {
		if (darkColors.containsKey(c))
			return darkColors.get(c);

		int[] rgb = new int[] { c.getRed(), c.getGreen(), c.getBlue() };

		for (int i = 0; i < rgb.length; i++)
			rgb[i] = rgb[i] / 5;

		Color c2 = new Color(rgb[0], rgb[1], rgb[2], c.getAlpha());
		darkColors.put(c, c2);
		return c2;
	}

	public static Color sunriseColor(Color c, float time) {

		if (sunriseColors.containsKey(time) && sunriseColors.get(time).containsKey(c))
			return sunriseColors.get(time).get(c);

		int[] rgb = new int[] { darkenColor(c).getRed(), darkColors.get(c).getGreen(), darkColors.get(c).getBlue() };
		int[] rgb2 = rgb.clone();
		rgb2[0] *= 7 * decimalPart(time);
		rgb2[1] *= 7 * decimalPart(time);
		rgb2[2] *= 7 * decimalPart(time);

		for (int i = 0; i < rgb2.length; i++)
			rgb2[i] += rgb[i];

		Color c2 = new MColor(rgb2[0], rgb2[1], rgb2[2], c.getAlpha());

		if (sunriseColors.containsKey(time))
			sunriseColors.get(time).put(c, c2);
		else {
			sunriseColors.put(time, new HashMap<Color, Color>());
			sunriseColors.get(time).put(c, c2);
		}

		return c2;
	}

	public static Color sunsetColor(Color c, float time) {

		if (sunsetColors.containsKey(time) && sunsetColors.get(time).containsKey(c))
			return sunsetColors.get(time).get(c);

		int[] rgb = new int[] { c.getRed(), c.getGreen(), c.getBlue() };
		int[] rgb2 = rgb.clone();

		rgb2[0] /= 8 * decimalPart(time);
		rgb2[1] /= 8 * decimalPart(time);
		rgb2[2] /= 8 * decimalPart(time);

		for (int i = 0; i < rgb2.length; i++)
			if (rgb2[i] > rgb[i])
				rgb2[i] = rgb[i];

		if (decimalPart(time) <= MID_SUNSET * 2) {
			rgb2[0] += getOrangeOffset(decimalPart(time), 500);
			rgb2[1] += getOrangeOffset(decimalPart(time), 250);
		}

		Color c2 = new MColor(rgb2[0], rgb2[1], rgb2[2], c.getAlpha());

		if (sunsetColors.containsKey(time))
			sunsetColors.get(time).put(c, c2);
		else {
			sunsetColors.put(time, new HashMap<Color, Color>());
			sunsetColors.get(time).put(c, c2);
		}

		return c2;
	}

	private static final float MID_SUNSET = 0.3f;
	protected static int getOrangeOffset(float time, int mult) {
		return (int) ((time <= MID_SUNSET? time : MID_SUNSET - (time - MID_SUNSET)) * mult);
	}

	public static void wipeColors() {
		darkColors = new HashMap<Color, Color>();
		sunriseColors = new HashMap<Float, HashMap<Color, Color>>();
		sunsetColors = new HashMap<Float, HashMap<Color, Color>>();
	}

	public static float decimalPart(float f) {
		while (f >= 1)
			f--;
		return f;
	}

	public static final Font PAUSE = NPC.NPC_NORMAL;
	public static final Font BOSS = new Font("Impact", Font.BOLD, 40);
	public static final Font MENU = new Font("Impact", Font.BOLD, 80);
	public static final String DUMMY = "images/dummy.png";

	public static final int LINE = 300;
	public static final int STRENGTH = 20;
	public static final float HALF_DARK = 7.25f;

	public static double pointTowards(Point b, Point a) {
		// Point at something, This will be useful for enemies, also in
		// ImportantLook class
		return (double) (Math.toDegrees(Math.atan2(b.getY() + -a.getY(), b.getX() + -a.getX())) + 180);
	}

	public static void showError(String err, JComponent owner) {
		JOptionPane.showMessageDialog(owner, err, "Error", JOptionPane.ERROR_MESSAGE);
	}

	/** Returns an ImageIcon, or null if the path was invalid. */
	// This method/concept courtesy of SwingExamples.components.DialogDemo
	private static ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = DigIt.class.getResource(path);
		return new ImageIcon(imgURL);
	}

	public static Image newImage(String loc) {
		return DigIt.lib.checkLibrary("/" + loc);
	}
public static String getRandomItem(String folderLoc){
	FilenameFilter dirFilter = new FilenameFilter() {
		@Override
		public boolean accept(File dir, String name) {
			if (name.endsWith(".png") || name.endsWith(".gif")|| name.endsWith(".jpg"))
				return true;
			return false;
		}
	};
	String[]items=new File(folderLoc).list(dirFilter);
	
	return items[RAND.nextInt(items.length)];
}
	public static int getFolderCont(String defaultDir) {//Outdated and will be removed. new ^
		// TODO Auto-generated method stub
		try {

			File projectDir = new File(defaultDir);
			FilenameFilter dirFilter = new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					if (name.startsWith(".") || name.endsWith(".log"))
						return false;
					if (dir.isDirectory())
						return true;
					return false;
				}
			};

			String[] results = projectDir.list(dirFilter);
			return results.length;

		} catch (Exception ex) {
			return 0;
		}
	}

	public static void playSound(JComponent jC, String url) {
		DigIt.soundPlayer.playSound(jC, url);
	}

	public static void exit(JComponent owner) {
		// TODO Auto-generated method stub

		if (JOptionPane.showConfirmDialog(owner, "Are you sure you want to quit?\n(Unsaved data will be lost)", DigIt.NAME,
				JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION)
			System.exit(0);
	}

	public static double dist(int x1, int y1, int x2, int y2) {
		return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
	}
}
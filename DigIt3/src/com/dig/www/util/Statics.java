package com.dig.www.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JComponent;


//import com.manor.www.start.Board;
import com.dig.www.start.DigIt;

public final class Statics {

	public static final int BLOCK_HEIGHT = 100;

	public static final int BOARD_WIDTH = 1000;
	public static final int BOARD_HEIGHT = 700;

	public static final String INF = "INF.";
	public static final String LAMBDA = "/\\";

	public static final Font SMALL = new Font("Chiller", Font.BOLD, 20);
	public static final ImageIcon ICON = createImageIcon("/images/icon.png");

	public static final Random RAND = new Random();

	public static final Color PURPLE = new Color(128, 0, 128);
	public static final Color ORANGE = new Color(254, 83, 1);
	public static final Color BROWN = new Color(128, 41, 0);
	public static final Color TAN = new Color(221, 182, 108);
	public static final Color LIGHT_GREEN = new Color(120, 255, 0);
	public static final Color OFF_GREEN = new Color(148, 190, 50);
	public static final Color LIGHT_OFF_GREEN = new Color(167, 207, 73);
	public static final Color LIGHT_BLUE = new Color(132, 255, 255);

	public static final Font BLOCK = new Font("Calibri", Font.PLAIN, 80);
	public static final Font MENU = new Font("Impact", Font.BOLD, 80);
	public static final String DUMMY = "images/dummy.png";


	public static double pointTowards(Point b, Point a) {
		// Point at something, This will be useful for enemies, also in
		// ImportantLook class
		return (double) (Math.toDegrees(Math.atan2(b.getY() + -a.getY(), b.getX()
				+ -a.getX())) + 180);
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

	public static int getFolderCont(String defaultDir) {
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
}
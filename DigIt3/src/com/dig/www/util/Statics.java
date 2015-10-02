package com.dig.www.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JComponent;



import javax.swing.JOptionPane;


//import com.manor.www.start.Board;
import com.dig.www.start.DigIt;

public final class Statics {

	public static final int BLOCK_HEIGHT = 100;

	public static final int BOARD_WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(); 
	public static final int BOARD_HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();;

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
	public static final Color DESERT_BLUE = new Color(100, 200, 255);
	public static final Color BLUE = new Color(50, 100, 255);
	public static final Color LIGHT_OFF_TAN = new Color(225,180,0);
	public static final Color OFF_TAN = new Color(220,185,60);
	public static final Color SAND_STONE = new Color(110,90,30);
	public static final Color LIGHT_BROWN=new Color(175, 75, 0);
	public static final Color MED_GRAY = new Color(150, 150, 160);
	
	// Colors likely used exclusively in the haunted skin.
	public static final Color DARK_GREEN = new Color(30, 72, 30);
	public static final Color DRAB_BROWN = new Color(68, 21, 15);
	public static final Color LIGHT_SAND_BLUE = new Color(63, 85, 116);
	public static final Color SAND_BLUE = new Color(48, 65, 88);
	public static final Color DARK_SAND_BLUE = new Color(32, 44, 60);
	public static final Color SAND_RED = new Color(155, 36, 36);
	
// Commented-out lighter versions of the above colors
//	public static final Color HAUNTED_GREEN = new Color(48, 118, 48);
//	public static final Color DRAB_BROWN = new Color(104, 34, 23);
//	public static final Color LIGHT_SAND_BLUE = new Color(85, 115, 155);
//	public static final Color SAND_BLUE = new Color(63, 85, 116);
//	public static final Color DARK_SAND_BLUE = new Color(48, 65, 88);
//	public static final Color SAND_RED = new Color(155, 36, 36);
	
	public static final Font BLOCK = new Font("Calibri", Font.PLAIN, 80);
	public static final Font NPC = new Font("Calibri", Font.PLAIN, 20);
	public static final Font MENU = new Font("Impact", Font.BOLD, 80);
	public static final String DUMMY = "images/dummy.png";

	public static final int LINE = 100;

	
	public static double pointTowards(Point b, Point a) {
		// Point at something, This will be useful for enemies, also in
		// ImportantLook class
		return (double) (Math.toDegrees(Math.atan2(b.getY() + -a.getY(), b.getX()
				+ -a.getX())) + 180);
	}

	public static void showError(String err, JComponent owner) {
		JOptionPane.showMessageDialog(owner, err, "Error",
				JOptionPane.ERROR_MESSAGE);
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

	public static void exit(JComponent owner) {
		// TODO Auto-generated method stub

		if (JOptionPane.showConfirmDialog(owner, "Are you sure you want to quit?\n(Unsaved data will be lost)", DigIt.NAME,
				JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION)
			System.exit(0);
	}
}
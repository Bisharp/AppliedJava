package com.dig.www.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Random;

import javax.swing.ImageIcon;

import javax.swing.JComponent;

//import com.manor.www.start.Board;
import com.dig.www.start.DigIt;

public final class Statics {

	public static final int BLOCK_HEIGHT = 100;
	public static final int INITIAL_OFFSET = 65;
	public static final int BLOCK_OFFSET = 40;

	public static final int BOARD_WIDTH = 1000;
	public static final int BOARD_HEIGHT = 700;

	public static final int JUMP_HEIGHT = 5;
	public static final int SPEED = 7;
	public static final float DEFENSE = 0.8f;
	public static final int STRENGTH = 1;
	public static final int WEIGHT = 10;
	public static final int JUMP_TIME = 20;

	public static final String INF = "INF.";
	public static final String LAMBDA = "/\\";

	public static final Font SMALL = new Font("Chiller", Font.BOLD, 20);
	public static final ImageIcon ICON = createImageIcon("/images/icon.png");

	public static final Random RAND = new Random();

	public static final String DAY = "day";
	public static final String NIGHT = "night";

	public static final Color NIGHT_SKY = //new Color(24, 24, 61);
			Color.black;
	public static final Color DAY_SKY = new Color(67, 67, 103);
	public static final Color TWILIGHT_SKY = new Color(40, 40, 140);
	public static final Color PURPLE = new Color(128, 0, 128);
	public static final Color ORANGE = new Color(254, 83, 1);
	public static final Color BROWN = new Color(128, 41, 0);
	public static final Color TAN = new Color(221, 182, 108);
	public static final Color LIGHT_GREEN = new Color(120, 255, 0);
	public static final Color OFF_GREEN = new Color(148, 190, 50);
	public static final Color LIGHT_OFF_GREEN = new Color(167, 207, 73);
	
	public static final Font ACHANGE = new Font("Algerian", Font.PLAIN, 80);
	public static final Font WARNING = new Font("Impact", Font.BOLD, 80);
	public static final String DUMMY = "images/dummy.png";
	
	public static final String UN = "+-+\n|?|\n+-+";
	public static final Color LIGHT_BLUE = new Color(132, 255, 255);
	
	public static boolean closeTo(int num1, int num2) {
		
		int threshHold = 5;
		
		if (Math.min(num1, num2) == num1) {
			if (num2 - threshHold <= num1)
				return true;
			else
				return false;
		} else {
			if (num1 - threshHold <= num2)
				return true;
			else
				return false;
		}
	}

	public static boolean willMorph() {
		switch (RAND.nextInt(300)) {
		case 0:
			return true;
		default:
			return false;
		}
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
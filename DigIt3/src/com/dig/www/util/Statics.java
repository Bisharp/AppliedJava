package com.dig.www.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

import com.dig.www.npc.NPC;
import com.dig.www.start.Board;
//import com.manor.www.start.Board;
import com.dig.www.start.DigIt;

public final class Statics {
	public static boolean is1024;
	public static final String MAIN="Story Mode";
	public static final int BLOCK_HEIGHT = 100;
	public static final String FONT = "Trebuchet MS";
public static boolean MAC=false;
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
	
	// This is for light stuff, still unfinished.
	public static final Color LIGHT = new Color(255, 255, 255, 50);

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
//	FilenameFilter dirFilter = new FilenameFilter() {
//		@Override
//		public boolean accept(File dir, String name) {
//			if (name.endsWith(".png") || name.endsWith(".gif")|| name.endsWith(".jpg"))
//				return true;
//			return false;
//		}
//	};
	String[]items=listFolder(folderLoc);
	
	return items[RAND.nextInt(items.length)];
}
public static String[] listFolder(String defaultDir,FilenameFilter ff) {//Outdated and will be removed. new ^
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
		return results;

	} catch (Exception ex) {
		return new String[0];
	}
}
public static String[] listFolder(String defaultDir) {//Outdated and will be removed. new ^
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
		return results;

	} catch (Exception ex) {
		return new String[0];
	}
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

	public static void exit(Board owner) {
		// TODO Auto-generated method stub

		if (JOptionPane.showConfirmDialog(owner, "Are you sure you want to quit?\n(Unsaved data will be lost)", DigIt.NAME,
				JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION){
		
			if (owner.getClient() != null && owner.getOtherServer() != null)
				try {
					String s = null;
					if (owner.getCharacter() != null)
						s =owner.getCharacter().getType().charName();
					owner.getOtherServer().leaveChatRoom(owner.getMPName(), s);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			System.exit(0);
		}
	}
	//Will be removed because Climb will not System.exit
	public static void exit(JComponent pane){
		System.exit(0);
	}
	public static double dist(int x1, int y1, int x2, int y2) {
		return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
	}
	
	public static String getBasedir()
	{
		if (basedir != null)
		{
			return basedir;
		}

		// use basedir property if set
		basedir = System.getProperty("digit3.basedir");
		if (basedir != null)
		{
			//from jar
			if (!basedir.endsWith("/"))
				basedir = basedir + "/";

			return basedir;
		}

		URL applicationRootPathURL = DigIt.class.getProtectionDomain().getCodeSource().getLocation();
		File applicationRootPath = new File(applicationRootPathURL.getPath());
		if (!applicationRootPath.isDirectory())
		{ // for deployed jar this will be the jar file. so need to get the
			// parent
			// in Eclipse this will be the bin dir
			applicationRootPath = applicationRootPath.getParentFile();
		}
		basedir = applicationRootPath.getAbsolutePath();
		if (!basedir.endsWith("/"))
			basedir = basedir + "/";
		System.out.println(basedir);
		return basedir;
	}

	public static String readFromNotJarFile(String filename)throws Exception
	{
		String readname=getBasedir()+filename;
		BufferedReader br=new BufferedReader(new FileReader(readname));
		String s;
		StringBuffer sb=new StringBuffer();
		while((s=br.readLine())!=null){
			sb.append(s+"\n");
		}
		br.close();
		return sb.toString();
		
	}
	public static String readFromJarFile(String filename) throws Exception
	{
		InputStream is = DigIt.class.getResourceAsStream(filename);
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		StringBuffer sb = new StringBuffer();
		String line;
		while ((line = br.readLine()) != null)
		{
			sb.append(line+"\n");
		}
		br.close();
		isr.close();
		is.close();
		return sb.toString();
	}
	public static String[] listFilesInNotJar(String path)throws IOException{
		String dir=getBasedir()+path;
		File f=new File(dir);
		return f.list();
	}
	public static String[] listFilesInJar(String path) throws IOException
	{
		ArrayList<String> filesList = new ArrayList<String>();

		URL applicationRootPathURL = DigIt.class.getProtectionDomain().getCodeSource().getLocation();
		File applicationRootPath = new File(applicationRootPathURL.getPath());
		if (applicationRootPath.isDirectory())
		{ // this is run when running inside Eclipse
			if (!path.startsWith("/"))
				path = "/" + path;
			File pathFile = new File(applicationRootPath.getAbsoluteFile() + path);
			return pathFile.list();
		}
		// only get here if running from jar file
		ZipInputStream zip = new ZipInputStream(applicationRootPathURL.openStream());
		while (true)
		{
			ZipEntry e = zip.getNextEntry();
			if (e == null)
				break;
			String name = e.getName();
			if (name.startsWith(path + "/"))
			{ // only want files starting with the path specified
				// strip off the path from the name
				name = name.substring(path.length() + 1);
				if (name.length() > 0)
				{
					// remove trailing / if there is one
					if (name.charAt(name.length() - 1) == '/')
						name = name.substring(0, name.length() - 1);
					
					// don't want to dive into subdirs so only list files in immediate dir
					if (!name.contains("/"))
						filesList.add(name);
				}
			}
		}
		return filesList.toArray(new String[filesList.size()]);
	}
	private static String basedir = null;
}
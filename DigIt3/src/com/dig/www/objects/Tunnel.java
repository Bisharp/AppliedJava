package com.dig.www.objects;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.dig.www.start.Board;
import com.dig.www.util.ActsOnFrameOne;
import com.dig.www.util.ImageLibrary;
import com.dig.www.util.Statics;

public class Tunnel extends Objects implements ActsOnFrameOne {

	public static String BRIDGE = "bridge";
	public static String STAIR = "stair";

	protected boolean frameOne = true;
	protected Direction dir;

	protected Railing[] rails = new Railing[2];

	public Tunnel(int x, int y, int width, int height, Board owner, Direction d, String skin) {
		super(x, y, Statics.DUMMY, false, owner, "");
		dir = d;
		this.width = width * Statics.BLOCK_HEIGHT;
		this.height = height * Statics.BLOCK_HEIGHT;

		image = createImage(skin, width, height, d);
	}

	public void actFrameOne() {
		frameOne = false;
		rails[0] = new Railing(x + rail1X(), y + rail1Y(), width(), height(), owner);
		rails[1] = new Railing(x + rail2X(), y + rail2Y(), width(), height(), owner);
		owner.getObjects().add(rails[0]);
		owner.getObjects().add(rails[1]);
	}

	public boolean isFrameOne() {
		return frameOne;
	}

	public void animate() {
		super.animate();

		if (frameOne)
			actFrameOne();
	}

	// @Override
	// public void draw(Graphics2D g2d) {
	// super.draw(g2d);
	//
	// if (frameOne)
	// return;
	//
	// g2d.setColor(Color.magenta);
	// g2d.fill(rails[0].getBounds());
	// g2d.fill(rails[1].getBounds());
	// }

	protected class Railing extends Objects {

		public Railing(int x, int y, int w, int h, Board owner) {
			super(x, y, Statics.DUMMY, true, owner, "");
			width = w;
			height = h;
		}

		@Override
		public void draw(Graphics2D g2d) {

		}
	}

	// --------------------This is the Image handling
	// stuff-------------------------------

	protected int rail1X() {
		return B_OFFSET;
	}

	protected int rail1Y() {
		return B_OFFSET;
	}

	protected int rail2X() {
		return dir.isVertical() ? width - B_OFFSET - WIDTH : B_OFFSET;
	}

	protected int rail2Y() {
		return dir.isVertical() ? B_OFFSET : height - B_OFFSET * 3;
	}

	protected int width() {
		return dir.isVertical() ? WIDTH : width - B_OFFSET * 2;
	}

	protected int height() {
		return dir.isVertical() ? height - B_OFFSET * 2 : WIDTH;
	}

	public enum Direction {
		UP {
			@Override
			boolean isVertical() {
				return true;
			}
		},
		DOWN {
			@Override
			boolean isVertical() {
				return true;
			}
		},
		LEFT, RIGHT;

		boolean isVertical() {
			return false;
		}
	}

	protected static final Color BRIDGE_RAILING = new Color(72, 36, 0);
	protected static final int HORIZ = 270;

	protected static int c = 0;

	protected static final int B_OFFSET = 8;
	protected static final int WIDTH = 10;

	protected Image createImage(String skin, int width, int height, Direction dir) {

		if (skin == BRIDGE)
			dir = dir.isVertical() ? Direction.UP : Direction.RIGHT;

		if (dir.isVertical()) {
			int temp = width;
			width = height;
			height = temp;

			temp = this.width;
			this.width = this.height;
			this.height = temp;
		}

		BufferedImage toReturn = newBImage(width, height);
		Graphics2D g2d = (Graphics2D) toReturn.getGraphics();

		final String loc = "images/objects/tunnel/" + skin + "/";

		for (int x = 0; x < width; x++)
			for (int y = 0; y < height; y++)
				// if (y == 0 || y == height - 1)
				// drawRailing()
				// else
				g2d.drawImage(Statics.newImage(loc + dir.toString() + ".png"), x * Statics.BLOCK_HEIGHT, y * Statics.BLOCK_HEIGHT, null);

		g2d.setColor(BRIDGE_RAILING);

		height *= Statics.BLOCK_HEIGHT;
		width *= Statics.BLOCK_HEIGHT;

		// if (dir.isVertical()) {
		g2d.fillRect(rail1X(), rail1Y(), width(), height());
		g2d.fillRect(rail2X(), rail2Y(), width(), height());

		g2d.setColor(Color.BLACK);

		g2d.drawRect(rail1X(), rail1Y(), width(), height());
		g2d.drawRect(rail2X(), rail2Y(), width(), height());
		// } else {
		// g2d.fillRect(rail1X(), rail1Y(), width - B_OFFSET * 2, WIDTH);
		// g2d.fillRect(rail2X(), rail2Y(), width - B_OFFSET * 2, WIDTH);
		//
		// g2d.setColor(Color.BLACK);
		//
		// g2d.drawRect(rail1X(), rail1Y(), width - B_OFFSET * 2, WIDTH);
		// g2d.drawRect(rail2X(), rail2Y(), width - B_OFFSET * 2, WIDTH);
		// }

		File outputfile = new File(Statics.getBasedir() + loc + c + ".png");
		outputfile.mkdirs();
		try {
			ImageIO.write(toReturn, "png", outputfile);
		} catch (IOException e) {
			e.printStackTrace();
		}

		shadow = createShadow();

		return toReturn;
	}

	protected Image createShadow() {
		BufferedImage i = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR_PRE);
		WritableRaster wr = i.getRaster();

		for (int x = 0; x < width; x++)
			for (int y = 0; y < height; y++)
				wr.setPixel(x, y, ImageLibrary.SHADOW);
		return i;
	}

	protected static BufferedImage newBImage(int width, int height) {
		return new BufferedImage(width * Statics.BLOCK_HEIGHT, height * Statics.BLOCK_HEIGHT, BufferedImage.TYPE_4BYTE_ABGR_PRE);
	}
}

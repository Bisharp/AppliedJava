package com.dig.www.objects;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import com.dig.www.start.Board;

public class InvisibleCheckPoint extends CheckPoint{

	public InvisibleCheckPoint(int x, int y, Board owner, int spawnNum) {
		super(x, y, owner, spawnNum);
		// TODO Auto-generated constructor stub
		
	}
	@Override
	public void draw(Graphics2D g2d) {
	}
	@Override
	public Image getShadow() {
		// TODO Auto-generated method stub
		return new ImageIcon().getImage();
	}
	@Override
	public boolean interact() {
		// TODO Auto-generated method stub
		return false;
	}
}

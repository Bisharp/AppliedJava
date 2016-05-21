package com.dig.www.objects;

import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.dig.www.start.Board;
import com.dig.www.start.DigIt;

public class Tree extends Objects{

	public Tree(int x, int y, String loc, Board owner) {
		super(x, y, loc, true, owner, "update");
	}
public boolean interact() {
	JOptionPane.showMessageDialog(owner, "A tree", DigIt.NAME + " Item Description", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(image));
	return true;
}
}

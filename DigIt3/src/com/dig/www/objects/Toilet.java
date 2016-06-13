package com.dig.www.objects;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.dig.www.start.Board;
import com.dig.www.start.DigIt;

public class Toilet extends Objects{

	public Toilet(int x, int y, String loc, Board owner) {
		super(x, y, loc, true, owner,"update");
		// TODO Auto-generated constructor stub
	}
@Override
public boolean interact() {
	if(JOptionPane.showOptionDialog(owner, "Just your average toilet.", DigIt.NAME+" Item Description", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, new ImageIcon(image),new String[]{"leave","flush"}, "leave")==1)
		JOptionPane.showMessageDialog(owner, "Good job! You just wasted three gallons of water.", DigIt.NAME + " Item Description", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(image));
	return true;
}
}
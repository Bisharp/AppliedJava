package com.dig.www.objects;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.dig.www.start.Board;
import com.dig.www.start.DigIt;

public class Sink extends Objects{
	public Sink(int x, int y, String loc, Board owner) {
		super(x, y, loc, true, owner,"update");
		// TODO Auto-generated constructor stub
	}
@Override
public boolean interact() {
	if(JOptionPane.showOptionDialog(owner, "A sink", DigIt.NAME+" Item Description", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, new ImageIcon(image),new String[]{"leave","wash your hands"}, "leave")==1)
		JOptionPane.showMessageDialog(owner, "There wasn't any soap, but you didn't really need to wash your hands anyway.", DigIt.NAME + " Item Description", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(image));
	return true;
}
}

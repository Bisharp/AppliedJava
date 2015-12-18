package com.dig.www.objects;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.dig.www.character.Items;
import com.dig.www.start.Board;
import com.dig.www.start.DigIt;
import com.dig.www.util.Statics;

public abstract class Collectible extends Objects {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Items type;
	
	public Collectible(int x, int y, String loc,boolean wall, Board owner, Items type) {
		super(x, y, loc, wall, owner,"update");
		this.type = type;
	}

	public Items getType() {
		return type;
	}

	public boolean collectible() {
		return true;
	}
	@Override
	public boolean interact(){
		String[]options={"Leave","Take"};
		return JOptionPane.showOptionDialog(owner,Items.getDesc(type.toString()), DigIt.NAME
				+ " Item Description",JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
				new ImageIcon(image),options,"Take"
				)==1;
	}
}

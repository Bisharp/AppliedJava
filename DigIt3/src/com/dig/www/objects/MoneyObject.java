package com.dig.www.objects;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.dig.www.character.Items;
import com.dig.www.start.Board;
import com.dig.www.start.DigIt;

public class MoneyObject extends Collectible {

	private int value;
	
	public MoneyObject(int x, int y, String loc, Board owner, int value) {
		super(x, y, loc,false, owner, Items.NULL);
		
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	@Override
	public boolean interact(){
String[]options={"Leave","Take"};
		return JOptionPane.showOptionDialog(owner,"This is Money, the game's currency. This particular coin is worth "+value+" Money.", DigIt.NAME
				+ " Item Description",JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
				new ImageIcon(image),options,"Take"
				)==1;
		
	}
}

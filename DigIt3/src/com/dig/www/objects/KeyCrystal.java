package com.dig.www.objects;

import com.dig.www.start.Board;
import com.dig.www.start.DigIt;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.dig.www.character.Items;

public class KeyCrystal extends Collectible {
	
	public final int id;
private int value;
	public KeyCrystal(int x, int y, String loc, Board owner, int id) {
		super(x, y, "images/objects/collectibles/keyCrystal/"+loc+".png",false, owner, Items.KEYCRYSTAL);
		this.id = id;
		value=Integer.parseInt(loc);
	}
	public int getValue(){
		return value;
	}
	@Override
	public boolean interact(){
		String[]options={"Leave","Take"};
		return JOptionPane.showOptionDialog(owner,Items.getDesc(getType().toString())+getValDesc(), DigIt.NAME
				+ " Item Description",JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
				new ImageIcon(image),options,"Take"
				)==1;
	}
	public String getValDesc(){
		if(value==5)
			return "\nThis is a super rare orange key crystal, worth five green key crystals.";
		if(value==3)
			return "\nThis is a semi rare yellow key crystal, worth three green key crystals.";
		return "";
	}
}
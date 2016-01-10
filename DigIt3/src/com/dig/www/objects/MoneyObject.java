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
	public String adder(){
		String s="\n ";
		switch(value){
		case 3:
			s+="About the price of a burger.";
			break;
		case 20:
			s+= "Don't spend it all in one place!";
			break;
		case -100:
			s+="Why would I ever pick that up?";
			break;
		default:
			return "";
		}
		return s;
	}
	@Override
	public boolean interact(){
String[]options={"Leave","Take"};
		return JOptionPane.showOptionDialog(owner,"This is Money, the game's currency. This particular coin is worth "+value+" Money."+adder(), DigIt.NAME
				+ " Item Description",JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
				new ImageIcon(image),options,"Take"
				)==1;
		
	}
}

package com.dig.www.objects;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.dig.www.character.GameCharacter;
import com.dig.www.character.Items;
import com.dig.www.start.Board;
import com.dig.www.start.DigIt;

public class Chest extends Objects{
	Items type;
	public Chest(int x, int y, Board owner,Items item) {
		super(x, y, "images/objects/chestC.png", true, owner, "update");
		saveString="C";
		type=item;
	}
	public void setSaveString(String newString){
		saveString=newString;
		image=newImage("images/objects/chest"+saveString+".png");
		shadow=newShadow("images/objects/chest"+saveString+".png");
	}
	@Override
	public boolean interact(){
		String[]options={"Leave","Open"};
		if(saveString.equals("C")){
		 boolean b=JOptionPane.showOptionDialog(owner,"This is a"+(saveString.equals("C")?" closed":"n opened")+" wooden chest.", DigIt.NAME
				+ " Item Description",JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
				new ImageIcon(image),options,"Open"
				)==1;
		 if(b){
			 saveString="O";
			 image=newImage("images/objects/chest"+saveString+".png");
			 shadow=newShadow("images/objects/chest"+saveString+".png");
			 owner.getData().setSaveString(this);
			 GameCharacter.getInventory().addItem(type, 1);
			 JOptionPane.showMessageDialog(owner,"It's got a(n) " + type.toString() + " in it.",DigIt.NAME
						+ " Item Description", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(image));
		if(type==Items.PLAINKEY){
			if(owner.getKeyMap().containsKey(owner.getLevel())){
			int a = owner.getKeyMap().get(owner.getLevel())+1;
			owner.getKeyMap().remove(owner.getLevel());
			owner.getKeyMap().put(owner.getLevel(), a);
			}
			else
			owner.getKeyMap().put(owner.getLevel(), 1);	
			}
		 }}else
			 JOptionPane.showMessageDialog(owner,"This is a"+(saveString.equals("C")?" closed":"n opened")+" wooden chest.",DigIt.NAME
						+ " Item Description", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(image));
		 return true;
	}
}

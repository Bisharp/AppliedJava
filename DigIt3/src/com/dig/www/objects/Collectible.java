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
		saveString="true";
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
		 boolean b=JOptionPane.showOptionDialog(owner,Items.getDesc(type.toString(),owner), DigIt.NAME
				+ " Item Description",JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
				new ImageIcon(image),options,"Take"
				)==1;
		 if(b){
			 saveString="false";
			 owner.getData().setSaveString(this);
		if(type==Items.PLAINKEY){
			if(owner.getKeyMap().containsKey(owner.getLevel())){
			int a = owner.getKeyMap().get(owner.getLevel())+1;
			owner.getKeyMap().remove(owner.getLevel());
			owner.getKeyMap().put(owner.getLevel(), a);
			}
			else
			owner.getKeyMap().put(owner.getLevel(), 1);	
			}
		 }
		 return b;
	}
	public boolean resolveSaveString(){
		return Boolean.parseBoolean(saveString);
	}
}

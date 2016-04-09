package com.dig.www.objects;

import java.awt.Graphics2D;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.dig.www.character.GameCharacter;
import com.dig.www.character.Items;
import com.dig.www.start.Board;
import com.dig.www.start.DigIt;

public class DangerousFire extends Objects{

	public DangerousFire(int x, int y, Board owner) {
		super(x, y, "images/objects/householdObjects/dangerousFire.gif", true, owner,"update");
		// TODO Auto-generated constructor stub
	}
	@Override
	public void draw(Graphics2D g2d) {
		g2d.drawImage(image, x, y, owner);
	}
@Override
public boolean interact() {
	if(GameCharacter.getInventory().contains(Items.GRAVEBOX)){
		String[]options={"Leave","Put the box in the fire"};
		boolean b=JOptionPane.showOptionDialog(owner,"This fire gives off light and heat. It is dangerous though.", DigIt.NAME
				+ " Item Description",JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
				new ImageIcon(image),options,"Leave"
				)==1;
		if(b){
			GameCharacter.getInventory().decrementItem(Items.GRAVEBOX, 1);
			GameCharacter.getInventory().addItem(Items.PLAINKEY, 1);
			JOptionPane.showMessageDialog(owner, "The box burns, but there was a key inside.",DigIt.NAME+" Item Description",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(image));
		}
		return b;
	}else{
		JOptionPane.showMessageDialog(owner, "This fire gives off light and heat. It is dangerous though.", DigIt.NAME + " Item Description", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(image));
	return true;
	}
}
}

package com.dig.www.objects;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.dig.www.character.GameCharacter;
import com.dig.www.character.Items;
import com.dig.www.start.Board;
import com.dig.www.start.DigIt;

public class Gate extends Objects{

	public Gate(int x, int y, String loc, boolean wall, Board owner) {
		super(x, y, loc//+(wall?"closed.png":"open.png")
				, wall, owner,"update");
		// TODO Auto-generated constructor stub
	}
@Override
public boolean interact() {
	// TODO Auto-generated method stub
	//JOptionPane.showMessageDialog(owner, "hi");
if(wall){
	String[]options={"Leave","Open"};
	boolean b=JOptionPane.showOptionDialog(owner,"This closed gate bars the way.", DigIt.NAME
			+ " Item Description",JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
			new ImageIcon(image),options,"Leave"
			)==1;
	if(b){
		if(GameCharacter.getInventory().contains(Items.PLAINKEY)){
			//JOptionPane.showMessageDialog(owner, "You open the gate with your key.", DigIt.NAME + " Item Description", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(image));
			GameCharacter.getInventory().decrementItem(Items.PLAINKEY, 1);
			wall=false;
			image=newImage("images/objects/BigRedButton.png");
			shadow=newShadow("images/objects/BigRedButton.png");
			return true;
		}else{
			JOptionPane.showMessageDialog(owner, "You don't have a key.", DigIt.NAME + " Item Description", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(image));
			return false;
		}
	}else{
		return false;
	}
}
	else{
		JOptionPane.showMessageDialog(owner, "This gate is already open.", DigIt.NAME + " Item Description", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(image));
		return false;
	}
		
	
}
}

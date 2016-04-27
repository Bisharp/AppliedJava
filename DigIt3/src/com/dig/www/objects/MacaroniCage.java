package com.dig.www.objects;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.dig.www.character.GameCharacter;
import com.dig.www.character.Items;
import com.dig.www.character.GameCharacter.Types;
import com.dig.www.enemies.Enemy;
import com.dig.www.enemies.SpinnyBoss;
import com.dig.www.npc.CagedMacaroni;
import com.dig.www.npc.Macaroni;
import com.dig.www.npc.NPC;
import com.dig.www.start.Board;
import com.dig.www.start.DigIt;

public class MacaroniCage extends CollectibleCharacter{
private String location;
	public MacaroniCage(int x, int y,String location, Board owner) {
		super(x, y,"images/objects/macaroniCage.png", owner);
		this.location=location;
		// TODO Auto-generated constructor stub
	}
public void makeCharacter() {
			// add Super Macaroni Noodle Man code here
			owner.getFriends().add(new com.dig.www.character.Macaroni(x, y, owner, false));
			owner.heyIaddedAFriendBack(owner.getFriends().get(owner.getFriends().size()-1),Types.MACARONI.toString());
		
	}
@Override
public boolean interact() {
	// TODO Auto-generated method stub
	owner.getCharacter().stop();
		String[]options={"Leave","Unlock"};
		boolean b=JOptionPane.showOptionDialog(owner,"This cage is locked.", DigIt.NAME
				+ " Door Dialog",JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
				new ImageIcon(image),options,"Leave"
				)==1;
		if(b){
			if(GameCharacter.getInventory().contains(Items.PLAINKEY)){
				GameCharacter.getInventory().decrementItem(Items.PLAINKEY, 1);
				//locked=false;
				//owner.getData().unlockDoor(owner.getPortals().indexOf(this));
				//image=newImage(path+"c.png");
				//owner.talk(new CagedMacaroni(-10000, -10000, owner, location,true));
				for(Enemy e:owner.getEnemies())
					if(e instanceof SpinnyBoss)
						((SpinnyBoss)e).deactivateShield();
				return true;
			}
			
			JOptionPane.showMessageDialog(owner, "You do not have a key.", DigIt.NAME + " Door Dialog", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(image));
		}
		//owner.talk(new CagedMacaroni(-10000, -10000, owner, location,false));
		return false;
	//}
	
	//return true;
}
}

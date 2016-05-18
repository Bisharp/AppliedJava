package com.dig.www.blocks;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.dig.www.character.GameCharacter;
import com.dig.www.character.Items;
import com.dig.www.start.Board;
import com.dig.www.start.DigIt;
import com.dig.www.util.PlayerList;

public class BossPortal extends Portal {
private int collectibleNum;
private boolean opened;
private PlayerList list;
	public BossPortal(int x, int y, Board owner, String area, String type, int spawnNum,int collectibles,PlayerList list) {
		super(x, y, owner, area, type+"/"+(collectibles==0?"open":"closed"), spawnNum);
		this.collectibleNum=collectibles;
		opened=collectibleNum==0;
		this.list=list;
		System.out.println(collectibleNum);
		// TODO Auto-generated constructor stub
	}
public int getCollectibleNum(){
	return collectibleNum;
}
public void doBoolean(Boolean boolean1) {
	// TODO Auto-generated method stub
	opened=boolean1;
	if(boolean1)
	type="boss/open";
}
@Override
	public boolean interact() {
	owner.getCharacter().stop();
		if(opened){
			ArrayList<String>names=new ArrayList<String>();
			for(int c=0;c<owner.getFriends().size();c++){
				names.add(owner.getFriends().get(c).getType().toString());
			}
	names.add(owner.getCharacter().getType().toString());
			if(list.special()||(names.containsAll(list.getStuff())&&list.getStuff().containsAll(names))){
			
			return true;}
			else{
				String[]options={"Leave","Accept"};
				boolean b=JOptionPane.showOptionDialog(owner,"Entering this portal will change your follower configurations.", DigIt.NAME
						+ " Dimensional Key Dialog",JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
						new ImageIcon(image),options,"Accept"
						)==1;
				if(b){
					list.sort();
				return true;}else
					return false;
			}
		}else{
			String[]options={"Leave","Use the Dimensional Key"};
			boolean b=JOptionPane.showOptionDialog(owner,"A mysterious steel entity is surrounding the portal.\nYou cannot pass.", DigIt.NAME
					+ " Dimensional Key Dialog",JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
					new ImageIcon(image),options,"Leave"
					)==1;
			if(b){
				if(GameCharacter.getInventory().contains(Items.KEYCRYSTAL)&&GameCharacter.getInventory().getItemNum(Items.KEYCRYSTAL)>=collectibleNum){
				GameCharacter.plusXP(25*(GameCharacter.getInventory().getItemNum(Items.KEYCRYSTAL)-collectibleNum), owner.getCharacter());
				GameCharacter.getInventory().decrementItem(Items.KEYCRYSTAL, GameCharacter.getInventory().getItemNum(Items.KEYCRYSTAL));
				opened=true;
				type="boss/open";
				owner.getData().unlockDoor(owner.getPortals().indexOf(this),true);
				return false;
				}
				JOptionPane.showMessageDialog(owner, "You do not have enough Key Crystals.", DigIt.NAME+ " Dimensional Key Dialog", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(image));
			}
			return false;
		}
	}
}

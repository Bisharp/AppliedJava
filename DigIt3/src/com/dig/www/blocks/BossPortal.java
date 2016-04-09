package com.dig.www.blocks;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.dig.www.character.GameCharacter;
import com.dig.www.character.Items;
import com.dig.www.start.Board;
import com.dig.www.start.DigIt;

public class BossPortal extends Portal {
private int collectibleNum;
private boolean opened;
private String[]list;
	public BossPortal(int x, int y, Board owner, String area, String type, int spawnNum,int collectibles,String[]list) {
		super(x, y, owner, area, type+"/"+(collectibles==0?"open":"closed"), spawnNum);
		this.collectibleNum=collectibles;
		opened=collectibleNum==0;
		this.list=list;
		// TODO Auto-generated constructor stub
	}
public int getCollectibleNum(){
	return collectibleNum;
}
public void doBoolean(Boolean boolean1) {
	// TODO Auto-generated method stub
	opened=boolean1;
}
@Override
	public boolean interact() {
	owner.getCharacter().stop();
		if(opened){
			ArrayList<String>names=new ArrayList<String>();
			for(int c=0;c<owner.getFriends().size();c++){
				names.add(owner.getFriends().get(c).getType().toString());
			}
			ArrayList<String>names2=new ArrayList<String>();
			for(int c=0;c<list.length;c++){
				names2.add(list[c]);
			}
			if(names.containsAll(names2)&&names2.containsAll(names)){
			
			return true;}
			else{
				String[]options={"Leave","Accept"};
				boolean b=JOptionPane.showOptionDialog(owner,"Entering this portal will change your follower configurations.", DigIt.NAME
						+ " Dimensional Key Dialog",JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
						new ImageIcon(image),options,"Accept"
						)==1;
				if(b){
					for(int c=0;c<names2.size();c++){
						boolean friendsContain=false;
						for(int c2=0;c2<owner.getFriends().size();c2++){
							if(!names2.contains(owner.getFriends().get(c2).getType().toString())){
								owner.getGoneFriends().add(owner.getFriends().get(c2).getSave());
								owner.getFriends().remove(c2);
							}
							else if(names2.get(c).equals(owner.getFriends().get(c2).getType().toString())){
								friendsContain=true;
							}
						}
						if(!names2.contains(owner.getCharacter().getType().toString())){
							//switch to other character
						GameCharacter temp=owner.getFriends().get(0);
						owner.getFriends().set(0, temp);
						owner.setCharacter(temp);
						}else if(owner.getCharacter().getType().toString().equals(names2.get(c))){
							friendsContain=true;
						}
						if(!friendsContain){
							owner.getFriends().add(owner.getACharacter(names2.get(c)));
							owner.heyIaddedAFriendBack(owner.getFriends().get(owner.getFriends().size()-1), names2.get(c));
						}
					}
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
				return false;
				}
				JOptionPane.showMessageDialog(owner, "You do not have enough Key Crystals.", DigIt.NAME+ " Dimensional Key Dialog", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(image));
			}
			return false;
		}
	}
}

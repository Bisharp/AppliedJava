package com.dig.www.objects;

import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.dig.www.character.GameCharacter;
import com.dig.www.character.Items;
import com.dig.www.npc.FactoryBridgeNPC;
import com.dig.www.npc.TutorialWizard;
import com.dig.www.npc.TutorialWizard2;
import com.dig.www.start.Board;
import com.dig.www.start.DigIt;

public class FactoryBridge extends Objects{
private boolean broken;
	public FactoryBridge(int x, int y,Board owner) {
		super(x, y, "images/objects/bridge/factory/unbroken.png", true, owner, "update");
		// TODO Auto-generated constructor stub
	}
	public void breakIt(){
		if(!broken){
			broken=true;
			image=newImage("images/objects/bridge/factory/broken.png");
		}
	}
	public Rectangle getBounds() {
		if(broken)
			return new Rectangle(x+25,y,200,100);
		return new Rectangle(x,y,250,100);
	};
	@Override
		public void collidePlayer(int playerNum) {
		if(!broken&&(GameCharacter.storyInt>7||(GameCharacter.getInventory().contains(Items.KEYCRYSTAL)&&GameCharacter.getInventory().getItemNum(Items.KEYCRYSTAL)>=2))){
			if (playerNum == -1) {
					owner.getCharacter().collision(this, false);

			} else {
					owner.getFriends().get(playerNum).collision(this, false);

			}
			owner.scroll(owner.getCharacterX()-(x+25+300), owner.getCharacterY()-y);
			owner.getCharacter().setX(x+25+300);
			owner.getCharacter().setY(y);
			for(int c=0;c<owner.getFriends().size();c++){
				owner.getFriends().get(c).setX(x+25+300);
				owner.getFriends().get(c).setY(y);
			}
			for(int c=0;c<owner.getNPCs().size();c++){
				if(owner.getNPCs().get(c) instanceof TutorialWizard2){
					((TutorialWizard2)owner.getNPCs().get(c)).next();
					owner.getNPCs().get(c).setX(owner.getNPCs().get(c).getX()+400);
					break;}
			}
			for(int c=0;c<owner.getObjects().size();c++){
				if(owner.getObjects().get(c) instanceof CheckPoint){
					((CheckPoint)owner.getObjects().get(c)).collidePlayer(-1);
					break;}
			}
			owner.talk(new FactoryBridgeNPC(x,y,owner,this));
		}else{
			
		if (playerNum == -1) {
			if(!broken)
			JOptionPane.showMessageDialog(owner, "You must collect the other Key Crystal to continue.", DigIt.NAME + " Item Description", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(image));
				owner.getCharacter().collision(this, false);

		} else {
				owner.getFriends().get(playerNum).collision(this, false);

		}
		}}
@Override
public boolean isWall() {
	return true;
}
@Override
public boolean interact() {
	String desc="A wood bridge";
if(broken){
	desc="A now broken wood bridge";
}
	JOptionPane.showMessageDialog(owner, desc, DigIt.NAME + " Item Description", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(image));
	return false;
}
}

package com.dig.www.blocks;

import java.awt.Graphics2D;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.dig.www.character.GameCharacter;
import com.dig.www.character.Items;
import com.dig.www.objects.Objects;
import com.dig.www.start.Board;
import com.dig.www.start.DigIt;
import com.dig.www.start.Board.State;

public class Door extends Portal{
private String doorType;
protected String path;
private boolean locked;
	public Door(int x, int y, Board owner, String area,
			String type,String doorType,int spawnNum,boolean locked) {
		super(x, y, owner, area, type,"images/portals/" + type + "/" + doorType//Color
				+"/"+(locked?"l":"c")+".png",spawnNum);
		// TODO Auto-generated constructor stub
	this.doorType=doorType;
	path="images/portals/" + type + "/" + doorType//Color
			+"/";
	image=newImage(path+(locked?"l":"c")+".png");//c=closed,l=locked,o=open
	this.locked=locked;
	}
	@Override
	public void animate() {
		basicAnimate();
	}
	@Override
	public void draw(Graphics2D g2d) {
		g2d.drawImage(image, x, y, owner);
		if (owner.darkenWorld())
			g2d.drawImage(shadow, x, y, owner);
	}
	@Override
	public String getArea() {
		image=new ImageIcon(getClass().getResource("/images/portals/doors/brown/o.png")).getImage();
		return area;
	}
	@Override
		public boolean interact() {
		owner.getCharacter().stop();
			if(!locked)
			return true;
			else{
				String[]options={"Leave","Unlock"};
				boolean b=JOptionPane.showOptionDialog(owner,"This door is locked.", DigIt.NAME
						+ " Door Dialog",JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
						new ImageIcon(image),options,"Leave"
						)==1;
				if(b){
					if(GameCharacter.getInventory().contains(Items.PLAINKEY)){
						GameCharacter.getInventory().decrementItem(Items.PLAINKEY, 1);
						locked=false;
						owner.getData().unlockDoor(owner.getPortals().indexOf(this));
						image=newImage(path+"c.png");
						return false;
					}
					JOptionPane.showMessageDialog(owner, "You do not have a key.", DigIt.NAME + " Door Dialog", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(image));
				}
				return false;
			}
		}
	public boolean isLocked(){
		return locked;
	}
	public void doBoolean(Boolean boolean1) {
		// TODO Auto-generated method stub
		locked=boolean1;
		image=newImage(path+(locked?"l":"c")+".png");
	}
}

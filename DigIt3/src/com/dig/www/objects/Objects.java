package com.dig.www.objects;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.dig.www.character.Items;
import com.dig.www.start.Board;
import com.dig.www.start.DigIt;
import com.dig.www.util.Sprite;

public  class Objects extends Sprite{
protected boolean wall;
	protected String desc;
	public Objects(int x, int y, String loc,boolean wall,Board owner,String desc) {
		super(x, y, loc,owner);
		// TODO Auto-generated constructor stub
		this.setWall(wall);
		if(desc!=null)
		this.desc=desc;
		else
			desc="An object.";
	}
	@Override
	public void animate() {
		// TODO Auto-generated method stub
		basicAnimate();
	}
	@Override
	public void draw(Graphics2D g2d) {
		g2d.drawImage(image, x, y, owner);
		if (owner.darkenWorld())
			g2d.drawImage(shadow, x, y, owner);
	}
	public boolean isWall() {
		return wall;
	}
	public void setWall(boolean wall) {
		this.wall = wall;
	}

	public void collidePlayer(int playerNum){
		if(loc.equals("images/objects/Leaves.png")){
			loc="images/objects/LeavesSc.png";
			image=newImage(loc);
			shadow = newShadow(loc);
			x-=15;
			y-=15;
		}
		if(playerNum==-1){
			if(wall)
				owner.getCharacter().collision(this, false);
				
			
		}
		else{
			if(wall)
				owner.getFriends().get(playerNum).collision(this, false);
				
		}
	}
	public void collideWall() {
	}
	public boolean interact(){
		JOptionPane.showMessageDialog(owner,"Description", DigIt.NAME
				+ " Item Description", JOptionPane.INFORMATION_MESSAGE,
				new ImageIcon(image)
				);
		return true;
	}
}

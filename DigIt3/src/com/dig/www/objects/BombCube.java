package com.dig.www.objects;

import java.awt.Graphics2D;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.dig.www.blocks.Block;
import com.dig.www.enemies.BigExplosion;
import com.dig.www.start.Board;
import com.dig.www.start.DigIt;

public class BombCube extends Objects{
private boolean exploded;
	public BombCube(int x, int y, Board owner) {
		super(x, y, "images/objects/bombCube.png",true, owner, "bombCube");
		// TODO Auto-generated constructor stub
	}
	@Override
		public void collidePlayer(int playerNum) {
			// TODO Auto-generated method stub
		if(!exploded){
			setWall(false);
			exploded=true;
			shadow=new ImageIcon().getImage();
			owner.getEnemies().add(new BigExplosion(x-75, y-75, owner,Integer.MAX_VALUE));}
		}
	@Override
		public void draw(Graphics2D g2d) {
			// TODO Auto-generated method stub
		if(!exploded)	
		super.draw(g2d);
		}
	public boolean interact(){
		String[]options={"Leave","Pull","Open"};
	boolean b=JOptionPane.showOptionDialog(owner,desc, DigIt.NAME
				+ " Item Description",JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
				new ImageIcon(image),options,"Leave"
				)!=0;
	if(b&&!exploded){
		setWall(false);
		exploded=true;
		shadow=new ImageIcon().getImage();
		owner.getEnemies().add(new BigExplosion(x-75, y-75, owner,Integer.MAX_VALUE));
	}
		return b;
	}
}

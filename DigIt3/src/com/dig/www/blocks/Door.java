package com.dig.www.blocks;

import java.awt.Graphics2D;

import javax.swing.ImageIcon;

import com.dig.www.objects.Objects;
import com.dig.www.start.Board;
import com.dig.www.start.Board.State;

public class Door extends Portal{
private String doorType;
protected String path;
	public Door(int x, int y, Board owner, String area, int collectibles,
			String type,String doorType,int spawnNum) {
		super(x, y, owner, area, collectibles, type,"images/portals/" + type + "/" + doorType//Color
				+"/"+"c.png",spawnNum);
		// TODO Auto-generated constructor stub
	this.doorType=doorType;
	path="images/portals/" + type + "/" + doorType//Color
			+"/";
	image=newImage(path+"c.png");//c=closed,l=locked,o=open
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
}

package com.dig.www.blocks;

import javax.swing.ImageIcon;

import com.dig.www.objects.Objects;
import com.dig.www.start.Board;

public class Door extends Portal{
private String doorType;
protected String path;
	public Door(int x, int y, Board owner, String area, int collectibles,
			String type,String doorType) {
		super(x, y, owner, area, collectibles, type,"images/portals/" + type + "/" + doorType//Color
				+"/"+"c.png");
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
	public String getArea() {
		for(int c=0;c<10;c++){
			owner.getObjects().add(new Objects(c*75, c*100, path+"o.png", false, owner));
		}
		
		image=new ImageIcon(getClass().getResource("/images/portals/rectDoors/brown/o.png")).getImage();
		shadow=new ImageIcon(getClass().getResource("/images/portals/rectDoors/brown/o.png")).getImage();
		owner.repaint();
		image=new ImageIcon(getClass().getResource("/images/portals/rectDoors/brown/o.png")).getImage();
		shadow=new ImageIcon(getClass().getResource("/images/portals/rectDoors/brown/o.png")).getImage();
	
		owner.repaint();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return area;
	}
}

package com.dig.www.objects;

import java.awt.Color;
import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.dig.www.blocks.Block;
import com.dig.www.character.GameCharacter;
import com.dig.www.character.Items;
import com.dig.www.enemies.Explosion;
import com.dig.www.start.Board;
import com.dig.www.start.DigIt;

public class PushCube extends Objects{
private boolean willIsWall;
private boolean inBetween;//This makes it so that friends will pathfind around it.
private int spawnX;
private int spawnY;
private boolean canIns;
	public PushCube(int x, int y,Board owner,boolean canIns) {
		super(x, y, "images/objects/pushCube.png", true, owner, "pushCube");
		// TODO Auto-generated constructor stub
		spawnX=x;
		spawnY=y-100;
		this.canIns=canIns;
	}
	public PushCube(int x, int y,String loc,Board owner,boolean canIns, String identifier) {
		super(x, y, loc, true, owner,identifier);
		// TODO Auto-generated constructor stub
		spawnX=x;
		spawnY=y-100;
		this.canIns=canIns;
	}
@Override
public void collidePlayer(int playerNum) {
	// TODO Auto-generated method stub
	//super.collidePlayer(playerNum);
	GameCharacter p;
	if(playerNum==-1){
		p=owner.getCharacter();
	
	int oldX=x;
	int oldY=y;
	Point mid=new Point(getMidX(),getMidY());
	Point pMid=new Point(p.getMidX(),p.getMidY());
	if(Math.abs(mid.x-pMid.x)>30){
		if(mid.x>pMid.x)
			x+=10;
		else
			x-=10;
	}
	if(Math.abs(mid.y-pMid.y)>30){
		if(mid.y>pMid.y)
			y+=10;
		else
			y-=10;
	}
	for (int i = owner.getStartPoint(); i < owner.getWorld().size(); i++) {
		if(!owner.getWorld().get(i).traversable()&&owner.getWorld().get(i).getBounds().intersects(getBounds())){
			x=oldX;
			y=oldY;
			willIsWall=true;
			super.collidePlayer(playerNum);
			return;
		}
	}
	for (int i = 0; i < owner.getNPCs().size(); i++) {
		if(owner.getNPCs().get(i).isObstacle()&&owner.getNPCs().get(i).getBounds().intersects(getBounds())){
			x=oldX;
			y=oldY;
			willIsWall=true;
			super.collidePlayer(playerNum);
			return;
		}
	}}else{
		super.collidePlayer(playerNum);
		willIsWall=true;}
}
@Override
public void animate(){
	super.animate();
	inBetween=false;
}
@Override
public void draw(java.awt.Graphics2D g2d) {
	super.draw(g2d);
	inBetween=true;
};
@Override
public boolean isWall() {
	// TODO Auto-generated method stub
	if(willIsWall||inBetween){
		willIsWall=false;
	return true;}
	else
		return false;
}
public boolean interact(){
	if(canIns){
	String[]options={"Leave","Pull","Pull2"};
boolean b=JOptionPane.showOptionDialog(owner,desc, DigIt.NAME
			+ " Item Description",JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
			new ImageIcon(image),options,"Leave"
			)==1;
if(b){
		int tx=owner.getCharacterX();
		int ty=owner.getCharacterY();
		int willX=x;
		int willY=y;
		boolean moveX=Math.abs((tx-x))>30;
		boolean moveY=Math.abs((ty-y))>30;
		owner.scroll(moveX?(-(tx-x)):0, moveY?(-(ty-y)):0);
		owner.getCharacter().setX(tx);
		owner.getCharacter().setY(ty);
		x=willX;
		y=willY;
		for (Block bl:owner.getWorld()) {
			if(!bl.traversable()&&(owner.getCharacter().getCollisionBounds().intersects(bl.getBounds()))){
				owner.scroll(moveX?((tx-x)):0, moveY?((ty-y)):0);owner.getCharacter().setX(tx);
		owner.getCharacter().setY(ty);
		x=willX;
		y=willY;
			}
		}
	}
	return b;}
	else{
		JOptionPane.showMessageDialog(owner,desc, DigIt.NAME
				+ " Item Description", JOptionPane.INFORMATION_MESSAGE,
				new ImageIcon(image)
				);
		return true;
	}
}
}

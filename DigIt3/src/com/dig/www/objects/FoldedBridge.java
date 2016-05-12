package com.dig.www.objects;

import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.dig.www.start.Board;
import com.dig.www.start.DigIt;

public class FoldedBridge extends Objects{
protected boolean unfolded;
private boolean gone;
	public FoldedBridge(int x, int y, Board owner) {
		super(x, y, "images/objects/bridge/folded.png", true, owner,"update");
	}
	@Override
		public void animate() {
			if(!gone){
				gone=true;
				owner.getObjects().add(new FoldedBridgeUnfolder(x+200,y+225,owner,this));
			}
			super.animate();
		}
	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y+25, 100, height-25);
	}
@Override
public boolean interact() {
	return false;
}
public void unfold(){
	wall=false;
	unfolded=true;
	image=newImage("images/objects/bridge/unfolded.png");
	shadow=newShadow("images/objects/bridge/unfolded.png");
}
}

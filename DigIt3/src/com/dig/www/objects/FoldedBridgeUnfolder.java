package com.dig.www.objects;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.dig.www.start.Board;

public class FoldedBridgeUnfolder extends SensorObject{
FoldedBridge maker;
	public FoldedBridgeUnfolder(int x, int y, Board owner,FoldedBridge maker) {
		super(x, y, owner);
	this.maker=maker;
	height=500;
	}
	@Override
	public void action() {
		maker.unfold();
		
	}

}

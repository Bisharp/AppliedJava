package com.dig.www.npc;

import java.awt.Color;

public class CutSceneImage {
	private Color backColor=Color.BLACK;
	private String loc;
public CutSceneImage(String loc){
	this.loc=loc;
}
public CutSceneImage(String loc,Color backColor){
	this.loc=loc;
	this.backColor=backColor;
}
public Color getBackColor() {
	return backColor;
}
public String getLoc() {
	return loc;
}
}

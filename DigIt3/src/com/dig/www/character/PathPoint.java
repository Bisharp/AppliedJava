package com.dig.www.character;


import java.awt.Point;

public class PathPoint extends Point{
int energy;
int distanceLeft;
int score;

int direction;
public PathPoint(int x, int y,int energy,int distanceLeft,int direction){
	super(x,y);
	this.distanceLeft=distanceLeft;
	this.energy=energy;
	score=energy+distanceLeft;
	this.direction=direction;
}
public void update(int scrollX,int scrollY) {
	// TODO Auto-generated method stub
	x+=scrollX;
	y+=scrollY;
}
}

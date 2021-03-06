package com.dig.www.objects;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.dig.www.start.Board;
import com.dig.www.start.DigIt;

public abstract class SensorObject extends Objects{
protected boolean activated;
private boolean thisOnly;
	public SensorObject(int x, int y, Board owner) {
		super(x, y, null, false, owner, "update");
		// TODO Auto-generated constructor stub
	}
	public SensorObject(int x, int y,boolean thisOnly, Board owner) {
		super(x, y, null, false, owner, "update");
	this.thisOnly=thisOnly;
	}
	public void collidePlayer(int playerNum){
		if(!activated){
			acivate();}
	}
	public void acivate(){
		activated=true;
		if(!thisOnly)
		for(Objects o:owner.getObjects())
			if(o.getClass().toString().equals(this.getClass().toString())&& o instanceof SensorObject)
				((SensorObject)o).setActivated(true);
		action();
	}
	public abstract void action();
	
	@Override
	public boolean interact(){
		return false;
	}
	public void setActivated(boolean activated) {
		this.activated = activated;
	}
}

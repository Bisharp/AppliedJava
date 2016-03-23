package com.dig.www.npc;

import java.awt.Image;

import com.dig.www.start.Board;

public class MPNPC extends InvisibleNormalOnceTouchNPC{

	public MPNPC(int x, int y, Board owner,String loc,boolean isObstacle) {
		super(x, y, owner,isObstacle);
	this.loc=loc;
		// TODO Auto-generated constructor stub
	}

	@Override
	public String exitLine() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getShowName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getGreeting() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getFarewell() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean willTalk(){
		return false;
	}

}

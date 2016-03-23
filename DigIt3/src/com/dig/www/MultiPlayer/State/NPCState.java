package com.dig.www.MultiPlayer.State;

public class NPCState extends SpriteState{
private boolean wall;
private boolean isChange;
private String change;
	public NPCState(int x, int y,boolean isChange,String changeImage,boolean wall) {
		super(x, y);
		// TODO Auto-generated constructor stub
		this.isChange=isChange;
		this.change=changeImage;
		this.wall=wall;
	}
	public boolean isChange(){
		return isChange;
	}
	public String getChange() {
		return change;
	}
	public boolean isWall() {
		return wall;
	}

}

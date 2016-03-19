package com.dig.www.npc;

public class CSDialog {
private String line;
private String name;
private CutSceneImage cS;
public CSDialog(String line,String name,CutSceneImage cS){
	this.line=line;
	this.name=name;
	this.cS=cS;
}
public String getLine() {
	return line;
}
public String getName() {
	return name;
}
public CutSceneImage getcS() {
	return cS;
}

}

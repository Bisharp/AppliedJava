package com.dig.www.MultiPlayer.State;

import java.io.Serializable;
import java.util.ArrayList;

public class GameState implements Serializable{
	protected String pack;
	protected String level;

protected ArrayList<PlayerState>playerStates=new ArrayList<PlayerState>();
protected ArrayList<EnemyState>enemyStates=new ArrayList<EnemyState>();
protected ArrayList<String>talks=new ArrayList<String>();
protected ArrayList<ActionState>actions=new ArrayList<ActionState>();
public ArrayList<PlayerState>getPlayerStates(){
	return playerStates;
}
public ArrayList<ActionState>getActions(){
	return actions;
}
public void addTalk(String s){
	talks.add(s);
}public GameState(String pack,String level){
	this.pack=pack;
	this.level=level;
}
public String getPack(){
	return pack;
}
public String getLevel(){
	return level;
}
public ArrayList<String>getTalks(){
	return talks;
}
public void clear(){
	playerStates.clear();
	enemyStates.clear();
	talks.clear();
	actions.clear();
}
}

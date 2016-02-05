package com.dig.www.MultiPlayer.State;

import java.io.Serializable;
import java.util.ArrayList;

import com.dig.www.character.GameCharacter;

public class GameState implements Serializable{
	protected String pack;
	protected String level;
	protected boolean server;
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
}public GameState(String pack,String level,boolean server){
	this.pack=pack;
	this.level=level;
	this.server=server;
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
public void clear(String level){
	playerStates.clear();
	enemyStates.clear();
	talks.clear();
	actions.clear();
	this.level=level;
}
public boolean isServer(){
	return server;
}
public ArrayList<EnemyState> getEnemyStates() {
	// TODO Auto-generated method stub
	return enemyStates;
}
}

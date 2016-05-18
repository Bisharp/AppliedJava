package com.dig.www.util;

import java.util.ArrayList;

import com.dig.www.character.GameCharacter;
import com.dig.www.start.Board;

public class PlayerList{
	private boolean special;
	private Board owner;
	private ArrayList<String> stuff=new ArrayList<String>();
public ArrayList<String>getStuff(){
	return stuff;
}
public boolean special(){
	return special;
}
	public PlayerList(String type2,Board owner){
	this.owner=owner;
	switch(type2){
	case "any":
		special=true;
		break;
	case "four":
		stuff.add("shovel");
		stuff.add("club");
		stuff.add("diamond");
		stuff.add("heart");
	break;
	default:
		String[]stuffA=type2.split("\\|");
		if(stuffA[0].equals("four")){
			//stuff=new String[stuff.length+3];
			for(int c=1;c<stuffA.length;c++){
				stuff.add(stuffA[c]);
			}
			stuff.add("shovel");
			stuff.add("club");
			stuff.add("diamond");
			stuff.add("heart");
		}else{
			for(int c=0;c<stuffA.length;c++)
				stuff.add(stuffA[c]);
		}
		
	}
}
public void sort(){
	if(special)
		return;
	ArrayList<GameCharacter> friends=owner.getFriends();
	GameCharacter character=owner.getCharacter();
//	boolean[]has=new boolean[friends.size()+1];
//	for(int c=0;c<has.length;c++)
//		has[c]=false;
	for(int c=0;c<friends.size();c++){
		if(!stuff.contains(friends.get(c).getType().toString())){
			owner.getGoneFriends().add(friends.get(c).getSave());
			friends.remove(c);
			
			c--;
		}
	}
	outer:
	for(int c2=0;c2<stuff.size();c2++){
		for(int c=0;c<friends.size();c++){
		if(friends.get(c).getType().toString().equals(stuff.get(c2))){
			continue outer;
		}
		}
		if(character.getType().toString().equals(stuff.get(c2)))
			continue outer;
		friends.add(owner.getACharacter(stuff.get(c2)));
		owner.heyIaddedAFriendBack(friends.get(friends.size()-1),stuff.get(c2));
	}
	if(!stuff.contains(character.getType().toString())){
		character=friends.get(0);
		friends.remove(0);
	}
	owner.setCharacter(character);
	owner.setFriends(friends);
}
}

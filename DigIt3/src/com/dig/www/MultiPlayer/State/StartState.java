package com.dig.www.MultiPlayer.State;

import java.io.Serializable;
import java.util.ArrayList;

import com.dig.www.MultiPlayer.State.ObjectState.ObjectsTypes;
import com.dig.www.blocks.Block;
import com.dig.www.blocks.TexturePack;
import com.dig.www.character.GameCharacter;
import com.dig.www.objects.MoneyObject;
import com.dig.www.objects.Objects;
import com.dig.www.start.Board;

public class StartState implements Serializable{
	//protected ArrayList<PlayerState>players=new ArrayList<PlayerState>();
	protected ArrayList<BlockState>blocks=new ArrayList<BlockState>();
	protected ArrayList<ObjectState>objects=new ArrayList<ObjectState>();
	protected TexturePack texture;
public StartState(Board owner){
	this.texture=owner.getTexturePack();
	Block first=owner.getWorld().get(0);
	//players.add(new PlayerState(//first.getX()+
	//		owner.getCharacter().getX(), //first.getY()+
	//		owner.getCharacter().getY(), 0,owner.getCharacter().getDirection(), owner.getCharacter().getS(), owner.getCharacter().isPlayer(), owner.getCharacter().getType().toString(),owner.getCharacter().getMpName()));
//for(GameCharacter character:owner.getFriends()){
//	players.add(new PlayerState(//first.getX()+
//			character.getX(),//first.getY()+
//			character.getY(), 0,character.getDirection(), character.getS(), character.isPlayer(),character.getType().toString(),character.getMpName()));
//}
for(Block b:owner.getWorld()){
	blocks.add(new BlockState(//first.getX()+
			b.getX(), //first.getY()+
			b.getY(), b.getType(),b.isVisible()));
}
for(Objects b:owner.getObjects()){
	int i=0;
	ObjectsTypes o=ObjectsTypes.NORMAL;
	if(b instanceof MoneyObject){
		o=ObjectsTypes.MONEY;
		i=((MoneyObject) b).getValue();}
	objects.add(new ObjectState(//first.getX()+
			b.getX(), //first.getY()+
			b.getY(),o, b.getLoc(),b.isWall(),b.getIdentifier(),i));
}
}
public ArrayList<BlockState>getWorld(){
	return blocks;
}
public ArrayList<ObjectState>getObjects(){
	return objects;
}
//public ArrayList<PlayerState>getPlayers(){
//	return players;
//}
public TexturePack getTexture(){
	return texture;
}
}

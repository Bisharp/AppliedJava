package com.dig.www.npc;

import com.dig.www.character.GameCharacter;
import com.dig.www.objects.FactoryBridge;
import com.dig.www.start.Board;

public class FactoryBridgeNPC extends NPC{
private FactoryBridge maker;
	public FactoryBridgeNPC(int x, int y, Board owner,
			FactoryBridge maker) {
		super(x, y, "images/icon.png", owner, new String[]{"We're on the top level of the building. The explosives assembly is beneath us."}, NPC.WIZARD, "United Country", new NPCOption[]{new NPCOption("Is Botanus down there?", "Yes, there is a portal that leads down ahead.", new String[]{"Is Botanus down there?","Is that Botanus guy down there?","...","Oh. Is Botanus down there?"}, false, new NPCOption[]{new NPCOption("Is he stronger than us?", "He's more powerful than any one of you alone, but I'm sure you can defeat him together!", new String[]{"Is he stronger than us?","Is he really stronger than us?","...","Is he stronger than us?"},true, owner)}, owner)});
	this.maker=maker;
	cantExit=true;
	byeChar=NPC.CLUB;
	}
	@Override
		public void act(NPCOption option) {
		options=new NPCOption[0];
		cantExit=false;
		}
@Override
	protected String getGreeting() {
	switch (owner.getCharacter().getType()) {
	case CLUB:
		return "Why the strange hole in the middle of a factory?(You have just activated a checkpoint. Just like entering doors and portals, checkpoints save the game and provide a place for you to spawn)";
	case DIAMOND:
		return "...(You have just activated a checkpoint. Just like entering doors and portals, checkpoints save the game and provide a place for you to spawn)";
	case HEART:
		return "This hole seems strange. Why would they put it in a factory?(You have just activated a checkpoint. Just  like entering doors and portals, checkpoints save the game and provide a place for you to spawn)";
	default:
		return "Whoa! That's quite a drop. Why is it in this factory?(You have just activated a checkpoint. Just like     entering doors and portals, checkpoints save the game and provide a place for you to spawn)";
	}
	}@Override
	public String exitLine() {
		// TODO Auto-generated method stub
		return "Oh... Um... Uh... Let's show him then! There is another Key Crystal ahead. This special one is worth    three regular ones. There are areas of the hole that you can fill up to get to it.";
	}
@Override
	protected String getFarewell() {
		return "More powerful?... +More powerful?|... *Smash! Smash! Smash!|";
	}
	@Override
	public String getShowName() {
		// TODO Auto-generated method stub
		return "The Wizard";
	}
@Override
public void startEnd() {
	maker.breakIt();
if(GameCharacter.storyInt==6)
	GameCharacter.storyInt++;
}
}
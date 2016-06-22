package com.dig.www.npc;

import java.awt.Rectangle;

import com.dig.www.start.Board;
import com.dig.www.util.OnlyFirstTimeEnteringMap;

public class FactoryEntryCutScene extends CutScene implements OnlyFirstTimeEnteringMap{
private boolean gone;
	public FactoryEntryCutScene(int x, int y, Board owner, String location) {
		super(x, y, owner, location, new CSDialog[]{new CSDialog("Hey! That was much better than the last time we used a portal.", NPC.SPADE, null),new CSDialog("Oh look, Another dump. Who would've guessed?", NPC.CLUB, null),new CSDialog("Ooh! It looks like we're in a DND Fireworks factory!", NPC.HEART, null),new CSDialog("Or at least what used to be one.", NPC.CLUB, null),new CSDialog("Only Botanus could have done this.", NPC.SPADE, null),new CSDialog("...", NPC.DIAMOND, null),new CSDialog("I have an idea. Since we don't Botanus getting any explosives, we could remove some fireworks from hereand say... launch them from the roof.", NPC.CLUB, null),new CSDialog("Um, no. Let's just try to find Botanus and stop him.", NPC.SPADE, null)});
		// TODO Auto-generated constructor stub
	}
@Override
public Rectangle getBounds() {
	if(!gone){
		gone=true;
		return new Rectangle();
	}
	return super.getBounds();
}
}

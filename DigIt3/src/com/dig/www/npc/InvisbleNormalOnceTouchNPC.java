package com.dig.www.npc;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import com.dig.www.start.Board;
import com.dig.www.util.Preferences;
import com.dig.www.util.Statics;

public abstract class InvisbleNormalOnceTouchNPC extends NPC implements TouchNPC{
	protected boolean hasTalked;
	protected boolean cantExit;
	protected String[] chars;
public InvisbleNormalOnceTouchNPC(int x, int y, Board owner,
			String[] dialogs, String s, String location, NPCOption[] options,
			String hiChar, String byeI, String byeChar,boolean cantExit,String[]chars) {
		super(x, y,Statics.DUMMY, owner, dialogs, s, location, options, hiChar, byeI, byeChar,
				false);
		this.cantExit=cantExit;
		this.chars=chars;
		// TODO Auto-generated constructor stub
	}


@Override
public String getShowName() {
	// TODO Auto-generated method stub
	return "";
}
@Override
public boolean buttonTalk() {
	// TODO Auto-generated method stub
	return false;
}

public boolean isChars(){
	String chara=owner.getCharacter().getType().toString();
	for(String s:chars){
		if(s.equals(chara))
			return true;
	}
	return false;
}

@Override
public boolean willTalk(){
	if(isChars()){
		return false;
	}else{
	boolean willTalk=!hasTalked;
	if(!hasTalked)
	for(NPC npc:owner.getNPCs()){
		if(this.getClass().toString().equals(npc.getClass().toString())){
			((InvisbleNormalOnceTouchNPC)npc).trueHasTalked();
		}
	}
	
	return willTalk;}
}
@Override
public void draw(Graphics2D g){
	super.draw(g);
	
}
@Override
protected String append() {
	if(cantExit&&!inDialogue)
		return "";
		else
	return super.append();
}
public void exitImmediately() {

	if (!iTalk) {
		super.exit();
	} else {
		end();
	}
}
@Override
public void exit() {
if(cantExit)
		wait=false;
		else super.exit();
}
public void trueHasTalked(){
	hasTalked=true;
}
}
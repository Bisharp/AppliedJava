package com.dig.www.enemies;

import com.dig.www.character.GameCharacter;
import com.dig.www.character.Moves;
import com.dig.www.start.Board;
import com.dig.www.util.ListenerOfAnAction;

public class DigSenseEnemy extends StandEnemy{
private boolean onceDug;
private boolean doneOnce;
private ListenerOfAnAction listener;
	public DigSenseEnemy(int x, int y, Board owner,boolean once,ListenerOfAnAction listener) {
		super(x, y,null, owner, true, -10);
		// TODO Auto-generated constructor stub
		this.onceDug=once;
		this.listener=listener;
		damage=0;
	}
	public DigSenseEnemy(int x, int y, Board owner,boolean once,ListenerOfAnAction listener,boolean test) {
		super(x, y,"images/icon.png", owner, true, -10);
		// TODO Auto-generated constructor stub
		this.onceDug=once;
		this.listener=listener;
		damage=0;
	}
@Override
	public void interact(Moves move, GameCharacter character, boolean fromP) {
		if(move==Moves.PIT)
if(!doneOnce||!onceDug){
doneOnce=true;

			listener.actionDone();
	}}
}

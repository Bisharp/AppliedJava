package com.dig.www.objects;

import com.dig.www.character.GameCharacter;
import com.dig.www.start.Board;
import com.dig.www.util.ConditionEnteringMap;

public class InvisibleWallObject extends Objects{
private int lessThan;
	public InvisibleWallObject(int x, int y,String bounds,String lessThan, Board owner) {
		super(x, y, null, true, owner, "update");
		String[]b=bounds.split("\\|");
		width=Integer.parseInt(b[0]);
		height=Integer.parseInt(b[1]);
		this.lessThan=Integer.parseInt(lessThan);
	}
	@Override
		public void animate() {
			// TODO Auto-generated method stub
			super.animate();
			wall=GameCharacter.storyInt<lessThan;
		}
@Override
public boolean interact() {
	// TODO Auto-generated method stub
	return false;
}
}

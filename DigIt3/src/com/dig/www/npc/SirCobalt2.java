package com.dig.www.npc;

import java.awt.Rectangle;

import com.dig.www.character.SirCobalt;
import com.dig.www.start.Board;
import com.dig.www.util.Statics;

public class SirCobalt2 extends ServiceNPC{
private boolean isThere=true;
	public SirCobalt2(int x, int y, String loc, Board owner) {
		super(x, y, loc, owner, new String[]{"There is danger ahead. Do you want me join you? (Enter ==> Yes)"}, SIR_COBALT, "You cloned me!");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void service() {
		// TODO Auto-generated method stub
		owner.getFriends().add(new SirCobalt(Statics.BOARD_WIDTH / 2, Statics.BOARD_HEIGHT / 2, owner, false));
isThere =false;
	}
	@Override
	public Rectangle getBounds() {
		if (isThere)
			return super.getBounds();
		else
			return new Rectangle();
	}
}

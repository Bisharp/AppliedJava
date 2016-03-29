package com.dig.www.npc;

import java.awt.Color;
import java.awt.Point;

import com.dig.www.enemies.PoisonOrFlame;
import com.dig.www.enemies.RyoBoss2;
import com.dig.www.start.Board;

public class RyoBoss2Start extends CutScene {

	public RyoBoss2Start(int x, int y, Board owner, String location) {
		super(x, y, owner, location,
				new CSDialog[] { new CSDialog("", "black", new CutSceneImage("images/npcs/cutScenes/RyoBoss2/out.gif")),
						new CSDialog("Whew, it's hot! We should've brought sunscreen.", NPC.SPADE,
								new CutSceneImage("images/npcs/cutScenes/RyoBoss2/hot.gif")),
				new CSDialog("It's about to get hotter... Much hotter.", NPC.REYZU,
						new CutSceneImage("images/npcs/cutScenes/RyoBoss2/ryo1.png")),
				new CSDialog("Oh no, not this guy.", NPC.SPADE,
						new CutSceneImage("images/npcs/cutScenes/RyoBoss2/ohNo.png")),
				new CSDialog("Let's get him.", NPC.CLUB, new CutSceneImage("images/npcs/cutScenes/RyoBoss2/ryo2.gif")),
				new CSDialog("Ye are c'rtainly outmatch'd.", NPC.KYSERYX, new CutSceneImage("images/npcs/cutScenes/RyoBoss2/ryoShot.gif")),
				new CSDialog("Whoa! Why can't we talk this through?!", NPC.SPADE, new CutSceneImage("images/npcs/cutScenes/RyoBoss2/jump.gif"))});
		// TODO Auto-generated constructor stub
	}

	public void act(NPCOption option) {
		if (c == cutScenes.length) {
			owner.getEnemies()
					.add(new PoisonOrFlame(owner.getCharacter().getX(), owner.getCharacter().getY(), false, owner,true));
			Point[] ps = new Point[owner.getFriends().size() + 1];
			ps[0] = owner.getCharPoint();
			for (int c = 0; c < owner.getFriends().size(); c++)
				ps[c + 1] = new Point(owner.getFriends().get(c).getX(), owner.getFriends().get(c).getY());
			owner.scroll(0, 200);
			owner.getCharacter().setX(ps[0].x);
			owner.getCharacter().setY(ps[0].y);
			for (int c = 0; c < owner.getFriends().size(); c++) {
				owner.getFriends().get(c).setX(ps[c + 1].x);
				owner.getFriends().get(c).setY(ps[c + 1].y);
			}
			for(int c=0;c<owner.getEnemies().size();c++)
				if(owner.getEnemies().get(c) instanceof RyoBoss2)
					((RyoBoss2) owner.getEnemies().get(c)).activate();
		}
	}

}

package com.dig.www.npc;

import java.awt.Rectangle;

import com.dig.www.enemies.VineBoss;
import com.dig.www.start.Board;
import com.dig.www.util.OnlyFirstTimeEnteringMap;

public class FirstTimeEnterBotanus extends CutScene implements OnlyFirstTimeEnteringMap{
private int waited=2;
	public FirstTimeEnterBotanus(int x, int y, Board owner, String location) {
		super(x, y, owner, location, new CSDialog[]{new CSDialog("Here comes the lawn mower of justice, you weed!", NPC.CLUB, null)
				,new CSDialog("That's what we're going with? *sigh|",NPC.SPADE , null),new CSDialog("That was the worst one-liner I've ever heard.","botanus", null),new CSDialog("Hey, that was rude! He's been working on that all day!", NPC.HEART, null),new CSDialog("Did you really think I would be as easy a challenge as my subordinates?", "botanus", null),new CSDialog("That's what we were all hoping.", NPC.SPADE, null),new CSDialog("Not all of us. Those guys were terrible.",NPC.CLUB , null),new CSDialog("You four are either very brave or very foolish. Perhaps both.","botanus" , null),new CSDialog("I like to think that I'm not either of those things.",NPC.SPADE , null)});
	}
@Override
public void act(NPCOption npcOption) {
	if(c>=cutScenes.length)
		for(int c=0;c<owner.getEnemies().size();c++)
			if(owner.getEnemies().get(c) instanceof VineBoss)
				((VineBoss)owner.getEnemies().get(c)).activate();
}

@Override
	public Rectangle getBounds() {
		if(waited<=0)
		return super.getBounds();
		waited--;
return new Rectangle();	
}
}

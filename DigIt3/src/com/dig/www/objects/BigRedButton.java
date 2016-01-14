package com.dig.www.objects;

import java.awt.Graphics2D;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.dig.www.enemies.BigExplosion;
import com.dig.www.enemies.Explosion;
import com.dig.www.enemies.Launch;
import com.dig.www.enemies.TrackingEnemy;
import com.dig.www.npc.CopyOfMacaroni;
import com.dig.www.npc.NPC;
import com.dig.www.start.Board;
import com.dig.www.start.DigIt;

public class BigRedButton extends Objects {
	protected CopyOfMacaroni macaroni;
protected boolean exploded;
	public BigRedButton(int x, int y, Board owner) {
		super(x, y, "images/objects/BigRedButton.png", false, owner, "update");
		// TODO Auto-generated constructor stub
		for(NPC npc:owner.getNPCs())
			if(npc instanceof CopyOfMacaroni){
			macaroni=(CopyOfMacaroni) npc;
			break;
			}
	}
@Override
public void draw(Graphics2D g2d) {
	// TODO Auto-generated method stub
	if(!exploded)
	super.draw(g2d);
}
	@Override
	public boolean interact() {
		// TODO Auto-generated method stub
		if(!exploded){
		boolean b = false;
		boolean macaroniAcc = macaroni.getAccepted()&&macaroni.getPhase()==1;
		String[] options;
		String desc;
		if (macaroniAcc) {
			options = new String[] { "Leave", "Press" };
			desc = "This seems to be the button Super Macaroni Noodle Man asked us to press.";
		} else {
			options = new String[] { "Leave" };
			desc = "A big red button; We should probably just leave it alone.";
		}
		b=JOptionPane.showOptionDialog(owner, desc, DigIt.NAME
				+ " Item Description", JOptionPane.DEFAULT_OPTION,
				JOptionPane.INFORMATION_MESSAGE, new ImageIcon(image), options,
				"Leave")==1;
		if(b){
			if(macaroni!=null)
			macaroni.setPhase(2);
			exploded=true;
			shadow=new ImageIcon().getImage();
			owner.getEnemies().add(new BigExplosion(x-75, y-75, owner,Integer.MAX_VALUE));
		}
			
		return b;}
		else
			return false;
	}
}

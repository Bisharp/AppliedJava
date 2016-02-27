package com.dig.www.objects;

import java.awt.Graphics2D;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.dig.www.start.Board;
import com.dig.www.start.DigIt;
import com.dig.www.util.ActsOnFrameOne;
import com.dig.www.util.Statics;

public class Lamp extends Objects implements ActsOnFrameOne {

	protected LightSpot light;
	protected int size;

	public Lamp(int x, int y, String loc, Board owner, int size) {
		super(x, y, loc, true, owner, "");
		this.size = size;
	}

	@Override
	public boolean interact() {

		String desc = "A lamp; it appears to be in working order.";
		String[] options = new String[] { "Leave", "Turn O" + (light.isActive() ? "ff" : "n") };
		boolean b = JOptionPane.showOptionDialog(owner, desc, DigIt.NAME + " Item Description", JOptionPane.DEFAULT_OPTION,
				JOptionPane.INFORMATION_MESSAGE, new ImageIcon(image), options, "Leave") == 1;
		if (b)
			light.switchLight();
		
		return true;
	}
	
	public void animate() {
		super.animate();
		
		if (frameOne)
			actFrameOne();
	}

	protected boolean frameOne = true;
	@Override
	public void actFrameOne() {
		
		frameOne = false;
		light = new LightSpot(x - (size * 100) / 2 + width / 2, y - (size * 100) / 2 + height / 2, Statics.DUMMY, owner, size);
		owner.getMovingObjects().add(light);
		owner.getObjects().add(light);
	}

	@Override
	public boolean isFrameOne() {
		return frameOne;
	}
}

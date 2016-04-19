package com.dig.www.objects;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.dig.www.character.Items;
import com.dig.www.start.Board;
import com.dig.www.start.DigIt;
import com.dig.www.util.Sprite;

public class Objects extends Sprite {
	protected boolean wall;
	protected String desc;
	protected String identifier;

	public Objects(int x, int y, String loc, boolean wall, Board owner, String identifier) {
		super(x, y, loc, owner);
		// TODO Auto-generated constructor stub
		this.setWall(wall);
		this.desc = getDescription(loc, identifier);
		this.identifier = identifier;
	}

	@Override
	public void animate() {
		// TODO Auto-generated method stub
		basicAnimate();
	}

	@Override
	public void draw(Graphics2D g2d) {
		g2d.drawImage(image, x, y, owner);
		drawShadow(g2d);
	}

	public boolean isWall() {
		return wall;
	}

	public void setWall(boolean wall) {
		this.wall = wall;
	}

	public void collidePlayer(int playerNum) {
		if (loc != null && loc.equals("images/objects/Leaves.png")) {
			loc = "images/objects/LeavesSc.png";
			image = newImage(loc);
			shadow = newShadow(loc);
			x -= 15;
			y -= 15;
			if (desc != null)
				switch (desc) {
				case "A pile of leaves. Did someone rake them or did they fall that way?":
					desc = "A now-scattered pile of leaves. I hope we didn't mess anything up.";
					break;
				case "How did these leaves get here? There isn't a tree in sight.":
					desc = "A now-scattered pile of leaves. Maybe they will blow away.";
					break;
				}
		}
		if (playerNum == -1) {
			if (wall)
				owner.getCharacter().collision(this, false);

		} else {
			if (wall)
				owner.getFriends().get(playerNum).collision(this, false);

		}
	}

	public void collideWall() {
	}

	public boolean interact() {
		JOptionPane.showMessageDialog(owner, desc, DigIt.NAME + " Item Description", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(image));
		return true;
	}

	public String getDescription(String loc, String identifier) {

		// Find by identifier
		if (identifier != null)
			switch (identifier) {
			case "update":
				return null;// This is used for objects that update the interact
							// method so that it wont go through the whole loop
			case "CubeButton":
				return "This looks like a button, but it doesn't do anything when stepped on, even with all of us together.";
			case "pushCube":
				return "Push cube: a pushable cube. It can activate certain buttons if it is on top of them.";
			case "bombCube":
				return "It says not to touch it, perhaps we should listen.";
			case "BossBlock":
				return "This strange portal-like wall bars the way.";
			case "HookObject":
				return "This object can be latched onto using Cain's chain attack to pull him across obstacles.";
			case "Dispenser":
				return "A dispenser placed using Destiny's wand given to her by the wizard.\n It blocks projectiles, heals friendlies, and slows enemies.";
			case "LeavesDefault":
				return "A pile of leaves. Did someone rake them or did they fall that way?";
			case "LeavesScDefault":
				return "A scattered pile of leaves.";// Note that with leaves
														// they change in the
				// collideWall() method. This is only for leaves that spawn
				// scattered.
			case "LeavesSnow":
				return "These leaves must have fallen recently, otherwise they would be covered in snow.";

			case "LeavesDesert":
				return "How did these leaves get here? There isn't a tree in sight.";
			case "RIP":
				return"This tombstone looks old. The lower text is unreadable.";
			case "Explosives":
				return "A box of explosives.\n We should probably leave them alone.";
			case "ExplosivesVine":
				return "A box of explosives.\n Why are we looking at these right now and why are they even here?";
			}
		// Find by loc
		if (loc == null)
			return null;
		switch (loc) {

		}
		// Find by special exceptions
		if (loc.startsWith("images/objects/table/")) {
			String ending = loc.split("images/objects/table/")[1];
			switch (ending) {
			case "table.png":
				return "A table with nothing on it.";
			case "tableDrinkAndTaco.png":
				return "Someone must have forgotten their lunch.";
			case "tableGameAndVile.png":
				return "Who left this mess here?";
			case "tableGogglesAndVile.png":
				return "How many pairs of goggles does Dr. Kepler have?";
			case "tableVileAndMicroscope.png":
				return "Nothing wrong here... Wait! Carl! Stop!";
			default:
				return "A table.";
			}
		}
		// Did not find anything. This still works fine
		return null;
	}
	public String getLoc(){
		return loc;
	}
	public String getIdentifier(){
		return identifier;
	}
}

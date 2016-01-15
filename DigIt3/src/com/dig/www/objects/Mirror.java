package com.dig.www.objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.dig.www.blocks.Block;
import com.dig.www.npc.NPC;
import com.dig.www.start.Board;
import com.dig.www.start.DigIt;
import com.dig.www.util.Statics;

public class Mirror extends PushCube {
	protected String d = "r";
	protected boolean hasSwitched;
	boolean a = false;
	protected Rectangle lightRect;
	protected boolean pushAble;
	protected boolean lightOn;

	public Mirror(int x, int y, Board owner, boolean pushAble) {
		super(x, y, "images/objects/mirror/" + "r.png", owner, "update");
		// TODO Auto-generated constructor stub
		loc = "images/objects/mirror/";
		this.pushAble = pushAble;
	}

	@Override
	public Image newImage(String loc) {
		// TODO Auto-generated method stub
		if (loc.contains("/"))
			return super.newImage(loc);
		else
			return super.newImage(this.loc + loc);
	}

	@Override
	public Image newShadow(String loc) {
		// TODO Auto-generated method stub
		if (loc.contains("/"))
			return super.newShadow(loc);
		else
			return super.newShadow(this.loc + loc);
	}

	@Override
	public void collidePlayer(int playerNum) {
		// TODO Auto-generated method stub
		if (!pushAble) {
			if (playerNum == -1) {
				if (wall)
					owner.getCharacter().collision(this, false);
			} else {
				if (wall)
					owner.getFriends().get(playerNum).collision(this, false);
			}
			willIsWall = true;
		} else {
			int a = x;
			int b = y;
			super.collidePlayer(playerNum);
			if (a != x || b != y) {
				for (Objects o : owner.getObjects()) {
					if (o instanceof Mirror)
						((Mirror) o).notHasSwitched();
				}
			}
		}
	}

	@Override
	public void basicAnimate() {
		// TODO Auto-generated method stub
		super.basicAnimate();
		if (lightRect != null) {
			lightRect.x += owner.getScrollX();
			lightRect.y += owner.getScrollY();
		}
	}

	public boolean interact() {
		String[] options = { "Leave", "Pull", "Spin" };
		int b = JOptionPane.showOptionDialog(owner, desc, DigIt.NAME + " Item Description", JOptionPane.DEFAULT_OPTION,
				JOptionPane.INFORMATION_MESSAGE, new ImageIcon(image), options, "Leave");
		if (b == 1) {
			int tx = owner.getCharacterX();
			int ty = owner.getCharacterY();
			int willX = x;
			int willY = y;
			boolean moveX = Math.abs((tx - x)) > 30;
			boolean moveY = Math.abs((ty - y)) > 30;
			owner.scroll(moveX ? (-(tx - x)) : 0, moveY ? (-(ty - y)) : 0);
			owner.getCharacter().setX(tx);
			owner.getCharacter().setY(ty);
			x = willX;
			y = willY;
			for (Block bl : owner.getWorld()) {
				if (!bl.traversable() && (owner.getCharacter().getCollisionBounds().intersects(bl.getBounds()))) {
					owner.scroll(moveX ? ((tx - x)) : 0, moveY ? ((ty - y)) : 0);
					owner.getCharacter().setX(tx);
					owner.getCharacter().setY(ty);
					x = willX;
					y = willY;
				}
			}
		} else if (b == 2) {
			hasSwitched = false;
			switch (d) {
			case "r":
				d = "u";
				break;
			case "u":
				d = "l";
				break;
			case "l":
				d = "d";
				break;
			case "d":
				d = "r";
				break;
			default:
				d = "r";
			}
		}
		return b == 1;
	}

	@Override
	public void draw(Graphics2D g2d) {
		drawLight(g2d);
		super.draw(g2d);

		// hasSwitched=false;
	}

	public void drawLight(Graphics2D g2d) {
		// TODO Auto-generated method stub

		if (!hasSwitched) {
			image = newImage(d + ".png");
			shadow = newShadow(d + ".png");
			hasSwitched = true;
			// ArrayList<Block> walls = owner.getWallList();
			ArrayList<NPC> npcs = owner.getNPCs();
			ArrayList<Objects> objects = owner.getObjects();
			switch (d) {
			case "r":
				lightRect = new Rectangle(x + 24, y + 10, 5000, 51);
				// for (int i = 0; i < walls.size(); i++) {
				// if (walls.get(i).getBounds().intersects(lightRect)) {
				// lightRect.width = walls.get(i).getX() - lightRect.x;
				// break;
				// }
				// }
				for (int i = 0; i < npcs.size(); i++) {
					if (npcs.get(i).isObstacle() && npcs.get(i).getBounds().intersects(lightRect)) {
						lightRect.width = npcs.get(i).getBounds().x - lightRect.x;

					}
				}
				for (int i = 0; i < objects.size(); i++) {
					if (!(objects.get(i) == this) && (objects.get(i).isWall() || objects.get(i) instanceof Mirror)
							&& objects.get(i).getBounds().intersects(lightRect)) {
						lightRect.width = objects.get(i).getBounds().x - lightRect.x + 1;

					}
				}
				break;
			case "d":
				lightRect = new Rectangle(x + 10, y + 10, 55, 5000);
				// for (int i = 0; i < walls.size(); i++) {
				// if (walls.get(i).getBounds().intersects(lightRect)) {
				// lightRect.height = walls.get(i).getY() - lightRect.y;
				// break;
				// }
				// }
				for (int i = 0; i < npcs.size(); i++) {
					if (npcs.get(i).isObstacle() && npcs.get(i).getBounds().intersects(lightRect)) {
						lightRect.height = npcs.get(i).getBounds().y - lightRect.y;

					}
				}
				for (int i = 0; i < objects.size(); i++) {
					if (!(objects.get(i) == this) && (objects.get(i).isWall() || objects.get(i) instanceof Mirror)
							&& objects.get(i).getBounds().intersects(lightRect)) {
						lightRect.height = objects.get(i).getY() - lightRect.y + 1;

					}
				}
				break;
			case "l":
				lightRect = new Rectangle(x + 51 - 5000, y + 10, 5000, 51);
				// for (int i = walls.size()-1; i >= 0; i--) {
				// if (walls.get(i).getBounds().intersects(lightRect)) {
				// lightRect.x =walls.get(i).getX()+Statics.BLOCK_HEIGHT;
				// lightRect.width=x-(lightRect.x);
				// break;
				// }
				// }
				for (int i = npcs.size() - 1; i >= 0; i--) {
					if (npcs.get(i).isObstacle() && npcs.get(i).getBounds().intersects(lightRect)) {
						lightRect.x = (int) npcs.get(i).getBounds().getMaxX();
						lightRect.width = x - (lightRect.x);

					}
				}

				for (int i = objects.size() - 1; i >= 0; i--) {
					if (!(objects.get(i) == this) && (objects.get(i).isWall() || objects.get(i) instanceof Mirror)
							&& objects.get(i).getBounds().intersects(lightRect)) {
						lightRect.x = (int) objects.get(i).getBounds().getMaxX() - 1;
						lightRect.width = x - (lightRect.x) + 1;

					}
				}
				break;
			case "u":
				lightRect = new Rectangle(x + 10, y + 10 - 5000, 55, 5000);
				// for (int i = walls.size()-1; i >= 0; i--) {
				// if (walls.get(i).getBounds().intersects(lightRect)) {
				// lightRect.y =walls.get(i).getY()+Statics.BLOCK_HEIGHT;
				// lightRect.height=y-(lightRect.y);
				// break;
				// }
				// }
				for (int i = npcs.size() - 1; i >= 0; i--) {
					if (npcs.get(i).isObstacle() && npcs.get(i).getBounds().intersects(lightRect)) {
						lightRect.y = (int) npcs.get(i).getBounds().getMaxY();
						lightRect.height = y - (lightRect.y);

					}
				}

				for (int i = objects.size() - 1; i >= 0; i--) {
					if (!(objects.get(i) == this) && (objects.get(i).isWall() || objects.get(i) instanceof Mirror)
							&& objects.get(i).getBounds().intersects(lightRect)) {
						lightRect.y = (int) objects.get(i).getBounds().getMaxY() - 1;
						lightRect.height = y - (lightRect.y) + 1;

					}
				}
				break;
			default:
				lightRect = new Rectangle(x, y, 100, 100);
			}

		}
		lightOn = lightOn();
		if (lightRect != null && lightOn) {
			g2d.setColor(new Color(225, 225, 255, 100));

			g2d.fill(lightRect);
		}
	}

	public Rectangle getLightRect() {
		if (lightOn)
			return lightRect;
		else
			return new Rectangle();
	}

	protected boolean lightOn() {
		for (Objects o : owner.getObjects()) {
			if (o == this)
				continue;
			if (o instanceof Mirror) {
				if (((Mirror) o).getLightRect().intersects(getBounds())) {
					((Mirror) o).notHasSwitched();
					return true;
				}
			}

		}
		return false;
	}

	@Override
	public void setX(int newX) {
		// TODO Auto-generated method stub
		super.setX(newX);
		hasSwitched = false;
	}

	@Override
	public void setY(int newY) {
		// TODO Auto-generated method stub
		super.setY(newY);
		hasSwitched = false;
	}

	public void notHasSwitched() {
		hasSwitched = false;
	}
}

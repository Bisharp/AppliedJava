package com.dig.www.character;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

import com.dig.www.character.GameCharacter.Direction;
import com.dig.www.start.Board;
import com.dig.www.util.Statics;

public class Ryo extends GameCharacter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Ryo(int x, int y, Board owner, boolean player) {
		super(x, y, owner, Types.RYO, "ryo", player, -20, -40, -50, 30, 35, 10, 100, 10, 100, 15, 25, 70, 20, 10, 50, Statics.STRENGTH + 5);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void drawCSHUD(Graphics2D g2d) {
		// TODO Auto-generated method stub
		// g2d.draw(getActBounds());
	}

	public int rangedAddX() {
		return 38;
	}
@Override
public void basicAnimate() {
	// TODO Auto-generated method stub
	super.basicAnimate();
if(specialTimer>0){
	energy--;
}
}
	@Override
	public void drawTool(Graphics2D g2d) {

		int dX = 0;
		int dY = 0;
		if(getActing()==1){
		switch (direction) {
		case UP:
			dX = x+40;
			dY = y - Statics.BLOCK_HEIGHT;
			break;

		case DOWN:
			dX = x+30;
			dY = y + Statics.BLOCK_HEIGHT - 25;
			break;

		case RIGHT:
		case DIAG_UR:
		case DIAG_DR:
			dX = x + Statics.BLOCK_HEIGHT - 50;
			dY = y+30;
			break;

		case LEFT:
		case DIAG_UL:
		case DIAG_DL:
			dX = x + 50;
			dY = y+30;
			break;
		}}else if(getActing()==2){
			switch (direction) {
			case UP:
				dX = x+10;
				dY = y - Statics.BLOCK_HEIGHT + 25;
				break;

			case DOWN:
				dX = x+10;
				dY = y + Statics.BLOCK_HEIGHT - 25;
				break;

			case RIGHT:
			case DIAG_UR:
			case DIAG_DR:
				dX = x + Statics.BLOCK_HEIGHT - 50;
				dY = y;
				break;

			case LEFT:
			case DIAG_UL:
			case DIAG_DL:
				dX = x + 50;
				dY = y;
				break;
			}
		}
		else{
			switch (direction) {
			case UP:
				dX = x+10;
				dY = y - 204+38;
				break;

			case DOWN:
				dX = x+10;
				dY = y + Statics.BLOCK_HEIGHT - 25;
				break;

			case RIGHT:
			case DIAG_UR:
			case DIAG_DR:
				dX = x + Statics.BLOCK_HEIGHT - 50;
				dY = y;
				break;

			case LEFT:
			case DIAG_UL:
			case DIAG_DL:
				dX = x + 50;
				dY = y;
				break;
			}
		}

		if (toMoveString() != null) {
			if (direction == Direction.LEFT||direction == Direction.DIAG_UL||direction == Direction.DIAG_DL) {
				Image anImg = newImage(toMoveString());
				g2d.drawImage(anImg, dX, dY, -anImg.getWidth(owner), anImg.getHeight(owner), owner);
				if (owner.darkenWorld()&&getActing()!=3) {
					anImg = newShadow(toMoveString());
					g2d.drawImage(anImg, dX, dY, -anImg.getWidth(owner), anImg.getHeight(owner), owner);
				}
			} else {
				g2d.drawImage(newImage(toMoveString()), dX, dY, owner);
				if (owner.darkenWorld()&&getActing()!=3)
					g2d.drawImage(newShadow(toMoveString()), dX, dY, owner);
			}
		}

	}

	@Override
	public boolean canAct() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void getsActor() {
		// TODO Auto-generated method stub

	}

	@Override
	public Rectangle getActBounds() {
if(getActing()==1){
	switch (direction) {
	case UP:
		return new Rectangle(x +40, y-100, 28, 138);
	case DOWN:
		return new Rectangle(x + 30, y + Statics.BLOCK_HEIGHT - 25, 28, 138);
	case LEFT:
	case DIAG_DL:
	case DIAG_UL:
		return new Rectangle(x -88, y + 30, 138, 28);
		
		case RIGHT:
		case DIAG_DR:
		case DIAG_UR:
			default:
		return new Rectangle(x + Statics.BLOCK_HEIGHT - 50, y + 30, 138, 28);
	}
}
		switch (direction) {
		case UP:
			return new Rectangle(x + 10, y -204+38, 80, 204);
		case DOWN:
			return new Rectangle(x + 10, y +75, 80, 204);
		case RIGHT:
		case DIAG_DR:
		case DIAG_UR:
			return new Rectangle(x + 50, y, 204, 80);
		case LEFT:
		case DIAG_DL:
		case DIAG_UL:
		default:
			return new Rectangle(x +50-204, y, 204, 80);
		}
	}

	@Override
	public Moves getMove() {
		// TODO Auto-generated method stub
		switch (getActing()) {
		case 1:
			return Moves.KYSERYX;
		case 2:
			return Moves.FIREBALL;
		case 3:
			return Moves.FLAME;
		default:
			return Moves.NONE;
		}
	}

	@Override
	public String toMoveString() {
		switch (getActing()) {
		case 1:
			return "k";
case 2:
	return "kProjectile";
		case 3:
			return "kSpecial";
		default:
		
			return null;
		}
	}

	@Override
	public Moves getRangedMove() {
		// TODO Auto-generated method stub
		return Moves.FIREBALL;
	}

	@Override
	public String getRangedString() {
		return "baseball.png";
	}
	@Override
	public Image newImage(String name) {
		// TODO Auto-generated method stub
		if(name.equals("kSpecial")){
			
			//	shadow = newShadow("kProjectile");
			
			return super.newImage(getPath() + name + ".gif");
		}else
		return super.newImage(name);
	}
	@Override
	public Image newShadow(String name) {
		// TODO Auto-generated method stub
		if(name.equals("kSpecial"))
			return new ImageIcon().getImage();
		return super.newShadow(name);
	}
}

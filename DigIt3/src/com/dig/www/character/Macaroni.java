package com.dig.www.character;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.ObjectInputStream.GetField;

import com.dig.www.character.GameCharacter.Direction;
import com.dig.www.start.Board;
import com.dig.www.util.Statics;

public class Macaroni extends GameCharacter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected boolean keyReleased;
	public Macaroni(int x, int y, Board owner, boolean player) {
		super(x, y, owner, Types.MACARONI, "macaroni", player, -20, -25, -50, 360, 55, 10, 80, 10, 100, 10, 20, 50, 10, 30, 0, Statics.STRENGTH - 1);
		// TODO Auto-generated constructor stub
	}

	

	

	

	@Override
	public Moves getMove() {
		// TODO Auto-generated method stub
		switch (getActing()) {
		case 1:
			return Moves.SPADE;
		case 2:
			return Moves.ARROW;
		case 3:
			return Moves.PIT;
		default:
			return Moves.NONE;
		}
	}

	@Override
	public String toMoveString() {
		switch (getActing()) {
		case 1:
			return null;
		case 2:
			return null;
		case 3:
			return getType().toString();
		default:

			return null;
		}
	}
@Override
public void animate() {
	super.animate();
	if(rangedTimer>=0){
		image=newImage("s");
		stop();
	}else if(meleeTimer>=0){
		stop();
		image=newImage("n");
		direction=Direction.RIGHT;
//		if(meleeTimer>=TIMER_MELEE-1)
//		direction=Direction.LEFT;
//		if(meleeTimer==TIMER_MELEE/2||meleeTimer==TIMER_MELEE/2-1)
//			direction=Direction.RIGHT;
		
	}
};
	@Override
	public void drawTool(Graphics2D g2d) {

		int dX = 0;
		int dY = 0;
		if(meleeTimer>=0){
			if(meleeTimer>TIMER_MELEE/2){
				switch (direction) {
				case UP:
					dX = x + 13;
					dY = y - Statics.BLOCK_HEIGHT + 50 + 13;
					break;

				case DOWN:
					dX = x + 12;
					dY = y + Statics.BLOCK_HEIGHT - 50;
					break;

				case RIGHT:
				case DIAG_UR:
				case DIAG_DR:
					dX = x + Statics.BLOCK_HEIGHT - 70;
					dY = y + 13;
					break;

				case LEFT:
				case DIAG_UL:
				case DIAG_DL:
					dX = x + 70;
					dY = y + 12;
					break;
				}
			}
			else{
				switch (direction) {
				case UP:
					dX = x + 13;
					dY = y - Statics.BLOCK_HEIGHT + 50 + 13;
					break;

				case DOWN:
					dX = x + 12;
					dY = y + Statics.BLOCK_HEIGHT - 50;
					break;

				case RIGHT:
				case DIAG_UR:
				case DIAG_DR:
					dX = x + Statics.BLOCK_HEIGHT - 70;
					dY = y + 13;
					break;

				case LEFT:
				case DIAG_UL:
				case DIAG_DL:
					dX = x + 70;
					dY = y + 12;
					break;
				}
			}
		}else
			switch (direction) {
			case UP:
				dX = x + 13;
				dY = y - Statics.BLOCK_HEIGHT + 50 + 13;
				break;

			case DOWN:
				dX = x + 12;
				dY = y + Statics.BLOCK_HEIGHT - 50;
				break;

			case RIGHT:
			case DIAG_UR:
			case DIAG_DR:
				dX = x + Statics.BLOCK_HEIGHT - 70;
				dY = y + 13;
				break;

			case LEFT:
			case DIAG_UL:
			case DIAG_DL:
				dX = x + 70;
				dY = y + 12;
				break;
			}
		if (toMoveString() != null) {

			if (direction == Direction.LEFT||direction == Direction.DIAG_UL||direction == Direction.DIAG_DL) {
				Image anImg = newImage(toMoveString());
				g2d.drawImage(anImg, dX, dY, -anImg.getWidth(owner), anImg.getHeight(owner), owner);
				if (owner.darkenWorld())
					g2d.drawImage(newShadow(toMoveString()), dX, dY, -anImg.getWidth(owner), anImg.getHeight(owner), owner);
			} else {
				g2d.drawImage(newImage(toMoveString()), dX, dY, owner);
				if (owner.darkenWorld())
					g2d.drawImage(newShadow(toMoveString()), dX, dY, owner);
			}
		}

	}

	@Override
	public Moves getRangedMove() {
		// TODO Auto-generated method stub
		return Moves.WIZ_R;
	}

	@Override
	public int rangedAddX() {
		return 13;
	}

	public int rangedAddY() {
		return height / 2 - 14;
	}


	@Override
	public String getRangedString() {
		return "arrow.png";
	}

	public Rectangle getActBounds() {
		if (1 == getActing()) {
			switch (direction) {
			case UP:
				return new Rectangle(x + 45, y - 50, 19, 60);
			case DOWN:
				return new Rectangle(x + 35, y + Statics.BLOCK_HEIGHT - 10, 19, 60);
			case RIGHT:
				return new Rectangle(x + 70, y + 45, 60, 19);
			case LEFT:
			default:
				return new Rectangle(x - 30, y + 45, 60, 19);
			}
		} else
			switch (direction) {
			case UP:
				return new Rectangle(x + 47, y - 30, 6, 6);
			case DOWN:
				return new Rectangle(x + 47, y + Statics.BLOCK_HEIGHT + 40, 6, 6);
			case RIGHT:
				return new Rectangle(x + Statics.BLOCK_HEIGHT + 15, y + Statics.BLOCK_HEIGHT - 6, 6, 6);
			case LEFT:
			default:
				return new Rectangle(x - 40, y + Statics.BLOCK_HEIGHT - 6, 6, 6);
			}
	}





	@Override
	protected void drawCSHUD(Graphics2D g2d) {
		// TODO Auto-generated method stub
		
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
	public Image newImage(String name) {
	if(!name.contains("/")){
		if (isCharacterSkin(name)){
			shadow = newShadow(name);}
		return super.newImage(getPath() + name + ".gif");}
		else{
		return	super.newImage(name);
		}
	}
	@Override
	public Image newShadow(String name) {
		return Statics.newImage(getPath()+ name + ".gif");//Temp
	
}
	

	public boolean isCharacterSkin(String name) {
		String[] playerSkinsM = new String[] { "n", "w0", "w1", "w2", "w3", "g","s" };
		for (String s : playerSkinsM)
			if (s.equals(name))
				return true;
		return false;
	}
}
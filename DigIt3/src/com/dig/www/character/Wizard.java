package com.dig.www.character;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.ObjectInputStream.GetField;

import com.dig.www.character.GameCharacter.Direction;
import com.dig.www.start.Board;
import com.dig.www.util.Statics;

public class Wizard extends GameCharacter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected boolean keyReleased;
protected int magic;
private static final int MAGIC_MAX=50;
	public Wizard(int x, int y, Board owner, boolean player) {
		super(x, y, owner, Types.WIZARD, "wizard", player, -20, -25, -50, 30, 55, 25, 80, 10, 100, 10, 20, 50, 10, 30, 20, Statics.STRENGTH - 1);
		// TODO Auto-generated constructor stub
	}

	

	

	

	@Override
	public Moves getMove() {
		// TODO Auto-generated method stub
		switch (getActing()) {
		case 1:
			return Moves.WIZ_M;
		case 2:
			return Moves.WIZ_R;
		case 3:
			return Moves.WIZ_S;
		default:
			return Moves.NONE;
		}
	}

	@Override
	public String toMoveString() {
		switch (getActing()) {
		case 1:
			return getType().toString();
		case 3:
			return "images/characters/wizard/special/"+getInt()+".png";
		default:
			return null;
		}
	}

	@Override
	public void drawTool(Graphics2D g2d) {

		int dX = 0;
		int dY = 0;
		
			switch (direction) {
			case UP:
				dX = x + 13;
				dY = y - Statics.BLOCK_HEIGHT+ 22;
				break;

			case DOWN:
				dX = x + 60;
				dY = y + Statics.BLOCK_HEIGHT - 60;
				break;

			case RIGHT:
			case DIAG_UR:
			case DIAG_DR:
				dX = x + 75;
				dY = y+5;
				break;

			case LEFT:
			case DIAG_UL:
			case DIAG_DL:
				dX = x+25;
				dY = y + 5;
				break;
			}
		if (toMoveString() != null) {
if(getActing()==1){
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
else{
	int s=getSize();
	g2d.drawImage(newImage(toMoveString()), x+50-s/2,y+50-s/2, owner);
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
				return new Rectangle(x + 13, y - 100+22, 50, 80);
			case DOWN:
				return new Rectangle(x + 35, y + 60, 50, 80);
			case RIGHT:
				return new Rectangle(x + 95, y + 5, 80, 50);
			case LEFT:
			default:
				return new Rectangle(x - 75, y + 5, 80, 50);
			}
		} else{
			int s=getSize();
			return new Rectangle(x+50-s/2,y+50-s/2,s,s);
		}
	}
public int getSize(){
	if(magic>=MAGIC_MAX)
		return 300;
	else if(magic>=MAGIC_MAX/2)
		return 237;
	else
		return 150;
}
public int getInt(){
	if(magic>=MAGIC_MAX)
		return 2;
	else if(magic>=MAGIC_MAX/2)
		return 1;
	else
		return 0;
}
public int getDamageMult(){
	if(magic>=MAGIC_MAX)
		return 4;
	else if(magic>=MAGIC_MAX/2)
		return 2;
	else
		return 1;
}


@Override
protected void drawCSHUD(Graphics2D g2d) {
	// TODO Auto-generated method stub
	int amount=(int)(((double)(magic/(double)MAGIC_MAX))*25);
	int normWidth = 280;
int macH=Statics.MAC?23:0;
	g2d.setColor(Color.BLACK);
	g2d.fillRect(normWidth, 50+macH, 20, 45);
	g2d.setColor(Statics.PURPLE);
	g2d.fillRect(normWidth, 50+macH+25-amount, 20, amount);
	g2d.setColor(Color.GRAY);
	if(amount>=12)
		g2d.setColor(Color.WHITE);
	g2d.drawRect(normWidth-1, 50+macH-1, 21, 26);
	if(amount==25){
		for(int c=(int)(Math.random()*4);c>0;c--){
			int a=Statics.RAND.nextBoolean()?normWidth-1:normWidth+22;
			g2d.fillRect(a-2, 50+macH+(int)(Math.random()*25), 4, 2);
		}
		for(int c=(int)(Math.random()*2);c>0;c--){
			int a=Statics.RAND.nextBoolean()?50+macH-3:74+macH-1;
			g2d.fillRect(normWidth-1+(int)(Math.random()*18), a, 2, 4);
		}	
	}
	g2d.drawImage(Statics.newImage("images/characters/wizard/special/"+getInt()+".png"), normWidth, 75+macH, 20, 20, owner);
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







	public void addMagic(int adder) {
		// TODO Auto-generated method stub
		magic+=adder;
		if(magic>MAGIC_MAX)
			magic=MAGIC_MAX;
	}
	public void clearMagic(){
		magic=0;
	}
}

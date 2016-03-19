package com.dig.www.npc;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import com.dig.www.start.Board;
import com.dig.www.util.Preferences;
import com.dig.www.util.Statics;

public abstract class CutScene extends InvisibleNormalOnceTouchNPC{
protected int c=0;
protected CSDialog[]cutScenes;
protected static final int boxHeight = 120;
	public CutScene(int x, int y, Board owner,String location,CSDialog[]cuts) {
		super(x, y, owner, new String[0], "black", location,new NPCOption[0]);
		this.cutScenes=cuts;
		// TODO Auto-generated constructor stub
	}
	@Override
		public void act(NPCOption option) {
		}
	@Override
		public void drawOption(Graphics2D g2d) {
			// TODO Auto-generated method stub

		g2d.setFont(NPC_NORMAL);
		g2d.setColor(Color.LIGHT_GRAY);
		g2d.fillRect(0, Statics.BOARD_HEIGHT - boxHeight, Statics.BOARD_WIDTH,
				boxHeight);
		g2d.setColor(Color.BLACK);

		String l;
		int posX;
		int posY;
		boolean escaped = false;
		boolean underline = false;

		l = cutScenes[c].getLine()+"\n+(" + KeyEvent.getKeyText(Preferences.NPC()) + " -> next)|";

		posX = 0;
		posY = Statics.BOARD_HEIGHT- 115+23;

		for (int i = 0; i < l.length(); i++) {
			if (posX > Statics.BOARD_WIDTH - 160) {
				posY += 25;
				posX = 0;
			}

			if (l.charAt(i) == '\n') {
				posX = Statics.BOARD_WIDTH;
				continue;
			}

			if (!escaped) {
				if (l.charAt(i) == THINK) {
					g2d.setFont(NPC_THOUGHT);
					continue;
				} else if (l.charAt(i) == BOLD) {
					g2d.setFont(NPC_BOLD);
					continue;
				} else if (l.charAt(i) == UNDERLINE) {
					underline = !underline;
					continue;
				} else if (l.charAt(i) == NORMAL) {
					g2d.setFont(NPC_NORMAL);
					continue;
				} else if (l.charAt(i) == ESCAPE) {
					escaped = true;
					continue;
				}
			} else
				escaped = false;

			g2d.drawString(l.charAt(i) + "", posX + 140, posY);

			if (underline) {
				g2d.setStroke(new BasicStroke(g2d.getFont() == NPC_BOLD ? 3 : 2));
				g2d.drawLine(
						posX + 140,
						posY + 5,
						posX
								+ 140
								+ getPosXAdd(l.charAt(i),
										g2d.getFont() == NPC_BOLD), posY + 5);
			}

			posX += getPosXAdd(l.charAt(i), g2d.getFont() == NPC_BOLD);
		}

		g2d.fillRect(5, Statics.BOARD_HEIGHT- 115,
				Statics.BLOCK_HEIGHT + 10, Statics.BLOCK_HEIGHT + 10);
		g2d.drawImage(newImage("images/npcs/talking/"+cutScenes[c].getName()+".gif"), 10, Statics.BOARD_HEIGHT
				- 110, owner);

		
			g2d.setStroke(new BasicStroke(5));
			g2d.drawLine(0, Statics.BOARD_HEIGHT - 8,
					Statics.BOARD_WIDTH, Statics.BOARD_HEIGHT
							- 8);
		
		String[] string = getShowName().split(" ");
		Font font;
		if (string.length == 1)
			font = new Font(Statics.FONT, Font.PLAIN,
					string[0].length() <= 11 ? 20
							: (int) (20 - ((string[0].length() - 10))));
		else {
			ArrayList<String> strings = new ArrayList<String>();
			for (int c = 0; c < string.length; c++) {
				strings.add(string[c]);
			}
			for (int c = 1; c < strings.size(); c++) {
				if (strings.get(c - 1).length() + strings.get(c).length() < 18) {
					strings.set(c - 1,
							strings.get(c - 1) + " " + strings.get(c));
					strings.remove(c);
					c--;
				}
			}
			string = new String[strings.size()];
			for (int c = 0; c < string.length; c++) {
				string[c] = strings.get(c);
			}
			int maxL = 0;
			for (int c = 0; c < string.length; c++) {
				if (string[c].length() > maxL)
					maxL = string[c].length();
			}
			font = new Font(Statics.FONT, Font.PLAIN, maxL <= 11 ? 20
					: (int) (20 - ((maxL - 10))));
		}
		g2d.setFont(font);
		g2d.setColor(Color.BLACK);
		for (int c = 0; c < string.length; c++)
			g2d.drawString(string[c], 6, Statics.BOARD_HEIGHT - boxHeight / 2
					- 100 + ((c - string.length) * font.getSize()));
		
	
			//if(lastICut!=null){
		if(cutScenes[c].getcS()!=null){
				g2d.setColor(cutScenes[c].getcS().getBackColor());
				g2d.fillRect(0, 0, Statics.BOARD_WIDTH,Statics.BOARD_HEIGHT - boxHeight);
		g2d.drawImage(Statics.newImage(cutScenes[c].getcS().getLoc()), 0, 0, Statics.BOARD_WIDTH,Statics.BOARD_HEIGHT - boxHeight, owner);
		}
		
	
		}
	@Override
	public void exit() {
		// TODO Auto-generated method stub
		c++;
		if(c>=cutScenes.length)
			end();
	}
	@Override
	protected String getGreeting() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getFarewell() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String exitLine() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setLine() {
	}
}

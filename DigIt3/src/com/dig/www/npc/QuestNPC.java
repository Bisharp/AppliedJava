package com.dig.www.npc;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.dig.www.character.CharData.SimpleQuest;
import com.dig.www.character.GameCharacter;
import com.dig.www.character.Items;
import com.dig.www.start.Board;
import com.dig.www.start.DigIt;
import com.dig.www.util.Quest;
import com.dig.www.util.Quest.Quests;
import com.dig.www.util.Statics;

public class QuestNPC extends NPC {

	public final int id;

	protected Quest quest;
	protected Items item;
	protected String place;
	protected boolean hasActed = false;
	protected boolean questAccepted = false;
	protected boolean questCompleted = false;

	protected NPCOption[] yesNo = null;
	protected Rectangle[] yesNoRect = null;
	protected static final String YES_OPTION = "Yes";

	protected boolean say = false;
	protected Integer n = null;

	public QuestNPC(int x, int y, String loc, Board owner, String location, int id) {

		super(x, y, loc, owner, new String[] { "So... any progress?" }, QUEST, location, new NPCOption[] { new NPCOption("What's wrong?", "",
				new String[] { "What's bothering you?", "What's bothering you and does fixing it involve smashing stuff?", "...",
						"Oh dear, something's wrong I just know. What is it?", "Something appears to be wrong; what is it?" }, true, owner) });

		quest = new Quest(this);
		item = quest.getItem();
		place = quest.getPlace();

		this.id = id;
	}

	protected void specify(Items i, String place) {

		if (i != Items.NULL) {
			item = i;
			quest.setItem(i);
		}

		if (place != null) {
			this.place = place;
			quest.setPlace(place);
		}
	}

	@Override
	public void act(NPCOption option) {

		if (!questCompleted)
			if (!questAccepted && hasActed) {
				// int n = JOptionPane.showConfirmDialog(owner, quest.getLine()
				// + "\nWill you help me?", DigIt.NAME,
				// JOptionPane.YES_NO_OPTION,
				// JOptionPane.YES_OPTION, new ImageIcon(gif));

				if (yesNo == null) {
					iTalk = true;
					yesNo = new NPCOption[] {
							new NPCOption(YES_OPTION, "", new String[] { "Of course!", "Why not?",
									"... (You can tell from the touched sparkle in his eyes that he accepts)",
									"I can't stand seeing someone in need! Of course!", "I will." }, true, owner),
							new NPCOption("No", "", new String[] { "I really can't...", "NO!",
									"... (You can tell from the flintlike glint in his eyes that he rejects)",
									"Oh, I'd love to, but I really can't make time. I'm so sorry...", "I cannot." }, true, owner) };
					yesNoRect = new Rectangle[2];
					int length = 0;
					for (int i = 0; i < yesNo.length; i++) {
						yesNoRect[i] = new Rectangle(length + 30, Statics.BOARD_HEIGHT - (int) (boxHeight / 2) + 50,
								yesNo[i].question().length() * 10 + 30, buttonHeight);
						length += yesNoRect[i].width + 30;
					}

					line = quest.getLine() + "\nWill you help me?";
				}

				if (n != null && n == JOptionPane.YES_OPTION) {
					owner.getData().registerQuest(this);
					questAccepted = true;
					line = "I knew you would!";
					endButtons();
					setAcceptedVals();
				} else if (n != null) {
					line = "Oh... I understand.";
					endButtons();
					say = true;
				}
			} else if (GameCharacter.getInventory().contains(item)) {
				GameCharacter.getInventory().decrementItem(item);
				owner.getData().completeQuest(this);
				line = "Oh! Thank you so!";
				questCompleted = true;
				hasActed = true;
			} else if (questAccepted) {
				line = quest.getIncompleteLine();
				hasActed = true;
			} else
				hasActed = true;
	}

	protected void endButtons() {
		yesNo = null;
		yesNoRect = null;
		n = null;
		hasActed = false;
	}

	@Override
	public void drawOption(Graphics2D g2d) {
		super.drawOption(g2d);

		if (yesNo != null) {

			g2d.setStroke(new BasicStroke(5));
			g2d.setColor(Color.LIGHT_GRAY);
			g2d.fillRect(0, Statics.BOARD_HEIGHT - (int) (boxHeight / 2) + 5, Statics.BOARD_WIDTH, boxHeight);

			if (quest.getType() == Quests.THEFT)
				g2d.fillRect(142, 603, 130, 18);
			
			g2d.setColor(Color.black);
			g2d.drawLine(0, Statics.BOARD_HEIGHT - (int) (boxHeight / 2) + 5, Statics.BOARD_WIDTH, Statics.BOARD_HEIGHT - (int) (boxHeight / 2) + 5);

			for (int i = 0; i < yesNo.length; i++) {

				g2d.setColor(Color.black);
				g2d.fill(yesNoRect[i]);
				g2d.setColor(Color.white);
				g2d.drawString(yesNo[i].question(), yesNoRect[i].x + 5, Statics.BOARD_HEIGHT - boxHeight / 4);
			}
		}
	}

	@Override
	public void mouseClick(MouseEvent m) {
		if (yesNo == null)
			super.mouseClick(m);
		else if (yesNoRect != null) {
			Rectangle mouseBounds = new Rectangle(m.getX(), m.getY(), 5, 10);

			for (int i = 0; i < yesNoRect.length; i++)
				if (yesNoRect[i].intersects(mouseBounds)) {
					n = yesNo[i].question().equals(YES_OPTION) ? JOptionPane.YES_OPTION : JOptionPane.NO_OPTION;
					act(BLANK);
					break;
				}
		}
	}

	@Override
	public String getLine() {

		if (!hasActed && !exiting && !questAccepted && !say)
			return "Uh... I could really use your help!";
		else if (questCompleted)
			if (!hasActed)
				return thankLine();
			else
				return line;
		else
			return line;
	}

	protected String thankLine() {
		return "Thank you again for yor help!";
	}

	public Items getType() {
		return item;
	}

	public void setQuestState(SimpleQuest state) {
		questAccepted = state.isAccepted();
		questCompleted = state.isCompleted();

		quest = new Quest(state);

		place = quest.getPlace();
		item = quest.getItem();

		if (questCompleted)
			setCompletedVals();
		else if (questAccepted)
			setAcceptedVals();
	}

	protected void setCompletedVals() {
		options[0].changeQuestion("Is there anything else we can do for you?", new String[] { "Do you need anything else?",
				"Do you need any more help?", "...", "Can we do anything else for you?", "Do you need further assistance?" });
		buttons[0] = new Rectangle((int) buttons[0].getX(), (int) buttons[0].getY(), options[0].question().length() * 10 + 10,
				(int) buttons[0].getHeight());
	}

	protected void setAcceptedVals() {
		options[0].changeQuestion("Let's check our standings", new String[] { "Let's see... do we have what you were looking for?",
				"Is any of this junk what you were looking for?", "...", "Is this it?", "Do we have what you were looking for?" });
		buttons[0] = new Rectangle((int) buttons[0].getX(), (int) buttons[0].getY(), options[0].question().length() * 10 + 10,
				(int) buttons[0].getHeight());
	}

	public String getPlace() {
		return place;
	}

	@Override
	public String exitLine() {
		if (questCompleted)
			return "See you around!";
		else if (questAccepted)
			return "Best of luck!";
		else
			return "Oh; well, see you later, I guess.";
	}

	@Override
	public void exit() {
		if (!hasActed || questAccepted) {
			super.exit();

			if (!inDialogue)
				hasActed = false;
		} else if (!iTalk)
			act(new NPCOption("", "", new String[] {}, owner));

		if (hasActed && questCompleted) {
			setCompletedVals();
			hasActed = false;
		}
	}

	@Override
	public void setLine() {
		super.setLine();
		hasActed = false;
		say = false;
	}
}

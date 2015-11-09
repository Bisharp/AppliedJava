package com.dig.www.npc;

import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.dig.www.character.CharData.SimpleQuest;
import com.dig.www.character.GameCharacter;
import com.dig.www.character.Items;
import com.dig.www.start.Board;
import com.dig.www.start.DigIt;
import com.dig.www.util.Quest;
import com.dig.www.util.Statics;

public class QuestNPC extends NPC {

	public final int id;

	protected Quest quest;
	protected Items item;
	protected String place;
	protected boolean hasActed = false;
	protected boolean questAccepted = false;
	protected boolean questCompleted = false;

	public QuestNPC(int x, int y, String loc, Board owner, String location, int id) {

		super(x, y, loc, owner, new String[] { "So... any progress?" }, QUEST, location, new NPCOption[] { new NPCOption("What's wrong?", "", new String[] {
				"What's bothering you?", "What's bothering you and does fixing it involve smashing stuff?", "...",
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

	protected NPCOption chosenOption = null;

	@Override
	public void act(NPCOption option) {

		if (!questCompleted)
			if (!questAccepted && hasActed) {
				int n = JOptionPane.showConfirmDialog(owner, quest.getLine() + "\nWill you help me?", DigIt.NAME, JOptionPane.YES_NO_OPTION,
						JOptionPane.YES_OPTION, new ImageIcon(gif));
				if (n == JOptionPane.YES_OPTION) {
					owner.getData().registerQuest(this);
					questAccepted = true;
					line = "I knew you would!";
					iTalk = true;
				} else {
					line = "Oh... I understand.";
					iTalk = true;
				}

				setAcceptedVals();
				hasActed = false;
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

	@Override
	public String getLine() {

		if (!hasActed && !exiting && !questAccepted)
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
}

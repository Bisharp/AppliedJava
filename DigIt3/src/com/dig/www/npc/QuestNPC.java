package com.dig.www.npc;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.dig.www.character.CharData.SimpleQuest;
import com.dig.www.character.GameCharacter;
import com.dig.www.character.Items;
import com.dig.www.start.Board;
import com.dig.www.start.DigIt;
import com.dig.www.util.Quest;

public class QuestNPC extends ServiceNPC {

	private Quest quest;
	private Items item;
	private String place;
	public final int id;
	private boolean questAccepted = false;
	private boolean questCompleted = false;

	public QuestNPC(int x, int y, String loc, Board owner, int id) {

		// Because of overrides, dialogs goes unused; therefore, I have stuck in
		// some minor messages for anyone outside of the AppliedJava class.
		// (Hotel Mario voice): Get the hint?
		super(x, y, loc, owner, new String[] { "NO TRESSPASSING", "STOP SNOOPING", "YOU MUST DIE!\n     --Ganon, Zelda: Wand of Gamelon" }, "reyzu",
				"Oh. Oh well.");

		quest = new Quest(this);
		item = quest.getItem();
		place = quest.getPlace();

		this.id = id;
	}

	@Override
	public void service() {

		if (!questCompleted)
			if (!questAccepted) {
				int n = JOptionPane.showConfirmDialog(owner.getOwner(), quest.getLine() + "\nWill you help me?", DigIt.NAME,
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon(gif));
				if (n == JOptionPane.YES_OPTION) {
					owner.getData().registerQuest(this);
					questAccepted = true;
				}
			} else if (GameCharacter.getInventory().contains(item)) {
				GameCharacter.getInventory().decrementItem(item);
				owner.getData().completeQuest(this);
				JOptionPane.showMessageDialog(owner.getOwner(), "Oh! Thank you so!", DigIt.NAME, JOptionPane.INFORMATION_MESSAGE, new ImageIcon(gif));
				questCompleted = true;
			} else
				JOptionPane.showMessageDialog(owner.getOwner(), quest.getLine().split("!")[1] + ". I guess you haven't seen it yet.", DigIt.NAME,
						JOptionPane.QUESTION_MESSAGE, new ImageIcon(gif));
	}

	@Override
	public String getLine() {
		if (!questCompleted)
			return (!questAccepted ? "Uh... I could really use your help! (Enter ==> What do you need?)"
					: "So... any progress? (Enter ==> Let's Check)");
		else
			return "Thanks again for your help!";
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
	}
	
	public String getPlace() {
		return place;
	}
}

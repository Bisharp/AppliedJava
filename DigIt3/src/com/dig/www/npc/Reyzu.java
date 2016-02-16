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

public class Reyzu extends QuestNPC {

	public Reyzu(int x, int y, Board owner, String location, int id) {

		super(
				x,
				y,
				"images/npcs/map/stationary/reyzu.png",
				owner,
				new String[] { "Um. I need help." },
				REYZU,
				location,
				new NPCOption[] { new NPCOption(
						"What's wrong?",
						"Nothing",
						new String[] {
								"What's bothering you?",
								"What's bothering you and does fixing it involve smashing stuff?",
								"...",
								"Oh dear, something's wrong I just know. What is it?",
								"Something appears to be wrong; what is it?" },
						false,
						new NPCOption[] {
								new NPCOption(
										"Yes",
										"I knew you would!",
										new String[] {
												"Of course!",
												"Why not?",
												"... (You can tell from the touched sparkle in his eyes that he accepts)",
												"I can't stand seeing someone in need! Of course!",
												"I will." }, true, owner),
								new NPCOption(
										"No",
										"Oh... I understand.",
										new String[] {
												"I really can't...",
												"NO!",
												"... (You can tell from the flintlike glint in his eyes that he rejects)",
												"Oh, I'd love to, but I really can't make time. I'm so sorry...",
												"I cannot." }, true, owner) },
						owner) }, id);
specify(Items.DONUT, "hauntedTest",Quest.Quests.FETCH);
options[0].setAnswer(quest.getLine() + "\nWill you help me?");
	}

	@Override
	public void act(NPCOption option) {

		if (!questCompleted && !questAccepted) {

			if (option.question().equals("Yes")) {
				owner.getData().registerQuest(this);
				questAccepted = true;
				setAcceptedVals(0);
			}

		} else if (!questCompleted) {
			if (option.question().equals("Let's check our standings.")) {
				if (GameCharacter.getInventory().contains(item)) {
					GameCharacter.getInventory().decrementItem(item,1);
					owner.getData().completeQuest(this);
					line = "Oh! Thank you so!";
					questCompleted = true;
					setCompletedVals();
				}
			}
		}
	}

	protected void setCompletedVals() {
		greetingDialogs = new String[1];
		greetingDialogs[0] = "Thank you again for yor help!";
		options = new NPCOption[2];
		options[0] = new NPCOption("Is there anything else?.",
				"No, thanks for your help.", new String[] {
						"Do you need anything else?",
						"Do you need any more help?", "...",
						"Can we do anything else for you?",
						"Do you need further assistance?" }, false, owner);
		// Will be removed
		options[1] = new NPCOption("Will you join us?",
				"Of course not! I am not a playable character!", new String[] {
						"Will you join us now?", "Join us!", "...",
						"Can you join us?", "Care to join the cause?" }, false,
				owner);

	}

	protected void setAcceptedVals(int phase) {
		greetingDialogs = new String[1];
		greetingDialogs[0] = "So.. Any progress?";
		options = new NPCOption[1];
		options[0] = new NPCOption("Let's check our standings.",
				quest.getIncompleteLine(), new String[] {
						"Let's see... do we have what you were looking for?",
						"Is any of this junk what you were looking for?",
						"...", "Is this it?",
						"Do we have what you were looking for?" }, true, owner);
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
	public String getShowName() {
		return "Reyzu";
	}
	@Override
	public Rectangle getBounds(){
		
		return new Rectangle(x+5,y+5,75,95);
		
	}
}

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

public abstract class QuestNPC extends NPC {

	public final int id;

	protected Quest quest;
	protected Items item;
	protected String place;
	protected boolean questAccepted = false;
	protected boolean questCompleted = false;

	public QuestNPC(int x, int y, String loc, Board owner, String[] greetings,
			String name,String location, NPCOption[] options,  int id) {
		
		super(x, y, loc, owner, greetings, name, location, options);

		quest = new Quest(this);
		item = quest.getItem();
		place = quest.getPlace();

		this.id = id;
	}

	protected void specify(Items i, String place,Quest.Quests type) {

		if (i != Items.NULL)
			item = i;
			
		if (place != null) 
			this.place = place;
		
		quest.specify(item, this.place,type);
	}

	// @Override
	// public void act(NPCOption option) {
	//
	//
	// if (!questCompleted&&!questAccepted) {
	//
	//
	// if(option.question().equals("Yes")){
	// owner.getData().registerQuest(this);
	// questAccepted = true;
	// setAcceptedVals();
	// }
	//
	//
	// } else if(!questCompleted){
	// if(option.question().equals("Let's check our standings.")){
	// if(GameCharacter.getInventory().contains(item)){
	// GameCharacter.getInventory().decrementItem(item);
	// owner.getData().completeQuest(this);
	// line="Oh! Thank you so!";
	// questCompleted = true;
	// setCompletedVals();
	// }
	// }}
	// }

	public Items getType() {
		return item;
	}

	public void setQuestState(SimpleQuest state) {
		questAccepted = state.isAccepted();
		questCompleted = state.isCompleted();

		quest = new Quest(state);

		place = quest.getPlace();
		item = quest.getItem();

		if (questCompleted) {
			setCompletedVals();
			currentOptions = options.clone();
			buttons = new Rectangle[currentOptions.length];
			int length = 0;
			for (int i = 0; i < currentOptions.length; i++) {
				buttons[i] = new Rectangle(length + 10, Statics.BOARD_HEIGHT
						- (int) (boxHeight / 2) + 50, currentOptions[i]
						.question().length() * 10 + 10, buttonHeight);
				length += buttons[i].width + 10;
			}
		} else if (questAccepted) {
			setAcceptedVals(state.getAcceptedPhase());
			currentOptions = options.clone();
			buttons = new Rectangle[currentOptions.length];
			int length = 0;
			for (int i = 0; i < currentOptions.length; i++) {
				buttons[i] = new Rectangle(length + 10, Statics.BOARD_HEIGHT
						- (int) (boxHeight / 2) + 50, currentOptions[i]
						.question().length() * 10 + 10, buttonHeight);
				length += buttons[i].width + 10;
			}
		}
	}

	protected abstract void setCompletedVals();

	protected abstract void setAcceptedVals(int stage);


	public String getPlace() {
		return place;
	}
public void setPhase(int setter){
	owner.getData().setAcceptedPhase(this, setter);
	setAcceptedVals(setter);
}
public int getPhase(){
	return owner.getData().getAcceptedPhase(this);
}
public void setAccepted(boolean setter){
	this.questAccepted=setter;
	setAcceptedVals(getPhase());
}
public boolean getAccepted(){
	return questAccepted;
}
public void setCompleted(boolean setter){
	this.questCompleted=setter;
	setCompletedVals();
}
public boolean getCompleted(){
	return questCompleted;
}
}

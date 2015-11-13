package com.dig.www.npc;

import java.awt.Rectangle;

import com.dig.www.start.Board;
import com.dig.www.util.Statics;

public class NPCOption {

	protected String question;
	protected String answer;
	protected String[] questionAsked;
	protected NPCOption[] newOptions;
	protected boolean acts;
	protected Board owner;
	public final int ID;
	protected static int id = 0;
	
	public static void resetId() {
		id = 0;
	}

	public NPCOption(String q, String a, String[] qS, boolean acts, Board owner) {

		question = q;
		answer = a;
		this.acts = acts;
		questionAsked = qS;
		this.owner = owner;
		newOptions=new NPCOption[0];
		ID = id;
		id++;
	}
	public NPCOption(String q, String a, String[] qS, boolean acts,NPCOption[]newOptions, Board owner) {
this.newOptions=newOptions;
		question = q;
		answer = a;
		this.acts = acts;
		questionAsked = qS;
		this.owner = owner;
		
		ID = id;
		id++;
	}

	public NPCOption(String q, String a, String[] qS, Board owner) {

		question = q;
		answer = a;
		acts = false;
		questionAsked = qS;
		this.owner = owner;

		ID = id;
		id++;
		newOptions=new NPCOption[0];
	}

	public String question() {
		return question;
	}

	public String answer() {
		return answer;
	}

	public boolean acts() {
		return acts;
	}

	public String questionAsked() {
		switch (owner.getCharacter().getType()) {
		case CLUB:
			return questionAsked[1];
		case DIAMOND:
			return questionAsked[2];
		case HEART:
			return questionAsked[3];
		case SIR_COBALT:
			return questionAsked[4];
		default:
			return questionAsked[0];
		}
	}
	public void setAnswer(String answer){
		this.answer=answer;
	}
	public String[] questionsAsked() {
		return questionAsked;
	}
	public void changeQuestion(String q, String[] qA) {
		question = q;
		questionAsked = qA;
	}
	public NPCOption[]getNewOptions(){
		return newOptions;
	}
	public void setNewOptions(NPCOption[]optionsSetter){
		newOptions=optionsSetter;
	}
}

package com.dig.www.npc;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.dig.www.character.GameCharacter;
import com.dig.www.start.Board;
import com.dig.www.util.ConditionEnteringMap;

public class WizardTowerSirCobalt extends NPC implements ConditionEnteringMap {
	public boolean visible;

	public WizardTowerSirCobalt(int x, int y, Board owner, String location) {
		super(x, y, "images/npcs/map/stationary/sirCobalt.png", owner,
				new String[] {
						"I will only answer a few things before you complete the course.(+Click| the black box to talk)" },
				NPC.SIR_COBALT, location, new NPCOption[] { new NPCOption("Who are you?", "I'm the one who has been called \"the masked man\". My real name is Sir Cobalt.", new String[]{"Who are you?","Who are you?","Who are you?","Who are you?"}, false, new NPCOption[]{new NPCOption("Who was that plant man and why were we taken?",
						"The plant man is called Botanus, but I don't know why he took you. I'm sure The Wizard has found some  weapons for you by now.",
						new String[] { "Who was that half monster and why did he take us?" }, true, owner)}, owner) });
		cantExit = true;
	}

	@Override
	public void act(NPCOption option) {

		if (GameCharacter.storyInt == 1)
			GameCharacter.storyInt = 2;
		options = new NPCOption[0];
		cantExit = false;

	}

	@Override
	protected String getGreeting() {
		return "I have many questions that need answering.";
	}

	@Override
	public Rectangle getBounds() {
		if (visible)
			return new Rectangle(x + 20, y, 60, 100);
		else
			return new Rectangle();
	}

	@Override
	public void draw(Graphics2D g2d) {
		if (visible)
			super.draw(g2d);
	}

	public void animate() {
		if (GameCharacter.storyInt >= 1)
			visible = true;
		super.animate();
	}

	@Override
	public boolean enter() {
		// TODO Auto-generated method stub
		return GameCharacter.storyInt < 4;
	}

	@Override
	public String exitLine() {
		// TODO Auto-generated method stub
		return "Excellent. Go outside, and Walter will be right behind you.";
	}

	@Override
	protected String getFarewell() {
		return "OK, I'll go on the course as long as it isn't dangerous.";
	}

	@Override
	public String getShowName() {
		// TODO Auto-generated method stub
		return "masked man";
	}

}

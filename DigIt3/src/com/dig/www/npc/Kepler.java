package com.dig.www.npc;

import java.awt.Rectangle;

import com.dig.www.character.GameCharacter;
import com.dig.www.character.Items;
import com.dig.www.start.Board;

public class Kepler extends NPC {

	public Kepler(int x, int y, Board owner, String location) {
		super(x, y, "images/npcs/map/stationary/kepler.png", owner, new String[] { "Science is cool!", "There are enemies ahead. Defeat them with SCIENCE!", "I miss my robot...",
				"Can you believe that guy? He thinks magic is superior to science!" }, KEPLER, location, 
				new NPCOption[] {
						new NPCOption(
								"Who are you?",
								"I'm Dr. Xavier Kepler--Inventor and Scientist extraordinare!",
								new String[] {
										"So... who are you?",
										"And who are YOU supposed to be?",
										"...",
										"Are you some kind of cool scientist?",
										"I'm sorry, your name is slipping my mind at the moment."
								}, false, owner),
						new NPCOption(
								"Magic isn't real?",
								"Of course not! \"Magic\" is just science that hasn't quite been figured out yet!",
								new String[] {
										"I've been wondering... is what the Wizard does really magic?",
										"What makes you think magic is bogus?",
										"...",
										"So... you REALLY don't believe in magic?",
										"Are you still adamant, after all that we've seen, that magic isn't real?"
								}, false, owner),
						new NPCOption(
								"What's that thing you're holding?",
								"Oh, this is just an old Video Game. You should take it off my hands, it's distracting me too easily.",
								new String[] {
										"Hey, what's that you're fiddling around with there?",
										"What does that dumb-looking gizmo do?",
										"...",
										"Ooh, a cool sciencey gadget! What does it do?",
										"Out of curiosity, what is that you're working on there? Do you think it will be helpful?"
								}, true, owner),
						new NPCOption(
								"Join us?",
								"Sorry, I can't at the moment. I'm working on inventing a rocket-powered swivel chair. Thank you for the offer, though!",
								new String[]{
										"Will you join us?", 
										"Come with us! Right now!",
										"...",
										"Will you come with us, *please?",
										"Are you ready to assist us?"
								}, false, owner)
						
				}
		
		);
	}
	@Override
	public void act(NPCOption option) {
		GameCharacter.getInventory().addItem(Items.VIDEO_GAME, 1);
	}

	@Override
	public String exitLine() {
		return "Continue onward-- for SCIENCE!";
	}

	@Override
	public String getShowName() {
		return "Kepler";
	}
	@Override
	public Rectangle getBounds(){
		
		return new Rectangle(x+15,y,65,100);
		
	}
}

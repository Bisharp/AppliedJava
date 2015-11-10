package com.dig.www.npc;

import com.dig.www.character.GameCharacter;
import com.dig.www.character.Items;
import com.dig.www.start.Board;

public class WizardGuy extends NPC {

	public WizardGuy(int x, int y, String loc, Board owner, String location) {
		super(x, y, loc, owner, new String[] { "Magic is cool!", "It's dangerous to go alone. Take this!",
				"Mom told me I could grow up to be anything I wanted, so I became a wizard!",
				"Can you believe that guy? He thinks science is superior to magic!" }, WIZARD, location, new NPCOption[] {
				new NPCOption("Who are you?", "I am your friendly neighborhood wizard!", new String[] { "What's your name?", "Give me your name.",
						"...", "May I ask your name?", "Your name, friend?" },false,
						new NPCOption[]{new NPCOption("Hi", "Hi back", new String[] { "Hello", "Hello",
								"Hello", "Hello", "Hello" }
						, owner)}
				,
				owner),
				new NPCOption("Can you do a magic trick for me?", "Of course! Have a banana!", new String[] { "So... you know magic?",
						"You do magic? Show me.", "...", "Ooh, you must be a really good stage magician!",
						"Uh... why do I suddenly get the urge to see one of your magic tricks?" }, true, owner),
				new NPCOption("Why are you a magician, anyway?",
						"My mom told me I could be +anything| I wanted when I grew up, so why not a wizard?", new String[] {
								"Why are you a wizard, anyway?", "What made you choose such a bizarre line of work?", "...",
								"So, uh, why are you a magician, anyway?", "Can you tell me why you're a wizard again?" }, false, owner),
				new NPCOption("Where did you get that robe and hat?",
						"I made them at summer camp, when the other kids were making tie-dyed shirts. *Those were the days...|", new String[] {
								"Where did you get those clothes? They don't look factory-made.", "Where did that getup come from?", "...",
								"Where did you get those clothes? I love the color!", "Where did you get that robe? I'm just curious." }, false,
						owner),
//				new NPCOption("This is a test.",
//						"_Underline_ *Think| Normal +Bold _Underlined Bold_ *_Underlined Italic|_ Escaped chars: \\+\\|\\_\\*\\\\", new String[] {
//								"Test.", "What kind of joke is this?", "...",
//								"Huh?", "Testing, 1-2-3." }, false,
//						owner)
				});
		// * * * * * /=\
		// * * * * * | Magic. Do not touch.
	}

	@Override
	public void act(NPCOption option) {
		GameCharacter.getInventory().addItem(Items.BANANA, 1);
	}

	@Override
	public String exitLine() {
		return "I will see you again! My Crystal Ball/Snowglobe tells me so!";
	}
}
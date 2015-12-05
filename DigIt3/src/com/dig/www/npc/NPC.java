package com.dig.www.npc;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.dig.www.start.Board;
import com.dig.www.util.Sprite;
import com.dig.www.util.Statics;
import com.dig.www.util.Preferences;

public abstract class NPC extends Sprite {

	public static final Font NPC_NORMAL = new Font(Statics.FONT, Font.PLAIN, 20);
	public static final Font NPC_THOUGHT = new Font(Statics.FONT, Font.ITALIC,
			20);
	public static final Font NPC_BOLD = new Font(Statics.FONT, Font.BOLD, 20);

	public static final String WIZARD = "wizard";
	public static final String SIR_COBALT = "sirCobalt";
	public static final String KEPLER = "kepler";
	public static final String SHOPKEEP = "shopkeep";
	public static final String GATEKEEPER = "gatekeeper";
	public static final String MACARONI = "macaroni";
	public static final String PLATO = "plato";
	public static final String REYZU = "reyzu";

	protected static final NPCOption BLANK = new NPCOption("", "",
			new String[] {}, null);

	protected String[] greetingDialogs;
	protected NPCOption[] options;
	protected NPCOption[] currentOptions;
	protected Rectangle[] buttons;
	protected transient Image gif;
	protected String line;
	protected int index = -1;
	protected String location;

	protected static final char NORMAL = '|';
	protected static final char THINK = '*';
	protected static final char BOLD = '+';
	protected static final char UNDERLINE = '_';
	protected static final char ESCAPE = '\\';

	protected static final ArrayList<Character> fatChar;
	protected static final ArrayList<Character> thinChar;

	static {
		fatChar = new ArrayList<Character>();
		fatChar.add('m');
		fatChar.add('w');
		fatChar.add('M');
		fatChar.add('W');
		fatChar.add('H');
		fatChar.add('G');

		thinChar = new ArrayList<Character>();
		thinChar.add('i');
		thinChar.add('I');
		thinChar.add('l');
		thinChar.add('t');
		thinChar.add('1');
		thinChar.add('\'');
		thinChar.add('\"');
		thinChar.add('(');
		thinChar.add(')');
		thinChar.add('r');
	}

	protected boolean iTalk = false;
	protected boolean inConversation=false;
	protected boolean inDialogue = true;
	protected boolean exiting = false;
	protected static final int MAX = 3;
	protected static final int MIN = 10;
	protected boolean wait = true;
	protected NPCOption willOption;

	public NPC(int x, int y, String loc, Board owner, String[] dialogs,
			String s, String location, NPCOption[] options) {
		super(x, y, loc, owner);

		this.greetingDialogs = dialogs;
		this.location = location;
		gif = newImage("images/npcs/talking/" + s + ".gif");
		this.currentOptions = options.clone();
		this.options = options;
		buttons = new Rectangle[options.length];

		int length = 0;
		for (int i = 0; i < options.length; i++) {
			buttons[i] = new Rectangle(length + 10, Statics.BOARD_HEIGHT
					- (int) (boxHeight / 2) + 50, options[i].question()
					.length() * 10 + 10, buttonHeight);
			length += buttons[i].width + 10;
		}

		NPCOption.resetId();
	}

	public void animate() {
		basicAnimate();
	}

	protected static final int boxHeight = 300;
	protected static final int buttonHeight = 75;

	public void drawOption(Graphics2D g2d) {
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

		l = iTalk ? getLine()
				+ (!inConversation&&!inDialogue ? append().replace("next", "exit") : append()) :

		getCharLine() + append();
		if (iTalk)
			doOption();
		posX = 0;
		posY = Statics.BOARD_HEIGHT - (boxHeight / 3) * 2;

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

		g2d.fillRect(5, Statics.BOARD_HEIGHT - boxHeight / 2 - 102,
				Statics.BLOCK_HEIGHT + 10, Statics.BLOCK_HEIGHT + 10);
		g2d.drawImage(iTalk ? getGif() : newImage("images/npcs/talking/"
				+ owner.getCharacter().getType().toString() + ".gif"), 10,
				Statics.BOARD_HEIGHT - boxHeight / 2 - 97, owner);

		if (iTalk && !inDialogue && !exiting) {
			g2d.setStroke(new BasicStroke(5));
			g2d.drawLine(0, Statics.BOARD_HEIGHT - (int) (boxHeight / 2) + 5,
					Statics.BOARD_WIDTH, Statics.BOARD_HEIGHT
							- (int) (boxHeight / 2) + 5);

			for (int i = 0; i < currentOptions.length; i++) {

				g2d.setColor(Color.black);
				g2d.fill(buttons[i]);
				g2d.setColor(Color.white);
				g2d.drawString(currentOptions[i].question(), buttons[i].x + 5,
						Statics.BOARD_HEIGHT - boxHeight / 4);
			}
		}
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

		if (inDialogue && !wait)
			if (iTalk)
				if (!exiting)
					inDialogue = false;
				else {
					end();
				}
			else {
				iTalk = true;
				wait = true;
			}
	}

	protected void end() {

		owner.setState(Board.State.INGAME);
		exiting = false;
		index = -1;
		wait = true;
		iTalk = false;
		inDialogue = true;
	}

	protected int getPosXAdd(char c, boolean bold) {
		return (fatChar.contains(c) ? 17 : thinChar.contains(c) ? 9 : 12)
				+ (bold ? 2 : 0);
	}

	protected String append() {
		return "\n+(" + KeyEvent.getKeyText(Preferences.NPC()) + " -> next)|";
	}

	public String[] getDialog() {
		return greetingDialogs;
	}

	public Image getGif() {
		return gif;
	}

	public String getLine() {
		return line;
	}

	public String getCharLine() {
		if (index == -1 && !exiting) {
			return getGreeting();
		} else if (exiting)
			return getFarewell();
		else
			return currentOptions[index].questionAsked();
	}

	protected String getGreeting() {
		switch (owner.getCharacter().getType()) {
		case CLUB:
			return "Hi. Do you have something I can break?";
		case DIAMOND:
			return "...";
		case HEART:
			return "Hi! My name's Destiny! What's yours?";
		case SIR_COBALT:
			return "Greetings.";
		default:
			return "Hello; I'm Clark. Nice to meet you.";
		}
	}

	protected String getFarewell() {
		switch (owner.getCharacter().getType()) {
		case CLUB:
			return "See ya.";
		case DIAMOND:
			return "...";
		case HEART:
			return "Oh, I need to go, bye!";
		case SIR_COBALT:
			return "Until our paths cross again.";
		default:
			return "I will see you later. Good-bye!";
		}
	}

	public void setLine() {
		line = greetingDialogs[Statics.RAND.nextInt(greetingDialogs.length)];
		wait = true;
		iTalk = false;
		inDialogue = true;
		index = -1;
	}

	public void setLine(NPCOption option) {
		line = option.answer();
		if (option.acts())
			act(option);
		willOption = option;
	}

	public void doOption() {
		if (willOption == null)
			return;
		if (willOption.getNewOptions().length > 0) {
			setCurrentOptions(willOption);
			inDialogue = true;
			inConversation=true;
		} else {
			resetCurrentOptions();
			inDialogue = false;
			inConversation=false;
		}
		willOption = null;
	}

	@Override
	public void draw(Graphics2D g2d) {
		g2d.drawImage(image, x, y, owner);
		if (owner.darkenWorld())
			g2d.drawImage(shadow, x, y, owner);
	}

	public void act(NPCOption option) {
		// Does nothing here.
	}

	public void mouseClick(MouseEvent m) {
		if (exiting || inDialogue)
			return;

		Rectangle mouseBounds = new Rectangle(m.getX(), m.getY(), 5, 10);

		for (int i = 0; i < buttons.length; i++)
			if (buttons[i].intersects(mouseBounds)) {

				setLine(currentOptions[i]);
				wait = true;
				iTalk = false;
				index = i;
				inDialogue = true;
			}
	}

	public void exit() {

		if (!inDialogue&&!inConversation) {
			line = exitLine();
			index = -1;
			exiting = true;
			wait = true;
			iTalk = false;
			inDialogue = true;
		} else {
			wait = false;
		}
	}

	public abstract String exitLine();

	public void setCurrentOptions(NPCOption optionGiver) {

		buttons = new Rectangle[optionGiver.getNewOptions().length];

		int length = 0;
		for (int i = 0; i < optionGiver.getNewOptions().length; i++) {
			buttons[i] = new Rectangle(
					length + 10,
					Statics.BOARD_HEIGHT - (int) (NPC.boxHeight / 2) + 50,
					optionGiver.getNewOptions()[i].question().length() * 10 + 10,
					NPC.buttonHeight);
			length += buttons[i].width + 10;
		}
		currentOptions = optionGiver.getNewOptions().clone();

	}

	public void resetCurrentOptions() {
		currentOptions = options.clone();
		buttons = new Rectangle[options.length];

		int length = 0;
		for (int i = 0; i < options.length; i++) {
			buttons[i] = new Rectangle(length + 10, Statics.BOARD_HEIGHT
					- (int) (boxHeight / 2) + 50, options[i].question()
					.length() * 10 + 10, buttonHeight);
			length += buttons[i].width + 10;
		}
	}

	public abstract String getShowName();
}

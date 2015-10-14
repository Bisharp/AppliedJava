package com.dig.www.util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.dig.www.start.Board;
import com.dig.www.start.DigIt;

public class Preferences implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String[] cases = new String[] { "Letters", "Numbers", "Punctuation", "Other" };

	private static final String[][] chars = { { "q", /* "w", */"e", "r", "t", "y", "u", "i", "o", "p", /*
																									 * "a"
																									 * ,
																									 * "s"
																									 * ,
																									 * "d"
																									 * ,
																									 */
	"f", "g", "h", "j", "k", "l", "z", "x", "c", "v", "b", "n", "m" }, { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" },
			{ "[", "]", "\\", ";", "\'", ",", ".", "/" }, { "SHIFT", "BACKSPACE", "ENTER", "SPACE", "ALT", "CTRL" } };
	// ----------------------------------------------------------------------------------------
	// TODO Movement
	private int up = KeyEvent.VK_UP;
	private int down = KeyEvent.VK_DOWN;
	private int left = KeyEvent.VK_LEFT;
	private int right = KeyEvent.VK_RIGHT;

	public int up() {
		return up;
	}

	public int down() {
		return down;
	}

	public int left() {
		return left;
	}

	public int right() {
		return right;
	}

	// ----------------------------------------------------------------------------------------
	// TODO Attack
	private int attack = KeyEvent.VK_SPACE;
	private int projectile = KeyEvent.VK_C;
	private int special = KeyEvent.VK_V;
	private int npc = KeyEvent.VK_X;

	public int attack() {
		return attack;
	}

	public int projectile() {
		return projectile;
	}

	public int special() {
		return special;
	}

	public int npc() {
		return npc;
	}

	// ----------------------------------------------------------------------------------------
	// TODO Menus
	private int change = KeyEvent.VK_R;
	private int pause = KeyEvent.VK_SHIFT;
	private int levelUp = KeyEvent.VK_L;

	public int change() {
		return change;
	}

	public int pause() {
		return pause;
	}

	public int levelUp() {
		return levelUp;
	}

	// ----------------------------------------------------------------------------------------
	// TODO Set
	private transient Board owner;

	public void setValues(Board owner) {
		new PreferenceFrame(owner);
		this.owner = owner;
	}

	public void finish() {
		save(Preferences.class.getProtectionDomain().getCodeSource().getLocation().getFile().toString() + "saveFiles/" + owner.getUserName() + "/");
		owner = null;
		GameControllerRunnable.renewKeys();
	}

	public void save(String location) {

		try {
			ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(location + "preferences.ser"));
			os.writeObject(this);
			os.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	

	private class PreferenceFrame extends JDialog {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		protected static final char SHIFT = '~';
		protected static final char CTRL = '!';
		protected static final char ALT = '@';
		protected static final char ESCAPE = '#';

		protected String[] movement = { "Arrow Keys", "WASD" };
		protected String[] defualts = { "Arrow Keys tight","Arrow Keys stretched", "WASD 1" };
		protected JButton move = new JButton("Movement: " + movement[up == KeyEvent.VK_W ? 1 : 0]);
		protected JButton attackB = new JButton("Attack: " + KeyEvent.getKeyText(attack));
		protected JButton projectileB = new JButton("Projectile: " + KeyEvent.getKeyText(projectile));
		protected JButton specialB = new JButton("Special: " + KeyEvent.getKeyText(special));
		protected JButton npcB = new JButton("Talk to NPCs: " + KeyEvent.getKeyText(npc));

		protected JButton changeB = new JButton("Switch characters: " + KeyEvent.getKeyText(change));
		protected JButton pauseB = new JButton("Pause: " + KeyEvent.getKeyText(pause));
		protected JButton levelB = new JButton("Level Up Menu: " + KeyEvent.getKeyText(levelUp));
		protected JButton resetB = new JButton("Reset to Control Scheme");

		public PreferenceFrame(JComponent owner) {

			this.setSize(new Dimension(675, 200));
			this.setLayout(new BorderLayout());

			Dimension d = new Dimension(225, 200);

			JPanel pane1 = new JPanel();
			JPanel pane2 = new JPanel();
			JPanel pane3 = new JPanel();
			pane1.setLayout(new BoxLayout(pane1, BoxLayout.Y_AXIS));
			pane2.setLayout(new BoxLayout(pane2, BoxLayout.Y_AXIS));

			pane1.setPreferredSize(d);
			pane2.setPreferredSize(d);
			pane3.setPreferredSize(d);

			pane1.setBackground(Color.black);
			pane2.setBackground(Color.black);
			pane3.setBackground(Color.black);

			pane1.add(move);
			pane1.add(pauseB);
			pane1.add(levelB);
			pane1.add(changeB);

			pane2.add(attackB);
			pane2.add(projectileB);
			pane2.add(specialB);
			pane2.add(npcB);

			pane3.add(resetB);

			move.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					setMove();
					move.setText("Movement: " + movement[up == KeyEvent.VK_W ? 1 : 0]);
				}
			});
			attackB.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					char c = getValue("Attack");
					
					if (c == ESCAPE)
						return;
					
					if (!special(c))
						attack = KeyEvent.getExtendedKeyCodeForChar(c);
					else
						attack = translateKey(c);
					attackB.setText("Attack: " + KeyEvent.getKeyText(attack));
				}
			});
			projectileB.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					char c = getValue("Projectile");
					
					if (c == ESCAPE)
						return;
					
					if (!special(c))
						projectile = KeyEvent.getExtendedKeyCodeForChar(c);
					else
						projectile = translateKey(c);
					projectileB.setText("Projectile: " + KeyEvent.getKeyText(projectile));
				}
			});
			specialB.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					char c = getValue("Special");
					
					if (c == ESCAPE)
						return;
					
					if (!special(c))
						special = KeyEvent.getExtendedKeyCodeForChar(c);
					else
						special = translateKey(c);
					specialB.setText("Special: " + KeyEvent.getKeyText(special));
				}
			});
			changeB.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					char c = getValue("Switch characters");
					
					if (c == ESCAPE)
						return;
					
					if (!special(c))
						change = KeyEvent.getExtendedKeyCodeForChar(c);
					else
						change = translateKey(c);
					changeB.setText("Switch characters: " + KeyEvent.getKeyText(change));
				}
			});
			pauseB.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					char c = getValue("Pause");
					
					if (c == ESCAPE)
						return;
					
					if (!special(c))
						pause = KeyEvent.getExtendedKeyCodeForChar(c);
					else
						pause = translateKey(c);
					pauseB.setText("Pause: " + KeyEvent.getKeyText(pause));
				}
			});
			levelB.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					char c = getValue("Level Up Menu");
					
					if (c == ESCAPE)
						return;
					
					if (!special(c))
						levelUp = KeyEvent.getExtendedKeyCodeForChar(c);
					else
						levelUp = translateKey(c);
					;
					levelB.setText("Level Up Menu: " + KeyEvent.getKeyText(levelUp));
				}
			});
			npcB.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					char c = getValue("Special");
					
					if (c == ESCAPE)
						return;
					
					if (!special(c))
						npc = KeyEvent.getExtendedKeyCodeForChar(c);
					else
						npc = translateKey(c);
					npcB.setText("Talk to NPCs: " + KeyEvent.getKeyText(npc));
				}
			});

			resetB.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					setDefualt();

					move.setText("Movement: " + movement[up == KeyEvent.VK_W ? 1 : 0]);
					attackB.setText("Attack: " + KeyEvent.getKeyText(attack));
					projectileB.setText("Projectile: " + KeyEvent.getKeyText(projectile));
					specialB.setText("Special: " + KeyEvent.getKeyText(special));
					changeB.setText("Switch characters: " + KeyEvent.getKeyText(change));
					pauseB.setText("Pause: " + KeyEvent.getKeyText(pause));
					levelB.setText("Level Up Menu: " + KeyEvent.getKeyText(levelUp));
					npcB.setText("Talk to NPCs: " + KeyEvent.getKeyText(npc));
				}
			});

			add(pane1, BorderLayout.WEST);
			add(pane2, BorderLayout.EAST);
			add(pane3, BorderLayout.CENTER);

			this.addWindowListener(new WindowAdapter() {

				@Override
				public void windowClosed(WindowEvent e) {
					super.windowClosed(e);
					finish();
				}
			});

			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setLocationRelativeTo(null);
			setTitle(DigIt.NAME);
			setResizable(false);
			setFocusable(true);
			setVisible(true);
		}

		private boolean special(char c) {
			return c == CTRL || c == ALT || c == SHIFT;
		}

		private boolean special(String s) {
			return s.equalsIgnoreCase("shift") || s.equalsIgnoreCase("alt") || s.equalsIgnoreCase("ctrl") || s.equalsIgnoreCase("backspace")
					|| s.equalsIgnoreCase("space") || s.equalsIgnoreCase("enter");
		}

		private int translateKey(char c) {

			switch (c) {
			case SHIFT:
				return KeyEvent.VK_SHIFT;
			case CTRL:
				return KeyEvent.VK_CONTROL;
			case '\n':
				return KeyEvent.VK_ENTER;
			case '\b':
				return KeyEvent.VK_BACK_SPACE;
			case ' ':
				return KeyEvent.VK_SPACE;
			default:
				return KeyEvent.VK_ALT;
			}
		}

		public boolean verify(char ch, boolean isSpecial) {

			boolean toReturn;

			if (isSpecial) {
				String s = KeyEvent.getKeyText(translateKey(ch));
				System.out.println(s);
				toReturn = !s.equalsIgnoreCase(KeyEvent.getKeyText(attack)) && !s.equalsIgnoreCase(KeyEvent.getKeyText(projectile))
						&& !s.equalsIgnoreCase(KeyEvent.getKeyText(special)) && !s.equalsIgnoreCase(KeyEvent.getKeyText(npc))
						&& !s.equalsIgnoreCase(KeyEvent.getKeyText(change)) && !s.equalsIgnoreCase(KeyEvent.getKeyText(pause))
						&& !s.equalsIgnoreCase(KeyEvent.getKeyText(levelUp));
			} else
				toReturn = !(ch + "").equalsIgnoreCase(KeyEvent.getKeyText(attack)) && !(ch + "").equalsIgnoreCase(KeyEvent.getKeyText(projectile))
						&& !(ch + "").equalsIgnoreCase(KeyEvent.getKeyText(special)) && !(ch + "").equalsIgnoreCase(KeyEvent.getKeyText(npc))
						&& !(ch + "").equalsIgnoreCase(KeyEvent.getKeyText(change)) && !(ch + "").equalsIgnoreCase(KeyEvent.getKeyText(pause))
						&& !(ch + "").equalsIgnoreCase(KeyEvent.getKeyText(levelUp));

			System.out.println(KeyEvent.getKeyText(attack));

			if (!toReturn)
				Statics.showError("The key code is already taken.", owner);
			return toReturn;
		}

		public char getValue(String action) {

			String str = null;
			int i = 0;
			String s = null;

			do {
				str = (String) JOptionPane.showInputDialog(this, "Please select the type of key to continue.", DigIt.NAME, JOptionPane.PLAIN_MESSAGE,
						Statics.ICON, cases, null);
				if (str == null) {
					s = null;
					break;
				}

				for (i = 0; i < cases.length; i++)
					if (str.equals(cases[i]))
						break;

				s = (String) JOptionPane.showInputDialog(this, "Please select the key you want to be associated with the following action:\n"
						+ action, DigIt.NAME, JOptionPane.PLAIN_MESSAGE, Statics.ICON, chars[i], null);
				
				if (s == null || !verify(!special(s) ? s.charAt(0) : specialTrans(s), special(s)))
					str = null;
			} while (str == null);
			// *_
			// / \
			// *| Rare occurance of a do/while();

			if (str != null)
				if (i != 3)
					return s.charAt(0);
				else
					return specialTrans(s);

			else
				return ESCAPE;
		}

		public char specialTrans(String s) {
			switch (s) {
			case "SPACE":
				return ' ';
			case "ENTER":
				return '\n';
			case "BACKSPACE":
				return '\b';
			case "CTRL":
				return CTRL;
			case "ALT":
				return ALT;
			default:
				return SHIFT;
			}
		}

		public void setMove() {
			String val = (String) JOptionPane.showInputDialog(this,
					"Please enter the character you want to be associated with the following action:\nMovement", DigIt.NAME,
					JOptionPane.PLAIN_MESSAGE, Statics.ICON, movement, null);

			if (val != null)
				if (val.equals(movement[0])) {
					up = KeyEvent.VK_UP;
					down = KeyEvent.VK_DOWN;
					left = KeyEvent.VK_LEFT;
					right = KeyEvent.VK_RIGHT;
				} else {
					up = KeyEvent.VK_W;
					down = KeyEvent.VK_S;
					left = KeyEvent.VK_A;
					right = KeyEvent.VK_D;
				}
		}
		public void setDefualt() {
			String val = (String) JOptionPane.showInputDialog(this,
					"Please enter the character you want to be associated with the following action:\nMovement", DigIt.NAME,
					JOptionPane.PLAIN_MESSAGE, Statics.ICON, defualts, null);

			if (val != null){
				if (val.equals(defualts[0])) {
					up = KeyEvent.VK_UP;
					down = KeyEvent.VK_DOWN;
					left = KeyEvent.VK_LEFT;
					right = KeyEvent.VK_RIGHT;

					attack = KeyEvent.VK_SPACE;
					projectile = KeyEvent.VK_C;
					special = KeyEvent.VK_V;
					npc = KeyEvent.VK_X;

					change = KeyEvent.VK_R;
					pause = KeyEvent.VK_SHIFT;
					levelUp = KeyEvent.VK_L;
				} else if(val.equals(defualts[1])){
					up = KeyEvent.VK_UP;
					down = KeyEvent.VK_DOWN;
					left = KeyEvent.VK_LEFT;
					right = KeyEvent.VK_RIGHT;

					attack = KeyEvent.VK_SPACE;
					projectile = KeyEvent.VK_E;
					special = KeyEvent.VK_Q;
					npc = KeyEvent.VK_X;

					change = KeyEvent.VK_R;
					pause = KeyEvent.VK_SHIFT;
					levelUp = KeyEvent.VK_L;
				}
				else if(val.equals(defualts[2])){
					up = KeyEvent.VK_W;
					down = KeyEvent.VK_S;
					left = KeyEvent.VK_A;
					right = KeyEvent.VK_D;

					attack = KeyEvent.VK_SPACE;
					projectile = KeyEvent.VK_K;
					special = KeyEvent.VK_J;
					npc = KeyEvent.VK_X;

					change = KeyEvent.VK_R;
					pause = KeyEvent.VK_SHIFT;
					levelUp = KeyEvent.VK_L;
				}
		}
	}}

	// ----------------------------------------------------------------------------------------
	// TODO static cases used in GameCharacter

	public static int UP() {
		return Board.preferences.up();
	}

	public static int DOWN() {
		return Board.preferences.down();
	}

	public static int LEFT() {
		return Board.preferences.left();
	}

	public static int RIGHT() {
		return Board.preferences.right();
	}

	// ----------------------------------------------------------------------------------------
	public static int ATTACK() {
		return Board.preferences.attack();
	}

	public static int PROJECTILE() {
		return Board.preferences.projectile();
	}

	public static int SPECIAL() {
		return Board.preferences.special();
	}

	public static int NPC() {
		return Board.preferences.npc();
	}

	// ----------------------------------------------------------------------------------------
	public static int PAUSE() {
		return Board.preferences.pause();
	}

	public static int LEVEL_UP() {
		return Board.preferences.levelUp();
	}

	public static int CHAR_CHANGE() {
		return Board.preferences.change();
	}

	// ----------------------------------------------------------------------------------------

	public static String getControls() {
		return "Controls:\nMovement Scheme: " + (KeyEvent.getKeyText(UP()).equals("W")? "WASD" : "Arrow Keys") + "\nAttack: " + KeyEvent.getKeyText(ATTACK()) + "  |  Projectile: "
				+ KeyEvent.getKeyText(PROJECTILE()) + "  |  Special: " + KeyEvent.getKeyText(SPECIAL()) + "  |  Talk to NPCs: "
				+ KeyEvent.getKeyText(NPC()) + "\nPause: " + KeyEvent.getKeyText(PAUSE()) + "  |  Level Up Menu: " + KeyEvent.getKeyText(LEVEL_UP())
				+ "  |  Switch characters: " + KeyEvent.getKeyText(CHAR_CHANGE());
	}
}

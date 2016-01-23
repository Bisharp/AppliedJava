package com.dig.www.util;

import java.awt.event.KeyEvent;

import javax.swing.JOptionPane;

import com.dig.www.start.Board;
import com.dig.www.start.DigIt;

import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

//import com.manor.www.start.DarkManor3D;

public class GameControllerRunnable implements Runnable {

	private static DigIt rOB; // #ROBReferences
	private Component[] components;
	private Controller controller;
	private float[] offValues;
	private boolean[] buttonPressed;
	private float data;
	private int i;

	protected static int UP = Preferences.UP();
	protected static int DOWN = Preferences.DOWN();
	protected static int LEFT = Preferences.LEFT();
	protected static int RIGHT = Preferences.RIGHT();
	protected static int ATTACK = Preferences.ATTACK();
	protected static int PROJECTILE = Preferences.PROJECTILE();
	protected static int SPECIAL = Preferences.SPECIAL();
	protected static int SWITCH = Preferences.CHAR_CHANGE();
	protected static int PAUSE = Preferences.PAUSE();
	protected static int NPC = Preferences.NPC();
	protected static int ITEM = Preferences.ITEM();

	public static void renewKeys() {

		UP = Preferences.UP();
		DOWN = Preferences.DOWN();
		LEFT = Preferences.LEFT();
		RIGHT = Preferences.RIGHT();
		ATTACK = Preferences.ATTACK();
		PROJECTILE = Preferences.PROJECTILE();
		SPECIAL = Preferences.SPECIAL();
		SWITCH = Preferences.CHAR_CHANGE();
		PAUSE = Preferences.PAUSE();
		NPC = Preferences.NPC();
		ITEM = Preferences.ITEM();
	}

	protected static final int Y_STICK = 0;
	protected static final int X_STICK = 1;
	protected static final int Y2_STICK = 2;
	protected static final int X2_STICK = 3;
	protected static final int Z_AXIS = 4;
	protected static final int A = 5;
	protected static final int B = 6;
	protected static final int X = 7;
	protected static final int Y = 8;
	protected static final int LB = 9;
	protected static final int RB = 10;
	protected static final int BACK = 11;
	protected static final int START = 12;
	protected static final int STICK_PRESS = 13;
	protected static final int STICK2_PRESS = 14;
	protected static final int HAT_SWITCH = 15;
	
	protected static final int Z_AXIS_L = 16;

	protected static GameControllerPreferences p;

	private static final float Z_SENSITIVITY = 0.5f;
	private final float WALK_SENSITIVITY = 0.4f;

	// private DarkManor3D owner;

	public GameControllerRunnable(DigIt dM) {

		getController();
		rOB = dM;

		if (controller != null)
			buttonPressed = new boolean[16];

		p = new GameControllerPreferences();

		// owner = dM;
	}

	@Override
	public void run() {
		while (controller != null) {
			try {
				Thread.sleep(15);
				pollController();
			} catch (Exception ex) {
				// if (JOptionPane
				// .showConfirmDialog(
				// rOB,
				// "The game controller was disconnected. If you wish to continue playing with the game controller,\nplug it back in and select the \"yes\" option of this window.\nOtherwise, select \"no\" or close the window.",
				// "Terra Novus", JOptionPane.YES_NO_OPTION) ==
				// JOptionPane.YES_OPTION) {
				// getController();
				// } else
				break;
			}
		}
	}

	public void pollController() {
		try {
			controller.poll();
		} catch (NullPointerException ex) {

		}
		components = controller.getComponents();

		for (i = 0; i < components.length; i++) {

			data = components[i].getPollData();

			// Checks buttons for changes since the last check

			if (data != offValues[i]) {

				// This code checks for the control stick's changes

				if (((i == p.moveX || i == p.moveY) && !p.isDPad) || (i == HAT_SWITCH && p.isDPad)) {
					if (!p.isDPad)
						handleStick();
					else
						handleDPad();
				}

				// TODO Attack
				else if (i == p.attack) {
					handleButton(ATTACK, 5, isZAxis(p.attack), p.attack == p.rZAxis);
				}

				// TODO Projectile
				else if (i == p.projectile) {
					handleButton(PROJECTILE, 6, isZAxis(p.projectile), p.projectile == p.rZAxis);
				}

				// TODO Special
				else if (i == p.special) {
					handleButton(SPECIAL, 7, isZAxis(p.special), p.special == p.rZAxis);
				}

				// TODO Pause
				else if (i == p.pause) {
					handleButton(PAUSE, 8, isZAxis(p.special), p.special == p.rZAxis);
				}

				// TODO LevelUp menu
				else if (i == p.switchC) {
					handleButton(SWITCH, 9, isZAxis(p.switchC), p.switchC == p.rZAxis);
				}

				// TODO Talk to NPC
				else if (i == p.npc) {
					handleButton(NPC, 10, isZAxis(p.npc), p.npc == p.rZAxis);
				}

				// TODO Talk to NPC
				else if (i == p.item) {
					handleButton(ITEM, 11, isZAxis(p.item), p.item == p.rZAxis);
				}
			}
		}

		for (int i = 0; i < components.length; i++)
			offValues[i] = components[i].getPollData();

		// } catch (NullPointerException ex) {
		//
		// }
	}
	
	protected boolean isZAxis(int check) {
		return check == Z_AXIS || check == Z_AXIS_L;
	}

	protected void handleButton(int press, int index, boolean isZAxis, boolean rightAxis) {

		if (!isZAxis) {
			if (data > 0) {
				rOB.keyPress(press);
				buttonPressed[index] = true;
			} else if (buttonPressed[index]) {
				rOB.keyRelease(press);
				buttonPressed[6] = false;
			}
		} else {
			if (data < -Z_SENSITIVITY) {
				rOB.keyPress(press);
				buttonPressed[index] = true;
			} else if (data > Z_SENSITIVITY) {
				rOB.keyPress(p.lZAxis);
				buttonPressed[12] = true;
			} else if (buttonPressed[index]) {
				rOB.keyRelease(press);
				buttonPressed[index] = false;
			}
		}
	}

	protected void handleStick() {
		// TODO Code run if the control stick is pressed in the Y axis
		if (i == p.moveY) {

			// Walk

			if (data > WALK_SENSITIVITY) {
				rOB.keyPress(DOWN);
				buttonPressed[0] = true;
			} else if (data < -WALK_SENSITIVITY) {
				rOB.keyPress(UP);
				buttonPressed[1] = true;

				// keyRelease

			} else if (data < WALK_SENSITIVITY && data > -WALK_SENSITIVITY) {
				if (buttonPressed[0]) {
					rOB.keyRelease(DOWN);
					buttonPressed[0] = false;
				} else if (buttonPressed[1]) {
					rOB.keyRelease(UP);
					buttonPressed[1] = false;
				}
			}
		}

		// TODO Code run if the control stick is pressed in the X axis
		else if (i == p.moveX) {

			// walk

			if (data > WALK_SENSITIVITY) {
				rOB.keyPress(RIGHT);
				buttonPressed[2] = true;
			} else if (data < -WALK_SENSITIVITY) {
				rOB.keyPress(LEFT);
				buttonPressed[3] = true;

				// keyRelease

			} else if (data < WALK_SENSITIVITY && data > -WALK_SENSITIVITY) {
				if (buttonPressed[2]) {
					rOB.keyRelease(RIGHT);
					buttonPressed[2] = false;
				} else if (buttonPressed[3]) {
					rOB.keyRelease(LEFT);
					buttonPressed[3] = false;
				}
			}
		}
	}

	protected int[] dPadDirs = new int[] { DOWN, UP, RIGHT, LEFT };

	protected void handleDPad() {
		int padValue = (int) (components[HAT_SWITCH].getPollData() * 1000);
		switch (padValue) {
		case 1000:
			rOB.keyPress(LEFT);
			buttonPressed[3] = true;
			break;
		case 125:
			rOB.keyPress(UP);
			buttonPressed[1] = true;
			rOB.keyPress(LEFT);
			buttonPressed[3] = true;
			break;
		case 250:
			rOB.keyPress(UP);
			buttonPressed[1] = true;
			break;
		case 375:
			rOB.keyPress(UP);
			buttonPressed[1] = true;
			rOB.keyPress(RIGHT);
			buttonPressed[2] = true;
			break;
		case 500:
			rOB.keyPress(RIGHT);
			buttonPressed[2] = true;
			break;
		case 625:
			rOB.keyPress(RIGHT);
			buttonPressed[2] = true;
			rOB.keyPress(DOWN);
			buttonPressed[0] = true;
			break;
		case 750:
			rOB.keyPress(DOWN);
			buttonPressed[0] = true;
			break;
		case 875:
			rOB.keyPress(LEFT);
			buttonPressed[3] = true;
			rOB.keyPress(DOWN);
			buttonPressed[0] = true;
			break;
		default:
			for (int i = 0; i < 4; i++)
				if (buttonPressed[i]) {
					rOB.keyRelease(dPadDirs[i]);
					buttonPressed[i] = false;
				}
		}
	}

	public void getController() {
		ControllerEnvironment ce = ControllerEnvironment.getDefaultEnvironment();
		// retrieve the available controllers
		Controller[] controllers = ce.getControllers();

		// fetch gamepad controller
		controller = null;
		for (Controller c : controllers) {
			if (c.getType() == Controller.Type.GAMEPAD) {
				controller = c;
				break;
			}
		}

		// none found
		if (controller == null) {
			System.out.println("Gamepad controller not found ");
		} else {
			Component[] components = controller.getComponents();
			offValues = new float[components.length];
			controller.poll();
			for (int i = 0; i < components.length; i++) {
				offValues[i] = components[i].getPollData();
			}
		}
	}
	
	public GameControllerPreferences getP() {
		return p;
	}
}

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

	private static int UP = Preferences.UP();
	private static int DOWN = Preferences.DOWN();
	private static int LEFT = Preferences.LEFT();
	private static int RIGHT = Preferences.RIGHT();
	private static int ATTACK = Preferences.ATTACK();
	private static int PROJECTILE = Preferences.PROJECTILE();
	private static int SPECIAL = Preferences.SPECIAL();
	private static int LEVEL_UP_MENU = Preferences.ITEM();
	private static int PAUSE = Preferences.PAUSE();
	private static int NPC = Preferences.NPC();

	public static void renewKeys() {

		UP = Preferences.UP();
		DOWN = Preferences.DOWN();
		LEFT = Preferences.LEFT();
		RIGHT = Preferences.RIGHT();
		ATTACK = Preferences.ATTACK();
		PROJECTILE = Preferences.PROJECTILE();
		SPECIAL = Preferences.SPECIAL();
		LEVEL_UP_MENU = Preferences.ITEM();
		PAUSE = Preferences.PAUSE();
		NPC = Preferences.NPC();
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

	protected static GameControllerPreferences p;

	private static final float Z_SENSITIVITY = 0.7f;
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
					if (p.isDPad)
						handleStick();
					else
						handleDPad();
				}

				// TODO Attack
				else if (i == A) {
					if (data > 0) {
						rOB.keyPress(ATTACK);
						buttonPressed[6] = true;
					} else if (buttonPressed[6]) {
						rOB.keyRelease(ATTACK);
						buttonPressed[6] = false;
					}
				}

				// TODO Projectile
				else if (i == Z_AXIS) {
					if (data < -Z_SENSITIVITY) {
						rOB.keyPress(PROJECTILE);
						buttonPressed[7] = true;
					} else if (buttonPressed[7]) {
						rOB.keyRelease(PROJECTILE);
						buttonPressed[7] = false;
					}
				}

				// TODO Special
				else if (i == B) {
					if (data > 0) {
						rOB.keyPress(SPECIAL);
						buttonPressed[7] = true;
					} else if (buttonPressed[7]) {
						rOB.keyRelease(SPECIAL);
						buttonPressed[7] = false;
					}
				}

				// TODO Pause
				else if (i == BACK) {
					if (data > 0) {
						rOB.keyPress(PAUSE);
						buttonPressed[8] = true;
					} else if (buttonPressed[8]) {
						rOB.keyRelease(PAUSE);
						buttonPressed[8] = false;
					}
				}

				// TODO LevelUp menu
				else if (i == START) {
					if (data > 0) {
						rOB.keyPress(LEVEL_UP_MENU);
						buttonPressed[8] = true;
					} else if (buttonPressed[8]) {
						rOB.keyRelease(LEVEL_UP_MENU);
						buttonPressed[8] = false;
					}
				}

				// TODO Talk to NPC
				else if (i == X) {
					if (data > 0) {
						rOB.keyPress(NPC);
						buttonPressed[8] = true;
					} else if (buttonPressed[8]) {
						rOB.keyRelease(NPC);
						buttonPressed[8] = false;
					}
				}
			}
		}

		for (int i = 0; i < components.length; i++)
			offValues[i] = components[i].getPollData();

		// } catch (NullPointerException ex) {
		//
		// }
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
	
	protected int[] dPadDirs = new int[]{ DOWN, UP, RIGHT, LEFT };

	protected void handleDPad() {
		int padValue = (int) (components[15].getPollData() * 1000);
		switch (padValue) {
		case 1000:
			rOB.keyPress(UP);
			buttonPressed[1] = true;
			break;
		case 125:
			rOB.keyPress(UP);
			buttonPressed[1] = true;
			rOB.keyPress(LEFT);
			buttonPressed[3] = true;
			break;
		case 250:
			rOB.keyPress(LEFT);
			buttonPressed[3] = true;
			break;
		case 375:
			rOB.keyPress(UP);
			buttonPressed[1] = true;
			rOB.keyPress(RIGHT);
			buttonPressed[2] = true;
			break;
		case 500:
			rOB.keyPress(DOWN);
			buttonPressed[0] = true;
			break;
		case 625:
			rOB.keyPress(RIGHT);
			buttonPressed[2] = true;
			rOB.keyPress(DOWN);
			buttonPressed[0] = true;
			break;
		case 750:
			rOB.keyPress(RIGHT);
			buttonPressed[2] = true;
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
}

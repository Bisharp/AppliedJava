package com.dig.www.util;

import java.awt.event.KeyEvent;

import javax.swing.JOptionPane;

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
	
	private static final int ATTACK = KeyEvent.VK_SPACE;
	private static final int PROJECTILE = KeyEvent.VK_C;
	private static final int SPECIAL = KeyEvent.VK_V;
	private static final int LEVEL_UP_MENU = KeyEvent.VK_L;
	private static final int PAUSE = KeyEvent.VK_SHIFT;
	

	private static final int Y_STICK = 0;
	private static final int X_STICK = 1;
	private static final int Y2_STICK = 2;
	private static final int X2_STICK = 3;
	private static final int Z_AXIS = 4;
	private static final int A = 5;
	private static final int B = 6;
	private static final int X = 7;
	private static final int Y = 8;
	private static final int LB = 9;
	private static final int RB = 10;
	private static final int BACK = 11;
	private static final int START = 12;
	private static final int STICK_PRESS = 13;
	private static final int STICK2_PRESS = 14;
	private static final int HAT_SWITCH = 15;

	private static final float Z_SENSITIVITY = 0.7f;
	private final float WALK_SENSITIVITY = 0.4f;

	// private DarkManor3D owner;

	public GameControllerRunnable(DigIt dM) {

		getController();
		rOB = dM;

		if (controller != null)
			buttonPressed = new boolean[16];

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

				// TODO Code run if the control stick is pressed in the Y axis
				if (i == Y_STICK) {

					// Walk

					if (data > WALK_SENSITIVITY) {
						rOB.keyPress(KeyEvent.VK_DOWN);
						buttonPressed[0] = true;
					} else if (data < -WALK_SENSITIVITY) {
						rOB.keyPress(KeyEvent.VK_UP);
						buttonPressed[1] = true;

						// keyRelease

					} else if (data < WALK_SENSITIVITY
							&& data > -WALK_SENSITIVITY) {
						if (buttonPressed[0]) {
							rOB.keyRelease(KeyEvent.VK_DOWN);
							buttonPressed[0] = false;
						} else if (buttonPressed[1]) {
							rOB.keyRelease(KeyEvent.VK_UP);
							buttonPressed[1] = false;
						}
					}
				}

				// TODO Code run if the control stick is pressed in the X axis
				else if (i == X_STICK) {

					// walk

					if (data > WALK_SENSITIVITY) {
						rOB.keyPress(KeyEvent.VK_RIGHT);
						buttonPressed[2] = true;
					} else if (data < -WALK_SENSITIVITY) {
						rOB.keyPress(KeyEvent.VK_LEFT);
						buttonPressed[3] = true;

						// keyRelease

					} else if (data < WALK_SENSITIVITY
							&& data > -WALK_SENSITIVITY) {
						if (buttonPressed[2]) {
							rOB.keyRelease(KeyEvent.VK_RIGHT);
							buttonPressed[2] = false;
						} else if (buttonPressed[3]) {
							rOB.keyRelease(KeyEvent.VK_LEFT);
							buttonPressed[3] = false;
						}
					}
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
			}
		}

		for (int i = 0; i < components.length; i++)
			offValues[i] = components[i].getPollData();

		// } catch (NullPointerException ex) {
		//
		// }
	}

	public void getController() {
		ControllerEnvironment ce = ControllerEnvironment
				.getDefaultEnvironment();
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

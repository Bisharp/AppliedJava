package com.dig.www.util;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
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
		synchControllerPrefs();
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

	protected static int[] nums = new int[] { Z_AXIS, A, B, X, Y, LB, RB, BACK, START, STICK_PRESS, STICK2_PRESS, Z_AXIS_L };

	protected static GameControllerPreferences p;

	protected static final float Z_SENSITIVITY = 0.5f;
	protected static final float WALK_SENSITIVITY = 0.4f;

	public GameControllerRunnable(DigIt dM) {

		if (getController()) {
			rOB = dM;
			if (controller != null)
				buttonPressed = new boolean[16];
			renewKeys();
		} else
			dM.nullCThread();
	}
	
	public static void synchControllerPrefs() {
		p = Board.preferences.getGCP();
	}

	@Override
	public void run() {
		while (controller != null)
			try {
				Thread.sleep(15);
				pollController();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
	}

	public void pollController() {
		try {
			controller.poll();
			components = controller.getComponents();

			for (i = 0; i < components.length; i++) {

				data = components[i].getPollData();

				// Checks buttons for changes since the last check
				// TODO mouse
				if (((i == p.mouseX || i == p.mouseY) && !p.mouseDPad) || (i == HAT_SWITCH && p.mouseDPad)) {
					if (!p.mouseDPad)
						handleSMouse();
					else
						handleDMouse();
				}

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
						handleButton(ATTACK, 5, isZAxis(p.attack), p.attack == p.rZAxis, -1);
					}

					// TODO Projectile
					else if (i == p.projectile) {
						handleButton(PROJECTILE, 6, isZAxis(p.projectile), p.projectile == p.rZAxis, -1);
					}

					// TODO Special
					else if (i == p.special) {
						handleButton(SPECIAL, 7, isZAxis(p.special), p.special == p.rZAxis, -1);
					}

					// TODO Pause
					else if (i == p.pause) {
						handleButton(PAUSE, 8, isZAxis(p.special), p.special == p.rZAxis, -1);
					}

					// TODO LevelUp menu
					else if (i == p.switchC) {
						handleButton(SWITCH, 9, isZAxis(p.switchC), p.switchC == p.rZAxis, -1);
					}

					// TODO Talk to NPC
					else if (i == p.npc) {
						handleButton(NPC, 10, isZAxis(p.npc), p.npc == p.rZAxis, -1);
					}

					// TODO Talk to NPC
					else if (i == p.item) {
						handleButton(ITEM, 11, isZAxis(p.item), p.item == p.rZAxis, -1);
					}

					// TODO Click the Mouse
					else if (i == p.mouseClick)
						handleButton(0, 12, isZAxis(p.mouseClick), p.mouseClick == p.rZAxis, InputEvent.BUTTON1_MASK);
				}
			}

			for (int i = 0; i < components.length; i++)
				offValues[i] = components[i].getPollData();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	protected boolean isZAxis(int check) {
		return check == Z_AXIS || check == Z_AXIS_L;
	}

	protected void handleButton(int press, int index, boolean isZAxis, boolean rightAxis, int mouseClick) {

		if (!isZAxis) {
			if (data > 0) {

				if (mouseClick == -1)
					rOB.keyPress(press);
				else
					robot.mousePress(mouseClick);

				buttonPressed[index] = true;
			} else if (buttonPressed[index]) {

				if (mouseClick == -1)
					rOB.keyRelease(press);
				else
					robot.mouseRelease(mouseClick);

				buttonPressed[6] = false;
			}
		} else {
			if (data < -p.zSensitivity) {
				if (mouseClick == -1)
					rOB.keyPress(press);
				else
					robot.mousePress(mouseClick);
				buttonPressed[index] = true;
			} else if (data > p.zSensitivity) {
				if (mouseClick == -1)
					rOB.keyPress(p.lZAxis);
				else
					robot.mousePress(mouseClick);
				buttonPressed[12] = true;
			} else if (buttonPressed[index]) {
				if (mouseClick == -1)
					rOB.keyRelease(press);
				else
					robot.mouseRelease(mouseClick);
				buttonPressed[index] = false;
			}
		}
	}

	protected void handleStick() {
		// TODO Code run if the control stick is pressed in the Y axis
		if (i == p.moveY) {
			// Walk

			if (data > p.walkSensitivity) {
				rOB.keyPress(DOWN);
				buttonPressed[0] = true;
			} else if (data < -p.walkSensitivity) {
				rOB.keyPress(UP);
				buttonPressed[1] = true;

				// keyRelease

			} else if (data < p.walkSensitivity && data > -p.walkSensitivity) {
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

			if (data > p.walkSensitivity) {
				rOB.keyPress(RIGHT);
				buttonPressed[2] = true;
			} else if (data < -p.walkSensitivity) {
				rOB.keyPress(LEFT);
				buttonPressed[3] = true;

				// keyRelease

			} else if (data < p.walkSensitivity && data > -p.walkSensitivity) {
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

	protected void releaseKeys() {
		for (int i = 0; i < 4; i++)
			if (buttonPressed[i]) {
				rOB.keyRelease(dPadDirs[i]);
				buttonPressed[i] = false;
			}
	}

	protected void releaseKeys(int preserve0, int preserve1) {
		for (int i = 0; i < 4; i++)
			if (buttonPressed[i] && i != preserve0 && i != preserve1) {
				rOB.keyRelease(dPadDirs[i]);
				buttonPressed[i] = false;
			}
	}

	protected void handleDPad() {
		int padValue = (int) (components[HAT_SWITCH].getPollData() * 1000);
		switch (padValue) {
		case 1000:
			releaseKeys();
			rOB.keyPress(LEFT);
			buttonPressed[3] = true;
			break;
		case 125:
			releaseKeys(1, 3);
			if (!buttonPressed[1]) {
				rOB.keyPress(UP);
				buttonPressed[1] = true;
			}
			if (!buttonPressed[3]) {
				rOB.keyPress(LEFT);
				buttonPressed[3] = true;
			}
			break;
		case 250:
			releaseKeys();
			rOB.keyPress(UP);
			buttonPressed[1] = true;
			break;
		case 375:
			releaseKeys(1, 2);
			if (!buttonPressed[1]) {
				rOB.keyPress(UP);
				buttonPressed[1] = true;
			}
			if (!buttonPressed[2]) {
				rOB.keyPress(RIGHT);
				buttonPressed[2] = true;
			}
			break;
		case 500:
			releaseKeys();
			rOB.keyPress(RIGHT);
			buttonPressed[2] = true;
			break;
		case 625:
			releaseKeys(2, 0);
			if (!buttonPressed[2]) {
				rOB.keyPress(RIGHT);
				buttonPressed[2] = true;
			}
			if (!buttonPressed[1]) {
				rOB.keyPress(DOWN);
				buttonPressed[0] = true;
			}
			break;
		case 750:
			releaseKeys();
			rOB.keyPress(DOWN);
			buttonPressed[0] = true;
			break;
		case 875:
			releaseKeys(3, 0);
			if (!buttonPressed[3]) {
				rOB.keyPress(LEFT);
				buttonPressed[3] = true;
			}
			if (!buttonPressed[0]) {
				rOB.keyPress(DOWN);
				buttonPressed[0] = true;
			}
			break;
		default:
			releaseKeys();
		}
	}

	protected float xAmount = 0, yAmount = 0;
	protected static final float diag = 0.71f;
	protected static Robot robot;
	static {
		try {
			robot = new Robot();
		} catch (Exception ohNo) {
			ohNo.printStackTrace();
		}
	}

	protected void handleSMouse() {
		// TODO Code run if the control stick is pressed in the Y axis
		Point mouse = MouseInfo.getPointerInfo().getLocation();

		boolean move = false;

		if (i == p.mouseY) {
			if (data > p.mouseSensitivity || data < -p.mouseSensitivity) {
				yAmount = data;
				move = true;
			} else
				yAmount = 0;
		} else if (i == p.mouseX) {
			if (data > p.mouseSensitivity || data < -p.mouseSensitivity) {
				xAmount = data;
				move = true;
			} else
				xAmount = 0;
		}

		if (move)
			robot.mouseMove(mouse.x + (int) (xAmount * p.mouseSpeed), mouse.y + (int) (yAmount * p.mouseSpeed));
	}

	protected void handleDMouse() {
		if (data == 0.125) {
			xAmount = -diag;
			yAmount = -diag;
		} else if (data == 0.25) {
			yAmount = -1;
		} else if (data == 0.375) {
			xAmount = diag;
			yAmount = -diag;
		} else if (data == 0.5) {
			xAmount = 1;
		} else if (data == 0.625) {
			xAmount = diag;
			yAmount = diag;
		} else if (data == 0.75) {
			yAmount = 1;
		} else if (data == 0.875) {
			xAmount = -diag;
			yAmount = diag;
		} else if (data == 1) {
			xAmount = -diag;
		}
	}

	public boolean getController() {
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
			return false;
		} else {
			Component[] components = controller.getComponents();
			offValues = new float[components.length];
			controller.poll();
			for (int i = 0; i < components.length; i++) {
				offValues[i] = components[i].getPollData();
			}

			return true;
		}
	}

	public GameControllerPreferences getPreferences() {
		return p;
	}
}

package com.dig.www.util;

import java.io.Serializable;

public class GameControllerPreferences implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected int moveX = GameControllerRunnable.X_STICK;
	protected int moveY = GameControllerRunnable.Y_STICK;
	protected boolean isDPad = false;
	
	protected int attack = GameControllerRunnable.A;
	protected int projectile = GameControllerRunnable.Z_AXIS;
	protected int special = GameControllerRunnable.B;
	protected int pause = GameControllerRunnable.BACK;
	protected int switchC = GameControllerRunnable.START;
	protected int npc = GameControllerRunnable.X;
	protected int item = GameControllerRunnable.Z_AXIS;
	
	protected int rZAxis = item;
	protected int lZAxis = GameControllerRunnable.ITEM;
}

package com.dig.www.util;

import java.io.Serializable;

public class GameControllerPreferences implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected int moveX = GameControllerRunnable.X_STICK;
	protected int moveY = GameControllerRunnable.Y_STICK;
	protected boolean isDPad = true;
	
}

package com.dig.www.start;

import javax.swing.JPanel;

public abstract class MPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public abstract void keyPress(int keyCode);

	public abstract void keyRelease(int keyCode);
}

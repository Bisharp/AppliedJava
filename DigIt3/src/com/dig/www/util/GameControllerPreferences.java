package com.dig.www.util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Serializable;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.dig.www.start.DigIt;

public class GameControllerPreferences implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected int moveX = GameControllerRunnable.X_STICK;
	protected int moveY = GameControllerRunnable.Y_STICK;
	protected boolean isDPad = false;
	
	protected int mouseX = GameControllerRunnable.X2_STICK;
	protected int mouseY = GameControllerRunnable.Y2_STICK;
	protected boolean mouseDPad = false;
	protected int mouseSpeed = 7;
	protected float mouseSensitivity = .25f;
	protected int mouseClick = GameControllerRunnable.STICK2_PRESS;

	protected int attack = GameControllerRunnable.A;
	protected int projectile = GameControllerRunnable.Z_AXIS;
	protected int special = GameControllerRunnable.B;
	protected int pause = GameControllerRunnable.BACK;
	protected int switchC = GameControllerRunnable.START;
	protected int npc = GameControllerRunnable.X;
	protected int item = GameControllerRunnable.Z_AXIS;

	protected int rZAxis = item;
	protected int lZAxis = GameControllerRunnable.ITEM;

	protected float zSensitivity = GameControllerRunnable.Z_SENSITIVITY;
	protected float walkSensitivity = GameControllerRunnable.WALK_SENSITIVITY;

	protected String getMovementScheme() {
		if (isDPad)
			return "D-Pad";
		else
			return moveX == GameControllerRunnable.X_STICK ? "Left Stick" : "Right Stick";
	}
	
	public void setValues() {
		new SetValues();
	}

	public class SetValues extends JDialog {

		protected JButton atkB = new JButton("Attack: " + trans(attack));
		protected JButton projB = new JButton("Projectile: " + trans(projectile));
		protected JButton specB = new JButton("Special: " + trans(special));
		protected JButton moveB = new JButton("Movement scheme: " + getMovementScheme());

		protected JButton pseB = new JButton("Pause: " + trans(pause));
		protected JButton swchB = new JButton("Switch characters: " + trans(switchC));
		protected JButton tlkB = new JButton("Talk to NPCs: " + trans(npc));
		protected JButton itmB = new JButton("Use items: " + trans(item));

		protected JSlider moveS = new JSlider(JSlider.HORIZONTAL, 2, 10, (int) (walkSensitivity * 10) > 10? 10 : (int) (walkSensitivity * 10));
		protected JSlider zS = new JSlider(JSlider.HORIZONTAL, 2, 10, (int) (zSensitivity * 10) > 10? 10 : (int) (zSensitivity * 10));

		protected JButton reset = new JButton("Reset to Defaults");

		public SetValues() {
			
			initiateSlider(moveS);
			initiateSlider(zS);
			
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

			pane1.add(moveB);
			pane1.add(pseB);
			pane1.add(swchB);
			pane1.add(itmB);

			pane2.add(atkB);
			pane2.add(projB);
			pane2.add(specB);
			pane2.add(tlkB);

			pane3.add(reset);
			pane3.add(moveS);
			pane3.add(zS);

			add(pane1, BorderLayout.WEST);
			add(pane2, BorderLayout.EAST);
			add(pane3, BorderLayout.CENTER);

//			this.addWindowListener(new WindowAdapter() {
//
//				@Override
//				public void windowClosed(WindowEvent e) {
//					super.windowClosed(e);
//					finish();
//				}
//			});

			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setLocationRelativeTo(null);
			setTitle(DigIt.NAME);
			setResizable(false);
			setFocusable(true);
			setVisible(true);
		}

		public void initiateSlider(JSlider framesPerSecond) {
			framesPerSecond.addChangeListener(new Listener());

			// Turn on labels at major tick marks.

			framesPerSecond.setMajorTickSpacing(10);
			framesPerSecond.setMinorTickSpacing(1);
			framesPerSecond.setPaintTicks(true);
			framesPerSecond.setPaintLabels(true);
			framesPerSecond.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		}

		protected class Listener implements ChangeListener {

			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				if (!source.getValueIsAdjusting())
					if (source == zS)
						zSensitivity = source.getValue() / 10;
					else
						walkSensitivity = source.getValue() / 10;
			}
		}
	}

	protected int[] nums = GameControllerRunnable.nums;
	protected String[] names = new String[] { "Left Trigger", "A Button", "B Button", "X Button", "Y Button", "Left Bumper", "Right Bumper", "Back",
			"Start", "Left Stick Press", "Right Stick Press", "Left Trigger" };

	protected String trans(int data) {
		for (int i = 0; i < nums.length; i++)
			if (nums[i] == data)
				return names[i];
		return "Unassigned";
	}
}

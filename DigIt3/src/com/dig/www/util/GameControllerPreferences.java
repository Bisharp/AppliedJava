package com.dig.www.util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Serializable;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
	protected int item = GameControllerRunnable.Z_AXIS_L;

	protected int rZAxis = projectile;
	protected int lZAxis = item;

	protected float zSensitivity = GameControllerRunnable.Z_SENSITIVITY;
	protected float walkSensitivity = GameControllerRunnable.WALK_SENSITIVITY;

	protected String getMovementScheme() {
		if (isDPad)
			return "D-Pad";
		else
			return moveX == GameControllerRunnable.X_STICK ? "Left Stick" : "Right Stick";
	}
	protected String getMouseScheme() {
		if (mouseDPad)
			return "D-Pad";
		else
			return mouseX == GameControllerRunnable.X_STICK ? "Left Stick" : "Right Stick";
	}

	public void setValues() {
		new SetValues();
	}

	public class SetValues extends JDialog {

		protected JButton atkB = new JButton("Attack: " + trans(attack));
		protected JButton projB = new JButton("Projectile: " + trans(projectile));
		protected JButton specB = new JButton("Special: " + trans(special));
		protected JButton moveB = new JButton("Movement scheme: " + getMovementScheme());
		protected JButton mouseB = new JButton("Mouse scheme: " + getMouseScheme());
		protected JButton clickB = new JButton("Mouse Click: " + trans(mouseClick));

		protected JButton pseB = new JButton("Pause: " + trans(pause));
		protected JButton swchB = new JButton("Switch characters: " + trans(switchC));
		protected JButton tlkB = new JButton("Talk to NPCs: " + trans(npc));
		protected JButton itmB = new JButton("Use items: " + trans(item));

		protected JSlider moveS = new JSlider(JSlider.HORIZONTAL, 2, 10, getInitialValue(walkSensitivity));
		protected JSlider zS = new JSlider(JSlider.HORIZONTAL, 2, 10, getInitialValue(zSensitivity));
		protected JSlider mouseS = new JSlider(JSlider.HORIZONTAL, 2, 10, getInitialValue(mouseSensitivity));

		protected JButton reset = new JButton("Reset to Defaults");

		protected boolean allSet = true;
		
		protected int getInitialValue(float sensitivity) {
			return (int) (sensitivity * 10) > 10 ? 10
					: (int) (sensitivity * 10) < 2 ? 2 : 10 - (int) (sensitivity * 10);
		}

		public SetValues() {

//			initiateSlider(moveS);
//			initiateSlider(zS);
//			initiateSlider(mouseS);

			this.setSize(new Dimension(675, 300));
			this.setLayout(new BorderLayout());

			//Dimension d = new Dimension(225, 200);

			JPanel pane1 = new JPanel();
			JPanel pane2 = new JPanel();
			JPanel pane3 = new JPanel();
			pane1.setLayout(new BoxLayout(pane1, BoxLayout.Y_AXIS));
			pane2.setLayout(new BoxLayout(pane2, BoxLayout.Y_AXIS));

			pseB.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					pause = getButton("Pause", pause);
					pseB.setText("Pause: " + trans(pause));
				}
			});
			itmB.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					item = getButton("Use items", item);
					itmB.setText("Use items: " + trans(item));
				}
			});
			swchB.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					switchC = getButton("Switch characters", switchC);
					swchB.setText("Switch characters: " + trans(switchC));
				}
			});
			atkB.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					attack = getButton("Attack", attack);
					atkB.setText("Attack: " + trans(attack));
				}
			});
			projB.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					projectile = getButton("Projectile", projectile);
					projB.setText("Projectile: " + trans(projectile));
				}
			});
			specB.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					special = getButton("Special", special);
					specB.setText("Special: " + trans(special));
				}
			});
			tlkB.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					npc = getButton("Talk to NPCs", npc);
					tlkB.setText("Talk to NPCs: " + trans(npc));
				}
			});
			moveB.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {

					try {
						setMovementScheme((JButton) arg0.getSource());
						((JButton) arg0.getSource()).setText("Movement scheme: " + getMovementScheme());
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, ex.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			});
			mouseB.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {

					try {
						setMovementScheme((JButton) arg0.getSource());
						((JButton) arg0.getSource()).setText("Mouse scheme: " + getMouseScheme());
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, ex.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			});

//			pane1.setPreferredSize(d);
//			pane2.setPreferredSize(d);
//			pane3.setPreferredSize(d);

			pane1.setBackground(Color.black);
			pane2.setBackground(Color.black);
			pane3.setBackground(Color.black);

			pane1.add(moveB);
			pane1.add(mouseB);
			pane1.add(pseB);
			pane1.add(swchB);
			pane1.add(itmB);

			pane2.add(atkB);
			pane2.add(projB);
			pane2.add(specB);
			pane2.add(tlkB);

			pane3.add(reset);
			pane3.add(new JLabel("Walk Sensitivity:"));
			pane3.add(moveS);
			pane3.add(new JLabel("Mouse Sensitivity:"));
			pane3.add(mouseS);
			pane3.add(new JLabel("Z-Axis Sensitivity:"));
			pane3.add(zS);

			add(pane1, BorderLayout.WEST);
			add(pane2, BorderLayout.EAST);
			add(pane3, BorderLayout.CENTER);

			this.addWindowListener(new WindowAdapter() {

				@Override
				public void windowClosing(WindowEvent e) {

					super.windowClosed(e);
				}
			});

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
			framesPerSecond.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		}

		protected final String[] movementSchemes = new String[] { "Left Stick", "Right Stick", "D-Pad" };

		protected void setMovementScheme(JButton source) throws Exception {
			String s = (String) JOptionPane.showInputDialog(this, "Please select the directional inpput for the following action:\n", DigIt.NAME, JOptionPane.PLAIN_MESSAGE,
					Statics.ICON, movementSchemes, null);

			if (s.equals(movementSchemes[0])) {
				if ((source == moveB && mouseX == GameControllerRunnable.X_STICK) || (source == mouseB && moveX == GameControllerRunnable.X_STICK))
					throw new Exception("The stick is already taken.");
				// else if (source == mouseB && moveX ==
				// GameControllerRunnable.X)
				// throw new Exception("The stick is already taken.");

				if (source == moveB) {
					moveX = GameControllerRunnable.X_STICK;
					moveY = GameControllerRunnable.Y_STICK;
					isDPad = false;
				} else if (source == mouseB) {
					mouseX = GameControllerRunnable.X_STICK;
					mouseY = GameControllerRunnable.Y_STICK;
					mouseDPad = false;
				}
			} else if (s.equals(movementSchemes[1])) {
				if ((source == moveB && mouseX == GameControllerRunnable.X2_STICK) || (source == mouseB && moveX == GameControllerRunnable.X2_STICK))
					throw new Exception("The stick is already taken.");
				// else if (source == mouseB && moveX ==
				// GameControllerRunnable.X)
				// throw new Exception("The stick is already taken.");

				if (source == moveB) {
					moveX = GameControllerRunnable.X2_STICK;
					moveY = GameControllerRunnable.Y2_STICK;
					isDPad = false;
				} else if (source == mouseB) {
					mouseX = GameControllerRunnable.X2_STICK;
					mouseY = GameControllerRunnable.Y2_STICK;
					mouseDPad = false;
				}
			} else if (s.equals(movementSchemes[2])) {
				if ((source == moveB && mouseDPad) || (source == mouseB && isDPad))
					throw new Exception("The d-pad is already taken.");
				// else if (source == mouseB && moveX ==
				// GameControllerRunnable.X)
				// throw new Exception("The d-pad is already taken.");

				if (source == moveB) {
					moveX = -1;
					moveY = -1;
					isDPad = true;
				} else if (source == mouseB) {
					mouseX = -1;
					mouseY = -1;
					mouseDPad = true;
				}
			}

		}

		protected int getButton(String action, int orig) {
			String s = null;

			s = (String) JOptionPane.showInputDialog(this, "Please select the key you want to be associated with the following action:\n" + action,
					DigIt.NAME, JOptionPane.PLAIN_MESSAGE, Statics.ICON, names, null);

			if (s == null)
				return orig;
			else
				try {
					return translate(s, orig);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					return orig;
				}
		}

		int[] verCheck;

		protected int translate(String s, int orig) throws Exception {

			int verify = -1;
			for (int i = 0; i < names.length; i++)
				if (names[i].equals(s)) {
					verify = nums[i];
					break;
				}

			verCheck = new int[] { mouseClick, attack, projectile, special, npc, pause, switchC, item, rZAxis, lZAxis };
			for (int i : verCheck)
				if (verify == i) {
					throw new Exception("The button is already taken.");
				}

			if (orig == lZAxis)
				lZAxis = -1;
			else if (orig == rZAxis)
				rZAxis = -1;

			if (verify == lZAxis)
				lZAxis = verify;
			else if (verify == rZAxis)
				rZAxis = verify;

			return verify;
		}

		protected class Listener implements ChangeListener {

			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				
				if (!source.getValueIsAdjusting()) {
					
					final int sense = 1 - source.getValue() / 10;
					System.out.println(sense);
					if (source == zS)
						zSensitivity = sense;
					else if (source == mouseS)
						mouseSensitivity = sense;
					else if (source == moveS)
						walkSensitivity = sense;
				}
			}
		}
	}

	protected int[] nums = GameControllerRunnable.nums;
	protected String[] names = new String[] { "Right Trigger", "A Button", "B Button", "X Button", "Y Button", "Left Bumper", "Right Bumper", "Back",
			"Start", "Left Stick Press", "Right Stick Press", "Left Trigger" };

	protected String trans(int data) {
		for (int i = 0; i < nums.length; i++)
			if (nums[i] == data) {
				if (data == lZAxis)
					return names[names.length - 1];
				return names[i];
			}
		return "Unassigned";
	}
}

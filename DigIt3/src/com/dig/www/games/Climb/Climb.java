package com.dig.www.games.Climb;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Climb extends JFrame implements KeyListener, ActionListener {

	static final int GH = 300;
	private int sX, sY;
	private Timer t;

	private Character player;

	public Climb() {
		getContentPane().add(new Pane());
		setSize(600, GH);
		addKeyListener(this);
		setAlwaysOnTop(true);
		setFocusable(true);
		setResizable(false);
		setUndecorated(true);
		requestFocus();

		player = new Character(300, GH / 2, 10, this);

		setVisible(true);
		t = new Timer(15, this);
		t.start();

	}

	protected void setDeltas(int dX, int dY) {
		sX = dX;
		sY = dY;
	}

	public class Pane extends JPanel {

		public void paint(Graphics g) {

			Graphics2D g2d = (Graphics2D) g;

			if (player.facingRight)
				g2d.drawImage(player.getImage(), player.getX(), player.getY(), this);
			else
				g2d.drawImage(player.getImage(), player.getX() - player.width, player.getY(), -player.width, player.height, this);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		player.animate(sX, sY);
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		player.press(arg0.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		player.release(arg0.getKeyCode());
	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}

	public static void main(String[] args) {
		new Climb();
	}
}

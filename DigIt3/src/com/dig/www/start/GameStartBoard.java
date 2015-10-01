package com.dig.www.start;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.ObjectInputStream;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.dig.www.util.Statics;

public class GameStartBoard extends MPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private DigIt owner;

	private Image screenImage;
	private JPanel buttonPanel;
	private JButton newGame;
	private JButton loadGame;

	// private boolean knobMoved = false;

	private String address = "images/titleScreen/title.png";
	private String defaultDir;
	private char[] invalidChars = { '\\', '/', '?', '*', ':', '"', '<', '>',
			'|' };

	public GameStartBoard(DigIt dM) {

		setLayout(new BorderLayout());

		defaultDir = GameStartBoard.class.getProtectionDomain().getCodeSource()
				.getLocation().getFile()
				+ "saveFiles/";
		defaultDir = defaultDir.replace("/C:", "C:");

		owner = dM;
		owner.setFocusable(false);
		buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.black);

		newGame = new JButton("New Game");
		loadGame = new JButton("Load Game");

		Dimension d = new Dimension(200, 75);
		newGame.setPreferredSize(d);
		loadGame.setPreferredSize(d);

		buttonPanel.add(newGame);
		buttonPanel.add(loadGame);

		newGame.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				newGame();
			}
		});

		add(buttonPanel, BorderLayout.SOUTH);
		// setOpaque(false);
		setBackground(Color.BLACK);
		setDoubleBuffered(true);

		setSize(Statics.BOARD_WIDTH, Statics.BOARD_HEIGHT);

		this.addKeyListener(new MyAdapter());
		setFocusable(true);

		repaint();
	}

	public void paint(Graphics g) {

		super.paint(g);

		// Graphics2D g2d = (Graphics2D) g;
		//
		// screenImage = newImage(address);
		// g2d.drawImage(screenImage, 0, 0, this);

		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	public Image newImage(String loc) {
		return DigIt.lib.checkLibrary("/" + loc);
	}

	private class MyAdapter extends KeyAdapter {

		public void keyPressed(KeyEvent e) {
			keyPress(e.getKeyCode());
		}

		public void keyReleased(KeyEvent e) {
			keyRelease(e.getKeyCode());
		}
	}

	public void keyPress(int key) {
if(key==KeyEvent.VK_ESCAPE){
	System.exit(0);
}
	}

	public void keyRelease(int key) {
	}

	public void newGame() {

		String s = (String) JOptionPane.showInputDialog(this,
				"Please enter a name for your save file: ", DigIt.NAME,
				JOptionPane.PLAIN_MESSAGE, Statics.ICON, null, null);

		if (s != null && !s.equals("")) {

			new File(GameStartBoard.class.getProtectionDomain().getCodeSource()
					.getLocation().getFile()
					+ "saveFiles/" + s).mkdirs();
			//System.out.println("Save name accepted");
			owner.setUserName(s);
			address = "images/titleScreen/loading.png";
			repaint();
			owner.newGame();

		} else if (s != null)
			Statics.showError("There is no entered name", this);
	}

	public DigIt getOwner() {
		// TODO Auto-generated method stub
		return owner;
	}

	public void deletePaint() {
		// TODO Auto-generated method stub
		screenImage = null;
		repaint();
	}
}
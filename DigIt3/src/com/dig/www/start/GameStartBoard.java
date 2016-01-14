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
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.dig.www.character.GameCharacter;
import com.dig.www.util.Statics;

public class GameStartBoard extends MPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private DigIt owner;

	private Image screenImage;
	private JPanel buttonPanel;
	// private JButton newGame;
	// private JButton loadGame;

	// private boolean knobMoved = false;
	private GameSavePanel game1;
	private GameSavePanel game2;
	private GameSavePanel game3;
	private GameSavePanel game4;
	private GameSavePanel game5;
	// private GameSavePanel game6;
	private String address = "images/titleScreen/title.png";
	private String defaultDir;
	private char[] invalidChars = { '\\', '/', '?', '*', ':', '"', '<', '>', '|' };

	public GameStartBoard(DigIt dM) {

		setLayout(new BorderLayout());

		defaultDir = GameStartBoard.class.getProtectionDomain().getCodeSource().getLocation().getFile() + "saveFiles/";
		defaultDir = defaultDir.replace("/C:", "C:");
		File dir = new File(GameStartBoard.class.getProtectionDomain().getCodeSource().getLocation().getFile() + "saveFiles");
		if (!dir.exists()) {
			dir.mkdirs();
		}
		owner = dM;
		owner.setFocusable(false);
		buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.black);
		buttonPanel.setFocusable(false);

		game1 = new GameSavePanel(1, Color.RED);
		game2 = new GameSavePanel(2, Color.BLUE);
		game3 = new GameSavePanel(3, Color.YELLOW);
		game4 = new GameSavePanel(4, Color.GREEN);
		game5 = new GameSavePanel(5, Color.CYAN);
		// game6 = new GameSavePanel(6,Color.MAGENTA);
		buttonPanel.add(game1);
		buttonPanel.add(game2);
		buttonPanel.add(game3);
		buttonPanel.add(game4);
		buttonPanel.add(game5);
		// buttonPanel.add(game6);
		add(buttonPanel, BorderLayout.SOUTH);
		// setOpaque(false);
		setBackground(Color.BLACK);
		setDoubleBuffered(true);

		setSize(Statics.BOARD_WIDTH, Statics.BOARD_HEIGHT);

		this.addKeyListener(new MyAdapter());
		setFocusable(true);

		repaint();
	}

	private boolean firstRun = true;

	public void paint(Graphics g) {

		super.paint(g);

		if (firstRun) {
			((Graphics2D) g).setXORMode(Color.white);
			((Graphics2D) g).drawImage(Statics.ICON.getImage(), -200, -200, this);
			firstRun = false;
		}

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
		if (key == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		}
	}

	public void keyRelease(int key) {
	}

	public void newGame(String s) {

		if (new File(Statics.getBasedir() + "saveFiles/" + s + "/" + s + ".txt").exists()) {
			if (JOptionPane.showConfirmDialog(owner, "Are you sure you want to delete this file and create a new game?") != JOptionPane.YES_OPTION)
				return;
		}

		if (s != null && !s.equals("")) {

			// new
			// File(GameStartBoard.class.getProtectionDomain().getCodeSource()
			// .getLocation().getFile()
			// + "saveFiles/" + s).mkdirs();
			try {
				String loc = Statics.getBasedir() + "saveFiles/" + s + "/";
				File f = new File(loc);

				if (!f.exists())
					f.mkdirs();

				BufferedWriter writer = new BufferedWriter(new FileWriter((loc + s + ".txt")));
				writer.write("");
				writer.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			String[] packs = new File(Statics.getBasedir() + "/maps").list();
			String pack = ((String) JOptionPane.showInputDialog(this, "Select a game.", DigIt.NAME, JOptionPane.PLAIN_MESSAGE, Statics.ICON, packs,
					Statics.MAIN));
			// System.out.println("Save name accepted");
			owner.setUserName(s);
			owner.setPack(pack);
			try {
				String location = (Statics.getBasedir() + "maps/" + pack + "/");
				File saveFile = new File(location + "info.txt");

				if (saveFile.exists()) {
					BufferedReader reader = new BufferedReader(new FileReader(saveFile));
					String line;
					ArrayList<String> lines = new ArrayList<String>();

					while ((line = reader.readLine()) != null)
						lines.add(line);
					if (lines.size() > 0) {
						owner.setLevel(lines.get(0).trim());
					}

					reader.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			// address = "images/titleScreen/loading.png";
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

	public void load(String save) {

		owner.setUserName(save);
		// address = "images/titleScreen/loading.png";
		repaint();
		owner.loadSave();
	}

	public void deleteGame(String s) {
		// TODO deleteGame(String s)
		if (JOptionPane.showConfirmDialog(owner, "Are you sure you want to delete this file?\n(Deleted data cannot be restored.)") == JOptionPane.YES_OPTION) {
			new File(GameStartBoard.class.getProtectionDomain().getCodeSource().getLocation().getFile() + "saveFiles/" + s + "/" + s + ".txt")
					.delete();

			switch (s.charAt(s.length() - 1)) {
			case '1':
				game1.reset();
				break;
			case '2':
				game2.reset();
				break;
			case '3':
				game3.reset();
				break;
			}

			revalidate();
			repaint();
		} else
			return;
	}

	public void loadGame(String file) {

		load(file);
	}

	public class GameSavePanel extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		protected int saveNum;
		protected JButton load;
		protected JButton create;
		protected JButton delete;
		protected Color color;

		public GameSavePanel(int saveNum, Color color) {
			this.color = color;

			this.setPreferredSize(new Dimension(200, 150));
			this.setBackground(color);
			this.saveNum = saveNum;
			this.setLayout(new BorderLayout());
			this.setFocusable(false);

			load = new JButton("Load Game");
			create = new JButton("New Game");
			delete = new JButton("Delete Game");

			load.setFocusable(false);
			create.setFocusable(false);
			delete.setFocusable(false);

			load.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					loadGame(fileS());
				}
			});
			create.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					newGame(fileS());
				}
			});
			delete.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					deleteGame(fileS());
				}
			});

			JLabel label = new JLabel(fileS(), SwingConstants.CENTER);
			label.setPreferredSize(new Dimension(200, 20));
			label.setFocusable(false);
			this.add(label, BorderLayout.NORTH);
			JPanel buttonPanel = new JPanel();
			buttonPanel.setBackground(color);
			buttonPanel.add(create);
			buttonPanel.setFocusable(false);
			if (new File((Statics.getBasedir() + "saveFiles/" + fileS() + "/" + fileS() + ".txt")).exists()) {
				buttonPanel.add(load);
				buttonPanel.add(delete);
			}
			this.add(buttonPanel, BorderLayout.CENTER);
		}

		public void reset() {

			this.removeAll();
			JLabel label = new JLabel(fileS(), SwingConstants.CENTER);
			label.setPreferredSize(new Dimension(200, 20));
			label.setFocusable(false);
			this.add(label, BorderLayout.NORTH);
			JPanel buttonPanel = new JPanel();
			buttonPanel.setBackground(color);
			buttonPanel.add(create);
			buttonPanel.setFocusable(false);
			if (new File((Statics.getBasedir() + "saveFiles/" + fileS() + "/" + fileS() + ".txt")).exists()) {
				buttonPanel.add(load);
				buttonPanel.add(delete);
			}
			this.add(buttonPanel, BorderLayout.CENTER);
		}

		public String fileS() {
			return "Game" + saveNum;
		}
	}

}

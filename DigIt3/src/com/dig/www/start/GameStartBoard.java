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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.sql.Savepoint;

import javax.swing.ImageIcon;
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
loadGame.addActionListener(new ActionListener() {
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		String[]files=new File(GameStartBoard.class.getProtectionDomain().getCodeSource()
				.getLocation().getFile()
				+ "saveFiles").list();
		
		String[]options=new String[files.length+1];
		options[0]="Cancel";
		for(int c=1;c<files.length+1;c++){
			options[c]=files[c-1];
		}
	
		int sel=JOptionPane.showOptionDialog(owner, "What game do you want to load?", "Load", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, 0);
	if(sel!=0){
		load(options[sel].substring(0, options[sel].lastIndexOf(".txt")));
	}
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
String[]files=new File(GameStartBoard.class.getProtectionDomain().getCodeSource()
					.getLocation().getFile()
					+ "saveFiles").list();
for(int c=0;c<files.length;c++){
	if(files[c].substring(0,  files[c].lastIndexOf(".txt")).equals(s)){
	int sel=	JOptionPane.showConfirmDialog(this, "This file already exists. Do you want to load?");
	if(sel==JOptionPane.OK_OPTION){
		load(s);
	}
		break;	
	}
}
		if (s != null && !s.equals("")) {

//			new File(GameStartBoard.class.getProtectionDomain().getCodeSource()
//					.getLocation().getFile()
//					+ "saveFiles/" + s).mkdirs();
			try{
			BufferedWriter writer=new BufferedWriter(new FileWriter((GameStartBoard.class.getProtectionDomain().getCodeSource()
					.getLocation().getFile()
					+ "saveFiles/" + s+".txt")));
			writer.write("");
			writer.close();
			}catch(Exception ex){
				ex.printStackTrace();
			}
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
	public void load(String save){
		
		owner.setUserName(save);
		address = "images/titleScreen/loading.png";
		repaint();
		owner.newGame();
		
	}
}
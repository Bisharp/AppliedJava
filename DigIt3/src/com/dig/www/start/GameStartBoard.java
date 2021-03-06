package com.dig.www.start;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import com.dig.www.util.Statics;

public class GameStartBoard extends MPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

private static int switchMax=200;
private int switchTimer=switchMax;
	private DigIt owner;
private Timer t;
//	private Image screenImage;
	private JPanel buttonPanel;
	private static final int starts=3;
	private int startInt=(Statics.RAND.nextInt(starts)+1);
	// private JButton newGame;
	// private JButton loadGame;

	// private boolean knobMoved = false;
	
	// private MapMakerPanel mapMaker;
	private JPanel draw;
	private GameSavePanel game1;
	private GameSavePanel game2;
	private GameSavePanel game3;
	private GameSavePanel game4;
	private Image startScreen;
	private Image startScreen2;
	private MultiPlayerPanel multiplayer;
	private String address = "images/titleScreen/title.png";
	private String defaultDir;
	private char[] invalidChars = { '\\', '/', '?', '*', ':', '"', '<', '>',
			'|' };
	private GameStartBoard gameStartBoard = this;

	public GameStartBoard(DigIt dM) {

		setLayout(new BorderLayout());

		defaultDir = Statics.getBasedir()
				+ "saveFiles/";
		defaultDir = defaultDir.replace("/C:", "C:");
		File dir = new File(Statics.getBasedir()
				+ "saveFiles");
		if (!dir.exists()) {
			dir.mkdirs();
		}
		owner = dM;
		owner.setFocusable(false);
		buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.black);
		buttonPanel.setFocusable(false);
		//GameSavePanel mapMaker=new GameSavePanel(0, Color.ORANGE);//new MapMakerPanel(Color.YELLOW?)//this is probably where
		// MapMaker will go to center the game saves
		game1 = new GameSavePanel(1, Color.RED);
		game2 = new GameSavePanel(2, new Color(127, 127, 127));
		game3 = new GameSavePanel(3, new Color(34, 177, 76));
		game4 = new GameSavePanel(4, new Color(255, 128, 255));
		multiplayer = new MultiPlayerPanel(Color.BLUE);
		
		 //buttonPanel.add(mapMaker);
		buttonPanel.add(multiplayer);
		buttonPanel.add(game1);
		buttonPanel.add(game2);
		buttonPanel.add(game3);
		buttonPanel.add(game4);
		
		
		add(buttonPanel, BorderLayout.SOUTH);
		//setBackground(Color.BLACK);
		
		setDoubleBuffered(true);

		setSize(Statics.BOARD_WIDTH, Statics.BOARD_HEIGHT);

		this.addKeyListener(new MyAdapter());
		setFocusable(true);
		newImages();
		draw=new JPanel(){
			@Override
			protected void paintComponent(Graphics g2) {
				Graphics2D g=(Graphics2D)g2;
				g.setColor(Color.BLACK);
			g.fillRect(0, 0, GameStartBoard.this.getWidth(), GameStartBoard.this.getHeight());
			g.setFont(Statics.MENU);
			if(switchTimer<50){
				g.drawImage(startScreen2, 0, 0, GameStartBoard.this.getWidth(), GameStartBoard.this.getHeight()-150, GameStartBoard.this);
			//g.setComposite(AlphaComposite.SrcOver.derive((switchTimer/(float)50);
				g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, switchTimer/(float)50));}
				g.drawImage(startScreen, 0, 0, GameStartBoard.this.getWidth(), GameStartBoard.this.getHeight()-150, GameStartBoard.this);
			if(switchTimer<50)
				g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
			g.setColor(Color.RED);
			g.drawString(DigIt.NAME, GameStartBoard.this.getWidth()/2-239, GameStartBoard.this.getHeight()/6);
			}
		};
add(draw,BorderLayout.CENTER);
		repaint();
		
		t=new Timer(50, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
	switchTimer--;
	if(switchTimer<0){
		switchTimer=switchMax;
		newImages();
	}
	draw.repaint();
			}
		});
		t.start();
		//System.out.println(buttonPanel.getPreferredSize().getHeight());
	}

	private boolean firstRun = true;

	public void paint(Graphics g) {

		super.paint(g);

		if (firstRun) {
			((Graphics2D) g).setXORMode(Color.white);
			((Graphics2D) g).drawImage(Statics.ICON.getImage(), -200, -200,
					this);
			firstRun = false;
		}

		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}
public void newImages(){
	
	if(startScreen2!=null)
		startScreen=startScreen2;
	else{
		startScreen=Statics.newImage("images/startScreens/startScreen"+startInt+".png");
	}
	startInt++;
	if(startInt>starts)
		startInt=1;
	startScreen2=Statics.newImage("images/startScreens/startScreen"+startInt+".png");
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
try {
		if (s != null && !s.equals("")) {
			//String[] packs = Statics.listFolder(Statics.getBasedir() + "/maps");
			
				String[]packs=Statics.listFilesInNotJar("maps");
		
			String pack = ((String) JOptionPane.showInputDialog(this,
					"Select a game.", DigIt.NAME, JOptionPane.PLAIN_MESSAGE,
					Statics.ICON, packs, Statics.MAIN));
			if (pack == null)
				return;

			if (new File(Statics.getBasedir() + "saveFiles/" + s + "/"
					+ "save.ser").exists()) {
				if (JOptionPane
						.showConfirmDialog(owner,
								"Are you sure you want to delete this file and create a new game?") != JOptionPane.YES_OPTION)
					return;
			}
			// new
			// File(GameStartBoard.class.getProtectionDomain().getCodeSource()
			// .getLocation().getFile()
			// + "saveFiles/" + s).mkdirs();
			try {
				String loc = Statics.getBasedir() + "saveFiles/" + s + "/";
				File f = new File(loc);

				if (!f.exists())
					f.mkdirs();

//				BufferedWriter writer = new BufferedWriter(new FileWriter((loc
//						+ s + ".txt")));
//				writer.write("");
//				writer.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			// System.out.println("Save name accepted");

			owner.setUserName(s);
			owner.setPack(pack);
			try {
				String location = (Statics.getBasedir() + "maps/" + pack + "/");
				File saveFile = new File(location + "info.txt");

				if (saveFile.exists()) {
					BufferedReader reader = new BufferedReader(new FileReader(
							saveFile));
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
			t.stop();
			owner.newGame();

		} else if (s != null)
			Statics.showError("There is no entered name", this);
	} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}}

	public DigIt getOwner() {
		// TODO Auto-generated method stub
		return owner;
	}

	public void deletePaint() {
		// TODO Auto-generated method stub
		//screenImage = null;
		repaint();
	}

	public void load(String save) {

		owner.setUserName(save);
		// address = "images/titleScreen/loading.png";
		repaint();
		t.stop();
		owner.loadSave();
	}

	public void deleteGame(String s) {
		// TODO deleteGame(String s)
		if (JOptionPane
				.showConfirmDialog(
						owner,
						"Are you sure you want to delete this file?\n(Deleted data cannot be restored.)") == JOptionPane.YES_OPTION) {
			new File(GameStartBoard.class.getProtectionDomain().getCodeSource()
					.getLocation().getFile()
					+ "saveFiles/" + s + "/save.ser").delete();

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

	public class MultiPlayerPanel extends JPanel {
		private Color color;
		private JButton join;
		private JButton getIP;

		public MultiPlayerPanel(Color color) {
			this.color = color;

			this.setPreferredSize(new Dimension(120, 150));
			this.setBackground(color);
			this.setLayout(new BorderLayout());
			this.setFocusable(false);

			join = new JButton("Join Game");
			getIP = new JButton("Get Host Code");

			join.setFocusable(false);
			getIP.setFocusable(false);

			join.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					owner.newMPGame();
				}
			});
			getIP.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					new IPView();
				}
			});

			JLabel label = new JLabel("Multiplayer", SwingConstants.CENTER);
			label.setPreferredSize(new Dimension(200, 20));
			label.setFocusable(false);
			this.add(label, BorderLayout.NORTH);
			JPanel buttonPanel = new JPanel();
			buttonPanel.setBackground(color);
			buttonPanel.add(join);
			buttonPanel.add(getIP);
			buttonPanel.setFocusable(false);
			this.add(buttonPanel, BorderLayout.CENTER);
		}

		public class IPView extends JFrame {
			public IPView() {
				setSize(500, 100);
				setAlwaysOnTop(true);
				setResizable(false);
				setFocusable(true);
				setLocation(Statics.BOARD_WIDTH / 2 - 250,
						Statics.BOARD_HEIGHT / 2 - 100);
				this.addWindowListener(new WindowListener() {

					@Override
					public void windowOpened(WindowEvent arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void windowIconified(WindowEvent arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void windowDeiconified(WindowEvent arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void windowDeactivated(WindowEvent arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void windowClosing(WindowEvent arg0) {
						// TODO Auto-generated method stub
						gameStartBoard.requestFocus();
					}

					@Override
					public void windowClosed(WindowEvent arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void windowActivated(WindowEvent arg0) {
						// TODO Auto-generated method stub

					}
				});
				this.setLayout(new BorderLayout());

				this.add(
						new JLabel(
								"Your Host Code is below. You can copy it to your clipboard.",
								SwingConstants.CENTER), BorderLayout.NORTH);
				JPanel tpan=new JPanel();
				tpan.setLayout(new BoxLayout(tpan, BoxLayout.Y_AXIS));
				JPanel apan=new JPanel();
				String sa = "Could not get Host Code.";
				try {
					//sa=Statics.getPublicAddress();
					sa =Statics.getPrivateAddress();// = InetAddress.getLocalHost().getHostAddress();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				JTextField tfa = new JTextField(sa, 25);

				// tf.setFocusable(false);
				tfa.setEditable(false);
				apan.add(new JLabel("Same wifi:"));
				apan.add(tfa);
				tpan.add(apan);
				String s = "Could not get Host Code.";
				JPanel bpan=new JPanel();
				try {
					s=Statics.getPublicAddress();
					//s = InetAddress.getLocalHost().getHostAddress();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				JTextField tf = new JTextField(s, 25);

				// tf.setFocusable(false);
				tf.setEditable(false);
				bpan.add(new JLabel("Outside wifi"));
				bpan.add(tf);
				tpan.add(bpan);
				this.add(tpan, BorderLayout.SOUTH);
				setVisible(true);
			}
		}
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

			this.setPreferredSize(new Dimension(120, 150));
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
			if (new File((Statics.getBasedir() + "saveFiles/" + fileS() + "/"
					+ "save.ser")).exists()) {
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
			if (new File((Statics.getBasedir() + "saveFiles/" + fileS() + "/"
					+  "save.ser")).exists()) {
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

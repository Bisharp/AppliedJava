package com.dig.www.start;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.lang.reflect.Field;
import java.rmi.RemoteException;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.dig.www.util.GameControllerPreferences;
import com.dig.www.util.GameControllerRunnable;
import com.dig.www.util.ImageLibrary;
import com.dig.www.util.Preferences;
import com.dig.www.util.SoundPlayer;
import com.dig.www.util.Statics;

public class DigIt extends JFrame {

	private static final long serialVersionUID = 1L;
	private MPanel activePanel;
	private String userName;
	private static Thread controllerThread;
	private static GameControllerRunnable gCR;
	public static final ImageLibrary lib;
	public static final SoundPlayer soundPlayer;
	public static final String NAME = "Quest of Four";
	private String pack = Statics.MAIN;
	private String level = Board.DEFAULT;
	static {
		lib = ImageLibrary.getInstance();
		soundPlayer = new SoundPlayer();
		//yadayadayada
	}

	public DigIt() {
		try {
			Statics.MAC = System.getProperty("os.name").startsWith("Mac");
		} catch (Exception ex) {
			Statics.MAC = false;
		}
		JFrame f = new JFrame(DigIt.NAME);
		f.setSize(0, 0);
		f.setIconImage(Statics.ICON.getImage());
		f.setLocation(-100, -100);
		f.setVisible(true);

		setUndecorated(true);
		activePanel = new GameStartBoard(this);
		getContentPane().add(BorderLayout.CENTER, activePanel);
		JOptionPane.showMessageDialog(this, "Please plug in any game controllers now.\nYou will not be able to later.", NAME, JOptionPane.INFORMATION_MESSAGE, Statics.ICON);

		gCR = new GameControllerRunnable(this);
		controllerThread = new Thread(gCR);
		controllerThread.start();
		f.dispose();
		setVisible(true);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(Statics.BOARD_WIDTH, Statics.BOARD_HEIGHT);
		setLocationRelativeTo(null);
		setTitle(NAME);
		setResizable(false);
		setFocusable(false);
		Statics.is1024 = Statics.BOARD_WIDTH <= 1024;
		setIconImage(Statics.ICON.getImage());
		setVisible(true);
		addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent arg0) {

			}

			@Override
			public void windowIconified(WindowEvent arg0) {

			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
			}

			@Override
			public void windowClosing(WindowEvent arg0) {
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				// TODO Pushing code here
			}

			@Override
			public void windowActivated(WindowEvent arg0) {

			}
		});
	}

	public static void main(String[] args) {
		Statics.getBasedir();
		if (Statics.isJar) {
			System.setProperty("java.library.path", "resources");
			try {
				Field fieldSysPath = ClassLoader.class.getDeclaredField("sys_paths");
				fieldSysPath.setAccessible(true);
				fieldSysPath.set(null, null);
			} catch (Exception ex) {

			}
		}
		new DigIt();
	}

	public void newBoard() {
		soundPlayer.stopMusic();
		nullBoards();
		activePanel = null;
		activePanel = new Board(this, pack, userName);

		getContentPane().add(BorderLayout.CENTER, activePanel);
	}

	public void newGame() {
		newBoard();
		if (activePanel instanceof Board) {
			((Board) activePanel).newGame(level);
			GameControllerRunnable.renewKeys();
		}
	}

	public void newMPBoard() {
		soundPlayer.stopMusic();
		nullBoards();
		activePanel = null;
		activePanel = new Board(this);
		getContentPane().add(BorderLayout.CENTER, activePanel);
	}

	public void newMPGame() {
		newMPBoard();
		// if (activePanel instanceof Board) {
		// ((Board) activePanel).newGame(level);
		// }
	}

	public void loadSave() {
		newBoard();
		if (activePanel instanceof Board) {
			((Board) activePanel).loadSave();
			GameControllerRunnable.renewKeys();
		}
	}

	public void quit(boolean forced) {
		Board activePanel=(Board)this.activePanel;
if (forced||JOptionPane.showConfirmDialog(this, "Are you sure you want to quit?\n(Unsaved data will be lost)", DigIt.NAME,
				JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE,Statics.ICON) == JOptionPane.YES_OPTION){
		
			if (activePanel.getClient() != null && activePanel.getOtherServer() != null)
				try {
					String s = null;
					if (activePanel.getCharacter() != null)
						s =activePanel.getCharacter().getType().charName();
					activePanel.getOtherServer().leaveChatRoom(activePanel.getMPName(), s);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
		nullBoards();
		Board.preferences = new Preferences();
		
		this.activePanel = new GameStartBoard(this);
		add(this.activePanel);
		GameControllerRunnable.renewKeys();

		System.gc();
		validate();}
		repaint();
	}

	public void nullBoards() {

		if (activePanel != null) {
			activePanel.setFocusable(false);
			getContentPane().remove(activePanel);
			activePanel = null;
			setVisible(false);
			setVisible(true);
		}

		repaint();
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

//	public void victory(String saveName) {
//		// TODO Add victory cutscene here
//
//		nullBoards();
//		setVisible(true);
//
//		quit();
//	}

	public DigIt getMe() {
		return this;
	}

	public void setPack(String setter) {
		pack = setter;
	}

	public void keyPress(int key) {
		activePanel.keyPress(key);
	}

	public void keyRelease(int key) {
		activePanel.keyRelease(key);
	}

	public void nullCThread() {
		controllerThread = null;
	}

	public static boolean hasController() {
		return controllerThread.isAlive();
	}

	public static Thread getCT() {
		return controllerThread;
	}
	
	public static GameControllerPreferences getGCP() {
		return gCR.getPreferences();
	}
	
	public static GameControllerRunnable getGCR() {
		return gCR;
	}

	public void setLevel(String setter) {
		this.level = setter;
	}

	public String getLevel() {
		return level;
	}
}
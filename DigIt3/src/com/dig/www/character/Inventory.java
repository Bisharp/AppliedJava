package com.dig.www.character;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;

import com.dig.www.games.Jump.Jump;
import com.dig.www.start.Board;
import com.dig.www.start.DigIt;
import com.dig.www.start.GameStartBoard;
import com.dig.www.util.Statics;

public class Inventory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private enum Option {
		ITEMS, GEAR, LEVEL;
	}

	private ArrayList<Items> items;
	private HashMap<Items, Integer> itemNums;
	private int index = items != null && items.size() >= 0 ? 0 : -1;

	// -------------------------------------------------------------------------------------------

	private static final int buttonWidth = 200;
	private static final int buttonHeight = 100;

	private static final int margin = buttonWidth + 100;
	private static final int row0 = 200;
	private static final int row1 = 400;

	// Options
	private static int optionsX = margin;
	private static Rectangle options = new Rectangle(optionsX, row0, buttonWidth, buttonHeight);

	// Level Up Menu
	private static int levelX = Statics.BOARD_WIDTH - ((margin * 2) + 100);
	private static Rectangle level = new Rectangle(levelX, row0, buttonWidth + 100, buttonHeight);

	// View Inventory
	private static int viewX = margin;
	private static Rectangle view = new Rectangle(viewX, row1, buttonWidth, buttonHeight);

	// Set Item Menu
	private static int itemX = Statics.BOARD_WIDTH - ((margin * 2) + 50);
	private static Rectangle item = new Rectangle(itemX, row1, buttonWidth + 75, buttonHeight);

	// TODO this used to be Wallet
	// ------------------------------------------------------------------------------------------
	private int money = 0;

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
		if (getDigits() > 9) {
			setMoney(999999999);
		}
	}

	public void addMoney(int money) {
		this.money += money;
		if (getDigits() > 9) {
			setMoney(999999999);
		}
	}

	public void spendMoney(int money) {
		this.money -= money;
	}

	public int getDigits() {
		return ("" + money).length();
	}

	// TODO end money
	// ------------------------------------------------------------------------------------------

	private transient Board owner;

	public Inventory(Board owner) {
		this.owner = owner;
		items = new ArrayList<Items>();
		itemNums = new HashMap<Items, Integer>();
	}

	public Inventory(Board owner, BufferedReader reader) {
		this.owner = owner;
		items = new ArrayList<Items>();
		itemNums = new HashMap<Items, Integer>();
		String[] elements;
		String line;
		ArrayList<String> lines = new ArrayList<String>();

		try {
			while ((line = reader.readLine()) != null)
				lines.add(line);
			reader.close();

			for (String line2 : lines) {
				if (line2.startsWith("*"))
					money = Integer.parseInt(line2.replace('*', '0'));
				else {
					elements = line2.split(",");
					addItem(Items.translate(elements[0]), Integer.parseInt(elements[1]));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void draw(Graphics2D g2d) {

		g2d.setColor(Color.BLACK);
		g2d.fill(owner.getScreen());
		g2d.setFont(Statics.PAUSE);

		g2d.setColor(Color.RED);
		g2d.fill(options);
		g2d.fill(level);
		g2d.fill(view);
		g2d.fill(item);

		g2d.setColor(Color.WHITE);
		g2d.drawString("Options", optionsX + buttonWidth / 4, row0 + buttonHeight / 3);
		g2d.drawString("View Level-Up Menu", levelX + buttonWidth / 4, row0 + buttonHeight / 3);
		g2d.drawString("View Inventory", viewX + buttonWidth / 4, row1 + buttonHeight / 3);
		g2d.drawString("Select Current Item", itemX + buttonWidth / 4, row1 + buttonHeight / 3);
	}

	public void mouseClick(MouseEvent m) {

		Rectangle r = new Rectangle(m.getX(), m.getY(), 5, 10);

		if (r.intersects(options))
			if (DigIt.hasController())
				DigIt.getCTR().getPreferences().setValues();
			else
				Board.preferences.setValues(owner);
		else if (r.intersects(view))
			showInventory(Option.ITEMS);
		else if (r.intersects(level))
			owner.getCharacter().OpenLevelUp();
		else if (r.intersects(item))
			selectItem();
	}

	public void addItem(Items type, int num) {

		// Items.NULL is the type given to items that should not go in the
		// inventory. These are handled by the Board.
		if (type == Items.NULL)
			return;

		if (!items.contains(type))
			items.add(type);

		if (itemNums.containsKey(type)) {
			int i=itemNums.get(type);
			itemNums.remove(type);
			itemNums.put(type, i + num);
		} else
			itemNums.put(type, num);
	}

	public void selectItem() {

		if (items.size() == 0) {
			Statics.showError("You have no equipable items", owner);
			return;
		}

		String s = (String) JOptionPane.showInputDialog(owner, "Select the item you want to have equipped:", DigIt.NAME,
				JOptionPane.INFORMATION_MESSAGE, Statics.ICON, getKeys(itemNums, items, false, true), null);

		if (s != null)
			for (int i = 0; i < items.size(); i++)
				if (s.equals(items.get(i).toString()))
					index = i;
	}

	public void showInventory(Option o) {
		new InventoryView(owner.getOwner(), o);
	}

	public class InventoryView extends JDialog {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private JScrollPane scrollPane;
		private JList<String> jList;
		private JButton b;
		private static final int buttonHeight = 100;

		public InventoryView(JFrame parent, Option which) {
			super(parent);

			Dimension d = new Dimension(500, 300);

			setSize(d);
			setTitle(DigIt.NAME + " Inventory");
			this.setLocation(Statics.BOARD_WIDTH / 2 - this.getWidth() / 2, Statics.BOARD_HEIGHT / 2 - this.getHeight() / 2);
			setBackground(Color.black);

			switch (which) {
			default:
			case ITEMS:
				jList = new JList<String>(getKeys(itemNums, items, true, false));
				jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				jList.setSelectedIndex(0);
				scrollPane = new JScrollPane(jList);
				b = new JButton("Show item description");
				b.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						if (jList.getSelectedValue() != null) {
							if (jList.getSelectedValue().split(" x")[0].equals(Items.VIDEO_GAME.toString())) {
								ArrayList<String> options = new ArrayList<String>();
								options.add("Turn off console");
								for (Items s : items) {
									switch (s.toString()) {
									case "Jump Video Game":
										if (!options.contains("Jump"))
											options.add("Jump");
										break;
									default:
									}
								}
								String desc = "Which game would you like to play?";
								if (options.size() == 1)
									desc = "You do not currently have any games. If you collect games, you can play them here.";

								int i = JOptionPane.showOptionDialog(getMe(), desc, DigIt.NAME + " Item Description", JOptionPane.CANCEL_OPTION,
										JOptionPane.INFORMATION_MESSAGE, new ImageIcon(Items.translate(jList.getSelectedValue().split(" x")[0])
												.getPath()), options.toArray(), "Leave");

								if (options.get(i).equals("Jump")) {
									new Jump();
								}
							} else
								JOptionPane.showMessageDialog(getMe(), Items.getDesc(jList.getSelectedValue().split(" x")[0]), DigIt.NAME
										+ " Item Description", JOptionPane.INFORMATION_MESSAGE,
										new ImageIcon(Items.translate(jList.getSelectedValue().split(" x")[0]).getPath()));

						}
					}
				});
			}
			scrollPane.setSize(new Dimension((int) d.getWidth(), (int) d.getHeight() - buttonHeight));
			b.setSize(new Dimension((int) d.getWidth(), buttonHeight));

			setLayout(new BorderLayout());
			add(scrollPane, BorderLayout.CENTER);
			add(b, BorderLayout.SOUTH);

			scrollPane.setForeground(Color.WHITE);
			scrollPane.setOpaque(true);
			scrollPane.setBackground(Color.black);

			revalidate();
			setVisible(true);
		}

		private JDialog getMe() {
			return this;
		}
	}

	private String[] getKeys(HashMap<Items, Integer> itemNums, ArrayList<Items> items, boolean showVals, boolean showOnlyThrowable) {

		ArrayList<String> toReturn = new ArrayList<String>();
		Items w;
		for (int i = 0; i < items.size(); i++) {
			w = items.get(i);

			if (itemNums.get(w) > 0 && (!showOnlyThrowable || w.isThrowable()))
				toReturn.add(w.toString() + (showVals ? " x" + itemNums.get(w) : ""));
		}

		String[] s = new String[toReturn.size()];
		for (int i = 0; i < toReturn.size(); i++)
			s[i] = toReturn.get(i);

		return s;
	}

	// Important stuff; might be rearranged or changed later.
	// -------------------------------------------------------------------------------------------

	public Items getItem() {
		return items.get(index);
	}

	public Items useItem() {

		if (index == -1 || getKeys(itemNums, items, false, true).length < 1)
			return Items.NULL;

		Items type = items.get(index);

		if (itemNums.get(type) > 0) {
			itemNums.put(type, itemNums.get(type) - 1);
			return type;
		} else
			return Items.NULL;
	}

	// public void writeStates() {
	// String location =
	// (Inventory.class.getProtectionDomain().getCodeSource().getLocation().getFile().toString()
	// + "saveFiles/"
	// + owner.getUserName() + "/inventory.txt");
	// try {
	// BufferedWriter writer = new BufferedWriter(new FileWriter(location));
	// String s;
	// Items w;
	//
	// for (int i = 0; i < items.size(); i++) {
	// w = items.get(i);
	// s = w.toString() + "," + itemNums.get(w);
	// writer.write(s);
	// System.out.println(s);
	// writer.newLine();
	// }
	//
	// writer.write("*" + money);
	// writer.close();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }

	public void setOwner(Board board) {
		owner = board;
	}

	public boolean contains(Items item2) {
		return itemNums.get(item2) != null && itemNums.get(item2) > 0;
	}

	public void decrementItem(Items key) {
		int i=itemNums.get(key);
		itemNums.remove(key);
		itemNums.put(key, i - 1);
	}
}

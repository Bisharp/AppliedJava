package com.dig.www.character;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
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

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;

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
	private int index;

	// -------------------------------------------------------------------------------------------

	private static final int buttonWidth = 200;
	private static final int buttonHeight = 100;

	// Options
	private static int optionsX = Statics.BOARD_WIDTH / 2 - buttonWidth / 2;
	private static int optionsY = 100;
	private static Rectangle options = new Rectangle(optionsX, optionsY, buttonWidth, buttonHeight);

	// View Inventory
	private static int viewX = Statics.BOARD_WIDTH / 2 - buttonWidth / 2;
	private static int viewY = 300;
	private static Rectangle view = new Rectangle(viewX, viewY, buttonWidth, buttonHeight);

	// Level Up Menu
	private static int levelX = Statics.BOARD_WIDTH / 2 - buttonWidth / 2 - 20;
	private static int levelY = 500;
	private static Rectangle level = new Rectangle(levelX, levelY, buttonWidth + 100, buttonHeight);

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
		g2d.setFont(Statics.NPC);

		g2d.setColor(Color.RED);
		g2d.fill(options);
		g2d.fill(view);
		g2d.fill(level);

		g2d.setColor(Color.WHITE);
		g2d.drawString("Options", optionsX + buttonWidth / 4, optionsY + buttonHeight / 3);
		g2d.drawString("View Inventory", viewX + buttonWidth / 4, viewY + buttonHeight / 3);
		g2d.drawString("View Level-Up Menu", levelX + buttonWidth / 4, levelY + buttonHeight / 3);
	}

	public void mouseClick(MouseEvent m) {

		Rectangle r = new Rectangle(m.getX(), m.getY(), 5, 10);

		if (r.intersects(options))
			Board.preferences.setValues(owner);
		else if (r.intersects(view))
			showInventory(Option.ITEMS);
		else if (r.intersects(level))
			owner.getCharacter().OpenLevelUp();
	}

	public void addItem(Items type, int num) {

		// Items.NULL is the type given to items that should not go in the
		// inventory. These are handled by the Board.
		if (type == Items.NULL)
			return;

		if (!items.contains(type))
			items.add(type);

		if (itemNums.containsKey(type)) {
			itemNums.replace(type, itemNums.get(type) + num);
		} else
			itemNums.put(type, num);
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

			switch (which) {
			default:
			case ITEMS:
				jList = new JList<String>(getKeys(itemNums, items));
				jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				jList.setSelectedIndex(0);
				scrollPane = new JScrollPane(jList);
				b = new JButton("Show item description");
				b.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						JOptionPane.showMessageDialog(getMe(), Items.getDesc(jList.getSelectedValue().split(" x")[0]), DigIt.NAME + " Item Description", JOptionPane.INFORMATION_MESSAGE);
					}
				});
			}
			scrollPane.setSize(new Dimension((int) d.getWidth(), (int) d.getHeight() - buttonHeight));
			b.setSize(new Dimension((int) d.getWidth(), buttonHeight));
			
			setLayout(new BorderLayout());
			add(scrollPane, BorderLayout.CENTER);
			add(b, BorderLayout.SOUTH);
			
			revalidate();
			setVisible(true);
		}
		
		private JDialog getMe() {
			return this;
		}
	}

	private String[] getKeys(HashMap<Items, Integer> itemNums, ArrayList<Items> items) {

		ArrayList<String> toReturn = new ArrayList<String>();
		Items w;
		for (int i = 0; i < items.size(); i++) {
			w = items.get(i);

			if (itemNums.get(w) > 0)
				toReturn.add(w.toString() + " x" + itemNums.get(w));
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

	// TODO this code may be useful later. DO NOT DELETE.
	// public void back() {
	// index--;
	//
	// if (index < 0)
	// index = itemNums.size() - 1;
	// }
	//
	// public void forward() {
	// index++;
	//
	// if (index >= itemNums.size())
	// index = 0;
	// }
	//
	// public boolean useItem(Items type) {
	//
	// if (itemNums.get(type) > 0 && itemNums.get(type) < 100)
	// itemNums.put(type, itemNums.get(type) - 1);
	// else if (itemNums.get(type) >= 100)
	// return true;
	// else
	// return false;
	//
	// return true;
	// }

//	public void writeStates() {
//		String location = (Inventory.class.getProtectionDomain().getCodeSource().getLocation().getFile().toString() + "saveFiles/"
//				+ owner.getUserName() + "/inventory.txt");
//		try {
//			BufferedWriter writer = new BufferedWriter(new FileWriter(location));
//			String s;
//			Items w;
//
//			for (int i = 0; i < items.size(); i++) {
//				w = items.get(i);
//				s = w.toString() + "," + itemNums.get(w);
//				writer.write(s);
//				System.out.println(s);
//				writer.newLine();
//			}
//
//			writer.write("*" + money);
//			writer.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

	public void setOwner(Board board) {
		owner = board;
	}
}

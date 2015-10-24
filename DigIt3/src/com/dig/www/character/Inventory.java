package com.dig.www.character;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListModel;

import com.dig.www.start.Board;
import com.dig.www.start.DigIt;
import com.dig.www.util.Statics;

public class Inventory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private enum Option {
		ITEMS, GEAR, LEVEL;
	}

	private static final int buttonWidth = 200;
	private static final int buttonHeight = 100;

	// Options
	private static int optionsX = Statics.BOARD_WIDTH / 2 - buttonWidth / 2;
	private static int optionsY = 100;
	private transient Rectangle options = new Rectangle(optionsX, optionsY, buttonWidth, buttonHeight);

	// View Inventory
	private static int viewX = Statics.BOARD_WIDTH / 2 - buttonWidth / 2;
	private static int viewY = 300;
	private transient Rectangle view = new Rectangle(viewX, viewY, buttonWidth, buttonHeight);

	// Level Up Menu
	private static int levelX = Statics.BOARD_WIDTH / 2 - buttonWidth / 2 - 20;
	private static int levelY = 500;
	private transient Rectangle level = new Rectangle(levelX, levelY, buttonWidth + 100, buttonHeight);

	// TODO this used to be the Wallet
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

	// ------------------------------------------------------------------------------------------

	private transient Board owner;

	public Inventory(Board owner) {
		this.owner = owner;
		items = new ArrayList<Items>();
		itemNums = new HashMap<Items, Integer>();
	}
	
	public void prepare(Board owner) {
		this.owner = owner;
		
		for (String s : getKeys(itemNums, items))
			System.out.println(s + " is in inventory");
		System.out.println("End of recap.");
	}

	private Rectangle bound = new Rectangle(0, 0, 100, 100);

	public void draw(Graphics2D g2d) {

		g2d.setColor(Color.BLACK);
		g2d.fill(owner.getScreen());
		g2d.setFont(Statics.NPC);

		g2d.setColor(Color.RED);
		g2d.fill(options);
		g2d.fill(view);
		g2d.fill(level);
		g2d.draw(bound);

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
		else if (r.intersects(bound))
			addItem(Items.TEST0, 5);
	}

	public void addItem(Items type, int num) {
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
		private JScrollPane comBox;

		public InventoryView(JFrame parent, Option which) {
			super(parent);

			Dimension d = new Dimension(500, 300);

			setSize(d);
			setTitle(DigIt.NAME + " Inventory");

			switch (which) {

			default:
			case ITEMS:
				comBox = new JScrollPane(new JList<String>(getKeys(itemNums, items)));
			}
			comBox.setSize(d);
			add(comBox);
			revalidate();
			setVisible(true);
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
	}
	
	// TODO delete
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

	private ArrayList<Items> items;
	private HashMap<Items, Integer> itemNums;
	private int index;

	public Items getItem() {
		return items.get(index);
	}

	public void back() {
		index--;

		if (index < 0)
			index = itemNums.size() - 1;
	}

	public void forward() {
		index++;

		if (index >= itemNums.size())
			index = 0;
	}

	public boolean useItem(Items type) {

		if (itemNums.get(type) > 0 && itemNums.get(type) < 100)
			itemNums.put(type, itemNums.get(type) - 1);
		else if (itemNums.get(type) >= 100)
			return true;
		else
			return false;

		return true;
	}
}

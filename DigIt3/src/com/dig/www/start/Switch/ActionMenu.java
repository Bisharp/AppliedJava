package com.dig.www.start.Switch;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListModel;
import javax.swing.border.Border;

import com.dig.www.start.Board;
import com.dig.www.util.Statics;

public class ActionMenu extends JFrame{
	private Board owner;
//	DefaultListModel<String>listMod=new DefaultListModel<String>();
//	JList<String>list=new JList<>(listMod);
public ActionMenu(Board owner){
	owner.setActionMenu(this);
	this.owner=owner;
	//owner.setFocusable(false);
	setSize(400,300);
	setLocation(Statics.BOARD_WIDTH/2-this.getWidth()/2,0);
	setAlwaysOnTop(true);
	setFocusable(false);
	//requestFocus();
	setUndecorated(true);
	setLayout(new BorderLayout());
//	String[]alive=owner.getCharacters();
//	list.setPreferredSize(new Dimension(100, 200));
//	for(String element:alive)
//	listMod.addElement(element);
	JPanel south=new JPanel();
	//west.setLayout(new BoxLayout(west, BoxLayout.Y_AXIS));
	//west.add(list);
	JButton switchB=new JButton("switch");
	switchB.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			new SwitchMenu(ActionMenu.this.owner);
			ActionMenu.this.dispose();
			ActionMenu.this.owner.setActionMenu(null);
			//ActionMenu.this.owner.requestFocus();
		}
	});
	JButton mulB=new JButton("start multiplayer server");
	mulB.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			//new SwitchMenu(ActionMenu.this.owner);
			ActionMenu.this.dispose();
			ActionMenu.this.owner.setActionMenu(null);
			ActionMenu.this.owner.multiplayer();
			//ActionMenu.this.owner.requestFocus();
		}
	});
	JButton cancelB=new JButton("cancel");
	cancelB.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
		ActionMenu.this.dispose();
		ActionMenu.this.owner.setActionMenu(null);
		ActionMenu.this.owner.requestFocus();
		}
	});
	JButton orderB=new JButton("order");
	orderB.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			new OrderMenu(ActionMenu.this.owner);
			ActionMenu.this.dispose();
			ActionMenu.this.owner.setActionMenu(null);
		}
	});
	JPanel pan=new JPanel();
	pan.add(mulB);
	this.add(pan,BorderLayout.NORTH);
	south.add(orderB);
	south.add(switchB);
	south.add(cancelB);
	this.add(south,BorderLayout.SOUTH);
//	addKeyListener(new KeyListener() {
//		
//		public void keyTyped(KeyEvent e) {}
//		public void keyReleased(KeyEvent e) {}
//		@Override
//		public void keyPressed(KeyEvent e) {
//			if(e.getKeyCode()==KeyEvent.VK_R){
//			ActionMenu.this.owner.setFocusable(true);
//			ActionMenu.this.owner.requestFocus();
//			ActionMenu.this.dispose();
//			ActionMenu.this.owner.setActionMenu(false);
//			}
//		}
//	});
	setVisible(true);
	setFocusable(false);
owner.requestFocus();	
}
}

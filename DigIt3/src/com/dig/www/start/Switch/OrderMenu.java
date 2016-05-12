package com.dig.www.start.Switch;

import java.awt.BorderLayout;import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.dig.www.MultiPlayer.State.SwitchState;
import com.dig.www.character.GameCharacter;
import com.dig.www.start.Board;
import com.dig.www.util.Statics;

public class OrderMenu extends JFrame{
private Board owner;
public OrderMenu(Board owner){
	this.owner=owner;
	setSize(400,300);
	setLocation(Statics.BOARD_WIDTH/2-this.getWidth()/2,0);
	setAlwaysOnTop(true);
	setFocusable(false);
	//requestFocus();
	setUndecorated(true);
	setLayout(new BorderLayout());
	JPanel south=new JPanel();
	JButton waitHere=new JButton((owner.pointedPoint==null?"wait here":"follow me"));
	waitHere.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			Board b=OrderMenu.this.owner;
			if (b.pointedPoint == null) {
Point p=b.getCharPoint();
				b.pointedPoint = new Point(p.x+50, p.y+50);
			} else
				b.pointedPoint = null;
			b.setFocusable(true);
			b.requestFocus();
			b.setOrderMenu(null);
			OrderMenu.this.dispose();
		}
	});
//	JButton goThere=new JButton("go there");
//	goThere.addActionListener(new ActionListener() {
//		
//		@Override
//		public void actionPerformed(ActionEvent e) {
//			Board b=OrderMenu.this.owner;
//			if (b.pointedPoint == null) {
//Point p=b.getCharPoint();
//				b.pointedPoint = new Point(p.x+50, p.y+50);
//			} else
//				b.pointedPoint = null;
//			b.setFocusable(true);
//			b.requestFocus();
//			b.setOrderMenu(null);
//			OrderMenu.this.dispose();
//		}
//	});
	JButton cancel=new JButton("cancel");
	cancel.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			Board b=OrderMenu.this.owner;
			b.setFocusable(true);
			b.requestFocus();
			b.setOrderMenu(null);
			OrderMenu.this.dispose();
		}
	});
	south.add(waitHere);
	south.add(cancel);
	this.add(south,BorderLayout.SOUTH);
	owner.setOrderMenu(this);
	setVisible(true);
}
}

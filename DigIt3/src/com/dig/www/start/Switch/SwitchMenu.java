package com.dig.www.start.Switch;

import java.awt.BorderLayout;
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

public class SwitchMenu extends JFrame{
private Board owner;
private DefaultListModel<String>listMod=new DefaultListModel<String>();
private JList<String>list=new JList<String>(listMod);
public SwitchMenu(Board owner){
	this.owner=owner;
	setSize(400,300);
	setLocation(Statics.BOARD_WIDTH/2-this.getWidth()/2,0);
	setAlwaysOnTop(true);
	setFocusable(false);
	//requestFocus();
	setUndecorated(true);
	String[]alive=owner.getCharacters();
	for(String element:alive)
	listMod.addElement(element);
	setLayout(new BorderLayout());
	JPanel south=new JPanel();
	JButton switchB=new JButton("switch");
	switchB.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			Board b=SwitchMenu.this.owner;
			if(list.getSelectedIndex()>=0){
				
			int des=b.getFriend(list.getSelectedValue());
			
			GameCharacter temp=b.getCharacter();
			if (b.getCurrentState() != null)
				b.getCurrentState().getActions().add(new SwitchState(temp.getType().charName(), list.getSelectedValue()));
			temp.setPlayer(false);
			b.setCharacter(b.getFriends().get(des));
			b.getFriends().set(des, temp);
			b.getCharacter().setPlayer(true);
			b.scroll(Statics.BOARD_WIDTH / 2 - 50 - b.getCharacter().getX(), (int) Statics.BOARD_HEIGHT / 2 - 50 - b.getCharacter().getY());
			b.getCharacter().stop();
			Collections.sort(b.getFriends());
			b.setFocusable(true);
			b.requestFocus();
			b.setSwitchingMenu(null);
			SwitchMenu.this.dispose();
		}}
	});
	south.add(switchB);
	JButton cancel=new JButton("cancel");
	cancel.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			Board b=SwitchMenu.this.owner;
			b.setFocusable(true);
			b.requestFocus();
			b.setSwitchingMenu(null);
			SwitchMenu.this.dispose();
		}
	});
	south.add(cancel);
	this.add(list,BorderLayout.CENTER);
	this.add(south,BorderLayout.SOUTH);
	owner.setSwitchingMenu(this);
	setVisible(true);
	list.addListSelectionListener(new ListSelectionListener() {
		
		@Override
		public void valueChanged(ListSelectionEvent e) {
			// TODO Auto-generated method stub
			SwitchMenu.this.owner.requestFocus();
		}
	});
	if(listMod.size()>0){
		list.setSelectedIndex(0);
	}
}
public void updateList(){
	String selected=list.getSelectedValue();
	listMod.clear();
	String[]alive=owner.getCharacters();
	for(String element:alive)
	listMod.addElement(element);
	if(listMod.contains(selected))
		list.setSelectedValue(selected, true);
}
}

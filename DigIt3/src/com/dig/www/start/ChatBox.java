package com.dig.www.start;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.dig.www.util.Statics;

public class ChatBox extends JFrame{
	Board owner;
	JTextField jf;
	 JTextArea jt;
public ChatBox(final Board owner){
	int size=290;
	this.owner=owner;
	setSize(size,125);
	setAlwaysOnTop(true);
	setLayout(new BorderLayout());
	 setFocusable(false); 
     setResizable(false);
     setUndecorated(true);
      jt=new JTextArea(10, 10);
     jt.setEditable(false);
     jt.setFocusable(false);
     jt.setFont(new Font("Courier", Font.PLAIN, 11));
     this.add(jt,BorderLayout.CENTER);
     jf=new JTextField();
     //jt.setTe
     
     jf.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			owner.getCurrentState().getTalks().add(jf.getText());
			jf.setText("");
			owner.requestFocus();
		}
	});
     this.add(jf,BorderLayout.SOUTH);
     setVisible(true);
     setLocation(Statics.BOARD_WIDTH-size, Statics.MAC?23:0);
     owner.requestFocus();
}
//public void add(String s){
//jt.append(s+"\n");	
//}
public void set(ArrayList<String>s){
	jt.setText("");
	for(String st:s)
		jt.append(st);
}
}

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
			int length = 30;
			int nameLength = 10;
			if (nameLength > owner.mpName.length())
				nameLength = owner.mpName.length();
			String name = owner.mpName.substring(0, nameLength);
			// ArrayList<String>s=new ArrayList<String>();
			
				for (int c2 = 0; c2 < jf.getText().length(); c2 += length) {
					int i = length;
					if (c2 + i > jf.getText().length())
						i = jf.getText().length() - c2;
					
					if(owner.server!=null)
					owner.chats.add(name + ":" + jf.getText().substring(c2, c2 + i) + "\n");
					else
						owner.getCurrentState().addTalk(name + ":" + jf.getText().substring(c2, c2 + i) + "\n");
				}
				// }
				// (state.getPlayerStates().get(0).getMpName()+":"+
				// state.getTalks()+"\n").
				// for(String i:s)
				// chats.add(state.getPlayerStates().get(0).getMpName()+":"+
				// state.getTalks()+"\n");
				// chatBox.add(state.getPlayerStates().get(0).getMpName()+":"+
				// state.getTalks()
				// .get(state.getTalks().size() - 1));
			
			//owner.chats.add(jf.getText());
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
	for(String st:((ArrayList<String>)s.clone()))
		jt.append(st);
}
}

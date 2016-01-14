package com.dig.www.games.Jump;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.dig.www.util.Preferences;


public class Jump extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int GH = 300;
	private int nextTimer=0;
	private	ArrayList<JumpObject>obj=new ArrayList<JumpObject>();
	private  MyPanel pan;
	private Timer timer;
  private int height=1;
  private int move=0;
  private int diff=2;
  private Polygon poly;
  private boolean start=false;
 private int best1=0;
 private int best2=0;
 private int best3=0;
 private double time=0;
 Jump jump=this;
 public Jump(){
	 init();
 }
  public void newPoly(){
	   poly=new Polygon();
	   poly.addPoint(0, GH-50);
	   poly.addPoint(30, GH-50);
	   poly.addPoint(50, GH-30);
	   poly.addPoint(100, GH-10);
	   poly.addPoint(0, GH-10);
  }
   public void init () {
	   
	   start=false;
	  // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   setSize(600,GH); 
	   setAlwaysOnTop(true);
	 
     // Construct the button
	 pan=new MyPanel();
	 add(pan);
	// ImageIcon thumbnailIcon = new ImageIcon(getScaledImage(icon.getImage(), 32, 32));
     setFocusable( true ); 
     setResizable(false);
     setUndecorated(true);
	   requestFocus();
     // add the button to the layout
     
     // specify that action events sent by this
   // requestFocus();
	 pan.repaint();
	 addKeyListener(new MyKeyListener());
     timer=new Timer(15, new ActionListener(){

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			while(obj.size()>0&&obj.get(0).getX()<-39){
				obj.remove(0);
			}
			for(int c=0;c<obj.size();c++){
				obj.get(c).mx();
			}
			if(nextTimer<=0){
				obj.add(new JumpObject());
				nextTimer=165+(int)(Math.random()*75/diff)
						;
			}else
				nextTimer-=3;
			time+=0.015;
	
			if(move>0){
				move--;
			}
			if(move==0){
				height=1;
			}
		
			pan.repaint();
		}
    	 
     });
     timer.start();
     setVisible(true);
     newPoly();
   }
  
public class MyKeyListener implements KeyListener{
public MyKeyListener(){
	
	
	
}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
			jump.dispose();
		}
		if(start){
			
			if(e.getKeyCode()==Preferences.UP()&&move==0){
				move=60;
				height=2;
			}
			if(e.getKeyCode()==Preferences.DOWN()&&move==0){
				move=60;
				height=0;
			}
			
		}
		else{
			if(e.getKeyCode()==KeyEvent.VK_1){
				diff=1;
			}
			if(e.getKeyCode()==KeyEvent.VK_2){
				diff=2;
			}
			if(e.getKeyCode()==KeyEvent.VK_3){
				diff=3;
			}
			if(e.getKeyCode()==KeyEvent.VK_SPACE){
				start=true;
				obj.clear();
				time=0;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
public class MyPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
public	void paintComponent(Graphics g){
		g.clearRect(0, 0, this.getWidth(), this.getHeight());
		 g.setColor(Color.cyan);
		   g.fillRect(0, 0, this.getWidth(), this.getHeight());
		   g.setFont(new Font("Trebuchet MS", Font.PLAIN, 15));
		   if(start){
			   g.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
			   g.setColor(Color.green);
			   g.fillRect(0, this.getHeight()-10, this.getWidth(), 12);
			   g.setColor(Color.yellow);
			   if(height==2){
				   g.fillRect(0, this.getHeight()-80-10-40, 40, 80);
				   g.setColor(Color.red);
				   g.fillRect(10, this.getHeight()-80-10-40+10, 5, 5);
				   g.fillRect(25, this.getHeight()-80-10-40+10, 5, 5);
				   g.fillRect(10, this.getHeight()-80-10-40+20, 20, 5);
			   }
			   if(height==1){
				   g.fillRect(0, this.getHeight()-80-10, 40, 80);
				   g.setColor(Color.red);
				   g.fillRect(10, this.getHeight()-80-10+10, 5, 5);
				   g.fillRect(25, this.getHeight()-80-10+10, 5, 5);
				   g.fillRect(10, this.getHeight()-80-10+20, 20, 5);
				   
			   }
			   if(height==0){
				   newPoly();
				   g.fillPolygon(poly);
				   g.setColor(Color.red);
				   g.fillRect(10, this.getHeight()-10-40+10, 5, 5);
				   g.fillRect(25, this.getHeight()-10-40+10, 5, 5);
				   g.fillRect(10, this.getHeight()-10-40+20, 20, 5);
			   }
			   //g.setColor(Color.orange);
			   for(int c=0,t,x,y,f;c<obj.size();c++){
				  t=obj.get(c).getType();
				  x=obj.get(c).getX();
				  y=obj.get(c).getY();
				  f=obj.get(c).getFlap();
				  if(t==0){
					  //sapling
					  g.setColor(new Color(139,69,19));
					  g.fillRect(x+10, this.getHeight()-50, 20, 40);
					  g.setColor(Color.green);
					  g.fillOval(x, this.getHeight()-80, 40, 40);
					  if(x<40&&(height==1||height==2)){
						  start=false;
					  }
				  }
				  else if(t==1){
					  //fence
					  g.setColor(new Color(139,69,19));
					  g.fillRect(x, this.getHeight()-50, 10, 40);
					  g.fillRect(x+30, this.getHeight()-50, 10, 40);
					  g.fillRect(x-5, this.getHeight()-23, 50, 7);
					  g.fillRect(x-5, this.getHeight()-41, 50, 7);
					  if(x<40&&(height==1||height==0)){
						  start=false;
					  }
				  }
				  else if(t==2){
					  //bird
					  g.setColor(new Color(15,15,15));
					  if(f<=0){
					  g.drawString("v", x,this.getHeight()- 110);}
					  else{
						  g.drawString("^", x,this.getHeight()-  102);
					  }
					  if(x<40&&(height==2)){
						  start=false;
					  }
				  }
				  else if(t==3){
					  //tree
					  g.setColor(new Color(139,69,19));
					  g.fillRect(x+5, this.getHeight()-10-125-y, 30, 125+y);
					  g.setColor(Color.green);
					  g.fillOval(x-60, this.getHeight()-125-160-y, 160, 160);
				  }
				  else if(t==4){
					  //bird
					  g.setColor(new Color(15,15,15));
					  if(f<=0){
					  g.drawString("v", x,this.getHeight()- 160-y);}
					  else{
						  g.drawString("^", x,this.getHeight()-  160-y);
					  }
				  }
				  g.setColor(Color.darkGray);  
				  g.setFont(new Font("Trebuchet MS", Font.PLAIN, 20));
			g.drawString(""+(time<4996800?
					((int)time)
					:"Good job, you ran around the world!")
					, this.getWidth()-(time<4996800?100:450), 20);
			g.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
				  if(start==false){
					  if(diff==1&&time>best1){
						 best1=(int)time; }
					  if(diff==2&&time>best2){
							 best2=(int)time; }
					  if(diff==3&&time>best3){
							 best3=(int)time; }
					time=0;  
				  }
			   }
		   }else{
			   g.setColor(Color.darkGray);
			   g.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
			   g.drawString("Jump", 275, 30);
			   g.setFont(new Font("Trebuchet MS", Font.PLAIN, 15));
			   
			   
			   g.drawString("Controls: "+KeyEvent.getKeyText(Preferences.UP())+" to jump, "+KeyEvent.getKeyText(Preferences.DOWN())+" to slide, Escape to exit.", 130, 30+50);
			   g.drawString("Jump over fences, slide under saplings, and don't touch the birds!", 90, 50+50);
			   g.drawString("press a number to select difficulty. press space to start.", 110, 90+50);
	       g.drawString("1=easy", 75, 110+50);
	       g.drawString("2=normal", 275, 110+50);
	       g.drawString("3=hard", 475, 110+50);
	       g.drawString("best", 20, 130+50);
	       g.drawString(""+best1, 85, 130+50);
	       g.drawString(""+best2, 285, 130+50);
	       g.drawString(""+best3, 485, 130+50);
	       g.drawString("Space to start",270,175+50);
	       g.drawRect(45+(diff-1)*200, 95+50, 130, 20);
	}
}
	}
//types
//0=sapling
//1=fence
//2=bird
//3=tree
//4=bird

}
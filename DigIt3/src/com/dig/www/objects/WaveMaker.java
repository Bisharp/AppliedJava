package com.dig.www.objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.dig.www.enemies.WaveTrackingEnemy;
import com.dig.www.start.Board;
import com.dig.www.start.DigIt;
import com.dig.www.util.Statics;

public class WaveMaker extends Objects{
private int waveSize;
private int count;
private int waveX;
private int waveY;
private String enLoc;
private int waveNum=1;
private int anNum;
private int anTimer;
private boolean timerGoing=true;
private int waveTimer=(60/owner.mult()*300);
	public WaveMaker(int x, int y,String waveStuff, Board owner) {
		super(x, y, "images/effects/shadow/0.png",false, owner, "update");
		// TODO Auto-generated constructor stub
		//loc="images/effects/shadow/";
		String[]waveProps=waveStuff.split("\\|");
		for(String s:waveProps)
			System.out.println(s);
		this.enLoc=waveProps[0];
		this.waveSize=Integer.parseInt(waveProps[1]);
		this.waveX=Integer.parseInt(waveProps[2]);
		if(waveProps.length>3)
		this.waveY=Integer.parseInt(waveProps[3]);
		else
			waveY=waveX;
	}
	public void makeWave(){
	for(int c=0;c<waveSize+waveNum-2;c++){
		int d=Statics.RAND.nextInt(4);
		int enX=x+(Statics.RAND.nextInt(waveX*2+1)-waveX);
		int enY =y+(Statics.RAND.nextInt(waveY*2+1)-waveY);
		switch(d){
		case 0:
			enX=x+waveX;
			break;
		case 1:
			enY=y-waveY;
			break;
		case 2:
			enX=x-waveX;
			break;
		case 3:
			enY=y+waveY;
		}
		
		
		owner.getEnemies().add(new WaveTrackingEnemy(enX,enY, enLoc, owner, true, 100));
	}
	count=waveSize+waveNum-1;
	}
	@Override
		public void animate() {
			// TODO Auto-generated method stub
			super.animate();
			if(timerGoing){
				waveTimer--;
				if(waveTimer<0){
				makeWave();
				timerGoing=false;}
			}else{
			boolean b=false;
			count=0;
			for(int c=0;c<owner.getEnemies().size();c++){
				if(owner.getEnemies().get(c) instanceof WaveTrackingEnemy){
					b=true;
					count++;
				}
			}
			if(!b){
				waveNum++;
				timerGoing=true;
				waveTimer=(60/owner.mult()*30);
			}
			
			}if(anTimer<0){
				anTimer=10;
				anNum++;
				if(anNum>3)
					anNum=0;
				image=newImage("images/effects/shadow/"+anNum+".png");
				shadow=newShadow("images/effects/shadow/"+anNum+".png");
			}anTimer--;
		}
	@Override
	public void draw(Graphics2D g2d) {
		super.draw(g2d);
		g2d.setColor(Color.white);
		g2d.drawString("Wave:"+(waveNum), x+5, y+25);
		if(timerGoing)
		g2d.drawString("Timer:"+waveTimer/(60/owner.mult()), x+5, y+60);
		else
		g2d.drawString("Enemies:"+count, x+5, y+60);
	}
@Override
public boolean interact() {
	int a=(timerGoing?2:1);
String[]options=new String[a];
options[0]="Leave";
if(a==2)
options[1]="Start Wave";
boolean b=JOptionPane.showOptionDialog(owner, "This is a Wave Spawner."+(timerGoing?"\nA new wave begins in "+waveTimer/(60/owner.mult())+" seconds.":""), DigIt.NAME+" Item Description", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, new ImageIcon(image), options, "Leave")==1;
if(b){
	waveTimer=0;
}
return true;
}
}

package com.dig.www.objects;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.dig.www.character.GameCharacter;
import com.dig.www.character.Items;
import com.dig.www.enemies.DigSenseEnemy;
import com.dig.www.start.Board;
import com.dig.www.start.DigIt;
import com.dig.www.util.ListenerOfAnAction;

public class StrangeBookTable extends Objects implements ListenerOfAnAction{
private boolean once;
private int poemNum;
private boolean putStuff;
	public StrangeBookTable(int x, int y, boolean wall, Board owner,String loc) {
		super(x, y, "images/objects/householdObjects/tableBook.png", true, owner, "update");
		// TODO Auto-generated constructor stub
		putStuff=wall;
		if(putStuff)
		owner.getEnemies().add(new DigSenseEnemy(x-50, y+450, owner, true, this));
		try{
		this.poemNum=Integer.parseInt(loc);}
		catch(Exception ex){
			poemNum=0;
		}
	}
	@Override
	public void basicAnimate() {
		// TODO Auto-generated method stub
		super.basicAnimate();
	if(!once){
		once=true;
		if(putStuff)
		owner.getObjects().add(new DangerousFire(x-250, y+50, owner));
	}
	}
	public String getNum(int i){
		switch(i){
		case 3:
			return " third";
		case 4:
			return " fourth";
		case 5:
			return " fifth";
		case 2:
		default:
			return"nother";
		}
	}
@Override
public boolean interact() {
	// TODO Auto-generated method stub
	String[]options={"Leave","Read"};
	boolean b=JOptionPane.showOptionDialog(owner,poemNum==0?"This table has nothing on it except a strange book.":"A"+getNum(poemNum+1)+" strange book.", DigIt.NAME
			+ " Item Description",JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
			new ImageIcon(image),options,"Leave"
			)==1;
	if(b)
		JOptionPane.showMessageDialog(owner,getPoem()
				,DigIt.NAME+" Item Description",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(image));
	return b;
}
@Override
public void actionDone() {
	// TODO Auto-generated method stub
	GameCharacter.getInventory().addItem(Items.GRAVEBOX, 1);
}
public String getPoem(){
	switch(poemNum){
	case 1:
		return "<html>Most of the pages are too worn to read, but a different page is readable this time.\nIt reads:\n"
				+ "<html><i>There once was a dangerous stone;\n"
				+ "<html><i>it threatened the lives of all.\n"
				+ "<html><i>One man endeavored to hide it away,\n"
				+ "<html><i>but his mission was only complete in part,\n"
				+ "<html><i>and the stone lying near is a peril still.";
	case 2:
		return "<html>Most of the pages are too worn to read, but a different page from the others is readable this time.\nIt reads:\n"
				+ "<html><i>Look away from the rising sun,\n"
				+ "<html><i>which here is so rarely seen.\n"
				+ "<html><i>Go in the way of coming darkness,\n"
				+ "<html><i>away from the fiery light,\n"
				+ "<html><i>and find the key to what bars the way.";
	case 3:
		return "<html>Most of the pages are too worn to read, but a different page from the others is readable this time.\nIt reads:\n"
		+ "<html><i>The treacherous stone grows stronger,\n"
		+ "<html><i>wielding the power of storms.\n"
		+ "<html><i>Keep away from the open sky,\n"
		+ "<html><i>as bolts of light pour down,\n"
		+ "<html><i>plunge into the darkness instead.";
	case 4:
		return "<html>Most of the pages are too worn to read, but a different page from the others is readable this time.\nIt reads:\n"
		+ "<html><i>The monsterous stone is uncontrolable;\n"
		+ "<html><i>the journey must come to an end.\n"
		+ "<html><i>Go back to the open,\n"
		+ "<html><i>advance quickly however,\n"
		+ "<html><i>and end this once and for all.";
	case 0:
	default:
	return	"<html>Most of the pages are too worn to read, but one seems to have aged well.\nIt reads:\n"
		+ "<html><i>Look to the heavens and away from the greatest star,\n"
		+ "<html><i>for there lies a man who died on a quest.\n"
		+ "<html><i>Under his stone an object rests,\n"
		+ "<html><i>which together with a dangerous light,\n"
		+ "<html><i>will be the key to what is sought.";
	}
}
}

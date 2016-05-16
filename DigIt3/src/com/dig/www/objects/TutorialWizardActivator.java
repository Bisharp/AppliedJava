package com.dig.www.objects;

import com.dig.www.npc.TutorialWizard;
import com.dig.www.start.Board;

public class TutorialWizardActivator extends SensorObject{

	public TutorialWizardActivator(int x, int y, Board owner) {
		super(x, y, true, owner);
		height=200;
	}
private int a;
private boolean ago;
@Override
public void basicAnimate() {
	super.basicAnimate();
	if(ago){
		owner.talk(owner.getNPCs().get(a));
		ago=false;}
}
	@Override
	public void action() {
		for(int c=0;c<owner.getNPCs().size();c++){
			if(owner.getNPCs().get(c) instanceof TutorialWizard){
				//owner.getNPCs().get(c).act(null);
				((TutorialWizard) owner.getNPCs().get(c)).next();
				a=c;
				ago=true;
				break;
			}
		}
	}
}

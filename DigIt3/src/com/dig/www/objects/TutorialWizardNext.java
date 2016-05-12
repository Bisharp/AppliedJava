package com.dig.www.objects;

import com.dig.www.npc.TutorialWizard;
import com.dig.www.start.Board;

public class TutorialWizardNext extends SensorObject{

	public TutorialWizardNext(int x, int y, Board owner) {
		super(x, y,true, owner);
	}

	@Override
	public void action() {
	for(int c=0;c<owner.getNPCs().size();c++){
		if(owner.getNPCs().get(c) instanceof TutorialWizard){
			((TutorialWizard) owner.getNPCs().get(c)).next();
		}
	}
	}

}

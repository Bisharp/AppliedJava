package com.dig.www.objects;

import com.dig.www.start.Board;

public class LightSource extends Mirror{

	public LightSource(int x, int y, Board owner, boolean pushAble) {
		super(x, y, owner, pushAble);
		// TODO Auto-generated constructor stub
	}
@Override
protected boolean lightOn() {
	// TODO Auto-generated method stub
	return true;
}
}

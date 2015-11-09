package com.dig.www.npc;

import com.dig.www.start.Board;

public class PLATO extends NPC {
	public PLATO(int x, int y, String loc, Board owner, String location) {
		super(x, y, loc, owner, new String[] {
				"But there's n0 sense crying 0ver every mist@ke, y0u just keep 0n trying til' you run 0ut of... I'm s0rry, did y0u w@nt s0mething?",
				"The w0rld's ste@lthiest l@nd @nim@l is the blue wh@le.", "W0uld I lie t0 y0u?",
				"He w@nted t0 n@me me KSAAITKCONWHGB, but I t0ld him th@t w0uld be t00 e@sy t0 pr0n0unce.",
				"Wh@t d0es PLATO st@nd f0r? 0bvi0usly it st@nds f0r P0lite L0v@ble Awes0me Truthful... wh@t's @ c0mpliment th@t st@rts with 0?" },
				PLATO, location, new NPCOption[] {});
	}

	@Override
	public String exitLine() {
		// TODO Auto-generated method stub
		return "@lways remember th@t f0r every 1 m0le 0f @ substance, y0u h@ve 20.6 x 10 t0 the 32nd @t0ms.";
	}
}

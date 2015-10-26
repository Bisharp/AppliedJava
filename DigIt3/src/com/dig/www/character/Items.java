package com.dig.www.character;

import java.io.Serializable;

public enum Items implements Serializable {

	TEST0 {
		@Override
		String getPersonalDesc() {
			return "A test object.";
		}
	},
	NULL {
		@Override
		String getPersonalDesc() {
			return "<html>A value for items which do not go in the inventory. If you are reading this and <i>not</i> snooping in the code, <i>do not</i> use this item, as it may cause undefined behavior.";
		}
	},
	INVISIBLE_CLOAK {
		public String toString() {
			return "Invisible Cloak";
		}

		@Override
		String getPersonalDesc() {
			return "<html>A bright blue cloak found in The Wizard's tower. What's this? The tag says \"Invisible Cloak.\" Well, <i>that</i> can't be true; I can see it just fine.";
		}
	};

	public static Items translate(String string) {
		for (Items i : Items.values())
			if (i.toString().equals(string))
				return i;

		return NULL;
	}

	public static String getDesc(String string) {
		for (Items i : Items.values())
			if (i.toString().equals(string))
				return i.getPersonalDesc();

		return NULL.getPersonalDesc();
	}

	abstract String getPersonalDesc();
}

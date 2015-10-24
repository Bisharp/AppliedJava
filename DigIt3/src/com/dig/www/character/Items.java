package com.dig.www.character;

import java.io.Serializable;

public enum Items implements Serializable {
	
	TEST0
//	{
//		public String toString() {
//			System.out.println("toString() called on TEST0");
//			return super.toString();
//		}
//	}
	, TEST1, NULL;

	public static Items translate(String string) {
		for (Items i : Items.values())
			if (i.toString().equals(string))
				return i;
		
		return NULL;
	}
}

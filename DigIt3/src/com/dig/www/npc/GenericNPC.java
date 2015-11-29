package com.dig.www.npc;

import com.dig.www.start.Board;

public class GenericNPC extends NPC {
	public enum GenericType {
		TYPE1 {
			public String[] getGreetings() {
				return new String[] { "Hello1", "Hi1" };
			}

			public String getExit() {
				return "Good-bye1";
			}
		},
		TYPE2 {
			public String[] getGreetings() {
				return new String[] { "Hello2", "Hi2" };
			}

			public String getExit() {
				String[] exits = { "Good-bye2", "Bye2" };
				return exits[(int) (Math.random() * 2)];
			}
		},
		FAIL {
			public String[] getGreetings() {
				return new String[] { "I am broken", "Something broke" };
			}

			public String getExit() {
				return "I hope I am fixed";
			}
		};
		public abstract String[] getGreetings();

		public abstract String getExit();
	}

	private GenericType type;
	private String name;

	public GenericNPC(int x, int y, String loc, Board owner, String type,
			String s, String location, String name) {

		super(x, y, loc, owner, getTheGreetings(type), s, location,
				getTheOptions(type));

		this.type = toType(type);
		this.name = name;
	}

	public static GenericType toType(String type) {
		switch (type) {
		case "type1":
			return GenericType.TYPE1;
		case "type2":
			return GenericType.TYPE2;
		default:
			return GenericType.FAIL;
		}
	}

	public static String[] getTheGreetings(String type) {
		return toType(type).getGreetings();
	}

	public static NPCOption[] getTheOptions(String type) {
		// TODO implement
		return new NPCOption[] {};
	}

	@Override
	public String exitLine() {
		return type.getExit();
	}

	@Override
	public String getShowName() {
		return name;
	}
}

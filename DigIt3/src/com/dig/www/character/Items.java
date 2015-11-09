package com.dig.www.character;

import java.io.Serializable;

import com.dig.www.util.Statics;

public enum Items implements Serializable {

	NULL {
		@Override
		protected String getPersonalDesc() {
			return "<html>A value for items which do not go in the inventory. If you are reading this (and not looking in the code), <i>do not</i> use this item, as it may cause undefined behavior.";
		}

		@Override
		public String getPath() {
			return "images/icon.png";
		}

		@Override
		public boolean isThrowable() {
			return false;
		}

		@Override
		public boolean isWeapon() {
			return false;
		}
	},
	SPECIAL_COLLECTIBLE {
		@Override
		protected String getPersonalDesc() {
			return "<html>An item required to progress through the game;<br>full decription to be written.";
		}

		@Override
		public String getPath() {
			return "images/objects/collectibles/special.png";
		}

		@Override
		public String toString() {
			return "Golden Troll Face";
		}

		@Override
		public boolean isThrowable() {
			return false;
		}

		@Override
		public boolean isWeapon() {
			return false;
		}
	},
	INVISIBLE_CLOAK {
		public String toString() {
			return "Invisible Cloak";
		}

		@Override
		protected String getPersonalDesc() {
			return "<html>A bright blue cloak found in The Wizard's tower. What's this? The tag says \"Invisible Cloak.\"<br>Well, <i>that</i> can't be true; I can see it just fine!";
		}

		@Override
		public String getPath() {
			return "images/objects/inventoryCollectibles/notSoInvisibleCloak.png";
		}

		@Override
		public boolean isThrowable() {
			return true;
		}

		@Override
		public boolean isWeapon() {
			return false;
		}
	},
	FOOD_NORMAL {
		@Override
		protected String getPersonalDesc() {
			return null;
		}

		@Override
		public String getPath() {
			return "images/objects/food/" + Statics.RAND.nextInt(Statics.getFolderCont("images/objects/food/")) + ".png";
		}

		@Override
		public boolean isThrowable() {
			return false;
		}

		@Override
		public boolean isWeapon() {
			return false;
		}
	},
	PROJECTILE {
		@Override
		protected String getPersonalDesc() {
			return null;
		}

		@Override
		public String getPath() {
			return "images/objects/food/" + Statics.RAND.nextInt(Statics.getFolderCont("images/objects/food/")) + ".png";
		}

		@Override
		public boolean isThrowable() {
			return false;
		}

		@Override
		public boolean isWeapon() {
			return false;
		}
	},
	BANANA {
		
		@Override
		public String toString() {
			return "Banana";
		}
		
		@Override
		protected String getPersonalDesc() {
			// TODO Auto-generated method stub
			return "<html>A banana, supposedly created by The Wizard using magic.<br>It <i>does</i> appear to have a Sunny Farm Fruits sticker on it, though...";
		}

		@Override
		public String getPath() {
			return "images/objects/food/banana.png";
		}

		@Override
		public boolean isThrowable() {
			return false;
		}

		@Override
		public boolean isWeapon() {
			return false;
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

	protected abstract String getPersonalDesc();

	public abstract String getPath();

	public abstract boolean isThrowable();

	public abstract boolean isWeapon();

	public int getDamage() {
		return 0;
	}

	public static Items getRandItem() {

		Items i;

		do {
			i = values()[Statics.RAND.nextInt(values().length)];
		} while (i == Items.SPECIAL_COLLECTIBLE || i == Items.NULL || i == Items.PROJECTILE);

		return i;
	}
}

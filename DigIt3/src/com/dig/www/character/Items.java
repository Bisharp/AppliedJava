package com.dig.www.character;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import com.dig.www.start.Board;
import com.dig.www.util.Statics;

public enum Items implements Serializable {
POTATOGUY{
	@Override public String toString(){
		return "Potato Guy";
	}
	@Override
	protected String getPersonalDesc(Board b) {
		// TODO Auto-generated method stub
		return "Is this thing alive?";
	}

	@Override
	public String getPath() {
		// TODO Auto-generated method stub
		return "images/objects/food/PotatoGuy.png";
	}

	@Override
	public boolean isThrowable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isWeapon() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isConsumable() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public void doAct(Board owner) {
		// TODO Auto-generated method stub
		owner.getCharacter().poison(true);
	}
	@Override
	public boolean stop() {
		// TODO Auto-generated method stub
		return true;
	}
},NandNs{
	@Override public String toString(){
		return "N&Ns";
	}
	@Override
	protected String getPersonalDesc(Board b) {
		// TODO Auto-generated method stub
		return "N&Ns: Chocolate balls of goodness.";
	}

	@Override
	public String getPath() {
		// TODO Auto-generated method stub
		return "images/objects/food/Candy.png";
	}

	@Override
	public boolean isThrowable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isWeapon() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isConsumable() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public void doAct(Board owner) {
		// TODO Auto-generated method stub
		owner.getCharacter().heal(50);
	}
	
},
	NULL {
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Null";
	}
		@Override
		protected String getPersonalDesc(Board b) {
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
	KEYCRYSTAL {
		@Override
		protected String getPersonalDesc(Board b) {
			return "A Key Crystal. Enough of these will open special red portals.";
		}

		@Override
		public String getPath() {
			return "images/objects/collectibles/keyCrystal/1.png";
		}

		@Override
		public String toString() {
			return "Key Crystal";
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
		protected String getPersonalDesc(Board b) {
			return "<html>A bright blue cloak found in The Wizard's tower. What's this? The tag says \"Invisible Cloak.\"<br>Well, <i>that</i> can't be true; I can see it just fine!";
		}

		@Override
		public String getPath() {
			return "images/objects/InventoryObjects/notSoInvisibleCloak.png";
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
	PROJECTILE {
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "Projectile";
		}
		@Override
		protected String getPersonalDesc(Board b) {
			return null;
		}

		@Override
		public String getPath() {
			return "images/objects/food/" + 0 + ".png";
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
		protected String getPersonalDesc(Board b) {
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
	},
	DONUT {
		
		@Override
		public String toString() {
			return "Donut";
		}
		@Override
		protected String getPersonalDesc(Board b) {
			return "That cop asked for a donut. We could probably give him this one. I hope he likes food coloring.";
		}
		@Override
		public String getPath() {
			return "images/objects/food/DonutUnhealthyAmountOfFoodColoring.png";
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
	WHATISTHIS {
		@Override
		public String toString(){
			return "????";
		}
		@Override
		protected String getPersonalDesc(Board b){
			return "I have no idea what this is or why we kept it.";
		}
		@Override
		public String getPath() {
			return "images/objects/InventoryObjects/whatIsThis.png";
		}
		@Override 
		public boolean isThrowable() {
			return true;
		}
		@Override
		public boolean isWeapon() {
			return true;
		}
	},
	GEM {
		@Override public String toString(){
			return "Gem";
		}
		@Override protected String getPersonalDesc(Board b){
			return "Guys, do you think maybe this was in the chest because WE WEREN'T SUPPOSED TO TAKE IT?!?!";
		}
		@Override public String getPath() {
			return "images/objects/InventoryObjects/gem.png";
		}
		@Override public boolean isThrowable() {
			return true;
		}
		@Override public boolean isWeapon() {
			return true;
		}
	},
	VIDEO_GAME {
		@Override public String toString(){
			return "Video Game Console";
		}
		@Override protected String getPersonalDesc(Board b){
			return "Video games are fun. Wouldn't it be so weird if WE were actually in a video game?";
		}
		@Override public String getPath() {
			int n = Statics.RAND.nextInt(8) + 1;
			return ("images/objects/InventoryObjects/videoGame" + n + ".png");
		}
		@Override public boolean isThrowable() {
			return false;
		}
		@Override public boolean isWeapon() {
			return false;
		}
	},
	JUMP {
		@Override public String toString(){
			return "Jump Video Game";
		}
		@Override protected String getPersonalDesc(Board b){
			return "<html>A video game called Jump. You can only play Jump if you have a <i>Video Game Console</i>.";
		}
		@Override public String getPath() {
			return "images/objects/InventoryObjects/videoGame8.png";
		}
		@Override public boolean isThrowable() {
			return false;
		}
		@Override public boolean isWeapon() {
			return false;
		}
	},
	BLACK_ORB{
		@Override public String toString(){
			return "Sinister Black Orb of Ultimate Agony and Suffering";
		}
		@Override protected String getPersonalDesc(Board b){
			return "It feels sort of tingly...";
		}
		@Override public String getPath(){
			return "images/objects/InventoryObjects/blackOrb.png";
		}
		@Override public boolean isThrowable() {
			return false;
		}
		@Override public boolean isWeapon() {
			return false;
		}
	},
	WIZARD_HAT{
		@Override public String toString(){
			return "Wizard Hat";
		}
		@Override protected String getPersonalDesc(Board b){
			return "The tag on the inside says \"If lost, draw a chalk circle around it, chant the magic words, and put it in the mail addressed to the forest tower.\"";
		}
		@Override public String getPath(){
			return "images/objects/InventoryObjects/wizardHat.png";
		}
		@Override public boolean isThrowable() {
			return false;
		}
		@Override public boolean isWeapon() {
			return false;
		}
	},
	COBALT_HAT{
		@Override public String toString(){
			return "Cobalt Hat";
		}
		@Override protected String getPersonalDesc(Board b){
			return "It looks exactly like Sir Cobalt's. Does wearing it make me look as cool as him?";
		}
		@Override public String getPath(){
			return "images/objects/InventoryObjects/cobaltHat.png";
		}
		@Override public boolean isThrowable() {
			return false;
		}
		@Override public boolean isWeapon() {
			return false;
		}
	},
	GOGGLES{
		@Override public String toString(){
			return "Goggles";
		}
		@Override protected String getPersonalDesc(Board b){
			return "A spare pair of Dr. Kepler's lab goggles. How can he see through these?";
		}
		@Override public String getPath(){
			return "images/objects/InventoryObjects/keplerGoggles.png";
		}
		@Override public boolean isThrowable() {
			return false;
		}
		@Override public boolean isWeapon() {
			return false;
		}
	},
	CAPE{
		@Override public String toString(){
			return "Cape";
		}
		@Override protected String getPersonalDesc(Board b){
			return "This looks like it probably belongs to Super Macaroni Noodle Man. It could use some cleaning.";
		}
		@Override public String getPath(){
			return "images/objects/InventoryObjects/cheesyCape.png";
		}
		@Override public boolean isThrowable() {
			return false;
		}
		@Override public boolean isWeapon() {
			return false;
		}
	},
	BACON{
		@Override public String toString(){
			return "Bacon";
		}
		@Override protected String getPersonalDesc(Board b){
			return "Everybody loves this stuff. Except for vegetarians.";
		}
		@Override public String getPath(){
			return "images/objects/food/Bacon.png";
		}
		@Override public boolean isThrowable(){
			return false;
		}
		@Override public boolean isWeapon(){
			return false;
		}
	},
	BACONGOLDEN{
		@Override public String toString(){
			return "Ultimate Bacon";
		}
		@Override protected String getPersonalDesc(Board b){
			return "Destiny says that legend spoke of bacon that put all other bacon to shame. Apparently if I eat it everything else will taste terrible by comparison. I... think I'll pass.";
		}
		@Override public String getPath(){
			return "images/objects/food/BaconGolden.png";
		}
		@Override public boolean isThrowable(){
			return false;
		}
		@Override public boolean isWeapon(){
			return false;
		}
	},PLAINKEY{
		@Override
		protected String getPersonalDesc(Board b) {
		String s="A normal key.\nIt will only open doors in the area it was found in.\nYou have "+(b.getKeyMap().containsKey(b.getLevel())?b.getKeyMap().get(b.getLevel()):"0")+" from this area.";
		String[]keys=b.getKeyMap().keySet().toArray(new String[0]);
		Integer[]vals=b.getKeyMap().values().toArray(new Integer[0]);
		for(int i=0;i<vals.length;i++){
		s+="\n"+vals[i]+" from "+keys[i]+".";
		}
		return s;
		}

		@Override
		public String getPath() {
			// TODO Auto-generated method stub
			return "images/objects/InventoryObjects/plainKey.png";
		}

		@Override
		public boolean isThrowable() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isWeapon() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "Plain Key";
		}
		
	},GRAVEBOX{

		@Override
		protected String getPersonalDesc(Board b) {
			// TODO Auto-generated method stub
			return "This is a box found under the tombstone of an adventurer.\nWhat could the dangerous light be?";
		}

		@Override
		public String getPath() {
			// TODO Auto-generated method stub
			return "images/objects/InventoryObjects/graveBox.png";
		}

		@Override
		public boolean isThrowable() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isWeapon() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "Box from a grave";
		}
		
	},
	URN{
		@Override public String toString(){
			return "Ancient Urn";
		}
		@Override protected String getPersonalDesc(Board b){
			return "An ancient urn depicting a man watching television. Perhaps it is not as old as it first appeared to be...";
		}
		@Override public String getPath(){
			return "images/objects/InventoryObjects/ancientUrn.png";
		}
		@Override public boolean isThrowable() {
			return false;
		}
		@Override public boolean isWeapon() {
			return false;
		}
	}
	;


	public static Items translate(String string) {
		for (Items i : Items.values())
			if (i.toString().equals(string)){
				return i;}

		return NULL;
	}

	public static String getDesc(String string,Board b) {
		for (Items i : Items.values())
			if (i.toString().equals(string))
				return i.getPersonalDesc(b);

		return NULL.getPersonalDesc(b);
	}

	protected abstract String getPersonalDesc(Board b);

	public abstract String getPath();

	public abstract boolean isThrowable();
	public boolean isConsumable(){
		return false;
	}
	
	public abstract boolean isWeapon();

	public int getDamage() {
		return 0;
	}

	public static Items getRandItem() {

		Items i;

		do {
			i = values()[Statics.RAND.nextInt(values().length)];
		} while (i == Items.KEYCRYSTAL || i == Items.NULL || i == Items.PROJECTILE);

		return i;
	}
	public void doAct(Board owner){
	}
	public abstract String toString();
	public boolean stop(){
		return false;
	}
}

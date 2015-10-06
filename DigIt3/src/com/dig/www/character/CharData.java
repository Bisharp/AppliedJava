package com.dig.www.character;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;

import com.dig.www.objects.Objects;
import com.dig.www.objects.SpecialCollectible;
import com.dig.www.start.Board;

public class CharData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Hashtable<String, LevelData> collectibles = new Hashtable<String, LevelData>();
	private String currentKey;
	private transient Board owner;
	private int collectibleNum = 0;

	public CharData(String key, Board owner) {
		currentKey = key;
		this.owner = owner;

		collectibles.put(key, new LevelData(owner.getObjects()));
	}

	public void enterLevel(String level) {

		collectibles.putIfAbsent(level, new LevelData(owner.getObjects()));
		currentKey = level;
	}

	public ArrayList<Objects> filter(ArrayList<Objects> input) {
		return collectibles.get(currentKey).filter(input);
	}

	public boolean hasBeenCollected(int address) {
		return collectibles.get(currentKey).hasCollected(address);
	}

	public void setOwner(Board owner) {
		this.owner = owner;
	}

	public void collect(int id) {
		if (collectibles.get(currentKey).collect(id))
			collectibleNum++;
	}

	public int getCollectibleNum() {
		return collectibleNum;
	}

	private class LevelData implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private Hashtable<Integer, Boolean> data = new Hashtable<Integer, Boolean>();

		private LevelData(ArrayList<Objects> objectList) {

			for (Objects obj : objectList)
				if (obj instanceof SpecialCollectible)
					data.put(((SpecialCollectible) obj).id, false);
		}

		// Returns whether or not the specified collectible has been collected
		// yet.
		public boolean hasCollected(int address) {
			return data.get(address);
		}

		// Sets the collectible specified by the address as collected
		public boolean collect(int address) {

			if (data.get(address) == true)
				return false;

			data.replace(address, true);
			return true;
		}

		// Returns an ArrayList<Object> that has removed any already collected
		// SpecialCollectibles from the list.
		public ArrayList<Objects> filter(ArrayList<Objects> input) {

			Objects obj;
			ArrayList<Objects> objList = new ArrayList<Objects>();

			for (int i = 0; i < input.size(); i++) {
				obj = input.get(i);
				if (!(obj instanceof SpecialCollectible))
					objList.add(obj);
				else if (data.containsKey(((SpecialCollectible) obj).id) ? !hasCollected(((SpecialCollectible) obj).id) : true)
					objList.add(obj);
			}

			return objList;
		}

	}
}

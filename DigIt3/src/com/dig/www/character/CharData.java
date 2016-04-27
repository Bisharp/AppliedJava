package com.dig.www.character;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import com.dig.www.blocks.BossPortal;
import com.dig.www.blocks.Door;
import com.dig.www.blocks.Portal;
import com.dig.www.npc.BlockerNPC;
import com.dig.www.npc.NPC;
import com.dig.www.npc.QuestNPC;
import com.dig.www.npc.Reyzu;
import com.dig.www.objects.DropPoint;
import com.dig.www.objects.Objects;
import com.dig.www.objects.KeyCrystal;
import com.dig.www.start.Board;
import com.dig.www.util.Quest;
import com.dig.www.util.Statics;

public class CharData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Hashtable<String, LevelData> areas = new Hashtable<String, LevelData>();
	private ArrayList<SimpleQuest> quests = new ArrayList<SimpleQuest>();
	private String currentKey;
	private transient Board owner;
	private int collectibleNum = 0;

	public CharData(String key, Board owner) {
		currentKey = key;
		this.owner = owner;

		areas.put(key, new LevelData(owner.getObjects(), owner.getNPCs(),owner.getPortals(), key));
	}

	// SpecialCollectible
	public void enterLevel(String level) {

		if (!areas.containsKey(level))
			areas.put(level, new LevelData(owner.getObjects(), owner.getNPCs(),owner.getPortals(),
					level));

		currentKey = level;
	}

	public ArrayList<Objects> filter(ArrayList<Objects> input) {
		return areas.get(currentKey).filter(input);
	}

	public ArrayList<NPC> filterNPC(ArrayList<NPC> input) {
		return areas.get(currentKey).filterNPC(input);
	}
public ArrayList<Portal>filterPortals(ArrayList<Portal>input){
	return areas.get(currentKey).filterPortals(input);
}
	public boolean hasBeenCollected(int address) {
		return areas.get(currentKey).hasCollected(address);
	}

	public void setOwner(Board owner) {
		this.owner = owner;
	}

	public void collect(int id) {
		if (areas.get(currentKey).collect(id))
			collectibleNum++;
	}

	public int getCollectibleNum() {
		return collectibleNum;
	}

	// Quest Generation

	// TODO repair
	// TODO repaired ;)
	public String[] getAreas() {

		Enumeration<String> keys = areas.keys();
		ArrayList<String> aS = new ArrayList<String>();
		try {
			String key;
			while (true) {
				key = keys.nextElement();
				if (areas.get(key).hasDropPoints())
					aS.add(key);
			}
		} catch (Exception ex) {
		}
		return aS.toArray(new String[aS.size()]);
	}

	public void registerQuest(QuestNPC npc) {
		areas.get(currentKey).registerQuest(npc.id);
	}

	public void setAcceptedPhase(QuestNPC npc, int phase) {
		// TODO Auto-generated method stub
		areas.get(currentKey).getQuest(npc.id).setAcceptedPhase(phase);
	}

	public int getAcceptedPhase(QuestNPC npc) {
		return areas.get(currentKey).getQuest(npc.id).getAcceptedPhase();
	}

	public void setAppearPhase(QuestNPC npc, int setter) {
		areas.get(currentKey).getQuest(npc.id).setAppearPhase(setter);
	}

	public void completeQuest(QuestNPC npc) {
		areas.get(currentKey).completeQuest(npc.id);
	}

	public boolean hasQuests() {

		for (SimpleQuest i : quests)
			if (i.place.equals(currentKey) && i.isAccepted())
				return true;
		return false;
	}

	// end

	public void clearBlocker(int id) {
		areas.get(currentKey).clearBlocker(id);
	}

	public class LevelData implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private Hashtable<Integer, Boolean> specialCollectibles = new Hashtable<Integer, Boolean>();
		private Hashtable<Integer, SimpleQuest> locQuests = new Hashtable<Integer, SimpleQuest>();
		private Hashtable<Integer, Boolean> blockerNPCs = new Hashtable<Integer, Boolean>();
		private Hashtable<Integer,Boolean>portals = new Hashtable<Integer,Boolean>();
		private final String location;
		// Quest generation
		private boolean hasDropPoints = false;

		// end

		private LevelData(ArrayList<Objects> objectList, ArrayList<NPC> npcs,ArrayList<Portal>portalList,
				String name) {

			for (Objects obj : objectList)
				if (obj instanceof KeyCrystal)
					specialCollectibles.put(((KeyCrystal) obj).id,
							false);
				else if (obj instanceof DropPoint)
					hasDropPoints = true;
for(int c=0;c<portalList.size();c++)
	if(portalList.get(c) instanceof BossPortal)
		portals.put(c, ((BossPortal) portalList.get(c)).getCollectibleNum()==0);
	else if(portalList.get(c) instanceof Door)
		portals.put(c, ((Door) portalList.get(c)).isLocked());
			QuestNPC qNPC;
			for (NPC npc : npcs)
				if (npc instanceof QuestNPC) {
					qNPC = (QuestNPC) npc;
					SimpleQuest s = new SimpleQuest(qNPC.getType(),
							qNPC.getPlace(), name, qNPC.id);
					quests.add(s);
					locQuests.put(s.id, s);
				} else if (npc instanceof BlockerNPC
						&& ((BlockerNPC) npc).id != -1)
					blockerNPCs.put(((BlockerNPC) npc).id, true);

			location = name;
			System.out.println("New level data created for " + name
					+ ". We have " + (hasDropPoints ? "" : "no ")
					+ "drop points.");
		}

		public void clearBlocker(int id) {
			if (id != -1)
				blockerNPCs.put(id, false);
		}

		// Returns whether or not the specified collectible has been collected
		// yet.
		public boolean hasCollected(int address) {
			return specialCollectibles.get(address);
		}

		// Sets the collectible specified by the address as collected
		public boolean collect(int address) {

			if (specialCollectibles.get(address) == true)
				return false;
specialCollectibles.remove(address);
			specialCollectibles.put(address, true);
			return true;
		}

		// Returns an ArrayList<Object> that has removed any already collected
		// SpecialCollectibles from the list, as well as having added a set of
		// chests with random quest goals in them.
		public ArrayList<Objects> filter(ArrayList<Objects> input) {

			Objects obj;
			ArrayList<Objects> objList = new ArrayList<Objects>();
			ArrayList<Integer> points;
			ArrayList<Items> quests2;

			// ---------------------------------------------------------------------------------
			if (hasQuests()) {

				// Gets the number of quests for this area
				int numOfQuests = 0;
				for (SimpleQuest s : quests)
					if (s.place.equals(location) && !s.isCompleted())
						numOfQuests++;

				if (numOfQuests != 0) {

					// More variables
					int length = 0;
					int point;
					int questNum = Statics.RAND
							.nextInt(numOfQuests < 5 ? numOfQuests : 4) + 1;
					if (questNum == 0)
						questNum++;

					// Gets the number of DropPoints for the area.
					for (Objects o : input)
						if (o instanceof DropPoint)
							length++;

					System.out.println(questNum + ". " + length + ".");

					points = new ArrayList<Integer>();

					for (int i = 0; i < questNum && i < length; i++) {
						do
							point = Statics.RAND.nextInt(length);
						while (points.contains(point));
						points.add(point);
					}

					quests2 = new ArrayList<Items>();
					for (int y = 0; y < quests.size(); y++) {
						if (quests.get(y).place.equals(location)
								&& !quests.get(y).isCompleted()
								&& quests.get(y).objectAppeared())
							quests2.add(quests.get(y).item);
						else if (!quests.get(y).objectAppeared())
							points.remove(0);
					}
				} else {
					points = null;
					quests2 = null;
				}
			} else {
				points = null;
				quests2 = null;
			}
			// -------------------------------------------------------------------------------

			int count = 0;

			for (int i = 0; i < input.size(); i++) {
				obj = input.get(i);
				if (!(obj instanceof KeyCrystal)) {

					if (points != null)
						if (obj instanceof DropPoint) {
							if (points.contains(count)) {
								Items quest = quests2.get(Statics.RAND
										.nextInt(quests2.size()));
								((DropPoint) obj).setSpecs(quest);
								quests2.remove(quest);
								points.remove(new Integer(count));
							}
							count++;
						}

					objList.add(obj);
				} else if (specialCollectibles
						.containsKey(((KeyCrystal) obj).id) ? !hasCollected(((KeyCrystal) obj).id)
						: true)
					objList.add(obj);
			}

			return objList;
		}
public ArrayList<Portal>filterPortals(ArrayList<Portal>input){
	Enumeration<Integer>portalStuff=portals.keys();
	while(portalStuff.hasMoreElements()){
		int i=portalStuff.nextElement();
		input.get(i).doBoolean(portals.get(i));}
	
	return input;
}
		// Quest generation

		public ArrayList<NPC> filterNPC(ArrayList<NPC> input) {

			NPC obj;
			QuestNPC obj2;
			ArrayList<NPC> objList = new ArrayList<NPC>();

			for (int i = 0; i < input.size(); i++) {
				obj = input.get(i);

				if (obj instanceof QuestNPC) {
					obj2 = (QuestNPC) obj;
					obj2.setQuestState(getQuest(obj2.id));
					objList.add(obj2);
				} else if (obj instanceof BlockerNPC) {
					if (((BlockerNPC) obj).id == -1
							|| blockerNPCs.get(((BlockerNPC) obj).id))
						objList.add(obj);
				} else
					objList.add(obj);
			}

			return objList;
		}

		public SimpleQuest getQuest(int id) {

			return locQuests.get(id);
		}

		public void registerQuest(int id) {
			locQuests.get(id).setAccepted(true);
		}

		public void completeQuest(int id) {
			locQuests.get(id).setCompleted(true);
		}

		public boolean hasDropPoints() {
			return hasDropPoints;
		}
	}

	public class SimpleQuest implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private boolean accepted = false;
		private boolean completed = false;
		private int acceptedPhase;
		public final Items item;
		public final String place;
		public final String origin;
		public final int id;
		private int appearPhase = 0;

		public SimpleQuest(Items item, String place, String origin, int id) {
			this.item = item;
			this.place = place;
			this.origin = origin;
			this.id = id;

			System.out.println("New simple quest.");
		}

		public boolean objectAppeared() {
			return appearPhase <= acceptedPhase;
		}

		public int getAppearPhase() {
			return appearPhase;
		}

		public void setAppearPhase(int setter) {
			appearPhase = setter;
		}

		public int getAcceptedPhase() {
			return acceptedPhase;
		}

		public void setAcceptedPhase(int phase) {
			this.acceptedPhase = phase;
		}

		public boolean isAccepted() {
			return accepted;
		}

		public void setAccepted(boolean accepted) {
			this.accepted = accepted;
		}

		public boolean isCompleted() {
			return completed;
		}

		public void setCompleted(boolean completed) {
			this.completed = completed;
		}
	}

	public void unlockDoor(int indexOf,boolean b) {
		// TODO Auto-generated method stub
		areas.get(currentKey).portals.remove(indexOf);
		areas.get(currentKey).portals.put(indexOf, b);
	
		//areas.get(currentKey).portals.replace(indexOf, false);
	}
	public void unlockDoor(int indexOf) {
		unlockDoor(indexOf, false);
	}
}

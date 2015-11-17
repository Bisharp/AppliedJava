package com.dig.www.util;

import java.io.Serializable;

import com.dig.www.character.CharData.SimpleQuest;
import com.dig.www.character.Items;
import com.dig.www.npc.NPC;
import com.dig.www.npc.QuestNPC;
import com.dig.www.npc.Reyzu;

public class Quest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public enum Quests {

		THEFT {

			@Override
			public String getQuestLine(String[] vals) {
				return "Help! My " + vals[0] + " was stolen and taken to " + vals[1] + "!";
			}

			@Override
			public int numOfSpecifics() {
				return 2;
			}

			@Override
			public Types[] getTypes() {
				return new Types[] { Types.THING, Types.PLACE };
			}
		},

		FETCH {

			@Override
			public String getQuestLine(String[] vals) {
				return "I know you're busy, but do you think you could get me a " + vals[0] + "?\n I've heard they have one in " + vals[1] + ".";
			}

			@Override
			public int numOfSpecifics() {
				return 2;
			}

			@Override
			public Types[] getTypes() {
				return new Types[] { Types.THING, Types.PLACE };
			}
		};

		public abstract String getQuestLine(String[] vals);

		public abstract int numOfSpecifics();

		public abstract Types[] getTypes();
	}

	public enum Types {
		NAME {

			@Override
			public String getDetail(Quest issuer) {
				return NameBuilder.getName();
			}
		},
		PLACE {

			@Override
			public String getDetail(Quest issuer) {
				return null;
			}
		},
		THING {
			@Override
			public String getDetail(Quest issuer) {
				return "";
			}
		};

		public abstract String getDetail(Quest issuer);
	}

	private QuestNPC issuer;
	private Quests questType = Quests.values()[Statics.RAND.nextInt(Quests.values().length)];
	private String[] specifics;
	private String place;
	private Items item;

	public Quest(QuestNPC issuer) {
		this.issuer = issuer;

		// Chooses a location
		String[] s = issuer.getOwner().getData().getAreas();
		place = s[Statics.RAND.nextInt(s.length)];
		item = Items.getRandItem();

		System.out.println(place);

		// Creates the specifics. Note: DO NOT use Types.PLACE if it is not the
		// location of the stolen item. Use Types.NAME for random locations
		// superfluous to the quest. Each quest MUST have 1 Types.PLACE in it
		// and 1 Types.THING in it to allow the player to know what he is
		// looking for and where.
		Types[] t = questType.getTypes();
		specifics = new String[questType.numOfSpecifics()];

		for (int i = 0; i < specifics.length; i++)
			if (t[i] != Types.PLACE && t[i] != Types.THING)
				specifics[i] = t[i].getDetail(this);
			else if (t[i] != Types.THING)
				specifics[i] = place;
			else
				specifics[i] = item.toString();
	}

	public Quest(SimpleQuest state) {
		place = state.place;
		item = state.item;

		Types[] t = questType.getTypes();
		specifics = new String[questType.numOfSpecifics()];

		for (int i = 0; i < specifics.length; i++)
			if (t[i] != Types.PLACE && t[i] != Types.THING)
				specifics[i] = t[i].getDetail(this);
			else if (t[i] != Types.THING)
				specifics[i] = place;
			else
				specifics[i] = item.toString();
	}

	public NPC getIssuer() {
		return issuer;
	}

	public String getLine() {
		return questType.getQuestLine(specifics);
	}

	public String getPlace() {
		return place;
	}

	public Items getItem() {
		return item;
	}

	public void setState(SimpleQuest state) {
	}

	public void setPlace(String place2) {
		place = place2;
	}

	public void setItem(Items i) {
		item = i;
	}

	public String getIncompleteLine() {

		if (questType == Quests.THEFT)
			return getLine().split("!")[1] + ". I guess you haven't seen it.";
		else
			return "I guess you haven't seen a/an " + item.toString() + " yet. I've heard they have them in " + place + ".";
	}

	public Quests getType() {
		return questType;
	}
}

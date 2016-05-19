package com.dig.www.npc;

import com.dig.www.start.Board;
import com.dig.www.util.OnlyFirstTimeEnteringMap;

public class FirstCutscene extends CutScene implements OnlyFirstTimeEnteringMap{


	public FirstCutscene(int x, int y, Board owner, String location) {
		super(x, y, owner, location, new CSDialog[]{new CSDialog("There is some stuff before this. Sorry.","futureScientist", new CutSceneImage("images/npcs/cutScenes/temporary.png"))
				,new CSDialog("Uggh... Huh? Where am I?",NPC.SPADE,new CutSceneImage("images/npcs/cutScenes/opening/1.gif")),new CSDialog("Where does it look like? We're in a dungeon!",NPC.CLUB,new CutSceneImage("images/npcs/cutScenes/opening/2.png"))
				,new CSDialog("Carl? What are you... What's going on?",NPC.SPADE,new CutSceneImage("images/npcs/cutScenes/opening/2.png")),new CSDialog("Oh, are you guys friends?",NPC.HEART,new CutSceneImage("images/npcs/cutScenes/opening/2.png"))
				,new CSDialog("Our parents are friends and make us hang out. Destiny, do you know what’s going on here?",NPC.SPADE,new CutSceneImage("images/npcs/cutScenes/opening/2.png")),new CSDialog("Yep!",NPC.HEART,new CutSceneImage("images/npcs/cutScenes/opening/2.png"))
				,new CSDialog("Good! Then what’s going on?",NPC.SPADE,new CutSceneImage("images/npcs/cutScenes/opening/2.png")),new CSDialog("I don’t know! I was saying that my name is Destiny.",NPC.HEART,new CutSceneImage("images/npcs/cutScenes/opening/2.png"))
				,new CSDialog("Right. Uh;",NPC.SPADE,new CutSceneImage("images/npcs/cutScenes/opening/2.png")),new CSDialog("Hey! I’ve seen you at school; what’s your name?",NPC.SPADE,new CutSceneImage("images/npcs/cutScenes/opening/2.png"))
				,new CSDialog("...",NPC.DIAMOND, new CutSceneImage("images/npcs/cutScenes/opening/2.png")),new CSDialog("Um; what’s your na—",NPC.SPADE, new CutSceneImage("images/npcs/cutScenes/opening/2.png"))
				,new CSDialog("He’s my cousin, Cain.",NPC.HEART, new CutSceneImage("images/npcs/cutScenes/opening/2.png")),new CSDialog("Uh, OK; Cain, do *you| know what’s going on?",NPC.SPADE, new CutSceneImage("images/npcs/cutScenes/opening/2.png"))
				,new CSDialog("...",NPC.DIAMOND, new CutSceneImage("images/npcs/cutScenes/opening/2.png")),new CSDialog("Not very talkative...",NPC.SPADE, new CutSceneImage("images/npcs/cutScenes/opening/2.png"))
				,new CSDialog("...",NPC.DIAMOND, new CutSceneImage("images/npcs/cutScenes/opening/2.png")),new CSDialog("...",NPC.SPADE, new CutSceneImage("images/npcs/cutScenes/opening/2.png"))
				,new CSDialog("...",NPC.HEART, new CutSceneImage("images/npcs/cutScenes/opening/2.png")),new CSDialog("OK. Enough of That! We need to get out of here!",NPC.CLUB, new CutSceneImage("images/npcs/cutScenes/opening/2.png"))
				,new CSDialog("Shouldn't we figure out where \"here\" is first? Besides, there isn't even a door!",NPC.SPADE,new CutSceneImage("images/npcs/cutScenes/opening/2.png")),new CSDialog("So we'll make one!",NPC.CLUB,new CutSceneImage("images/npcs/cutScenes/opening/2.png"))
				,new CSDialog("Hold on a--",NPC.SPADE,new CutSceneImage("images/npcs/cutScenes/opening/3.gif")),new CSDialog("Ow.",NPC.CLUB,new CutSceneImage("images/npcs/cutScenes/opening/3.5.png"))
				,new CSDialog("Oh look, it's working! Do that again!",NPC.HEART,new CutSceneImage("images/npcs/cutScenes/opening/3.5.png")),new CSDialog("Are you crazy?",NPC.SPADE,new CutSceneImage("images/npcs/cutScenes/opening/3.5.png"))
				,new CSDialog("...",NPC.DIAMOND,new CutSceneImage("images/npcs/cutScenes/opening/3.5.png")),new CSDialog("Oh, you be quiet!",NPC.SPADE,new CutSceneImage("images/npcs/cutScenes/opening/3.5.png"))
				,new CSDialog("You did it, Carl!",NPC.HEART,new CutSceneImage("images/npcs/cutScenes/opening/4.gif"))
				,new CSDialog("HuH? WhErE's ThE bUiLdInG tHaT fElL oN mE?",NPC.CLUB,new CutSceneImage("images/npcs/cutScenes/opening/4.5.png")),new CSDialog("*sigh.| Let's just get out of here!",NPC.SPADE,new CutSceneImage("images/npcs/cutScenes/opening/4.5.png"))
				,new CSDialog("What is this place?",NPC.SPADE,new CutSceneImage("images/npcs/cutScenes/opening/5.png")),new CSDialog("Look, more people are trapped! Carl, get them out!",NPC.HEART,new CutSceneImage("images/npcs/cutScenes/opening/5.png"))
				,new CSDialog("OK! 3.. 2... 1...",NPC.CLUB,new CutSceneImage("images/npcs/cutScenes/opening/5.png")),new CSDialog("Stop it! At this rate you're going kill what few brain cells you have left!",NPC.SPADE,new CutSceneImage("images/npcs/cutScenes/opening/5.png"))
				,new CSDialog("What are you suggesting we do, then?",NPC.HEART,new CutSceneImage("images/npcs/cutScenes/opening/5.png")),new CSDialog("We should go get help!",NPC.SPADE,new CutSceneImage("images/npcs/cutScenes/opening/5.png"))
				,new CSDialog("Hmm... what do you think, Cain?",NPC.HEART,new CutSceneImage("images/npcs/cutScenes/opening/5.png")),new CSDialog("...",NPC.DIAMOND,new CutSceneImage("images/npcs/cutScenes/opening/5.png"))
				,new CSDialog("An escape? How foolish.","botanus",new CutSceneImage("images/npcs/cutScenes/opening/6.png")),new CSDialog("Don't let him grab you with that vine arm!",NPC.HEART,new CutSceneImage("images/npcs/cutScenes/opening/7.png"))
				,new CSDialog("This guy has a +PLANT| for an +ARM! WHAT IS GOING ON?|",NPC.SPADE,new CutSceneImage("images/npcs/cutScenes/opening/7.png")),new CSDialog("Oh, it's a good guy!",NPC.HEART,new CutSceneImage("images/npcs/cutScenes/opening/8.gif"))
				,new CSDialog("Eh, I could have taken that guy while he was distracted with capturing you guys.",NPC.CLUB,new CutSceneImage("images/npcs/cutScenes/opening/9.png")),new CSDialog("...",NPC.DIAMOND,new CutSceneImage("images/npcs/cutScenes/opening/9.png"))
				,new CSDialog("+WHAT IS GOING ON HERE?|",NPC.SPADE,new CutSceneImage("images/npcs/cutScenes/opening/9.png"))
				,new CSDialog("I'll be happy to answer that question later. Right now you need to get somewhere safe. Tell the Wizard   that Sir Cobalt sent you.",NPC.SIR_COBALT,new CutSceneImage("images/npcs/cutScenes/opening/9.png"))
				,new CSDialog("Go in there before the barrier breaks!",NPC.SIR_COBALT,new CutSceneImage("images/npcs/cutScenes/opening/10.png"))
				,new CSDialog("We need to go into THAT thing?",NPC.SPADE,new CutSceneImage("images/npcs/cutScenes/opening/11.gif"))
				,new CSDialog("+WHOA!|",NPC.SPADE,new CutSceneImage("images/npcs/cutScenes/opening/12.gif"))
				,new CSDialog("Ow ow ow, everything hurts.", NPC.SPADE,new CutSceneImage("images/npcs/cutScenes/opening/13.png")),new CSDialog("We're here!", NPC.HEART, new CutSceneImage("images/npcs/cutScenes/opening/13.png")),new CSDialog("This place is a dump. The last place was a dump. I wonder where we'll go next.", NPC.CLUB,new CutSceneImage("images/npcs/cutScenes/opening/13.png")),new CSDialog("...", NPC.DIAMOND,new CutSceneImage("images/npcs/cutScenes/opening/13.png")),new CSDialog("I think we should meet this Wizard person!", NPC.HEART,new CutSceneImage("images/npcs/cutScenes/opening/13.png")),new CSDialog("I think we should go home, but I guess we don't have a choice.", NPC.SPADE, new CutSceneImage("images/npcs/cutScenes/opening/13.png")),new CSDialog("There's the door!(Press the +Arrow Keys| to move)", NPC.HEART, new CutSceneImage("images/npcs/cutScenes/opening/14.png"))});}

	@Override
	protected String getGreeting() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	protected String getFarewell() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String exitLine() {
		// TODO Auto-generated method stub
		return "";
	}

}

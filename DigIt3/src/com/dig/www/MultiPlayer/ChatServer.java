package com.dig.www.MultiPlayer;


import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JOptionPane;

import com.dig.www.MultiPlayer.State.GameState;
import com.dig.www.MultiPlayer.State.PlayerState;
import com.dig.www.MultiPlayer.State.StartState;
import com.dig.www.character.GameCharacter;
import com.dig.www.start.Board;


public class ChatServer  implements IChatServer
{
	private String passWord;
	private Board owner;
	private HashMap<String, IChatClient> clientMap = new HashMap<>();
	private ArrayList<String>keys=new ArrayList<String>();
	@Override
	public Set<String> enterChatRoom(IChatClient chatClient, String name,String passWord) throws Exception
	{
		if(!this.passWord.equals("None")&&!passWord.equals(this.passWord))
			throw new Exception();
		for (IChatClient client : clientMap.values())
		{
			client.addChatClient(name);
		}	
		keys.add(name);
		clientMap.put(name, chatClient);
		// need a HashSet since it is serializable
		HashSet<String> existingClients = new HashSet<>();
		for(String client: clientMap.keySet())
		{
			existingClients.add(client);
		}
		owner.addAction(name.substring(0, name.length()>9?9:0)+" joined", "images/icon.png");
		//owner.getCurrentState().clear(owner.getLevel());
		//sendMessage(owner.getMPName(), name, new StartState(owner.getMode(),owner.getLevel()));
		return(existingClients);
	}
	
	@Override
	public void leaveChatRoom(String name,String playerName) throws RemoteException
	{
		clientMap.remove(name);
		keys.remove(name);
		for(GameCharacter chara:owner.getFriends()){
			//if(chara.getType().charName().equals(playerName)){
				//System.out.println(chara.getType().charName());
				//chara.setPlayer(false);
				//chara.setMpName("I love cake");
			
			if(name.equals(chara.getMpName())){
				chara.setMpName(null);
				chara.setPlayer(false);
			}
			
//				for(int c=0;c<owner.getFriends().size();c++){
//					
//					System.out.println(name+","+owner.getCurrentState().getPlayerStates().get(c).getMpName());
//					if(name.equals(owner.getCurrentState().getPlayerStates().get(c).getMpName())){
////						owner.getCurrentState().getPlayerStates().remove(c);
////					c--;
//					//	System.out.println(name);
//					owner.getCurrentState().getPlayerStates().get(c).left();	
//					}
//				//}
//			}
		}
		for (IChatClient client: clientMap.values())
		{
			client.removeChatClient(name);
		}
		//owner.getCurrentState().clear(owner.getLevel());
	}

	@Override
	public boolean sendMessage(String sender, String sendee, GameState message) throws RemoteException
	{
		IChatClient client = clientMap.get(sendee);
		if (client != null)
		{
			client.receiveMessage(message, sender);
			return true;
		}
		// no delivered
		return false;
	}
@Override
public StartState getStartState()throws RemoteException{
return new StartState(owner,owner.getSpawnLoc());
}
	@Override
	public boolean broadcast(String sender,GameState  message) throws RemoteException
	{
		//JOptionPane.showMessageDialog(owner,""+clientMap.size());
//		if(sender.equals("CakeA"))
//		owner
		for(int c=0;c<clientMap.size();c++)
		{
			try{
			((IChatClient)clientMap.values().toArray()[c]).receiveMessage(message, sender);
			}catch(Exception ex){
				ex.printStackTrace();
//				System.out.println(((IChatClient)clientMap.values().toArray()[c]));
//				HashMap<String, IChatClient> clientMap2 = new HashMap<>();
//				for(int c2=0;c2<clientMap.size();c2++){
//					if(c!=c2)
//						clientMap2.put(keys.get(c2), clientMap.get(keys.get(c2)));
//				}
				System.err.println(keys.get(c)+" left the wrong way.");
				String playerName="";
				for(int c2=0;c2<owner.getFriends().size();c2++){
					if(owner.getFriends().get(c2).getMpName()!=null&&owner.getFriends().get(c2).getMpName().equals(keys.get(c))){
						playerName=owner.getFriends().get(c).getType().charName();
						break;
					}
						
				}
				leaveChatRoom(keys.get(c), playerName);
				//clientMap.remove(keys.get(c));
			//String client=	clientMap.get("club");
				//leaveChatRoom("apl2of2","club");
				//ex.printStackTrace();
				//clientMap.remove(arg0);
			}
			}
		getTold(message);
		return true;
	}
	
	public ChatServer(Board owner,String passWord){
		IChatServer stub;
		this.passWord=passWord;
		try {
			stub = (IChatServer) UnicastRemoteObject.exportObject(this, PORT);
		
		Registry registry = LocateRegistry.createRegistry(PORT+1);
		registry.bind("QuestOfFour", stub);} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.owner=owner;
	}

	@Override
	public void getTold(GameState state) throws RemoteException {
		// TODO Auto-generated method stub
	owner.getTold(state);
	}
	public boolean contains(ArrayList<PlayerState>players){
		for(int c=0;c<players.size();c++){
			if(clientMap.containsKey(players.get(c).getMpName()))
					return true;
		}
		for(int c=0;c<players.size();c++){
			for(GameCharacter chara:owner.getFriends()){
				chara.setMpName(null);
				chara.setPlayer(false);
			}
		}
		return false;
	}
}

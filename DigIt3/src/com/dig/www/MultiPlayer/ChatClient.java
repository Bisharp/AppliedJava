package com.dig.www.MultiPlayer;


import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

import com.dig.www.MultiPlayer.State.GameState;
import com.dig.www.MultiPlayer.State.PlayerState;
import com.dig.www.character.GameCharacter;
import com.dig.www.start.Board;


public class ChatClient implements IChatClient
{
	private Board owner;
	private ArrayList<String> clientMap = new ArrayList<String>();
	public ChatClient(Board owner,String serverHostName,String myName,String passWord) throws RemoteException, AlreadyBoundException, NotBoundException
	{
		
		Registry remoteRegistry = LocateRegistry.getRegistry(serverHostName, ChatServer.PORT + 1);
		IChatServer chatServer = (IChatServer) remoteRegistry.lookup("QuestOfFour");//ERROR HERE
		
		// setup the rmi interface so server can send messages here
		IChatClient stub = (IChatClient) UnicastRemoteObject.exportObject(this, 0);
		// bind the remote objects's stub in the registry
		Registry registry = LocateRegistry.createRegistry(0);
		registry.bind("Chat Client", stub);
		this.owner=owner;
		owner.setOtherServer(chatServer);
		try{
		chatServer.enterChatRoom(this, myName,passWord);}
		catch(Exception ex){
			JOptionPane.showMessageDialog(owner, "Could not connect to server. You probably have the wrong password.");
		System.exit(0);
		}
		System.out.println("Client communications setup");
	//	ui = new ChatUI(this, chatServer);
	}

	@Override
	public void receiveMessage(GameState message, String sender) throws RemoteException
	{
		owner.getTold(message);
	}

	@Override
	public void addChatClient(String name) throws RemoteException
	{
		clientMap.add(name);
		//owner.addChatClient(name);
	}

	@Override
	public void removeChatClient(String name) throws RemoteException
	{
		//owner.removeChatClient(name);
	}
	public String getName(){
		return owner.getMPName();
	}
	public String getPlayerName(){
		if(owner.getCharacter()!=null)
		return owner.getCharacter().getType().toString();
		return "";
	}
	public boolean contains(ArrayList<PlayerState>players){
		for(int c=0;c<players.size();c++){
			if(clientMap.contains(players.get(c).getMpName()))
					return true;
			else{
				
			}
		}
//		for(int c=0;c<players.size();c++){
//			for(GameCharacter chara:owner.getFriends()){
//				chara.setMpName(null);
//				chara.setPlayer(false);
//			}
//		}
		return false;
	}
} 

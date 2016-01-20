package com.dig.www.MultiPlayer;


import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JOptionPane;

import com.dig.www.MultiPlayer.State.GameState;
import com.dig.www.start.Board;


public class ChatServer  implements IChatServer
{
	Board owner;
	private HashMap<String, IChatClient> clientMap = new HashMap<>();
	
	@Override
	public Set<String> enterChatRoom(IChatClient chatClient, String name) throws RemoteException
	{
		for (IChatClient client : clientMap.values())
		{
			client.addChatClient(name);
		}	
		clientMap.put(name, chatClient);
		// need a HashSet since it is serializable
		HashSet<String> existingClients = new HashSet<>();
		for(String client: clientMap.keySet())
		{
			existingClients.add(client);
		}
		//sendMessage(owner.getMPName(), name, new StartState(owner.getMode(),owner.getLevel()));
		return(existingClients);
	}
	
	@Override
	public void leaveChatRoom(String name) throws RemoteException
	{
		clientMap.remove(name);
		for (IChatClient client: clientMap.values())
		{
			client.removeChatClient(name);
		}
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
	public boolean broadcast(String sender,GameState  message) throws RemoteException
	{
		//JOptionPane.showMessageDialog(owner,""+clientMap.size());
//		if(sender.equals("CakeA"))
//		owner
		for(IChatClient client: clientMap.values())
		{
			client.receiveMessage(message, sender);
		}
		getTold(message);
		return true;
	}
	
	public ChatServer(Board owner){
		IChatServer stub;
		
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
}

package com.dig.www.MultiPlayer;


import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.JOptionPane;

import com.dig.www.MultiPlayer.State.GameState;
import com.dig.www.start.Board;


public class ChatClient implements IChatClient
{
	private Board owner;
	
	public ChatClient(Board owner,String serverHostName,String myName) throws RemoteException, AlreadyBoundException, NotBoundException
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
		System.out.println("Client communications setup");
		chatServer.enterChatRoom(this, myName);
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
		//owner.addChatClient(name);
	}

	@Override
	public void removeChatClient(String name) throws RemoteException
	{
		//owner.removeChatClient(name);
	}
	

} 

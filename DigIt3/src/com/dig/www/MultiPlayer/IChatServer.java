package com.dig.www.MultiPlayer;


import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Set;

import com.dig.www.MultiPlayer.State.GameState;

public interface IChatServer extends Remote
{
	public static final int PORT = 5000;
	// returns a list of all users in chat room
	public Set<String> enterChatRoom(IChatClient chatclient, String name,String passWord) throws RemoteException,Exception;
	public void leaveChatRoom(String name,String playerName) throws RemoteException;
	public boolean sendMessage(String sender, String sendee, GameState message) throws RemoteException;
	public boolean broadcast(String sender, GameState Message) throws RemoteException;
	public void getTold(GameState Message) throws RemoteException;

}

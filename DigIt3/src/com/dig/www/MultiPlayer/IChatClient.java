package com.dig.www.MultiPlayer;


import java.rmi.Remote;
import java.rmi.RemoteException;

import com.dig.www.MultiPlayer.State.GameState;

public interface IChatClient extends Remote
{
	public void receiveMessage(GameState Message, String sender) throws RemoteException;
	public void addChatClient(String name) throws RemoteException;
	public void removeChatClient(String name) throws RemoteException;
}

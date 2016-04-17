package mudServer;

import java.net.*;
import java.io.*;
import java.util.*;

/**
 * On startup, ServerMain will create new socket pairs and the GameServer.
 * 
 * ServerMain then awaits socket connections from clients.
 * New connections are passed to the GameServer for processing
 * 
 * @author Nemesis
 *
 */
public class ServerMain {
	
	public static void main(String[] args)
	{
		System.out.println("\nSITMud Game Server v1.3\n");
		System.out.print("Initializing...");
		
		try
		{
			// Create server sockets
			ServerSocket server = new ServerSocket(4000);
			Socket connection = new Socket();
			
			// Create GameServer
			GameServer Game = new GameServer();
			Thread GameThread = new Thread(Game);
			GameThread.start();
			
			// Initialization completed
			System.out.println("completed.\n");
			
			while(true)
			{	
				// Accept new connection
				connection = server.accept();
				System.out.println("Host connected from: " + connection.getInetAddress()
				+ " at " + new Date() + "\n");
				
				// Pass new connection down to the game server
				Game.acceptNew(connection);
				
				
			} // End while
		} // End try
		
		catch(IOException e){}
	
	} // End main
} // End ServerMain

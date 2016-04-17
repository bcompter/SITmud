package mudServer;

import java.util.ArrayList;
import java.net.*;
import mudServer.Commands.*;
import mudServer.Mobiles.*;

/**
 * The GameServer accepts new connections from ServerMain and logs them into the 
 * game.
 * 
 * GameServer is a runnable thread which will check all of it's players for new 
 * input and execute commands for them.
 * @author Nemesis
 *
 */
public class GameServer implements Runnable
{
	// -------------------------------------------------------------------------
	// Members
	// -------------------------------------------------------------------------
	World GameWorld;
	ArrayList Players = new ArrayList();

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------
	
	/**
	 * Default constructor.
	 *
	 */
	public GameServer()	
	{
		GameWorld = new World();
		//GameWorld.saveWorld();
		
	} // end constructor 
	
	
	// -------------------------------------------------------------------------
	// Methods
	// -------------------------------------------------------------------------
	
	/**
	 * Accept a connected socket and log the new user into the game.
	 * Creates all players and places them into the world.
	 * @param newConnection
	 */
	public void acceptNew(Socket newConnection)
	{
		// Create new player
		Player newPlayer = new Player(newConnection);
		
		// Login - get player information
		Login a = new Login(this, newPlayer);
		Thread LogInThread = new Thread (a);
		LogInThread.start();	
	} // end acceptNew
	
	/**
	 * Add a player to the gmae server
	 * @param newPlayer The player to be added
	 */
	public void addPlayer(Player newPlayer)
	{
		// Put player into the GameServer list and the GameWorld
		Players.add(newPlayer);
		
		// Set room
		GameWorld.addMobile(newPlayer, newPlayer.getMyRoomId());
		
		// Set log on status
		newPlayer.setLogOn();
		
		newPlayer.sendMessage("\nLogin Successful.\nEntering the Game World.\n");
		Command newLook = new Look();
		newLook.execute(newPlayer, "look");
	} // end addPlayer
	
	
	// -------------------------------------------------------------------------
	// Runnable thread
	// -------------------------------------------------------------------------
	
	/**
	 * Checks all players in the game for new input and 
	 * processes commands.
	 */
	public void run()
	{
		// Create CommandFactory to handle commands
		CommandFactory cmdFactory = new CommandFactory();
		Command thisCmd;
		
		while(true)
		{
			// Search through all players and check to see if they have sent a command
			for (int i = 0; i < Players.size(); i++)
			{					
				// Remove loggedout players
				if (!((Player)Players.get(i)).isLoggedOn())
				{
					Players.remove(i);
					i = 0;
				}
				
				// If the player is logged in and connected, 
				// see if there is a command waiting to be processed.
				else if(((Player)Players.get(i)).isReady())
				{
					// Get command and process
					String fromClient = ((Player)Players.get(i)).recieveMessage();
					
					thisCmd = cmdFactory.getCommand(fromClient);
					thisCmd.execute((Mobile)Players.get(i), fromClient);
					
				} // end if
				
			} // end for
			
			// Sleep the thread after each round of searches
			// to prevent CPU hogging
			try
			{
				Thread.sleep(100);
			}
			catch(InterruptedException e){}
			
		} // end while	
		
	} // end run
	
} // end GameServer

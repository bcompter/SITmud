package mudServer;

import java.io.*;
import java.util.*;
import mudServer.Mobiles.*;

/**
 * Verify a connected user's name and password.  
 * Load the player information from a file and enter
 * the user into the game.
 * @author Nemesis
 *
 */
public class Login implements Runnable
{
	GameServer currentGame;
	Player currentPlayer;
	private final String loginWelcome = 
		":*~*:._.:*~*:._.:*~*:.:*~*:._.:*~*:._.:*~*:.:*~*:\n" + 
		"  __    _   _______" + "\n" + 
		" /  \\  | | |__   __|" + "\n" + 
		"/ /\\_\\ | |    | |" + "\n" +
		"\\ \\_   | |    | |                 _    _       _" + "\n" +
		" \\_ \\  | |    | |      /\\  /\\    | |  | |  __ | |" + "\n" +
		"__ \\ \\ | |    | |     /  \\/  \\   | |  | | /  \\| |" + "\n" +
		"\\ \\/ / | |    | |    / /\\  /\\ \\  | \\__| | |()   |" + "\n" +
		" \\__/  |_|    |_|   |_/  \\/  \\_| \\______| \\__/|_|" + "\n\n" +
		"A CpE 490 Project by:" + "\n" +
		"     * Brian Compter" + "\n" +
		"     * Jeff Smith" + "\n" +
		"     * Patrick McCabe" + "\n\n" +
		":*~*:._.:*~*:._.:*~*:.:*~*:._.:*~*:._.:*~*:.:*~*:\n";
	
	public void run()
	{
		// Before we get down ot business, the welcome screen
		currentPlayer.sendMessage(loginWelcome);

		String userIn;
		String userName = new String();
		
		boolean usernameOk = false;
		boolean passwordOk = false;
		
		ArrayList userNames = new ArrayList();
		ArrayList passWords = new ArrayList();
		int playerIndex = -1;
		
		// First read in username and password file for 
		// reference during login.  
		
		// In later versions we will need to load this file to to the gameserver.
		// This will prevent file read/write collisions.
		// The list must be saved and loaded upon shutdown/startup.
		
		try
		{
			FileInputStream file = new FileInputStream("UserPass.txt");
			InputStreamReader in = new InputStreamReader(file);
			BufferedReader buffIn = new BufferedReader(in);

			while(buffIn.ready())
			{
				userNames.add(buffIn.readLine());
				passWords.add(buffIn.readLine());
			}
		
			buffIn.close();
			in.close();
			file.close();
		}
		catch (IOException e){}
		
		// Ask the player to enter his username.
		currentPlayer.sendMessage("Please enter your username: ");

		while (true)
		{
			// Loop until Login success or failure
			if(!currentPlayer.isReady())
			{
				try
				{
					Thread.sleep(100);
				}
				catch(InterruptedException e){}
			}
			else
			{
				// Verify username and password
				userIn = currentPlayer.recieveMessage();
				
				if(!usernameOk)
				{
					userName = userIn;
					
					//Match username
					for (int i = 0; i < userNames.size(); i++)
					{
						if (userIn.equalsIgnoreCase((String)userNames.get(i)))
						{
							playerIndex = i;
							usernameOk = true;
						}
					}
					
					if (playerIndex == -1)
						currentPlayer.sendMessage("Invalid user name. Please try again.");
					else
						currentPlayer.sendMessage("Please enter your password.");
				}
				else
				{
					//Match password
					if (userIn.equalsIgnoreCase((String)passWords.get(playerIndex)))
						passwordOk = true;
					else
						currentPlayer.sendMessage("Invalid password. Please try again.");
				}
				
				if(usernameOk && passwordOk) break;			
			}	
			
		} // end while
		
		// If the login was successful...
		if(usernameOk && passwordOk)
		{
			// Load player from file	 
			currentPlayer.loadPlayer(userName);
			
			// Add player to the Server
			currentGame.addPlayer(currentPlayer);
			System.out.println(currentPlayer.nameToString() + " has successfully logged in from "
		    + currentPlayer.getIpAdd() + " at " + new Date() + ".\n");
		}
		else
		{
			currentPlayer.sendMessage("Login Failed.");
			currentPlayer.disconnect();
		}
			
	}
	
	/**
	 * Constructor.  Create a login thread with the given information.
	 * @param thisServer A referenc to the game server.
	 * @param thisPlayer A blank player with  a connected socket only.
	 */
	public Login(GameServer thisServer, Mobile thisPlayer)
	{
		currentGame = thisServer;
		currentPlayer = (Player)thisPlayer;
	}
	
} // end Login

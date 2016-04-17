package mudServer.Mobiles;

import java.io.*;
import java.net.*;
import mudServer.Items.*;

/**
 * Players.  Each player will have a set of stats, such as
 * the player's name.  Each will hold items as well.
 * 
 * Each player will be responsible for sending and receiving messages to and from 
 * it's remote client. 
 * @author Nemesis
 *
 */
public class Player extends MobileStd implements Mobile
{
	// -------------------------------------------------------------------------
	// Character Members
	// -------------------------------------------------------------------------

	private String race;
	private String title;
	
	// -------------------------------------------------------------------------
	// Network Members
	// -------------------------------------------------------------------------
	private Socket mySocket;	
	private BufferedReader fromClient;
	private PrintWriter toClient;
	private boolean loggedOn;
	
	
	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------	
	
	/**
	 * Creates a new player from a socket.
	 * Opens file streams to and from the client.
	 * @param connection The connected socket.
	 */
	public Player(Socket connection)
	{
		mySocket = connection;
		
		// Open IO streams
		try
		{
			fromClient = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
			toClient = new PrintWriter(mySocket.getOutputStream());
		}
		catch (IOException e){}
		
	} // End constructor

	// -------------------------------------------------------------------------
	// Methods
	// -------------------------------------------------------------------------
	
	/**
	 * Get the name of the player.
	 * @return The name of the player.
	 */
	public String fullNameToString()
	{
		return name;
	}
	
	
	/**
	 * Get the name and status of the player.
	 * @return The name and status of the player.
	 */
	public String fullNameAndStatusToString()
	{
		String full = fullNameToString();
		
		if(status == 0)
			full = full + " who appears dead";
		else if (status == 2)
			full = full + " who is kneeling";		
		else if (status == 3)
			full = full + " who is sitting";
		else if (status == 4)
			full = full + " who is laying down";
		
		return full;
	}
	
	/**
	 * Returns the player name race and title.
	 * @return The player description as a string.
	 */
	public String lookAtMobile()
	{
		String desc;
		
		desc = "You see " + nameToString() + " the " + race + " " + title + ".";
		
		return desc;
	}
	
	/**
	 * Finds out if this mobile is a player or not.
	 * @return True.
	 */
	public boolean isPlayer()
	{
		return true;
	}
	

	/**
	 * Gets an item being worn by the player.
	 * @param itemName The name of the item being looked for.
	 * @return The item or nothing.
	 */
	public Item getWornItem(String itemName)
	{
		Item temp = new generalItem();
		return temp;
	}

	// -------------------------------------------------------------------------
	// Network Methods
	// -------------------------------------------------------------------------
	
	/**
	 * Removes the player from his current room, closes IO streams, and closes 
	 * the socket.
	 */
	public void disconnect()
	{
		myRoom.removeMobile(this);
		myRoom.sendMessage(nameToString() + " left.");
		loggedOn = false;
		
		sendMessage("Logged out of the game.  Thankyou for playing.");
		
		toClient.close();
		try{
			fromClient.close();
			mySocket.close();
		}catch (IOException e){}
		
	} // end logOut
	
	
	/**
	 * Finds out if the player is logged on or not.
	 * @return True if the player is logged on, false otherwise.
	 */
	public boolean isLoggedOn()
	{
		return loggedOn;
	}
	
	/**
	 * Sets the logon status to true.
	 *
	 */
	public void setLogOn()
	{
		loggedOn = true;
	}
	
	/**
	 * Return the IP address associated with the player.
	 * @return The IP address in string form.
	 */
	public String getIpAdd()
	{
		return "" + mySocket.getInetAddress();
	}
	
	
	/**
	 * Finds out if the client has sent a command to the server.  
	 * @return True if the BufferedReader is ready, false otherwise.
	 */
	public boolean isReady()
	{
		try
		{
			return fromClient.ready();
		}
		catch (IOException e){return false;}
	}
	
	/**
	 * Send a message to the remote client.
	 * @param message The message to be sent.
	 */
	public void sendMessage(String message)
	{
		toClient.write(message + "\n");
		toClient.flush();
	}
	
	/**
	 * Returns a message from the client.
	 * This method uses a safe algorithm in order to read from the 
	 * BufferedReader.
	 * This is neccessary due to the fact that the readLine() method hangs.
	 * @return The received message.
	 */
	public String recieveMessage()
	{
		String output = new String();
		
		try
		{
			// Read characters from the client until the buffer is empty
			// Add them together and return the full line
			while(fromClient.ready())
			{
				output = output + ((char)fromClient.read());
			}
		}
		catch (IOException e){}
		
		return output;
		
	} // End recieveMessage
	
	/**
	 * Saves a player to a file.
	 *
	 */
	public void savePlayer()
	{
		FileWriter myFile = null;
		
		try{
			myFile = new FileWriter(name + ".txt");
		}catch(IOException e){}
		
		BufferedWriter buffer = new BufferedWriter(myFile);
		PrintWriter buffWriter = new PrintWriter(buffer);
		
		// Save character members
		buffWriter.println(name);
		buffWriter.println(myRoom.getId());
		buffWriter.println(status);
		
		buffWriter.println(race);
		buffWriter.println(title);
		
		buffWriter.println(hitPoints);
		buffWriter.println(MAXHitPoints);
		
		buffWriter.flush();
		
		// Save all items
		rightHand.saveItem(buffWriter);
		leftHand.saveItem(buffWriter);
		
		buffWriter.println(Items.size());
		buffWriter.flush();
		
		for (int i = 0; i < Items.size(); i++)
		{
			((Item)Items.get(i)).saveItem(buffWriter);
		}
		
		// Cleanup files
		buffWriter.close();
		try{
			buffer.close();
			myFile.close();
		}catch(IOException e){}
		
	} // end savePlayer
	
	
	/**
	 * Load a player from a file.
	 * The file must already exist on the server.
	 * @param thisName The name of the player being loaded.
	 */
	public void loadPlayer(String thisName)
	{		
		try 
		{
			
			FileReader myReader = new FileReader(thisName + ".txt");
			BufferedReader mybuffRead = new BufferedReader(myReader);
			
			// Load name, roomId, and Status
			// and stats when we get to them
			name = mybuffRead.readLine();
			myRoomId = Integer.parseInt(mybuffRead.readLine());
			status = Integer.parseInt(mybuffRead.readLine());
			
			race = mybuffRead.readLine();
			title = mybuffRead.readLine();
			
			hitPoints = Integer.parseInt(mybuffRead.readLine());
			MAXHitPoints = Integer.parseInt(mybuffRead.readLine());
			
			// Load right and left hands
			
			// Load all other items			
			
		}	
		catch(IOException e){}
		
	} // end loadPlayer
	
} // End player


package mudServer;

import java.util.ArrayList;
import java.io.*;

import mudServer.Items.*;
import mudServer.Mobiles.*;
import java.util.Random;

/**
 * Each room holds players, mobiles, items, and paths to other rooms.
 * The room sends messages to all players in it, and move players into and out of itself.
 * 
 * @author Nemesis
 *
 */
public class Room
{
	// -------------------------------------------------------------------------
	// Members
	// -------------------------------------------------------------------------
	private ArrayList Mobiles = new ArrayList();
	private ArrayList Items = new ArrayList();
	private ArrayList Paths = new ArrayList();
	
	private String Title, Description;
	private int Id;
	
	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------
	
	/**
	 * Create a new room.
	 * @param newTitle The room title.
	 * @param newDesc The room description.
	 * @param newId The room id.
	 */
	public Room(String newTitle, String newDesc, int newId)
	{
		Title = newTitle;
		Description = newDesc;
		Id = newId;
	}

	/**
	 * Default Constructor
	 */
	public Room()
	{
		Id = -1;
	}
	
	// -------------------------------------------------------------------------
	// Methods
	// -------------------------------------------------------------------------
	
	/**
	 * Sends the given message to all players in the room.
	 * @param message The message to be sent.
	 */
	public void sendMessage(String message)
	{
		// Loop through all players in the room and 
		// send the message.
		for (int i = 0; i < Mobiles.size(); i++)
		{
			if (((Mobile)Mobiles.get(i)).isPlayer())
				((Player)Mobiles.get(i)).sendMessage(message);
		}
	} // end sendMessage
	
	/**
	 * Sends a message to everyone in the room except thisPlayer.  
	 * This is an exclusive message sender.
	 * @param message The message to be sent.
	 * @param thisPlayer The player that will be excluded.
	 */
	public void exclusiveSend(String message, Player thisPlayer)
	{
		// Loop through all players in the room and 
		// send the message to each player except thisPlayer
		for (int i = 0; i < Mobiles.size(); i++)
		{
			if (((Mobile)Mobiles.get(i)).isPlayer())
			{
				if (!thisPlayer.equals((Player)Mobiles.get(i)))
					((Player)Mobiles.get(i)).sendMessage(message);
			}
		}
	} // end exclusiveSend
	
	/**
	 * Add a mobile to the room.
	 * @param thisMobile the player to be added.
	 */
	public void addMobile(Mobile thisMobile)
	{
		Mobiles.add(thisMobile);
		thisMobile.setRoom(this);
	}	
	
	/**
	 * Remove a mobile from the room.
	 * @param thisMobile The player to be removed.
	 */
	public void removeMobile(Mobile thisMobile)
	{
		Mobiles.remove(thisMobile);
	}
	
	
	/**
	 * Get a mobile from the room.
	 * @param mobileName The name of the mobile.
	 * @return The first mobile with a matching name.
	 */
	public Mobile getMobile(String mobileName)
	{
		// Search through list for a match
		for (int i = 0; i < Mobiles.size(); i++)
		{
			if(((Mobile)Mobiles.get(i)).nameToString().equalsIgnoreCase(mobileName))
				return (Mobile)Mobiles.get(i);
		}
		// No match found, return nothing
		return new Monster();
	}
	
	/**
	 * Adds an item to the room. 
	 * @param thisItem The item to be added.
	 */
	public void addItem(Item thisItem)
	{
		Items.add(thisItem);
	}
	
	/**
	 * Removes an item form the room.
	 * @param thisItem The item to be removed.
	 */
	public void removeItem(Item thisItem)
	{
		Items.remove(thisItem);
	}
	
	/**
	 * Returns the first item in the room whose name matches itemName.
	 * @param itemName
	 * @return Matching item.
	 */
	public Item getItem(String itemName)
	{
		// Search list for a match
		for (int i = 0; i < Items.size(); i++)
		{
			if (((Item)Items.get(i)).nameToString().equalsIgnoreCase(itemName))
				return (Item)Items.get(i);
		}
		
		// No match found, return nothing
		return new generalItem();
		
	} // end getItem
	

	/**
	 * Adds a path the the room.
	 * @param thisPath The path to be added
	 */
	public void addPath(Path thisPath)
	{
		Paths.add(thisPath);
	}
	
	/**
	 * Gets a room from a given path direction.
	 * @param thisDir The path direction.
	 * @return The destination room.
 	 */
	public Room getRoomFromPath(String thisDir)
	{
		// Search paths for matching direction
		for (int i = 0; i < Paths.size(); i++)
		{
			if(((Path)Paths.get(i)).dirToString().equalsIgnoreCase(thisDir))
			{
				return ((Path)Paths.get(i)).getRoom();
			}
		}
		
		// No match found, return notfound
		return new Room();
	}
		
	/**
	 * Gets the direction to the target room from this room.
	 * @param thisRoom The target room.
	 * @return The direction as a string.
	 */
	public String getDir(Room thisRoom)
	{
		// Search paths for match
		for(int i = 0; i < Paths.size(); i++)
		{
			if(((Path)Paths.get(i)).getRoom().equals(thisRoom))
				return ((Path)Paths.get(i)).dirToString();
		}
		
		// No match found, return ""
		return "";
		
	} // end getDir
	
	
	/**
	 * Gets a random direction. Used by monsters for movement.
	 * @return The direction as a string.
	 */
	public String getRandDir()
	{
		Random randDir = new Random();
		return ((Path)Paths.get(Math.abs(randDir.nextInt()) % Paths.size())).dirToString();
	}
	
	public String getRandPlayer()
	{
		Random randPlayer = new Random();
		int playerIndex = Math.abs(randPlayer.nextInt()) % numPlayers();
		int count = 0;
		String playerName = new String();
		
		for (int i = 0; i < Mobiles.size(); i++)
		{
			if(((Mobile)Mobiles.get(i)).isPlayer())
			{
				if(playerIndex == count)
				{
					return ((Mobile)Mobiles.get(i)).nameToString();
				}
				count++;
			}
		}
		
		return playerName;
	}
	
	/**
	 * Returns the description of this room as a String.
	 * @return roomDesc The room description.
	 */
	public String lookAtRoom(Player thisPlayer)
	{
		String roomDesc;
		int num, count;
		roomDesc = "\n" + Title + "\n" + Description + "\n";
		
		roomDesc = roomDesc + "\nYou also see: ";
		
		// *************************************************************************		
		// Display all monsters
		// *************************************************************************
		num = 0;
		count = 0;
		for (int i = 0; i < Mobiles.size(); i++)
		{
			if (!((Mobile)Mobiles.get(i)).isPlayer())
				count ++;
		}
		
		for (int i = 0; i < Mobiles.size(); i++)
		{
			if (!((Mobile)Mobiles.get(i)).isPlayer())
			{
				if (num == 0 && count > 2)
				{
					// First mobile
					roomDesc = roomDesc + ((Mobile)Mobiles.get(i)).fullNameAndStatusToString() + ", ";
				}
				else if(num == 0 && count == 1)
				{
					// First and last mobile
					roomDesc = roomDesc + 
					((Mobile)Mobiles.get(i)).fullNameAndStatusToString() + ". ";
				}
				else if(num == 0 && count == 2)
				{
					// First and only two mobs.
					roomDesc = roomDesc + 
					((Mobile)Mobiles.get(i)).fullNameAndStatusToString() + " ";
				}
				else if (num + 1 == count)
				{
					// Last mobile
					roomDesc = roomDesc + "and " + 
					((Mobile)Mobiles.get(i)).fullNameAndStatusToString().toLowerCase() + ". ";
				}
				else
				{
					// Mobile in the middle
					roomDesc = roomDesc +
					((Mobile)Mobiles.get(i)).fullNameAndStatusToString().toLowerCase() + ", ";
				}
				num ++;
			}
		}
		
		// *************************************************************************		
		// Display all items
		// *************************************************************************
		for (int i = 0; i < Items.size(); i++)
		{
			if (i == 0 && Items.size() == 1)
				roomDesc = roomDesc + ((Item)Items.get(i)).fullnameToString() + ".";
			else if (i == 0 && Items.size() == 2)
				roomDesc = roomDesc + ((Item)Items.get(i)).fullnameToString() + " ";
			else if (i == 0 && Items.size() > 2)
				roomDesc = roomDesc + ((Item)Items.get(i)).fullnameToString() + ", ";
			else if (i + 1 == Items.size())
				roomDesc = roomDesc + "and " + ((Item)Items.get(i)).fullnameToString().toLowerCase() + ".";
			else
				roomDesc = roomDesc + ((Item)Items.get(i)).fullnameToString().toLowerCase() + ", ";
		}
		
		// *************************************************************************		
		// Display all obvious paths
		// *************************************************************************
		roomDesc = roomDesc + "\nObvious paths: "; 
		// Search through all the paths in the room and print their directions
		num = 0;
		count = 0;
		for (int i = 0; i < Paths.size(); i++)
		{
			// Fetch number of obvious paths
			String dir = ((Path)Paths.get(i)).dirToString();
			if (dir.equalsIgnoreCase("north")||dir.equalsIgnoreCase("south")
				|| dir.equalsIgnoreCase("east")||dir.equalsIgnoreCase("west")
				|| dir.equalsIgnoreCase("northeast")||dir.equalsIgnoreCase("northwest")
				|| dir.equalsIgnoreCase("southeast")||dir.equalsIgnoreCase("southwest"))
			{
				num ++;
			}
				
		}
		for (int i = 0; i < Paths.size(); i++)
		{
			// Only add obvious directions
			String dir = ((Path)Paths.get(i)).dirToString();
			if (dir.equalsIgnoreCase("north")||dir.equalsIgnoreCase("south")
				|| dir.equalsIgnoreCase("east")||dir.equalsIgnoreCase("west")
				|| dir.equalsIgnoreCase("northeast")||dir.equalsIgnoreCase("northwest")
				|| dir.equalsIgnoreCase("southeast")||dir.equalsIgnoreCase("southwest"))
			{
				if (count == 0 && num == 1)
					roomDesc = roomDesc + ((Path)Paths.get(i)).dirToString() + ".";
				else if (count == 0 && num == 2)
					roomDesc = roomDesc + ((Path)Paths.get(i)).dirToString() + " ";
				else if (count == 0 && num > 2)
					roomDesc = roomDesc + ((Path)Paths.get(i)).dirToString() + ", ";
				else if (count + 1 == num)
					roomDesc = roomDesc + "and " + ((Path)Paths.get(i)).dirToString().toLowerCase() + ".";
				else
					roomDesc = roomDesc + ((Path)Paths.get(i)).dirToString().toLowerCase() + ", ";
				
				count ++;
			}
				
		}
		
		// *************************************************************************		
		// Display all other players
		// *************************************************************************
		roomDesc = roomDesc + "\nAlso here: "; 
		num = 0;
		count = 0;
		for (int i = 0; i < Mobiles.size(); i++)
		{
			if (((Mobile)Mobiles.get(i)).isPlayer())
			{
				if (!((Player)Mobiles.get(i)).equals(thisPlayer))
					num ++;
			}
		}
		for (int i = 0; i < Mobiles.size(); i++)
		{
			if (((Mobile)Mobiles.get(i)).isPlayer())
			{
				if (!((Player)Mobiles.get(i)).equals(thisPlayer))
				{
					if (count == 0 && num == 1)
						roomDesc = roomDesc + ((Player)Mobiles.get(i)).fullNameAndStatusToString() + ".";
					else if (count == 0 && num == 2)
						roomDesc = roomDesc + ((Player)Mobiles.get(i)).fullNameAndStatusToString() + " ";
					else if (count == 0 && num > 2)
						roomDesc = roomDesc + ((Player)Mobiles.get(i)).fullNameAndStatusToString() + ", ";
					else if (count + 1 == num)
						roomDesc = roomDesc + "and " + ((Player)Mobiles.get(i)).fullNameAndStatusToString() + ".";
					else
						roomDesc = roomDesc + ((Player)Mobiles.get(i)).fullNameAndStatusToString() + ", ";
					count ++;
				}
				
			}
		}
		
		return roomDesc;
	}
	
	/**
	 * Saves the state of the room.
	 * @param myWriter is the name of the PrintWriter.
	 */
	public void saveRoom(PrintWriter myWriter)
	{
		myWriter.println(Title);
		myWriter.println(Description);
		myWriter.println(Id);
		
		myWriter.println(Paths.size());
		for (int i=0; i < Paths.size(); i++)
		{
			((Path)Paths.get(i)).savePath(myWriter);
		}
		
		myWriter.flush();
	}

	/**
	 * Accesses the room Id.
	 * @return The room Id.
	 */
	public int getId()
	{
		return Id;
	}
	
	/**
	 * Gets the number of players in the room.
	 * @return The number of players in the room.
	 */
	public int numPlayers()
	{
		int num = 0;
		for (int i = 0; i < Mobiles.size(); i++)
		{
			if(((Mobile)Mobiles.get(i)).isPlayer())
				num++;
		}
		return num;
	}
	
	/**
	 * Loads a room from a file.
	 * @param mybuffRead The file to be read from.
	 */
	public void loadRoom(BufferedReader mybuffRead)
	{
		try
		{
			String input;
			int numinput;
			
			// Title and description and Id
			Title = mybuffRead.readLine();
			Description = mybuffRead.readLine();
			input = mybuffRead.readLine();
			numinput = Integer.parseInt(input);
			Id = numinput;
			
			// Number of paths
			input = mybuffRead.readLine();
			numinput = Integer.parseInt(input);
			
			for(int i = 0; i < numinput; i++)
			{
			// Store each path, excluding room	
				Path newPath = new Path();
				newPath.loadPath(mybuffRead);
				Paths.add(newPath);
			}
			
		}
		catch(IOException e){}
	}
	
	
	/**
	 * Sets the room of each path to the appropiate destination room.
	 * @param Rooms The list of rooms within the world.
	 */
	public void buildPaths(ArrayList Rooms)
	{
		//Loop through Paths
		for (int i=0; i < Paths.size();i++)
		{
			//Get Id
			int Id = ((Path)Paths.get(i)).getDestId();
			
			//Set Destination
			// Fetch room that has the Id number we have
			
			for (int j=0; j < Rooms.size(); j++)
			{
				if (((Room)Rooms.get(j)).getId() == Id)
				{
					// Set the destination of the ith path
					// to the room at j
					((Path)Paths.get(i)).setDestination(((Room)Rooms.get(j)));
					
				} // end if
			} // end for j
		} // end for i
	} // end buildPaths
	
} // End room

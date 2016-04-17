package mudServer;

import java.io.*;

/**
 * Paths connect rooms together and allow players to move between rooms.
 * A path will hold a direction and a destination room.
 * @author Nemesis
 *
 */
public class Path
{
	// -------------------------------------------------------------------------
	// Members
	// -------------------------------------------------------------------------
	private String Direction;
	private Room Destination;
	private int destId;
	
	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------	
	
	/**
	 * Create a new path from a direction and destination.
	 * @param newDir The path direction.
	 * @param newDest The path destination.
	 */
	public Path(String newDir, Room newDest)
	{
		Direction = newDir;
		Destination = newDest;
	}
	
	public Path (){}
	
	// -------------------------------------------------------------------------
	// Methods
	// -------------------------------------------------------------------------
	
	/**
	 * Gets the destination room.
	 * @return The connecting room.
	 */
	public Room getRoom()
	{
		return Destination;
	}
	
	/**
	 * Gets the room Id number for the destination room
	 * @return The Id number of the destination.
	 */
	public int getDestId()
	{
		return destId;
	}
	
	/**
	 * Sets the destination room.
	 * @param thisRoom
	 */
	public void setDestination(Room thisRoom)
	{
		Destination = thisRoom;
	}
	
	/**
	 * Return this path's direction as a string.
	 * @return The direction
	 */
	public String dirToString()
	{
		return Direction;
	}
	
	/**
	 * Saves this path to the provided file.
	 * @param pathWriter The file to save to.
	 */
	public void savePath(PrintWriter pathWriter)
	{
		pathWriter.println(Direction);
		pathWriter.println(Destination.getId());
		pathWriter.flush();
	}
	
	/**
	 * Loads the path from the provided buffer.
	 * @param mybuffRead The file to load from.
	 */
	public void loadPath(BufferedReader mybuffRead)
	{
		try
		{
		// Load direction (String) and destId (int)
		String input;
		int numinput;
		Direction = mybuffRead.readLine();
		input = mybuffRead.readLine();
		numinput = Integer.parseInt(input);
		destId = numinput;
		
		Destination = new Room();
		}
		
		catch(IOException e){}
	}
	
} // End path

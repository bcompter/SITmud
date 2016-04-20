package mudServer;

import java.util.ArrayList;
import java.io.*;

import Items.*;
import Mobiles.*;

/**
 * The World will hold all the rooms that make up the world.
 * 
 * It will also be able to send messages to all players in the world.
 * @author Nemesis
 *
 */
public class World 
{
    	// -------------------------------------------------------------------------
	// Members
	// -------------------------------------------------------------------------
	private ArrayList Rooms = new ArrayList();
	
	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------
	
	/**
	 * Creates a new world.
	 * 
	 * At the current version, the world is built staticaly.  Later, once world persistance
	 * is possible, there will be a world load feature.
	 */
	public World()
	{
		// Add crap to play with

		loadWorld();
		
		Item itemA = new generalItem("Salmon", "It's large and wet.  It doesn't smell good either.", "a");
		((Room)Rooms.get(0)).addItem(itemA);
		
		itemA = new generalItem("Shield", "A standard round shield made out of wood and bound with iron trim.", "a");
		((Room)Rooms.get(0)).addItem(itemA);
		itemA = new generalItem("Sword", "A sturdy blade with a double edge.", "a");
		((Room)Rooms.get(0)).addItem(itemA);
		
		Item itemB = new generalItem("Spear", "It is a sturdy wooden shaft with a razor sharp metal tip attached to the end.", "a");
		((Room)Rooms.get(1)).addItem(itemB);
		itemB = new generalItem("Large shield", "Built from metal, this protective shield appears sturdy and reliable.", "a");
		((Room)Rooms.get(1)).addItem(itemB);
		
		Item itemC = new Container("Bag", "It is a small leather bag capable of holding other items.", "a");
		((Room)Rooms.get(2)).addItem(itemC);
		
		String[]newEmotes = new String[3];
		newEmotes[0] = "picks it's nose and eats it!";
		newEmotes[1] = "franticly searches around, unsure of it's situation.";
		newEmotes[2] = "says: Yoo peeples is sooo oog-ly!";
		
		Mobile newMonster = new Monster
		("kobold","A", 
		"It is a scrawny creature with pale grey skin.  It stands about 4 and a half feet in height.  It has large eyes that are constantly in motion, scanning the area for potential threats or an easy target.",
		75, 75, 25, 25, newEmotes);
		((Room)Rooms.get(15)).addMobile(newMonster);
		
		newMonster = new Monster
		("kobold","A", 
		"It is a scrawny creature with pale grey skin.  It stands about 4 and a half feet in height.  It has large eyes that are constantly in motion, scanning the area for potential threats or an easy target.",
		75, 75, 25, 25, newEmotes);
		((Room)Rooms.get(15)).addMobile(newMonster);
		
		newMonster = new Monster
		("kobold","A", 
		"It is a scrawny creature with pale grey skin.  It stands about 4 and a half feet in height.  It has large eyes that are constantly in motion, scanning the area for potential threats or an easy target.",
		75, 75, 25, 25, newEmotes);
		((Room)Rooms.get(15)).addMobile(newMonster);
		
		newEmotes = new String[3];
		newEmotes[0] = "beats it's chest wildly in an effort to intimidate you.";
		newEmotes[1] = "roars with savage ferocity.";
		newEmotes[2] = "screams at you: Go away!";
		
		newMonster = new Monster
		("orc","An", 
		"A large grey skinned humanoid creature with an impressive build. It stalks the land in search of prey weaker than itself.",
		200, 100, 50, 65, newEmotes);
		((Room)Rooms.get(25)).addMobile(newMonster);
		
		newMonster = new Monster
		("orc","An", 
		"A large grey skinned humanoid creature with an impressive build. It stalks the land in search of prey weaker than itself.",
		200, 100, 50, 65, newEmotes);
		((Room)Rooms.get(25)).addMobile(newMonster);
		
		newMonster = new Monster
		("orc","An", 
		"A large grey skinned humanoid creature with an impressive build. It stalks the land in search of prey weaker than itself.",
		200, 100, 50, 65, newEmotes);
		((Room)Rooms.get(25)).addMobile(newMonster);
		
		newEmotes = new String[3];
		newEmotes[0] = "twirls his blade in the air while eyeing you.";
		newEmotes[1] = "glares at you with malicious intent.";
		newEmotes[2] = "says: Your money or your life maggot!";
		
		newMonster = new Monster
		("bandit","A", 
		"This common thief is clad in dark clothes and a bandana which hides his face from view.",
		125, 100, 0, 50, newEmotes);
		((Room)Rooms.get(7)).addMobile(newMonster);
		
	} // end constructor
		
	
	// -------------------------------------------------------------------------
	// Methods
	// -------------------------------------------------------------------------
	
	/**
	 * Sends the given message to all players in the world.
	 * @param message The message to be sent.
	 */
	public void sendMessage(String message)
	{
		
	}
	
	/**
	 * Adds a player to a room in the World at the index thisId.
	 * @param thisMobile The player to be added.
	 * @param thisIndex The id number of the room the player will be added to.
	 */
	public void addMobile(Mobile thisMobile, int thisIndex)
	{
		// Search through all rooms in the world and add the player to the
		// room with the matching Id.
		((Room)Rooms.get(thisIndex)).addMobile(thisMobile);
		if (thisMobile.isPlayer())
			((Room)Rooms.get(thisIndex)).exclusiveSend(thisMobile.nameToString() + " arrives.", (Player)thisMobile);
	}
	
	/**
	 * Saves the state of the world.
	 *
	 */
	public void saveWorld()
	{
	try
	{
		//Creates new file "thisworld.txt"
			
		FileWriter myFile = new FileWriter("./../thisworld.txt");
		BufferedWriter myBuff = new BufferedWriter(myFile);
		PrintWriter buffsaveStream = new PrintWriter(myBuff);
		buffsaveStream.println( Rooms.size() );
		buffsaveStream.flush();
		
		for (int i=0; i < Rooms.size(); i++)
		{
			((Room)Rooms.get(i)).saveRoom(buffsaveStream);
		}
		
		myFile.close();
		myBuff.close();
		buffsaveStream.close();
		
	}
	
	catch(IOException e){}
	
	}
	
	/**
	 * Loads a state of the world.
	 *
	 */
	public void loadWorld()
	{
	
		try 
		{
			String input;
			int numinput = 0;
			FileReader myReader = new FileReader("./../thisworld.txt");
			BufferedReader mybuffRead = new BufferedReader(myReader);
			
			input = mybuffRead.readLine();
			numinput = Integer.parseInt(input);
			
			for (int i = 0; i < numinput; i++)
			{
			// Load each room...
				Room newRoom = new Room();
				newRoom.loadRoom(mybuffRead);
				Rooms.add(newRoom);		
			}
			
			// Now link all rooms together
			for (int i = 0; i < Rooms.size(); i++)
			{
				Room thisRoom = (Room)Rooms.get(i);
				thisRoom.buildPaths(Rooms);
			}
		
		}	
		catch(IOException e)
                {
                    System.err.println("World::loadWorld, " + e.getMessage());
                }
	
	}
} // End world



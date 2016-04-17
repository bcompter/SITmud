package mudServer.Commands;

import mudServer.*;
import mudServer.Mobiles.*;

/**
 * Allows a dead character to be ressurected.
 * @author Nemesis
 *
 */
public class Depart implements Command
{
	public void execute(Mobile thisMobile,  String Args)
	{
	
		String[] modifiedArgs;
		modifiedArgs = Args.split(" ");
		
		// Check status
		if (thisMobile.getStatus() != 0)
		{
			((Player)thisMobile).sendMessage("Cannot depart now.");
		}
		// Check arguments
		else if (modifiedArgs.length != 1)
		{
			((Player)thisMobile).sendMessage("Please rephrase.");
		}
		else
		{			
			// Remove player from current room
			Room thisRoom = thisMobile.getMyRoom();
			thisRoom.removeMobile(thisMobile);
			
			// Restore health and lay the player down
			thisMobile.dealDamage(-500);
			thisMobile.setStatus(4);
			
			// Place player in front of the temple
			// I have to find it first...
			String direction;
			
			while (thisRoom.getId() != 5)
			{
				direction = thisRoom.getRandDir();
				thisRoom = thisRoom.getRoomFromPath(direction);
			}
			
			thisRoom.addMobile(thisMobile);
			Command newLook = new Look();
			newLook.execute(thisMobile, "look");
			
			// Send message to Player
			((Player)thisMobile).sendMessage("Your body dissolves and as your essence departs, you find yourself among familiar surroundings.");
			((Player)thisMobile).sendMessage("You awaken feeling slightly drained but in one piece again.");
			
		}
		
	} // end execute
	
} // end Drop

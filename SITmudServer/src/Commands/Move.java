package mudServer.Commands;

import mudServer.Room;
import mudServer.Mobiles.*;
import mudServer.*;

/**
 * Moves the player from his current room along a path to an adjoining room.
 * @author Nemesis
 *
 */
public class Move implements Command
{
	public void execute(Mobile thisMobile,  String args)
	{
		// Check status...
		if (thisMobile.getStatus() == 0)
		{
			if (thisMobile.isPlayer())
				((Player)thisMobile).sendMessage("You can't do that. You are dead!");
		}
		else if (thisMobile.getStatus() > 1)
		{
			if (thisMobile.isPlayer())
				((Player)thisMobile).sendMessage("You must be standing to do that.");
		}
		else
		{
			String toPlayer, toRoom;
			String[] modifiedArgs;
			
			modifiedArgs = args.split(" ");
			
			if (modifiedArgs.length > 2)
			{
				// Too many arguments
				toPlayer = "You cannot go there.";
				((Player)thisMobile).sendMessage(toPlayer);
			}
			else if (modifiedArgs.length < 2)
			{
				// Not enough arguments
				toPlayer = modifiedArgs[0] + " where?";
				((Player)thisMobile).sendMessage(toPlayer);
			}
			else
			{
				// Format is correct, move the mobile.
				
				String direction = modifiedArgs[1];
				Room oldRoom = thisMobile.getMyRoom();
				Room newRoom = oldRoom.getRoomFromPath(direction);
				
				String fwdDir = oldRoom.getDir(newRoom);
				String revDir = newRoom.getDir(oldRoom);
				
				if (!(newRoom.getId() == -1))
				{
					// Remove from current room
					oldRoom.removeMobile(thisMobile);
					toPlayer = "You move " + fwdDir + ".";
					toRoom = thisMobile.fullNameToString() + " moves " + fwdDir + ".";
					oldRoom.sendMessage(toRoom);
					
					if (thisMobile.isPlayer())
						((Player)thisMobile).sendMessage(toPlayer);
									
					// Add to new room
					newRoom.addMobile(thisMobile);
					toRoom = thisMobile.fullNameToString() + " arrives from the " + revDir + ".";
					if (thisMobile.isPlayer())
					{
						Command newLook = new Look();
						newLook.execute(thisMobile, "look");
						newRoom.exclusiveSend(toRoom, (Player)thisMobile);
					}
					else
					{
						newRoom.sendMessage(toRoom);
					}
					
				}
				else
				{
					// The given direction was not found
					toPlayer = "You cannot go there.";
					((Player)thisMobile).sendMessage(toPlayer);
				}	
			}
		}
		

		
	} // end execute
} // end Move

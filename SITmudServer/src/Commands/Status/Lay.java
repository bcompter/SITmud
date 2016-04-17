package mudServer.Commands.Status;

import mudServer.Room;
import mudServer.Commands.Command;
import mudServer.Mobiles.*;
import mudServer.*;

/**
 * Allows a player to lie down
 * The String following the lie command is displayed
 * to all users in the room.
 * @author Nemesis
 *
 */


public class Lay implements Command
{
	public void execute(Mobile thisMobile, String args)
	{
		
		String[] modifiedArgs;
		modifiedArgs = args.split(" ");
		String toRoom, toPlayer;
		
		Room thisRoom = thisMobile.getMyRoom();
		
		if (thisMobile.getStatus() < 1)
		{
			if (thisMobile.isPlayer())
				((Player)thisMobile).sendMessage("You can't do that. You are dead!");
		}
		else if (thisMobile.getStatus() == 4)
		{
			if (thisMobile.isPlayer())
				((Player)thisMobile).sendMessage("You are already laying down.");
		}
		else
		{
			if (modifiedArgs.length == 1)
			{
				toRoom = thisMobile.nameToString() + " lays down.";
				toPlayer = "You lay down.";
				thisMobile.setStatus(4);
				if (thisMobile.isPlayer())
				{
					thisRoom.exclusiveSend(toRoom, (Player)thisMobile);
					((Player)thisMobile).sendMessage(toPlayer);
				}
				else
				{
					thisRoom.sendMessage(toRoom);
				}
			}
			else if (modifiedArgs.length >= 2) 
			{
				toRoom = thisMobile.nameToString() + " lay down";
				toPlayer = "You lay down";
				thisMobile.setStatus(4);
				for(int i=1; i<(modifiedArgs.length); i++)
				{
					toPlayer = toPlayer + " " + modifiedArgs[i];
					toRoom = toRoom + " " + modifiedArgs[i];
				}
				toPlayer = toPlayer + ".";
				toRoom = toRoom + ".";
				if (thisMobile.isPlayer())
				{
					thisRoom.exclusiveSend(toRoom, (Player)thisMobile);
					((Player)thisMobile).sendMessage(toPlayer);
				}
				else
				{
					thisRoom.sendMessage(toRoom);
				}
			}
			else
			{
				toPlayer = "Please rephrase.";
				((Player)thisMobile).sendMessage(toPlayer);
			}
		}
		
		
	} // end execute
} // end Lay

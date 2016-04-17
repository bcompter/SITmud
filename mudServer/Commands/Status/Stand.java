package mudServer.Commands.Status;

import mudServer.Commands.Command;
import mudServer.Mobiles.*;
import mudServer.*;

/**
 * Allows a player to kneel
 * The String following the Kneel command is displayed
 * to all users in the room.
 * @author pamccabe
 *
 */


public class Stand implements Command
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
		else if (thisMobile.getStatus() == 1)
		{
			if (thisMobile.isPlayer())
				((Player)thisMobile).sendMessage("You are already standing.");
		}
		else
		{
			if (modifiedArgs.length == 1)
			{
				toRoom = thisMobile.nameToString() + " stands up.";
				toPlayer = "You stand up.";
				thisMobile.setStatus(1);
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
				toRoom = thisMobile.nameToString() + " stands up";
				toPlayer = "You stand up";
				thisMobile.setStatus(1);
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
} // end stand

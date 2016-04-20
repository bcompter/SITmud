package Commands.Status;

import mudServer.Room;
import Commands.Command;
import Mobiles.*;

/**
 * Allows a player to kneel
 * The String following the Kneel command is displayed
 * to all users in the room.
 * @author Nemesis
 *
 */


public class Kneel implements Command
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
		else if (thisMobile.getStatus() == 2)
		{
			if (thisMobile.isPlayer())
				((Player)thisMobile).sendMessage("You are already kneeling.");
		}
		else
		{
			if (modifiedArgs.length == 1)
			{
				toRoom = thisMobile.nameToString() + " kneels down.";
				toPlayer = "You kneel down.";
				thisMobile.setStatus(2);
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
				toRoom = thisMobile.nameToString() + " kneels";
				toPlayer = "You kneel";
				thisMobile.setStatus(2);
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
} // end kneel

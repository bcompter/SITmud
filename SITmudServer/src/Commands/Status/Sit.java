package Commands.Status;

import mudServer.Room;
import Commands.Command;
import Mobiles.*;

/**
 * Allows a player to sit
 * The String following the sit command is displayed
 * to all users in the room.
 */
public class Sit implements Command
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
		else if (thisMobile.getStatus() == 3)
		{
			if (thisMobile.isPlayer())
				((Player)thisMobile).sendMessage("You are already sitting.");
		}
		else
		{
			if (modifiedArgs.length == 1)
			{
				toRoom = thisMobile.nameToString() + " sits down.";
				toPlayer = "You sit down.";
				thisMobile.setStatus(3);
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
				toRoom = thisMobile.nameToString() + " sit down";
				toPlayer = "You sit down";
				thisMobile.setStatus(3);
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
} // end Sit

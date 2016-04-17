package mudServer.Commands.Emotes;

import mudServer.Mobiles.*;
import mudServer.*;
import mudServer.Commands.Command;

/**
 * Allows a player to kiss
 * The String following the Kiss command is displayed
 * to all users in the room.
 * @author pamccabe
 *
 */


public class Kiss implements Command
{
	public void execute(Mobile thisMobile, String args)
	{
		// Check status...
		if (thisMobile.getStatus() == 0)
		{
			if (thisMobile.isPlayer())
				((Player)thisMobile).sendMessage("You can't do that. You are dead!");
		}
		else
		{
			String[] modifiedArgs;
			modifiedArgs = args.split(" ");
			String toRoom, toPlayer;
			
			Room thisRoom = thisMobile.getMyRoom();
			
			if (modifiedArgs.length == 1)
			{
				toRoom = thisMobile.nameToString() + " blows a kiss.";
				toPlayer = "You blow a kiss.";
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
				toRoom = thisMobile.nameToString() + " kisses";
				toPlayer = "You kiss";
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
} // end Kiss

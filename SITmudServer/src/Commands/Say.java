package Commands;

import mudServer.Room;
import Mobiles.*;

/**
 * Allows the player to talk.
 * Other players in the same room will see the message.
 * @author Nemesis
 *
 */
public class Say implements Command 
{
	public void execute(Mobile thisMobile,  String args)
	{
		String toRoom, toPlayer;
		Room thisRoom = thisMobile.getMyRoom();
		String modifiedArg = args.replaceFirst("'", "");
		
		// Check status...
		if (thisMobile.getStatus() == 0)
		{
			if (thisMobile.isPlayer())
			{
				toRoom = "You hear the ghostly voice of " + thisMobile.nameToString() + " say: " + modifiedArg;
				toPlayer = "You say: " + modifiedArg;
				
				if (thisMobile.isPlayer())
				{
					thisRoom.exclusiveSend(toRoom, (Player)thisMobile);
					((Player)thisMobile).sendMessage(toPlayer);
				}
				else
					thisRoom.sendMessage(toRoom);
			}
		}
		else
		{	
			if (thisMobile.isPlayer())
				toRoom = thisMobile.nameToString() + " says: " + modifiedArg;
			else
				toRoom = thisMobile.fullNameToString() + " says: " + modifiedArg;
			
			toPlayer = "You say: " + modifiedArg;
			
			if (thisMobile.isPlayer())
			{
				thisRoom.exclusiveSend(toRoom, (Player)thisMobile);
				((Player)thisMobile).sendMessage(toPlayer);
			}
			else
				thisRoom.sendMessage(toRoom);
		}
		
	} // end execute
	
} // end say

package mudServer.Commands;

import mudServer.Mobiles.*;
import mudServer.Items.*;
import mudServer.*;

/**
 * Gets an item from the room and puts it into
 * the players hands.
 * @author Nemesis
 *
 */
public class Get implements Command
{
	public void execute(Mobile thisMobile,  String args)
	{
		
		// Check status...
		if (thisMobile.getStatus() == 0)
		{
			if (thisMobile.isPlayer())
				((Player)thisMobile).sendMessage("You can't do that. You are dead!");
		}		
		else
		{
			String toPlayer, toRoom;
			String[] modifiedArgs = args.split(" ");
			
			Room thisRoom = thisMobile.getMyRoom();
			
			if(modifiedArgs.length == 1)
			{
				// Not enough arguments
				toPlayer = "Get what?";
				((Player)thisMobile).sendMessage(toPlayer);
			}
			else if (modifiedArgs.length > 2)
			{
				// Too many arguments
				toPlayer = "Please rephrase.";
				((Player)thisMobile).sendMessage(toPlayer);
			}
			else
			{
				// get <item>
				// Searches for an item in the room
				
				String itemName = modifiedArgs[1];
				Item thisItem = thisRoom.getItem(itemName);
				
				if(thisItem.isValid())
				{
					// Found the item
					if (thisMobile.holdItem(thisItem))
					{
						// At least one hand was free, success
						thisRoom.removeItem(thisItem);
						toPlayer = "You pick up " + thisItem.fullnameToString().toLowerCase() + ".";
						toRoom = thisMobile.nameToString() + " picks up " + thisItem.fullnameToString().toLowerCase() + ".";
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
						// Hands are full
						toPlayer = "You need a free hand for that.";
						((Player)thisMobile).sendMessage(toPlayer);
					}
				}
				else
				{
					// Item was not found
					toPlayer = "I could not find what you were looking for.";
					((Player)thisMobile).sendMessage(toPlayer);
				}
			}
		}
		
		
	} // end execute
	
} // end Get

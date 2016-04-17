package mudServer.Commands;

import mudServer.Room;
import mudServer.*;
import mudServer.Items.*;
import mudServer.Mobiles.*;

/**
 * Drops an item to the ground.  
 * The targeted item is removed from the player's hand and 
 * put into the room.
 * @author Nemesis
 *
 */
public class Drop implements Command
{
	public void execute(Mobile thisMobile,  String Args)
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
			String[] modifiedArgs;
			
			modifiedArgs = Args.split(" ");
			
			if (modifiedArgs.length == 1)
			{
				// Not enough arguments
				toPlayer = "Drop what?";
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
				// Format is correct, drop the item
				String itemName = modifiedArgs[1];
				Item thisItem = thisMobile.getHeldItem(itemName);
				Room thisRoom = thisMobile.getMyRoom();
				
				if (thisItem.isValid())
				{
					// Drop the item
					itemName = thisItem.fullnameToString();
					thisMobile.dropItem(thisItem);
					thisRoom.addItem(thisItem);
					
					toPlayer = "You drop " + itemName + ".";
					toRoom = thisMobile.nameToString() + " drops " + itemName + ".";
					
					if (thisMobile.isPlayer())
					{
						((Player)thisMobile).sendMessage(toPlayer);
						thisRoom.exclusiveSend(toRoom, (Player)thisMobile);
					}
					else
					{
						thisRoom.sendMessage(toRoom);
					}
					
				}
				else
				{
					// Not holding the item
					toPlayer = "You are not holding that.";
					((Player)thisMobile).sendMessage(toPlayer);
				}
				
			}
		}
		
		
	} // end execute
	
} // end Drop

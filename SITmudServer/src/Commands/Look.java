package mudServer.Commands;

import mudServer.Room;
import mudServer.Mobiles.*;
import mudServer.*;
import mudServer.Items.*;

/**
 * Provides a description of an item or room.
 * @author Nemesis
 *
 */
public class Look implements Command
{
	public void execute(Mobile thisMobile,  String args)
	{
		Player thisPlayer = (Player)thisMobile;
		String toPlayer = new String();
		toPlayer = "nothing";
		String[] modifiedArgs;
		
		modifiedArgs = args.split(" ");
		
		Room thisRoom = thisPlayer.getMyRoom();
		
		//	Look
		// 	Looks at the room
		if (modifiedArgs.length == 1)
		{
			toPlayer = thisRoom.lookAtRoom(thisPlayer);
			thisPlayer.sendMessage(toPlayer);
		}
		else if (modifiedArgs.length > 2)
		{
			toPlayer = "Please rephrase.";
			thisPlayer.sendMessage(toPlayer);
		}
		else
		{
			String target = modifiedArgs[1];
			
			//   Look <item>/<mobile>
			//   Looks at an item in the room or another player
			
			Mobile targetMobile = thisRoom.getMobile(target);
			
			if(!targetMobile.nameToString().equals("nothing"))
			{
				toPlayer = targetMobile.lookAtMobile();
			}
			else
			{
				Item targetItem = thisRoom.getItem(target);
				if (!targetItem.nameToString().equals("nothing"))
				{
					toPlayer = targetItem.lookAtItem();
				}
				else
				{
					targetItem = thisMobile.getHeldItem(target);
					if (!targetItem.nameToString().equals("nothing"))
					{
						toPlayer = targetItem.lookAtItem();
					}
					else
					{
						targetItem = thisMobile.getWornItem(target);
						if (!targetItem.nameToString().equals("nothing"))
						{
							toPlayer = targetItem.lookAtItem();
						}
					}
				}
								
			}
			// Check if anything was found...
			if(toPlayer.equals("nothing"))
			{
				toPlayer = "I could not find what you were looking for.";
			}
			
			// Send message to player			
			thisPlayer.sendMessage(toPlayer);

		}
			
	} // end execute
} // end Look

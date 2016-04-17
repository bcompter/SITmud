package mudServer.Commands;

import mudServer.Room;
import mudServer.Mobiles.Mobile;
import mudServer.Mobiles.Player;

/**
 * Removes a body from the game world.
 * @author Nemesis
 *
 */
public class Loot implements Command 
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
			String[] modifiedArgs;
			modifiedArgs = args.split(" ");
			
			String toRoom, toPlayer;
			
			// Check user input
			if(modifiedArgs.length < 1)
			{
				toPlayer = "Loot what?";
				((Player)thisMobile).sendMessage(toPlayer);
			}
			else
			{
				// Loot the corpse
				Room thisRoom = thisMobile.getMyRoom();
				Mobile targetMobile = thisRoom.getMobile(modifiedArgs[1]);
				
				if (!targetMobile.isPlayer() && !targetMobile.nameToString().equals("nothing"))
				{
					thisRoom.removeMobile(targetMobile);
					
					toPlayer = "You search " + targetMobile.fullNameToString().toLowerCase() + " corpse.";
					toRoom = thisMobile.nameToString() + " searches " + targetMobile.fullNameToString() + " corpse.";
					((Player)thisMobile).sendMessage(toPlayer);
					thisRoom.exclusiveSend(toRoom, (Player)thisMobile);
					
					toRoom = targetMobile.fullNameToString() + " decomposes and turns to dust.";
					thisRoom.sendMessage(toRoom);
				}
				
			}
		}

	} // end execute
} // end Loot
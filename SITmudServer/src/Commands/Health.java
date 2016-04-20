package Commands;

import Mobiles.*;

/**
 * Reports total hitpoint to the player.
 * @author Nemesis
 *
 */
public class Health implements Command 
{
	public void execute(Mobile thisMobile,  String args)
	{
		String toPlayer;
		String[] modifiedArgs = args.split(" ");
		
		// Check format
		if (modifiedArgs.length > 1)
		{
			toPlayer = "Please rephrase.";
		}
		else
		{
			// Check status...
			if (thisMobile.getStatus() == 0)
			{
				toPlayer = "You are very dead.";
			}
			else
			{			
				int[] health = thisMobile.getHitPoints();
				toPlayer = "You have " + health[0] + " hitpoints remaining out of " + health[1] + ".";
			}
		}
	
		((Player)thisMobile).sendMessage(toPlayer);
		
	} // end execute
	
} // end health

package mudServer.Commands;

import mudServer.Mobiles.*;

/**
 * Displays the content of the players hands.
 * @author Nemesis
 *
 */
public class Glance implements Command
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
			Player thisPlayer = (Player)thisMobile;
			String toPlayer;
			String[] modifiedArgs;
		
			modifiedArgs = args.split(" ");
			
			if (modifiedArgs.length == 1)
			{
				String[] hands = thisPlayer.lookHands();
				toPlayer = "You glance down to see " + hands[0].toLowerCase() + " in your right hand and "
				+ hands[1].toLowerCase() + " in your left hand.";
				thisPlayer.sendMessage(toPlayer);
			}
			else
			{
				toPlayer = "Please rephrase.";
				thisPlayer.sendMessage(toPlayer);
			}
		}
		
	
	} // end execute
	
} // end Glance

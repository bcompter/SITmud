package mudServer.Commands;

import mudServer.Mobiles.*;

/**
 * Notify the player that he dun fucked up!
 * @author Nemesis
 *
 */
public class UnknownCmd implements Command
{
	public void execute(Mobile thisMobile,  String args)
	{
		String toPlayer;

		toPlayer = "No such command.";
		((Player)thisMobile).sendMessage(toPlayer);
	}

}

package mudServer.Commands;

import mudServer.Mobiles.*;

/**
 * Command will encapsulate all player driven actions.
 * Each command may be executed given a reference the world and the
 * calling player.
 * 
 * It is the responsibility of each command implimentation to parse the arguments.
 * @author Nemesis
 *
 */
public interface Command 
{
	/**
	 * Execute the command given the following information.
	 * 
	 * @param thisMobile The mobile giving the command.
	 * @param args The complete command passed from the game server.
	 */	
	public void execute(Mobile thisMobile,  String args);
}

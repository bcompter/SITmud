package Commands;

import java.util.*;

import Mobiles.Mobile;
import Mobiles.Player;

/**
 * Logs a player out of the game and saves his character.
 */
public class Logout implements Command
{
    public void execute(Mobile thisMobile,  String args)
    {
        String name = ((Player)thisMobile).nameToString();

        // Remove the player from the room
        ((Player)thisMobile).disconnect();

        // Save player to file
        ((Player)thisMobile).savePlayer();

        // Remove the player from the GameServer
        System.out.println(name + " logged out at " + new Date() + ".\n");
    }

} // end Logout

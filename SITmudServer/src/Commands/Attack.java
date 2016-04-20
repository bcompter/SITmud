package Commands;

import mudServer.Room;
import java.util.Random;
import Mobiles.Mobile;
import Mobiles.Player;

/**
 * This allows a player to attack a target
 * The command checks if the argument is a target and does checks against
 * Attack Strength, Defense Strength, attack speed, etc.
 * 
 * Enjoy!
 * 
 * @author pamccabe
 *
 */
public class Attack implements Command
{
	public void execute(Mobile thisMobile,  String Args)
	{
		String toPlayer, toRoom, addTo;
		String[] modifiedArgs;
		modifiedArgs = Args.split(" ");
		
		// Check status...
		if (thisMobile.getStatus() == 0)
		{
			if (thisMobile.isPlayer())
				((Player)thisMobile).sendMessage("You can't do that. You are dead!");
		}
		// Check for roundtime...
		else if (thisMobile.isInRoundTime())
		{
			if (thisMobile.isPlayer())
			{
				toPlayer = "Too soon to attack again.";
				((Player)thisMobile).sendMessage(toPlayer);
			}
		}
		// Check arguments
		else if (modifiedArgs.length != 2)
		{
			toPlayer = "Please rephrase.";
			((Player)thisMobile).sendMessage(toPlayer);
		}
		// Procede with attack sequence
		else
		{
			Room thisRoom = thisMobile.getMyRoom();
			Mobile targetMobile = thisRoom.getMobile(modifiedArgs[1]);
			
			// Check is target is invalid
			if (targetMobile.nameToString().equals("nothing"))
			{
				if (thisMobile.isPlayer())
				{
					toPlayer = "I could not find what you were looking for.";
					((Player)thisMobile).sendMessage(toPlayer);
				}
			}
			// Check if target is dead
			else if(targetMobile.getStatus() == 0)
			{
				if (thisMobile.isPlayer())
				{
					toPlayer = "Your target is already dead.";
					((Player)thisMobile).sendMessage(toPlayer);
				}
			}
			// Check if target is the same as the attacker
			else if(targetMobile.equals(thisMobile))
			{
				if (thisMobile.isPlayer())
				{
					toPlayer = "You cannot attack yourself.";
					((Player)thisMobile).sendMessage(toPlayer);
				}
			}
			// Proceed with attack sequence
			else
			{
				// Set roundtime
				thisMobile.setRoundTime();
				
				// Calculate all attack parameters
				int attackStr = thisMobile.calcAS();
				int defendStr = targetMobile.calcDS();
				Random die = new Random();
				int dieRoll = Math.abs(die.nextInt()) % 100 + 1;
				int result = attackStr - defendStr + dieRoll;
				int damage = result - 100;
				
				// Format message displays
				toPlayer = "\nYou swing your weapon at " + targetMobile.fullNameToString().toLowerCase() + "!\n";
				toRoom = thisMobile.fullNameToString() + " swings his weapon at " + targetMobile.fullNameToString().toLowerCase()+ "!\n";
				
				addTo = "AS: " + attackStr + " - DS: " + defendStr + " + D100(" + dieRoll + ") = " + result + ".\n";
				toPlayer = toPlayer + addTo;
				toRoom = toRoom + addTo;
				
				// Check for a hit
				if(damage > 0)
				{
					targetMobile.dealDamage(damage);
					addTo = "   ... and hits for " + damage + " damage.";
					toPlayer = toPlayer + addTo;
					toRoom = toRoom + addTo;
				}
				else
				{
					addTo = "   ... and misses.";
					toPlayer = toPlayer + addTo;
					toRoom = toRoom + addTo;
				}
				
				addTo = "\nRoundtime: 5 seconds.";
				toPlayer = toPlayer + addTo;
				
				// Messages are completed, send to the room.
				if (thisMobile.isPlayer())
				{
					thisRoom.exclusiveSend(toRoom, (Player)thisMobile);
					((Player)thisMobile).sendMessage(toPlayer);
				}
				else
				{
					thisRoom.sendMessage(toRoom);
				}
				
				// Check for a kill...
				if(targetMobile.getStatus() == 0)
				{
					// Target has died.
					toRoom = "\n" + targetMobile.fullNameToString() + " screams one last time and dies.";
					toPlayer = "You have died!";
					
					// Send Strings
					if (targetMobile.isPlayer())
					{
						((Player)targetMobile).sendMessage(toPlayer);
						thisRoom.exclusiveSend(toRoom, (Player)targetMobile);
					}
					else
					{
						thisRoom.sendMessage(toRoom);
					}
					
				} // end kill check
				
				
			}
			
		}

	}// end execute
}//end class

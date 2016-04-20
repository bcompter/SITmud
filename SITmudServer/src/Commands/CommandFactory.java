package Commands;

import Commands.Emotes.*;
import Commands.Status.*;


/**
 * Parses commands and separates them from the arguments. 
 * New commands are made and returned to the calling object.
 * @author Nemesis
 *
 */
public class CommandFactory 
{	
	/**
	 * Returns a new command object to the calling object
	 * @param thisInput Input from calling object
	 * @return newCommand The new command object
	 */
	public Command getCommand(String thisInput)
	{
		Command newCommand;
		String input = parseInput(thisInput);
		
		if (input.equalsIgnoreCase("'"))
			newCommand = new Say();
		else if (input.equalsIgnoreCase("look")||input.equalsIgnoreCase("l"))
			newCommand = new Look();
		else if (input.equalsIgnoreCase("move")||input.equalsIgnoreCase("go"))
			newCommand = new Move();
		else if (input.equalsIgnoreCase("get"))
			newCommand = new Get();
		else if (input.equalsIgnoreCase("drop"))
			newCommand = new Drop();
		else if (input.equalsIgnoreCase("quit")||input.equalsIgnoreCase("exit"))
			newCommand = new Logout();
		else if (input.equalsIgnoreCase("glance"))
			newCommand = new Glance();
		else if (input.equalsIgnoreCase("smile"))
			newCommand = new Smile();
		else if (input.equalsIgnoreCase("swap"))
			newCommand = new Swap();
		else if (input.equalsIgnoreCase("beg"))
			newCommand = new Beg();
		else if (input.equalsIgnoreCase("bow"))
			newCommand = new Bow();
		else if (input.equalsIgnoreCase("cry"))
			newCommand = new Cry();
		else if (input.equalsIgnoreCase("dance"))
			newCommand = new Dance();
		else if (input.equalsIgnoreCase("flex"))
			newCommand = new Flex();
		else if (input.equalsIgnoreCase("kiss"))
			newCommand = new Kiss();
		else if (input.equalsIgnoreCase("kneel"))
			newCommand = new Kneel();
		else if (input.equalsIgnoreCase("stand"))
			newCommand = new Stand();		
		else if (input.equalsIgnoreCase("sit"))
			newCommand = new Sit();	
		else if (input.equalsIgnoreCase("lay"))
			newCommand = new Lay();
		else if (input.equalsIgnoreCase("laugh"))
			newCommand = new Laugh();
		else if (input.equalsIgnoreCase("point"))
			newCommand = new Point();
		else if (input.equalsIgnoreCase("salute"))
			newCommand = new Salute();
		else if (input.equalsIgnoreCase("wave"))
			newCommand = new Wave();
		else if (input.equalsIgnoreCase("wink"))
			newCommand = new Wink();
		else if (input.equalsIgnoreCase("attack"))
			newCommand = new Attack();
		else if (input.equalsIgnoreCase("loot"))
			newCommand = new Loot();
		else if (input.equalsIgnoreCase("depart"))
			newCommand = new Depart();
		else if (input.equalsIgnoreCase("health")||input.equalsIgnoreCase("hea"))
			newCommand = new Health();
		else
			newCommand = new UnknownCmd();
		
		return newCommand;
	}
	
	/**
	 * Separate input into a command and an argument
	 * @param thisInput Input from client
	 */
	private String parseInput(String thisInput)
	{
		String[] input;
		
		// First detect if it is a say command
		if (thisInput.startsWith("'"))
			return "'";
		
		// Otherwise, a normal command is assumed
		else
			input = thisInput.split(" ");
			
		return input[0];
	}
	
} // end CommandFactory

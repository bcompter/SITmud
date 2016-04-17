package mudServer.Mobiles;

import mudServer.*;
import mudServer.Commands.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;

public class Monster extends MobileStd implements Mobile
{
	// -------------------------------------------------------------------------
	// Members
	// -------------------------------------------------------------------------
	private String article;
	private String description;
	private Timer actionTimer;
	
	// Emote strings
	private String[] emotes;
	
	// Monster actions
	private int emoteRate;
	private int currentEmote;
	private int moveRate;
	private int currentMove;
	private int attackRate;
	private int currentAttack;
	
	
	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------
	
	/**
	 * Constructor given all 
	 */
	public Monster(String newName, String newArticle, String newDesc, int newHealth, 
	int newEmote, int newMove, int newAttack, String[] newEmotes)
	{
		name = newName;
		article = newArticle;
		description = newDesc;
		hitPoints = newHealth;
		MAXHitPoints = newHealth;
		status = 1;
		
		emoteRate = newEmote;
		moveRate = newMove;
		attackRate = newAttack;
		
		// Randomize starting rates
		Random die = new Random();
		currentEmote = Math.abs((die.nextInt())) % 1000 + 1;
		currentMove = Math.abs((die.nextInt())) % 1000 + 1;
		currentAttack = Math.abs((die.nextInt())) % 1000 + 1;
		
		emotes = newEmotes;
		
		actionTimer = new Timer();
		actionTimer.schedule(new actionTask(this), 1000, 1000);
	
	}
	
	/**
	 * Default constructor.
	 *
	 */
	public Monster()
	{
		name = "nothing";
	}
	
	
	// -------------------------------------------------------------------------
	// Methods
	// -------------------------------------------------------------------------
	
	/**
	 * Get the full name fo the monster including article.
	 * @return The full name as a string.
	 */
	public String fullNameToString()
	{
		return article + " " + name;
	}
	
	/**
	 * Get the full name and status for the monster including article.
	 * @return The full name and status as a string.
	 */
	public String fullNameAndStatusToString()
	{
		String full = fullNameToString();
		
		if(status == 0)
			full = full + " who appears dead";
		else if (status == 2)
			full = full + " who is kneeling";		
		else if (status == 3)
			full = full + " who is sitting";
		else if (status == 4)
			full = full + " who is laying down";
		
		return full;
	}
	
	/**
	 * Look at the monster
	 * @return The monster's description as a string.
	 */
	public String lookAtMobile()
	{
		return description;
	}
	
	/**
	 * Always returns false for a monster.
	 * @return False always.
	 */
	public boolean isPlayer()
	{
		return false;
	}

	/**
	 * Emote the monster
	 * @param dieRoll Random die roll
	 */
	public void mobileEmote(int dieRoll)
	{		
		if (dieRoll + currentEmote > 1000)
		{
			// Emote the monster
			Random newRnd = new Random();
			Room thisRoom = this.getMyRoom();
			int num = Math.abs(newRnd.nextInt()) % emotes.length;
			String toRoom = this.fullNameToString() + " " + emotes[num];
			thisRoom.sendMessage(toRoom);
			
			currentEmote = 0;
		}
		else
		{
			// Did not emote this time, so increase rate
			currentEmote += emoteRate;
		}
		
	} // end mobileEmote
	
	/**
	 * Move the monster.
	 * @param dieRoll Random die roll
	 */
	public void mobileMove(int dieRoll)
	{		
		if (dieRoll + currentMove > 1000)
		{
			// Find out the number of players in the room.
			Room thisRoom = this.getMyRoom();
			int numPlayers = thisRoom.numPlayers();
			
			// If there are not any players in the room, move randomly
			// OR
			// If there are players in the room and the monster has < 30%
			// of its max health, flee in a random direction
			if(numPlayers == 0 || hitPoints < 3 * (MAXHitPoints / 10))
			{
				String direction = thisRoom.getRandDir();
				Command newCmd = new Move();
				newCmd.execute(this, "move " + direction);
			}
	
			currentMove = 0;
		}
		else
		{
			// Did not move this time, so increase rate
			currentMove += moveRate;
		}
		
	} // end mobileMove
	
	
	/**
	 * Attack with the monster.
	 * @param dieRoll Random die roll.
	 */
	public void mobileAttack(int dieRoll)
	{
		if (dieRoll + currentAttack > 1000)
		{
			// Find out the number of players in the room.
			Room thisRoom = this.getMyRoom();
			int numPlayers = thisRoom.numPlayers();
			
			if(numPlayers != 0)
			{
				// Attack something
				Command newCmd = new Attack();
				newCmd.execute(this, "attack " + thisRoom.getRandPlayer());
				
			}
			
			currentAttack = 0;
		}
		else
		{
			// Did not attack, increase rate
			currentAttack += attackRate;
		}
	}
		
	/**
	 * Timer action handler, checks for monster actions based on random number generators.
	 * @author Nemesis
	 *
	 */
	class actionTask extends TimerTask
	{
		private Monster myMobile;
		
		public actionTask(Mobile thisMobile)
		{
			myMobile = (Monster)thisMobile;
		}
		
		public void run()
		{
			if(!(myMobile.getStatus() == 0))
			{
				Random die = new Random();
				myMobile.mobileEmote( Math.abs((die.nextInt())) % 100 + 1);  // Random number from 1 to 100
				myMobile.mobileMove ( Math.abs((die.nextInt())) % 100 + 1);
				myMobile.mobileAttack ( Math.abs((die.nextInt())) % 100 + 1);
			}
		}
		
	} // End emoteTask
	
} // end Monster

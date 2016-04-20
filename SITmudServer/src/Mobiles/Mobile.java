package Mobiles;

import mudServer.Room;
import Items.Item;

/**
 * Defines methods for which all Mobiles must implement.
 * There are two categories:
 * 
 * All methods whose implementation is the same for all Mobiles are
 * implemented by extending MobileStd.
 * 
 * All methods whose implementation is dependant on each new class 
 * of Mobile are defined here, but implemented in the specific class.
 * 
 * @author Nemesis
 */
public interface Mobile
{
	// -------------------------------------------------------------------------
	// Methods whose implementation is included in MobileStd.
	// -------------------------------------------------------------------------	
	public String nameToString();
	public void setRoom(Room thisRoom);
	public Room getMyRoom();
	
	public boolean holdItem(Item thisItem);
	public Item getHeldItem(String itemName);
	public void dropItem(Item thisItem);
	public Item getWornItem(String itemName);
	
	public int getStatus();
	public void setStatus(int thisStatus);
	public int[] getHitPoints();
	
	public int calcAS();
	public int calcDS();
	public void dealDamage(int Damage);
	public void setRoundTime();
	public boolean isInRoundTime();

	// -------------------------------------------------------------------------	
	// Methods whose implementation depends on the child class.
	// -------------------------------------------------------------------------	
	public boolean isPlayer();
	public String fullNameToString();
	public String fullNameAndStatusToString();
	public String lookAtMobile();
}

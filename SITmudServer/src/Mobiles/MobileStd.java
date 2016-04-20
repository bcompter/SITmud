package Mobiles;

import java.util.*;
import mudServer.Room;
import Items.*;


/**
 * MobileStd sets data members common to all Mobiles.  It also implements 
 * methods that are the same no matter what kind of mobile uses it.
 * @author Nemesis
 *
 */
public abstract class MobileStd
{
    // -------------------------------------------------------------------------
    // Members common to all mobiles
    // -------------------------------------------------------------------------
    protected String name;
    protected Room myRoom;
    protected int myRoomId;
    protected int status;
    protected Item rightHand = new generalItem();
    protected Item leftHand = new generalItem();
    protected ArrayList Items = new ArrayList();

    protected int MAXHitPoints;
    protected int hitPoints;

    protected long roundTime;

    // -------------------------------------------------------------------------	
    // Methods whose implementation is the same for all mobiles
    // -------------------------------------------------------------------------	

    /**
     * Sets myRoom to the thisRoom.
     * @param thisRoom The room the mobile will occupy.
     */
    public void setRoom(Room thisRoom)
    {
        myRoom = thisRoom;
    }

    /**
     * Get this mobiles current room.
     * @return The mobiles room.
     */
    public Room getMyRoom()
    {
            return myRoom;
    }

    /**
     * Get this mobiles current room Id.
     * @return The mobiles room Id.
     */
    public int getMyRoomId()
    {
        return myRoomId;
    }

    /**
     * Returns the mobile's name as a string.
     * @return Name The mobile's name.
     */
    public String nameToString()
    {
        return name;
    }

    /**
     * Place an item into the mobile's hands.
     * @param thisItem The item to be held.
     * @return True on success, false otherwise.
     */
    public boolean holdItem(Item thisItem)
    {
        // Check if the right hand is free
        if (rightHand.nameToString().equalsIgnoreCase("nothing"))
        {
            rightHand = thisItem;
            return true;
        }

        // Check if the left hand is free
        else if (leftHand.nameToString().equalsIgnoreCase("nothing"))
        {
            leftHand = thisItem;
            return true;
        }

        // No hands available, return false
        else
            return false;
    }

    /**
     * Swap the contents of the players hands
     *
     */
    public void swapHands()
    {
        Item temp;
        temp = rightHand;
        rightHand = leftHand;
        leftHand = temp;
    }


    /**
     * Looks at the contents of the players hands
     * @return The contents as a string.
     */
    public String[] lookHands()
    {
        String output = rightHand.fullnameToString() + ":" + 
        leftHand.fullnameToString();
        return output.split(":");
    }

    /**
     * Returns the first matching item worn by the player.
     * @param itemName The name of the item being searched for.
     * @return The item
     */
    public Item getWornItem(String itemName)
    {
        // Check worn items
        for (int i = 0; i < Items.size(); i++)
        {
            if( ((Item)Items.get(i)).nameToString().equalsIgnoreCase(itemName) )
                return (Item)Items.get(i);
        }
        return new generalItem();
    }

    /**
     * Returns the first matching item held by the player.
     * @param itemName The name of the item being searched for.
     * @return The item
     */
    public Item getHeldItem(String itemName)
    {
        // Check the right hand for a match
        if (rightHand.nameToString().equalsIgnoreCase(itemName))
            return rightHand;

        // Check the left hand for a match
        else if (leftHand.nameToString().equalsIgnoreCase(itemName))
            return leftHand;

        // No match found, return nothing
        else
            return new generalItem();
    }

    /**
     * Drop a held item
     * @param thisItem The item name.
     */
    public void dropItem(Item thisItem)
    {
        // Check the right hand for a match
        if (rightHand.equals(thisItem))
            rightHand = new generalItem();

        // Check the left hand for a match
        else if (rightHand.equals(thisItem))
            rightHand = new generalItem();
    }

    /**
     * Wear an item.
     * @param thisItem The item to be worn.
     */
    public void wearItem(Item thisItem)
    {

    }


    /**
     * Remove a worn item.
     * @param thisItem The item to be removed.
     */
    public void removeItem(Item thisItem)
    {

    }

    /**
     * Calculate attack strength
     * @return The AS as an int
     */
    public int calcAS()
    {
        return 100;
    }

    /**
     * Calculate defence strength
     * @return The DS as an int
     */
    public int calcDS()
    {
        return 50;
    }

    /**
     * Deal damage to a mobile.
     * @param Damage The damage to be dealt.
     */
    public void dealDamage(int Damage)
    {
        //subtract damage from HP
        hitPoints = hitPoints - Damage;

        // Kill the person if their health drops below 0
        if (hitPoints < 0)
            status  = 0;
    }


    /**
     * Gets the player status.
     * @return The status as an int.
     */
    public int getStatus()
    {
        return status;
    }

    /**
     * Sets the player status.
     * @param thisStatus The new status.
     */
    public void setStatus(int thisStatus)
    {
        status = thisStatus;
    }	

    /**
     * Gets the total hitpoints and max hitpoints
     * @return Total and max hp as string.
     */
    public int[] getHitPoints()
    {
        int[] result = new int[2];
        result[0] = hitPoints;
        result[1] = MAXHitPoints;
        return result;
    }

    /**
     * Sets the roundtime for this mobile to the current time.
     *
     */
    public void setRoundTime()
    {
        Date timestamp = new Date();
        long timeLong = timestamp.getTime();
        roundTime = (timeLong + 5000);
    }

    /**
     * Tells if a mobile is in round time or not.
     * @return True if the player has roundtime remaining.
     */
    public boolean isInRoundTime()
    {
        Date timestamp = new Date();
        long timeLong = timestamp.getTime();
        if (timeLong < roundTime)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
} // end MobileStd

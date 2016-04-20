package Commands;

import Mobiles.*;

public class Swap implements Command
{
    public void execute (Mobile thisMobile, String args)
    {
        // Check status...
        if (thisMobile.getStatus() == 0)
        {
            if (thisMobile.isPlayer())
                ((Player)thisMobile).sendMessage("You can't do that. You are dead!");
        }
        else
        {
            String toPlayer;
            String [] modifiedArgs = args.split(" ");

            if (modifiedArgs.length != 1)
            {
                toPlayer = "Please rephrase.";
                ((Player)thisMobile).sendMessage(toPlayer);
            }
            else
            {
                ((Player)thisMobile).swapHands();
                toPlayer = "You swap the contents of your hands.";
                ((Player)thisMobile).sendMessage(toPlayer);
            }
        }

    } // end execute
} // end Swap

package Items;

import java.io.PrintWriter;

public class Weapon extends ItemStd implements Item 
{
    // -------------------------------------------------------------------------
    // Members
    // -------------------------------------------------------------------------
    int attackStrength = 25;
    
    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a new Item from a full set of assignments.
     * The item will take on the attributes passed as arguments.
     * @param newName The item name.
     * @param newDesc The item description.
     */
    public Weapon(String newName, int as, String newDesc, String newArticle)
    {
        Name = newName.toLowerCase();
        Description = newDesc;
        Article = newArticle.toUpperCase();
        attackStrength = as;
    }

    /**
     * Default Constructor.  Used by Player.
     * The name "nothing" signifies a non-existant item.
     */
    public Weapon()
    {
        Name = "nothing";
        Article = "";
    }	

    // -------------------------------------------------------------------------
    // Methods
    // -------------------------------------------------------------------------

    /**
     * 
     * @return 
     */
    public int GetAttackStrength()
    {
        return attackStrength;
    }
    
    /**
     * 
     * @return 
     */
    public boolean isWeapon()
    {
        return true;
    }

    /**
     * Saves an item to a file.
     */
    public void saveItem(PrintWriter buffWriter)
    {
        buffWriter.println("generalItem");
        buffWriter.println(Name);
        buffWriter.println(Article);
        buffWriter.println(Description);
        buffWriter.flush();
    }
	
} // end generalItem

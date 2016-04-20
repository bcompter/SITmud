package Items;

import java.io.PrintWriter;

public class generalItem extends ItemStd implements Item 
{
    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a new Item from a full set of assignments.
     * The item will take on the attributes passed as arguments.
     * @param newName The item name.
     * @param newDesc The item description.
     */
    public generalItem(String newName, String newDesc, String newArticle)
    {
        Name = newName.toLowerCase();
        Description = newDesc;
        Article = newArticle.toUpperCase();
    }

    /**
     * Default Constructor.  Used by Player.
     * The name "nothing" signifies a non-existant item.
     */
    public generalItem()
    {
        Name = "nothing";
        Article = "";
    }	

    // -------------------------------------------------------------------------
    // Methods
    // -------------------------------------------------------------------------

    /**
     * Always returns false for general items
     * @return False always
     */
    public boolean isContainer()
    {
        return false;
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

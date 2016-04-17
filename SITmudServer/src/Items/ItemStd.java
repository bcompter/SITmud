package mudServer.Items;

/**
 * ItemStd sets data members common to all Items.  It also Defines and implements 
 * methods that are the same no matter what kind of item uses it.
 * @author Nemesis
 *
 */
public abstract class ItemStd 
{
	// -------------------------------------------------------------------------	
	// Members
	// -------------------------------------------------------------------------	
	protected String Name, Description, Article;
	
	// -------------------------------------------------------------------------	
	// Methods
	// -------------------------------------------------------------------------
	
	/**
	 * Returns the name of the item.
	 * @return The name of the item.
	 */
	public String nameToString()
	{
		return Name;
	}
	
	/**
	 * Returns the name of the item with the article included.
	 * @return The full name of the item.
	 */
	public String fullnameToString()
	{
		if (Name.equals("nothing"))
			return Name.toLowerCase();
		else
			return Article + " " + Name.toLowerCase();
	}
	
	/**
	 * Returns the description of the item.
	 * @return The item description
	 */
	public String lookAtItem()
	{
		return Description;
	}
	
	/**
	 * Tells if an item is a valid object and not set to nothing.
	 * @return False if the name is nothing, true otherwise.
	 */
	public boolean isValid()
	{
		if (Name.equalsIgnoreCase("nothing"))
			return false;
		else
			return true;
	}
	
} // end ItemStd

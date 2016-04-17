package mudServer.Items;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Container extends ItemStd implements Item
{
	// -------------------------------------------------------------------------
	// Members
	// -------------------------------------------------------------------------
	
	private ArrayList Items = new ArrayList();

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------
	
	/**
	 * Constructor used to create containers.
	 *
	 */
	public Container(String newName, String newDesc, String newArticle)
	{
		Name = newName.toLowerCase();
		Description = newDesc;
		Article = newArticle.toUpperCase();
	}
	
	/**
	 * Default constructors
	 *
	 */
	public Container(){}
	
	
	// -------------------------------------------------------------------------
	// Methods
	// -------------------------------------------------------------------------
	
	/**
	 * Always returns true for a container.
	 */
	public boolean isContainer()
	{
		return true;
	}
	
	/**
	 * Tests if the container is holding any items.
	 * @return True is the Items array is empty.
	 */
	public boolean isEmpty()
	{
		if (Items.size() == 0)
			return true;
		else 
			return false;
	}
	
	/**
	 * Adds an item to this container.
	 * @param thisItem The item to be added.
	 * @return True if the item can be added.  False otherwise.
	 */
	public boolean addItem(Item thisItem)
	{
		return true;
	}
	
	/**
	 * Gets the first item in the container with the given name and 
	 * returns it.  The item will be removed from the container.
	 * @param itemName The name of the item to be returned. 
	 * @return The item from the container.
	 */
	public Item getItem(String itemName)
	{
		return new generalItem();
	}
	
	/**
	 * Looks for an item within the conatiner.
	 * @param itemName The name of the item being searched for.
	 * @return True if an item match is made.
	 */
	public boolean hasItem(String itemName)
	{
		return true;
	}
	
	/**
	 * Look inside a container.
	 * @return The contents as a string.
	 */
	public String lookInside()
	{
		// Loop through all items inside and display 
		// their names.
		if (isEmpty())
		{
			return "That is empty.";
		}
		else
			return "You see some stuff.";
	} // end lookInside
	
	/**
	 * Saves an item to a file.
	 */
	public void saveItem(PrintWriter buffWriter)
	{
		buffWriter.println("Container");
		buffWriter.println(Name);
		buffWriter.println(Article);
		buffWriter.println(Description);
		
		// Save all items within the container
		// Later...
		
		buffWriter.flush();
	}
	
} // end Container

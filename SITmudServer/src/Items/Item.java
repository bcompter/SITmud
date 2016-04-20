package Items;

import java.io.*;

/**
 * Defines methods for which all Items must implement.
 * There are two categories:
 * All methods whose implementation is the same for all items are
 * implemented by extending ItemStd.
 * All methods whose implementation is dependant on each new class 
 * of item are defined here, but implemented in the specific class.
 */
public interface Item 
{
    // Methods whose implementation is included in ItemStd
    public String nameToString();
    public String fullnameToString();
    public String lookAtItem();
    public boolean isValid();

    // Methods whose implementation depends on the child class	
    public boolean isContainer();
    public void saveItem(PrintWriter buffWriter);
	
} // End Item
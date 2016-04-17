package mudClient;

import java.awt.*;
import java.awt.event.*;
//import java.awt.Color;
import java.awt.Font;
import java.net.*;
import java.io.*;

import javax.swing.*;

public class gameClient extends JPanel implements ActionListener {
    
    // GUI elements
    protected JTextField textField;
    protected JTextArea textArea;
    protected Timer GUItimer;
    private final static String newline = "\n";
    
    // Needed to make warning disappear...
    private final static long serialVersionUID = 42;
    
    // Network elements
    protected Socket mySocket;
    protected PrintWriter networkOut;
    protected BufferedReader networkIn;
    
    /**
     * Default constructor
     *
     */
    public gameClient() {
        super(new GridBagLayout());

        // Create GUI components...

        textArea = new JTextArea(20, 50);
        
        Font font = new Font("Courier", Font.PLAIN, 12);
        textArea.setFont(font);
        
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(textArea,
                                       JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                                       JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        textField = new JTextField(50);
        textField.addActionListener(this);
        
        //Add Components to this panel.
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;

        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        add(scrollPane, c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        add(textField, c);

        // Create Sockets and IO Streams
        try{
        	mySocket = new Socket("localhost", 4000);	
        	networkOut = new PrintWriter(mySocket.getOutputStream(), true);
        	networkIn = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
        }catch(IOException e)
        {
        	textArea.append("Connection error: " + e);
        }
 
        // GUI update timer, 100 = 1/10 second...
        GUItimer = new Timer (100, new ActionListener() 
        	{
        		public void actionPerformed(ActionEvent evt) 
        		{
        		// Check server for information
        			try{
        				if(networkIn.ready())
        				{
        					String fromServer = new String();
        					
        					while (networkIn.ready())
        					{
        						fromServer = fromServer + ((char)networkIn.read());
        					}
        					
        					// Display in text area
        					textArea.append(fromServer + newline);        					
        					
        				} // end if ready
        			}catch(IOException e)
        	        {
        	        	textArea.append("Connection error: " + e);
        	        }
        		}
        	}); // end of GUItimer declaration
        
        GUItimer.start();
        
    } //end constructor

    public void actionPerformed(ActionEvent evt) {
        String text = textField.getText();
        
        // Send info to server
        textArea.append(text + newline);
        networkOut.write(text);
        networkOut.flush();
        
        textField.selectAll();

        //Make sure the new text is visible, even if there
        //was a selection in the text area.
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);

        //Create and set up the window.
        JFrame frame = new JFrame("SITmud Client");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = new gameClient();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });   

    } // end main
} // end TextDemo

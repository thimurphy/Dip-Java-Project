//  RestaurantBillCalculator.java
// Calculates a table's bill.

/* 
 NAME: THIAGO LEAO MURPHY
 EMAIL: thi.murphy@hotmail.com
 */
//**** TODO ******  PROVIDE IMPORT STATEMENT Modify the import Statements 
//so that only classes used are actually imported
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;

public class RestaurantBillCalculator extends JFrame {

    // JLabel for Restaurant
    private JLabel restaurantJLabel;

    // JPanel to display waiter information
    private JPanel waiterJPanel;

    // JLabel and JTextField for table number
    private JLabel tableNumberJLabel;
    private JTextField tableNumberJTextField;

    // JLabel and JTextField for waiter name
    private JLabel waiterNameJLabel;
    private JTextField waiterNameJTextField;

    // JPanel to display menu items
    private JPanel menuItemsJPanel;

    // JLabel and JComboBox for beverage
    private JLabel beverageJLabel;
    private JComboBox beverageJComboBox;

    // JLabel and JComboBox for appetizer
    private JLabel appetizerJLabel;
    private JComboBox appetizerJComboBox;

    // JLabel and JComboBox for main course
    private JLabel mainCourseJLabel;
    private JComboBox mainCourseJComboBox;

    // JLabel and JComboBox for dessert
    private JLabel dessertJLabel;
    private JComboBox dessertJComboBox;

    // JButton for calculate bill
    private JButton calculateBillJButton;

    // JLabel and JTextField for subtotal
    private JLabel subtotalJLabel;
    private JTextField subtotalJTextField;

    // JLabel and JTextField for tax
    private JLabel taxJLabel;
    private JTextField taxJTextField;

    // JLabel and JTextField for total
    private JLabel totalJLabel;
    private JTextField totalJTextField;

    // constant for tax rate
    private final static double TAX_RATE = 0.05;

    // declare instance variables for database processing
    private Connection myConnection;
    private Statement myStatement;
    private ResultSet myResultSet;

    // Variable to store the username and password of the DB
    private static String databaseUserName;
    private static String databasePassword;

    // declare instance variable ArrayList to hold bill items
    private ArrayList billItems = new ArrayList();

    // database URL                              
    static final String DATABASE_URL = "jdbc:mysql://localhost:3306/restaurant";

    // constructor
    public RestaurantBillCalculator(
            String databaseUserName, String databasePassword) {

        // TODO: code to connect to the database
      /* Adding a database connection and creating a Statement object */
        try {
            //establish connection with the database
            myConnection = DriverManager.getConnection(DATABASE_URL,
                    databaseUserName, databasePassword);

            //Create Statement for database
            myStatement = myConnection.createStatement(
                    myResultSet.TYPE_SCROLL_INSENSITIVE,
                    myResultSet.CONCUR_READ_ONLY);

            //query database  
            myResultSet = myStatement.executeQuery("SELECT * FROM menu;");

        } catch (SQLException sqlException) {
            JOptionPane.showMessageDialog(null, sqlException.getMessage(),
                    "Database Error! Why?! ", JOptionPane.ERROR_MESSAGE);

        }//end of try/catch statement

        createUserInterface(); // set up GUI  

    } // end constructor

    // create and position GUI components; register event handlers
    private void createUserInterface() {

        // get content pane for attaching GUI components
        Container contentPane = getContentPane();

        // enable explicit positioning of GUI components 
        contentPane.setLayout(null);

        // **** TODO ****** set up restaurantJLabel
      /* The piece of code below is setting up the big Restaurant word in bold 
         at the top of the frame. The size is based on ( x, y, weight, height ).
         On setFont method, I'm setting the font, bold and the size of the font.
         At the end, I'm adding it to the frame using contentPane. */
        restaurantJLabel = new JLabel("Restaurant");
        restaurantJLabel.setBounds(80, -8, 232, 90);
        restaurantJLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        contentPane.add(restaurantJLabel);

        // **** TODO ****** set up waiterJPanel
      /* Calling the method createWaiterJPanel. After, adding all the content 
         from the method to the contentPane. It will show on the frame. The same 
         I did with the create MenuItemsJPanel. */
        createWaiterJPanel();
        contentPane.add(waiterJPanel);

        // **** TODO ****** set up menuItemsJPanel
        createMenuItemsJPanel();
        contentPane.add(menuItemsJPanel);

        // **** TODO ****** set up subtotalJLabel
      /* Setting up the label "Subtotal". After that, positioning and sizing it
         as I did on restaurantJLabel. At the end, I added it to the frame using 
         contentPane. */
        subtotalJLabel = new JLabel("Subtotal: ");
        subtotalJLabel.setBounds(25, 380, 80, 16);
        contentPane.add(subtotalJLabel);

        // **** TODO ****** set up subtotalJTextField
      /* Repeating process... However, I set the text field not editable. And 
         added border to change the appearance. Finally, added to the frame. 
        The same process was repeated to all variables below */
        subtotalJTextField = new JTextField();
        subtotalJTextField.setBounds(95, 380, 90, 20);
        subtotalJTextField.setEditable(false);
        subtotalJTextField.setBorder(BorderFactory.createLoweredBevelBorder());
        subtotalJTextField.setHorizontalAlignment(JTextField.RIGHT);
        contentPane.add(subtotalJTextField);

        // **** TODO ****** set up taxJLabel
        taxJLabel = new JLabel("Tax: ");
        taxJLabel.setBounds(25, 410, 56, 16);
        contentPane.add(taxJLabel);

        // **** TODO ****** set up taxJTextField
        taxJTextField = new JTextField();
        taxJTextField.setBounds(95, 410, 90, 20);
        taxJTextField.setEditable(false);
        taxJTextField.setBorder(BorderFactory.createLoweredBevelBorder());
        taxJTextField.setHorizontalAlignment(JTextField.RIGHT);
        contentPane.add(taxJTextField);

        // **** TODO ****** set up totalJLabel
        totalJLabel = new JLabel("Total: ");
        totalJLabel.setBounds(25, 440, 56, 16);
        contentPane.add(totalJLabel);

        // **** TODO ****** set up totalJTextField
        totalJTextField = new JTextField();
        totalJTextField.setBounds(95, 440, 90, 20);
        totalJTextField.setEditable(false);
        totalJTextField.setBorder(BorderFactory.createLoweredBevelBorder());
        totalJTextField.setHorizontalAlignment(JTextField.RIGHT);
        contentPane.add(totalJTextField);

        // create calculateBillJButton
        calculateBillJButton = new JButton("Calculate Bill");
        calculateBillJButton.setBounds(95, 334, 110, 30);
        contentPane.add(calculateBillJButton);

        calculateBillJButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent event) {
                        if (event.getSource() == calculateBillJButton) {
                            calculateBillJButtonActionPerformed(event);
                        }
                    }

                } // end anonymous inner class

        ); // end addActionListener

        // **** TODO ****** set properties of application's window
      /* I added the title at the top bar of the frame as "Restaurat Bill 
         Calculator". Set frame visible and sized). Methods enhanced from JFrame. */
        setTitle("Restaurant Bill Calculator");
        setVisible(true);
        setSize(300, 540);

        // **** TODO ****** ensure database connection is closed
        try {
            myConnection.close();
            myStatement.close();
            myResultSet.close();

        } catch (SQLException sqlException) {
            JOptionPane.showMessageDialog(null, sqlException.getMessage(),
                    "Error Closing Database ! ", JOptionPane.ERROR_MESSAGE);

        }//end of try/catch statement

        //**** TODO ****** when user quits application
        addWindowListener(
                new WindowAdapter() //anonymous inner class
                {
                    //event handler called when close button is clicked
                    @Override
                    public void windowClosing(WindowEvent event) {
                        frameWindowClosing(event);
                    }

                } //  end anonymous inner class

        ); // end addWindowListener

    } // end method createUserInterface

    // **** TODO ****** set up waiterJPanel
    public void createWaiterJPanel() {
        // **** TODO ****** set up waiterJPanel
      /* Setting waiterJPanel box/border. After declared, I set the size 
         following by BorderFactory method to create the box/border and labeled as
         "Waiter Information". To set size and place on the frame I removed the
         default layout manager calling the method setLayout and passing null to
         it. */
        waiterJPanel = new JPanel();
        waiterJPanel.setBounds(20, 60, 232, 90);
        waiterJPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), " Waiter Information "));
        waiterJPanel.setLayout(null);

        // **** TODO ****** set up tableNumberJLabel
      /* The same process of createUserInterface method was applied to the 
         variable below. */
        tableNumberJLabel = new JLabel("Table Number: ");
        tableNumberJLabel.setBounds(20, 22, 98, 24);
        waiterJPanel.add(tableNumberJLabel);

        // **** TODO ****** set up tableNumberJTextField
        tableNumberJTextField = new JTextField();
        tableNumberJTextField.setBounds(130, 24, 80, 20);
        tableNumberJTextField.setEditable(true);
        tableNumberJTextField.setBorder(BorderFactory.createLoweredBevelBorder());
        tableNumberJTextField.setHorizontalAlignment(JTextField.LEFT);
        waiterJPanel.add(tableNumberJTextField);

        // **** TODO ****** set up waiterNameJLabel
        waiterNameJLabel = new JLabel("Waiter Name: ");
        waiterNameJLabel.setBounds(20, 52, 88, 24);
        waiterJPanel.add(waiterNameJLabel);

        // **** TODO ****** set up waiterNameJTextField
        waiterNameJTextField = new JTextField();
        waiterNameJTextField.setBounds(130, 54, 80, 20);
        waiterNameJTextField.setEditable(true);
        waiterNameJTextField.setBorder(BorderFactory.createLoweredBevelBorder());
        waiterNameJTextField.setHorizontalAlignment(JTextField.LEFT);
        waiterJPanel.add(waiterNameJTextField);

    } // end method createWaiterJPanel

    // **** TODO ****** create menuItemsJPanel
    public void createMenuItemsJPanel() {
        //  set up menuItemsJPanel
      /* The same knowledge of createUserInterface was applied below. */
        menuItemsJPanel = new JPanel();
        menuItemsJPanel.setBounds(20, 172, 232, 152);
        menuItemsJPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), " Menu Items "));
        menuItemsJPanel.setLayout(null);

        //  set up beverageJLabel
        beverageJLabel = new JLabel("Beverage: ");
        beverageJLabel.setBounds(8, 24, 80, 24);
        menuItemsJPanel.add(beverageJLabel);

        // set up beverageJComboBox
        beverageJComboBox = new JComboBox();
        beverageJComboBox.setBounds(88, 24, 128, 25);
        beverageJComboBox.setEditable(false);
        menuItemsJPanel.add(beverageJComboBox);
        beverageJComboBox.addItemListener(
                new ItemListener() // **** TODO ****** anonymous inner class
                {
                    //**** TODO ****** event handler called when item in beverageJComboBox
                    //**** TODO ****** is selected
                    public void itemStateChanged(ItemEvent event) {
                        //determine selection
                        if (event.getStateChange() == ItemEvent.SELECTED) {
                            beverageJComboBoxItemStateChanged(event);
                        }
                    }

                } // end anonymous inner class

        ); // end addItemListener

        // **** TODO ****** add items to beverageJComboBox
        loadCategory(" 'beverage' ", beverageJComboBox);

        // **** TODO ****** set up appetizerJLabel
        appetizerJLabel = new JLabel("Appetizer: ");
        appetizerJLabel.setBounds(8, 58, 90, 24);
        menuItemsJPanel.add(appetizerJLabel);

        // **** TODO ****** set up appetizerJComboBox
        appetizerJComboBox = new JComboBox();
        appetizerJComboBox.setBounds(88, 57, 128, 25);
        menuItemsJPanel.add(appetizerJComboBox);
        appetizerJComboBox.addItemListener(
                new ItemListener() // **** TODO ****** anonymous inner class
                {
                    //**** TODO ****** event handler called when item in appetizerJComboBox
                    //**** TODO ****** is selected
                    public void itemStateChanged(ItemEvent event) {
                        //determine selection
                        if (event.getStateChange() == ItemEvent.SELECTED) {
                            appetizerJComboBoxItemStateChanged(event);
                        }
                    }
                } // end anonymous inner class
        ); // end addItemListener        

        // **** TODO ****** add items to appetizerJComboBox
        loadCategory(" 'appetizer' ", appetizerJComboBox);

        // **** TODO ****** set up mainCourseJLabel
        mainCourseJLabel = new JLabel("Main Course: ");
        mainCourseJLabel.setBounds(8, 88, 88, 24);
        menuItemsJPanel.add(mainCourseJLabel);

        // **** TODO ****** set up mainCourseJComboBox
        mainCourseJComboBox = new JComboBox();
        mainCourseJComboBox.setBounds(88, 90, 128, 25);
        menuItemsJPanel.add(mainCourseJComboBox);
        mainCourseJComboBox.addItemListener(
                new ItemListener() // **** TODO ****** anonymous inner class
                {
                    //**** TODO ****** event handler called when item in mainCourseJComboBox
                    //**** TODO ****** is selected
                    public void itemStateChanged(ItemEvent event) {
                        //determine selection
                        if (event.getStateChange() == ItemEvent.SELECTED) {
                            mainCourseJComboBoxItemStateChanged(event);
                        }
                    }
                } // end anonymous inner class
        ); // end addItemListener   

        // **** TODO ****** add items to mainCourseJComboBox
        loadCategory(" 'main course' ", mainCourseJComboBox);

        // **** TODO ****** set up dessertJLabel
        dessertJLabel = new JLabel("Dessert: ");
        dessertJLabel.setBounds(8, 120, 80, 24);
        menuItemsJPanel.add(dessertJLabel);

        // **** TODO ****** set up dessertJComboBox
        dessertJComboBox = new JComboBox();
        dessertJComboBox.setBounds(88, 120, 128, 25);
        menuItemsJPanel.add(dessertJComboBox);
        dessertJComboBox.addItemListener(
                new ItemListener() // **** TODO ****** anonymous inner class
                {
                    //**** TODO ****** event handler called when item in dessertJComboBox
                    //**** TODO ****** is selected
                    public void itemStateChanged(ItemEvent event) {
                        //determine selection
                        if (event.getStateChange() == ItemEvent.SELECTED) {
                            dessertJComboBoxItemStateChanged(event);
                        }
                    }
                } // end anonymous inner class
        ); // end addItemListener

        // **** TODO ****** add items to appetizerJComboBox
        loadCategory(" 'dessert' ", dessertJComboBox);

    } // end method createMenuItemsJPanel

    // **** TODO ****** add items to JComboBox
    private void loadCategory(
            String category, JComboBox categoryJComboBox) {
        
        //Query information from DB comparing category and obtain the right name
        try {

            myResultSet = myStatement.executeQuery("SELECT name, category"
                    + " FROM menu WHERE category LIKE " + category);

            while (myResultSet.next()) {
                categoryJComboBox.addItem(myResultSet.getString("name"));

            } // end while

        } catch (SQLException sqlException) {
            JOptionPane.showMessageDialog(null, sqlException.getMessage(),
                    "Error Loading Items! ", JOptionPane.ERROR_MESSAGE);

        }//end of try/catch

    } // end method loadCategory

    // **** TODO ****** user select beverage
    private void beverageJComboBoxItemStateChanged(ItemEvent event) {
        beverageJComboBox.addItemListener(
                new ItemListener() {
                    public void itemStateChanged(ItemEvent event) {
                        if (event.getStateChange() == ItemEvent.SELECTED) {
                            beverageJComboBox.getSelectedIndex();

                            //If the array is empty, add item to it
                            //If is not empty replace the item for the new one
                            if (billItems.isEmpty() == true) {
                                billItems.add(0, beverageJComboBox.getSelectedItem());
                            } else {
                                billItems.set(0, beverageJComboBox.getSelectedItem());
                            }//end inner if statement

                        }//end of if statement
                    }//end of anonymous inner method

                }
        );

    } // end method beverageJComboBoxItemStateChanged

    // **** TODO ****** user select appetizer
    private void appetizerJComboBoxItemStateChanged(ItemEvent event) {
        appetizerJComboBox.addItemListener(
                new ItemListener() {
                    public void itemStateChanged(ItemEvent event) {
                        if (event.getStateChange() == ItemEvent.SELECTED) {
                            appetizerJComboBox.getSelectedIndex();

                            //If the array is empty or less than 1 item, add item to it
                            //If is not empty or more than 1 item 
                            //replace the item for the new one
                            if (billItems.isEmpty() == true || billItems.size() <= 1) {
                                billItems.add(1, appetizerJComboBox.getSelectedItem());
                            } else {
                                billItems.set(1, appetizerJComboBox.getSelectedItem());
                            }//end inner if statement

                        }//end of if statement
                    }//end inner anonymous method

                }
        );

    } // end method appetizerJComboBoxItemStateChanged

    // **** TODO ****** user select main course
    private void mainCourseJComboBoxItemStateChanged(
            ItemEvent event) {
        mainCourseJComboBox.addItemListener(
                new ItemListener() {
                    public void itemStateChanged(ItemEvent event) {
                        if (event.getStateChange() == ItemEvent.SELECTED) {
                            mainCourseJComboBox.getSelectedIndex();

                            //If the array is less than 2 selection, add item to it
                            //else replace the item for the new one
                            if (billItems.size() <= 2) {
                                billItems.add(2, mainCourseJComboBox.getSelectedItem());
                            } else {
                                billItems.set(2, mainCourseJComboBox.getSelectedItem());

                            }//end inner if statement

                        }//end if statement
                    }//end inner anonymous method
                }
        );

    } // end method mainCourseJComboBoxItemStateChanged

    // **** TODO ****** user select dessert
    private void dessertJComboBoxItemStateChanged(ItemEvent event) {
        dessertJComboBox.addItemListener(
                new ItemListener() {
                    public void itemStateChanged(ItemEvent event) {
                        if (event.getStateChange() == ItemEvent.SELECTED) {
                            dessertJComboBox.getSelectedIndex();

                            //If the array is less than 3 selection, add item to it
                            //else replace the item for the new one
                            if (billItems.size() <= 3) {
                                billItems.add(3, dessertJComboBox.getSelectedItem());
                            } else {
                                billItems.set(3, dessertJComboBox.getSelectedItem());

                            }//end inner if statement

                        }//end of if statement
                    }//end of anonymous inner method

                }
        );

    } // end method dessertJComboBoxItemStateChanged

    // **** TODO ****** user click Calculate Bill JButton
    private void calculateBillJButtonActionPerformed(ActionEvent event) {

        //Adding event to the calculateBillJButton
        if (event.getSource() == calculateBillJButton) {
            //Retrieves value entre by the user and store it in a String
            String name = waiterNameJTextField.getText();
            String table = tableNumberJTextField.getText();

            //If table length is equal to 0 means it is empty. In this case, 
            //an informative message box will apear to retreive "waiter name" and 
            //"table number".
            if (table.length() == 0) {
                JOptionPane.showMessageDialog(null,
                        "Enter TABLE NUMBER !", "INFORMATION MISSING",
                        JOptionPane.INFORMATION_MESSAGE);
            }//end of inner if statement
            else if (name.length() == 0) {
                JOptionPane.showMessageDialog(null,
                        "Enter WAITER NAME !", "INFORMATION MISSING",
                        JOptionPane.INFORMATION_MESSAGE);
            }//end of inner else if statement
            else {
                
                //Obtain price from calculateSubtotal and storing at subtotal variable
                double subtotal = calculateSubtotal();

                //Calculate the tax and total. Add subtotal, tax and total values to
                //the JTextField area.
                subtotalJTextField.setText("$" + String.valueOf(subtotal));//invoke calculateSubtotal method
                taxJTextField.setText("$" + String.valueOf((float) (subtotal * TAX_RATE)));
                totalJTextField.setText("$" + String.valueOf(subtotal + subtotal * TAX_RATE));

            }//end of else stetament

        }//end of if statement

    } // end method calculateBillJButtonActionPerformed

    // **** TODO ****** calculate subtotal
    private double calculateSubtotal() {

        // TODO: code to connect to the database
        //Add database connection and create Statement object 
        try {
            //establish connection with the database
            myConnection = DriverManager.getConnection(DATABASE_URL,
                    databaseUserName, databasePassword);

            //Create Statement for database
            myStatement = myConnection.createStatement(
                    myResultSet.TYPE_SCROLL_INSENSITIVE,
                    myResultSet.CONCUR_READ_ONLY);

            //query database  
            myResultSet = myStatement.executeQuery("SELECT * FROM menu;");

        } catch (SQLException sqlException) {
            JOptionPane.showMessageDialog(null, sqlException.getMessage(),
                    "Database Error! Why?! ", JOptionPane.ERROR_MESSAGE);

        }//end of try/catch statement
        
        //Declare variable price to store the value from database.
        double price = 0;
        
        //Get names from database and compare it to the database value to get
        //the right price
        for (int x = 0; x < billItems.size(); x++) {
            
            //Obtain price from DB
            try {

                myResultSet = myStatement.executeQuery("SELECT name, price FROM menu "
                        + "WHERE name LIKE '" + billItems.get(x) + "'");

                while (myResultSet.next()) {
                    //Add value tp price  from each selected item
                    price += myResultSet.getInt("price");
                }//end of while loop

            } catch (SQLException sqlException) {
                JOptionPane.showMessageDialog(null, "ERROR OBTAINING PRICE",
                        "ERROR! ", JOptionPane.ERROR_MESSAGE);

            }//end of try/catch block

        }//end of for loop

        return price; //Return finilised value with all the items selected

    } // end method calculateSubtotal

    // **** TODO ****** user close window
    private void frameWindowClosing(WindowEvent event) {

        //Close DB
        try {
            myStatement.close();
            myConnection.close();

            //Inform user that DB was closed successfully
            JOptionPane.showMessageDialog(null, "DB Closed Successfully", "INFORMATIVE",
                    JOptionPane.INFORMATION_MESSAGE);

            System.exit(0);

        } catch (SQLException sqlException) {
            JOptionPane.showMessageDialog(null, sqlException.getMessage(),
                    "WINDOW CLOSING ERROR", JOptionPane.ERROR_MESSAGE);
        }

    }  // end method frameWindowClosing

    // **** TODO ****** method main
    public static void main(String[] args) {

        //Set array size to obtain password and username from user
        args = new String[2];
        
        //Obtain username and password from user
        args[ 0] = JOptionPane.showInputDialog("Database User Name: ");
        args[ 1] = JOptionPane.showInputDialog("Database Password: ");

        // **** TODO ****** check command-line arguments
        if (args.length == 2) {
            // **** TODO ****** get command-line arguments
            databaseUserName = args[ 0];
            databasePassword = args[ 1];

            // **** TODO ****** create new RestaurantBillCalculator
            RestaurantBillCalculator rb = new RestaurantBillCalculator(
                    databaseUserName, databasePassword);
        } else {
            System.out.println("Usage: java "
                    + "RestaurantBillCalculator databaseUserName databasePassword");
        }

    } // end method main

} // end class RestaurantBillCalculator


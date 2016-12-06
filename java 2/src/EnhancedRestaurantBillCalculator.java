//EnhancedRestaurantBillCalculator.java
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

public class EnhancedRestaurantBillCalculator extends JFrame {

    // JLabel for Restaurant
    private JLabel restaurantJLabel;

    // JPanel to display waiter information
    private JPanel waiterJPanel;

    // JLabel and JComboBox for table number
    private JLabel tableNumberJLabel;
    private JComboBox tableNumberJComboBox;

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

    // JButton for save table
    private JButton saveTableJButton;

    // JButton for pay bill 
    private JButton payBillJButton;

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

    //Variable subtotal to store the subtotal value
    private double subtotal;

    //Variable to store the Table number
    int table;

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
    public EnhancedRestaurantBillCalculator(
            String databaseUserName, String databasePassword) {

        // TODO: code to connect to the database
        openDB(); // method to connect to the DB

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
         After, I'm setting the font, bold and the size of the font. At the end, 
         I'm adding it to the frame using contentPane. */
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
         added border to change the appearance. Finally, added to the frame. The same process
         was repeated to all variables below */
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
        calculateBillJButton.setBounds(189, 397, 110, 30);
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

        // create saveTableJButton
        saveTableJButton = new JButton("Save Table");
        saveTableJButton.setBounds(189, 367, 110, 30);
        contentPane.add(saveTableJButton);

        saveTableJButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent event) {
                        if (event.getSource() == saveTableJButton) {
                            saveTableJButtonActionPerformed(event);
                        }
                    }

                } // end anonymous inner class

        ); // end addActionListener

        // create payBillJButton
        payBillJButton = new JButton("Pay Bill");
        payBillJButton.setBounds(189, 427, 110, 30);
        contentPane.add(payBillJButton);

        payBillJButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent event) {
                        if (event.getSource() == payBillJButton) {
                            payBillJButtonActionPerformed(event);
                        }
                    }

                } // end anonymous inner class

        ); // end addActionListener

        // **** TODO ****** set properties of application's window
      /* I added the title at the top bar of the frame as "Restaurat Bill 
         Calculator". Set frame visible and sized). Methods enhanced from JFrame. */
        setTitle(" Enhanced Restaurant Bill Calculator");
        setVisible(true);
        setSize(300, 540);

        // **** TODO ****** ensure database connection is closed
        closeDB(); //Invoke method to close DB

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
        tableNumberJComboBox = new JComboBox();
        tableNumberJComboBox.setBounds(145, 26, 70, 20);
        tableNumberJComboBox.setEditable(false);
        waiterJPanel.add(tableNumberJComboBox);
        tableNumberJComboBox.addItemListener(
                new ItemListener() // **** TODO ****** anonymous inner class
                {
                    //**** TODO ****** event handler called when item in tableNumberBox
                    //**** TODO ****** is selected
                    public void itemStateChanged(ItemEvent event) {
                        //determine selection
                        if (event.getStateChange() == ItemEvent.SELECTED) {
                            tableNumberJComboBoxItemStateChanged(event);
                        }
                    }

                } // end anonymous inner class

        ); // end addItemListener

        loadTableNumbers();

        // **** TODO ****** set up waiterNameJLabel
        waiterNameJLabel = new JLabel("Waiter Name: ");
        waiterNameJLabel.setBounds(20, 52, 88, 24);
        waiterJPanel.add(waiterNameJLabel);

        // **** TODO ****** set up waiterNameJTextField
        waiterNameJTextField = new JTextField();
        waiterNameJTextField.setBounds(130, 54, 80, 20);
        waiterNameJTextField.setEditable(false);
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

    //Retrieve tableNumber column from DB. And, adding to JComboBox
    private void loadTableNumbers() {

        openDB();

        try {

            myResultSet = myStatement.executeQuery("SELECT tableNumber FROM restaurantTables");

            while (myResultSet.next()) {
                tableNumberJComboBox.addItem(myResultSet.getInt("tableNumber"));
            } // end while loop

        } catch (SQLException sqlException) {
            JOptionPane.showMessageDialog(null, sqlException.getMessage(),
                    "Error Getting Table Number! ", JOptionPane.ERROR_MESSAGE);

        }//end of try/catch

    } //  end loadTableNumbers method

    // **** TODO ****** add items to JComboBox
    private void loadCategory(
            String category, JComboBox categoryJComboBox) {
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

    // **** TODO ****** user select table number
    private void tableNumberJComboBoxItemStateChanged(ItemEvent event) {

        tableNumberJComboBox.addItemListener(
                new ItemListener() {
                    public void itemStateChanged(ItemEvent event) {

                        openDB(); // Connecting DB
                        table = tableNumberJComboBox.getSelectedIndex() + 1;

                        if (event.getStateChange() == ItemEvent.SELECTED) {
                            tableNumberJComboBox.getSelectedIndex();

                            try {

                                myResultSet = myStatement.executeQuery("SELECT * FROM restaurantTables");

                                while (myResultSet.relative(table)) {
                                    waiterNameJTextField.setText(myResultSet.getString("waitername"));

                                }

                            } catch (SQLException sqlException) {
                                JOptionPane.showMessageDialog(null, sqlException, "Error Table Item State!",
                                        JOptionPane.ERROR_MESSAGE);
                            }

                            displayTotal();

                            //Enable menuItemsJPanel
                            beverageJComboBox.setEnabled(true);
                            appetizerJComboBox.setEnabled(true);
                            mainCourseJComboBox.setEnabled(true);
                            dessertJComboBox.setEnabled(true);

                            //Disable waiterJPanel
                            tableNumberJComboBox.setEnabled(false);

                            //Enable JButtons
                            saveTableJButton.setEnabled(true);
                            calculateBillJButton.setEnabled(true);
                            payBillJButton.setEnabled(true);

                        }//end of if statement

                    }//end of anonymous inner method

                }
        );

    } // end method tableNumberJComboBoxItemStateChanged

    // **** TODO ****** user select beverage
    private void beverageJComboBoxItemStateChanged(ItemEvent event) {
        beverageJComboBox.addItemListener(
                new ItemListener() {
                    public void itemStateChanged(ItemEvent event) {
                        if (event.getStateChange() == ItemEvent.SELECTED) {
                            billItems.add(beverageJComboBox.getSelectedItem());

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
                            billItems.add(appetizerJComboBox.getSelectedItem());
                        }//end of if statement
                    }

                }
        );

    } // end method appetizerJComboBoxItemStateChanged

    // **** TODO ****** user select main course
    private void mainCourseJComboBoxItemStateChanged(ItemEvent event) {
        mainCourseJComboBox.addItemListener(
                new ItemListener() {
                    public void itemStateChanged(ItemEvent event) {
                        if (event.getStateChange() == ItemEvent.SELECTED) {
                            billItems.add(mainCourseJComboBox.getSelectedItem());
                        }
                    }

                }
        );

    } // end method mainCourseJComboBoxItemStateChanged

    // **** TODO ****** user select dessert
    private void dessertJComboBoxItemStateChanged(ItemEvent event) {
        dessertJComboBox.addItemListener(
                new ItemListener() {
                    public void itemStateChanged(ItemEvent event) {
                        if (event.getStateChange() == ItemEvent.SELECTED) {
                            billItems.add(dessertJComboBox.getSelectedItem());
                        }//end of if statement
                    }//end of anonymous inner method

                }
        );

    } // end method dessertJComboBoxItemStateChanged

    // Action performed method to saveTable
    private void saveTableJButtonActionPerformed(ActionEvent event) {
        if (event.getSource() == saveTableJButton) {

            updateTable();
            resetJFrame();

        }

    } // end saveTableJButtonActionPerformed method

    //Create payBillJButtonActionPerformed
    private void payBillJButtonActionPerformed(ActionEvent event) {

        if (event.getSource() == payBillJButton) {
            subtotalJTextField.setText("$0.0");
            taxJTextField.setText("$0.0");
            totalJTextField.setText("$0.0");

            subtotal = 0.0;

            updateTable();
            resetJFrame();

        }

    }//end payBillJButtonActionPerformed method

    //Create updateTable method
    private void updateTable() {

        table = tableNumberJComboBox.getSelectedIndex() + 1;

        try {

            myResultSet = myStatement.executeQuery("SELECT * FROM restaurantTables");

            while (myResultSet.relative(table)) {
                myResultSet.updateDouble("subTotal", subtotal);
                myResultSet.updateRow();
                break;
            }

            JOptionPane.showMessageDialog(null, "Database Updated", "INFORMATIVE",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException sqlException) {
            JOptionPane.showMessageDialog(null, sqlException, "Error Updating DB",
                    JOptionPane.ERROR_MESSAGE);
        }//end try/catch

    }//end payTable method

    // **** TODO ****** user click Calculate Bill JButton
    private void calculateBillJButtonActionPerformed(ActionEvent event) {

        //Adding event to the calculateBillJButton
        if (event.getSource() == calculateBillJButton) {
            //Retrieves value entre by the user and store it in a String
            String name = waiterNameJTextField.getText();
            table = tableNumberJComboBox.getSelectedIndex() + 1;

            //If table length is equal to 0 means it is empty. In this case, 
            //an informative message box will apear to retreive "waiter name" and 
            //"table number".
            if (table == 0) {
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

                double total = calculateSubtotal();

                subtotalJTextField.setText("$" + String.valueOf(total));//invoke calculateSubtotal method
                subtotal = total;
                taxJTextField.setText("$" + String.valueOf((float) (total * TAX_RATE)));
                totalJTextField.setText("$" + String.valueOf(total + total * TAX_RATE));

            }//end of else stetament

        }//end of if statement

    } // end method calculateBillJButtonActionPerformed

    // **** TODO ****** calculate subtotal
    private double calculateSubtotal() {

        openDB();//invoke openDB to create a link with the database

        double price = 0;

        for (int x = 0; x < billItems.size(); x++) {

            try {

                myResultSet = myStatement.executeQuery("SELECT name, price FROM menu "
                        + "WHERE name LIKE '" + billItems.get(x) + "'");

                while (myResultSet.next()) {
                    price += myResultSet.getInt("price");
                }//end of while loop

            } catch (SQLException sqlException) {
                JOptionPane.showMessageDialog(null, "ERROR OBTAINING PRICE",
                        "ERROR! ", JOptionPane.ERROR_MESSAGE);

            }//end of try/catch block

        }//end of for loop

        return price;

    } // end method calculateSubtotal

    //Retrieve DB subTotal
    private void displayTotal() {

        double total = 0;
        table = tableNumberJComboBox.getSelectedIndex() + 1;

        try {
            myResultSet = myStatement.executeQuery("SELECT * FROM restaurantTables");

            while (myResultSet.relative(table)) {
                total = myResultSet.getDouble("subTotal");
            }//end while loop

        } catch (SQLException sqlException) {
            JOptionPane.showMessageDialog(null, sqlException, "Error Retriving Subtotal from DB",
                    JOptionPane.ERROR_MESSAGE);
        }//end try/catch

        subtotalJTextField.setText("$" + String.valueOf(total));//invoke calculateSubtotal method
        taxJTextField.setText("$" + String.valueOf((float) (total * TAX_RATE)));
        totalJTextField.setText("$" + String.valueOf(total + total * TAX_RATE));

    }// end displayTotal method

    //Create resetJFrame
    private void resetJFrame() {

        //Reset instance variable billItems
        billItems.removeAll(billItems);

        //Reset and disable menuItemsJPanel
        //Beverage
        beverageJComboBox.setSelectedIndex(-1);
        beverageJComboBox.setEnabled(false);

        //Appetizer
        appetizerJComboBox.setSelectedIndex(-1);
        appetizerJComboBox.setEnabled(false);

        //Main course
        mainCourseJComboBox.setSelectedIndex(-1);
        mainCourseJComboBox.setEnabled(false);

        //Dessert
        dessertJComboBox.setSelectedIndex(-1);
        dessertJComboBox.setEnabled(false);

        //Reset and Enable waiterJPanel
        //table number
        tableNumberJComboBox.setSelectedIndex(-1);
        tableNumberJComboBox.setEnabled(true);

        //waiter name
        waiterNameJTextField.setText(" ");
        waiterNameJTextField.setEnabled(true);

        //Clear JTextFields
        subtotalJTextField.setText(" ");
        taxJTextField.setText(" ");
        totalJTextField.setText(" ");

        //Disable JButtons
        calculateBillJButton.setEnabled(false);
        saveTableJButton.setEnabled(false);
        payBillJButton.setEnabled(false);

    } //end resetFrame method

    private void openDB() {

        /* Adding a database connection and creating a Statement object */
        try {
            //establish connection with the database
            myConnection = DriverManager.getConnection(DATABASE_URL,
                    databaseUserName, databasePassword);

            //Create Statement for database
            myStatement = myConnection.createStatement(
                    myResultSet.TYPE_SCROLL_SENSITIVE,
                    myResultSet.CONCUR_UPDATABLE);

            //query database  
            myResultSet = myStatement.executeQuery("SELECT * FROM menu, restaurantTables;");

        } catch (SQLException sqlException) {
            JOptionPane.showMessageDialog(null, sqlException.getMessage(),
                    "Error Connecting Database! ", JOptionPane.ERROR_MESSAGE);

        }//end of try/catch statement
    }// end openDB method

    //Close DB method
    private void closeDB() {

        try {
            myConnection.close();
            myStatement.close();
            myResultSet.close();

        } catch (SQLException sqlException) {
            JOptionPane.showMessageDialog(null, sqlException, "Error Closing DB!",
                    JOptionPane.ERROR_MESSAGE);
        }

    }// end closeDB

    // **** TODO ****** user close window
    private void frameWindowClosing(WindowEvent event) {

        try {
            myConnection.close();
            myStatement.close();

            System.exit(0);

        } catch (SQLException sqlException) {
            JOptionPane.showMessageDialog(null, sqlException, "Error Closing DB!",
                    JOptionPane.ERROR_MESSAGE);

        }//end try/catch

    }  // end method frameWindowClosing

    // **** TODO ****** method main
    public static void main(String[] args) {

        //Setting size of the array args
        args = new String[2];

        //Ask user for database user name and password
        args[ 0] = JOptionPane.showInputDialog("Database User Name: ");
        args[ 1] = JOptionPane.showInputDialog("Database Password: ");

        // **** TODO ****** check command-line arguments
        if (args.length == 2) {
            // **** TODO ****** get command-line arguments
            databaseUserName = args[ 0]; //Adding args value of position 0 to database user name
            databasePassword = args[ 1]; //Adding args value of position 1 to database password

            // **** TODO ****** create new RestaurantBillCalculator
            EnhancedRestaurantBillCalculator erb = new EnhancedRestaurantBillCalculator(
                    databaseUserName, databasePassword);
        } else {
            System.out.println("Usage: java "
                    + "EnhancedRestaurantBillCalculator databaseUserName databasePassword");
        }

    } // end method main

} // end class EnhancedRestaurantBillCalculator


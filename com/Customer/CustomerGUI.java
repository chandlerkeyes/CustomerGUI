package com.Customer;

import com.Customer.CustomerDatabase;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.io.*;
import java.text.*;

/***********************************************************************
 * GUI front end for lab10 - A customer database
 *
 * @author Ana Posada
 * @version October 2016
 **********************************************************************/
public class CustomerGUI extends JFrame  implements ActionListener{

    /** results box */
    private JTextArea resultsArea;

    /** object of the CustomerDatabase class */
    private CustomerDatabase db;

    /** JButtons  */
    private JButton findCustByName;
    private JButton findCustomersEmailDomain;

    /** JTextFields */
    private JTextField firstName;
    private JTextField lastName;
    private JTextField email;

    /** menu items */
    private JMenuBar menus;
    private JMenu fileMenu;
    private JMenu reportsMenu;
    private JMenuItem quitItem;
    private JMenuItem openItem;
    private JMenuItem countItem;
    private JMenuItem allItem;

    /*********************************************************************
     Main Method
     *********************************************************************/
    public static void main(String arg[]){
        CustomerGUI gui = new CustomerGUI();
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setTitle("Customer Database");
        gui.pack();
        gui.setVisible(true);

    }

    /*********************************************************************
     Constructor - instantiates and displays all of the GUI commponents
     *********************************************************************/
    public CustomerGUI(){
        db = new CustomerDatabase();

        // create the Gridbag layout
        setLayout(new GridBagLayout());
        GridBagConstraints position = new GridBagConstraints();

        // create the Results Text Area (5 x 10 cells)
        resultsArea = new JTextArea(20,40);
        JScrollPane scrollPane = new JScrollPane(resultsArea);
        position.gridx = 0;
        position.gridy = 0;
        position.gridheight = 10;
        position.gridwidth = 5;
        position.insets =  new Insets(20,20,0,0);
        add(scrollPane, position);

        // Create label and textfield for Month

        //first name
        position.insets =  new Insets(0,20,0,0);
        position.gridx = 0;
        position.gridy = 10;
        position.gridheight = 1;
        position.gridwidth = 1;
        position.anchor = GridBagConstraints.LINE_START;
        add(new JLabel("First Name"), position);
        position.gridx = 0;
        position.gridy = 11;
        firstName = new JTextField(15);
        add(firstName, position);

        //Last name
        position.gridx = 1;
        position.gridy = 10;
        add(new JLabel("Last Name"), position);
        position.gridx = 1;
        position.gridy = 11;
        lastName = new JTextField(15);
        add(lastName, position);

        // email domain
        position.gridx = 2;
        position.gridy = 10;
        add(new JLabel("Email Domain"), position);
        position.gridx = 2;
        position.gridy = 11;
        email = new JTextField(10);
        add(email, position);

        // Add buttons and labels on right side
        // selections
        position.insets =  new Insets(30,5,5,5);
        position.gridx = 6;
        position.gridy = 0;
        add(new JLabel("Selections"), position);

        position.gridx = 6;
        position.gridy = 1;
        position.insets =  new Insets(0,5,5,5);
        position.anchor = GridBagConstraints.LINE_START;
        findCustByName = new JButton("Search names");
        add(findCustByName, position);

        position.gridx = 6;
        position.gridy = 2;
        findCustomersEmailDomain = new JButton("Search Email domain");
        add(findCustomersEmailDomain, position);

        // setting actions listeners
        findCustByName.addActionListener(this);
        findCustomersEmailDomain.addActionListener(this);

        // set up File menus
        setupMenus();
        pack();

    }

    /*********************************************************************
     * List all entries given an ArrayList of customers.  Include a final
     * line with the number of customers listed
     *
     *  @param ArrayList <Customer>  list of customers
     *********************************************************************/
    private void displayCustomers(ArrayList <Customer> list){
        resultsArea.setText("");
        for(Customer c : list){
            resultsArea.append("\n" + c.toString());
        }
        resultsArea.append ("\nNumber of Customers: " + list.size());
    }

    /*********************************************************************
     * Respond to menu selections and button clicks
     *
     *  @param e the button or menu item that was selected
     *********************************************************************/
    public void actionPerformed(ActionEvent e){
        // either open a file or warn the user
        if (e.getSource() == openItem){
            openFile();
        }else if(db.getNumberCustomers() == 0){
            String errorMessage = "Did you forget to open a file?";
            resultsArea.setText(errorMessage);
            return;
        }

        // menu item - quit
        else if (e.getSource() == quitItem){
            System.exit(1);
        }

        // Count menu item - display number of customers
        else if (e.getSource() == countItem){
            resultsArea.setText("\nNumber of Customers: " + db.getNumberCustomers ( ));
        }

        // all menu item - display ALL customers
        else if (e.getSource() == allItem){
            displayCustomers (db.getDB());
        }

        // findCustByName
        else if (e.getSource() == findCustByName){
            if (firstName.getText().length() > 0 && lastName.getText().length() > 0)
                resultsArea.setText("\nSearch by names " +
                        db.findCustomer(firstName.getText(), lastName.getText()));
            else
                JOptionPane.showMessageDialog(null, "Enter first name and last name to be able to search");
        }

        // findCustomersEmailDomain
        else if (e.getSource() == findCustomersEmailDomain){
            if (email.getText().length() > 0 )
                displayCustomers(db.findCustomersWithSameEmailDomain(email.getText()));
            else
                JOptionPane.showMessageDialog(null, "Enter email domain to be able to search");
        }

    }

    /*********************************************************************
     In response to the menu selection - open a data file
     *********************************************************************/
    private void openFile(){
        JFileChooser fc = new JFileChooser(new File(System.getProperty("user.dir")));
        int returnVal = fc.showOpenDialog(this);

        // did the user select a file?
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String filename = fc.getSelectedFile().getName();
            System.out.println(filename);
            db.readCustomerData(filename);
        }
    }

    /*********************************************************************
     Set up the menu items
     *********************************************************************/
    private void setupMenus(){

        // create menu components
        fileMenu = new JMenu("File");
        quitItem = new JMenuItem("Quit");
        openItem = new JMenuItem("Open...");
        reportsMenu = new JMenu("Reports");
        countItem = new JMenuItem("Counts");
        allItem = new JMenuItem("All Customers");

        // assign action listeners
        quitItem.addActionListener(this);
        openItem.addActionListener(this);
        countItem.addActionListener(this);
        allItem.addActionListener(this);

        // display menu components
        fileMenu.add(openItem);
        fileMenu.add(quitItem);
        reportsMenu.add(countItem);
        reportsMenu.add(allItem);
        menus = new JMenuBar();

        menus.add(fileMenu);
        menus.add(reportsMenu);
        setJMenuBar(menus);
    }
}
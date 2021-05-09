package com.Customer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CustomerDatabase {
    // FIX ME: define your private ArrayList of customers here.
    private ArrayList<Customer> customerList;

    public CustomerDatabase() {
        customerList = new ArrayList<Customer>();
    }

    /**
     * Find a particular customer based on the first and last name
     * @param firstName search for
     * @param lastName to search
     * @return
     */
    public Customer findCustomer(String firstName, String lastName) {
        // FIX ME:  iterate through your ArrayList of customers and return the
        // one Customer whose firstName and lastName match the input parameters, or
        // return a null value if it is not found.
        for (Customer x: customerList) {
            if (x.getLastName().equalsIgnoreCase(lastName) && x.getFirstName().equalsIgnoreCase(firstName)) {
                return x;
            }
        }
        return null;
    }

    /**
     * Find a customer based on the email domain
     * @param domain to search for
     * @return
     */
    public ArrayList<Customer> findCustomersWithSameEmailDomain(String domain) {
        // Replaced this with a stream of the collection to make the code cleaner
        ArrayList<Customer> list = (ArrayList<Customer>) customerList.stream().filter(s -> s.getEmail().contains(domain)).collect(Collectors.toList());
        return list;
    }

    /**
     * Gets all the customers in the customers list
     * @return
     */
    public ArrayList<Customer> getDB() {
        return customerList;
    }

    /**
     * Gets the total count of customer in the database
     * @return
     */
    public int getNumberCustomers () {
        // FIX ME:  return the number of customers in the
        // ArrayList - ONE line of code
        return customerList.size();
    }

    /**
     * Reads the customer data file and add its to the arraylist for later use
     * @param filename to look at to retrieve information
     */
    public void readCustomerData(String filename)  {

        // Read the full set of data from a text file
        try {

            // open the text file and use a Scanner to read the text
            // Changed this into one line rather than 2
            Scanner scnr = new Scanner(new File(filename));
            scnr.useDelimiter("[,\r\n]+");

            // keep reading as long as there is more data
            while(scnr.hasNext()) {
                // FIX ME: read the firstName, lastName and email
                String firstName = scnr.next();
                String lastName = scnr.next();
                String email = scnr.next();
                // discarding the data found in the file after the email - IT is not needed
                scnr.nextLine();

                // FIX ME: instantiate a Customer object and add it to the ArrayList
                // You could do this with one or two lines of code.
                Customer c = new Customer(firstName, lastName, email);
                customerList.add(c);

            }
            scnr.close();
        }
        catch(FileNotFoundException e) {
            System.out.println("Failed to read the data file: " + filename);
        }
    }

    public static void main(String[] args) {
        CustomerDatabase customers = new CustomerDatabase();
        customers.readCustomerData("CustomerRecords.txt");

        System.out.println("\nSearching for Jack King..." +
                "\n============================");
        Customer jack = customers.findCustomer("Jack", "King");

        if(jack != null) {
            System.out.println("Found record: " + jack);
        } else {
            System.out.println("Could not find Jack King");
        }

        System.out.println("\nSearching for Bill Gates..." +
                "\n============================");
        Customer bill = customers.findCustomer("Bill", "Gates");

        if(bill != null) {
            System.out.println("Found record: " + bill);
        } else {
            System.out.println("Could not find Bill Gates");
        }

        System.out.println("\nFinding all customers who have a google email account" +
                "\n=======================================================");
        ArrayList<Customer> domainCustomers =
                customers.findCustomersWithSameEmailDomain("@deviantart") ;
        System.out.println("Found " + domainCustomers.size() + " records total:");
        // Used a lambda expression again to make cleaner
        domainCustomers.forEach(s -> System.out.println(s));


    }

}
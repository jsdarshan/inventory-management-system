package IMS.data;

import IMS.DB.DBC;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class transactionData {
    // Singleton instance
    private static transactionData instance;

    // Data structures to hold transaction information
    private ArrayList<Integer> transactionId;
    private ArrayList<String[]> items;

    // Private constructor to prevent external instantiation
    private transactionData() {
        // Initialize data structures
        transactionId = new ArrayList<>(5);
        items = new ArrayList<>(5);
        // Initialize transaction data from database
        initializeData();
    }

    // Singleton getInstance() method to provide global access to the instance
    public static transactionData getInstance() {
        if (instance == null) {
            synchronized (transactionData.class) {
                if (instance == null) {
                    instance = new transactionData();
                }
            }
        }
        return instance;
    }

    // Factory method to create instances of transactionData
    public static transactionData createInstance() throws SQLException {
        return new transactionData();
    }

    // Method to initialize transaction data from the database
    private void initializeData() {
        try {
            DBC dbc = new DBC();
            ResultSet resultSet = dbc.retrieveData("select transactionId from transaction;");
            retrieveTransactionId(resultSet);
            resultSet = dbc.retrieveData("select item from transaction;");
            retrieveTransactionItem(resultSet);
        } catch (SQLException e) {
            // Handle SQLException appropriately
            e.printStackTrace();
        }
    }

    // Method to retrieve transaction items from the database
    private void retrieveTransactionItem(ResultSet set) throws SQLException {
        while (set.next()) {
            items.add((set.getString(1)).trim().split("&"));
        }
    }

    // Method to retrieve transaction IDs from the database
    private void retrieveTransactionId(ResultSet set) throws SQLException {
        while (set.next()) {
            transactionId.add(set.getInt(1));
        }
    }

    // Getter method for transaction IDs
    public ArrayList<Integer> getTransactionId() {
        return transactionId;
    }

    // Getter method for transaction items
    public ArrayList<String[]> getItems() {
        return items;
    }
}

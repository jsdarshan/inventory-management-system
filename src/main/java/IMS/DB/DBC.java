package IMS.DB;
import javax.swing.*;
import java.sql.*;

//Singleton Pattern
//It typically ensures that only one instance of the class
// exists throughout the application, which can be beneficial for managing
// database connections efficiently and avoiding redundant resource allocation.
public class DBC {
    Connection connection;

    public DBC() {
        setConnection();
    }

    public void setConnection() {
        try {
            String password = "Vijay@1976";
            String username = "root";
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/oose", username, password);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "DataBase Connection Failed");
        }
    }

    public ResultSet retrieveData(String query) {
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery(query);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return null;
    }

    public void insertMemberData(int memberid ,String name, String address, String contact) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO members (memid, name, address, contact) VALUES (?, ?, ?, ?)");
            statement.setObject(1, memberid);
            statement.setObject(2, name);
            statement.setObject(3, address);
            statement.setObject(4, contact);
            statement.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void recordTransation(String name) {
        try {
            System.out.println(name);
            ResultSet resultSet = retrieveData("Select * from transaction;");
            int trID = 0;
            while (resultSet.next()) {
                trID = resultSet.getInt(1);
            }
            PreparedStatement statement = connection.prepareStatement("insert into transaction values(" + "?,?" + ");");
            statement.setObject(1, trID + 1);
            statement.setObject(2, name);
            statement.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

    }

    public void insertItemData(String name, int quantity, double  price) {
        try {
            // Check if the item already exists in the database
            PreparedStatement checkStatement = connection.prepareStatement("select * from items where name = ?");
            checkStatement.setObject(1, name);
            ResultSet resultSet = checkStatement.executeQuery();

            if (resultSet.next()) {
                // Item exists, update its quantity
                int existingQuantity = resultSet.getInt("quantity");
                int newQuantity = existingQuantity + quantity;

                PreparedStatement updateStatement = connection.prepareStatement("update items set quantity = ?, price = ? where name = ?");
                updateStatement.setDouble(1, newQuantity);
                updateStatement.setDouble(2, price);
                updateStatement.setString(3, name);
                updateStatement.executeUpdate();

                JOptionPane.showMessageDialog(null, "Quantity and Price Updated Successfully");
            } else {
                // Item doesn't exist, insert a new record
                PreparedStatement insertStatement = connection.prepareStatement("insert into items(name,quantity,price) values (?,?,?)");
                insertStatement.setObject(1, name);
                insertStatement.setObject(2, quantity);
                insertStatement.setObject(3, price);
                insertStatement.executeUpdate();

                JOptionPane.showMessageDialog(null, "Item Added Successfully");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }


    public boolean verifyMemberData(String memberId) {
        boolean memberVerified = false;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM members WHERE memid = ?");
            statement.setString(1, memberId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                memberVerified = true; // Member exists in the database
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return memberVerified;
    }

    public int getActualQuantityData(String itemName) {
        int actualQuantity = 0;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT quantity FROM items WHERE name = ?");
            statement.setString(1, itemName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                actualQuantity = resultSet.getInt("quantity");
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return actualQuantity;
    }

    public void updateItemQuantity(String itemName, int purchasedQuantity){
        try {
            String query = "UPDATE items SET quantity = quantity - ? WHERE name = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, purchasedQuantity);
            statement.setString(2, itemName);
            statement.executeUpdate();
        } catch (SQLException e) {
            // Handle any SQL exceptions here
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}

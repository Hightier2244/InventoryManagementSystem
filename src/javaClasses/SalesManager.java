package javaClasses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SalesManager {
    public static void addSale(int productId, int quantity) {
        String query = "INSERT INTO SRaichle_Sales (ProductID, Quantity, SalesDate) VALUES (?, ?, GETDATE())";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, productId);
            ps.setInt(2, quantity);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

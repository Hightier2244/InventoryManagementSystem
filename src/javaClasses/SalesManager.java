package javaClasses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public static List<Sale> getAllSales() throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Sale> sales = new ArrayList<>();

        try {
            conn = DatabaseConnection.getConnection();
            String query = "SELECT * FROM SRaichle_Sales";
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Sale sale = new Sale(rs.getInt("id"), rs.getInt("productId"), rs.getInt("quantity"), rs.getDate("salesDate"));
                sales.add(sale);
            }
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }
        return sales;
    }

    public static void removeSale(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            String query = "DELETE FROM SRaichle_Sales WHERE id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } finally {
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }
    }
}

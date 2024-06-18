package javaClasses;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductManager {
    public static double calculateAveragePrice() throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        double avgPrice = 0.0;

        try {
            conn = DatabaseConnection.getConnection();
            String sql = "SELECT db_owner.SRaichle_CalculateAveragePrice() AS AvgPrice";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            if (rs.next()) {
                avgPrice = rs.getDouble("AvgPrice");
            }
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }

        return avgPrice;
    }

    public static List<Product> getAllProducts() throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Product> productList = new ArrayList<>();

        try {
            conn = DatabaseConnection.getConnection();
            String query = "SELECT * FROM SRaichle_Products";
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("ID");
                String name = rs.getString("Name");
                String description = rs.getString("Description");
                double price = rs.getDouble("Price");
                int quantity = rs.getInt("Quantity");

                Product product = new Product(id, name, description, price, quantity);
                productList.add(product);
            }
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }

        return productList;
    }

    public static void removeProduct(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            String query = "DELETE FROM SRaichle_Products WHERE id = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } finally {
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }
    }
}

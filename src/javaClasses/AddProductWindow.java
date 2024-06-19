package javaClasses;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddProductWindow {
    // function to display a pop-up to add a new Product
    public static void display() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Produkt hinzufügen");
        window.setMinWidth(300);

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        Label nameLabel = new Label("Name:");
        GridPane.setConstraints(nameLabel, 0, 0);
        TextField nameInput = new TextField();
        GridPane.setConstraints(nameInput, 1, 0);

        Label descriptionLabel = new Label("Beschreibung:");
        GridPane.setConstraints(descriptionLabel, 0, 1);
        TextField descriptionInput = new TextField();
        GridPane.setConstraints(descriptionInput, 1, 1);

        Label priceLabel = new Label("Preis:");
        GridPane.setConstraints(priceLabel, 0, 2);
        TextField priceInput = new TextField();
        GridPane.setConstraints(priceInput, 1, 2);

        Label quantityLabel = new Label("Menge:");
        GridPane.setConstraints(quantityLabel, 0, 3);
        TextField quantityInput = new TextField();
        GridPane.setConstraints(quantityInput, 1, 3);

        Button addButton = new Button("Hinzufügen");
        GridPane.setConstraints(addButton, 1, 4);
        addButton.setOnAction(e -> {
            // Hier fügen Sie das Produkt der Datenbank hinzu
            try {
                String name = nameInput.getText();
                String description = descriptionInput.getText();
                double price = Double.parseDouble(priceInput.getText());
                int quantity = Integer.parseInt(quantityInput.getText());

                // Verbindung zur Datenbank
                Connection con = DatabaseConnection.getConnection();
                String query = "EXEC SRaichle_AddProduct ?, ?, ?, ?";
                PreparedStatement stmt = con.prepareStatement(query);
                stmt.setString(1, name);
                stmt.setString(2, description);
                stmt.setDouble(3, price);
                stmt.setInt(4, quantity);
                stmt.executeUpdate();
                stmt.close();
                con.close();

                // Fenster schließen
                window.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        grid.getChildren().addAll(nameLabel, nameInput, descriptionLabel, descriptionInput, priceLabel, priceInput, quantityLabel, quantityInput, addButton);
        Scene scene = new Scene(grid);
        window.setScene(scene);
        window.showAndWait();
    }
}

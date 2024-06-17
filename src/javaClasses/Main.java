package javaClasses;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

public class Main extends Application {

    private TableView<Product> table;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Inventarverwaltungssystem");

        table = new TableView<>();

        TableColumn<Product, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Product, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Product, String> descriptionColumn = new TableColumn<>("Beschreibung");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Product, Double> priceColumn = new TableColumn<>("Preis");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Product, Integer> quantityColumn = new TableColumn<>("Menge");
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        table.getColumns().add(idColumn);
        table.getColumns().add(nameColumn);
        table.getColumns().add(descriptionColumn);
        table.getColumns().add(priceColumn);
        table.getColumns().add(quantityColumn);

        Button addButton = new Button("Produkt hinzufügen");
        addButton.setOnAction(e -> {
            // Öffnen Sie ein neues Fenster zum Hinzufügen von Produkten
            AddProductWindow.display();
            updateTable();
        });

        Button addSaleButton = new Button("Verkauf hinzufügen");
        addSaleButton.setOnAction(e -> {
            addSale();
            updateTable();
        });

        Button updateButton = new Button("Tabelle aktualisieren");
        updateButton.setOnAction(e -> {
            updateTable();
        });

        Button calculateAveragePriceButton = new Button("Durchschnittspreis berechnen");
        calculateAveragePriceButton.setOnAction(e -> {
            double averagePrice = 0;
            try {
                averagePrice = ProductManager.calculateAveragePrice();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Durchschnittspreis");
            alert.setHeaderText(null);
            alert.setContentText("Der Durchschnittspreis der Produkte beträgt: " + averagePrice);
            alert.showAndWait();
        });

        updateTable();

        VBox vbox = new VBox(table, addButton, addSaleButton, updateButton, calculateAveragePriceButton);
        Scene scene = new Scene(vbox);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void updateTable() {
        try {
            List<Product> productList = ProductManager.getAllProducts();
            table.setItems(FXCollections.observableArrayList(productList));
        } catch (SQLException ex) {
            ex.printStackTrace();
            showAlert("Fehler beim Laden der Produkte", "Fehler beim Laden der Produkte aus der Datenbank: " + ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void addSale() {
        // Create a new window or dialog to collect sale details
        Stage window = new Stage();
        window.setTitle("Verkauf hinzufügen");

        VBox layout = new VBox(10);
        layout.setPadding(new javafx.geometry.Insets(10));

        TextField productIdInput = new TextField();
        productIdInput.setPromptText("Produkt ID");

        TextField quantityInput = new TextField();
        quantityInput.setPromptText("Menge");

        Button submitButton = new Button("Verkauf hinzufügen");
        submitButton.setOnAction(e -> {
            int productId = Integer.parseInt(productIdInput.getText());
            int quantity = Integer.parseInt(quantityInput.getText());
            SalesManager.addSale(productId, quantity);
            window.close();
        });

        layout.getChildren().addAll(new Label("Produkt ID"), productIdInput, new Label("Menge"), quantityInput, submitButton);

        Scene scene = new Scene(layout, 300, 200);
        window.setScene(scene);
        window.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

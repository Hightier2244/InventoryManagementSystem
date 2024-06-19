package javaClasses;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class Main extends Application {

    private TableView<Product> productTable;
    private TableView<Sale> salesTable;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Inventarverwaltungssystem");

        productTable = new TableView<>();
        salesTable = new TableView<>();

        // Product Table Columns
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

        // adding the columns to the table
        productTable.getColumns().add(idColumn);
        productTable.getColumns().add(nameColumn);
        productTable.getColumns().add(descriptionColumn);
        productTable.getColumns().add(priceColumn);
        productTable.getColumns().add(quantityColumn);

        // Sales Table Columns
        TableColumn<Sale, Integer> saleIdColumn = new TableColumn<>("Verkauf ID");
        saleIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Sale, Integer> saleProductIdColumn = new TableColumn<>("Produkt ID");
        saleProductIdColumn.setCellValueFactory(new PropertyValueFactory<>("productId"));

        TableColumn<Sale, Integer> saleQuantityColumn = new TableColumn<>("Menge");
        saleQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        TableColumn<Sale, Date> saleDateColumn = new TableColumn<>("Verkaufsdatum");
        saleDateColumn.setCellValueFactory(new PropertyValueFactory<>("salesDate"));

        // adding the columns to the table
        salesTable.getColumns().add(saleIdColumn);
        salesTable.getColumns().add(saleProductIdColumn);
        salesTable.getColumns().add(saleQuantityColumn);
        salesTable.getColumns().add(saleDateColumn);

        // Initializing all the Buttons
        Button addProductButton = new Button("Produkt hinzufügen");
        addProductButton.setOnAction(e -> {
            // Öffnen Sie ein neues Fenster zum Hinzufügen von Produkten
            AddProductWindow.display();
            updateTables();
        });

        Button removeProductButton = new Button("Produkt entfernen");
        removeProductButton.setOnAction(e -> removeProduct());

        Button addSaleButton = new Button("Verkauf hinzufügen");
        addSaleButton.setOnAction(e -> {
            addSale();
        });

        Button removeSaleButton = new Button("Verkauf entfernen");
        removeSaleButton.setOnAction(e -> removeSale());

        Button updateButton = new Button("Tabelle aktualisieren");
        updateButton.setOnAction(e -> {
            updateTables();
        });

        Button calculateAveragePriceButton = new Button("Durchschnittspreis berechnen");
        calculateAveragePriceButton.setOnAction(e -> {
            double averagePrice = 0;
            try {
                averagePrice = ProductManager.calculateAveragePrice();
            } catch (SQLException ex) {
                showAlert("Fehler beim Berechnen des Durchschnittspreises", "Fehler: " + ex.getMessage(), Alert.AlertType.ERROR);
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Durchschnittspreis");
            alert.setHeaderText(null);
            alert.setContentText("Der Durchschnittspreis der Produkte beträgt: " + averagePrice);
            alert.showAndWait();
        });

        // VBox for the left buttons
        VBox productButtons = new VBox(10, addProductButton, removeProductButton, updateButton, calculateAveragePriceButton);
        productButtons.setPadding(new Insets(10));

        // VBox for the right buttons
        VBox salesButtons = new VBox(10, addSaleButton, removeSaleButton);
        salesButtons.setPadding(new Insets(10));

        // VBox to combine tables and buttons
        VBox productVBox = new VBox(10, productTable, productButtons);
        VBox salesVBox = new VBox(10, salesTable, salesButtons);

        // HBox to combine both sides
        HBox mainLayout = new HBox(20, productVBox, salesVBox);
        mainLayout.setPadding(new Insets(10));

        Scene scene = new Scene(mainLayout);

        primaryStage.setScene(scene);
        primaryStage.show();

        // update to populate the tables
        updateTables();
    }

    // update function to populate the tables
    private void updateTables() {
        try {
            List<Product> productList = ProductManager.getAllProducts();
            productTable.setItems(FXCollections.observableArrayList(productList));

            List<Sale> salesList = SalesManager.getAllSales();
            salesTable.setItems(FXCollections.observableArrayList(salesList));
        } catch (SQLException ex) {
            ex.printStackTrace();
            showAlert("Fehler beim Laden der Produkte", "Fehler beim Laden der Produkte aus der Datenbank: " + ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    // function for a pop-up to remove a product
    private void removeProduct() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Produkt entfernen");
        dialog.setHeaderText("Produkt ID eingeben");
        dialog.setContentText("Bitte Produkt ID eingeben:");

        dialog.showAndWait().ifPresent(id -> {
            try {
                ProductManager.removeProduct(Integer.parseInt(id));
                updateTables();
            } catch (SQLException ex) {
                ex.printStackTrace();
                showAlert("Fehler beim Entfernen des Produkts", "Fehler: " + ex.getMessage(), Alert.AlertType.ERROR);
            }
        });
    }

    // function for a pop-up to remove a sale
    private void removeSale() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Verkauf entfernen");
        dialog.setHeaderText("Verkauf ID eingeben");
        dialog.setContentText("Bitte Verkauf ID eingeben:");

        dialog.showAndWait().ifPresent(id -> {
            try {
                SalesManager.removeSale(Integer.parseInt(id));
                updateTables();
            } catch (SQLException ex) {
                ex.printStackTrace();
                showAlert("Fehler beim Entfernen des Verkaufs", "Fehler: " + ex.getMessage(), Alert.AlertType.ERROR);
            }
        });
    }

    // function to show an error alert
    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // function for a pop-up to add a sale
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
            updateTables();
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

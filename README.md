Inventarverwaltungssystem

A JavaFX application for managing inventory, allowing users to add products, add sales, and calculate the average price of products.
Table of Contents

    Prerequisites
    Installation
    Running the Application
    Usage
    Project Structure
    Contributing
    License

Prerequisites

Before you begin, ensure you have the following software installed on your machine:

    Java Development Kit (JDK) - version 8 or higher
    IntelliJ IDEA (or any other Java IDE)
    JavaFX SDK (if not included in your JDK)
    sqljdbc Bibliothek (Connector by Microsoft=
    SQL Server (or any compatible SQL database)

Installation

Follow these steps to download and set up the project:

    Clone the Repository

    Open your terminal and run:

    sh git clone https://github.com/your-username/inventarverwaltungssystem.git

Open the Project in IntelliJ IDEA

    Launch IntelliJ IDEA.
    Click on Open or Import and select the cloned project directory.

Configure JavaFX and the Microsoft SQL Connector

If your JDK does not include JavaFX, download the JavaFX SDK from Gluon.

In IntelliJ IDEA:

    Open File > Project Structure.
    Go to Libraries and add the JavaFX SDK lib directory.
    Go to Modules and aa the sqljdbc directory.

Setup Database

    Ensure your SQL Server is running.
    Create the necessary tables by executing the SQLRunner.
    This will automatically create the tables, function, procedures and triggers provided in the sql directory of the repository.

Configure Database Connection

    Ensure your application can connect to your database by configuring the database connection settings in your project. Update the connection parameters in your DatabaseConnection class as needed.

Running the Application

    Build and Run the Application
        In IntelliJ IDEA, click on the 3 dots in the top right corner to add a run configuration.
        Click on the + and select "application". Then select the javaClasses.Main.
        Then click on "modify options" and select "add vm options" and put this into it: "--module-path "<Path to the JavaFX library>" --add-modules javafx.controls,javafx.fxml"

    Application Interface

    The main interface includes:
        A table displaying products.
        Buttons to add products, add sales, and calculate the average price.

Usage

    Add Product: Opens a window to input and add new products to the inventory.
    Add Sale: Opens a window to input sales details and update the inventory.
    Remove Sale: Opens a window to input a product id and update the inventory.
    Remove Sale: Opens a window to input a sale id and update the inventory.
    Calculate Average Price: Calculates and displays the average price of the products in the inventory.

Project Structure

    sql
        create_tables.sql: sql statement to create the tables
        function.sql: function to calculate the average price of whats in the product table
        stored_procedure.sql: procedure to add a product to the product table
        triggers.sql: trigger to change the product amounts according to the sales
    src/javaClasses
        Main.java: The main application class.
        DatabaseConnection.java: Stores the connection details.
        ProductManager.java: Handles database operations related to products.
        SalesManager.java: Handles database operations related to sales.
        Product.java: Represents a product entity.
        Sales.java: Represents a sale entity.
        AddProductWindow.java: UI for adding a new product.
    src/sql
        SqlRunner.java: Implements a runner to execute the sql files, in order to create the needed database infrastructure

CREATE TABLE SRaichle_Products (
                          ID INT PRIMARY KEY IDENTITY(1,1),
                          Name NVARCHAR(50),
                          Description NVARCHAR(255),
                          Price DECIMAL(10, 2),
                          Quantity INT
);

CREATE TABLE SRaichle_Sales (
                       ID INT PRIMARY KEY IDENTITY(1,1),
                       ProductID INT,
                       Quantity INT,
                       SalesDate DATETIME,
                       FOREIGN KEY (ProductID) REFERENCES SRaichle_Products(ID)
);

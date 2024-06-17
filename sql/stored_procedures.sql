CREATE OR ALTER PROCEDURE SRaichle_AddProduct
    @Name NVARCHAR(50),
    @Description NVARCHAR(255),
    @Price DECIMAL(10, 2),
    @Quantity INT
AS
BEGIN
    INSERT INTO db_owner.SRaichle_Products (Name, Description, Price, Quantity)
    VALUES (@Name, @Description, @Price, @Quantity)
END;

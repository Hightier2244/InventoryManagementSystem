CREATE TRIGGER SRaichle_UpdateStock
    ON SRaichle_Sales
    AFTER INSERT
    AS
BEGIN
    DECLARE @ProductID INT, @Quantity INT;
    SELECT @ProductID = INSERTED.ProductID, @Quantity = INSERTED.Quantity FROM INSERTED;
    UPDATE SRaichle_Products
    SET Quantity = Quantity - @Quantity
    WHERE ID = @ProductID;
END;

CREATE OR ALTER TRIGGER SRaichle_UpdateStock
    ON db_owner.SRaichle_Sales
    AFTER INSERT, DELETE
    AS
BEGIN
    DECLARE @ProductID INT, @Quantity INT;

    -- Handle INSERT operation
    IF EXISTS (SELECT * FROM INSERTED)
        BEGIN
            SELECT @ProductID = INSERTED.ProductID, @Quantity = INSERTED.Quantity FROM INSERTED;
            UPDATE db_owner.SRaichle_Products
            SET Quantity = Quantity - @Quantity
            WHERE ID = @ProductID;
        END

    -- Handle DELETE operation
    IF EXISTS (SELECT * FROM DELETED)
        BEGIN
            SELECT @ProductID = DELETED.ProductID, @Quantity = DELETED.Quantity FROM DELETED;
            UPDATE db_owner.SRaichle_Products
            SET Quantity = Quantity + @Quantity
            WHERE ID = @ProductID;
        END
END;

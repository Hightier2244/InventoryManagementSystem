CREATE OR ALTER FUNCTION SRaichle_CalculateAveragePrice()
    RETURNS DECIMAL(10, 2)
AS
BEGIN
    DECLARE @AvgPrice DECIMAL(10, 2)
    SELECT @AvgPrice = SUM(Price * Quantity) / SUM(Quantity)
    FROM db_owner.SRaichle_Products
    RETURN @AvgPrice
END

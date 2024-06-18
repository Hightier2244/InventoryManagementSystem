package javaClasses;

import java.util.Date;

public class Sale {

    private final int id;
    private final int productId;
    private final int quantity;

    private final Date salesDate;

    public Sale(int id, int productId, int quantity, Date salesDate) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.salesDate = salesDate;
    }

    public int getId() {
        return id;
    }

    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public Date getSalesDate() {
        return salesDate;
    }
}

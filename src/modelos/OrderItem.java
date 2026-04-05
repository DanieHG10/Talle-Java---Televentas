package modelos;

public class OrderItem {
    private String productCode;
    private String productDescription;
    private int quantity;
    private double unitPrice;

    public OrderItem(String productCode, String productDescription, int quantity, double unitPrice) {
        this.productCode = productCode;
        this.productDescription = productDescription;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public double getSubtotal() { return quantity * unitPrice; }
    public String getProductCode() { return productCode; }
    public String getProductDescription() { return productDescription; }
    public int getQuantity() { return quantity; }
}
import java.util.ArrayList;
import java.util.List;

public class Order {
    private String orderId;
    private String customerName; // <-- Cambiamos customerId por customerName
    private List<OrderItem> items;
    private OrderStatus status;
    private String trackingNumber;
    private String notes;

    // Actualizamos el constructor para recibir el nombre
    public Order(String orderId, String customerName, String deliveryAddress) {
        this.orderId = orderId;
        this.customerName = customerName; 
        this.items = new ArrayList<>();
        this.status = OrderStatus.PENDIENTE;
    }

    public double getTotalAmount() {
        double total = 0;
        for (OrderItem item : items) {
            total += item.getSubtotal();
        }
        return total;
    }

    public void addItem(OrderItem item) {
        this.items.add(item);
    }

    public boolean canBeCancelled() {
        return this.status == OrderStatus.PENDIENTE || this.status == OrderStatus.CONFIRMADO;
    }

    public String getOrderId() { return orderId; }
    
    // Nuevo getter para obtener el nombre
    public String getCustomerName() { return customerName; } 
    
    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }
    public void setPaymentStatus(PaymentStatus paymentStatus) { }
    public String getTrackingNumber() { return trackingNumber; }
    public void setTrackingNumber(String trackingNumber) { this.trackingNumber = trackingNumber; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public List<OrderItem> getItems() { return items; }
}
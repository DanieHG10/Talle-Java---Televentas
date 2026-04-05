package modelos;

import java.util.ArrayList;
import java.util.List;
import enums.OrderStatus;
import enums.PaymentStatus;

public class Order {
    private String orderId;
    private String customerName;
    private List<OrderItem> items;
    private OrderStatus status;
    private PaymentStatus paymentStatus;
    private String deliveryAddress;
    private String trackingNumber;
    private String notes;

    public Order(String orderId, String customerName, String deliveryAddress) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.deliveryAddress = deliveryAddress;
        this.items = new ArrayList<>();

        this.status = OrderStatus.PENDIENTE;
        this.paymentStatus = PaymentStatus.PENDIENTE; 
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
    public String getCustomerName() { return customerName; }
    public List<OrderItem> getItems() { return items; }
    
    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }    
    public PaymentStatus getPaymentStatus() { return paymentStatus; } 
    public void setPaymentStatus(PaymentStatus paymentStatus) { this.paymentStatus = paymentStatus; }
    
    public String getDeliveryAddress() { return deliveryAddress; }
    
    public String getTrackingNumber() { return trackingNumber; }
    public void setTrackingNumber(String trackingNumber) { this.trackingNumber = trackingNumber; }
    
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
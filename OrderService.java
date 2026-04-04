import java.util.HashMap;
import java.util.Map;

public class OrderService {
    private IPaymentProcessor paymentProcessor;
    private INotificationService notificationService;
    private Map<String, Order> orders;
    private int orderCounter;

    public OrderService(IPaymentProcessor paymentProcessor, IInventoryService inventoryService, INotificationService notificationService) {
        this.paymentProcessor = paymentProcessor;
        this.notificationService = notificationService;
        this.orders = new HashMap<>();
        this.orderCounter = 0;
    }

  // Cambiamos el parámetro para que reciba customerName
    public Order createOrder(String customerName, String deliveryAddress) {
        this.orderCounter++;
        String orderId = String.format("ORD-%06d", this.orderCounter);
        
        // Pasamos el nombre al crear la orden
        Order order = new Order(orderId, customerName, deliveryAddress); 
        this.orders.put(orderId, order);
        return order;
    }

    public String processPayment(String orderId, PaymentMethod method, Map<String, String> cardDetails) {
        Order order = orders.get(orderId);
        if (order == null) return "Orden no encontrada";
        
        boolean success = paymentProcessor.processPayment(order.getTotalAmount(), method, cardDetails);
        
        if (success) {
            order.setPaymentStatus(PaymentStatus.APROBADO);
            order.setStatus(OrderStatus.CONFIRMADO);
            
            String correoSimulado = order.getCustomerName().replace(" ", "").toLowerCase() + "@televentas.com";
            notificationService.sendOrderConfirmation(correoSimulado, orderId);
            
            return "Pago procesado exitosamente";
        } else {
            order.setPaymentStatus(PaymentStatus.RECHAZADO);
            return "El pago fue rechazado";
        }
    }   
    public Order getOrder(String orderId) {
        return orders.get(orderId);
    }

    public java.util.List<Order> getAllOrders() {
        return new java.util.ArrayList<>(orders.values());
    }
} 

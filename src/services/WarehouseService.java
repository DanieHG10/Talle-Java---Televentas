package services;

import interfaces.*;
import modelos.*;

import java.util.*;

public class WarehouseService {
    private OrderService orderService;
    private ITransportService transportService;
    private IInventoryService inventoryService; // Agregamos el inventario

    public WarehouseService(OrderService orderService, ITransportService transportService, IInventoryService inventoryService) {
        this.orderService = orderService;
        this.transportService = transportService;
        this.inventoryService = inventoryService; // Lo inicializamos
    }

    public List<Order> getPendingOrders() {
        List<Order> pending = new ArrayList<>();
        for (Order order : orderService.getAllOrders()) {
            if (order.getStatus() == OrderStatus.CONFIRMADO) {
                pending.add(order);
            }
        }
        return pending;
    }

    public String packOrder(String orderId, String notes) {
        Order order = orderService.getOrder(orderId);
        if (order == null) return "Orden no encontrada";
        if (order.getStatus() != OrderStatus.CONFIRMADO) return "Solo se pueden empaquetar órdenes confirmadas";

        // CUMPLIENDO EL ENUNCIADO: Actualizar inventario al armar el pedido
        for (OrderItem item : order.getItems()) {
            boolean hasStock = inventoryService.checkStock(item.getProductCode(), item.getQuantity());
            if (!hasStock) {
                return "❌ Error: Stock insuficiente para " + item.getProductDescription() + " al intentar empaquetar.";
            }
        }
        
        for (OrderItem item : order.getItems()) {
            inventoryService.updateStock(item.getProductCode(), item.getQuantity());
        }

        order.setStatus(OrderStatus.LLENO);
        order.setNotes(notes);
        return "✅ Orden empaquetada. Stock descontado del inventario.";
    }

    public String arrangeShipment(String orderId, String destination, double weight) {
        Order order = orderService.getOrder(orderId);
        if (order == null) return "Orden no encontrada";
        if (order.getStatus() != OrderStatus.LLENO) return "La orden debe estar empaquetada primero";

        List<TransportCompany> companies = transportService.getAvailableCompanies(destination);
        if (companies.isEmpty()) return "No hay empresas disponibles";

        TransportCompany company = companies.get(0);
        String tracking = transportService.createShipment(orderId, company.getCompanyId(), destination, weight);
        
        order.setTrackingNumber(tracking);
        order.setStatus(OrderStatus.ENVIADO);
        
        return "✅ Envío organizado. Guía: " + tracking;
    }
}
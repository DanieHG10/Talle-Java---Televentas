# Sistema de Gestión de Televentas

Este es el taller POO sobre las televentas diseñada para digitalizar y optimizar el ciclo de preventa, venta y postventa a distancia desarrollado en Java utilizando Programación Orientada a Objetos.

## Diagrama UML de Clases


A continuación se presenta la estructura de clases y sus relaciones:

```mermaid

classDiagram
    %% Enums
    class OrderStatus {
        <<enumeration>>
        PENDIENTE
        CONFIRMADO
        ENVIADO
        CANCELADO
    }
    class PaymentStatus {
        <<enumeration>>
        PENDIENTE
        APROBADO
        RECHAZADO
        REINTEGRADO
    }
    class TipoQueja {
        <<enumeration>>
        RETRASO_EN_LA_ENTREGA
        PRODUCTO_DANADO
        PROBLEMAS_DE_CALIDAD
        OTROS
    }

    %% Interfaces (Contratos)
    class IPaymentProcessor {
        <<interface>>
    }
    class IInventoryService {
        <<interface>>
    }
    class INotificationService {
        <<interface>>
    }
    class ITransportService {
        <<interface>>
    }

    %% Modelos (Entidades)
    class Order {
        -String orderId
        -String customerName
        -String deliveryAddress
        -OrderStatus status
        -PaymentStatus paymentStatus
        -List~OrderItem~ items
        +getTotalAmount() double
        +addItem(OrderItem item) void
        +canBeCancelled() boolean
    }

    class OrderItem {
        -String productId
        -String description
        -int quantity
        -double unitPrice
        +getSubtotal() double
    }

    class Product {
        -String codigo
        -String descripcion
        -double precio
        -int cantidadDisponible
    }

    class Complaint {
        -String complaintId
        -String customerName
        -TipoQueja tipo
        -String descripcion
    }

    %% Servicios (Lógica de Negocio)
    class OrderService {
        -IPaymentProcessor paymentProcessor
        -IInventoryService notificationService
        -INotificationService inventoryService
        +createOrder(String name, String address) Order
        +processPayment(String id, Map details) String
        +getOrder(String id) Order
        +getAllOrders() List~Order~
    }

    class WarehouseService {
        -OrderService orderService
        -ITransportService transportService
        -IInventoryService inventoryService
        +getPendingOrders() List~Order~
        +packOrder(String id, String notes) String
        +arrangeShipment(String id, String city) String
    }

    class ComplaintService {
        -INotificationService notificationService
        +createComplaint(String name, TipoQueja tipo, String desc) Complaint
    }

    %% Presentación (UI)
    class TeleventasUI {
        -OrderService orderService
        -WarehouseService warehouseService
        -IInventoryService inventoryService
        -ComplaintService complaintService
        +iniciar() void
        -menuCliente() void
        -menuDeposito() void
        -menuQuejas() void
    }

    %% Relaciones (Composición, Agregación y Dependencia)
    Order "1" *-- "*" OrderItem : contiene
    Order --> OrderStatus : tiene
    Order --> PaymentStatus : tiene
    Complaint --> TipoQueja : clasifica

    OrderService ..> Order : gestiona
    OrderService --> IPaymentProcessor
    OrderService --> IInventoryService
    OrderService --> INotificationService

    WarehouseService --> OrderService
    WarehouseService --> ITransportService
    WarehouseService --> IInventoryService

    ComplaintService ..> Complaint : registra
    ComplaintService --> INotificationService

    TeleventasUI --> OrderService
    TeleventasUI --> WarehouseService
    TeleventasUI --> IInventoryService
    TeleventasUI --> ComplaintService

```

import java.util.Scanner;
import java.util.List;
import java.util.HashMap;

public class TeleventasUI {
    private OrderService orderService;
    private WarehouseService warehouseService;
    private IInventoryService inventoryService;
    private ComplaintService complaintService;
    private Scanner scanner;

    public TeleventasUI(OrderService orderService, WarehouseService warehouseService, IInventoryService inventoryService, ComplaintService complaintService) {
        this.orderService = orderService;
        this.warehouseService = warehouseService;
        this.inventoryService = inventoryService;
        this.complaintService = complaintService;
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        boolean salir = false;
        while (!salir) {
            System.out.println("  SISTEMA TELEVENTAS - MENÚ PRINCIPAL");
            System.out.println("1. Acceso Cliente");
            System.out.println("2. Acceso Depósito/Almacén");
            System.out.println("3. Presentar Queja / Reclamo");
            System.out.println("4. Salir");
            System.out.print("\nSeleccione una opción: ");

            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1": menuCliente(); break;
                case "2": menuDeposito(); break;
                case "3": menuQuejas(); break;
                case "4": 
                    System.out.println("\n✅ ¡Gracias por usar TeleVentas! Hasta luego.");
                    salir = true; 
                    break;
                default: System.out.println("❌ Opción no válida.");
            }
        }
    }

    private void menuCliente() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- MENÚ CLIENTE ---");
            System.out.println("1. Consultar Catálogo de Productos");
            System.out.println("2. Suscribirse al Catálogo (Correo)");
            System.out.println("3. Crear Orden de Compra");
            System.out.println("4. Cancelar Orden");
            System.out.println("5. Volver al menú principal");
            System.out.print("Opción: ");
            String opcion = scanner.nextLine();
            
            switch (opcion) {
                case "1":
                    System.out.println("\n📦 CATÁLOGO DISPONIBLE:");
                    for (Product p : inventoryService.getAllProducts()) {
                        System.out.println("- [" + p.getCodigo() + "] " + p.getDescripcion() + " | Precio: $" + p.getPrecio() + " | Disponibles: " + p.getCantidadDisponible());
                    }
                    break;
                case "2":
                    System.out.print("Ingrese su nombre para la suscripción: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Ingrese su correo electrónico: ");
                    String correo = scanner.nextLine();
                    System.out.println("✅ " + nombre + ", te has suscrito. El catálogo se enviará periódicamente a " + correo);
                    break;
                case "3":
                    crearOrdenUI();
                    break;
                case "4":
                    System.out.print("Ingrese el ID de la orden a cancelar (ej. ORD-000001): ");
                    String orderId = scanner.nextLine();
                    Order orderToCancel = orderService.getOrder(orderId);
                    if (orderToCancel != null && orderToCancel.canBeCancelled()) {
                        orderToCancel.setStatus(OrderStatus.CANCELADO);
                        System.out.println("✅ Orden cancelada exitosamente.");
                    } else {
                        System.out.println("❌ No se encontró la orden o ya no puede ser cancelada.");
                    }
                    break;
                case "5": volver = true; break;
                default: System.out.println("❌ Opción no válida.");
            }
        }
    }

    private void crearOrdenUI() {
        System.out.print("Nombre del Cliente: "); 
        String nombreCliente = scanner.nextLine();
        System.out.print("Dirección de entrega: ");
        String direccion = scanner.nextLine();
        
        Order order = orderService.createOrder(nombreCliente, direccion);
        System.out.println("✅ Orden creada con ID: " + order.getOrderId());

        boolean agregando = true;
        while (agregando) {
            System.out.print("\nIngrese el código del producto (o 'fin' para terminar): ");
            String codigo = scanner.nextLine().toUpperCase();
            
            if (codigo.equals("FIN")) {
                agregando = false;
            } else {
                Product p = inventoryService.getProductInfo(codigo);
                if (p != null) {
                    System.out.print("Cantidad a llevar: ");
                    int cantidad = Integer.parseInt(scanner.nextLine());
                    order.addItem(new OrderItem(p.getCodigo(), p.getDescripcion(), cantidad, p.getPrecio()));
                    System.out.println("✅ Producto agregado.");
                } else {
                    System.out.println("❌ Producto no encontrado.");
                }
            }
        }

        if (order.getItems().size() > 0) {
            System.out.println("\n💳 Total a pagar: $" + order.getTotalAmount());
            System.out.println("Procesando pago automático con Tarjeta de Crédito...");
            String resultado = orderService.processPayment(order.getOrderId(), PaymentMethod.TARJETA_DE_CREDITO, new HashMap<>());
            System.out.println(resultado);
        } else {
            System.out.println("Orden vacía. Se descartará.");
        }
    }

    private void menuDeposito() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- MENÚ DEPÓSITO/ALMACÉN ---");
            System.out.println("1. Ver órdenes confirmadas para empaquetar");
            System.out.println("2. Empaquetar orden (Actualiza Stock)");
            System.out.println("3. Determinar logística de entrega (Envío)");
            System.out.println("4. Volver");
            System.out.print("Opción: ");
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    List<Order> pendientes = warehouseService.getPendingOrders();
                    if (pendientes.isEmpty()) System.out.println("✅ No hay órdenes pendientes.");
                    for (Order o : pendientes) {
                        System.out.println("🔹 Orden: " + o.getOrderId() + " | Cliente: " + o.getCustomerName());
                    }
                    break;
                case "2":
                    System.out.print("ID de la orden a empaquetar: ");
                    String idEmpaque = scanner.nextLine();
                    System.out.println(warehouseService.packOrder(idEmpaque, "Revisado por depósito"));
                    break;
                case "3":
                    System.out.print("ID de la orden para enviar: ");
                    String idEnvio = scanner.nextLine();
                    System.out.print("Ciudad de destino: ");
                    String destino = scanner.nextLine();
                    System.out.println(warehouseService.arrangeShipment(idEnvio, destino, 2.5));
                    break;
                case "4": volver = true; break;
                default: System.out.println("❌ Opción no válida.");
            }
        }
    }

    private void menuQuejas() {
        System.out.println("\n--- PRESENTAR QUEJA ---");
        System.out.print("Nombre del Cliente: ");
        String nombre = scanner.nextLine();
        System.out.println("Motivos: 1. Retraso 2. Producto Dañado 3. Calidad 4. Otros");
        System.out.print("Seleccione motivo: ");
        String motivo = scanner.nextLine();
        
        Complaint tipo = Complaint.OTROS;
        if (motivo.equals("1")) tipo = Complaint.RETRASO_EN_LA_ENTREGA;
        if (motivo.equals("2")) tipo = Complaint.PRODUCTO_DANADO;
        if (motivo.equals("3")) tipo = Complaint.PROBLEMAS_DE_CALIDAD;

        System.out.print("Describa su problema: ");
        String desc = scanner.nextLine();

        Complaint queja = complaintService.createComplaint(nombre, tipo, desc);
        System.out.println("✅ Queja registrada con ID: " + queja.getComplaintId() + " y remitida al gerente.");
    }

    public static void main(String[] args) {
        // Inicialización de todas las dependencias
        IPaymentProcessor payment = new CreditCardPaymentProcessor(); 
        IInventoryService inventory = new InventoryService();
        INotificationService notification = new EmailNotificationService();
        ITransportService transport = new TransportServiceImpl();
        
        // Servicios de Lógica
        OrderService orderService = new OrderService(payment, inventory, notification);
        WarehouseService warehouseService = new WarehouseService(orderService, transport, inventory);
        ComplaintService complaintService = new ComplaintService(notification);
        
        TeleventasUI ui = new TeleventasUI(orderService, warehouseService, inventory, complaintService);
        
        System.out.println("      SISTEMA TELEVENTAS - GESTIÓN DE COMPRAS A DISTANCIA     ");        
        ui.iniciar();
    }
}
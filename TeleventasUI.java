import java.util.Scanner;

public class TeleventasUI {
    private OrderService orderService;
    private Scanner scanner;

    public TeleventasUI(OrderService orderService) {
        this.orderService = orderService;
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        boolean salir = false;
        while (!salir) {
            System.out.println("  SISTEMA TELEVENTAS - MENÚ PRINCIPAL");
            System.out.println("1. Acceso Cliente");
            System.out.println("2. Acceso Depósito/Almacén");
            System.out.println("3. Salir");
            System.out.print("\nSeleccione una opción: ");

            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    menuCliente();
                    break;
                case "2":
                    System.out.println("Módulo en construcción...");
                    break;
                case "3":
                    System.out.println("\n✅ ¡Gracias por usar TeleVentas! Hasta luego.");
                    salir = true;
                    break;
                default:
                    System.out.println("❌ Opción no válida. Intente de nuevo.");
            }
        }
    }

    private void menuCliente() {
        System.out.println("\n--- MENÚ CLIENTE ---");
        System.out.println("1. Crear orden");
        System.out.println("2. Volver");
        System.out.print("Opción: ");
        String opcion = scanner.nextLine();
        
        if (opcion.equals("1")) {
            System.out.print("ID del Cliente: ");
            String id = scanner.nextLine();
            System.out.print("Dirección de entrega: ");
            String direccion = scanner.nextLine();
            
            Order order = orderService.createOrder(id, direccion);
            System.out.println("✅ Orden creada con ID: " + order.getOrderId());
        }
    }

    public static void main(String[] args) {
        // Inicialización de dependencias
        IPaymentProcessor payment = new CreditCardPaymentProcessor(); 
        IInventoryService inventory = new InventoryService();
        INotificationService notification = new EmailNotificationService();
        
        OrderService orderService = new OrderService(payment, inventory, notification);
        
        TeleventasUI ui = new TeleventasUI(orderService);
        
        System.out.println("        SISTEMA TELEVENTAS - GESTIÓN DE COMPRAS A DISTANCIA       ");
        
        ui.iniciar();
    }
}
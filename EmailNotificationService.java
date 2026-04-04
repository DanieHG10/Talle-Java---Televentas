public class EmailNotificationService implements INotificationService {
    
    @Override
    public boolean sendEmail(String recipient, String subject, String body) {
        try {
            System.out.println("📧 Email enviado a " + recipient);
            System.out.println("   Asunto: " + subject);
            return true;
        } catch (Exception e) {
            System.out.println("❌ Error enviando email: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean sendOrderConfirmation(String customerEmail, String orderId) {
        String subject = "Confirmación de Orden - " + orderId;
        String body = "Su orden " + orderId + " ha sido confirmada.";
        return sendEmail(customerEmail, subject, body);
    }
}
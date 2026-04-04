public interface INotificationService {
    boolean sendEmail(String recipient, String subject, String body);
    boolean sendOrderConfirmation(String customerEmail, String orderId);
}
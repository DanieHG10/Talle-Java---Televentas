import java.util.Map;

public interface IPaymentProcessor {
    boolean processPayment(double amount, PaymentMethod paymentMethod, Map<String, String> cardDetails);
    boolean validatePaymentMethod(PaymentMethod paymentMethod);
}
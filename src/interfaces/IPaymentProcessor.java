package interfaces;

import java.util.Map;
import enums.*;;

public interface IPaymentProcessor {
    boolean processPayment(double amount, PaymentMethod paymentMethod, Map<String, String> cardDetails);
    boolean validatePaymentMethod(PaymentMethod paymentMethod);
}
import java.util.Map;

public class CreditCardPaymentProcessor implements IPaymentProcessor {
    
    @Override
    public boolean processPayment(double amount, PaymentMethod paymentMethod, Map<String, String> cardDetails) {
        if (!validatePaymentMethod(paymentMethod)) {
            return false;
        }
        System.out.println("✅ Pago de $" + String.format("%.2f", amount) + " procesado exitosamente");
        return true;
    }

    @Override
    public boolean validatePaymentMethod(PaymentMethod paymentMethod) {
        return paymentMethod == PaymentMethod.TARJETA_DE_CREDITO;
    }
}
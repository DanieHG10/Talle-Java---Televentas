import java.util.List;

public interface IInventoryService {
    Product getProductInfo(String productCode);
    boolean checkStock(String productCode, int quantity);
    boolean updateStock(String productCode, int quantityDecrease);
    List<Product> getAllProducts();
}

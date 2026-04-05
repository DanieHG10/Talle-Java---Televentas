package services;

import interfaces.*;
import modelos.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryService implements IInventoryService {
    private Map<String, Product> products;

    public InventoryService() {
        this.products = new HashMap<>();
        products.put("PROD-001", new Product("PROD-001", "Laptop Dell Inspiron", 2500000, 15, "Electrónica"));
        products.put("PROD-002", new Product("PROD-002", "Mouse Inalámbrico", 80000, 50, "Accesorios"));
    }

    @Override
    public Product getProductInfo(String productCode) {
        return products.get(productCode);
    }

    @Override
    public boolean checkStock(String productCode, int quantity) {
        Product product = products.get(productCode);
        if (product == null) return false;
        return product.isAvailable(quantity);
    }

    @Override
    public boolean updateStock(String productCode, int quantityDecrease) {
        Product product = products.get(productCode);
        if (product == null) return false;
        return product.decreaseStock(quantityDecrease);
    }

    @Override
    public List<Product> getAllProducts() {
        return new ArrayList<>(products.values());
    }
}
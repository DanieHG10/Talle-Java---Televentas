package modelos;

public class Product {
    private String codigo;
    private String descripcion;
    private double precio;
    private int cantidadDisponible;
    public Product(String codigo, String descripcion, double precio, int cantidadDisponible, String category) {
        if (precio < 0) throw new IllegalArgumentException("El precio no puede ser negativo");
        if (cantidadDisponible < 0) throw new IllegalArgumentException("La cantidad no puede ser negativa");
        
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidadDisponible = cantidadDisponible;
    }

    public boolean isAvailable(int cantidad) {
        return this.cantidadDisponible >= cantidad;
    }

    public boolean decreaseStock(int cantidad) {
        if (isAvailable(cantidad)) {
            this.cantidadDisponible -= cantidad;
            return true;
        }
        return false;
    }

    public String getCodigo() { return codigo; }
    public String getDescripcion() { return descripcion; }
    public double getPrecio() { return precio; }
    public int getCantidadDisponible() { return cantidadDisponible; }

}
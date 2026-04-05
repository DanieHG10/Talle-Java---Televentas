package modelos;

public class Complaint {
    public static final Complaint RETRASO_EN_LA_ENTREGA = null;
	public static final Complaint OTROS = null;
	public static final Complaint PRODUCTO_DANADO = null;
	public static final Complaint PROBLEMAS_DE_CALIDAD = null;
    private String complaintId;
    private String customerName; // Usando Nombre en vez de ID como pediste
    private EstadoQueja status;

    public Complaint(String complaintId, String customerName, Complaint type, String description) {
        this.complaintId = complaintId;
        this.customerName = customerName;
        this.status = EstadoQueja.ABIERTO;
    }

    public String getComplaintId() { return complaintId; }
    public String getCustomerName() { return customerName; }
    public EstadoQueja getStatus() { return status; }

	public String name() {
		throw new UnsupportedOperationException("Unimplemented method 'name'");
	}
}
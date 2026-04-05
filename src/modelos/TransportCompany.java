package modelos;

public class TransportCompany {
    private String companyId;
    private String name;
    public TransportCompany(String companyId, String name, double cost) {
        this.companyId = companyId;
        this.name = name;
    }

    public String getCompanyId() { return companyId; }
    public String getName() { return name; }
}
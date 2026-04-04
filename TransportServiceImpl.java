import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TransportServiceImpl implements ITransportService {
    private Map<String, TransportCompany> companies;

    public TransportServiceImpl() {
        this.companies = new HashMap<>();
        // Simulamos empresas colombianas para darle un toque local
        companies.put("TRANS-1", new TransportCompany("TRANS-1", "Servientrega", 15.0));
        companies.put("TRANS-2", new TransportCompany("TRANS-2", "Inter Rapidísimo", 10.0));
    }

    @Override
    public List<TransportCompany> getAvailableCompanies(String destination) {
        return new ArrayList<>(companies.values());
    }

    @Override
    public String createShipment(String orderId, String companyId, String address, double weight) {
        // Genera un código de rastreo aleatorio
        String tracking = "TRACK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        System.out.println("📦 Envío creado por la empresa " + companyId + " con guía: " + tracking);
        return tracking;
    }
}
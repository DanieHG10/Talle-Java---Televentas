package interfaces;

import java.util.List;

import modelos.TransportCompany;

public interface ITransportService {
    List<TransportCompany> getAvailableCompanies(String destination);
    String createShipment(String orderId, String companyId, String address, double weight);
}
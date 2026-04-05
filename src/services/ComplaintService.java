package services;

import enums.TipoQueja;
import interfaces.*;
import modelos.*;

public class ComplaintService {
    private INotificationService notificationService;
    private int complaintCounter = 0;

    public ComplaintService(INotificationService notificationService) {
        this.notificationService = notificationService;
    }

    public Complaint createComplaint(String customerName, TipoQueja tipo, String description) {
        this.complaintCounter++;
        String complaintId = String.format("COMP-%06d", this.complaintCounter);
        Complaint complaint = new Complaint(complaintId, customerName, tipo, description);
        
        String subject = "Urgente: Nueva Queja " + complaintId;
        String body = "Cliente: " + customerName + "\nTipo: " + tipo.name() + "\nDescripción: " + description;
        notificationService.sendEmail("gerente_relaciones@televentas.com", subject, body);
        
        return complaint;
    }
}
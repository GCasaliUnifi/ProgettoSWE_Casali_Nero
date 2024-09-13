package sender;

public class SMTPEmailService implements EmailService {
    private static SMTPEmailService instance;

    private SMTPEmailService() {
        // Configurazione del servizio SMTP...
    }

    public static SMTPEmailService getInstance() {
        if (instance == null) {
            instance = new SMTPEmailService();
        }
        return instance;
    }

    @Override
    public void sendEmail(Email email) {
        System.out.println("Invio dell'email a: " + email.getTo() + " con Oggetto: " + email.getSubject());
        // Codice per inviare l'email...
    }
}
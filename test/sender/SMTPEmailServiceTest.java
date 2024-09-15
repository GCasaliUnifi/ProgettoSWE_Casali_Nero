package test.sender;

import org.junit.Test;
import sender.Email;
import sender.SMTPEmailService;

import static org.junit.Assert.*;

public class SMTPEmailServiceTest {

    @Test
    public void testSingletonInstance() {
        SMTPEmailService instance1 = SMTPEmailService.getInstance();
        SMTPEmailService instance2 = SMTPEmailService.getInstance();
        assertSame(instance1, instance2);
    }

    @Test
    public void testSendEmail() {
        SMTPEmailService emailService = SMTPEmailService.getInstance();
        Email email = new Email.EmailBuilder()
                .setTo("example@example.com")
                .setSubject("Subject")
                .setBody("Email body")
                .build();

        emailService.sendEmail(email);
        // Verifica che l'email sia stata inviata correttamente (puoi usare un mock per questo)
    }
}
package test.sender;

import org.junit.Test;
import sender.Email;

import static org.junit.Assert.*;

public class EmailTest {

    @Test
    public void testEmailBuilder() {
        Email email = new Email.EmailBuilder()
                .setTo("example@example.com")
                .setSubject("Subject")
                .setBody("Email body")
                .build();

        assertEquals("example@example.com", email.getTo());
        assertEquals("Subject", email.getSubject());
        assertEquals("Email body", email.getBody());
    }
}
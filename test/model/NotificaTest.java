package test.model;

import model.Notifica;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NotificaTest {

    @Test
    public void testNotificaCreation() {
        Notifica notifica = new Notifica(0, 1, "Test Messaggio");

        assertNotNull(notifica);
        assertEquals(0, notifica.getId());
        assertEquals("Test Messaggio", notifica.getMessaggio());
    }
}
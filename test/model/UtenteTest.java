package test.model;

import model.Utente;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UtenteTest {

    @Test
    public void testUtenteCreation() {
        Utente utente = new Utente("mario.rossi@example.com", "123456");
        utente.setId(1);
        utente.setNome("Mario Rossi");

        assertNotNull(utente);
        assertEquals(1, utente.getId());
        assertEquals("Mario Rossi", utente.getNome());
        assertEquals("mario.rossi@example.com", utente.getEmail());
        assertEquals("123456", utente.getPassword());
    }

    @Test
    public void testSetters() {
        Utente utente = new Utente("mario.rossi@example.com", "123456");

        utente.setId(2);
        utente.setNome("Luigi Bianchi");
        utente.setEmail("luigi.bianchi@example.com");
        utente.setPassword("newpassword456");

        assertEquals(2, utente.getId());
        assertEquals("Luigi Bianchi", utente.getNome());
        assertEquals("luigi.bianchi@example.com", utente.getEmail());
        assertEquals("newpassword456", utente.getPassword());
    }
}
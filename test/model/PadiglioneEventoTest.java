package test.model;

import model.Padiglione;
import model.PadiglioneEvento;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PadiglioneEventoTest {

    @Test
    public void testPadiglioneCreation() {
        PadiglioneEvento padiglioneEvento = new PadiglioneEvento(1, 1, 1, 0);

        assertNotNull(padiglioneEvento);
        assertEquals(1, padiglioneEvento.getId_evento());
        assertEquals(1, padiglioneEvento.getId_padiglione());
        assertEquals(1, padiglioneEvento.getId_utente());
        assertEquals(0, padiglioneEvento.getTipo());
        assertEquals("Ristoro", padiglioneEvento.getTipoPadiglione());
    }

    @Test
    public void testSetters() {
        PadiglioneEvento padiglioneEvento = new PadiglioneEvento(1, 1, 1, 0);

        padiglioneEvento.setId_evento(2);
        padiglioneEvento.setId_padiglione(2);
        padiglioneEvento.setId_utente(2);
        padiglioneEvento.setTipo(1);

        assertEquals(2, padiglioneEvento.getId_evento());
        assertEquals(2, padiglioneEvento.getId_padiglione());
        assertEquals(2, padiglioneEvento.getId_utente());
        assertEquals(1, padiglioneEvento.getTipo());
        assertEquals("Commerciale", padiglioneEvento.getTipoPadiglione());

        padiglioneEvento.setTipo(2);
        assertEquals("Intrattenimento", padiglioneEvento.getTipoPadiglione());

        padiglioneEvento.setTipo(3);
        assertEquals("Altro...", padiglioneEvento.getTipoPadiglione());
    }
}
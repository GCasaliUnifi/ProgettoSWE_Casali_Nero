package test.model;

import model.Padiglione;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PadiglioneTest {

    @Test
    public void testPadiglioneCreation() {
        Padiglione padiglione = new Padiglione("P_01", 600.60f);
        padiglione.setId(1);

        assertNotNull(padiglione);
        assertEquals(1, padiglione.getId());
        assertEquals("P_01", padiglione.getCodice());
        assertEquals(600.60f, padiglione.getDimensione());
    }

    @Test
    public void testSetters() {
        Padiglione padiglione = new Padiglione("P_01", 600.60f);
        padiglione.setId(1);
        padiglione.setCodice("P_02");
        padiglione.setDimensione(700.70f);

        assertEquals(1, padiglione.getId());
        assertEquals("P_02", padiglione.getCodice());
        assertEquals(700.70f, padiglione.getDimensione());
    }
}
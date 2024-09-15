package test.model;

import model.Licenza;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LicenzaTest {

    @Test
    public void testLicenzaCreation() {
        Licenza licenza = new Licenza("145789");
        licenza.setId(1);
        licenza.setId_utente(1);
        licenza.setScadenza("2023-01-01");


        assertNotNull(licenza);
        assertEquals(1, licenza.getId());
        assertEquals(1, licenza.getId_utente());
        assertEquals("145789", licenza.getCodice());
        assertEquals("2023-01-01", licenza.getScadenza());
    }

    @Test
    public void testSetters() {
        Licenza licenza = new Licenza("145789");
        licenza.setId(1);
        licenza.setId_utente(1);
        licenza.setScadenza("2023-01-01");

        licenza.setId(2);
        licenza.setId_utente(2);
        licenza.setCodice("145790");
        licenza.setScadenza("2023-01-02");

        assertEquals(2, licenza.getId());
        assertEquals(2, licenza.getId_utente());
        assertEquals("145790", licenza.getCodice());
        assertEquals("2023-01-02", licenza.getScadenza());
    }
}
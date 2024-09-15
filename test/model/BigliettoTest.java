package test.model;

import model.Biglietto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BigliettoTest {

    @Test
    public void testBigliettoCreation() {
        Biglietto biglietto = new Biglietto("Marco", "Rossi", "RSSMRC96A01A012R");
        biglietto.setIdUtente(1);
        biglietto.setIdEvento(2);
        biglietto.setDataPrenotazione("2023-12-31");

        assertNotNull(biglietto);
        assertEquals("Marco", biglietto.getNome());
        assertEquals("Rossi", biglietto.getCognome());
        assertEquals("RSSMRC96A01A012R", biglietto.getCodiceFiscale());
        assertEquals(1, biglietto.getIdUtente());
        assertEquals(2, biglietto.getIdEvento());
        assertEquals("2023-12-31", biglietto.getDataPrenotazione());

        Assertions.assertNotEquals("Luca", biglietto.getNome());
        Assertions.assertNotEquals("Bianchi", biglietto.getCognome());
        Assertions.assertNotEquals("BNCLCU96A01A012R", biglietto.getCodiceFiscale());
        Assertions.assertNotEquals(2, biglietto.getIdUtente());
        Assertions.assertNotEquals(3, biglietto.getIdEvento());
        Assertions.assertNotEquals("2023-12-30", biglietto.getDataPrenotazione());
    }
}
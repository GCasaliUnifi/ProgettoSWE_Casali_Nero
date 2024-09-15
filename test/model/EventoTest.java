package test.model;

import model.Evento;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EventoTest {

    @Test
    public void testEventoCreation() {
        Evento evento = new Evento("E001", "Concerto", "2023-12-31", "Concerto di Capodanno");

        assertNotNull(evento);
        assertEquals("E001", evento.getCodice());
        assertEquals("Concerto", evento.getNome());
        assertEquals("2023-12-31", evento.getData());
        assertEquals("Concerto di Capodanno", evento.getDescrizione());
    }

    @Test
    public void testSetters() {
        Evento evento = new Evento("E001", "Concerto", "2023-12-31", "Concerto di Capodanno");
        evento.setId(1);
        evento.setCodice("E002");
        evento.setNome("Opera");
        evento.setData("2024-01-01");
        evento.setDescrizione("Opera di Capodanno");
        evento.setPosti(100);

        assertEquals(1, evento.getId());
        assertEquals("E002", evento.getCodice());
        assertEquals("Opera", evento.getNome());
        assertEquals("2024-01-01", evento.getData());
        assertEquals("Opera di Capodanno", evento.getDescrizione());
        assertEquals(100, evento.getPosti());

        Assertions.assertNotEquals(0, evento.getId());
        Assertions.assertNotEquals("E001", evento.getCodice());
        Assertions.assertNotEquals("Concerto", evento.getNome());
        Assertions.assertNotEquals("2023-12-31", evento.getData());
        Assertions.assertNotEquals("Concerto di Capodanno", evento.getDescrizione());
        Assertions.assertNotEquals(0, evento.getPosti());
    }
}
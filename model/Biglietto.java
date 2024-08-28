package model;

import com.pdfjet.Font;
import com.pdfjet.Image;
import net.sourceforge.barbecue.Barcode;
import pdf.GeneraPDF;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import com.pdfjet.*;
import java.awt.print.*;
import net.sourceforge.barbecue.*;

import javax.imageio.ImageIO;

public class Biglietto implements GeneraPDF {
    private int id;
    private int idEvento;
    private int idUtente;
    private String nome;
    private String cognome;
    private String codiceFiscale;
    private String nomeEvento;
    private String dataPrenotazione;

    public Biglietto(String nome, String cognome, String codiceFiscale) {
        this.nome = nome;
        this.cognome = cognome;
        this.codiceFiscale = codiceFiscale;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }

    public int getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(int idUtente) {
        this.idUtente = idUtente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public String getNomeEvento() {
        return nomeEvento;
    }

    public void setNomeEvento(String nomeEvento) {
        this.nomeEvento = nomeEvento;
    }

    public String getDataPrenotazione() {
        return dataPrenotazione;
    }

    public void setDataPrenotazione(String dataPrenotazione) {
        this.dataPrenotazione = dataPrenotazione;
    }

    @Override
    public boolean generaPDF() throws Exception {
        String nome_biglietto = "BigliettoIngresso"+this.nome+this.cognome+".pdf";
        PDF pdf = new PDF(new BufferedOutputStream(new FileOutputStream("pdf/"+nome_biglietto)));
        Font h1B = new Font(pdf, CoreFont.HELVETICA_BOLD);
        Font h2B = new Font(pdf, CoreFont.HELVETICA_BOLD);
        Font h2 = new Font(pdf, CoreFont.HELVETICA);

        h1B.setSize(20f);
        h2B.setSize(14f);
        h2.setSize(14f);

        Float xPosition = 100f;

        Page page = new Page(pdf, Letter.PORTRAIT);

        //Prima riga: Biglietto di Ingresso
        TextLine textLine = new TextLine(h1B, "Biglietto di Ingresso");
        textLine.setPosition( (page.getWidth() - textLine.getWidth()) / 2, 50f);
        textLine.drawOn(page);

        //Seconda riga: Nome evento
        textLine = new TextLine(h2, "Evento: "+this.nomeEvento);
        textLine.setPosition( xPosition, 100f);
        textLine.drawOn(page);
        //Terza riga: Data prenotazione
        String dataIta = this.dataPrenotazione.substring(8, 10)+"/"+this.dataPrenotazione.substring(5, 7)+"/"+this.dataPrenotazione.substring(0, 4);
        textLine = new TextLine(h2, "Data Prenotazione: "+dataIta);
        textLine.setPosition( xPosition, 150f);
        textLine.drawOn(page);

        textLine = new TextLine(h2B, "--- Intestatario del Biglietto ---");
        textLine.setPosition( (page.getWidth() - textLine.getWidth()) / 2, 200f);
        textLine.drawOn(page);

        textLine = new TextLine(h2, "Utente: "+this.nome+" "+this.cognome);
        textLine.setPosition( xPosition, 250f);
        textLine.drawOn(page);

        textLine = new TextLine(h2, "Codice Fiscale: "+this.codiceFiscale);
        textLine.setPosition( xPosition, 300f);
        textLine.drawOn(page);

        //Barcode
        Barcode barcode = BarcodeFactory.createCode128B(this.id+this.codiceFiscale);
        BufferedImage image = new BufferedImage(barcode.getWidth(), barcode.getHeight()-4, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) image.getGraphics();
        barcode.draw(g, 0, 0);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        byte[] imageBytes = baos.toByteArray();
        Image barcodeImage = new Image(pdf, new ByteArrayInputStream(imageBytes), ImageType.PNG);
        barcodeImage.setPosition((page.getWidth() - barcodeImage.getWidth()) / 2, 350f);
        barcodeImage.drawOn(page);

        try{
            pdf.complete();
            System.out.println("biglietto Generato!");
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}

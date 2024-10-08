package view;

import controller.Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;
import model.*;
import model.Biglietto;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Home extends ViewInterface {
    @FXML
    private MenuItem menuPadiglioni;
    @FXML
    private MenuItem menuEventi;
    @FXML
    private Button btnLogOut;

    //IC
    @FXML
    private Button btnAggiungiEvento;
    @FXML
    private Button btnAggiungiPadiglione;
    @FXML
    private Button btnListaCittadini;
    @FXML
    private TableColumn<Notifica, String> nomeNotifica;
    @FXML
    private TableColumn<Notifica, String> cognomeNotifica;
    @FXML
    private TableColumn<Notifica, String> dataNotifica;
    @FXML
    private TableView<Notifica> tabellaNotifiche;
    @FXML
    private Button btnScan;

    //Cittadino
    @FXML
    private Label codiceLicenza;
    @FXML
    private Label scadenzaLicenza;
    @FXML
    private Button btnRichiediLicenza;
    @FXML
    private TableColumn<Biglietto, String> eventoBiglietto;
    @FXML
    private TableColumn<Biglietto, String> nomeBiglietto;
    @FXML
    private TableColumn<Biglietto, String> cognomeBiglietto;
    @FXML
    private TableView<Biglietto> tabellaBiglietti;
    @FXML
    private TableColumn<PadiglioneEvento, String> eventoPadiglione;
    @FXML
    private TableColumn<PadiglioneEvento, String> codicePadiglione;
    @FXML
    private TableColumn<PadiglioneEvento, String> tipologiaPadiglione;
    @FXML
    private TableView<PadiglioneEvento> tabellaPadiglioni;

    @FXML
    public void initialize() throws SQLException {
        menuPadiglioni.setOnAction(event -> {
            try{
                controller.setViewAttuale(new ListaPadiglioni(this.controller, stage));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        menuEventi.setOnAction(event -> {
            try {
                controller.setViewAttuale(new ListaEventi(this.controller, stage));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        btnLogOut.setOnAction(event -> {
            try {
                controller.setViewAttuale(new LogIn(this.controller, stage));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        if(this.controller.isAmministratore()) {    //IC
            btnAggiungiPadiglione.setOnAction(event -> {
                try {
                    controller.setViewAttuale(new AggiungiPadiglione(this.controller, stage));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

            btnAggiungiEvento.setOnAction(event -> {
                try {
                    controller.setViewAttuale(new AggiungiEvento(this.controller, stage));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

            btnListaCittadini.setOnAction(event -> {
                try {
                    controller.setViewAttuale(new ListaCittadini(this.controller, stage));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

            nomeNotifica.setCellValueFactory(new PropertyValueFactory<>("nome"));
            cognomeNotifica.setCellValueFactory(new PropertyValueFactory<>("cognome"));
            dataNotifica.setCellValueFactory(new PropertyValueFactory<>("data"));

            ArrayList<Notifica> notifiche = controller.getNotificheLicenza();

            if(notifiche != null){
                for(Notifica n : notifiche){
                    //recupera i dati dell'utente
                    Utente utente = controller.getUtente(n.getId_utente());
                    n.setNome(utente.getNome());
                    n.setCognome(utente.getCognome());
                    //Data in formato italiano
                    String data_ita = n.getData().substring(8, 10) + "/" + n.getData().substring(5, 7) + "/" + n.getData().substring(0, 4);
                    n.setData(data_ita);
                    //Aggiunge la notifica alla tabella
                    tabellaNotifiche.getItems().add(n);
                }
            }

            //Se clicco su una notifica apri la modifica dell'utente
            tabellaNotifiche.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    try {
                        controller.setUtenteSelezionato(controller.getUtente(newSelection.getId_utente()));
                        controller.setViewAttuale(new ModificaCittadino(controller, stage));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            btnScan.setOnAction(event -> {
                try {
                    controller.setViewAttuale(new ScansionaBiglietto(this.controller, stage));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });


        } else {    //Cittadino
            Licenza licenza = controller.getLicenzaCittadino();
            if(licenza == null) {
                codiceLicenza.setText("Nessuna licenza");
                scadenzaLicenza.setText("Nessuna licenza");
                btnRichiediLicenza.setOnAction(event -> {
                    try {
                        if(controller.onRequestLicenza()){
                            controller.setViewAttuale(new Home(this.controller, stage));
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
            }else{
                codiceLicenza.setText(licenza.getCodice());
                String data_ita = licenza.getScadenza().substring(8, 10) + "/" + licenza.getScadenza().substring(5, 7) + "/" + licenza.getScadenza().substring(0, 4);
                scadenzaLicenza.setText(data_ita);
                btnRichiediLicenza.setText("Richiedi Rinnovo");
                //Controllo se la licenza è scaduta confrontando la data di scadenza con la data attuale
                if(controller.isLicenzaScaduta(licenza)){
                    btnRichiediLicenza.setOnAction(event -> {
                        try {
                            if(controller.onRequestLicenza()){
                                controller.setViewAttuale(new Home(this.controller, stage));
                            }
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    });
                }else{
                    btnRichiediLicenza.setDisable(true);
                }
            }

            eventoBiglietto.setCellValueFactory(new PropertyValueFactory<>("nomeEvento"));
            nomeBiglietto.setCellValueFactory(new PropertyValueFactory<>("nome"));
            cognomeBiglietto.setCellValueFactory(new PropertyValueFactory<>("cognome"));

            ArrayList<Biglietto> biglietti = controller.getBigliettiCittadino();

            if (biglietti != null) {
                for (Biglietto b : biglietti) {
                    tabellaBiglietti.getItems().add(b);
                }
            }

            //Se clicco su un biglietto apro la modifica dell'utente
            tabellaBiglietti.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    try {
                        controller.setBigliettoSelezionato(newSelection);
                        controller.setViewAttuale(new DettagliBiglietto(controller, stage));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });


            eventoPadiglione.setCellValueFactory(new PropertyValueFactory<>("nomeEvento"));
            codicePadiglione.setCellValueFactory(new PropertyValueFactory<>("codicePadiglione"));
            tipologiaPadiglione.setCellValueFactory(new PropertyValueFactory<>("tipoPadiglione"));

            ArrayList<PadiglioneEvento> padiglioneEvento = controller.getPadiglioniEvento_Cittadino();

            if (padiglioneEvento != null) {
                for (PadiglioneEvento pe : padiglioneEvento) {
                    tabellaPadiglioni.getItems().add(pe);
                }
            }

            tabellaPadiglioni.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    try {
                        System.out.println("Hai cliccato su un PadiglioneEvento");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }

    }

    public Home(Controller c, Stage stage) {
        super(c, "Dashboard", c.isAmministratore() ? "fxml/DashboardImpiegatoC.fxml" : "fxml/DashboardCittadino.fxml");
        ViewInterface.stage = stage;
    }

    @Override
    public void display() throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            loader.setController(this);
            Parent root = loader.load();
            Scene scene = new Scene(root, width, height);
            stage.setScene(scene);
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            throw new IOException("Errore nel caricamento della finestra di dashboard");
        }
    }
}

package view;

import controller.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Padiglione;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class ListaPadiglioni extends ViewInterface{

    @FXML
    private MenuItem menuHome;
    @FXML
    private MenuItem menuEventi;

    @FXML
    private TableView<Padiglione> tabellaPadiglioni;
    @FXML
    private TableColumn<Padiglione, Integer> idPadiglione;
    @FXML
    private TableColumn<Padiglione, String> codicePadiglione;
    @FXML
    private TableColumn<Padiglione, Float> dimensionePadiglione;
    @FXML
    private Label txtListaPadiglioni;

    @FXML
    public void initialize() throws SQLException {
        menuHome.setOnAction(event -> {
            try {
                controller.setViewAttuale(new Home(this.controller, stage));
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

        if(controller.isAmministratore()){
            txtListaPadiglioni.setText("Seleziona un padiglione per modificarlo");
        }

        idPadiglione.setCellValueFactory(new PropertyValueFactory<>("id"));
        codicePadiglione.setCellValueFactory(new PropertyValueFactory<>("codice"));
        dimensionePadiglione.setCellValueFactory(new PropertyValueFactory<>("dimensione"));

        ArrayList<Padiglione> lista = controller.getListaPadiglioni();
        if(lista != null){
            ObservableList<Padiglione> tmpList = FXCollections.observableArrayList(lista);
            tabellaPadiglioni.setItems(tmpList);
        }

        //Se seleziono un padiglione dalla tabella carico i suoi dati nei campi di testo
        tabellaPadiglioni.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                System.out.println("Padiglione selezionato: "+newSelection.getId());
                Padiglione p = new Padiglione(newSelection.getCodice(), newSelection.getDimensione());
                p.setId(newSelection.getId());
                controller.setPadiglioneSelezionato(p);
                try{
                    if(controller.isAmministratore()){
                        controller.setViewAttuale(new ModificaPadiglione(this.controller, stage));
                    }else{
                        System.out.println("Implementare vista per Cittadino");
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }

    public ListaPadiglioni(Controller c, Stage stage) {
        super(c, "Lista Padiglioni", "fxml/ListaPadiglioni.fxml");
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
            throw new IOException("Errore nel caricamento della finestra di Lista padiglioni");
        }
    }
}

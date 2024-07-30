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
    private TableView<Padiglione> tabellaPadiglioni;
    @FXML
    private TableColumn<Padiglione, Integer> idPadiglione;
    @FXML
    private TableColumn<Padiglione, String> codicePadiglione;
    @FXML
    private TableColumn<Padiglione, Float> dimensionePadiglione;
    @FXML
    private TextField idModifica;
    @FXML
    private TextField codiceModifica;
    @FXML
    private TextField dimensioneModifica;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnDelete;

    @FXML
    public void initialize() throws SQLException {
        menuHome.setOnAction(event -> {
            try {
                controller.setViewAttuale(new Home(this.controller, stage));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        idPadiglione.setCellValueFactory(new PropertyValueFactory<>("id"));
        codicePadiglione.setCellValueFactory(new PropertyValueFactory<>("codice"));
        dimensionePadiglione.setCellValueFactory(new PropertyValueFactory<>("dimensione"));

        ArrayList<Padiglione> lista = controller.getListaPadiglioni();
        ObservableList<Padiglione> tmpList = FXCollections.observableArrayList(lista);
        tabellaPadiglioni.setItems(tmpList);

        //Se seleziono un padiglione dalla tabella carico i suoi dati nei campi di testo
        tabellaPadiglioni.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                System.out.println("Padiglione selezionato: "+newSelection.getId());
                try{
                    idModifica.setText(String.valueOf(newSelection.getId()));
                    codiceModifica.setText(newSelection.getCodice());
                    dimensioneModifica.setText(String.valueOf(newSelection.getDimensione()));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        //Se clicco sul bottone salva, aggiorno i dati del padiglione selezionato
        btnSave.setOnAction(event -> {
            if(idModifica.getText().isEmpty() || codiceModifica.getText().isEmpty() || dimensioneModifica.getText().isEmpty()) {
                controller.alert(Alert.AlertType.WARNING, "Uno o più campi sono vuoti!");
            } else {
                try {
                    if(controller.onUpdatePadiglione(Integer.parseInt(idModifica.getText()), codiceModifica.getText(), Float.parseFloat(dimensioneModifica.getText()))){
                        controller.alert(Alert.AlertType.INFORMATION, "Padiglione aggiornato con successo!");
                        controller.setViewAttuale(new ListaPadiglioni(this.controller, stage));
                    }
                } catch (NumberFormatException e) {
                    controller.alert(Alert.AlertType.ERROR, "Inserire una dimensione valida!");
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

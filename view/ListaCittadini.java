package view;

import controller.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Evento;
import model.Utente;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class ListaCittadini extends ViewInterface{
    @FXML
    private MenuItem menuHome;
    @FXML
    private MenuItem menuEventi;
    @FXML
    private MenuItem menuPadiglioni;

    @FXML
    private TableColumn<Utente, String> nomeCittadino;
    @FXML
    private TableColumn<Utente, String> cognomeCittadino;
    @FXML
    private TableColumn<Utente, String> emailCittadino;
    @FXML
    private TableView<Utente> tabellaCittadini;

    @FXML
    public void initialize() throws SQLException {
        menuHome.setOnAction(e -> {
            try {
                controller.setViewAttuale(new Home(controller, stage));
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        menuEventi.setOnAction(e -> {
            try {
                controller.setViewAttuale(new ListaEventi(controller, stage));
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        menuPadiglioni.setOnAction(e -> {
            try {
                controller.setViewAttuale(new ListaPadiglioni(controller, stage));
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        nomeCittadino.setCellValueFactory(new PropertyValueFactory<>("nome"));
        cognomeCittadino.setCellValueFactory(new PropertyValueFactory<>("cognome"));
        emailCittadino.setCellValueFactory(new PropertyValueFactory<>("email"));

        ArrayList<Utente> lista = controller.getListaCittadini();
        if(lista != null){
            ObservableList<Utente> tmpList = FXCollections.observableArrayList(lista);
            tabellaCittadini.setItems(tmpList);
        }

        tabellaCittadini.setOnMouseClicked(e -> {
            Utente cittadinoSelezionato = tabellaCittadini.getSelectionModel().getSelectedItem();
            if (cittadinoSelezionato != null) {
                controller.setUtenteSelezionato(cittadinoSelezionato);
                try {
                    controller.setViewAttuale(new ModificaCittadino(controller, stage));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public ListaCittadini(Controller c, Stage stage) {
        super(c, "Lista Cittadini", "fxml/ListaCittadini.fxml");
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
            throw new IOException("Errore nel caricamento della finestra di Lista cittadini");
        }
    }
}

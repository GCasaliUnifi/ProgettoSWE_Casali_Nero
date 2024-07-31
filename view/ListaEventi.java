package view;

import controller.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Evento;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class ListaEventi extends ViewInterface{
    @FXML
    private TableColumn<Evento, String> dataEvento;
    @FXML
    private TableColumn<Evento, String> nomeEvento;
    @FXML
    private TableColumn<Evento, String> descrizioneEvento;
    @FXML
    private TableView<Evento> tabellaEventi;

    @FXML
    public void initialize() throws SQLException {
        dataEvento.setCellValueFactory(new PropertyValueFactory<>("data"));
        nomeEvento.setCellValueFactory(new PropertyValueFactory<>("nome"));
        descrizioneEvento.setCellValueFactory(new PropertyValueFactory<>("descrizione"));

        ArrayList<Evento> lista = controller.getListaEventi();
        ObservableList<Evento> tmpList = FXCollections.observableArrayList(lista);
        tabellaEventi.setItems(tmpList);
    }

    public ListaEventi(Controller c, Stage stage) {
        super(c, "Lista Eventi", "fxml/ListaEventi.fxml");
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
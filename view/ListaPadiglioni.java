package view;

import controller.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Padiglione;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class ListaPadiglioni extends ViewInterface{
    @FXML
    private TableView<Padiglione> tabellaPadiglioni;
    @FXML
    private TableColumn<Padiglione, Integer> idPadiglione;
    @FXML
    private TableColumn<Padiglione, String> codicePadiglione;
    @FXML
    private TableColumn<Padiglione, Float> dimensionePadiglione;

    @FXML
    public void initialize() throws SQLException {
        idPadiglione.setCellValueFactory(new PropertyValueFactory<>("id"));
        codicePadiglione.setCellValueFactory(new PropertyValueFactory<>("codice"));
        dimensionePadiglione.setCellValueFactory(new PropertyValueFactory<>("dimensione"));

        ArrayList<Padiglione> lista = controller.getListaPadiglioni();
        ObservableList<Padiglione> tmpList = FXCollections.observableArrayList(lista);
        tabellaPadiglioni.setItems(tmpList);
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

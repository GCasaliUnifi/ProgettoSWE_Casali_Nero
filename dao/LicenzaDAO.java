package dao;

import model.Licenza;

import java.sql.SQLException;
import java.util.ArrayList;

public interface LicenzaDAO {
    boolean createLicenza(Licenza licenza) throws SQLException;
    Licenza readLicenza(int id_utente) throws SQLException;
    Licenza readLicenza(String codice) throws SQLException;
    boolean updateLicenza(Licenza licenza) throws SQLException;
    boolean deleteLicenza(int id) throws SQLException;
}

package dao;

import model.Notifica;

import java.sql.SQLException;
import java.util.ArrayList;

public interface NotificaDAO {
    boolean createNotifica(Notifica notifica) throws SQLException;
    ArrayList<Notifica> readNotifiche(int id_utente) throws SQLException;
    ArrayList<Notifica> readAllNotifiche() throws SQLException;
    boolean updateNotifica(Notifica notifica) throws SQLException;
    boolean deleteNotifica(int id) throws SQLException;
}

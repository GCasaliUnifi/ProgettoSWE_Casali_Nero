package dao;

import model.Biglietto;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class BigliettoDAOImpl extends DataBaseConnector implements BigliettoDAO {
    @Override
    public boolean createBiglietto(Biglietto biglietto) {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT posti FROM evento WHERE id = ?");
            preparedStatement.setInt(1, biglietto.getIdEvento());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next() && resultSet.getInt("posti") > 0) {
                //se ci sono posti disponibili inserisco il biglietto
                preparedStatement = connection.prepareStatement("INSERT INTO biglietto(id_user, id_evento, nome, cognome, codf, data_prenotazione) values (?, ?, ?, ?, ?, NOW())");
                preparedStatement.setInt(1, biglietto.getIdUtente());
                preparedStatement.setInt(2, biglietto.getIdEvento());
                preparedStatement.setString(3, biglietto.getNome());
                preparedStatement.setString(4, biglietto.getCognome());
                preparedStatement.setString(5, biglietto.getCodiceFiscale());

                if (preparedStatement.executeUpdate() > 0) {
                    preparedStatement = connection.prepareStatement("UPDATE evento SET posti = posti - 1 WHERE id = ?");
                    preparedStatement.setInt(1, biglietto.getIdEvento());
                    if(preparedStatement.executeUpdate() > 0){
                        return true;
                    }
                } else {
                    throw new Exception("Errore durante l'inserimento del biglietto");
                }
            }
        } catch (Exception e) {
            System.out.println("Errore in createBiglietto: "+e.getMessage());
        }
        return false;
    }

    @Override
    public Biglietto readBiglietto(int id) {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM biglietto WHERE id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                Biglietto biglietto = new Biglietto(resultSet.getString("nome"), resultSet.getString("cognome"), resultSet.getString("codf"));
                biglietto.setId(resultSet.getInt("id"));
                biglietto.setIdEvento(resultSet.getInt("id_evento"));
                biglietto.setIdUtente(resultSet.getInt("id_user"));
                biglietto.setDataPrenotazione(resultSet.getString("data_prenotazione"));
                return biglietto;
            }
        } catch (Exception e) {
            System.out.println("Errore in createBiglietto: "+e.getMessage());
        }
        return null;
    }

    @Override
    public ArrayList<Biglietto> readAllBigliettiEvento(int id_evento) {
        return null;
    }


    @Override
    public ArrayList<Biglietto> readAllBigliettiUtente(int id_utente) {
        try{
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT biglietto.id AS id, biglietto.nome AS nome, biglietto.cognome AS cognome, biglietto.codf AS codf, biglietto.id_evento AS id_evento, biglietto.id_user AS id_user, biglietto.data_prenotazione AS data_prenotazione, evento.nome AS nome_evento FROM biglietto, evento WHERE biglietto.id_evento=evento.id AND id_user = ?");
            preparedStatement.setInt(1, id_utente);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Biglietto> biglietti = new ArrayList<>();
            while(resultSet.next()){
                Biglietto biglietto = new Biglietto(resultSet.getString("nome"), resultSet.getString("cognome"), resultSet.getString("codf"));
                biglietto.setId(resultSet.getInt("id"));
                biglietto.setIdEvento(resultSet.getInt("id_evento"));
                biglietto.setIdUtente(resultSet.getInt("id_user"));
                biglietto.setNomeEvento(resultSet.getString("nome_evento"));
                biglietto.setDataPrenotazione(resultSet.getString("data_prenotazione"));
                biglietti.add(biglietto);
            }
            return biglietti;
        }catch (Exception e){
            System.out.println("Errore in readAllBigliettiUtente: "+e.getMessage());
        }
        return null;
    }

    @Override
    public int readBigliettoUtenteEvento(int id_utente, int id_evento) {
        //ritorna il numero di biglietti acquistati da un utente per un evento
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT count(*) as numero_biglietti from biglietto where id_user = ? and id_evento = ?");
            preparedStatement.setInt(1, id_utente);
            preparedStatement.setInt(2, id_evento);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getInt("numero_biglietti");
            }
        } catch (Exception e) {
            System.out.println("Errore in readBigliettoUtenteEvento: "+e.getMessage());
        }
        return 0;
    }

    @Override
    public boolean updateBiglietto(Biglietto biglietto) {
        return false;
    }

    @Override
    public boolean deleteBiglietto(int id) {
        return false;
    }
}

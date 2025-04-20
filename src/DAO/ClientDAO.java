package DAO;

import Modele.Client;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ClientDAO {


    public void creerClient(String nom, String email, String motDePasse) {
        String sql = "INSERT INTO utilisateur (nom, email, mot_de_passe, type) VALUES (?, ?, ?, 'client')";
        try (Connection conn = DBConnection.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nom);
            stmt.setString(2, email);
            stmt.setString(3, motDePasse);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur création client : " + e.getMessage());
        }
    }



    public Client getClientByEmailAndPassword(String email, String password) {
        Client client = null;

        String sql = "SELECT * FROM utilisateur WHERE email = ? AND mot_de_passe = ? AND type = 'client'";

        try (Connection conn = DBConnection.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                client = new Client(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("email"),
                        rs.getString("mot_de_passe")
                );
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la connexion client : " + e.getMessage());
        }



        return client;
    }

    public List<Client> getAllClients() {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM utilisateur WHERE type = 'client'";

        try (Connection conn = DBConnection.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Client client = new Client(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("email"),
                        rs.getString("mot_de_passe")
                );
                clients.add(client);
            }

        } catch (SQLException e) {
            System.err.println("Erreur récupération clients : " + e.getMessage());
        }

        return clients;
    }

    public boolean deleteClientById(int id) {
        String sql = "DELETE FROM utilisateur WHERE id = ? AND type = 'client'";

        try (Connection conn = DBConnection.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erreur suppression client : " + e.getMessage());
            return false;
        }
    }

    public boolean aDesCommandes(int idClient) {
        String sql = "SELECT COUNT(*) FROM commande WHERE id_utilisateur = ?";

        try (Connection conn = DBConnection.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idClient);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            System.err.println("Erreur vérification commandes client : " + e.getMessage());
        }

        return false;
    }

    public void updateClient(int id, String nom, String email, String motDePasse) {
        String sql = "UPDATE utilisateur SET nom = ?, email = ?, mot_de_passe = ? WHERE id = ? AND type = 'client'";

        try (Connection conn = DBConnection.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nom);
            stmt.setString(2, email);
            stmt.setString(3, motDePasse);
            stmt.setInt(4, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erreur mise à jour client : " + e.getMessage());
        }
    }



}

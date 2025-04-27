package DAO;

import Modele.Client;
import java.sql.*;

public class ClientDAO {

    // Crée un nouveau client dans la base de données
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

    // Recherche un client par son email et son mot de passe (connexion client)
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

    // Récupère tous les clients de la base (uniquement ceux de type 'client')
    public java.util.List<Client> getAllClients() {
        java.util.List<Client> clients = new java.util.ArrayList<>();
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

    // Supprime un client par son ID (seulement s'il est de type client)
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

    // Vérifie si un client a des commandes en base (avant suppression)
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

    // Met à jour les informations d'un client
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

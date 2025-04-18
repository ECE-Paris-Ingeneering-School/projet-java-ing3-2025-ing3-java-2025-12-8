package DAO;

import Modele.Client;
import java.sql.*;

public class ClientDAO {


    public void creerClient(String nom, String email, String motDePasse) {
        String sql = "INSERT INTO utilisateur (nom, email, mot_de_passe, type) VALUES (?, ?, ?, 'client')";

        try (Connection conn = DBConnection.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nom);
            stmt.setString(2, email);
            stmt.setString(3, motDePasse);

            stmt.executeUpdate();
            System.out.println("Compte client créé avec succès.");

        } catch (SQLException e) {
            System.err.println("Erreur lors de la création du compte : " + e.getMessage());
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
}

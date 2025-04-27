package DAO;

import Modele.Admin;
import java.sql.*;

public class AdminDAO {

    // Crée un nouvel admin dans la base de données
    public void creerAdmin(String nom, String email, String motDePasse) {
        String sql = "INSERT INTO utilisateur (nom, email, mot_de_passe, type) VALUES (?, ?, ?, 'admin')";
        try (Connection conn = DBConnection.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nom);
            stmt.setString(2, email);
            stmt.setString(3, motDePasse);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erreur création admin : " + e.getMessage());
        }
    }

    // Cherche un admin par son ID et son mot de passe
    public Admin getAdminByIdAndPassword(int id, String password) {
        Admin admin = null;
        String sql = "SELECT * FROM utilisateur WHERE id = ? AND mot_de_passe = ? AND type = 'admin'";

        try (Connection conn = DBConnection.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                admin = new Admin(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("email"),
                        rs.getString("mot_de_passe")
                );
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la connexion admin : " + e.getMessage());
        }

        return admin;
    }

    // Met à jour les informations d'un admin
    public void updateAdmin(int id, String nom, String email, String motDePasse) {
        String sql = "UPDATE utilisateur SET nom = ?, email = ?, mot_de_passe = ? WHERE id = ? AND type = 'admin'";

        try (Connection conn = DBConnection.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nom);
            stmt.setString(2, email);
            stmt.setString(3, motDePasse);
            stmt.setInt(4, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erreur mise à jour admin : " + e.getMessage());
        }
    }
}

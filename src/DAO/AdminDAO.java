package DAO;

import Modele.Admin;
import java.sql.*;

public class AdminDAO {

    public Admin getAdminByIdAndPassword(int id, String password) {
        Admin admin =  null;

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
            System.err.println("Erreur connexion admin : " + e.getMessage());
        }

        return admin;
    }

    public void creerAdmin(int id, String nom, String email, String motDePasse) {
        String sql = "INSERT INTO utilisateur (id, nom, email, mot_de_passe, type) VALUES (?, ?, ?, ?, 'admin')";
        try (Connection conn = DBConnection.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.setString(2, nom);
            stmt.setString(3, email);
            stmt.setString(4, motDePasse);

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur création admin : " + e.getMessage());
        }
    }

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

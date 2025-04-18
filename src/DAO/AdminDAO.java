package DAO;

import Modele.Admin;
import java.sql.*;

public class AdminDAO {

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
            System.err.println("Erreur connexion admin : " + e.getMessage());
        }

        return admin;
    }
}

package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestConnexion {
    public static void main(String[] args) {
        try {
            Connection conn = DBConnection.getConnexion();
            System.out.println("✅ Connexion réussie à la BDD");

            String sql = "SELECT nom FROM marque LIMIT 1";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                String nomMarque = rs.getString("nom");
                System.out.println("✔️ Première marque dans la base : " + nomMarque);
            } else {
                System.out.println("❌ Aucune marque trouvée.");
            }

            conn.close();
        } catch (SQLException e) {
            System.err.println("❌ Erreur de connexion : " + e.getMessage());
        }
    }
}

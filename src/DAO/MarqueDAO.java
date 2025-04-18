package DAO;

import Modele.Marque;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MarqueDAO {

    // Récupère toutes les marques
    public List<Marque> getAllMarques() {
        List<Marque> marques = new ArrayList<>();
        String sql = "SELECT * FROM marque";

        try (Connection conn = DBConnection.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                marques.add(new Marque(id, nom));
            }

        } catch (SQLException e) {
            System.err.println("Erreur récupération marques : " + e.getMessage());
        }

        return marques;
    }

    // Récupère une marque par son ID
    public Marque getMarqueById(int idRecherche) {
        Marque marque = null;
        String sql = "SELECT * FROM marque WHERE id = ?";

        try (Connection conn = DBConnection.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idRecherche);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                marque = new Marque(rs.getInt("id"), rs.getString("nom"));
            }

        } catch (SQLException e) {
            System.err.println("Erreur récupération d'une marque : " + e.getMessage());
        }

        return marque;
    }

    // Ajoute une nouvelle marque
    public void insertMarque(Marque marque) {
        String sql = "INSERT INTO marque (id, nom) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, marque.getId());
            stmt.setString(2, marque.getNom());
            stmt.executeUpdate();
            System.out.println("Marque ajoutée : " + marque.getNom());

        } catch (SQLException e) {
            System.err.println("Erreur insertion marque : " + e.getMessage());
        }
    }

    // Supprime une marque par ID
    public boolean deleteMarqueById(int id) {
        String sql = "DELETE FROM marque WHERE id = ?";

        try (Connection conn = DBConnection.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Marque supprimée (id = " + id + ")");
                return true;
            } else {
                System.out.println("Aucune marque trouvée avec l'id : " + id);
            }

        } catch (SQLException e) {
            System.err.println("Erreur suppression marque : " + e.getMessage());
        }

        return false;
    }

}

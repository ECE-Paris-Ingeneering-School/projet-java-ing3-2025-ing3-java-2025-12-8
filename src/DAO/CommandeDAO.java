package DAO;

import Modele.Article;
import Modele.Client;
import Modele.Panier;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CommandeDAO {

    public void enregistrerCommande(Client client, Panier panier) {
        String insertCommande = "INSERT INTO commande (id_utilisateur, date_commande, total) VALUES (?, ?, ?)";
        String insertLigne = "INSERT INTO ligne_commande (id_commande, id_article, quantite, prix) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnexion()) {
            conn.setAutoCommit(false);

            // 1. Créer la commande
            PreparedStatement stmtCommande = conn.prepareStatement(insertCommande, Statement.RETURN_GENERATED_KEYS);
            stmtCommande.setInt(1, client.getId());
            stmtCommande.setString(2, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            stmtCommande.setFloat(3, panier.getTotal());
            stmtCommande.executeUpdate();

            ResultSet rs = stmtCommande.getGeneratedKeys();
            if (rs.next()) {
                int idCommande = rs.getInt(1);

                // 2. Insérer les lignes
                PreparedStatement stmtLigne = conn.prepareStatement(insertLigne);
                for (Article article : panier.getArticles()) {
                    stmtLigne.setInt(1, idCommande);
                    stmtLigne.setInt(2, article.getId());
                    stmtLigne.setInt(3, 1); // 1 par défaut pour l’instant
                    stmtLigne.setFloat(4, article.getPrixUnitaire());
                    stmtLigne.addBatch();
                }
                stmtLigne.executeBatch();
                conn.commit();
                System.out.println("Commande enregistrée avec succès !");
            } else {
                conn.rollback();
                System.err.println("Erreur : ID commande non généré.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

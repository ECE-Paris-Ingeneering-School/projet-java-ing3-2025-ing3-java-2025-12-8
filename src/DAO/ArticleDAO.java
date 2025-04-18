package DAO;

import Modele.Article;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArticleDAO {

    // 1. Récupère tous les articles avec leur marque
    public List<Article> getAllArticles() {
        List<Article> articles = new ArrayList<>();
        String sql = """
            SELECT a.*, m.nom AS nom_marque
            FROM article a
            JOIN marque m ON a.id_marque = m.id
        """;

        try (Connection conn = DBConnection.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Article article = new Article(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("nom_marque"),
                        rs.getFloat("prix_unitaire"),
                        rs.getObject("prix_gros") != null ? rs.getFloat("prix_gros") : null,
                        rs.getObject("quantite_gros") != null ? rs.getInt("quantite_gros") : null,
                        rs.getInt("id_marque")
                );
                articles.add(article);
            }

        } catch (SQLException e) {
            System.err.println("Erreur récupération articles : " + e.getMessage());
        }

        return articles;
    }

    // 2. Ajouter un article
    public void insertArticle(Article article) {
        String sql = "INSERT INTO article (id, nom, marque, prix_unitaire, prix_gros, quantite_gros, id_marque) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, article.getId());
            stmt.setString(2, article.getNom());
            stmt.setString(3, article.getMarque());
            stmt.setFloat(4, article.getPrixUnitaire());

            if (article.getPrixGros() != null) {
                stmt.setFloat(5, article.getPrixGros());
            } else {
                stmt.setNull(5, Types.FLOAT);
            }

            if (article.getQuantiteGros() != null) {
                stmt.setInt(6, article.getQuantiteGros());
            } else {
                stmt.setNull(6, Types.INTEGER);
            }

            stmt.setInt(7, article.getIdMarque());

            stmt.executeUpdate();
            System.out.println("Article ajouté : " + article.getNom());

        } catch (SQLException e) {
            System.err.println("Erreur insertion article : " + e.getMessage());
        }
    }

    // 3. Supprimer un article par ID
    public boolean deleteArticleById(int id) {
        String sql = "DELETE FROM article WHERE id = ?";

        try (Connection conn = DBConnection.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {
            System.err.println("Erreur suppression article : " + e.getMessage());
            return false;
        }
    }

    // 4. Trouver un article par ID (manquant dans ta version précédente)
    public Article findById(int id) {
        Article article = null;
        String sql = "SELECT a.*, m.nom AS nom_marque FROM article a JOIN marque m ON a.id_marque = m.id WHERE a.id = ?";

        try (Connection conn = DBConnection.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                article = new Article(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("nom_marque"),
                        rs.getFloat("prix_unitaire"),
                        rs.getObject("prix_gros") != null ? rs.getFloat("prix_gros") : null,
                        rs.getObject("quantite_gros") != null ? rs.getInt("quantite_gros") : null,
                        rs.getInt("id_marque")
                );
            }

        } catch (SQLException e) {
            System.err.println("Erreur findById article : " + e.getMessage());
        }

        return article;
    }
}

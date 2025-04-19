package DAO;

import Modele.Article;
import Modele.Client;
import Modele.Commande;
import Modele.Panier;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CommandeDAO {

    public void enregistrerCommande(Client client, Panier panier) {
        String insertCommande = "INSERT INTO commande (id_utilisateur, date_commande, total) VALUES (?, ?, ?)";
        String insertLigne = "INSERT INTO ligne_commande (id_commande, id_article, quantite, prix) VALUES (?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement stmtCommande = null;
        PreparedStatement stmtLigne = null;

        try {
            conn = DBConnection.getConnexion();
            conn.setAutoCommit(false); // début transaction

            // Étape 1 : enregistrer la commande
            stmtCommande = conn.prepareStatement(insertCommande, Statement.RETURN_GENERATED_KEYS);
            stmtCommande.setInt(1, client.getId());
            stmtCommande.setString(2, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            stmtCommande.setFloat(3, panier.getTotal());
            stmtCommande.executeUpdate();

            ResultSet rs = stmtCommande.getGeneratedKeys();
            if (!rs.next()) {
                System.err.println("❌ Aucun ID généré pour la commande !");
                conn.rollback();
                return;
            }

            int idCommande = rs.getInt(1);

            // Étape 2 : lignes de commande
            stmtLigne = conn.prepareStatement(insertLigne);
            for (Map.Entry<Article, Integer> entry : panier.getArticles().entrySet()) {
                Article article = entry.getKey();
                int quantite = entry.getValue();

                float prix = (article.getPrixGros() != null && article.getQuantiteGros() != null && quantite >= article.getQuantiteGros())
                        ? article.getPrixGros()
                        : article.getPrixUnitaire();

                stmtLigne.setInt(1, idCommande);
                stmtLigne.setInt(2, article.getId());
                stmtLigne.setInt(3, quantite);
                stmtLigne.setFloat(4, prix);
                stmtLigne.addBatch();
            }

            stmtLigne.executeBatch();

            // Étape 3 : mise à jour du stock
            ArticleDAO articleDAO = new ArticleDAO();
            for (Map.Entry<Article, Integer> entry : panier.getArticles().entrySet()) {
                articleDAO.mettreAJourStock(conn, entry.getKey().getId(), entry.getValue());
            }

            conn.commit();
            System.out.println("✅ Commande enregistrée pour client ID = " + client.getId());

        } catch (SQLException e) {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.rollback();
                    System.err.println("⛔ ROLLBACK effectué");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (stmtCommande != null) stmtCommande.close();
                if (stmtLigne != null) stmtLigne.close();
                if (conn != null && !conn.isClosed()) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }




    public List<Commande> getCommandesByClientId(int idClient) {
        List<Commande> commandes = new ArrayList<>();
        String sql = "SELECT * FROM commande WHERE id_utilisateur = ? ORDER BY date_commande DESC";

        try (Connection conn = DBConnection.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idClient);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Commande commande = new Commande(rs.getInt("id_utilisateur"), rs.getFloat("total"));
                commande.setId(rs.getInt("id"));
                commande.setDateCommande(rs.getTimestamp("date_commande").toLocalDateTime());
                commandes.add(commande);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return commandes;
    }

    public List<Commande> getAllCommandes() {
        List<Commande> commandes = new ArrayList<>();
        String sql = "SELECT * FROM commande ORDER BY date_commande DESC";

        try (Connection conn = DBConnection.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Commande commande = new Commande(rs.getInt("id_utilisateur"), rs.getFloat("total"));
                commande.setId(rs.getInt("id"));
                commande.setDateCommande(rs.getTimestamp("date_commande").toLocalDateTime());
                commandes.add(commande);
            }

        } catch (SQLException e) {
            System.err.println("Erreur récupération de toutes les commandes : " + e.getMessage());
        }


        return commandes;
    }

    public Map<Article, Integer> getArticlesByCommandeId(int idCommande) {
        Map<Article, Integer> articles = new HashMap<>();

        String sql = """
        SELECT a.*, m.nom AS nom_marque, lc.quantite, lc.prix
        FROM ligne_commande lc
        JOIN article a ON lc.id_article = a.id
        JOIN marque m ON a.id_marque = m.id
        WHERE lc.id_commande = ?
    """;

        try (Connection conn = DBConnection.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCommande);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Article article = new Article(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("nom_marque"),
                        rs.getFloat("prix_unitaire"),
                        rs.getObject("prix_gros") != null ? rs.getFloat("prix_gros") : null,
                        rs.getObject("quantite_gros") != null ? rs.getInt("quantite_gros") : null,
                        rs.getInt("id_marque"),
                        rs.getInt("quantite_stock")
                );

                int quantite = rs.getInt("quantite");
                articles.put(article, quantite);
            }

        } catch (SQLException e) {
            System.err.println("Erreur récupération des articles commande : " + e.getMessage());
        }

        return articles;
    }

    public Map<String, Integer> getVentesParArticle() {
        Map<String, Integer> ventes = new LinkedHashMap<>();

        String sql = """
        SELECT a.nom, SUM(lc.quantite) AS total
        FROM ligne_commande lc
        JOIN article a ON lc.id_article = a.id
        GROUP BY a.nom
        ORDER BY total DESC
        LIMIT 10
    """;

        try (Connection conn = DBConnection.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ventes.put(rs.getString("nom"), rs.getInt("total"));
            }

        } catch (SQLException e) {
            System.err.println("Erreur stats ventes par article : " + e.getMessage());
        }

        return ventes;
    }

    public Map<String, Float> getChiffreAffairesParArticle() {
        Map<String, Float> stats = new LinkedHashMap<>();

        String sql = """
        SELECT a.nom, SUM(lc.quantite * lc.prix) AS chiffre_affaires
        FROM ligne_commande lc
        JOIN article a ON lc.id_article = a.id
        GROUP BY a.nom
        ORDER BY chiffre_affaires DESC
        LIMIT 10
    """;

        try (Connection conn = DBConnection.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                stats.put(rs.getString("nom"), rs.getFloat("chiffre_affaires"));
            }

        } catch (SQLException e) {
            System.err.println("Erreur récupération chiffre d'affaires par article : " + e.getMessage());
        }

        return stats;
    }

    public Map<String, Integer> getArticlesLesPlusVendus() {
        Map<String, Integer> stats = new LinkedHashMap<>();

        String sql = """
        SELECT a.nom, SUM(lc.quantite) AS total_vendu
        FROM ligne_commande lc
        JOIN article a ON lc.id_article = a.id
        GROUP BY a.nom
        ORDER BY total_vendu DESC
        LIMIT 10
    """;

        try (Connection conn = DBConnection.getConnexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                stats.put(rs.getString("nom"), rs.getInt("total_vendu"));
            }

        } catch (SQLException e) {
            System.err.println("Erreur récupération top ventes : " + e.getMessage());
        }

        return stats;
    }




}

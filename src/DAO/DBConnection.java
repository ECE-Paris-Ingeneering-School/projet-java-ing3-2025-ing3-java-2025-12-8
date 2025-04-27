package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // URL de connexion à la base de données MySQL
    private static final String URL = "jdbc:mysql://localhost:3306/shopping";

    // Nom d'utilisateur pour la base de données
    private static final String USER = "root";

    // Mot de passe pour la base de données
    private static final String PASSWORD = "nanterre";

    // Instance unique de connexion à la base de données
    private static Connection connexion;

    // Méthode pour obtenir la connexion à la base de données
    public static Connection getConnexion() throws SQLException {
        // Si la connexion est nulle ou fermée, en créer une nouvelle
        if (connexion == null || connexion.isClosed()) {
            connexion = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        // Retourner la connexion existante
        return connexion;
    }
}

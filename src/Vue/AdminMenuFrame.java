package Vue; // Déclare le package Vue

import Controleur.AdminControleur;
import Modele.Admin;

import javax.swing.*;
import java.awt.*;

public class AdminMenuFrame extends JFrame {

    private AdminControleur controleur; // Contrôleur pour gérer les actions du menu
    private Admin admin; // L'admin connecté

    public AdminMenuFrame(Admin admin) {
        this.admin = admin;

        // Configuration de la fenêtre
        setTitle("Menu Admin");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        controleur = new AdminControleur();
        controleur.setMenu(this); // Passe cette vue au contrôleur
        controleur.setAdmin(admin); // Passe l'admin connecté au contrôleur

        // Création du panel principal
        JPanel panel = new JPanel(new GridLayout(6, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Bouton pour gérer les articles
        JButton btnCatalogue = new JButton("Gérer les articles");
        btnCatalogue.addActionListener(e -> {
            this.setVisible(false);
            controleur.ouvrirCatalogue();
        });

        // Bouton pour voir les statistiques
        JButton btnStats = new JButton("Voir statistiques");
        btnStats.addActionListener(e -> {
            this.setVisible(false);
            controleur.ouvrirStats();
        });

        // Bouton pour voir les commandes clients
        JButton btnCommandes = new JButton("Voir commandes clients");
        btnCommandes.addActionListener(e -> {
            this.setVisible(false);
            controleur.ouvrirCommandes();
        });

        // Bouton pour gérer les clients
        JButton btnClients = new JButton("Gérer les clients");
        btnClients.addActionListener(e -> {
            this.setVisible(false);
            controleur.ouvrirClients();
        });

        // Bouton pour accéder au profil
        JButton btnCompte = new JButton("Mon compte");
        btnCompte.addActionListener(e -> new ProfilFrame(admin).setVisible(true));

        // Bouton pour se déconnecter
        JButton btnDeconnexion = new JButton("Déconnexion");
        btnDeconnexion.addActionListener(e -> {
            dispose();
            new ConnexionFrame().setVisible(true);
        });

        // Ajout des boutons au panel
        panel.add(btnCompte);
        panel.add(btnCatalogue);
        panel.add(btnStats);
        panel.add(btnCommandes);
        panel.add(btnClients);
        panel.add(btnDeconnexion);

        add(panel); // Ajout du panel à la fenêtre
    }

    // Méthode principale pour tester l'affichage de ce menu
    public static void main(String[] args) {
        Admin adminTest = new Admin(999, "Admin Test", "admin@example.com", "admin123");
        SwingUtilities.invokeLater(() -> new AdminMenuFrame(adminTest).setVisible(true));
    }
}

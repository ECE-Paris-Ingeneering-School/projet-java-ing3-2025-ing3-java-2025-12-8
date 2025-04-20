package Vue;

import Controleur.AdminControleur;
import Modele.Admin;

import javax.swing.*;
import java.awt.*;

public class AdminMenuFrame extends JFrame {

    private AdminControleur controleur;
    private Admin admin;

    public AdminMenuFrame(Admin admin) {
        this.admin = admin;

        setTitle("Menu Admin");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        controleur = new AdminControleur();
        controleur.setMenu(this);
        controleur.setAdmin(admin); // 👈 important


        JPanel panel = new JPanel(new GridLayout(6, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JButton btnCatalogue = new JButton("Gérer les articles");
        btnCatalogue.addActionListener(e -> {
            this.setVisible(false);
            controleur.ouvrirCatalogue();
        });

        JButton btnStats = new JButton("Voir statistiques");
        btnStats.addActionListener(e -> {
            this.setVisible(false);
            controleur.ouvrirStats();
        });

        JButton btnCommandes = new JButton("Voir commandes clients");
        btnCommandes.addActionListener(e -> {
            this.setVisible(false);
            controleur.ouvrirCommandes();
        });

        JButton btnClients = new JButton("Gérer les clients");
        btnClients.addActionListener(e -> {
            this.setVisible(false);
            controleur.ouvrirClients();
        });

        JButton btnCompte = new JButton("Mon compte");
        btnCompte.addActionListener(e -> new ProfilFrame(admin).setVisible(true));

        JButton btnDeconnexion = new JButton("Déconnexion");
        btnDeconnexion.addActionListener(e -> {
            dispose();
            new ConnexionFrame().setVisible(true);
        });

        panel.add(btnCompte);
        panel.add(btnCatalogue);
        panel.add(btnStats);
        panel.add(btnCommandes);
        panel.add(btnClients);
        panel.add(btnDeconnexion);

        add(panel);
    }
    public static void main(String[] args) {
        Admin adminTest = new Admin(999, "Admin Test", "admin@example.com", "admin123");
        SwingUtilities.invokeLater(() -> new AdminMenuFrame(adminTest).setVisible(true));
    }

}

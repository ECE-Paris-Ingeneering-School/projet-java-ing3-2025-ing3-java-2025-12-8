package Vue;

import Controleur.AdminControleur;

import javax.swing.*;
import java.awt.*;

public class AdminMenuFrame extends JFrame {

    private AdminControleur controleur;

    public AdminMenuFrame() {
        setTitle("Menu Admin");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        controleur = new AdminControleur();
        controleur.setMenu(this); // on donne au contrôleur une référence au menu

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
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

        JButton btnDeconnexion = new JButton("Déconnexion");
        btnDeconnexion.addActionListener(e -> {
            dispose(); // ferme le menu admin
            new ConnexionFrame().setVisible(true); // retourne à la page de connexion
        });


        panel.add(btnCatalogue);
        panel.add(btnStats);
        panel.add(btnCommandes);
        panel.add(btnDeconnexion);

        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminMenuFrame().setVisible(true));
    }
}

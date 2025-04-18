package Vue;

import Modele.Client;
import Modele.Panier;

import javax.swing.*;
import java.awt.*;

public class MenuClientFrame extends JFrame {

    private Client client;
    private Panier panier;

    public MenuClientFrame(Client client) {
        this.client = client;
        this.panier = new Panier(); // panier partagé

        setTitle("Espace Client - Bienvenue " + client.getNom());
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 1, 15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel labelBienvenue = new JLabel("Bienvenue dans votre espace client, " + client.getNom(), JLabel.CENTER);
        panel.add(labelBienvenue);

        JButton btnCatalogue = new JButton("Voir le catalogue");
        btnCatalogue.addActionListener(e -> new CatalogueClientFrame(client, panier).setVisible(true));
        panel.add(btnCatalogue);

        JButton btnPanier = new JButton("Mon panier");
        btnPanier.addActionListener(e -> new PanierFrame(client, panier).setVisible(true));
        panel.add(btnPanier);

        JButton btnHistorique = new JButton("Mes commandes");
        btnHistorique.addActionListener(e -> JOptionPane.showMessageDialog(this, "Historique à venir !"));
        panel.add(btnHistorique);

        JButton btnDeconnexion = new JButton("Déconnexion");
        btnDeconnexion.addActionListener(e -> {
            dispose();
            new ConnexionClientFrame().setVisible(true);
        });
        panel.add(btnDeconnexion);

        add(panel);
    }
}

package Vue; // Déclare le package Vue

import Modele.Client;
import Modele.Panier;

import javax.swing.*;
import java.awt.*;

public class ClientMenuFrame extends JFrame {

    private Client client; // Client connecté
    private Panier panier; // Panier associé au client

    public ClientMenuFrame(Client client, Panier panier) {
        this.client = client;
        this.panier = panier;

        // Configuration de la fenêtre
        setTitle("Espace Client - Bienvenue " + client.getNom());
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel panel = new JPanel(new GridLayout(4, 1, 15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Message de bienvenue
        JLabel labelBienvenue = new JLabel("Bienvenue dans votre espace client, " + client.getNom(), JLabel.CENTER);
        panel.add(labelBienvenue);

        // Bouton pour accéder au catalogue
        JButton btnCatalogue = new JButton("Voir le catalogue");
        btnCatalogue.addActionListener(e -> new ClientCatalogueFrame(client, panier).setVisible(true));
        panel.add(btnCatalogue);

        // Bouton pour accéder au panier
        JButton btnPanier = new JButton("Mon panier");
        btnPanier.addActionListener(e -> new PanierFrame(client, panier).setVisible(true));
        panel.add(btnPanier);

        // Bouton pour se déconnecter
        JButton btnDeconnexion = new JButton("Déconnexion");
        btnDeconnexion.addActionListener(e -> {
            dispose();
            new ConnexionFrame().setVisible(true);
        });
        panel.add(btnDeconnexion);

        // Bouton pour voir ses commandes
        JButton btnCommandes = new JButton("Mes commandes");
        btnCommandes.addActionListener(e -> new ClientCommandesFrame(client).setVisible(true));
        panel.add(btnCommandes);

        // Bouton pour accéder à son compte/profil
        JButton btnCompte = new JButton("Mon compte");
        btnCompte.addActionListener(e -> new ProfilFrame(client).setVisible(true));
        panel.add(btnCompte);

        add(panel); // Ajout du panel à la fenêtre
    }
}

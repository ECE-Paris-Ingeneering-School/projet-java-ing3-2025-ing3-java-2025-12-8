package Vue;

import Controleur.ClientControleur;
import Modele.Client;
import Modele.Panier;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PaiementFrame extends JFrame {

    private Client client; // Client connecté
    private Panier panier; // Panier du client
    private JLabel lblMontant; // Label pour afficher le montant total
    private ClientControleur controleur; // Contrôleur pour gérer les actions de paiement

    public PaiementFrame(Client client, Panier panier) {
        this.client = client;
        this.panier = panier;
        this.controleur = new ClientControleur();

        // Configuration de la fenêtre
        setTitle("Paiement - Espace Client");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Affiche le montant total à payer
        lblMontant = new JLabel("Total à payer : " + panier.getTotal() + " €", JLabel.CENTER);
        lblMontant.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(lblMontant, BorderLayout.CENTER);

        // Bouton pour effectuer le paiement
        JButton btnPayer = new JButton("Payer");
        btnPayer.setFont(new Font("Arial", Font.BOLD, 16));
        btnPayer.addActionListener(e -> effectuerPaiement());

        panel.add(btnPayer, BorderLayout.SOUTH);

        add(panel); // Ajout du panel à la fenêtre
    }

    // Méthode appelée lorsqu'on clique sur "Payer"
    private void effectuerPaiement() {
        if (panier.estVide()) { // Vérifie si le panier est vide
            JOptionPane.showMessageDialog(this, "Votre panier est vide.");
            return;
        }

        // Affiche la facture avant d'enregistrer la commande
        new FactureFrame(client, panier).setVisible(true);

        // Enregistre la commande et vide le panier
        controleur.passerCommande(client, panier);

        JOptionPane.showMessageDialog(this, "Paiement effectué avec succès !");
        dispose(); // Ferme la fenêtre après le paiement
    }
}

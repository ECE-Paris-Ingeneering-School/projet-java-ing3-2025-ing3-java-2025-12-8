package Vue;

import Controleur.ClientControleur;
import Modele.Client;
import Modele.Panier;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PaiementFrame extends JFrame {

    private Client client;
    private Panier panier;
    private JLabel lblMontant;
    private ClientControleur controleur;

    public PaiementFrame(Client client, Panier panier) {
        this.client = client;
        this.panier = panier;
        this.controleur = new ClientControleur();

        setTitle("Paiement - Espace Client");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        lblMontant = new JLabel("Total à payer : " + panier.getTotal() + " €", JLabel.CENTER);
        lblMontant.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(lblMontant, BorderLayout.CENTER);

        JButton btnPayer = new JButton("Payer");
        btnPayer.setFont(new Font("Arial", Font.BOLD, 16));
        btnPayer.addActionListener(e -> effectuerPaiement());

        panel.add(btnPayer, BorderLayout.SOUTH);

        add(panel);
    }

    private void effectuerPaiement() {
        if (panier.estVide()) {
            JOptionPane.showMessageDialog(this, "Votre panier est vide.");
            return;
        }

        // ✅ Afficher la facture AVANT d'enregistrer la commande (et donc de vider le panier)
        new FactureFrame(client, panier).setVisible(true);

        // ✅ Ensuite enregistrer + vider
        controleur.passerCommande(client, panier);

        JOptionPane.showMessageDialog(this, "Paiement effectué avec succès !");
        dispose();
    }

}

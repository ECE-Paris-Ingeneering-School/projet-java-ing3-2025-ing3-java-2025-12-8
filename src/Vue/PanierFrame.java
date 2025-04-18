package Vue;

import Modele.Article;
import Modele.Client;
import Modele.Panier;

import javax.swing.*;
import java.awt.*;

public class PanierFrame extends JFrame {

    private Client client;
    private Panier panier;
    private DefaultListModel<String> modelListe;

    public PanierFrame(Client client, Panier panier) {
        this.client = client;
        this.panier = panier;

        setTitle("Mon Panier");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel label = new JLabel("Panier de " + client.getNom(), JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(label, BorderLayout.NORTH);

        modelListe = new DefaultListModel<>();
        for (Article article : panier.getArticles()) {
            modelListe.addElement(article.getNom() + " - " + article.getPrixUnitaire() + "€");
        }

        JList<String> list = new JList<>(modelListe);
        panel.add(new JScrollPane(list), BorderLayout.CENTER);

        JPanel bas = new JPanel(new GridLayout(1, 3, 10, 10));

        JButton btnCommander = new JButton("Passer commande");
        btnCommander.addActionListener(e -> JOptionPane.showMessageDialog(this, "Commande simulée (à venir)"));
        bas.add(btnCommander);

        JButton btnVider = new JButton("Vider le panier");
        btnVider.addActionListener(e -> viderPanier());
        bas.add(btnVider);

        JButton btnRetour = new JButton("Retour au menu");
        btnRetour.addActionListener(e -> dispose());
        bas.add(btnRetour);

        panel.add(bas, BorderLayout.SOUTH);

        add(panel);
    }

    private void viderPanier() {
        modelListe.clear();
        panier.vider();
        JOptionPane.showMessageDialog(this, "Panier vidé");
    }
}

package Vue; // Déclare le package Vue

import Modele.Article;
import Modele.Client;
import Modele.Panier;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class FactureFrame extends JFrame {

    public FactureFrame(Client client, Panier panier) {
        // Configuration de la fenêtre
        setTitle("Facture");
        setSize(500, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Zone de texte pour afficher la facture
        JTextArea factureArea = new JTextArea();
        factureArea.setEditable(false);
        factureArea.setFont(new Font("Monospaced", Font.PLAIN, 14)); // Police alignée type facture
        JScrollPane scrollPane = new JScrollPane(factureArea);

        // Construction du contenu de la facture
        StringBuilder sb = new StringBuilder();

        sb.append("=========== FACTURE CLIENT ===========\n\n");
        sb.append("Client ID : ").append(client.getId()).append("\n");
        sb.append("Date      : ").append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))).append("\n\n");
        sb.append(String.format("%-25s %-10s %-10s %-10s\n", "Article", "Qté", "Prix", "Total"));

        float total = 0f; // Total général à payer

        // Boucle sur les articles du panier
        for (Map.Entry<Article, Integer> entry : panier.getArticles().entrySet()) {
            Article a = entry.getKey();
            int qte = entry.getValue();

            float ligneTotal = 0f;
            String prixDetail;

            // Gestion du prix en fonction des quantités (gros ou unitaire)
            if (a.getPrixGros() != null && a.getQuantiteGros() != null && a.getQuantiteGros() > 0 && qte >= a.getQuantiteGros()) {
                int nbGroupes = qte / a.getQuantiteGros();
                int reste = qte % a.getQuantiteGros();

                float prixGroupes = nbGroupes * a.getPrixGros();
                float prixUnitaires = reste * a.getPrixUnitaire();
                ligneTotal = prixGroupes + prixUnitaires;

                prixDetail = nbGroupes + "x" + a.getQuantiteGros() + " à " + a.getPrixGros() +
                        (reste > 0 ? " + " + reste + " à " + a.getPrixUnitaire() : "");
            } else {
                ligneTotal = qte * a.getPrixUnitaire();
                prixDetail = qte + " x " + a.getPrixUnitaire();
            }

            total += ligneTotal; // Ajoute au total final

            // Ajoute une ligne formatée pour cet article
            sb.append(String.format("%-25s %-10d %-10s %-10.2f\n", a.getNom(), qte, prixDetail, ligneTotal));
        }

        // Affiche le total final
        sb.append("\n======================================\n");
        sb.append(String.format("TOTAL À PAYER : %.2f €\n", total));
        sb.append("======================================\n");

        // Met le texte dans la zone d'affichage
        factureArea.setText(sb.toString());
        add(scrollPane); // Ajout du scrollpane à la fenêtre
    }
}

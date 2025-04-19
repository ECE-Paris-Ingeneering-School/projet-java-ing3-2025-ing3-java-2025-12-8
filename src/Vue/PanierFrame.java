package Vue;

import Controleur.ClientControleur;
import Modele.Article;
import Modele.Client;
import Modele.Panier;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Map;

public class PanierFrame extends JFrame {

    private Client client;
    private Panier panier;
    private JTable table;
    private JLabel lblTotal;
    private DefaultTableModel model;
    private ClientControleur controleur;

    public PanierFrame(Client client, Panier panier) {
        this.client = client;
        this.panier = panier;
        this.controleur = new ClientControleur();

        setTitle("Panier - Client");
        setSize(650, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        model = new DefaultTableModel(new String[]{"ID", "Nom", "Marque", "Détail prix", "Quantité", "Total (€)"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };


        table = new JTable(model);
        table.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(table);

        lblTotal = new JLabel("Total : " + panier.getTotal() + " €");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 16));

        JButton btnModifierQuantite = new JButton("Modifier quantité");
        btnModifierQuantite.addActionListener(e -> modifierQuantite());

        JButton btnSupprimer = new JButton("Supprimer l'article");
        btnSupprimer.addActionListener(e -> supprimerArticle());

        JButton btnValider = new JButton("Valider la commande");
        btnValider.addActionListener(e -> validerCommande());

        JButton btnRetour = new JButton("Retour");
        btnRetour.addActionListener(e -> dispose());

        JPanel bas = new JPanel();
        bas.add(lblTotal);
        bas.add(btnModifierQuantite);
        bas.add(btnSupprimer);
        bas.add(btnValider);
        bas.add(btnRetour);

        add(scrollPane, BorderLayout.CENTER);
        add(bas, BorderLayout.SOUTH);

        rafraichirTableau();
    }

    private void rafraichirTableau() {
        model.setRowCount(0);
        for (Map.Entry<Article, Integer> entry : panier.getArticles().entrySet()) {
            Article a = entry.getKey();
            int qte = entry.getValue();

            String detailPrix;
            float totalArticle;

            if (a.getPrixGros() != null && a.getQuantiteGros() != null && a.getQuantiteGros() > 0 && qte >= a.getQuantiteGros()) {
                int groupes = qte / a.getQuantiteGros();
                int reste = qte % a.getQuantiteGros();

                float prixGroupes = groupes * a.getPrixGros();
                float prixRestes = reste * a.getPrixUnitaire();

                detailPrix = groupes + "x" + a.getQuantiteGros() + " à " + a.getPrixGros() + " €"
                        + (reste > 0 ? " + " + reste + " à " + a.getPrixUnitaire() + " €" : "");

                totalArticle = prixGroupes + prixRestes;

            } else {
                detailPrix = qte + " x " + a.getPrixUnitaire() + " €";
                totalArticle = qte * a.getPrixUnitaire();
            }

            model.addRow(new Object[]{
                    a.getId(),
                    a.getNom(),
                    a.getMarque(),
                    detailPrix,
                    qte,
                    totalArticle
            });
        }

        lblTotal.setText("Total : " + panier.getTotal() + " €");
    }


    private void modifierQuantite() {
        int row = table.getSelectedRow();
        if (row != -1) {
            int id = (int) model.getValueAt(row, 0);
            Article article = controleur.getArticleById(id);
            if (article != null) {
                String input = JOptionPane.showInputDialog(this, "Nouvelle quantité :");
                try {
                    int nouvelleQuantite = Integer.parseInt(input);
                    if (nouvelleQuantite <= 0) {
                        panier.retirerArticle(article);
                    } else if (nouvelleQuantite > article.getQuantiteStock()) {
                        JOptionPane.showMessageDialog(this, "Stock insuffisant.");
                        return;
                    } else {
                        panier.modifierQuantite(article, nouvelleQuantite);
                    }
                    rafraichirTableau();
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Saisie invalide.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un article.");
        }
    }

    private void supprimerArticle() {
        int row = table.getSelectedRow();
        if (row != -1) {
            int id = (int) model.getValueAt(row, 0);
            Article article = controleur.getArticleById(id);
            panier.retirerArticle(article);
            rafraichirTableau();
        } else {
            JOptionPane.showMessageDialog(this, "Sélectionnez un article à supprimer.");
        }
    }

    private void validerCommande() {
        if (panier.estVide()) {
            JOptionPane.showMessageDialog(this, "Votre panier est vide.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Souhaitez-vous valider votre commande et passer au paiement ?",
                "Confirmation", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            new PaiementFrame(client, panier).setVisible(true);
            dispose(); // ferme la fenêtre panier
        }
    }

}

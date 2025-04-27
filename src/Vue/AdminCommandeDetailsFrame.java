package Vue; // Déclare le package Vue

import DAO.CommandeDAO;
import Modele.Article;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Map;

public class AdminCommandeDetailsFrame extends JFrame {

    private JTable table; // Tableau pour afficher les détails de la commande
    private DefaultTableModel model; // Modèle de données du tableau
    private int idCommande; // ID de la commande à afficher
    private CommandeDAO commandeDAO; // DAO pour récupérer les données de la commande

    public AdminCommandeDetailsFrame(int idCommande) {
        this.idCommande = idCommande;
        this.commandeDAO = new CommandeDAO();

        // Configuration de la fenêtre
        setTitle("Détails de la commande n°" + idCommande);
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Création du modèle du tableau
        model = new DefaultTableModel(new String[]{"ID", "Nom", "Marque", "Prix", "Quantité"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Empêche la modification des cellules
            }
        };

        table = new JTable(model);
        table.setRowHeight(25); // Hauteur des lignes du tableau
        JScrollPane scrollPane = new JScrollPane(table);

        // Bouton pour fermer la fenêtre
        JButton btnRetour = new JButton("Retour");
        btnRetour.addActionListener(e -> dispose());

        // Panel pour le bouton en bas
        JPanel bas = new JPanel();
        bas.add(btnRetour);

        // Ajout des composants à la fenêtre
        add(scrollPane, BorderLayout.CENTER);
        add(bas, BorderLayout.SOUTH);

        // Chargement des lignes de commande
        chargerLignesCommande();
    }

    // Méthode pour charger les articles de la commande dans le tableau
    private void chargerLignesCommande() {
        model.setRowCount(0); // Vide le tableau

        Map<Article, Integer> lignes = commandeDAO.getArticlesByCommandeId(idCommande);
        for (Map.Entry<Article, Integer> entry : lignes.entrySet()) {
            Article a = entry.getKey(); // Article associé
            int quantite = entry.getValue(); // Quantité commandée

            // Déterminer le prix à appliquer selon la quantité (prix gros ou prix unitaire)
            float prixApplique = (a.getPrixGros() != null && a.getQuantiteGros() != null && quantite >= a.getQuantiteGros())
                    ? a.getPrixGros()
                    : a.getPrixUnitaire();

            // Ajout d'une ligne au tableau
            model.addRow(new Object[]{
                    a.getId(),
                    a.getNom(),
                    a.getMarque(),
                    prixApplique,
                    quantite
            });
        }
    }
}

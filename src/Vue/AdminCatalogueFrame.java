package Vue; // Déclare le package Vue

import Controleur.AdminControleur;
import DAO.ArticleDAO;
import Modele.Article;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminCatalogueFrame extends JFrame {

    private JTable tableArticles; // Tableau pour afficher les articles
    private DefaultTableModel model; // Modèle de données pour le tableau
    private JTextField tfRecherche; // Champ de recherche
    private AdminControleur controleur; // Contrôleur pour gérer les actions

    public AdminCatalogueFrame(AdminControleur controleur) {
        this.controleur = controleur;

        // Configuration de la fenêtre
        setTitle("Catalogue - Admin");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Création du panel principal
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Header : champ de recherche + titre
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));

        // Barre de recherche
        JPanel recherchePanel = new JPanel(new BorderLayout());
        recherchePanel.add(new JLabel("Rechercher : "), BorderLayout.WEST);

        tfRecherche = new JTextField();
        recherchePanel.add(tfRecherche, BorderLayout.CENTER);
        tfRecherche.addActionListener(e -> filtrerArticles()); // Action lors de la recherche

        // Titre du tableau
        JLabel label = new JLabel("Catalogue des articles", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Ajout de la recherche et du titre au header
        headerPanel.add(recherchePanel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Espace entre recherche et titre
        headerPanel.add(label);

        panel.add(headerPanel, BorderLayout.NORTH);

        // Tableau des articles
        String[] columns = {"ID", "Nom", "Marque", "Prix Unitaire (€)", "Prix Gros (€)", "Quantité pour prix gros", "Stock"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Empêche la modification directe des cellules
            }
        };

        tableArticles = new JTable(model);
        tableArticles.setRowHeight(25); // Hauteur des lignes
        JScrollPane scrollPane = new JScrollPane(tableArticles);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Chargement des articles dans le tableau
        chargerArticles();

        // Panel du bas avec le bouton Retour
        JPanel panelBas = new JPanel(new FlowLayout());

        JButton btnRetour = new JButton("Retour");
        btnRetour.addActionListener(e -> {
            controleur.retournerAuMenu(); // Retour au menu principal
            dispose(); // Ferme la fenêtre actuelle
        });

        panelBas.add(btnRetour);
        panel.add(panelBas, BorderLayout.SOUTH);

        add(panel); // Ajout du panel principal à la fenêtre
    }

    // Méthode pour charger tous les articles dans le tableau
    private void chargerArticles() {
        model.setRowCount(0); // Vide le tableau
        List<Article> articles = new ArticleDAO().getAllArticles();
        for (Article a : articles) {
            model.addRow(new Object[]{
                    a.getId(),
                    a.getNom(),
                    a.getMarque(),
                    a.getPrixUnitaire(),
                    a.getPrixGros() != null ? a.getPrixGros() : "-",
                    a.getQuantiteGros() != null ? a.getQuantiteGros() : "-",
                    a.getQuantiteStock()
            });
        }
    }

    // Méthode pour filtrer les articles selon la recherche
    private void filtrerArticles() {
        String recherche = tfRecherche.getText().toLowerCase();
        model.setRowCount(0); // Vide le tableau avant de remplir avec les résultats filtrés

        for (Article a : new ArticleDAO().getAllArticles()) {
            if (a.getNom().toLowerCase().contains(recherche) ||
                    a.getMarque().toLowerCase().contains(recherche)) {

                model.addRow(new Object[]{
                        a.getId(),
                        a.getNom(),
                        a.getMarque(),
                        a.getPrixUnitaire(),
                        a.getPrixGros() != null ? a.getPrixGros() : "-",
                        a.getQuantiteGros() != null ? a.getQuantiteGros() : "-",
                        a.getQuantiteStock()
                });
            }
        }
    }
}

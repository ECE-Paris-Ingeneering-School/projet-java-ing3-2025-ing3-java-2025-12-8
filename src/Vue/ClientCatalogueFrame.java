package Vue; // Déclare le package Vue

import DAO.ArticleDAO;
import Modele.Article;
import Modele.Client;
import Modele.Panier;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ClientCatalogueFrame extends JFrame {

    private Client client; // Client connecté
    private Panier panier; // Panier associé au client
    private JTable tableArticles; // Tableau pour afficher les articles
    private DefaultTableModel model; // Modèle de données pour le tableau
    private JSpinner spinnerQuantite; // Sélecteur de quantité
    private JTextField tfRecherche; // Champ de recherche d'articles

    public ClientCatalogueFrame(Client client, Panier panier) {
        this.client = client;
        this.panier = panier;

        // Configuration de la fenêtre
        setTitle("Catalogue - Espace Client");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Header : recherche + titre
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));

        // Barre de recherche
        JPanel recherchePanel = new JPanel(new BorderLayout());
        recherchePanel.add(new JLabel("Rechercher : "), BorderLayout.WEST);
        tfRecherche = new JTextField();
        recherchePanel.add(tfRecherche, BorderLayout.CENTER);
        tfRecherche.addActionListener(e -> filtrerArticles());

        // Titre du catalogue
        JLabel label = new JLabel("Catalogue des articles", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Ajout de la recherche et du titre au header
        headerPanel.add(recherchePanel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        headerPanel.add(label);

        panel.add(headerPanel, BorderLayout.NORTH);

        // Tableau pour afficher les articles
        String[] columns = {"ID", "Nom", "Marque", "Prix Unitaire (€)", "Prix Gros (€)", "Quantité pour prix gros", "Stock"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Empêche la modification des cellules
            }
        };

        tableArticles = new JTable(model);
        tableArticles.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(tableArticles);
        panel.add(scrollPane, BorderLayout.CENTER);

        chargerArticles(); // Chargement initial des articles

        // Panel bas : sélection de la quantité et boutons
        JPanel panelBas = new JPanel(new FlowLayout());

        panelBas.add(new JLabel("Quantité :"));
        spinnerQuantite = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        panelBas.add(spinnerQuantite);

        JButton btnAjouter = new JButton("Ajouter au panier");
        btnAjouter.addActionListener(e -> ajouterAuPanier());
        panelBas.add(btnAjouter);

        JButton btnRetour = new JButton("Retour");
        btnRetour.addActionListener(e -> dispose());
        panelBas.add(btnRetour);

        panel.add(panelBas, BorderLayout.SOUTH);

        add(panel); // Ajout du panel à la fenêtre
    }

    // Charge les articles dans le tableau
    private void chargerArticles() {
        model.setRowCount(0);
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

    // Filtre les articles selon le texte saisi dans la recherche
    private void filtrerArticles() {
        String recherche = tfRecherche.getText().toLowerCase();
        model.setRowCount(0);

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

    // Ajoute l'article sélectionné au panier
    private void ajouterAuPanier() {
        int selectedRow = tableArticles.getSelectedRow();
        if (selectedRow != -1) {
            int idArticle = (int) tableArticles.getValueAt(selectedRow, 0);
            int quantite = (int) spinnerQuantite.getValue();

            Article article = new ArticleDAO().findById(idArticle);
            if (article != null) {
                if (quantite <= article.getQuantiteStock()) {
                    panier.ajouterArticle(article, quantite);
                    JOptionPane.showMessageDialog(this,
                            quantite + " x " + article.getNom() + " ajouté(s) au panier.");
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Stock insuffisant. Il reste seulement " + article.getQuantiteStock() + " en stock.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un article.");
        }
    }
}

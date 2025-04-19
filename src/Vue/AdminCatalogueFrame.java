package Vue;

import DAO.ArticleDAO;
import Modele.Article;
import Controleur.AdminControleur;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminCatalogueFrame extends JFrame {

    private JTable tableArticles;
    private DefaultTableModel model;
    private ArticleDAO articleDAO;
    private AdminControleur controleur;

    public AdminCatalogueFrame(AdminControleur controleur) {
        this.controleur = controleur;
        setTitle("Catalogue - Espace Admin");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        articleDAO = new ArticleDAO();

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel label = new JLabel("Catalogue des articles", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(label, BorderLayout.NORTH);

        // Tableau des articles
        String[] columns = {"ID", "Nom", "Marque", "Prix Unitaire (€)", "Prix Gros (€)", "Quantité pour prix gros", "Stock"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableArticles = new JTable(model);
        tableArticles.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(tableArticles);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Bas : boutons
        JPanel panelBas = new JPanel(new FlowLayout());

        JButton btnAjouter = new JButton("Ajouter un article");
        btnAjouter.addActionListener(e -> {
            new AjoutArticleFrame().setVisible(true);
            dispose(); // referme la fenêtre pour forcer un rafraîchissement propre
        });
        panelBas.add(btnAjouter);

        JButton btnSupprimer = new JButton("Supprimer l'article");
        btnSupprimer.addActionListener(e -> supprimerArticle());
        panelBas.add(btnSupprimer);

        JButton btnRetour = new JButton("Retour");
        btnRetour.addActionListener(e -> {
            controleur.retournerAuMenu(); // réaffiche le menu admin
            dispose(); // ferme le catalogue
        });
        panelBas.add(btnRetour);

        panel.add(panelBas, BorderLayout.SOUTH);

        add(panel);
        chargerArticles();
    }

    private void chargerArticles() {
        model.setRowCount(0);
        List<Article> articles = articleDAO.getAllArticles();

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

    private void supprimerArticle() {
        int row = tableArticles.getSelectedRow();
        if (row != -1) {
            int id = (int) tableArticles.getValueAt(row, 0);
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Voulez-vous vraiment supprimer cet article ?", "Confirmation",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                boolean success = articleDAO.deleteArticleById(id);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Article supprimé.");
                    chargerArticles();
                } else {
                    JOptionPane.showMessageDialog(this, "Erreur : article non trouvé.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un article à supprimer.");
        }
    }
}

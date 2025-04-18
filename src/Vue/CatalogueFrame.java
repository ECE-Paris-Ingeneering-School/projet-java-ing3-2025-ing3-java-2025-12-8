package Vue;

import DAO.ArticleDAO;
import Modele.Article;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CatalogueFrame extends JFrame {

    private JTable tableArticles;
    private ArticleDAO articleDAO;

    public CatalogueFrame() {
        articleDAO = new ArticleDAO();
        setTitle("Catalogue des Articles");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ---------- EN-TÊTE ----------
        JLabel title = new JLabel("Catalogue des Articles");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

        // ---------- TABLE ----------
        String[] colonnes = {"ID", "Nom", "Marque", "Prix Unitaire", "Prix Gros", "Quantité Gros", "ID Marque"};
        DefaultTableModel model = new DefaultTableModel(colonnes, 0);

        List<Article> articles = articleDAO.getAllArticles();
        for (Article article : articles) {
            Object[] row = {
                    article.getId(),
                    article.getNom(),
                    article.getMarque(),
                    article.getPrixUnitaire() + " €",
                    article.getPrixGros() != null ? article.getPrixGros() + " €" : "-",
                    article.getQuantiteGros() != null ? article.getQuantiteGros() : "-",
                    article.getIdMarque()
            };
            model.addRow(row);
        }

        tableArticles = new JTable(model);
        tableArticles.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tableArticles.setRowHeight(28);
        tableArticles.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 15));

        // Centrer les cellules
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tableArticles.getColumnCount(); i++) {
            tableArticles.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(tableArticles);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        // ---------- MAIN PANEL ----------
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.add(title, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CatalogueFrame().setVisible(true));
    }
}

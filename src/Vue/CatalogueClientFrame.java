package Vue;

import DAO.ArticleDAO;
import Modele.Article;
import Modele.Client;
import Modele.Panier;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CatalogueClientFrame extends JFrame {

    private Client client;
    private Panier panier;
    private JTable tableArticles;

    public CatalogueClientFrame(Client client, Panier panier) {
        this.client = client;
        this.panier = panier;

        setTitle("Catalogue - Espace Client");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel label = new JLabel("Catalogue des articles", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(label, BorderLayout.NORTH);

        ArticleDAO articleDAO = new ArticleDAO();
        List<Article> articles = articleDAO.getAllArticles();

        String[] columns = {"ID", "Nom", "Marque", "Prix Unitaire (€)", "Prix Gros (€)", "Quantité pour prix gros"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        for (Article a : articles) {
            model.addRow(new Object[]{
                    a.getId(),
                    a.getNom(),
                    a.getMarque(),
                    a.getPrixUnitaire(),
                    a.getPrixGros() != null ? a.getPrixGros() : "-",
                    a.getQuantiteGros() != null ? a.getQuantiteGros() : "-"
            });
        }

        tableArticles = new JTable(model);
        tableArticles.setRowHeight(25);
        panel.add(new JScrollPane(tableArticles), BorderLayout.CENTER);

        // Bouton ajouter au panier
        JButton btnAjouter = new JButton("Ajouter l'article sélectionné au panier");
        btnAjouter.addActionListener(e -> ajouterAuPanier());
        panel.add(btnAjouter, BorderLayout.SOUTH);

        add(panel);
    }

    private void ajouterAuPanier() {
        int selectedRow = tableArticles.getSelectedRow();
        if (selectedRow != -1) {
            int idArticle = (int) tableArticles.getValueAt(selectedRow, 0);
            ArticleDAO dao = new ArticleDAO();
            Article article = dao.findById(idArticle); // à implémenter dans ArticleDAO si pas encore fait
            if (article != null) {
                panier.ajouterArticle(article);
                JOptionPane.showMessageDialog(this, "Article ajouté au panier !");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un article.");
        }
    }
}

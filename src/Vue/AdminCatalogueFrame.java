package Vue;

import Controleur.AdminControleur;
import DAO.ArticleDAO;
import Modele.Article;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminCatalogueFrame extends JFrame {

    private JTable tableArticles;
    private DefaultTableModel model;
    private JTextField tfRecherche;
    private AdminControleur controleur;

    public AdminCatalogueFrame(AdminControleur controleur) {
        this.controleur = controleur;

        setTitle("Catalogue - Admin");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // ✅ Header combiné : recherche + titre
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));

        // Barre de recherche
        JPanel recherchePanel = new JPanel(new BorderLayout());
        recherchePanel.add(new JLabel("Rechercher : "), BorderLayout.WEST);

        tfRecherche = new JTextField();
        recherchePanel.add(tfRecherche, BorderLayout.CENTER);
        tfRecherche.addActionListener(e -> filtrerArticles());

        // Titre
        JLabel label = new JLabel("Catalogue des articles", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(recherchePanel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 10))); // espace
        headerPanel.add(label);

        panel.add(headerPanel, BorderLayout.NORTH);

        // ✅ Tableau
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

        chargerArticles();

        // ✅ Bas : boutons + retour
        JPanel panelBas = new JPanel(new FlowLayout());

        JButton btnRetour = new JButton("Retour");
        btnRetour.addActionListener(e -> {
            controleur.retournerAuMenu();
            dispose();
        });

        panelBas.add(btnRetour);
        panel.add(panelBas, BorderLayout.SOUTH);

        add(panel);
    }

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
}

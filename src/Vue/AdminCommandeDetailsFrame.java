package Vue;

import DAO.CommandeDAO;
import Modele.Article;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Map;

public class AdminCommandeDetailsFrame extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private int idCommande;
    private CommandeDAO commandeDAO;

    public AdminCommandeDetailsFrame(int idCommande) {
        this.idCommande = idCommande;
        this.commandeDAO = new CommandeDAO();

        setTitle("Détails de la commande n°" + idCommande);
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        model = new DefaultTableModel(new String[]{"ID", "Nom", "Marque", "Prix", "Quantité"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(table);

        JButton btnRetour = new JButton("Retour");
        btnRetour.addActionListener(e -> dispose());

        JPanel bas = new JPanel();
        bas.add(btnRetour);

        add(scrollPane, BorderLayout.CENTER);
        add(bas, BorderLayout.SOUTH);

        chargerLignesCommande();
    }

    private void chargerLignesCommande() {
        model.setRowCount(0);

        Map<Article, Integer> lignes = commandeDAO.getArticlesByCommandeId(idCommande);
        for (Map.Entry<Article, Integer> entry : lignes.entrySet()) {
            Article a = entry.getKey();
            int quantite = entry.getValue();
            float prixApplique = (a.getPrixGros() != null && a.getQuantiteGros() != null && quantite >= a.getQuantiteGros())
                    ? a.getPrixGros()
                    : a.getPrixUnitaire();

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

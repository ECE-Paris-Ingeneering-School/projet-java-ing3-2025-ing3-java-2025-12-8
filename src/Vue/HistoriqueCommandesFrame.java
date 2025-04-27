package Vue;

import DAO.CommandeDAO;
import Modele.Client;
import Modele.Commande;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class HistoriqueCommandesFrame extends JFrame {

    private Client client; // Client connecté

    public HistoriqueCommandesFrame(Client client) {
        this.client = client;

        // Configuration de la fenêtre
        setTitle("Historique des commandes");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Récupération des commandes du client
        CommandeDAO dao = new CommandeDAO();
        List<Commande> commandes = dao.getCommandesByClientId(client.getId());

        // Création du modèle de tableau
        String[] columns = {"ID Commande", "Date", "Total (€)"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        // Remplissage du tableau avec les commandes
        for (Commande c : commandes) {
            model.addRow(new Object[]{
                    c.getId(),
                    c.getDateCommande().toString(),
                    c.getTotal()
            });
        }

        // Création du tableau et du scroll
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Bouton retour
        JButton btnRetour = new JButton("Retour");
        btnRetour.addActionListener(e -> dispose());
        add(btnRetour, BorderLayout.SOUTH);
    }
}

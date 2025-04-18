package Vue;

import DAO.CommandeDAO;
import Modele.Client;
import Modele.Commande;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class HistoriqueCommandesFrame extends JFrame {

    private Client client;

    public HistoriqueCommandesFrame(Client client) {
        this.client = client;
        setTitle("Historique des commandes");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        CommandeDAO dao = new CommandeDAO();
        List<Commande> commandes = dao.getCommandesByClientId(client.getId());

        String[] columns = {"ID Commande", "Date", "Total (€)"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        for (Commande c : commandes) {
            model.addRow(new Object[]{
                    c.getId(),
                    c.getDateCommande().toString(),
                    c.getTotal()
            });
        }

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JButton btnRetour = new JButton("Retour");
        btnRetour.addActionListener(e -> dispose());
        add(btnRetour, BorderLayout.SOUTH);
    }
}

package Vue;

import Controleur.AdminControleur;
import DAO.CommandeDAO;
import Modele.Commande;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminCommandesFrame extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private CommandeDAO commandeDAO;
    private AdminControleur controleur;

    public AdminCommandesFrame(AdminControleur controleur) {
        this.controleur = controleur;

        setTitle("Commandes - Admin");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        commandeDAO = new CommandeDAO();

        model = new DefaultTableModel(new String[]{"ID", "ID Client", "Date", "Total (€)"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(table);

        JButton btnDetails = new JButton("Voir détails");
        btnDetails.addActionListener(e -> voirDetails());

        JButton btnRetour = new JButton("Retour");
        btnRetour.addActionListener(e -> {
            controleur.retournerAuMenu();
            dispose();
        });

        JPanel bas = new JPanel();
        bas.add(btnDetails);
        bas.add(btnRetour);

        add(scrollPane, BorderLayout.CENTER);
        add(bas, BorderLayout.SOUTH);

        chargerCommandes();
    }

    private void chargerCommandes() {
        model.setRowCount(0);
        List<Commande> commandes = commandeDAO.getAllCommandes();

        for (Commande c : commandes) {
            model.addRow(new Object[]{
                    c.getId(),
                    c.getIdClient(),
                    c.getDateCommande(),
                    c.getTotal()
            });
        }
    }

    private void voirDetails() {
        int row = table.getSelectedRow();
        if (row != -1) {
            int idCommande = (int) model.getValueAt(row, 0);
            new AdminCommandeDetailsFrame(idCommande).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Sélectionnez une commande.");
        }
    }
}

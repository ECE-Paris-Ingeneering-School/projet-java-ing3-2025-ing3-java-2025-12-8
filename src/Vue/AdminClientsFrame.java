package Vue;

import Controleur.AdminControleur;
import DAO.ClientDAO;
import Modele.Client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminClientsFrame extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private ClientDAO clientDAO;
    private AdminControleur controleur;

    public AdminClientsFrame(AdminControleur controleur) {
        this.controleur = controleur;
        this.clientDAO = new ClientDAO();

        setTitle("Gestion des clients");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        model = new DefaultTableModel(new String[]{"ID", "Nom", "Email"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(table);

        JButton btnSupprimer = new JButton("Supprimer le client");
        btnSupprimer.addActionListener(e -> supprimerClient());

        JButton btnRetour = new JButton("Retour");
        btnRetour.addActionListener(e -> {
            controleur.retournerAuMenu();
            dispose();
        });

        JPanel bas = new JPanel();
        bas.add(btnSupprimer);
        bas.add(btnRetour);

        add(scrollPane, BorderLayout.CENTER);
        add(bas, BorderLayout.SOUTH);

        chargerClients();
    }

    private void chargerClients() {
        model.setRowCount(0);
        List<Client> clients = clientDAO.getAllClients();

        for (Client c : clients) {
            model.addRow(new Object[]{
                    c.getId(),
                    c.getNom(),
                    c.getEmail()
            });
        }
    }

    private void supprimerClient() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un client.");
            return;
        }

        int idClient = (int) model.getValueAt(row, 0);

        // 🔐 Vérifie s’il a des commandes
        if (clientDAO.aDesCommandes(idClient)) {
            JOptionPane.showMessageDialog(this,
                    "Ce client ne peut pas être supprimé car il a déjà passé des commandes.",
                    "Suppression impossible", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Confirmez-vous la suppression de ce client ?",
                "Confirmation", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = clientDAO.deleteClientById(idClient);
            if (success) {
                JOptionPane.showMessageDialog(this, "Client supprimé.");
                chargerClients();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de la suppression.");
            }
        }
    }

}

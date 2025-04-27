package Vue; // Déclare le package Vue

import Controleur.AdminControleur;
import DAO.ClientDAO;
import Modele.Client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminClientsFrame extends JFrame {

    private JTable table; // Tableau pour afficher les clients
    private DefaultTableModel model; // Modèle de données du tableau
    private ClientDAO clientDAO; // DAO pour accéder aux données des clients
    private AdminControleur controleur; // Contrôleur pour gérer les actions

    public AdminClientsFrame(AdminControleur controleur) {
        this.controleur = controleur;
        this.clientDAO = new ClientDAO();

        // Configuration de la fenêtre
        setTitle("Gestion des clients");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Création du modèle pour le tableau
        model = new DefaultTableModel(new String[]{"ID", "Nom", "Email"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Empêche la modification directe des cellules
            }
        };

        table = new JTable(model);
        table.setRowHeight(25); // Hauteur des lignes du tableau
        JScrollPane scrollPane = new JScrollPane(table);

        // Bouton pour supprimer un client
        JButton btnSupprimer = new JButton("Supprimer le client");
        btnSupprimer.addActionListener(e -> supprimerClient());

        // Bouton pour retourner au menu principal
        JButton btnRetour = new JButton("Retour");
        btnRetour.addActionListener(e -> {
            controleur.retournerAuMenu();
            dispose(); // Ferme la fenêtre actuelle
        });

        // Panel pour contenir les boutons en bas
        JPanel bas = new JPanel();
        bas.add(btnSupprimer);
        bas.add(btnRetour);

        // Ajout des composants à la fenêtre
        add(scrollPane, BorderLayout.CENTER);
        add(bas, BorderLayout.SOUTH);

        // Chargement des clients dans le tableau
        chargerClients();
    }

    // Méthode pour charger tous les clients dans le tableau
    private void chargerClients() {
        model.setRowCount(0); // Vide le tableau
        List<Client> clients = clientDAO.getAllClients();

        for (Client c : clients) {
            model.addRow(new Object[]{
                    c.getId(),
                    c.getNom(),
                    c.getEmail()
            });
        }
    }

    // Méthode pour supprimer un client sélectionné
    private void supprimerClient() {
        int row = table.getSelectedRow();
        if (row == -1) { // Aucun client sélectionné
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un client.");
            return;
        }

        int idClient = (int) model.getValueAt(row, 0); // Récupère l'ID du client sélectionné

        // Vérifie si le client a déjà passé des commandes
        if (clientDAO.aDesCommandes(idClient)) {
            JOptionPane.showMessageDialog(this,
                    "Ce client ne peut pas être supprimé car il a déjà passé des commandes.",
                    "Suppression impossible", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Demande de confirmation avant suppression
        int confirm = JOptionPane.showConfirmDialog(this,
                "Confirmez-vous la suppression de ce client ?",
                "Confirmation", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = clientDAO.deleteClientById(idClient);
            if (success) {
                JOptionPane.showMessageDialog(this, "Client supprimé.");
                chargerClients(); // Recharge les clients pour mettre à jour le tableau
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de la suppression.");
            }
        }
    }
}

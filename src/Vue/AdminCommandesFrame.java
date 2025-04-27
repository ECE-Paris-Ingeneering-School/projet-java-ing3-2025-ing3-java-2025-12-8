package Vue; // Déclare le package Vue

import Controleur.AdminControleur;
import DAO.CommandeDAO;
import Modele.Commande;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminCommandesFrame extends JFrame {

    private JTable table; // Tableau pour afficher les commandes
    private DefaultTableModel model; // Modèle de données du tableau
    private CommandeDAO commandeDAO; // DAO pour accéder aux données des commandes
    private AdminControleur controleur; // Contrôleur pour gérer les actions

    public AdminCommandesFrame(AdminControleur controleur) {
        this.controleur = controleur;

        // Configuration de la fenêtre
        setTitle("Commandes - Admin");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        commandeDAO = new CommandeDAO();

        // Création du modèle du tableau
        model = new DefaultTableModel(new String[]{"ID", "ID Client", "Date", "Total (€)"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Empêche l'édition des cellules
            }
        };

        table = new JTable(model);
        table.setRowHeight(25); // Hauteur des lignes du tableau
        JScrollPane scrollPane = new JScrollPane(table);

        // Bouton pour voir les détails d'une commande
        JButton btnDetails = new JButton("Voir détails");
        btnDetails.addActionListener(e -> voirDetails());

        // Bouton pour retourner au menu principal
        JButton btnRetour = new JButton("Retour");
        btnRetour.addActionListener(e -> {
            controleur.retournerAuMenu();
            dispose(); // Ferme la fenêtre
        });

        // Panel pour les boutons du bas
        JPanel bas = new JPanel();
        bas.add(btnDetails);
        bas.add(btnRetour);

        // Ajout des composants à la fenêtre
        add(scrollPane, BorderLayout.CENTER);
        add(bas, BorderLayout.SOUTH);

        // Chargement des commandes
        chargerCommandes();
    }

    // Méthode pour charger toutes les commandes dans le tableau
    private void chargerCommandes() {
        model.setRowCount(0); // Vide le tableau
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

    // Méthode pour afficher les détails de la commande sélectionnée
    private void voirDetails() {
        int row = table.getSelectedRow();
        if (row != -1) { // Si une commande est sélectionnée
            int idCommande = (int) model.getValueAt(row, 0);
            new AdminCommandeDetailsFrame(idCommande).setVisible(true); // Ouvre la fenêtre de détails
        } else {
            JOptionPane.showMessageDialog(this, "Sélectionnez une commande."); // Message d'erreur
        }
    }
}

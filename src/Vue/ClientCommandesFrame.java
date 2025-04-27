package Vue; // Déclare le package Vue

import DAO.CommandeDAO;
import Modele.Client;
import Modele.Commande;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ClientCommandesFrame extends JFrame {

    public ClientCommandesFrame(Client client) {
        // Configuration de la fenêtre
        setTitle("Mes commandes");
        setSize(600, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Titre
        JLabel titre = new JLabel("Historique de vos commandes", JLabel.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titre, BorderLayout.NORTH);

        // Récupération des commandes du client
        CommandeDAO dao = new CommandeDAO();
        List<Commande> commandes = dao.getCommandesByClientId(client.getId());

        // Debug console (peut être supprimé en version finale)
        System.out.println(">> RÉCUPÉRATION COMMANDES POUR ID = " + client.getId());
        System.out.println(">> NOMBRE COMMANDES = " + commandes.size());

        // Création du tableau
        String[] colonnes = {"ID Commande", "Date", "Total (€)"};
        DefaultTableModel model = new DefaultTableModel(colonnes, 0);

        for (Commande c : commandes) {
            model.addRow(new Object[]{
                    c.getId(),
                    c.getDateCommande().toString(),
                    c.getTotal()
            });
        }

        JTable table = new JTable(model);
        table.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Bouton retour
        JButton btnRetour = new JButton("Retour");
        btnRetour.addActionListener(e -> dispose());
        panel.add(btnRetour, BorderLayout.SOUTH);

        add(panel); // Ajout du panel à la fenêtre
    }
}

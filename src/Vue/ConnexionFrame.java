package Vue; // Déclare le package Vue

import DAO.AdminDAO;
import DAO.ClientDAO;
import Modele.Admin;
import Modele.Client;
import Modele.Panier;

import javax.swing.*;
import java.awt.*;

public class ConnexionFrame extends JFrame {

    private JTextField tfIdentifiant; // Champ pour saisir l'identifiant
    private JPasswordField tfMotDePasse; // Champ pour saisir le mot de passe
    private JComboBox<String> cbType; // Choix du type de compte (client/admin)

    public ConnexionFrame() {
        // Configuration de la fenêtre
        setTitle("Connexion");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal avec grille
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Sélection du type de compte
        panel.add(new JLabel("Type de compte :"));
        cbType = new JComboBox<>(new String[]{"client", "admin"});
        panel.add(cbType);

        // Champ pour l'email (client) ou l'ID (admin)
        panel.add(new JLabel("Email (client) ou ID (admin) :"));
        tfIdentifiant = new JTextField();
        panel.add(tfIdentifiant);

        // Champ pour le mot de passe
        panel.add(new JLabel("Mot de passe :"));
        tfMotDePasse = new JPasswordField();
        panel.add(tfMotDePasse);

        // Bouton pour se connecter
        JButton btnConnexion = new JButton("Se connecter");
        btnConnexion.addActionListener(e -> connecter());
        panel.add(btnConnexion);

        // Bouton pour créer un compte
        JButton btnCreerCompte = new JButton("Créer un compte");
        btnCreerCompte.addActionListener(e -> creerCompte());
        panel.add(btnCreerCompte);

        add(panel); // Ajout du panel à la fenêtre
    }

    // Méthode appelée lors de la tentative de connexion
    private void connecter() {
        String type = (String) cbType.getSelectedItem();
        String identifiant = tfIdentifiant.getText();
        String motDePasse = new String(tfMotDePasse.getPassword());

        if (type.equals("client")) {
            ClientDAO clientDAO = new ClientDAO();
            Client client = clientDAO.getClientByEmailAndPassword(identifiant, motDePasse);

            // Debug console
            System.out.println(">> CLIENT CONNECTÉ ID = " + (client != null ? client.getId() : "null"));

            if (client != null) {
                JOptionPane.showMessageDialog(this, "Bienvenue " + client.getEmail());
                Panier panier = new Panier(); // Panier vide créé pour le client
                dispose();
                new ClientMenuFrame(client, panier).setVisible(true); // Ouvre le menu client
            } else {
                JOptionPane.showMessageDialog(this, "Identifiants client incorrects.");
            }

        } else if (type.equals("admin")) {
            try {
                int id = Integer.parseInt(identifiant);
                AdminDAO adminDAO = new AdminDAO();
                Admin admin = adminDAO.getAdminByIdAndPassword(id, motDePasse);

                if (admin != null) {
                    JOptionPane.showMessageDialog(this, "Bienvenue " + admin.getNom());
                    dispose();
                    new AdminMenuFrame(admin).setVisible(true); // Ouvre le menu admin
                } else {
                    JOptionPane.showMessageDialog(this, "Identifiants admin incorrects.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "L'identifiant admin doit être un nombre entier.");
            }
        }
    }

    // Méthode appelée lorsqu'on clique sur "Créer un compte"
    private void creerCompte() {
        String type = (String) cbType.getSelectedItem();
        if ("client".equals(type)) {
            new ClientCreationCompteFrame().setVisible(true); // Ouvre création compte client
        } else {
            new AdminCreationCompteFrame().setVisible(true); // Ouvre création compte admin
        }
    }

    // Méthode principale pour lancer la fenêtre
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ConnexionFrame().setVisible(true));
    }
}

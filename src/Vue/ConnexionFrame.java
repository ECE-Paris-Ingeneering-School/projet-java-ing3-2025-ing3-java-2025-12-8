package Vue;

import DAO.AdminDAO;
import DAO.ClientDAO;
import Modele.Admin;
import Modele.Client;
import Modele.Panier;

import javax.swing.*;
import java.awt.*;

public class ConnexionFrame extends JFrame {

    private JTextField tfIdentifiant;
    private JPasswordField tfMotDePasse;
    private JComboBox<String> cbType;

    public ConnexionFrame() {
        setTitle("Connexion");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Type de compte :"));
        cbType = new JComboBox<>(new String[]{"client", "admin"});
        panel.add(cbType);

        panel.add(new JLabel("Email (client) ou ID (admin) :"));
        tfIdentifiant = new JTextField();
        panel.add(tfIdentifiant);

        panel.add(new JLabel("Mot de passe :"));
        tfMotDePasse = new JPasswordField();
        panel.add(tfMotDePasse);

        JButton btnConnexion = new JButton("Se connecter");
        btnConnexion.addActionListener(e -> connecter());
        panel.add(btnConnexion);

        JButton btnCreerCompte = new JButton("Créer un compte");
        btnCreerCompte.addActionListener(e -> creerCompte());
        panel.add(btnCreerCompte);

        add(panel);
    }

    private void connecter() {
        String type = (String) cbType.getSelectedItem();
        String identifiant = tfIdentifiant.getText();
        String motDePasse = new String(tfMotDePasse.getPassword());

        if (type.equals("client")) {
            ClientDAO clientDAO = new ClientDAO();
            Client client = clientDAO.getClientByEmailAndPassword(identifiant, motDePasse);
            System.out.println(">> CLIENT CONNECTÉ ID = " + client.getId());

            if (client != null) {
                JOptionPane.showMessageDialog(this, "Bienvenue " + client.getEmail());
                Panier panier = new Panier(); // ✅ Panier partagé initialisé
                dispose();
                new ClientMenuFrame(client, panier).setVisible(true); // Assure-toi que c’est bien MenuClientFrame
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
                    new AdminMenuFrame(admin).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Identifiants admin incorrects.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "L'identifiant admin doit être un nombre entier.");
            }
        }
    }

    private void creerCompte() {
        String type = (String) cbType.getSelectedItem();
        if ("client".equals(type)) {
            new ClientCreationCompteFrame().setVisible(true);
        } else {
            new AdminCreationCompteFrame().setVisible(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ConnexionFrame().setVisible(true));
    }
}

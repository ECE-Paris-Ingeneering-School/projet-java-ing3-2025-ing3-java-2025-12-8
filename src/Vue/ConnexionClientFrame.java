package Vue;

import DAO.ClientDAO;
import Modele.Client;

import javax.swing.*;
import java.awt.*;

public class ConnexionClientFrame extends JFrame {

    private JTextField tfEmail;
    private JPasswordField tfPassword;

    public ConnexionClientFrame() {
        setTitle("Connexion Client");
        setSize(350, 220);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        panel.add(new JLabel("Email :"));
        tfEmail = new JTextField();
        panel.add(tfEmail);

        panel.add(new JLabel("Mot de passe :"));
        tfPassword = new JPasswordField();
        panel.add(tfPassword);

        JButton btnConnexion = new JButton("Se connecter");
        btnConnexion.addActionListener(e -> connexionClient());
        panel.add(btnConnexion);

        JButton btnCreerCompte = new JButton("Créer un compte");
        btnCreerCompte.addActionListener(e -> new CreationCompteFrame().setVisible(true));
        panel.add(btnCreerCompte);

        JButton btnAdmin = new JButton("Connexion Admin");
        btnAdmin.addActionListener(e -> {
            new ConnexionAdminFrame().setVisible(true);
            dispose();
        });
        panel.add(btnAdmin);

        add(panel);
    }

    private void connexionClient() {
        String email = tfEmail.getText();
        String password = new String(tfPassword.getPassword());

        ClientDAO clientDAO = new ClientDAO();
        Client client = clientDAO.getClientByEmailAndPassword(email, password);

        if (client != null) {
            JOptionPane.showMessageDialog(this, "Bienvenue " + client.getNom());
            dispose();
            new MenuClientFrame(client).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Email ou mot de passe incorrect.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ConnexionClientFrame().setVisible(true));
    }
}

package Vue;

import DAO.ClientDAO;

import javax.swing.*;
import java.awt.*;

public class ClientCreationCompteFrame extends JFrame {

    private JTextField tfNom, tfEmail; // Champs pour le nom et l'email
    private JPasswordField tfPassword; // Champ pour le mot de passe

    public ClientCreationCompteFrame() {
        // Configuration de la fenêtre
        setTitle("Créer un compte client");
        setSize(300, 220);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal avec une grille
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Label + Champ pour le nom
        panel.add(new JLabel("Nom :"));
        tfNom = new JTextField();
        panel.add(tfNom);

        // Label + Champ pour l'email
        panel.add(new JLabel("Email :"));
        tfEmail = new JTextField();
        panel.add(tfEmail);

        // Label + Champ pour le mot de passe
        panel.add(new JLabel("Mot de passe :"));
        tfPassword = new JPasswordField();
        panel.add(tfPassword);

        // Bouton pour créer le compte
        JButton btnCreer = new JButton("Créer");
        btnCreer.addActionListener(e -> {
            ClientDAO dao = new ClientDAO();
            dao.creerClient(tfNom.getText(), tfEmail.getText(), new String(tfPassword.getPassword())); // Appel DAO pour créer le client
            JOptionPane.showMessageDialog(this, "Compte client créé !");
            dispose(); // Ferme la fenêtre
        });

        panel.add(btnCreer);

        add(panel); // Ajout du panel à la fenêtre
    }
}

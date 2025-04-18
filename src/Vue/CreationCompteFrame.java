package Vue;

import DAO.ClientDAO;
import javax.swing.*;
import java.awt.*;

public class CreationCompteFrame extends JFrame {

    private JTextField tfNom;
    private JTextField tfEmail;
    private JPasswordField tfMotDePasse;

    public CreationCompteFrame() {
        setTitle("Créer un compte client");
        setSize(300, 220);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        panel.add(new JLabel("Nom :"));
        tfNom = new JTextField();
        panel.add(tfNom);

        panel.add(new JLabel("Email :"));
        tfEmail = new JTextField();
        panel.add(tfEmail);

        panel.add(new JLabel("Mot de passe :"));
        tfMotDePasse = new JPasswordField();
        panel.add(tfMotDePasse);

        JButton btnCreer = new JButton("Créer le compte");
        btnCreer.addActionListener(e -> creerCompte());
        panel.add(btnCreer);

        JButton btnAnnuler = new JButton("Annuler");
        btnAnnuler.addActionListener(e -> dispose());
        panel.add(btnAnnuler);

        add(panel);
    }

    private void creerCompte() {
        String nom = tfNom.getText();
        String email = tfEmail.getText();
        String motDePasse = new String(tfMotDePasse.getPassword());

        if (nom.isEmpty() || email.isEmpty() || motDePasse.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tous les champs sont obligatoires.");
            return;
        }

        ClientDAO clientDAO = new ClientDAO();
        clientDAO.creerClient(nom, email, motDePasse);

        JOptionPane.showMessageDialog(this, "Compte créé avec succès !");
        dispose();
    }
}

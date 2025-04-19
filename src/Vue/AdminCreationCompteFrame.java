package Vue;

import DAO.AdminDAO;

import javax.swing.*;
import java.awt.*;

public class AdminCreationCompteFrame extends JFrame {

    private JTextField tfId, tfNom, tfEmail;
    private JPasswordField tfPassword;

    public AdminCreationCompteFrame() {
        setTitle("Créer un compte administrateur");
        setSize(350, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        panel.add(new JLabel("ID :"));
        tfId = new JTextField();
        panel.add(tfId);

        panel.add(new JLabel("Nom :"));
        tfNom = new JTextField();
        panel.add(tfNom);

        panel.add(new JLabel("Email :"));
        tfEmail = new JTextField();
        panel.add(tfEmail);

        panel.add(new JLabel("Mot de passe :"));
        tfPassword = new JPasswordField();
        panel.add(tfPassword);

        JButton btnCreer = new JButton("Créer");
        btnCreer.addActionListener(e -> creerAdmin());
        panel.add(btnCreer);

        add(panel);
    }

    private void creerAdmin() {
        try {
            int id = Integer.parseInt(tfId.getText());
            String nom = tfNom.getText();
            String email = tfEmail.getText();
            String motDePasse = new String(tfPassword.getPassword());

            AdminDAO dao = new AdminDAO();
            dao.creerAdmin(id, nom, email, motDePasse);

            JOptionPane.showMessageDialog(this, "Compte administrateur créé !");
            dispose();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "L'ID doit être un nombre entier.");
        }
    }
}

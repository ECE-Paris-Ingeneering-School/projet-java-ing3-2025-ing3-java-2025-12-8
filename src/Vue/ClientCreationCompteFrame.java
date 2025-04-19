package Vue;

import DAO.ClientDAO;

import javax.swing.*;
import java.awt.*;

public class ClientCreationCompteFrame extends JFrame {

    private JTextField tfNom, tfEmail;
    private JPasswordField tfPassword;

    public ClientCreationCompteFrame() {
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
        tfPassword = new JPasswordField();
        panel.add(tfPassword);

        JButton btnCreer = new JButton("Créer");
        btnCreer.addActionListener(e -> {
            ClientDAO dao = new ClientDAO();
            dao.creerClient(tfNom.getText(), tfEmail.getText(), new String(tfPassword.getPassword()));
            JOptionPane.showMessageDialog(this, "Compte client créé !");
            dispose();
        });

        panel.add(btnCreer);
        add(panel);
    }
}

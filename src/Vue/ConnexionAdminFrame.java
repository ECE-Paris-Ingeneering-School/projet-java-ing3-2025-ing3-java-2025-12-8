package Vue;

import javax.swing.*;
import java.awt.*;

public class ConnexionAdminFrame extends JFrame {

    private JTextField tfId;
    private JPasswordField tfPassword;

    public ConnexionAdminFrame() {
        setTitle("Connexion Admin");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        panel.add(new JLabel("ID Admin :"));
        tfId = new JTextField();
        panel.add(tfId);

        panel.add(new JLabel("Mot de passe :"));
        tfPassword = new JPasswordField();
        panel.add(tfPassword);

        JButton btnConnexion = new JButton("Connexion");
        btnConnexion.addActionListener(e -> connexion());
        panel.add(btnConnexion);

        JButton btnAnnuler = new JButton("Annuler");
        btnAnnuler.addActionListener(e -> dispose());
        panel.add(btnAnnuler);

        add(panel);
    }

    private void connexion() {
        try {
            int id = Integer.parseInt(tfId.getText());
            String password = new String(tfPassword.getPassword());

            // à remplacer ensuite par un appel DAO réel
            if (id == 1 && password.equals("admin")) {
                JOptionPane.showMessageDialog(this, "Connexion réussie");
                new AdminMenuFrame().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "ID ou mot de passe incorrect.");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID invalide (doit être un nombre)");
        }
    }
}

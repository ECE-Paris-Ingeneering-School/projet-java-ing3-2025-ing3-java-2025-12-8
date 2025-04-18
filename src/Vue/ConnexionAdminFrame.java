package Vue;

import DAO.AdminDAO;
import Modele.Admin;

import javax.swing.*;
import java.awt.*;

public class ConnexionAdminFrame extends JFrame {

    private JTextField tfId;
    private JPasswordField tfPassword;

    public ConnexionAdminFrame() {
        setTitle("Connexion Administrateur");
        setSize(350, 180);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Champ ID
        panel.add(new JLabel("ID Admin :"));
        tfId = new JTextField();
        panel.add(tfId);

        // Champ mot de passe
        panel.add(new JLabel("Mot de passe :"));
        tfPassword = new JPasswordField();
        panel.add(tfPassword);

        // Bouton Connexion
        JButton btnConnexion = new JButton("Se connecter");
        btnConnexion.addActionListener(e -> connexion());
        panel.add(btnConnexion);

        // Bouton Annuler
        JButton btnAnnuler = new JButton("Annuler");
        btnAnnuler.addActionListener(e -> dispose());
        panel.add(btnAnnuler);

        add(panel);
    }

    private void connexion() {
        try {
            int id = Integer.parseInt(tfId.getText());
            String password = new String(tfPassword.getPassword());

            AdminDAO dao = new AdminDAO();
            Admin admin = dao.getAdminByIdAndPassword(id, password);

            if (admin != null) {
                JOptionPane.showMessageDialog(this, "Bienvenue " + admin.getNom());
                dispose();
                new AdminMenuFrame().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "ID ou mot de passe incorrect.");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "L'ID doit être un nombre entier.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ConnexionAdminFrame().setVisible(true));
    }
}

package Vue; // Déclare le package Vue

import DAO.AdminDAO;

import javax.swing.*;
import java.awt.*;

public class AdminCreationCompteFrame extends JFrame {

    private JTextField tfId, tfNom, tfEmail; // Champs pour ID, nom et email
    private JPasswordField tfPassword; // Champ pour le mot de passe

    public AdminCreationCompteFrame() {
        // Configuration de la fenêtre
        setTitle("Créer un compte administrateur");
        setSize(350, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Création du panel principal
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Champ pour l'ID
        panel.add(new JLabel("ID :"));
        tfId = new JTextField();
        panel.add(tfId);

        // Champ pour le nom
        panel.add(new JLabel("Nom :"));
        tfNom = new JTextField();
        panel.add(tfNom);

        // Champ pour l'email
        panel.add(new JLabel("Email :"));
        tfEmail = new JTextField();
        panel.add(tfEmail);

        // Champ pour le mot de passe
        panel.add(new JLabel("Mot de passe :"));
        tfPassword = new JPasswordField();
        panel.add(tfPassword);

        // Bouton pour créer le compte
        JButton btnCreer = new JButton("Créer");
        btnCreer.addActionListener(e -> creerAdmin());
        panel.add(btnCreer);

        add(panel); // Ajout du panel à la fenêtre
    }

    // Méthode pour créer un nouvel administrateur
    private void creerAdmin() {
        try {
            int id = Integer.parseInt(tfId.getText()); // Conversion du texte ID en entier
            String nom = tfNom.getText();
            String email = tfEmail.getText();
            String motDePasse = new String(tfPassword.getPassword());

            AdminDAO dao = new AdminDAO();
            dao.creerAdmin(id, nom, email, motDePasse); // Appel DAO pour insérer l'admin

            JOptionPane.showMessageDialog(this, "Compte administrateur créé !");
            dispose(); // Ferme la fenêtre après création

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "L'ID doit être un nombre entier."); // Gestion d'erreur ID non numérique
        }
    }
}

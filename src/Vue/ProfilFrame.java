package Vue; // Déclare le package Vue

import DAO.AdminDAO;
import DAO.ClientDAO;
import Modele.Admin;
import Modele.Client;

import javax.swing.*;
import java.awt.*;

public class ProfilFrame extends JFrame {

    private JTextField tfNom, tfEmail; // Champs pour nom et email
    private JPasswordField tfMotDePasse; // Champ pour mot de passe
    private JButton btnEnregistrer; // Bouton enregistrer

    private Client client; // Client connecté
    private Admin admin; // Admin connecté

    // Constructeur pour un client
    public ProfilFrame(Client client) {
        this.client = client;
        initUI("client");
    }

    // Constructeur pour un admin
    public ProfilFrame(Admin admin) {
        this.admin = admin;
        initUI("admin");
    }

    // Initialisation de l'interface
    private void initUI(String type) {
        setTitle("Mon compte - " + type);
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Nom
        panel.add(new JLabel("Nom :"));
        tfNom = new JTextField(type.equals("client") ? client.getNom() : admin.getNom());
        panel.add(tfNom);

        // Email
        panel.add(new JLabel("Email :"));
        tfEmail = new JTextField(type.equals("client") ? client.getEmail() : admin.getEmail());
        panel.add(tfEmail);

        // Mot de passe
        panel.add(new JLabel("Mot de passe :"));
        tfMotDePasse = new JPasswordField(type.equals("client") ? client.getMotDePasse() : admin.getMotDePasse());
        tfMotDePasse.setEchoChar((char) 0); // Mot de passe affiché en clair
        panel.add(tfMotDePasse);

        // Bouton enregistrer
        btnEnregistrer = new JButton("Enregistrer les modifications");
        btnEnregistrer.addActionListener(e -> enregistrerModifications(type));
        panel.add(btnEnregistrer);

        add(panel);
    }

    // Enregistrer les modifications du profil
    private void enregistrerModifications(String type) {
        String nom = tfNom.getText();
        String email = tfEmail.getText();
        String motDePasse = new String(tfMotDePasse.getPassword());

        if (type.equals("client")) {
            ClientDAO dao = new ClientDAO();
            dao.updateClient(client.getId(), nom, email, motDePasse);
            client.setNom(nom);
            client.setEmail(email);
            client.setMotDePasse(motDePasse);
        } else {
            AdminDAO dao = new AdminDAO();
            dao.updateAdmin(admin.getId(), nom, email, motDePasse);
            admin.setNom(nom);
            admin.setEmail(email);
            admin.setMotDePasse(motDePasse);
        }

        JOptionPane.showMessageDialog(this, "Profil mis à jour !");
        dispose(); // Ferme la fenêtre
    }
}

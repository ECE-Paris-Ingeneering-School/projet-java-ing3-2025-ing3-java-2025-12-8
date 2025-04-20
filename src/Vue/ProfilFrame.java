package Vue;

import DAO.AdminDAO;
import DAO.ClientDAO;
import Modele.Admin;
import Modele.Client;

import javax.swing.*;
import java.awt.*;

public class ProfilFrame extends JFrame {

    private JTextField tfNom, tfEmail;
    private JPasswordField tfMotDePasse;
    private JButton btnEnregistrer;

    private Client client;
    private Admin admin;

    public ProfilFrame(Client client) {
        this.client = client;
        initUI("client");
    }

    public ProfilFrame(Admin admin) {
        this.admin = admin;
        initUI("admin");
    }

    private void initUI(String type) {
        setTitle("Mon compte - " + type);
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        panel.add(new JLabel("Nom :"));
        tfNom = new JTextField(type.equals("client") ? client.getNom() : admin.getNom());
        panel.add(tfNom);

        panel.add(new JLabel("Email :"));
        tfEmail = new JTextField(type.equals("client") ? client.getEmail() : admin.getEmail());
        panel.add(tfEmail);

        panel.add(new JLabel("Mot de passe :"));
        tfMotDePasse = new JPasswordField(type.equals("client") ? client.getMotDePasse() : admin.getMotDePasse());
        tfMotDePasse.setEchoChar((char) 0); // Affiche le mot de passe
        panel.add(tfMotDePasse);


        btnEnregistrer = new JButton("Enregistrer les modifications");
        btnEnregistrer.addActionListener(e -> enregistrerModifications(type));
        panel.add(btnEnregistrer);

        add(panel);
    }

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
        dispose();
    }
}

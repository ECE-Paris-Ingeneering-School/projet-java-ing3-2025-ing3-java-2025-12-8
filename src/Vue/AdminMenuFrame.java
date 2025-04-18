package Vue;

import javax.swing.*;
import java.awt.*;

public class AdminMenuFrame extends JFrame {

    public AdminMenuFrame() {
        setTitle("Menu Administrateur");
        setSize(300, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Boutons
        JButton btnCatalogue = new JButton("Consulter le Catalogue");
        btnCatalogue.addActionListener(e -> new CatalogueFrame().setVisible(true));
        panel.add(btnCatalogue);

        JButton btnAjouter = new JButton("Ajouter un Article");
        btnAjouter.addActionListener(e -> new AjoutArticleFrame().setVisible(true));
        panel.add(btnAjouter);

        JButton btnSupprimer = new JButton("🗑Supprimer un Article");
        btnSupprimer.addActionListener(e -> new SuppressionArticleFrame().setVisible(true));
        panel.add(btnSupprimer);

        JButton btnQuitter = new JButton("Quitter");
        btnQuitter.addActionListener(e -> System.exit(0));
        panel.add(btnQuitter);

        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminMenuFrame().setVisible(true));
    }
}

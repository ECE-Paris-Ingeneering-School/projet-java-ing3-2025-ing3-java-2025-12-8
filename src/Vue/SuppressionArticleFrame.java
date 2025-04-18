package Vue;

import DAO.ArticleDAO;

import javax.swing.*;
import java.awt.*;

public class SuppressionArticleFrame extends JFrame {

    private JTextField tfId;
    private ArticleDAO articleDAO;

    public SuppressionArticleFrame() {
        articleDAO = new ArticleDAO();
        setTitle("Supprimer un Article");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Champ ID
        panel.add(new JLabel("ID de l'article à supprimer :"));
        tfId = new JTextField();
        panel.add(tfId);

        // Bouton
        JButton btnSupprimer = new JButton("Supprimer");
        btnSupprimer.addActionListener(e -> supprimerArticle());
        panel.add(btnSupprimer);

        // Fermeture
        JButton btnFermer = new JButton("Fermer");
        btnFermer.addActionListener(e -> dispose());
        panel.add(btnFermer);

        add(panel);
    }

    private void supprimerArticle() {
        try {
            int id = Integer.parseInt(tfId.getText());
            boolean success = articleDAO.deleteArticleById(id);
            if (success) {
                JOptionPane.showMessageDialog(this, "Article supprimé avec succès.");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Aucun article trouvé avec cet ID.", "Erreur", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer un ID valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SuppressionArticleFrame().setVisible(true));
    }
}

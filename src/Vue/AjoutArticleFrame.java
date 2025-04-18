package Vue;

import DAO.ArticleDAO;
import Modele.Article;

import javax.swing.*;
import java.awt.*;

public class AjoutArticleFrame extends JFrame {

    private JTextField tfId, tfNom, tfNomMarque, tfPrixUnitaire, tfPrixGros, tfQuantiteGros, tfIdMarque;
    private ArticleDAO articleDAO;

    public AjoutArticleFrame() {
        articleDAO = new ArticleDAO();
        setTitle("Ajouter un Article");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(8, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Champs du formulaire
        panel.add(new JLabel("ID de l'article :"));
        tfId = new JTextField();
        panel.add(tfId);

        panel.add(new JLabel("Nom de l'article :"));
        tfNom = new JTextField();
        panel.add(tfNom);

        panel.add(new JLabel("Nom de la marque (affichage) :"));
        tfNomMarque = new JTextField();
        panel.add(tfNomMarque);

        panel.add(new JLabel("Prix unitaire (€) :"));
        tfPrixUnitaire = new JTextField();
        panel.add(tfPrixUnitaire);

        panel.add(new JLabel("Prix gros (€) (0 si aucun) :"));
        tfPrixGros = new JTextField();
        panel.add(tfPrixGros);

        panel.add(new JLabel("Quantité gros (0 si aucun) :"));
        tfQuantiteGros = new JTextField();
        panel.add(tfQuantiteGros);

        panel.add(new JLabel("ID de la marque (existant) :"));
        tfIdMarque = new JTextField();
        panel.add(tfIdMarque);

        // Bouton
        JButton btnAjouter = new JButton("Ajouter l'article");
        btnAjouter.addActionListener(e -> ajouterArticle());

        // Layout
        add(panel, BorderLayout.CENTER);
        add(btnAjouter, BorderLayout.SOUTH);
    }

    private void ajouterArticle() {
        try {
            int id = Integer.parseInt(tfId.getText());
            String nom = tfNom.getText();
            String nomMarque = tfNomMarque.getText();
            float prixUnitaire = Float.parseFloat(tfPrixUnitaire.getText());

            Float prixGros = Float.parseFloat(tfPrixGros.getText());
            if (prixGros == 0) prixGros = null;

            Integer quantiteGros = Integer.parseInt(tfQuantiteGros.getText());
            if (quantiteGros == 0) quantiteGros = null;

            int idMarque = Integer.parseInt(tfIdMarque.getText());

            Article article = new Article(id, nom, nomMarque, prixUnitaire, prixGros, quantiteGros, idMarque);
            articleDAO.insertArticle(article);

            JOptionPane.showMessageDialog(this, "✅ Article ajouté avec succès !");
            dispose(); // ferme la fenêtre après ajout
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "⚠️ Veuillez vérifier les données saisies.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AjoutArticleFrame().setVisible(true));
    }
}

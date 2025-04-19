package Vue;

import DAO.ArticleDAO;
import DAO.MarqueDAO;
import Modele.Article;
import Modele.Marque;

import javax.swing.*;
import java.awt.*;

public class AjoutArticleFrame extends JFrame {

    private JTextField tfId, tfNom, tfMarqueNom, tfPrixUnitaire, tfPrixGros, tfQuantiteGros, tfIdMarque, tfQuantiteStock;
    private MarqueDAO marqueDAO;

    public AjoutArticleFrame() {
        setTitle("Ajouter un article");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        marqueDAO = new MarqueDAO();

        JPanel panel = new JPanel(new GridLayout(9, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        panel.add(new JLabel("ID de l'article :"));
        tfId = new JTextField();
        panel.add(tfId);

        panel.add(new JLabel("Nom de l'article :"));
        tfNom = new JTextField();
        panel.add(tfNom);

        panel.add(new JLabel("Nom de la marque (affiché) :"));
        tfMarqueNom = new JTextField();
        panel.add(tfMarqueNom);

        panel.add(new JLabel("Prix unitaire (€) :"));
        tfPrixUnitaire = new JTextField();
        panel.add(tfPrixUnitaire);

        panel.add(new JLabel("Prix gros (€) (optionnel) :"));
        tfPrixGros = new JTextField();
        panel.add(tfPrixGros);

        panel.add(new JLabel("Quantité pour prix gros (optionnelle) :"));
        tfQuantiteGros = new JTextField();
        panel.add(tfQuantiteGros);

        panel.add(new JLabel("ID Marque :"));
        tfIdMarque = new JTextField();
        panel.add(tfIdMarque);

        panel.add(new JLabel("Quantité en stock :"));
        tfQuantiteStock = new JTextField();
        panel.add(tfQuantiteStock);

        JButton btnAjouter = new JButton("Ajouter l'article");
        btnAjouter.addActionListener(e -> ajouterArticle());
        panel.add(btnAjouter);

        JButton btnVoirMarques = new JButton("Voir les marques existantes");
        btnVoirMarques.addActionListener(e -> afficherMarquesExistantes());
        panel.add(btnVoirMarques);

        add(panel);
    }

    private void ajouterArticle() {
        try {
            int id = Integer.parseInt(tfId.getText());
            String nom = tfNom.getText();
            String marqueAffichee = tfMarqueNom.getText();
            float prixUnitaire = Float.parseFloat(tfPrixUnitaire.getText());

            Float prixGros = tfPrixGros.getText().isEmpty() ? null : Float.parseFloat(tfPrixGros.getText());
            Integer quantiteGros = tfQuantiteGros.getText().isEmpty() ? null : Integer.parseInt(tfQuantiteGros.getText());
            int idMarque = Integer.parseInt(tfIdMarque.getText());
            int quantiteStock = Integer.parseInt(tfQuantiteStock.getText());

            // Vérification marque
            Marque marque = marqueDAO.findById(idMarque);
            if (marque == null) {
                int choix = JOptionPane.showConfirmDialog(this,
                        "La marque avec l'ID " + idMarque + " n'existe pas.\nSouhaitez-vous la créer ?",
                        "Marque inconnue", JOptionPane.YES_NO_OPTION);

                if (choix == JOptionPane.YES_OPTION) {
                    String nomNouvelleMarque = JOptionPane.showInputDialog(this, "Nom de la nouvelle marque :");

                    if (nomNouvelleMarque == null || nomNouvelleMarque.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Création annulée : nom invalide.");
                        return; // ❌ conserve ce return
                    }

                    if (marqueDAO.findById(idMarque) != null) {
                        JOptionPane.showMessageDialog(this, "ID déjà utilisé.");
                        return;
                    }

                    marqueDAO.ajouterMarque(idMarque, nomNouvelleMarque);
                    JOptionPane.showMessageDialog(this, "Marque créée !");

                    // ✅ Ne pas faire de return ici : on veut continuer l'insertion de l'article
                } else {
                    JOptionPane.showMessageDialog(this, "Ajout annulé.");
                    return;
                }

            }

            // Créer l'article
            Article article = new Article(id, nom, marqueAffichee, prixUnitaire, prixGros, quantiteGros, idMarque, quantiteStock);
            new ArticleDAO().insertArticle(article);

            JOptionPane.showMessageDialog(this, "Article ajouté avec succès !");
            dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Erreur de saisie : vérifiez les champs numériques.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur inattendue : " + e.getMessage());
        }
    }

    private void afficherMarquesExistantes() {
        java.util.List<Marque> marques = marqueDAO.getAllMarques();
        if (marques.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Aucune marque trouvée.");
            return;
        }

        StringBuilder message = new StringBuilder("Marques existantes :\n");
        for (Marque m : marques) {
            message.append("ID: ").append(m.getId())
                    .append(" - Nom: ").append(m.getNom()).append("\n");
        }

        JTextArea textArea = new JTextArea(message.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(300, 200));

        JOptionPane.showMessageDialog(this, scrollPane, "Liste des marques", JOptionPane.INFORMATION_MESSAGE);
    }

}

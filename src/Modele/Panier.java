package Modele;

import java.util.HashMap;
import java.util.Map;

public class Panier {

    private Map<Article, Integer> articles;

    public Panier() {
        articles = new HashMap<>();
    }

    // Ajouter avec gestion de quantité
    public void ajouterArticle(Article article, int quantite) {
        articles.put(article, articles.getOrDefault(article, 0) + quantite);
    }

    // Retirer un article complètement
    public void retirerArticle(Article article) {
        articles.remove(article);
    }

    // Modifier la quantité d’un article
    public void modifierQuantite(Article article, int nouvelleQuantite) {
        if (nouvelleQuantite <= 0) {
            articles.remove(article);
        } else {
            articles.put(article, nouvelleQuantite);
        }
    }

    // Calcul du total avec gestion du prix de groupe
    public float getTotal() {
        float total = 0f;

        for (Map.Entry<Article, Integer> entry : articles.entrySet()) {
            Article a = entry.getKey();
            int quantite = entry.getValue();

            if (a.getPrixGros() != null && a.getQuantiteGros() != null && a.getQuantiteGros() > 0) {
                int groupe = quantite / a.getQuantiteGros();
                int reste = quantite % a.getQuantiteGros();

                total += groupe * a.getPrixGros();
                total += reste * a.getPrixUnitaire();
            } else {
                total += quantite * a.getPrixUnitaire();
            }
        }

        return total;
    }


    public Map<Article, Integer> getArticles() {
        return articles;
    }

    public void vider() {
        articles.clear();
    }

    public boolean estVide() {
        return articles.isEmpty();
    }
}

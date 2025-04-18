package Modele;

import java.util.ArrayList;
import java.util.List;

public class Panier {

    private List<Article> articles;

    public Panier() {
        this.articles = new ArrayList<>();
    }

    public void ajouterArticle(Article article) {
        articles.add(article);
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void vider() {
        articles.clear();
    }

    public float getTotal() {
        float total = 0;
        for (Article a : articles) {
            total += a.getPrixUnitaire();
        }
        return total;
    }
}

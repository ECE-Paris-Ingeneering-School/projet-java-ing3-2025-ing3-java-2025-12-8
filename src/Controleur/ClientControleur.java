package Controleur;

import DAO.ArticleDAO;
import DAO.CommandeDAO;
import Modele.Article;
import Modele.Client;
import Modele.Panier;


public class ClientControleur {

    private final ArticleDAO articleDAO;
    private final CommandeDAO commandeDAO;

    public ClientControleur() {
        this.articleDAO = new ArticleDAO();
        this.commandeDAO = new CommandeDAO();
    }

    public Article getArticleById(int id) {
        return articleDAO.findById(id);
    }

    public void passerCommande(Client client, Panier panier) {
        commandeDAO.enregistrerCommande(client, panier);
        panier.vider();
    }
}

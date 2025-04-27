package Controleur;

// Importation des DAO et des classes métier
import DAO.ArticleDAO;
import DAO.CommandeDAO;
import Modele.Article;
import Modele.Client;
import Modele.Panier;

public class ClientControleur {

    // Références aux DAO pour accéder aux données
    private final ArticleDAO articleDAO;
    private final CommandeDAO commandeDAO;

    // Constructeur du contrôleur client
    public ClientControleur() {
        this.articleDAO = new ArticleDAO();
        this.commandeDAO = new CommandeDAO();
    }

    // Recherche un article par son ID
    public Article getArticleById(int id) {
        return articleDAO.findById(id);
    }

    // Enregistre une commande à partir d'un client et de son panier
    public void passerCommande(Client client, Panier panier) {
        commandeDAO.enregistrerCommande(client, panier);
        panier.vider(); // vide le panier
    }
}

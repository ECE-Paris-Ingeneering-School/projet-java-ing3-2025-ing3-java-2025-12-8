package Modele;

public class LigneCommande {
    private int idArticle;
    private int quantite;
    private float prix;
    //constructeur
    public LigneCommande(int idArticle, int quantite, float prix) {
        this.idArticle = idArticle;
        this.quantite = quantite;
        this.prix = prix;
    }
    //getters
    public int getIdArticle() { return idArticle; }
    public int getQuantite() { return quantite; }
    public float getPrix() { return prix; }
}

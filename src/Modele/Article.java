package Modele;

public class Article {
    private int id;
    private String nom;
    private String marque;
    private float prixUnitaire;
    private Float prixGros;
    private Integer quantiteGros;
    private int idMarque;
    private int quantiteStock;

    // Constructeur complet
    public Article(int id, String nom, String marque, float prixUnitaire, Float prixGros, Integer quantiteGros, int idMarque, int quantiteStock) {
        this.id = id;
        this.nom = nom;
        this.marque = marque;
        this.prixUnitaire = prixUnitaire;
        this.prixGros = prixGros;
        this.quantiteGros = quantiteGros;
        this.idMarque = idMarque;
        this.quantiteStock = quantiteStock;
    }

    // Getters
    public int getId() { return id; }
    public String getNom() { return nom; }
    public String getMarque() { return marque; }
    public float getPrixUnitaire() { return prixUnitaire; }
    public Float getPrixGros() { return prixGros; }
    public Integer getQuantiteGros() { return quantiteGros; }
    public int getIdMarque() { return idMarque; }
    public int getQuantiteStock() {
        return quantiteStock;
    }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setNom(String nom) { this.nom = nom; }
    public void setMarque(String marque) { this.marque = marque; }
    public void setPrixUnitaire(float prixUnitaire) { this.prixUnitaire = prixUnitaire; }
    public void setPrixGros(Float prixGros) { this.prixGros = prixGros; }
    public void setQuantiteGros(Integer quantiteGros) { this.quantiteGros = quantiteGros; }
    public void setIdMarque(int idMarque) { this.idMarque = idMarque; }

    @Override
    public String toString() {
        return nom + " (" + marque + ") - " + prixUnitaire + " €";
    }
}

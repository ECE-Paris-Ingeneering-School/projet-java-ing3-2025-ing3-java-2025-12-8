package Modele;

import java.time.LocalDateTime;

public class Commande {
    private int id;
    private int idClient;
    private LocalDateTime dateCommande;
    private float total;
    //constructeur
    public Commande(int idClient, float total) {
        this.idClient = idClient;
        this.total = total;
        this.dateCommande = LocalDateTime.now();
    }
    //setters
    public void setId(int id) {
        this.id = id;
    }
    public void setDateCommande(LocalDateTime date) {
        this.dateCommande = date;
    }
    //getters
    public int getId() { return id; }
    public LocalDateTime getDateCommande() { return dateCommande; }
    public float getTotal() { return total; }
    public int getIdClient() {
        return idClient;
    }

}
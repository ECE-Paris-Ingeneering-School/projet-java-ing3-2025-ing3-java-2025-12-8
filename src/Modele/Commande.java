package Modele;

import java.time.LocalDateTime;

public class Commande {
    private int id;
    private int idClient;
    private LocalDateTime dateCommande;
    private float total;

    public Commande(int idClient, float total) {
        this.idClient = idClient;
        this.total = total;
        this.dateCommande = LocalDateTime.now();
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setDateCommande(LocalDateTime date) {
        this.dateCommande = date;
    }

    public int getId() { return id; }
    public LocalDateTime getDateCommande() { return dateCommande; }
    public float getTotal() { return total; }
}
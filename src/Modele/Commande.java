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

    public int getIdClient() { return idClient; }
    public LocalDateTime getDateCommande() { return dateCommande; }
    public float getTotal() { return total; }
}

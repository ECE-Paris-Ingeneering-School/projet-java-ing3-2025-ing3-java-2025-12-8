package Modele;

public class Marque {
    private int id;
    private String nom;

    public Marque(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public int getId() { return id; }
    public String getNom() { return nom; }

    @Override
    public String toString() {
        return nom;
    }
}

package Modele;

public class Marque {
    private int id;
    private String nom;
    //constructeur
    public Marque(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }
    //getter
    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    @Override
    public String toString() {
        return id + " - " + nom;
    }
}


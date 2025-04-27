package Modele;
public class Admin {
    private int id;
    private String nom;
    private String email;
    private String motDePasse;

    // Constructeur
    public Admin(int id, String nom, String email, String motDePasse) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.motDePasse = motDePasse;
    }

    // Getter pour id
    public int getId() { return id; }

    // Getter pour nom
    public String getNom() { return nom; }

    // Getter pour email
    public String getEmail() { return email; }

    // Getter pour motDePasse
    public String getMotDePasse() { return motDePasse; }

    // Setter pour nom
    public void setNom(String nom) {
        this.nom = nom;
    }

    // Setter pour email
    public void setEmail(String email) {
        this.email = email;
    }

    // Setter pour motDePasse
    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }
}

class Rectangle extends Forme {
    private int longueur;
    private int largeur;

    public Rectangle() { // Constructeur par défaut
        super();
        this.longueur = 0;
        this.largeur = 0;
    }

    public Rectangle(int couleur, int longueur, int largeur) { // Constructeur paramètres
        super(couleur);
        this.longueur = longueur;
        this.largeur = largeur;
    }

    public Rectangle(Rectangle autre) { // Constructeur copié
        super(autre);
        this.longueur = autre.longueur;
        this.largeur = autre.largeur;
    }

    public int getLongueur() {
        return longueur;
    }

    public int getLargeur() {
        return largeur;
    }

    @Override
    public void Afficher() {
        System.out.println(this);
    }

    @Override
    public float calculerSurface() {
        return longueur * largeur;
    }

    @Override
    public String toString() { // Redéfinition de toString() avec override
        return "Rectangle -> Couleur: " + couleur + ", Longueur: " + longueur + ", Largeur: " + largeur + ", Surface: " + calculerSurface();
    }
}

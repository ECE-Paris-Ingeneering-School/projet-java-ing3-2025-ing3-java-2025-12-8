class Cercle extends Forme {
    private float rayon;

    public Cercle() { // Constructeur
        super();
        this.rayon = 0; // Valeur
    }

    public Cercle(int couleur, float rayon) { // Constructeur avec paramètres
        super(couleur);
        this.rayon = rayon;
    }

    public Cercle(Cercle autre) { // Constructeur copié
        super(autre);
        this.rayon = autre.rayon;
    }

    public float getRayonCercle() {
        return rayon;
    }

    @Override
    public void Afficher() {
        System.out.println(this);
    }

    @Override
    public float calculerSurface() {
        return (float) (Math.PI * rayon * rayon);
    }

    @Override
    public String toString() { // Redéfinition de toString()
        return "Cercle -> Couleur: " + couleur + ", Rayon: " + rayon + ", Surface: " + calculerSurface();
    }
}


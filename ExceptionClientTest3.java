public class DivisionParZero {

    public static void main(String[] args) {
        // Appel de la méthode qui effectue la division
        divisionParZero();
    }

    public static void divisionParZero() {
        try {
            // Tentative de division par zéro
            int resultat = 10 / 0;
        } catch (ArithmeticException e) {
            // Gestion de l'exception et affichage d'un message d'erreur personnalisé
            System.out.println("Erreur : Impossible de diviser par zéro !");
        }
    }
}

// Source : https://stackoverflow.com/questions/21269461/how-does-java-handle-division-by-zero


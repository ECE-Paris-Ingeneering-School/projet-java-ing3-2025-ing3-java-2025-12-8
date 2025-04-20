class AgeInvalideException {
    public AgeInvalideExption(String message) {
        super(message); // erreur lié au super, verifier sur stackOverflow
    }
}

public class ValidationAge {

    public static void main(String[] args) {
        try {
            // Test avec un âge invalide
            validerAge(150);  // erreur : redefinir valider âge en temps voulu
        } catch (AgeInvalideException) {  //
            // Affichage de l'exception personnalisée
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    public static void validerAge(int age) throws AgeInvalideException {  //
        if (age < 0 || age > 120) {
            // Lancer une exception personnalisée si l'âge est invalide
            throw new AgeInvalideException("L'âge " + age + " est invalide. Il doit être compris entre 0 et 120.");
        } else {
            System.out.println("L'âge " + age + " est valide.");
        }
    }
}

// source : https://stackoverflow.com/questions/28542585/why-does-the-main-function-in-java-reside-in-a-class/28542682

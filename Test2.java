import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GestionFichier {

    public static void main(String[] args) {
        // Spécifiez le chemin du fichier à lire
        lireFichier("fichier.txt");
    }

    public static void lireFichier(String chemin) {
        BufferedReader reader = null;

        try {
            // Ouverture du fichier en lecture
            reader = new BufferedReader(new FileReader(chemin));
            String ligne;

            // Lecture ligne par ligne et affichage
            while ((ligne = reader.readLine()) != null) {
                System.out.println(ligne);
            }
        } catch (IOException e) {
            // Gestion des erreurs (fichier introuvable, erreur de lecture)
            System.out.println("Erreur lors de la lecture du fichier : " + e.getMessage());
        } finally {
            // finally pour fermer le fichier, même en cas d'exception
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                System.out.println("Erreur lors de la fermeture du fichier : " + e.getMessage());
            }
        }
    }
}

// source : https://stackoverflow.com/questions/2049380/reading-a-text-file-in-java
// https://stackoverflow.com/questions/33527027/how-can-i-use-java-to-read-a-file?rq=3

package Controleur;

// Importation des vues utilisées par l'admin
import Vue.AdminCatalogueFrame;
import Vue.AdminClientsFrame;
import Vue.AdminCommandesFrame;
import Vue.AdminMenuFrame;
import Vue.AdminStatsFrame;
import Modele.Admin;

public class AdminControleur {

    // Référence vers la fenêtre principale du menu admin
    private AdminMenuFrame menu;

    // Référence vers l'objet Admin connecté (pour savoir qui est connecté)
    private Admin admin;

    // Constructeur sans paramètre : juste création vide
    public AdminControleur() {
        // Le menu sera défini via setMenu plus tard
    }

    // Associe le menu admin à ce contrôleur
    public void setMenu(AdminMenuFrame menu) {
        this.menu = menu;
    }

    // Associe l'admin connecté à ce contrôleur
    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    // Ouvre la fenêtre du catalogue d'articles admin
    public void ouvrirCatalogue() {
        new AdminCatalogueFrame(this).setVisible(true);
    }

    // Ouvre la fenêtre des statistiques admin
    public void ouvrirStats() {
        new AdminStatsFrame(this).setVisible(true);
    }

    // Ouvre la fenêtre de gestion des commandes admin
    public void ouvrirCommandes() {
        new AdminCommandesFrame(this).setVisible(true);
    }

    // Ouvre la fenêtre de gestion des clients
    public void ouvrirClients() {
        new AdminClientsFrame(this).setVisible(true);
    }

    // Retourne au menu principal admin
    public void retournerAuMenu() {
        if (menu != null) {
            menu.setVisible(true); // Si le menu est déjà chargé, le réaffiche
        } else if (admin != null) {
            // Si pas de menu existant, recrée un AdminMenuFrame avec l'admin connecté
            new AdminMenuFrame(admin).setVisible(true);
        }
    }
}

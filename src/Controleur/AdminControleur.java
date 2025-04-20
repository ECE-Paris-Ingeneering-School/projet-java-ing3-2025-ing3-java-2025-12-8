package Controleur;

import Modele.Admin;
import Vue.AdminCatalogueFrame;
import Vue.AdminClientsFrame;
import Vue.AdminCommandesFrame;
import Vue.AdminMenuFrame;
import Vue.AdminStatsFrame;

public class AdminControleur {

    private AdminMenuFrame menu;
    private Admin admin; // ✅ admin connecté

    public AdminControleur() {
        // Le menu sera défini via setMenu
    }

    public void setMenu(AdminMenuFrame menu) {
        this.menu = menu;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public void ouvrirCatalogue() {
        new AdminCatalogueFrame(this).setVisible(true);
    }

    public void ouvrirStats() {
        new AdminStatsFrame(this).setVisible(true);
    }

    public void ouvrirCommandes() {
        new AdminCommandesFrame(this).setVisible(true);
    }

    public void ouvrirClients() {
        new AdminClientsFrame(this).setVisible(true);
    }

    public void retournerAuMenu() {
        if (menu != null) {
            menu.setVisible(true);
        } else if (admin != null) {
            new AdminMenuFrame(admin).setVisible(true); // ✅ on relance avec l’admin connu
        }
    }
}

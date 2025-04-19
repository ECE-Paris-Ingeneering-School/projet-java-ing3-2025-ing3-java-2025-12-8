package Controleur;

import Vue.AdminCatalogueFrame;
import Vue.AdminCommandesFrame;
import Vue.AdminMenuFrame;
import Vue.AdminStatsFrame;

public class AdminControleur {

    private AdminMenuFrame menu;

    public AdminControleur() {
        // Le menu sera instancié ailleurs
    }

    public void setMenu(AdminMenuFrame menu) {
        this.menu = menu;
    }

    public void ouvrirCatalogue() {
        new AdminCatalogueFrame(this).setVisible(true); // passe le contrôleur
    }


    public void ouvrirStats() {
        new AdminStatsFrame(this).setVisible(true);
    }


    public void ouvrirCommandes() {
        new AdminCommandesFrame(this).setVisible(true);
    }


    public void retournerAuMenu() {
        if (menu != null) {
            menu.setVisible(true);
        } else {
            new AdminMenuFrame().setVisible(true);
        }
    }
}

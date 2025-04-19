package Vue;

import DAO.CommandeDAO;
import Controleur.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class AdminStatsFrame extends JFrame {

    private AdminControleur controleur;

    public AdminStatsFrame(AdminControleur controleur) {
        this.controleur = controleur;
        setTitle("Statistiques de ventes");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Onglets pour les différents types de stats
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Top ventes", creerCamembert());
        tabs.addTab("Chiffre d'affaires", creerHistogrammeCA());

        add(tabs, BorderLayout.CENTER);

        // ✅ Ajouter un bouton retour
        JButton btnRetour = new JButton("Retour");
        btnRetour.addActionListener(e -> {
            controleur.retournerAuMenu();
            dispose();
        });
        JPanel panelBas = new JPanel();
        panelBas.add(btnRetour);
        add(panelBas, BorderLayout.SOUTH);
    }

    private ChartPanel creerCamembert() {
        CommandeDAO dao = new CommandeDAO();
        Map<String, Integer> stats = dao.getArticlesLesPlusVendus();

        DefaultPieDataset dataset = new DefaultPieDataset();
        for (Map.Entry<String, Integer> entry : stats.entrySet()) {
            dataset.setValue(entry.getKey(), entry.getValue());
        }

        JFreeChart chart = ChartFactory.createPieChart(
                "Articles les plus vendus",
                dataset,
                true, true, false
        );

        return new ChartPanel(chart);
    }

    private ChartPanel creerHistogrammeCA() {
        CommandeDAO dao = new CommandeDAO();
        Map<String, Float> statsCA = dao.getChiffreAffairesParArticle();

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Float> entry : statsCA.entrySet()) {
            dataset.addValue(entry.getValue(), "CA (€)", entry.getKey());
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Chiffre d'affaires par article",
                "Article",
                "CA (€)",
                dataset,
                PlotOrientation.VERTICAL,
                false, true, false
        );

        return new ChartPanel(chart);
    }
}

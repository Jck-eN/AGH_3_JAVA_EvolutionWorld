package agh.cs.project1;
import agh.cs.project1.Animal;
import agh.cs.project1.EvolutionMap;
import agh.cs.project1.MapPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Window to show the map
 *
 * @author Jacek N.
 */
public class MapSwingVisualizer implements ActionListener {

    private EvolutionMap map;

    JFrame frame;
    private MapPanel mapPanel;
    private Timer timer;

    /**
     * Creates a window containing panel with map
     *
     * @param map we want to visualize
     * @param timeForOneDay time for showing one day of simulation in [ms]
     */
    MapSwingVisualizer(EvolutionMap map, int timeForOneDay) {

        this.map = map;

        this.timer = new Timer(timeForOneDay, this);

        this.frame = new JFrame("Evolution World");
        this.frame.setSize(Config.EVOLUTION_MAP_WIDTH*15, Config.EVOLUTION_MAP_HEIGHT*15);
        this.frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.frame.setLocationRelativeTo(null);
        this.frame.setVisible(true);

        this.mapPanel = new MapPanel(map, this);
        this.mapPanel.setSize(new Dimension(1, 1));


        this.frame.add(mapPanel);

    }

    /**
     * starts timer for each day
     */
    void startSimulation() {
        this.timer.start();
    }

    /**
     * It is executed when timer finish counting
     */
    public void actionPerformed(ActionEvent e) {
        this.mapPanel.repaint();
        this.map.run();

    }

}
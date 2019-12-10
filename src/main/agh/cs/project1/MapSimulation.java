package agh.cs.project1;
import agh.cs.project1.Animal;
import agh.cs.project1.EvolutionMap;
import agh.cs.project1.MapPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MapSimulation implements ActionListener {

    //simulation options:
    public final int delay;
    public EvolutionMap map;
    public int startNumOfAnimals;
    public int grassSpawnedInEachDay;

    //simulation necessary:
    public JFrame frame;
    public MapPanel mapPanel;
    public Timer timer;


    public MapSimulation(EvolutionMap map, int delay) {

        this.map = map;
        this.delay = delay;
        this.grassSpawnedInEachDay = grassSpawnedInEachDay;

        timer = new Timer(delay*1, this);

        frame = new JFrame("Evolution Simulator");
        frame.setSize(1000, 1000);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        mapPanel = new MapPanel(map, this);
        mapPanel.setSize(new Dimension(1, 1));


        frame.add(mapPanel);

    }

    public void startSimulation() {

        timer.start();

    }

    @Override
    //It will executed when timer finish Counted
    public void actionPerformed(ActionEvent e) {
        mapPanel.repaint();
        map.run();

    }

}
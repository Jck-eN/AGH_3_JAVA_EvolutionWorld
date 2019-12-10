package agh.cs.project1;

import javax.swing.*;
import java.awt.*;

public class MapPanel extends JPanel {

    public EvolutionMap map;
    public MapSimulation simulation;

    public MapPanel(EvolutionMap map, MapSimulation simulation) {
        this.map = map;
        this.simulation = simulation;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setSize((int) (simulation.frame.getWidth()), simulation.frame.getHeight());
        this.setLocation(0, 0);
        int width = this.getWidth();
        int height = this.getHeight(); //38 is toolbar size
        int widthScale = Math.round(width / Config.EVOLUTION_MAP_WIDTH);
        int heightScale = height / Config.EVOLUTION_MAP_HEIGHT;

        //draw Steppe
        g.setColor(new Color(170, 224, 103));
        g.fillRect(0, 0, width, height);

        //draw Jungle
        g.setColor(new Color(0, 160, 7));
        g.fillRect(map.getJungleBottomLeft().x * widthScale,
                map.getJungleBottomLeft().y * heightScale,
                (map.getJungleTopRight().x-map.getJungleBottomLeft().x) * widthScale,
                (map.getJungleTopRight().y-map.getJungleBottomLeft().y) * heightScale);

        //draw Grass
        for (Plant plant : map.plants.values()) {
            g.setColor(new Color(67, 222, 31));
            int y = (plant.getPosition()).y * heightScale;
            int x = (plant.getPosition()).x * widthScale;
            g.fillRect(x, y, widthScale, heightScale);
        }
        //draw Animals
        for (Animal a : map.allAnimals()) {
            g.setColor(new Color(227, 222, 31));
            int y = (a.getPosition()).y * heightScale;
            int x = (a.getPosition()).x * widthScale;
            g.fillOval(x, y, widthScale, heightScale);
        }
    }

}
package agh.cs.project1;

import javax.swing.*;
import java.awt.*;

/**
 * Panel showing the map
 *
 * @author Jacek N.
 */
public class MapPanel extends JPanel {

    private EvolutionMap map;
    private MapSwingVisualizer visualizer;

    /**
     *
     * @param map map we want to visualize
     * @param visualizer frame which we show map in
     */
    MapPanel(EvolutionMap map, MapSwingVisualizer visualizer) {
        this.map = map;
        this.visualizer = visualizer;
    }

    /**
     * Draws map on panel
     *
     * @param graphics graphics wi want to paint on
     */
    @Override
    protected void paintComponent(Graphics graphics) {
        this.visualizer.frame.setTitle("Evolution World - Dzień: "+ this.map.getDay());
        super.paintComponent(graphics);
        this.setSize((int) (visualizer.frame.getWidth()), visualizer.frame.getHeight());
        this.setLocation(0, 0);
        int width = this.getWidth();
        int height = this.getHeight()-40; //38 is toolbar size
        int widthScale = width / Config.EVOLUTION_MAP_WIDTH;
        int heightScale = height / Config.EVOLUTION_MAP_HEIGHT;

        graphics.setColor(new Color(199, 255, 0));
        graphics.fillRect(0, 0, width, height);
        graphics.setColor(new Color(134, 218, 0));
        graphics.fillRect(this.map.getJungle().getBottomLeftCorner().x*widthScale,
                this.map.getJungle().getBottomLeftCorner().y*heightScale,
                (this.map.getJungle().getTopRightCorner().x-this.map.getJungle().getBottomLeftCorner().x+1)*widthScale,
                (this.map.getJungle().getTopRightCorner().y-this.map.getJungle().getBottomLeftCorner().y+1)*heightScale);
        for(int i=0; i< Config.EVOLUTION_MAP_WIDTH; i++){               //Fills every square of the map with proper color
            for(int j=0; j< Config.EVOLUTION_MAP_HEIGHT; j++){          //for animals and plants
                Object obj = this.map.objectAt(new Vector2d(i, j));
                if(obj instanceof Animal) {
                    int energy =((Animal) obj).getEnergy();             //The darker the rectangle is, the more energy animal possess
                    int red = 255-energy*2;
                    if(red<100)red=100;
                    if(red > 255) red=255;
                    graphics.setColor(new Color(red, 0, 0));
                    graphics.fillRect(i*widthScale, j*heightScale, widthScale, heightScale);
                }
                else if(obj instanceof Plant){
                    int size = ((Plant) obj).getSize();
                    int green = 255-size*4;                              //The darker the circle is, the bigger the plant is
                    if(green<100)green=100;
                    if(green > 255) green=255;
                    graphics.setColor(new Color(0, green, 0));
                    graphics.fillOval(i*widthScale, j*heightScale, widthScale, heightScale);
                }


            }
        }
    }

}
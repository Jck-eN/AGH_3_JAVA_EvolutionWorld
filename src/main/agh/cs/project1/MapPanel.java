package agh.cs.project1;

import javax.swing.*;
import java.awt.*;

public class MapPanel extends JPanel {

    private EvolutionMap map;
    private MapSwingVisualizer visualizer;

    MapPanel(EvolutionMap map, MapSwingVisualizer visualizer) {
        this.map = map;
        this.visualizer = visualizer;
    }

    @Override
    protected void paintComponent(Graphics g) {
        this.visualizer.frame.setTitle("Evolution World - Dzień: "+ this.map.getDay());
        super.paintComponent(g);
        this.setSize((int) (visualizer.frame.getWidth()), visualizer.frame.getHeight());
        this.setLocation(0, 0);
        int width = this.getWidth();
        int height = this.getHeight()-40; //38 is toolbar size
        int widthScale = width / Config.EVOLUTION_MAP_WIDTH;
        int heightScale = height / Config.EVOLUTION_MAP_HEIGHT;

        g.setColor(new Color(199, 255, 0));
        g.fillRect(0, 0, width, height);
        g.setColor(new Color(134, 218, 0));
        g.fillRect(this.map.getJungle().getBottomLeftCorner().x*widthScale, this.map.getJungle().getBottomLeftCorner().y*heightScale,
                (this.map.getJungle().getTopRightCorner().x-this.map.getJungle().getBottomLeftCorner().x+1)*widthScale,
                (this.map.getJungle().getTopRightCorner().y-this.map.getJungle().getBottomLeftCorner().y+1)*heightScale);
        for(int i=0; i< Config.EVOLUTION_MAP_WIDTH; i++){
            for(int j=0; j< Config.EVOLUTION_MAP_HEIGHT; j++){
                Object obj = this.map.objectAt(new Vector2d(i, j));
                if(obj instanceof Animal) {
                    int enegry =((Animal) obj).getEnergy();
                    int red = 255-enegry*2;
                    if(red<100)red=100;
                    if(red > 255) red=255;
                    g.setColor(new Color(red, 0, 0));
                    g.fillRect(i*widthScale, j*heightScale, widthScale, heightScale);
                }
                else if(obj instanceof Plant){
                    int size = ((Plant) obj).getSize();
                    int green = 255-size*4;
                    if(green<100)green=100;
                    if(green > 255) green=255;
                    g.setColor(new Color(0, green, 0));
                    g.fillOval(i*widthScale, j*heightScale, widthScale, heightScale);
                }


            }
        }
    }

}
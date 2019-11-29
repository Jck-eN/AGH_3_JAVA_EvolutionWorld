package agh.cs.project1;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class World {
    public static void main(String[] args) {
        try {

            Object obj = new JSONParser().parse(new FileReader("D:\\Jacek\\Programowanie\\AGH\\AGH_3_JAVA\\labs\\src\\main\\agh\\cs\\project1\\config.json"));
            JSONObject jo = (JSONObject) obj;
            Config.EVOLUTION_MAP_WIDTH = Math.toIntExact((Long) jo.get("width"));
            Config.EVOLUTION_MAP_HEIGHT = Math.toIntExact((Long) jo.get("height"));
            Config.DEFAULT_ENERGY = Math.toIntExact((Long)  jo.get("startEnergy"));
            Config.ENERGY_LOST_PER_MOVE = Math.toIntExact((Long) jo.get("moveEnergy"));
            Config.PLANT_DEFAULT_SIZE = Math.toIntExact((Long) jo.get("startPlantSize"));
            Config.PLANT_MAX_SIZE = Math.toIntExact((Long) jo.get("maxPlantSize"));
            Config.JUNGLE_HEIGHT = Math.toIntExact(Math.round(Double.valueOf(Config.EVOLUTION_MAP_HEIGHT) * (Double) jo.get("jungleRatio")));
            Config.JUNGLE_WIDTH = Math.toIntExact(Math.round(Double.valueOf(Config.EVOLUTION_MAP_WIDTH) * (Double) jo.get("jungleRatio")));

            EvolutionMap map = new EvolutionMap();
            //    IWorldMap map = new RectangularMap(10, 5);
            Animal a = new Animal(map, new Vector2d(3, 4));

            Animal b = new Animal(map, new Vector2d(34, 24));
            Animal c = new Animal(map, new Vector2d(2, 3));
            Animal d = new Animal(map, new Vector2d(0, 0));
            Animal e = new Animal(map, new Vector2d(53, 17));
            Animal f = new Animal(map, new Vector2d(11, 5));
            Animal g = new Animal(map, new Vector2d(13, 6));
            Animal h = new Animal(map, new Vector2d(18, 17));
            Animal j = new Animal(map, new Vector2d(13, 11));
            for(int i=0; i<100000; i++){
                map.run();
                if(i%100==0){
                    System.out.println(map);
                    System.out.println("Tura: " +i);
                    Thread.sleep(100);
                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                }
            }
            System.out.println(map);
            System.out.println("koniec");
        }
        catch (IllegalArgumentException ex){
            System.out.println(ex.getMessage());
            System.exit(1);
        }
        catch (InterruptedException ex2){
            System.out.println(ex2.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

}

package agh.cs.project1;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

public class World {
    public static void main(String[] args) {
        if(args.length == 0){
            System.out.println("program <config.json>");
            System.exit(1);
        }
        try {
            Config.init(args[0]);
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
        }


    }

}

package agh.cs.project1;

import java.io.*;

public class World {
    public static void main(String[] args) {
        if(args.length < 2){
            System.out.println("program <config.json> <number_of_animals");
            System.exit(1);
        }
        try {
            Config.init(args[0]);
            EvolutionMap map = new EvolutionMap();
            for(int j = 0; j<Integer.parseInt(args[1]); j++){
                new Animal(map, map.getRandomFreePosition());
            }
            MapSimulation sim = new MapSimulation(map,50);
            sim.startSimulation();

//            for(int i=0; i<100000; i++){
//                map.run();
//                if(i%100==0){
//                    System.out.println(map);
//                    System.out.println("Tura: " +i);
//                    Thread.sleep(100);
//                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
//                }
//            }
//            System.out.println(map);
//            System.out.println("koniec");
        }
        catch (IllegalArgumentException ex){
            System.out.println(ex.getMessage());
            System.exit(1);
        }
//        catch (InterruptedException ex2){
//            System.out.println(ex2.getMessage());
//        } catch (IOException e) {
//            e.printStackTrace();
//    }



    }

}

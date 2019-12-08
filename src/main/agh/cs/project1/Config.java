package agh.cs.project1;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

class Config {
    public static Integer EVOLUTION_MAP_WIDTH = 100;
    public static Integer EVOLUTION_MAP_HEIGHT = 30;
    public static Integer JUNGLE_WIDTH = 10;
    public static Integer JUNGLE_HEIGHT = 10;
    public static Integer PLANT_MAX_SIZE = 10;
    public static Integer PLANT_DEFAULT_SIZE = 5;
    public static Integer ENERGY_LOST_PER_MOVE = 1;
    public static Integer REQUIRED_ENERGY = 100;
    public static Integer DEFAULT_ENERGY = 200;

    public static void init(String filename){
        try(Reader reader = new FileReader(filename)) {
            JSONObject jo =(JSONObject) new JSONParser().parse(reader);

            Config.EVOLUTION_MAP_WIDTH = Math.toIntExact((Long) jo.get("width"));
            Config.EVOLUTION_MAP_HEIGHT = Math.toIntExact((Long) jo.get("height"));
            Config.DEFAULT_ENERGY = Math.toIntExact((Long) jo.get("startEnergy"));
            Config.REQUIRED_ENERGY = Config.DEFAULT_ENERGY/2;
            Config.ENERGY_LOST_PER_MOVE = Math.toIntExact((Long) jo.get("moveEnergy"));
            Config.PLANT_DEFAULT_SIZE = Math.toIntExact((Long) jo.get("startPlantSize"));
            Config.PLANT_MAX_SIZE = Math.toIntExact((Long) jo.get("maxPlantSize"));
            Config.JUNGLE_HEIGHT = Math.toIntExact(Math.round(Double.valueOf(Config.EVOLUTION_MAP_HEIGHT) * (Double) jo.get("jungleRatio")));
            Config.JUNGLE_WIDTH = Math.toIntExact(Math.round(Double.valueOf(Config.EVOLUTION_MAP_WIDTH) * (Double) jo.get("jungleRatio")));



        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}

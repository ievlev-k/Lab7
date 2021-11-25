package core;

import datee.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayDeque;

public class FileParser {
    public ArrayDeque<City> parse(String fileName){
        ArrayDeque<City> collectionInput = new ArrayDeque<>();
        JSONParser jsonParser = new JSONParser();

        try(BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(fileName))) {
            Reader reader = new InputStreamReader(bufferedInputStream,"UTF-8");
            Object obj = jsonParser.parse(reader);
            JSONArray jsonArray = (JSONArray) obj;
            collectionInput = saveIntoCollection(jsonArray);

        } catch (IOException e) {
            e.printStackTrace();
        }catch (ParseException e){
            System.out.println("Коллекцию не удалось закрузить. Данные поломаны. Следовательно не все элементы коллекции смогли подгрузиться!");


        }catch (NullPointerException e){
            e.printStackTrace();
        }


        return collectionInput;
    }

    private ArrayDeque<City> saveIntoCollection(JSONArray jsonArray){
        ArrayDeque<City> newDeque = new ArrayDeque<>();
        jsonArray.forEach(p -> {
            try {
                newDeque.add(convertJsonObjIntoCity((JSONObject ) p));
            }catch (java.text.ParseException e){
                e.printStackTrace();
            }catch (NullPointerException e){
                System.out.println("Данные поврежденны, не все элементы смогли подгрузиться!");

            }
        });
        return newDeque;
    }

    private City convertJsonObjIntoCity(JSONObject jsonObject) throws java.text.ParseException{
        City c = new City();
        //set id
        Long newId = (Long) jsonObject.get("id");
        if(CollectionManager.IDChecker.contains(newId)){
            System.out.println("Такое Id уже существует!");
        }
        else {
            CollectionManager.IDChecker.add(newId);
            c.setId(newId);
        }
        // set name
        c.setName((String) jsonObject.get("name"));

        // set coordinates
        JSONObject coordinatesObj = (JSONObject) jsonObject.get("coordinates");
        c.setCoordinates(new Coordinates(Math.toIntExact((long) coordinatesObj.get("x")), Math.toIntExact(((Long) coordinatesObj.get("y")).intValue())));

        // set creationDate
        String dateString = (String) jsonObject.get("creationDate");
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        ZonedDateTime dateTime = ZonedDateTime.parse(dateString,formatter);
        c.setCreationDate(dateTime);

        // set area
        float f =  ((Double) jsonObject.get("area")).floatValue();
        c.setArea(f);

        //set population
        c.setPopulation((long) jsonObject.get("population"));

        //set metersAboveSeaLevel
        c.setMetersAboveSeaLevel(((Long)jsonObject.get("metersAboveSeaLevel")).intValue());

        //set capital
        c.setCapital((boolean) jsonObject.get("capital"));

        //set government
        String governmentString = (String) jsonObject.get("government");
        Government governmentEnum = Government.valueOf(governmentString);
        c.setGovernment(governmentEnum);

        //set standardOfLiving
        try {
            String standardOfLivingString = (String) jsonObject.get("standardOfLiving");
            StandardOfLiving standardOfLivingEnum = StandardOfLiving.valueOf(standardOfLivingString);
            c.setStandardOfLiving(standardOfLivingEnum);
        }catch (NullPointerException e){
            c.setStandardOfLiving(null);
        }


        //set governor
        JSONObject governorObj = (JSONObject) jsonObject.get("governor");
        c.setHumen(new Human((String) governorObj.get("name")));

        return c;


    }
}

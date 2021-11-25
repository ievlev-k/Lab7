package core;

import bd.CollectionHandler;
import datee.*;
import interaction.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


public class CollectionManager {

    public User user;
    public ArrayDeque<City> listCity = new ArrayDeque<>();
    public static HashSet<Long> IDChecker = new HashSet<>();
    private final ZonedDateTime creationDate = ZonedDateTime.now();
    private final FileParser fileParser = new FileParser();
    private final CollectionHandler collectionHandler;
    private final UserHandler userHandler;
    public CollectionManager (CollectionHandler collectionHandler, UserHandler userHandler){
        this.collectionHandler = collectionHandler;
        this.userHandler = userHandler;
    }

    public UserHandler getUserHandler(){
        return userHandler;
    }
    public void loadCollectionFromDatabase(){
        try {
            listCity = collectionHandler.loadCollectionFromDB();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void add(City city){listCity.addLast(city);}

    public int size() {return listCity.size();}


    public void deleteElementsByUser( String user){
        String username = user;
        listCity = listCity.stream().filter(x -> !x.getUser().equals(username)).collect(Collectors.toCollection(ArrayDeque::new));

    }
    public void clear() {listCity.clear();}

    public ZonedDateTime getCreationDate() {return this.creationDate;}

    public CollectionHandler getCollectionHandler(){
        return collectionHandler;
    }

    public void printCollection(){
        if(listCity.size() == 0){
            ResponseOutput.appendln("Коллекция пустая");
        }else {
            listCity.forEach(p -> ResponseOutput.appendln(p.toString()));
        }
    }

    public boolean removeFirst(User otherUser){

        City city =listCity.peekFirst();
        assert city != null;
        if (city.getUser().equals(otherUser.getUsername())){
            return true;
        }
        return false;
    }


    public void reedInputFromJSONFile(String inputFileName){
        listCity = fileParser.parse(inputFileName);

    }

    public boolean removeByID(Long id, User otherUser){
        long n = 0;
        n = listCity.stream().filter(p -> (p.getId().equals(id) && p.getUser().equals(otherUser.getUsername()))).count();

        listCity.stream().filter(p -> (p.getId().equals(id) && p.getUser().equals(otherUser.getUsername()))).forEach(p -> {listCity.remove(p);
                                                                            });
        if (n == 0){
            return false;
        }return true;
    }

    public Long idCreate(){

        Long newID = new Random().nextLong();
        if (IDChecker.contains(newID) || newID < 0){
            return idCreate();}
        return newID;
    }

//    public boolean removeFirst() {
//        int size = listCity.size();
//        if (size == 0) {
//            System.out.println("В коллекции нет элементов");
//            return false;
//        }
//
//
//        if (size == 1) {
//            listCity.clear();
//            return true;
//        }
//
//        if (size > 1) {
//            listCity.removeFirst();
////            Iterator<City> iterator = listCity.iterator();
////            if (iterator.hasNext()) {
////                City firstDragon = iterator.next();
////                System.out.println("Был удален первый элемент с id = " + firstDragon.getId());
////                iterator.remove();
////
////                return true;
////            }
//            return false;
//        }
//        return false;
//    }


    public void removeGreater(City c, User otherUser){
        long n = 0;
        n = listCity.stream().filter(p -> ((c.compareTo(p) < 0)&&p.getUser().equals(otherUser.getUsername()))).count();
        listCity.stream().filter(p -> ((c.compareTo(p) < 0)&&p.getUser().equals(otherUser.getUsername()))).forEach(p -> {ResponseOutput.appendln("Был удален элемент с id = "+ p.getId());
            listCity.remove(p);
        });


        if (n == 0){
            ResponseOutput.appendln("Элементы не были удалены");

        }
    }


    public void removeLower(City c, User otherUser){
        long n = 0;
        n = listCity.stream().filter(p -> ((c.compareTo(p) > 0)&&p.getUser().equals(otherUser.getUsername()))).count();
        listCity.stream().filter(p -> ((c.compareTo(p) > 0)&&p.getUser().equals(otherUser.getUsername()))).forEach(p -> {ResponseOutput.appendln("Был удален элемент с id = "+ p.getId());
            listCity.remove(p);
        });


        if (n == 0){
            ResponseOutput.appendln("Элементы не были удалены");

        }
    }
    public void printElementsByEquals(StandardOfLiving standardOfLiving){
        listCity.stream().filter(c -> c.getStandardOfLiving() == standardOfLiving).sorted(new Comparator<City>() {
            @Override
            public int compare(City o1, City o2) {
                return o1.compareTo(o2);
            }
        }).forEach(c -> ResponseOutput.appendln(c.toString()));
//        listCity.forEach(p-> {
//            int n = 0;
//            if(p.getStandardOfLiving() == standardOfLiving){
//                n = n + 1;
//                System.out.println(p.toString());
//                if (n == 0){
//                    System.out.println("В коллекции нет элементов с таким standardOfLiving");
//                }
//            }  });
    }

    public void printElementsByNotEquals(Government government){

        if (government == Government.MERITOCRACY){
            ResponseOutput.appendln("Выше такого показателя нет City, есть только с таким же.");
        }
        if (government == Government.THALASSOCRACY){
            ResponseOutput.appendln("Эти City выше заданного");
            listCity.stream().sorted(new Comparator<City>() {
                @Override
                public int compare(City o1, City o2) {
                    return o1.compareTo(o2);
                }
            }).forEach(p -> {
                if (p.getGovernment() == Government.MERITOCRACY){
                    ResponseOutput.appendln(p.toString());
                }
            });
            }
        if (government == Government.JUNTA){
            ResponseOutput.appendln("Эти City выше заданного");
            listCity.stream().sorted(new Comparator<City>() {
                @Override
                public int compare(City o1, City o2) {
                    return o1.compareTo(o2);
                }
            }).forEach(p -> {
                if (p.getGovernment() == Government.MERITOCRACY){
                    ResponseOutput.appendln(p.toString());
                }
            });

            listCity.stream().sorted(new Comparator<City>() {
                @Override
                public int compare(City o1, City o2) {
                    return o1.compareTo(o2);
                }
            }).forEach(p -> {
                if (p.getGovernment() == Government.THALASSOCRACY){
                    ResponseOutput.appendln(p.toString());
                }
            });
        }
    }

    public void printAscending(){

        listCity.stream().sorted(new Comparator<City>() {
            @Override
            public int compare(City o1, City o2) {
                return o1.getMetersAboveSeaLevel() - o2.getMetersAboveSeaLevel();
            }
        }).forEach(c -> ResponseOutput.appendln(c.toString()));

    }

    public void save(String fileName){
        JSONArray CityList = new JSONArray();
        for (City city : listCity){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", city.getId());
            jsonObject.put("name", city.getName());
            /*
             * Запись координат в файл
             */
            JSONObject coordinatesObj = new JSONObject();
            coordinatesObj.put("x", city.getCoordinates().getX());
            coordinatesObj.put("y", city.getCoordinates().getY());
            jsonObject.put("coordinates", coordinatesObj);
            /*
             * Запись даты создания а файл
             */

            ZonedDateTime date = city.getCreationDate();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME;
            String stringDateTime = date.format(dateTimeFormatter);
            jsonObject.put("creationDate", stringDateTime);

            jsonObject.put("area", city.getArea());

            jsonObject.put("population", city.getPopulation());

            jsonObject.put("metersAboveSeaLevel", city.getMetersAboveSeaLevel());

            jsonObject.put("capital", city.getCapital());

            jsonObject.put("government", city.getGovernment().toString());

            try {
                jsonObject.put("standardOfLiving", city.getStandardOfLiving().toString());
            }catch (NullPointerException e){
                jsonObject.put("standardOfLiving", null);
            }

            /*
             * Запись Human а файл
             */
            JSONObject humanObj = new JSONObject();
            humanObj.put("name", city.getGovernor().getName());
            jsonObject.put("governor", humanObj);

            CityList.add(jsonObject);
        }
        /*
            Запись выходного файла
         */

        try(BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(fileName))) {

            bufferedOutputStream.write(CityList.toJSONString().getBytes(StandardCharsets.UTF_8));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}


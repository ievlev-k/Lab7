package core;

import datee.*;

import java.time.ZonedDateTime;
import java.util.Random;
import java.util.Scanner;

public class CommandAsker {
    private static final Scanner scanner = new Scanner(System.in);
    private final InputChecker inputChecker;
    public CommandAsker(InputChecker ic){this.inputChecker = ic;}

    public City createCity(){
        City newCity = new City();
        System.out.println("Создаем City");
        newCity.setId(idAsker());
        newCity.setName(nameAsker());
        newCity.setCoordinates(coordinatesAsker());
        newCity.setCreationDate(dateAsker());
        newCity.setArea(areaAsker());
        newCity.setPopulation(populationAsker());
        newCity.setMetersAboveSeaLevel(metersAboveSeaLevelAsker());
        newCity.setCapital(capitalAsker());
        newCity.setGovernment(askerGoverment());
        newCity.setStandardOfLiving(standardOfLivingAsker());
        newCity.setHumen(humanAsker());
        return newCity;

    }

    public Long idAsker(){

        Long newID = new Random().nextLong();
        if (CollectionManager.IDChecker.contains(newID) || newID < 0){
            return idAsker();
        }else {
            CollectionManager.IDChecker.add(newID);
            System.out.println("Id генерируется само.");
            System.out.println("ID = " + newID + " успешно сгенерировался");
            return newID;
        }
    }

    public String nameAsker(){
        while (true){
            System.out.println("Вставте имя: ");
            String name = scanner.nextLine().trim();
            if (name.equals("")) {
                System.out.println("Вставсте имя еще раз. Name не может быть пустым.");
            } else {
                return name;
            }
        }
    }

    public Coordinates coordinatesAsker(){
        System.out.println("Введите координаты: ");
        System.out.println("Введите X: ");
        long x = longAsker(-847, Long.MAX_VALUE, false);
        System.out.println("Введите Y: ");
        Integer y = intAsker(Integer.MIN_VALUE, Integer.MAX_VALUE, false);
        return new Coordinates(x,y);
    }

    public ZonedDateTime dateAsker(){return ZonedDateTime.now();}

    public Long longAsker(long min, long max, boolean canEmpty) {
        while (true) {

            String[] inputNumber = scanner.nextLine().trim().split(" ");
            if (canEmpty && inputNumber[0].equals("")) {
                return null;
            } else {
                if (inputNumber.length != 1) {
                    System.out.println("Введите ровно 1 long число: ");
                } else {
                    if (!inputChecker.longChecker(inputNumber[0], min, max, canEmpty)) continue;
                    return Long.parseLong(inputNumber[0]);

                }
            }
        }
    }





    public float areaAsker(){
        System.out.println("Введите area: ");
        return floatAsker(0, Float.MAX_VALUE, false);
    }

    public long populationAsker(){
        System.out.println("Введите population: ");
        return longAsker(0, Long.MAX_VALUE, false);
    }

    public int metersAboveSeaLevelAsker(){
        System.out.println("Введите metersAboveSeaLevel: ");
        return intAsker(Integer.MIN_VALUE, Integer.MAX_VALUE,false);
    }

    public boolean capitalAsker(){
        System.out.println("Введите capital: ");
        return booleanAsker();
    }

    public Government askerGoverment(){
        while (true) {
            System.out.println("Введите government: ");
            String[] inputNumber = scanner.nextLine().trim().split(" ");
            if (inputNumber.length != 1) {
                System.out.println("Введите тоkько 1 government");
            } else {
                try {
                    return Government.valueOf(inputNumber[0]);
                } catch (IllegalArgumentException e) {
                    System.out.println("Неверный government! Такого government нет");
                    System.out.println("Введите характер из следующего списка");
                    for (Government government : Government.values()) {
                        System.out.println(government);
                    }
                }
            }
        }
    }

    public StandardOfLiving standardOfLivingAsker(){
        while (true) {
            System.out.println("Введите standardOfLiving: ");
            String input = scanner.nextLine();
            if (input.isEmpty()){
                return null;
            }
            String[] inputNumber = input.trim().split(" ");

            if (inputNumber.length != 1) {
                System.out.println("Введите только 1 government");
            } else {
                try {
                    return StandardOfLiving.valueOf(inputNumber[0]);
                } catch (IllegalArgumentException e) {
                    System.out.println("Неверный standardOfLiving! Такого standardOfLiving нет");
                    System.out.println("Введите характер из следующего списка");
                    for (StandardOfLiving standardOfLiving : StandardOfLiving.values()) {
                        System.out.println(standardOfLiving);
                    }
                }
            }
        }
    }

    public Human humanAsker(){
        while (true){
            System.out.println("Введите governor name: ");
            String name = scanner.nextLine().trim();
            if (name.equals("")) {
                System.out.println("Вставсте имя еще раз. Name не может быть пустым.");
            } else {
                return new Human(name);
            }
        }

    }

    public Float floatAsker(float min, float max, boolean canEmpty) {
        while (true) {
            String[] inputNumber = scanner.nextLine().trim().split(" ");
            if (canEmpty && inputNumber[0].equals("")) {
                return null;
            }
            if (inputNumber.length != 1) {
                System.out.println("Введите ровно 1 float число: ");
            } else {
                if (!inputChecker.floatChecker(inputNumber[0], min, max, canEmpty)) continue;
                return Float.parseFloat(inputNumber[0]);
            }
        }
    }


    public Integer intAsker(int min, int max, boolean canEmpty) {
        while (true) {
            String[] inputNumber = scanner.nextLine().trim().split(" ");
            if (canEmpty && inputNumber[0].equals("")) {
                return null;
            } else {
                if (inputNumber.length != 1) {
                    System.out.println("Введите ровно 1 integer число: ");
                } else {
                    if (!inputChecker.intChecker(inputNumber[0], min, max, canEmpty)) continue;
                    return Integer.parseInt(inputNumber[0]);
                }
            }
        }
    }

    public boolean booleanAsker(){
        while (true){
            String[] inputNumber = scanner.nextLine().trim().split(" ");
            if (inputNumber.length != 1) {
                System.out.println("Введите ровно 1 boolean число: ");
            } else {
                switch (inputNumber[0]){
                    case "true":
                        return true;
                    case "false":
                        return false;
                    default:
                        System.out.println("Значеноие может быть true или false. Введите значение повторно:");
                }

            }
        }
    }



}

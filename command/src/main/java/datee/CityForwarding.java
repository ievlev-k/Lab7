package datee;

import datee.Coordinates;
import datee.Government;
import datee.Human;
import datee.StandardOfLiving;

import java.io.Serializable;
import java.time.ZonedDateTime;

public class CityForwarding implements Serializable {

    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private float area; //Значение поля должно быть больше 0
    private long population; //Значение поля должно быть больше 0
    private int metersAboveSeaLevel;
    private boolean capital;
    private Government government; //Поле не может быть null
    private StandardOfLiving standardOfLiving; //Поле может быть null
    private Human governor; //Поле не может быть null

    public CityForwarding(String name, Coordinates coordinates, float area, long population,
                          int metersAboveSeaLevel, boolean capital, Government government, StandardOfLiving standardOfLiving, Human governor){
        this.name = name;
        this.coordinates = coordinates;
        this.area = area;
        this.population = population;
        this.metersAboveSeaLevel = metersAboveSeaLevel;
        this.capital = capital;
        this.government = government;
        this.standardOfLiving = standardOfLiving;
        this.governor = governor;
    }

    public String getName() {
        return name;
    }

    public StandardOfLiving getStandardOfLiving() {
        return standardOfLiving;
    }

    public Human getGovernor() {
        return governor;
    }

    public Government getGovernment() {
        return government;
    }

    public int getMetersAboveSeaLevel() {
        return metersAboveSeaLevel;
    }

    public long getPopulation() {
        return population;
    }

    public float getArea() {
        return area;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public boolean getCapital(){
        return capital;
    }

}

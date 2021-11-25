package datee;

import interaction.User;

import java.io.Serializable;
import java.time.ZonedDateTime;

public class City implements Comparable<City> {
    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private float area; //Значение поля должно быть больше 0
    private long population; //Значение поля должно быть больше 0
    private int metersAboveSeaLevel;
    private boolean capital;
    private Government government; //Поле не может быть null
    private StandardOfLiving standardOfLiving; //Поле может быть null
    private Human governor; //Поле не может быть null
    private String user;


    public Long getId() {
        return id;
    }


    public void setUser(String user){
        this.user = user;
    }

    public String getUser(){
        return user;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public float getArea() {
        return area;
    }

    public void setArea(float area) {
        this.area = area;
    }

    public void setPopulation(long population) {
        this.population = population;
    }

    public long getPopulation() {
        return population;
    }

    public int getMetersAboveSeaLevel() {
        return metersAboveSeaLevel;
    }

    public void setMetersAboveSeaLevel(int metersAboveSeaLevel) {
        this.metersAboveSeaLevel = metersAboveSeaLevel;
    }

    public boolean getCapital(){
        return capital;
    }

    public void setCapital(boolean capital) {
        this.capital = capital;
    }

    public Government getGovernment() {
        return government;
    }

    public void setGovernment(Government government) {
        this.government = government;
    }

    public Human getGovernor() {
        return governor;
    }

    public void setHumen(Human governor) {
        this.governor = governor;
    }

    public StandardOfLiving getStandardOfLiving() {
        return standardOfLiving;
    }

    public void setStandardOfLiving(StandardOfLiving standardOfLiving) {
        this.standardOfLiving = standardOfLiving;
    }

    @Override
    public String toString() {
        String info = "";
        info += ("Name: " + name + '\n');
        info += ("ID: " + id + '\n');
        info += ("Coordinates: \n");
        info += ("             x: " + coordinates.getX() + '\n');
        info += ("             y: " + coordinates.getY() + '\n');
        info += ("creationDate: " + creationDate + '\n');
        info += ("area: " + area + '\n');
        info += ("population: " + population + '\n');
        info += ("metersAboveSeaLevel: " + metersAboveSeaLevel + '\n');
        info += ("capital: " + capital + '\n');
        info += ("government: " + government + '\n');
        info += ("standardOfLiving: " + standardOfLiving + '\n');
        info += ("Human: \n");
        info += ("      name: " + governor.getName() + '\n');
        return info;
    }

    @Override
    public int compareTo(City o) {
        return (int) metersAboveSeaLevel - o.getMetersAboveSeaLevel();
    }
}
package commands;


import core.*;

import datee.*;
import interaction.User;

import java.time.ZonedDateTime;

public class RemoveGreaterCommand extends AbstractCommand{
    private final CollectionManager collectionManager;
    private final CommandAsker commandAsker;
    public RemoveGreaterCommand(CollectionManager cm, CommandAsker ca){

        this.collectionManager = cm;
        this.commandAsker = ca;
    }

    @Override
    public boolean execute(String commandStringArgument, Object commandObjectArgument, User user) {
        if (collectionManager.size() <= 0){
            System.out.println("В коллекции нет элементов");
        }else {
            CityForwarding cityForwarding = (CityForwarding) commandObjectArgument;
            City newCity = new City();
            newCity.setId(collectionManager.idCreate());
            newCity.setName(cityForwarding.getName());
            newCity.setCoordinates(cityForwarding.getCoordinates());
            newCity.setCreationDate(ZonedDateTime.now());
            newCity.setArea(cityForwarding.getArea());
            newCity.setPopulation(cityForwarding.getPopulation());
            newCity.setMetersAboveSeaLevel(cityForwarding.getMetersAboveSeaLevel());
            newCity.setCapital(cityForwarding.getCapital());
            newCity.setGovernment(cityForwarding.getGovernment());
            newCity.setStandardOfLiving(cityForwarding.getStandardOfLiving());
            newCity.setHumen(cityForwarding.getGovernor());


            collectionManager.removeGreater(newCity, user);
            collectionManager.getCollectionHandler().deleteGreater(user, newCity);
        }
        return true;
    }
}
